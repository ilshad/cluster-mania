(ns cluster-mania.core
  (:require [cluster-mania.config :as config]
            [cluster-mania.server :as server]
            [cluster-mania.security :refer [authorize]]
            [cluster-mania.message :refer [parse]]
            [cluster-mania.state :refer [route]]
            [cluster-mania.log :refer [log]]))

(defn start [states]
  (let [conf (config/config)
        nodes (config/nodes conf)]
    (server/start (:server-port conf)
      (-> (route states)
          authorize
          parse
          log))))

(defn stop [s]
  (server/stop s))
