(ns {{name}}.objects.text
  (:use [reverie.core :only [defobject]]))

(defobject text {:attributes {:text {:initial ""
                                     :input :richtext
                                     :name "Text"}}}
  [:any
   text])
