(ns cluster-mania.state)

(defn- update-fn [state]
  (case (:type state)
    :map (fn [state message]
           (assoc-in state (:path message) (:value message)))))

(defn- handler [req]
  (let [state (:state req)]
    (swap! (:atom state) (update-fn state) (:message req))
    {:message "OK"}))

(defn route [states]
  (fn [req]
    (if-let [state (-> req :message :target states)]
      (handler (assoc req :state state))
      {:message "Target Not Found"})))
