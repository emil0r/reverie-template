(ns {{name}}.dev
  (:require [{{name}}.init :as init]
            [reverie.server :as server])
  (:use [reverie.atoms :only [read-routes!]]))


(init/init (-> "settings.edn" slurp read-string))
(server/init)
(read-routes!)

(def app (server/server-handler {}))
