(ns clj-stm-poc.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [taoensso.timbre :as timbre
             :refer [log trace debug info warn error fatal spy]]
            [clj-stm-poc.sql-wrapper :as sw]))


(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "clj-stm-poc"
                    :description "STM/locking example"}
             :tags [{:name "api", :description "SQL access APIs"}]}}}

    (context "/api" []
      :tags ["api"]

      (GET "/con-count" []
           :return {:connection-count Long}
           (let [count (sw/count-cons)]
             (ok {:connection-count (sw/count-cons)})))

      (POST "/sim-sql" []
            :query-params [sleep-secs :- Long]
            :summary "simulate connection hold for 'sleep' seconds"
            (try
              (ok (sw/sim-sql sleep-secs))
              (catch Exception e
                (do #_(info "Exception " (.getMessage e))
                    (locked))))))))
