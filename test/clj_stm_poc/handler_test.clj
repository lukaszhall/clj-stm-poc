(ns clj-stm-poc.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clj-stm-poc.handler :as handler]
            [cheshire.core :as chesire]
            [taoensso.timbre :as timbre]
            [clj-stm-poc.sql-wrapper :as sql-wrapper]))


(deftest handler-read-test

  (testing "connection count read"
    (let [response (handler/app (mock/request :get "/api/con-count"))
          status   (:status response)]

      (is (= status 200))
      (is (clojure.string/includes? (get-in response [:headers "Content-Type"])
                                    "application/json"))))

  (testing "sim-sql read"
    (let [sleep-dur 2
          response  (handler/app (mock/request :post (str "/api/sim-sql?sleep-secs="
                                                          sleep-dur)))
          status    (:status response)]
      (is (= status 200)))))



(deftest simultaneous-sql-test

  (let [expected-cons    sql-wrapper/max-cons
        sleep-dur        2]

    (testing "sim-sql multi-access"
      (let [successes (atom 0)
            attempts  10
            latch     (java.util.concurrent.CountDownLatch. attempts)
            post-str  (str "/api/sim-sql?sleep-secs=" sleep-dur)
            request   (mock/request :post post-str)]

        (doseq [i (range 0 attempts)]
          (.start (Thread. (fn []
                             (let [response (handler/app request)
                                   success  (= (:status response) 200)]
                               (if success (swap! successes inc))
                               (.countDown latch))))))
        (.await latch)

        (is (= @successes expected-cons))))))
