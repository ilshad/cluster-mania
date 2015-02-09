(ns cluster-mania.core
  (:require [cluster-mania.config :as c]
            [cluster-mania.security :as security]
            [cluster-mania.server :as server]
            [cluster-mania.message :as message]
            [cluster-mania.state :as state]
            [cluster-mania.log :as log]))

(defn start [spec]
  (let [states (atom spec)
        conf (c/config)
        nodes (c/nodes conf)
        secret (security/secret)]
    (server/start (:server-port conf)
      (-> state/handler
          (state/dispatch states)
          (security/authorize secret)
          message/parse
          log/log))))

(defn stop [s]
  (server/stop s))
