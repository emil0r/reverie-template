(ns {{name}}.core
  (:gen-class)
  (:require [reverie.server :as server]
            [{{name}}.init :as init])
  (:use [{{name}}.command :only [run-command]]))


(defn -main [& args]
  (if (= :command (read-string (first args)))
    (run-command (map read-string (rest args)))
    (let [settings (merge {:port 8080}
                          (into {}
                                (map (fn [[a b]] {a b})
                                     (partition 2 (map read-string args)))))]
      (init/init)
      (server/start settings))))
