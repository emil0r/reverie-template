(ns {{name}}.init
  (:require [reverie.auth.user :as user]
            [reverie.migration :as migration])
  (:use [korma.db :only [defdb {{db-type}}]]
        [reverie.atoms :only [read-routes!]]
        [reverie.server :only [load-views-ns]]))


(defdb {{name}}-db ({{db-type}} {:db "{{db}}"
                                 :user "{{db-user}}"
                                 :password "{{db-password}}"}))
(def lobos-db {:classname "{{db-driver}}"
               :subprotocol "{{db-subprotocol}}"
               :subname "{{db-subname}}"
               :user "{{db-user}}"
               :password "{{db-password}}"})

(defn init-superuser [first-name last-name name password email]
  (user/add! {:first-name first-name :last-name last-name :name name
              :password password :email email :is-staff true :is-admin true}))

(defn init []
  (migration/open-global-when-necessary lobos-db)
  (migration/migrate)
  (migration/migrate '{{name}}.objects.migrations)
  (read-routes!)
  
  (load-views-ns '{{name}}.templates)
  (load-views-ns '{{name}}.objects))
