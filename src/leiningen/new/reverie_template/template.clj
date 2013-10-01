(ns {{name}}.templates.main
    (:use [hiccup core page]
          [reverie.core :only [deftemplate area]]

          {{name}}.includes.head))

;; change as you see fit

(deftemplate main {}
  (html5
   [:head
    (http-meta)
    (title request)
    (css)]
   [:body
    [:div.container
     [:div.column1 (area :a)]
     [:div.column2 (area :b)]]
    (js)]))
