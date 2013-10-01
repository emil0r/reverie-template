(ns leiningen.new.reverie-template
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files year]]
            [leiningen.core.main :as main])
  (:use [clojure.string :only [blank?]]))

(def render (renderer "reverie-template"))

(defn- read-input [info]
  (println info)
  (read-line))

(defn- read-db-type []
  (let [db-types ["postgres" "mssql" "msaccess" "oracle" "mysql" "sqlite3" "h2"]
        db-type (read-input (str "Which RDBMS do you use? " db-types))]
    (if (some #(= db-type %) db-types)
      db-type
      (do
        (println "Invalid database. Please try again")
        (read-db-type)))))

(defn- read-db-host [db-type]
  (let [db-host (read-input "Host? (Leave blank for default)")]
    (if (blank? db-host)
      (if (not (some #(= db-type %) ["postgres" "mysql"]))
        (println (str "I have no clue what the default is for " db-type ", contributions welcome. Picking blank host."))
        (case db-type
          "postgres" "localhost"
          "mysql" "localhost"
          "oracle" "localhost"
          ""))
      db-host)))

(defn- read-db-port [db-type]
  (let [db-host (read-input "Port? (Leave blank for default)")]
    (if (blank? db-host)
      (if (not (some #(= db-type %) ["postgres" "mysql"]))
        (println (str "I have no clue what the default is for " db-type ", contributions welcome. Picking blank port."))
        (case db-type
          "postgres" "5432"
          "mysql" "3306"
          "oracle" "1521"
          ""))
      db-host)))

(defn- read-database []
  (println "Database info\n----------\n")
  (let [db-type (read-db-type)
        db (read-input "Name of the database?")
        db-host (read-db-host db-type)
        db-port (read-db-port db-type)
        db-user (read-input "User?")
        db-password (read-input "Password?")
        db-driver (case db-type
                    "postgres" "org.postgresql.Driver"
                    "mysql" "com.mysql.jdbc.Driver"
                    "oracle" "oracle.jdbc.OracleDriver"
                    "")
        db-subprotocol (case db-type
                         "postgres" "postgresql"
                         "mysql" "mysql"
                         "oracle" "oracle"
                         "")]
    {:db-type db-type
     :db db
     :db-host db-host
     :db-port db-port
     :db-user db-user
     :db-password db-password
     :db-driver db-driver
     :db-subprotocol db-subprotocol
     :db-subname (case db-type
                   "postgres" (str "//" db-host ":" db-port "/" db)
                   "mysql" (str "//" db-host ":" db-port "/" db)
                   "oracle" (str "thin:@" db-host ":" db-port "/" db ":SID")
                   "")}))

(defn reverie-template
  [name]
  (let [data (merge {:name name
                     :sanitized (name-to-path name)
                     :year (year)}
                    (read-database))]
    (main/info "Generating fresh 'lein new' reverie project.")
    (->files data
             [".gitignore" (render "gitignore")]
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             ["LICENSE" (render "LICENSE")]
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["src/{{sanitized}}/init.clj" (render "init.clj" data)]
             ["src/{{sanitized}}/dev.clj" (render "dev.clj" data)]
             ["src/{{sanitized}}/command.clj" (render "command.clj" data)]
             ["src/{{sanitized}}/templates/main.clj" (render "template.clj" data)]
             ["src/{{sanitized}}/includes/head.clj" (render "include.head.clj" data)]
             ["src/{{sanitized}}/objects/head.clj" (render "object.text.clj" data)]
             ["src/{{sanitized}}/objects/raw.clj" (render "object.raw.clj" data)]
             ["src/{{sanitized}}/objects/migrations.clj" (render "object.migrations.clj" data)])))
