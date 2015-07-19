(ns main
  (:require

    [goog.dom :as gdom]
    [goog.events :as gevents]
    [cljs.core.async :as a]
    [om.core :as om :include-macros true]
    [om.dom :as dom :include-macros true])
  (:require-macros
    [cljs.core.async.macros :refer [go-loop go]]))

(def om-number (atom 12))

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
      (let [rand-num (rand-int 100)]
        (reset! om-number rand-num)
        (a/>! nums rand-num)
        (a/<! (a/timeout 100))
        (recur)))))

(om/root
 (fn [app owner]
   (om/component
    (dom/h2 nil (str app))))
 om-number
 {:target (gdom/getElement "text3")})

(.log js/console "hello")

;(numbers)

;(.log js/console (gdom/getDocument))
