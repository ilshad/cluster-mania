(ns cluster-mania.message)

(defn parse [handler]
  (fn [req]
    (handler (assoc req :message (:raw req)))))
