(ns {{name}}.endpoints.api
  (:require [reverie.core :refer [defpage]]))


(defn my-api [request page params]
  "Nothing to see here")

(defpage "/api"
  {:headers {"Content-Type" "text/plain; charset=utf-8;"}}
  [["/" {:get my-api}]])
