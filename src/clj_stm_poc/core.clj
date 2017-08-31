(ns clj-stm-poc.core
  (:require [taoensso.timbre :as timbre])
  (:gen-class))

(defn -main
  "Placeholder/uberjar entrypoint."
  [& args]
  (timbre/info "Invoke live/reloadable server via 'lein ring server-headless'")
  (timbre/info "Invoke tests via 'lein test'"))
