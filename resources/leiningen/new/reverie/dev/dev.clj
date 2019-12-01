(ns dev
  (:require [{{name}}.init :refer [start-server opts]]
            [reverie.dev.migration :as migration]
            [reverie.dev.app :as app]
            [reverie.dev.module :as module]
            [reverie.dev.object :as object]
            [reverie.dev.page :as page]
            [reverie.server :as server]))

(comment

  ;; run this for running the server in dev mode through the REPL
  (do
    (server/stop)
    (start-server opts))


  ;; copy/paste and change the commented out migration to suit your needs
  ;; run in the REPL as necessary during development

  (object/create {:override? true} 'myobject)
  (migration/migrate :object 'myobject)
  (migration/rollback :object 'myobject)
  (object/add-migration 'myobject "foobar")
  (object/remove-migration 'myobject "foobar")
  
  )

