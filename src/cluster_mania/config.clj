(ns cluster-mania.config
  (:require [clojure.edn :as edn]
            [clojure.string :as string]
            [environ.core :as env]))

(def default-config-path "~/.cluster_mania_config")

(defn- read-config []
  (edn/read-string
    (slurp
      (env/env :cluster-mania-config-path default-config-path))))

(defn- parse-port [s]
  (when-not (empty? s)
    (try
      (Long/parseLong s)
      (catch NumberFormatException _
        (throw
          (java.lang.IllegalArgumentException. "Port must be integer."))))))

(defn- parse-nodes [conf]
  (vec
    (for [s (:nodes conf)]
      (let [[host port] (string/split s #":")]
        {:host host
         :port (or (parse-port port) (:default-port conf))}))))

(def defaults
  {:default-port 6174
   :server-port 6174
   :health-check-interval 60000
   :nodes []})

(defn config []
  (let [conf (merge defaults (read-config))]
    (assoc conf :nodes (parse-nodes conf))))
