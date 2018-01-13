(ns hieroglyphs.core
  (:require [ring.adapter.jetty :as jetty]
            [clojure.pprint :as pp]
            [ring.middleware.params :as pars]))

(:require 'JTestTransp)

(defn process-request
  "The main and only handler for this app"
  [request]
  (if (= "/favicon.ico" (:uri request))
    {:status 404
     :body ""
     :headers {}}
    (if-not (contains? (:params request)
                       "mdc")
      {:status 500
       :body "No transliteration provided"
       :headers { "Content-Type" "text/plain" }}
      {:status 200
       :body (JTestTransp/pipeline ((:params request)
                                    "mdc"))
       :headers { "Content-Type" "text/plain" }})
    )
  )

(defn -main
  "A web app for hieroglyph visualisation"
  [port-number]
  (jetty/run-jetty (pars/wrap-params process-request)
                   {:port (Integer. port-number)}))









