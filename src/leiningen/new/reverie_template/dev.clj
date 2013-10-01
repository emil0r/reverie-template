(ns {{name}}.dev
  (:require [{{name}}.init :as init]
            [reverie.server :as server]))


(server/init)
(init/init)

(def app (server/server-handler {}))
