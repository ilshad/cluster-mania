(ns cluster-mania.log)

(defn log [handler]
  (fn [request]
    (println "Request:" request)
    (let [response (handler request)]
      (println "Response:" response)
      response)))
