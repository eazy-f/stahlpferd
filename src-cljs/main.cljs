(ns main
  (:require 
    [goog.dom :as gdom]
    [goog.events :as gevents]))

(defn hello []
  (let
    [body (gdom/getElement "text1")
     p #(gdom/createDom "p" nil %1)]
    (gdom/appendChild body
      (p "Hello, World!"))
    (gdom/appendChild body
      (p (str (/ true false))))))

(let
  [doc (gdom/getWindow)]
  (gevents/listen doc goog.events.EventType.LOAD hello))

(.log js/console (gdom/getDocument))