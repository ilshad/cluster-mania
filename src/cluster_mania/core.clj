(ns cluster-mania.core
  (:require [cluster-mania.config :as config]
            [cluster-mania.secret :as secret]
            [cluster-mania.server :as server]))

(defn parse-message [req]
  {:key :my-state :value 42})

(defn swap-fn [target]
  (case (:type target)
    :map (fn [m {:keys [ks value]}]
           (assoc-in m ks value))))

(defn start [state]
  (let [cfg (config/config)
        nodes (config/nodes cfg)
        state (atom state)
        secret (secret/secret)]
    (server/start (:server-port cfg)
      (fn [req]
        (let [msg (parse-message req)]
          (if-let [target (state (:key msg))]
            (swap! target (swap-fn target) (:value msg))))))))

(defn stop [s]
  (server/stop s))
