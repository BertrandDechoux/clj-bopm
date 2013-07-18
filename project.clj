(defproject clj-bopm "0.1.0-SNAPSHOT"
  :description "Binomial options pricing model (BOPM) in clojure."
  :url "https://github.com/BertrandDechoux/clj-bopm"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [incanter "1.5.1"]
                 [org.clojure/tools.cli "0.2.2"]]
  :profiles {:dev {:plugins [[lein-midje "3.0.1"] [lein-marginalia "0.7.1"]]
                   :dependencies [[midje "1.5.1"]]}}
  :main clj-bopm.cli)
