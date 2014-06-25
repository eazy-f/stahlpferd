(ns stahlpferd.handler
  (:require [compojure.core :as cc]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [stahlpferd.views :as v]))

(cc/defroutes app-routes
  (cc/GET "/" [] v/main)
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
