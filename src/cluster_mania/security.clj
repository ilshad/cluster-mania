(ns cluster-mania.security
  (:require [environ.core :as env]))

(def default-secret-path "~/.cluster_mania_secret")

(defn secret []
  (slurp (env/env :cluster-mania-secret-path default-secret-path)))

(defn authorized? [message secret]
  (= (:secret message) secret))
