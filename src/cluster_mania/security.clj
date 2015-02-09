(ns cluster-mania.security
  (:require [environ.core :as env]))

(def default-secret-path "~/.cluster_mania_secret")

(defn- secret []
  (slurp (env/env :cluster-mania-secret-path default-secret-path)))

(defn authorize [handler]
  (let [s (secret)]
    (fn [req]
      (if (= (:secret req) s)
        (handler req)
        {:message "Unauthorized Request"}))))
