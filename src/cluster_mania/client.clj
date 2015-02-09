(ns cluster-mania.client
  (:require [clojure.java.io :as io])
  (:import [java.io StringWriter]
           [java.net Socket]))

(defn send [host port content]
  (with-open [sock (Socket. host port)
              writer (io/writer sock)
              reader (io/reader sock)
              response (StringWriter.)]
    (.append writer content)
    (.flush writer)
    (io/copy reader response)
    (str response)))
