(ns cluster-mania.message
  (:require [cognitect.transit :as transit]))

(def reader (partial transit/reader :msgpack))

(defn- read-transit [bytes]
  (-> bytes reader transit/read))

(defn parse [handler]
  (fn [req]
    (handler (assoc req :message (-> req :raw identity)))))
