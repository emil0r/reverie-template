(ns leiningen.new.reverie
  (:require [clojure.string :as str]
            [leiningen.new.templates :refer [renderer name-to-path ->files year]]
            [leiningen.core.main :as main]))

(def render (renderer "reverie"))

(defn- read-input [info]
  (println info)
  (read-line))

(defn- read-db-type []
  (let [db-types ["postgres"]
        db-type (read-input (str "Which RDBMS do you use? " db-types))]
    (if (some #(= db-type %) db-types)
      db-type
      (do
        (println "Invalid database. Please try again")
        (read-db-type)))))

(defn- read-db-host [db-type]
  (let [db-host (read-input "Host? (Leave blank for default)")]
    (if (str/blank? db-host)
      (if (not (some #(= db-type %) ["postgres"]))
        (println (str "I have no clue what the default is for " db-type ", contributions welcome. Picking blank host."))
        (case db-type
          "postgres" "localhost"
          ""))
      db-host)))

(defn- read-db-port [db-type]
  (let [db-host (read-input "Port? (Leave blank for default)")]
    (if (str/blank? db-host)
      (if (not (some #(= db-type %) ["postgres"]))
        (println (str "I have no clue what the default is for " db-type ", contributions welcome. Picking blank port."))
        (case db-type
          "postgres" "5432"
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
                    "")
        db-subprotocol (case db-type
                         "postgres" "postgresql"
                         "")
        db-dependancy (case db-type
                        "postgres" "org.postgresql/postgresql \"9.2-1002-jdbc4\""
                        (str db-type "\"version\""))]
    {:db-type db-type
     :db db
     :db-host db-host
     :db-port db-port
     :db-user db-user
     :db-password db-password
     :db-driver db-driver
     :db-subprotocol db-subprotocol
     :db-dependancy db-dependancy
     :db-subname (case db-type
                   "postgres" (str "//" db-host ":" db-port "/" db)
                   "")}))

(defn reverie
  [name]
  (let [data (merge {:name name
                     :sanitized (name-to-path name)
                     :year (year)}
                    (read-database))]
    (main/info "Generating fresh 'lein new' reverie project.")
    (->files data
             ;; root
             [".gitignore" (render "gitignore")]
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             ["LICENSE" (render "LICENSE")]
             ["settings.edn" (render "settings.edn" data)]
             ;; basics
             ["src/{{sanitized}}/core.clj" (render "src/example/core.clj" data)]
             ["src/{{sanitized}}/init.clj" (render "src/example/init.clj" data)]
             ["src/{{sanitized}}/dev.clj" (render "src/example/dev.clj" data)]
             ["src/{{sanitized}}/command.clj" (render "src/example/command.clj" data)]
             ;; templates
             ["src/{{sanitized}}/templates/main.clj" (render "src/example/templates/main.clj" data)]
             ["src/{{sanitized}}/templates/common.clj" (render "src/example/templates/common.clj" data)]
             ;; object text
             ["src/{{sanitized}}/objects/text.clj" (render "src/example/objects/text.clj" data)]
             ["src/{{sanitized}}/objects/migrations/text/0001-text.up.sql" (render "src/example/objects/migrations/text/0001-text.up.sql" data)]
             ["src/{{sanitized}}/objects/migrations/text/0001-text.down.sql" (render "src/example/objects/migrations/text/0001-text.down.sql" data)]
             ;; object raw
             ["src/{{sanitized}}/objects/raw.clj" (render "src/example/objects/raw.clj" data)]
             ["src/{{sanitized}}/objects/migrations/raw/0001-raw.up.sql" (render "src/example/objects/migrations/raw/0001-raw.up.sql" data)]
             ["src/{{sanitized}}/objects/migrations/raw/0001-raw.down.sql" (render "src/example/objects/migrations/raw/0001-raw.down.sql" data)]
             ;; endpoints
             ["src/{{sanitized}}/endpoints/api.clj" (render "src/example/endpoints/api.clj" data)]
             ;; apps
             ["src/{{sanitized}}/apps/myapp.clj" (render "src/example/apps/myapp.clj" data)]
             )))
