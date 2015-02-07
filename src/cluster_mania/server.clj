(ns cluster-mania.server
  (:require [clojure.java.io :as io])
  (:import [java.net ServerSocket]))

(defn start [& {:keys [port receiver sender handler]}]
  (let [run? (atom true)]
    (future
      (with-open [socket (ServerSocket. port)]
        (while @run?
          (with-open [s (.accept socket)]
            (->> (receiver s)
                 (handler)
                 (sender s))))))
    run?))

(defn stop [run?]
  (reset! run? false))

(defn text-receiver [socket]
  (.readLine (io/reader socket)))

(defn text-sender [socket message]
  (doto (io/writer socket)
    (.write message)
    (.flush)))
