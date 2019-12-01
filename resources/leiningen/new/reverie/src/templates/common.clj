(ns {{name}}.templates.common
  (:require [clojure.string :as str]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [html5]]
            [reverie.page :as page]
            [reverie.settings :as settings]))

(defn head [{:keys [page] :as context}]
  (let [title (->> [(page/title page)
                    (page/name page)]
                   (remove str/blank?))]
   (list
    [:meta {:charset "utf-8"}]
    [:title title])))

(defn scripts [{:keys [dev?]}]
  (let [js-scripts (if dev?
                     []
                     [])]
    (map (fn [src]
           [:script {:src src} :type "text/javascript"])
         js-scripts)))

(defn menu [contenxt]
  [:div
   "my menu here"])

(defn footer [context]
  [:div "my footer here"])


(defn base [{:keys [request content] :as context}]
  (let [{:keys [settings]} (get request :reverie)
        context (assoc context
                       :dev? (settings/dev? settings))
        ;; render the content up here to force any lazyness to be
        ;; executed. this is in order for downstream to work properly
        ;; if you need to use it
        rendered-content (html content)]
    (html5
     [:head
      (head context)
      (scripts context)]
     [:body
      (menu context)
      [:div.container
       [:div.content
        rendered-content]]
      (footer context)])))


