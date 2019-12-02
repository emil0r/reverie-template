(ns {{name}}.templates.start
  (:require [{{name}}.templates.common :refer [base]]
            [reverie.core :refer [deftemplate area]]))


(defn template-start [request page properites params]
  (base {:request request
         :page page
         :content [:div.start
                   (area start)]}))

(deftemplate start template-start)


