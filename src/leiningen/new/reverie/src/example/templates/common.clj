(ns {{name}}.templates.common
  (:require [clojure.string :as str]
            [hiccup.page :refer [include-css include-js]]
            [reverie.core :refer [area]]
            [reverie.database :as rev.db]
            [reverie.page :as page]))


(defn head [page & [args]]
  (let [title (cond
               (nil? (page/parent page)) (str (->> [(page/title page)
                                                    (page/name page)]
                                                   (remove str/blank?)
                                                   first)
                                              " &mdash; Slogan Here")
               :else (str (->> [(page/title page) (page/name page)]
                               (remove str/blank?)
                               first)
                          " &mdash; {{Name}}"))]
    [:head
     [:meta {:charset "UTF-8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1, maximum-scale=1"}]
     ;;[:link {:rel "shortcut icon" :href "/static/images/my-icon-file.png"}]
     [:title title]
     (map include-css [;;"/static/css/my-css-file.css"
                       ])]))

(defn nav [page db]
  [:nav
   [:ul.main-menu
    (let [pages (rev.db/get-children db 1 true)]
      (map (fn [{:keys [title name] :as child}]
             [:li (if (.startsWith (page/path page) (page/path child))
                    {:class "active"})
              [:a {:href (page/path child)}
               (->> [title name]
                    (remove str/blank?)
                    first)]])
           (remove #(-> % :properties :menu :hide?) pages)))]])


(defn footer [page dev?]
  (list
   [:footer.footer-container
    "{{Name}}"]
   (when-not dev?
     (list ;; tracking code can go here
      )
     )
   (map include-js [;;"/static/js/my-js-file.js"
                    ])))
