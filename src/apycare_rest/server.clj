(ns apycare-rest.server 
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [pdfboxing.form :as pdff]
            [ring.util.response :as response] 
            [clojure.pprint :refer [pprint]]
            [clojure.core.async :as async :refer
             [go >! <! chan]]  
            [ring.middleware.json :as middleware]
            [ring.adapter.jetty :as jetty]
            [compojure.handler :as handlers]
            [clojure.java.io :as io]))

(defn spit-fields [pdf-input txt-ouput]
  "output a txt with the fillable-final.pdf field."
  (spit  (str "out/" txt-ouput) 
        (keys
          (pdff/get-fields
            ("resources/" pdf-input))))) 



(defn fill-form [{params :params} file-name]
  "Writes the pdf filled of a form using input from a 
  map with the same string keys"
  (pdff/set-fields
    (str "resources/pdf-forms/" file-name)
    (str "out/filled" "-" file-name)  params))

(defn async-form-deliver [req file]
  "rises a thread to fille a form on requests
  and return the form as a ring response"
  (let [answear (future (fill-form req file))]
    (letfn [(pdf-response [_]
              (response/file-response
                (str "filled" "-" file) 
                {:root "out/"
                 :content-type "application/pdf"}))]
      (pdf-response @answear))))



(defn create-routes []
  (let [pdfs  (.list (io/file "resources/pdf-forms"))]
    (apply routes
           (map #(POST (str "/fill-form/" %) req (async-form-deliver req %)) pdfs)))) 


(defroutes app-routes
  (create-routes)
  (route/not-found "Not Found"))


(def handler 
  (-> (handlers/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-params)
      (middleware/wrap-json-response)))

(defn -main [& args]
  (jetty/run-jetty handler))

;;  (.println System/out (pprint req))))
