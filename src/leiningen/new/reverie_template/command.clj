(ns {{name}}.command
  (:require [clojure.string :as s]
            [korma.core :as k]
            [reverie.auth.user :as user]
            [reverie.page :as page]
            [reverie.migration :as migration])
  (:use [korma.db :only [defdb {{db-type}}]]))


(def settings (-> "settings.edn" slurp read-string))
(defdb {{name}}-db ({{db-type}} (:db settings)))

(defn- read-input [info]
  (println info)
  (read-line))

(defn- add-superuser [first-name last-name name password email]
  (user/add! {:first-name first-name :last-name last-name :name name
              :password password :email email :is-staff true :is-admin true}))

(defn- command-superuser []
  (println "Adding new superuser"
           "\n----------"
           "\n")
  (let [first-name (read-input "First name?")
        last-name (read-input "Last name?")
        email (read-input "Email?")
        name (read-input "User name?")
        password (read-input "Password?")
        passes? (cond
                 (s/blank? email) "Blank email"
                 (s/blank? name) "Blank username"
                 (s/blank? password) "Blank password"
                 (not (zero? (count (k/select :user (k/where {:name name}))))) "User already exists"
                 :else true)]
    (if (true? passes?)
      (do
        (add-superuser first-name last-name name password email)
        (println "Superuser" name "added"))
      (println "Could not add new superuser:" passes?))))

(defn- command-migrate []
  (println "Migrating...")
  (migration/open-global-when-necessary (:lobos-db settings))
  (migration/migrate)
  (println "Migration done..."))

(defn- command-root-page []
  (println "Adding root page"
           "\n----------"
           "\n")
  (if (zero? (count (k/select :page)))
   (let [name (read-input "Name?")
         title (read-input "Title? (blank is ok)")]
     (if (s/blank? name)
       (do
         (println "Name can't be blank")
         (command-root-page))
       (do
         (page/add! {:tx-data {:name name :title title :template "main" :type "normal"
                               :app "" :parent 0 :uri "/"}})
         (println "Root page" name "added"))))
   (println "Pages already exists. Aborting...")))

(defn- command-init []
  (command-migrate)
  (command-superuser)
  (command-root-page))

(defn run-command [[command & args]]
  (case command
    :superuser (command-superuser)
    :migrate (command-migrate)
    :init (command-init)
    :root-page (command-root-page)
    "No command found"))
