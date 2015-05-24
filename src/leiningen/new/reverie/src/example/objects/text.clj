(ns {{name}}.objects.text
  (:require [clojure.core.match :refer [match]]
            [reverie.core :refer [defobject]]))


(defn- text [request object {:keys [text mode css]} params]
  (let [edit? (get-in request [:reverie :edit?])
        class (case css
                "always" ""
                "desktop" "hidden-xs hidden-sm"
                "mobile" "hidden-lg hidden-md")]
    (list
     (when edit?
       [:div "image (mode " mode ", show when: " css ")"])
     [:div {:class class}
      text])))

(defobject text
  {:table "objects_text"
   :migration {:path "src/{{name}}/objects/migrations/text"
               :automatic? true}
   :fields {:text {:name "Text"
                   :type :richtext
                   :initial ""}
            :css {:name "Show when?"
                  :type :dropdown
                  :options [["Always" "always"]
                            ["Desktop" "desktop"]
                            ["Mobile" "mobile"]]
                  :initial "always"}}
   :sections [{:fields [:text]}
              {:name "Meta" :fields [:css]}]}
  {:any text})
