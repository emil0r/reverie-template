(ns {{name}}.init
  (:require [reverie.server :as server]))

(def opts {:reverie.settings/path "settings.edn"
           :reverie.system/load-namespaces ['{{name}}.templates
                                            '{{name}}.objects
                                            '{{name}}.apps
                                            '{{name}}.endpoints]})

(defn start-server [opts]
  (server/start opts []))
