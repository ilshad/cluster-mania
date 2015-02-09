(ns cluster-mania.nodes
  (:require [cluster-mania.client :as client]))

(defn- nodes-state [conf]
  (atom
    (into {}
      (map
        (fn [m]
          [(:host m) {:port m :healthy? false}])
        (:nodes conf)))))

(defn- healthy? [host port]
  (= (client/send host port "1") "1"))

(defn- health-check [host nodes]
  (let [port (get-in @nodes [host :port])
        result (healthy? host port)]
    (swap! nodes #(assoc-in % [host :healthy?] result))))

(defn start [conf]
  (let [nodes (nodes-state conf)
        msec (:health-check-interval conf)
        run? (atom true)]
    (future
      (while @run?
        (doseq [{host :host} (:nodes conf)]
          (health-check host nodes))
        (Thread/sleep msec)))
    run?))

(defn stop [run?]
  (reset! run? false))
