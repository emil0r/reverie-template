(ns {{name}}.core
  (:gen-class)
  (:require [reverie.server :as server]
            [{{name}}.init :as init])
  (:use [{{name}}.command :only [run-command]]))

(defn- read-settings [args]
  (let [settings (into {}
                       (map (fn [[a b]] {a b})
                            (partition 2 (map read-string args))))]
    (merge
     ;; defaults
     {:port 8080}
     (reduce (fn [settings k]
               (cond
                (= k :settings) (merge
                                 (-> :settings settings str slurp read-string)
                                 settings)
                :else settings))
             settings
             (keys settings)))))


(defn -main [& args]
  (if (= :command (read-string (first args)))
    (run-command (map read-string (rest args)))
    (let [settings (read-settings args)]
      (init/init settings)
      (server/start settings))))
