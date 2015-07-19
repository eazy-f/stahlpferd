(defproject stahlpferd "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [hiccup "1.0.5"]
                 [stasis "2.1.1"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/clojurescript "0.0-2197"]
                 [om "0.8.0-rc1"]]
  :plugins [[lein-ring "0.8.11"]
            [lein-cljsbuild "1.0.3"]
            [cider/cider-nrepl "0.7.0"]]
  :ring {:handler stahlpferd.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}}
  :aliases {"build-site" ["run" "-m" "stahlpferd.web/export"]}
  :cljsbuild {
    :builds [
             {
      :source-paths ["src-cljs/cljakstab"]
      :compiler {
        :output-to "resources/public/cljakstab.js"
        ;:optimizations :whitespace
        :optimizations :advanced
        :pretty-print true}}]})
