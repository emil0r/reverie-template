(ns {{name}}.core
    (:gen-class)
    (:require [{{name}}.init :refer [start-server opts]]
              [reverie.command :as command]))

(defn -main [& args]
  ;; run commands first. if a command is sent in the system exits after the command has run
  (command/run-command opts args)
  ;; if we manage to continue, we start the server 
  (start-server))
