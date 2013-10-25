(ns {{name}}.init
  (:require [reverie.migration :as migration])
  (:use [korma.db :only [create-db default-connection {{db-type}}]]
        [reverie.atoms :only [settings]]
        [reverie.server :only [load-views-ns]]))


(defn init [db-settings]
  (let [db (create-db ({{db-type}} (:db db-settings)))]
    (default-connection db)
    (swap! settings assoc :dbs {:default db})
    (migration/open-global-when-necessary (:lobos-db db-settings))
    (migration/migrate)
    (migration/migrate '{{name}}.objects.migrations)
    (load-views-ns '{{name}}.templates)
    (load-views-ns '{{name}}.objects)))
