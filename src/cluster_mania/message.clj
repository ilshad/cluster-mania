(ns cluster-mania.message)

(defn parse [handler]
  (fn [request]
    (handler (assoc request :message (:raw request)))))
