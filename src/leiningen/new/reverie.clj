(ns leiningen.new.reverie
  (:require [leiningen.new.templates :refer [->files
                                             name-to-path
                                             project-name
                                             renderer
                                             year]]
            [leiningen.core.main :as main]))

(def render (renderer "reverie"))

(defn reverie
  [name & options]
  (let [options (set options)
        data (merge {:name name
                     :sanitized (name-to-path name)
                     :year (year)
                     :db-name "postgres"
                     :db-username "postgres"
                     :db-password "postgres"}
                    (if (options "+batteries")
                      {:deps-batteries "[reverie-batteries \"0.8.1\"]\n"}))]
    (main/info "Generating fresh 'lein new' reverie project.")
    (apply ->files data
           (remove
            nil?
            [ ;; options
             (if (options "+docker-compose")
               ["docker-compose.yml" (render "docker/docker-compose.yml" data)])
            
             [".gitignore" (render "core/gitignore" data)]
             ["project.clj" (render "core/project.clj" data)]
             ["README.adoc" (render "core/README.adoc" data)]
             ["settings.edn" (render "core/settings.edn" data)]
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
             "resources/public/static"]))))
