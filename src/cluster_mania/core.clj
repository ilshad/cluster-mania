(ns cluster-mania.core
  (:require [cluster-mania.config :as config]
            [cluster-mania.server :as server]
            [cluster-mania.nodes :as nodes]
            [cluster-mania.security :refer [authorize]]
            [cluster-mania.message :refer [parse]]
            [cluster-mania.state :refer [route]]
            [cluster-mania.log :refer [log]]))

(defn start [states]
  (let [conf (config/config)
        handler (-> (route states) authorize parse log)
        server (server/start conf handler)
        nodes (nodes/start conf)]
    [server nodes]))

(defn stop [[server nodes]]
  (server/stop server)
  (nodes/stop nodes))
