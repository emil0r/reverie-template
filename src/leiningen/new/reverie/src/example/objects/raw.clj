(ns {{name}}.objects.raw
  (:require [reverie.core :refer [defobject]]))


(defn raw [request object {:keys [text] :as properties} params]
  text)

(defobject raw
  {:table "objects_raw"
   :migration {:path "src/{{name}}/objects/migrations/raw/"
               :automatic? true}
   :fields {:text {:name "Text"
                   :type :textarea
                   :initial ""}}
   :sections [{:fields [:text]}]}
  {:any raw})
