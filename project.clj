(defproject clj-bopm "0.1.0-SNAPSHOT"
  :description "Binomial options pricing model (BOPM) in clojure."
  :url "https://github.com/BertrandDechoux/clj-bopm"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [incanter "1.9.0"]
                 [org.clojure/tools.cli "0.3.3"]]
  :profiles {:dev {:plugins [[lein-midje "3.2"] [lein-marginalia "0.8.0"]]
                   :dependencies [[midje "1.8.3"]]}}
  :main clj-bopm.cli)
