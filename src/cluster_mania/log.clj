(ns cluster-mania.log)

(defn log [handler]
  (fn [req]
    (println "Request:" req)
    (let [resp (handler req)]
      (println "Response:" resp)
      resp)))
