(defproject apycare-rest "0.0.1"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.2.0"]
                 [ring/ring-defaults "0.1.2"]
                 [pdfboxing "0.1.11"]                 
                 [org.clojure/core.async "0.3.442"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-jetty-adapter "1.3.2"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:nrepl {:start? true}
         :port 8000
         :handler apycare-rest.server/handler}
  :main ^:skip-aot apycare-rest.server
  :aot :all
  :profiles
   {:dev {:dependencies
          [[javax.servlet/servlet-api "2.5"]]}})
