(ns cluster-mania.message
  (:require [cognitect.transit :as transit]
            [plumbing.core :refer [keywordize-map]]))

(def reader (partial transit/reader :msgpack))

(defn- read-transit [bytes]
  (-> bytes
      reader
      transit/read
      keywordize-map))

(defn parse [handler]
  (fn [req]
    (handler (assoc req :message (-> req :raw identity)))))
