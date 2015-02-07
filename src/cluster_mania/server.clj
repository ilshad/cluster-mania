(ns cluster-mania.server
  (:require [clojure.java.io :as io])
  (:import [java.net ServerSocket]))

(defn- text-receiver [socket]
  (.readLine (io/reader socket)))

(defn- text-sender [socket message]
  (doto (io/writer socket)
    (.write message)
    (.flush)))

(defn- run [& {:keys [port handler receiver sender]}]
  (let [run? (atom true)]
    (future
      (with-open [socket (ServerSocket. port)]
        (while @run?
          (with-open [s (.accept socket)]
            (->> (receiver s)
                 (handler)
                 (sender s))))))
    run?))

(defn start [port handler]
  (run :port port
       :handler handler
       :receiver text-receiver
       :sender text-sender))

(defn stop [run?]
  (reset! run? false))
