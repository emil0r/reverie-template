(ns {{name}}.objects.raw
  (:use [reverie.core :only [defobject]]))

(defobject raw {:attributes {:text {:initial ""
                                    :input :textarea
                                    :name "Raw text"}}}
  [:any
   text])
