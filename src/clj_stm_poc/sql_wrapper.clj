(ns clj-stm-poc.sql-wrapper
  (:require [taoensso.timbre :as timbre
             :refer [log trace debug info warn error fatal spy]]))

; Max Connection count
(def ^:const max-cons 4)
(def con-cnt (atom max-cons))

(defn- consume-con
  "Impure fn. Consume an available connection or fail-fast if unavailble"
  []
  (let [success (atom true)
        _       (swap! con-cnt
                       (fn [_] (if (pos-int? @con-cnt)
                                 (do (reset! success true)  (dec @con-cnt))
                                 (do (reset! success false) @con-cnt))))]
    (if @success @success (throw (Exception. "Connection limit reached")))))

(defn- release-con
  "Impure fn. Release connection lock"
  []
  (swap! con-cnt inc))


(defn- blocking-call
  "Simulate blocking call"
  [sleep-duration]
  (let [_  (Thread/sleep sleep-duration)]
    "Execution Success"))


(defn count-cons
  "Connection counter" [] @con-cnt)


(defn sim-sql
  "Impure fn. Simulate SQL call with limited connection pool running for
  'duration-sec seconds."
  [duration-sec]
  (let [dur-ms (* 1000 duration-sec)
        _      (consume-con)
        result (blocking-call dur-ms)
        _      (release-con)]
    result))
