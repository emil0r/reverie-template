(ns {{name}}.dev
  (:require [{{name}}.init :as init]
            [reverie.atoms :only [read-routes!]]
            [reverie.server :as server]))


(server/init)
(init/init)
(read-rotes!)

(def app (server/server-handler {}))
