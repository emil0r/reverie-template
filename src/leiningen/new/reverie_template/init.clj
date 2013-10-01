(ns {{name}}.init
  (:require [reverie.migration :as migration])
  (:use [korma.db :only [defdb {{db-type}}]]
        [reverie.server :only [load-views-ns]]))


(defdb {{name}}-db ({{db-type}}
                    {:db "{{db}}"
                     :user "{{db-user}}"
                     :password "{{db-password}}"}))
(def lobos-db {:classname "{{db-driver}}"
               :subprotocol "{{db-subprotocol}}"
               :subname "{{db-subname}}"
               :user "{{db-user}}"
               :password "{{db-password}}"})

(defn init []
  (migration/open-global-when-necessary lobos-db)
  (migration/migrate)
  (migration/migrate '{{name}}.objects.migrations)
  
  (load-views-ns '{{name}}.templates)
  (load-views-ns '{{name}}.objects))
