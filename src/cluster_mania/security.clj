(ns cluster-mania.security
  (:require [environ.core :as env]))

(def default-secret-path "~/.cluster_mania_secret")

(defn secret []
  (slurp (env/env :cluster-mania-secret-path default-secret-path)))

(defn authorize [handler secret]
  (fn [req]
    (if (= (:secret req) secret)
      (handler req)
      {:message "Unauthorized Request"})))
