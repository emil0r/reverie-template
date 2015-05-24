(ns {{name}}.templates.main
  (:require [hiccup.page :refer [html5]]
            [{{name}}.templates.common :as common]
            [reverie.core :refer [deftemplate area]]))


(defn template [request page properties params]
  (let [{:keys [edit? database dev?]} (get-in request [:reverie])]
   (html5
    (common/head page)
    [:body
     [:div#container
      (common/nav page database)
      [:div.container
       (area a)]
      (common/footer page dev?)]])))


(deftemplate main template)
