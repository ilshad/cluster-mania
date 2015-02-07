(ns cluster-mania.core
  (:require [clojure.edn :as edn]
            [clojure.string :as string]
            [environ.core :as env]))

(defn- parse-port [s]
  (when-not (empty? s)
    (try
      (Long/parseLong s)
      (catch NumberFormatException _
        (throw (java.lang.IllegalArgumentException. "Error in nodes configuration. Port must be integer."))))))

(def defaults
  {:default-port 6174
   :server-port 6174
   :nodes []})

(defn- read-config []
  (-> :cluster-mania-config env/env slurp edn/read-string))

(defn- env-nodes [config]
  (into {}
    (for [s (:nodes config)]
      (let [[host port] (string/split s #":")]
        [host (or (parse-port port) (:default-port config))]))))

(defn start [& [state]]
  (let [config (merge defaults (read-config))
        nodes (env-nodes config)
        state (atom (or state {}))]
    {:state state}))

(defn add [m name ref]
  (swap! (:state m) assoc name ref))
