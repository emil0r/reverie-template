(ns {{name}}.core
    (:require [clojure.edn :as edn]
              [{{name}}.command :as command]
              [{{name}}.init :as init])
    (:gen-class))


(defn -main [& args]
  (if (= :command (-> args first edn/read-string))
    (command/run-command (map edn/read-string (rest args)))
    (init/init "settings.edn")))
