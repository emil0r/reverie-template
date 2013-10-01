(ns {{name}}.objects.migrations
  (:refer-clojure :exclude [alter defonce drop bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]] core schema)))


(defmigration init-object-text
  (up [] (create (table :text
                        (integer :id :primary-key :auto-inc :not-null)
                        (text :text :not-null (default ""))
                        (integer :object_id [:refer :object :id] :not-null))))
  (down [] (drop (table :text))))



(defmigration init-object-raw
  (up [] (create (table :raw
                        (integer :id :primary-key :auto-inc :not-null)
                        (text :text :not-null (default ""))
                        (integer :object_id [:refer :object :id] :not-null))))
  (down [] (drop (table :raw))))

