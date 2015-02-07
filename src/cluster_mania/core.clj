(ns cluster-mania.core
  (:require [cluster-mania.config :as config]))

(defn start [state]
  (let [config (config/config)
        nodes (config/nodes config)
        state (atom state)]
    {:state state :nodes nodes}))
