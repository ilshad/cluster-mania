(ns cluster-mania.core-test
  (:require [clojure.test :refer :all]
            [cluster-mania.core :refer :all]))

(deftest test-1
  (testing "fixme"
    (let [cluster (start)]
      (is (= cluster {})))))

{:key :sessions :ks [:users :frodo] :value 42 :secret "c9a3dade-aec6-11e4-b809-7cd1c3f174bb"}
