(ns stahlpferd.web
  (:require [stasis.core :as stasis]
            [hiccup.page :as hp]))

(defn index-page []
  (hp/html5
    [:body
      [:div {:id "text1"}]
      [:div {:id "text2"}]
      [:div {:id "text3"}]
      (hp/include-js "http://fb.me/react-0.12.2.js")
      (hp/include-js "./cljakstab.js")]))

(defn get-pages []
  (merge
    (stasis/slurp-directory "resources/public" #".+\.js$")
    {"/" (index-page)}))

(defn export []
  (let [export-dir "web"]
    (stasis/empty-directory! export-dir)
    (stasis/export-pages (get-pages) export-dir)))

(defn handle-pages []
  (stasis/serve-pages get-pages))
