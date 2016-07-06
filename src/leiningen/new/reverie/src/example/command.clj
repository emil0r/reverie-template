(ns {{name}}.command
    (:require [clojure.string :as str]
              [com.stuartsierra.component :as component]
              [ez-database.core :as db]
              [honeysql.core :as sql]
              [reverie.auth :as auth]
              reverie.auth.sql
              [reverie.database.sql :as db.sql]
              [reverie.migrator :as migrator]
              [reverie.migrator.sql :as migrator.sql]
              reverie.modules.auth
              [reverie.modules.role :as role]
              [reverie.settings :as settings]))

(defn- get-settings [path]
  (component/start (settings/settings path)))

(defn- get-db [settings]
  (let [prod? (settings/prod? settings)
        db-specs (settings/get settings [:db :specs])
        ds-specs (settings/get settings [:db :ds-specs])]
    (component/start (db.sql/database (not prod?) db-specs ds-specs))))

(defn- read-input [info]
  (println info)
  (read-line))

(defn- command-superuser [db args]
  (println "Adding new superuser"
           "\n----------"
           "\n")
  (let [full-name (read-input "Full name?")
        spoken-name (read-input "Spoken name?")
        username (read-input "User name? (This is what you will log in with)")
        email (read-input "Email?")
        password (read-input "Password?")
        passes? (cond
                 (str/blank? email) "Blank email"
                 (str/blank? username) "Blank username"
                 (str/blank? password) "Blank password"
                 (not (zero? (count (->> (db/query db {:select [:*]
                                                       :from [:auth_user]
                                                       :where [:= :username username]}))))) "User already exists"
                                                       :else true)]
    (if (true? passes?)
      (do
        (-> (role/get-rolemanager)
            (assoc :database db)
            (component/start)
            (component/stop))
        (auth/add-user! {:username username
                         :password password
                         :email email
                         :full_name full-name
                         :spoken_name spoken-name} #{:admin} nil db)
        (println "Superuser" username "added"))
      (println "Could not add new superuser:" passes?))))

(defn- command-migrate [db args]
  (println "Migrating...")
  (->> db
       (migrator.sql/get-migrator)
       (migrator/migrate))
  (println "Migration done..."))


(defn- command-root-page [db args]
  (println "Adding root page"
           "\n----------")
  (if (->> (db/query db {:select [:%count.*]
                         :from [:reverie_page]})
           first :count zero?)
   (let [name (read-input "Name?")
         title (read-input "Title? (blank is ok)")]
     (if (str/blank? name)
       (do
         (println "Name can't be blank")
         (command-root-page db args))
       (do
         (db/query! db {:insert-into :reverie_page
                        :values [{:name name
                                  :title title
                                  :type "page"
                                  :app ""
                                  :slug "/"
                                  :route "/"
                                  :parent nil
                                  :serial 1
                                  :version 0
                                  (sql/raw "\"order\"") 1
                                  :template "main"}]})
         (println "Root page" name "added"))))
   (println "Pages already exists. Aborting...")))

(defn- command-init [db args]
  (command-migrate db args)
  (command-root-page db args)
  (command-superuser db args))

(defn run-command [[command & args]]
  (let [settings (get-settings "settings.edn")
        db (get-db settings)]
    (case command
      :init (command-init db args)
      :migrate (command-migrate db args)
      :root-page (command-root-page db args)
      :superuser (command-superuser db args)
      "No command found")

    (component/stop db)
    (component/stop settings)
    (System/exit 0)))
