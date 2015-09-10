(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [reverie-core "0.7.0-SNAPSHOT"]
                 [reverie-sql "0.7.0-SNAPSHOT"]
                 [http-kit "2.1.19"]
                 [{{& db-dependancy}}]]
  :main {{name}}.core
  :min-lein-version "2.0.0"
  :uberjar-name "{{name}}.jar")
