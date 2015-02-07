(ns cluster-mania.core
  (:require [cluster-mania.config :as config]
            [cluster-mania.secret :as secret]))

(defn start [state]
  (let [config (config/config)
        nodes (config/nodes config)
        state (atom state)
        secret (secret/secret)]
    {:state state :nodes nodes}))
 
