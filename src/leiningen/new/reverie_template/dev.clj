(ns {{name}}.dev
  (:require [{{name}}.init :as init]
            [reverie.server :as server])
  (:use [reverie.atoms :only [read-routes!]]))


(server/init)
(init/init)
(read-routes!)

(def app (server/server-handler {}))
