(ns {{name}}.core
  (:gen-class)
  (:require [reverie.server :as server]
            [{{name}}.init :as init]))


(defn -main [& args]
  (let [m (merge {:port 8080}
                 (into {}
                       (map (fn [[a b]] {a b})
                            (partition 2 (map read-string args)))))]
    (init/init)
    (server/start m)))
