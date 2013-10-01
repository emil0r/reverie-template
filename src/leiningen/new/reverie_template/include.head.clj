(ns {{name}}.includes.head
  (:require [clojure.string :as s]
            [reverie.page :as page])
  (:use [hiccup.page :only [include-css include-js]]))

(defn title [request]
  (let [p (page/get {:page-id (get-in request [:reverie :page-id])})]
    [:title (first (remove s/blank? [(:title p) (:name p)])) " &mdash; " {{name}}]))

(defn css []
  (map include-css ["/css/main.css"
                    "/admin/css/editing.css"]))

(defn js []
  (map include-js [""]))


(defn http-meta []
  (list
   [:meta {:charset "utf-8"}]
   [:meta {:name :viewport :content "width=device-width, initial-scale=1.0"}]))

