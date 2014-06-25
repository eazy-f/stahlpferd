(ns stahlpferd.web
  (:require [stasis.core :as stasis]
            [hiccup.page :as hp]))

(defn index-page []
  (hp/html5
    [:head
     (hp/include-js "main.js")]
    [:body
      [:p "Ride das stahl pferd"]]))

(defn get-pages []
  (merge
    (stasis/slurp-directory "resources/public" #".+\.js$")
    {"/" (index-page)}))

(defn export []
  (let [export-dir "web"]
    (stasis/empty-directory! export-dir)
    (stasis/export-pages (get-pages) export-dir)))