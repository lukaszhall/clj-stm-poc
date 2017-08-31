 (defproject clj-stm-poc "0.1.0-SNAPSHOT"
   :description "STM over REST API poc"
   :dependencies [[org.clojure/clojure "1.9.0-alpha19"]
                  [metosin/compojure-api "1.1.11"]
                  [com.taoensso/timbre "4.10.0"]
                  [clj-http "3.7.0"]
                  [ring/ring-mock "0.3.1"]
                  [cheshire "5.8.0"]
                  ]
   :main  clj-stm-poc.core
   :ring {:handler clj-stm-poc.handler/app}
   :uberjar-name "clj-stm-poc.jar"
   :source-paths["src"]
   :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]
                                   [cheshire "5.8.0"]
                                   [com.taoensso/timbre "4.10.0"]]
                    :plugins      [[lein-ring "0.12.0"]]}})
