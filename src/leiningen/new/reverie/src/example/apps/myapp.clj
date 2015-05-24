(ns {{name}}.apps.myapp
  (:require [reverie.core :refer [defapp]]))



(defn level2 [request page properties {:keys [caught]}]
  {:a (str "I caught myself " caught)})

(defn level1 [request page properties params]
  {:a "I don't do much..."})


(defapp myapp {}
  [["/" {:any level1}]
   ["/:caught" {:any level2}]])
