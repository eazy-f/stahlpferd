(defproject stahlpferd "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [hiccup "1.0.5"]
                 [stasis "2.1.1"]]
  :plugins [[lein-ring "0.8.11"]
            [lein-cljsbuild "1.0.3"]]
  :ring {:handler stahlpferd.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]
                        [org.clojure/clojurescript "0.0-2197"]]}}
  :aliases {"build-site" ["run" "-m" "stahlpferd.web/export"]}
  :cljsbuild {
    :builds [{
      :source-paths ["src-cljs"]
      :compiler {
        :output-to "resources/public/main.js"
        ;:optimizations :whitespace
        :optimizations :advanced
        :pretty-print true}}]})