(ns main
  (:require 

    [goog.dom :as gdom]
    [goog.events :as gevents]
    [cljs.core.async :as a])
  (:require-macros
    [cljs.core.async.macros :refer [go-loop go]]))

(defn hello []
  (let
    [body (gdom/getElement "text1")
     p #(gdom/createDom "p" nil %1)]
    ;casting is used here to strict lazy for
    ;for some reason casting to `seq` doesn't work
    (vec
      (for [t ["Hello, World!" (str (/ true false))]]
           (gdom/appendChild body (p t))))))

(defn numbers []
  (let
      [nums (a/chan)
       p (gdom/createDom "p" nil "")
       text (gdom/getElement "text2")]
    (gdom/appendChild text p)
    (go-loop []
      (let [num (.toString (a/<! nums))]
        (gdom/removeChildren p)
        (gdom/append p num)
        (recur)))
    (go-loop []
      (a/>! nums (rand-int 100))
      (a/<! (a/timeout 100))
      (recur))))

(let
  [doc (gdom/getWindow)]
  (gevents/listen doc goog.events.EventType.LOAD numbers))

;(.log js/console (gdom/getDocument))
