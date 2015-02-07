(ns cluster-mania.core
  (:require [cluster-mania.config :as c]
            [cluster-mania.security :as s]
            [cluster-mania.server :as server]))

(def parse-message identity)

(defn swap-fn [target]
  (case (:type target)
    :map (fn [m {:keys [ks value]}] (assoc-in m ks value))))

(defn start [state]
  (let [state (atom state)
        conf (c/config)
        nodes (c/nodes conf)
        secret (s/secret)]
    (server/start (:server-port conf)
      (fn [req]
        (let [msg (parse-message req)]
          (when (s/authorized? msg secret)
            (when-let [target (state (:key msg))]
              (swap! (:atom target) (swap-fn target) msg))))))))

(defn stop [s]
  (server/stop s))
