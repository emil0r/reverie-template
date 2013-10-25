(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :ring {:handler {{name}}.dev/app}
  :main {{name}}.core
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [reverie "0.5.0-beta2"]
                 [{{& db-dependancy}}]])
