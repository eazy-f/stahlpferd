(ns cljakstab
  (:require
   [goog.dom :as gdom]
   [goog.events :as gevents]))

(defn anchor
  [id]
  (str "#" id))

(defn jump-nodes
  [jumps]
  (amap jumps i ret
        (let [target (aget jumps i)]
          (gdom/createDom "a" #js{:href (anchor target)} target))))

(defn statement-nodes
  [statements]
  (amap statements i ret
        (let [statement (aget statements i)
              jumps     (aget statement "jumps")
              statement-text (aget statement "statement")
              label     (aget statement "label")]
          (apply gdom/createDom "p" nil (str label ": " statement-text)
                 (jump-nodes jumps)))))

(defn create-instruction-node
  [instruction]
  (let [instruction-name (aget instruction "instruction")
        statements (aget instruction "statements")
        first-label (aget statements 0 "label")]
    (apply
     gdom/createDom "p"
                    #js{:id first-label}
                    instruction-name
                    (statement-nodes statements))))

(defn parse-result-file
  [file-name file-reader]
  (let [file-content (aget file-reader "result")]
    (.log js/console (str "parsing "
                          file-name ": "
                          (aget file-content "length")))
    (let [analysis (.parse js/JSON file-content)
          target-node (gdom/getElement "text2")]
      (.log js/console (str "instructions: " (aget analysis "length")))
      ; for some reason I cannot use `map` here
      (amap analysis i ret
            (->> (aget analysis i)
              create-instruction-node
              (gdom/appendChild target-node))))))

(defn upload-file
  [input]
  (let [reader (new js/FileReader)
        file (aget input "files" 0)
        name (aget file "name")]
    (aset reader "onload" #(parse-result-file name reader))
    (.readAsText reader file)))

(defn create-file-input
  []
  (gdom/createDom "form" nil
                  (gdom/createDom "p" nil "Select analysis result file")
                  (gdom/createDom "input" #js{:type "file"
                                              :hidden nil})))

(defn create-load-form
  []
  (let
    [body (gdom/getElement "text1")
     file-form (create-file-input)
     file-input (aget file-form "elements" 0)]
    (aset file-form "onchange" #(upload-file file-input))
    (gdom/appendChild body file-form)))

(create-load-form)
