(ns cluster-mania.config
  (:require [clojure.edn :as edn]
            [clojure.string :as string]
            [environ.core :as env]))

(def default-config-path "~/.cluster_mania_config.edn")

(defn config-path []
  (or (env/env :cluster-mania-config-path) default-config-path))

(defn- read-config []
  (edn/read-string (slurp (config-path))))

(def defaults
  {:default-port 6174
   :server-port 6174
   :nodes []})

(defn config []
  (merge defaults (read-config)))

(defn- parse-port [s]
  (when-not (empty? s)
    (try
      (Long/parseLong s)
      (catch NumberFormatException _
        (throw
          (java.lang.IllegalArgumentException. "Port must be integer."))))))

(defn nodes [config]
  (into {}
    (for [s (:nodes config)]
      (let [[host port] (string/split s #":")]
        [host (or (parse-port port) (:default-port config))]))))
