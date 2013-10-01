(ns {{name}}.command
  (:require [clojure.string :as s]
            [korma.core :as k]
            [reverie.auth.user :as user]))


(defn- read-input [info]
  (println info)
  (read-line))

(defn- add-superuser [first-name last-name name password email]
  (user/add! {:first-name first-name :last-name last-name :name name
              :password password :email email :is-staff true :is-admin true}))

(defn- command-superuser []
  (println "Adding new superuser"
           "----------"
           "\n\n")
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
      (user/add! first-name last-name name password email)
      (println "Could not add new superuser:" passes?))))

(defn run-command [command & args]
  (case command
    :superuser (command-superuser)))
