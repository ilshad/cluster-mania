(ns cluster-mania.core-test
  (:require [clojure.test :refer :all]
            [cluster-mania.core :refer :all]))

(deftest test-1
  (testing "fixme"
    (let [cluster (start)]
      (is (= cluster {})))))
