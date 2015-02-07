(ns cluster-mania.secret
  (:require [environ.core :as env]))

(def default-secret-path "~/.cluster_mania_secret")

(defn secret []
  (slurp (env/env :cluster-mania-secret-path default-secret-path)))
