(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.10.1"]

                 ;; reverie
                 [reverie-core "0.9.0-alpha7"]
                 [reverie-sql "0.9.0-alpha7"]

                 ;; web server
                 [http-kit "2.3.0"]

                 ;; batteries
                 [ez-form "0.7.3"]
                 {{& deps-batteries}}

                 ;; database
                 [org.postgresql/postgresql "42.2.8"]
                 [hikari-cp "2.9.0"]
                 [ez-database "0.7.0-alpha2"]]

  :main {{name}}.core

  :min-lein-version "2.0.0"

  :uberjar-name "{{name}}.jar"

  :profiles {:uberjar {:aot [{{name}}.core]}
             :dev {:dependencies [[reverie-dev "0.3.0"]]}})

