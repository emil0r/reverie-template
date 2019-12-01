(ns leiningen.new.reverie
  (:require [leiningen.new.templates :refer [->files
                                             name-to-path
                                             project-name
                                             renderer
                                             year]]
            [leiningen.core.main :as main]))

(def render (renderer "reverie"))

(defn reverie
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)
              :year (year)}]
    (main/info "Generating fresh 'lein new' reverie project.")
    (->files data
             [".gitignore" (render "core/gitignore" data)]
             ["project.clj" (render "core/project.clj" data)]
             ["README.adoc" (render "core/README.adoc" data)]
             ["src/{{sanitized}}/core.clj" (render "src/core.clj" data)]
             ["src/{{sanitized}}/init.clj" (render "src/init.clj" data)]
             ["src/{{sanitized}}/templates/start.clj" (render "src/templates/start.clj" data)]
             ["src/{{sanitized}}/templates/common.clj" (render "src/templates/common.clj" data)]
             ["dev/dev.clj" (render "dev/dev.clj" data)]

             ;; directories
             "src/{{sanitized}}/apps"
             "src/{{sanitized}}/endpoints"
             "src/{{sanitized}}/objects"
             "resources"
             "resources/public"
             "resources/public/static")))
