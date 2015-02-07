(ns cluster-mania.core
  (:require [cluster-mania.config :as config]
            [cluster-mania.server :as server]
            [cluster-mania.security :as security]))

(defn parse-message [req]
  {:key :my-state
   :value {:ks [:a :b] :value 42}
   :secret "c9a3dade-aec6-11e4-b809-7cd1c3f174bb"})

(defn swap-fn [target]
  (case (:type target)
    :map (fn [m {:keys [ks value]}]
           (assoc-in m ks value))))

(defn start [state]
  (let [conf (config/config)
        nodes (config/nodes conf)
        state (atom state)
        secret (security/secret)]
    (server/start (:server-port conf)
      (fn [req]
        (let [msg (parse-message req)]
          (when (security/authorized? msg secret)
            (when-let [target (state (:key msg))]
              (swap! target (swap-fn target) (:value msg)))))))))

(defn stop [s]
  (server/stop s))
