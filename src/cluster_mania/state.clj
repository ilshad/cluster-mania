(ns cluster-mania.state)

(defn- update-fn [state]
  (case (:type state)
    :map (fn [state message]
           (assoc-in state (:path message) (:value message)))))

(defn handler [request]
  (let [state (:state request)]
    (swap! (:atom state) (update-fn state) (:message request))
    {:message "OK"}))

(defn dispatch [handler states]
  (fn [request]
    (if-let [state (-> request :message :target states)]
      (handler (assoc request :state state))
      {:message "Target Not Found"})))
