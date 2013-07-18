(ns clj-bopm.cli
  (:use [clj-bopm.core :only [styles options start-bopm]])
  (:use [clojure.tools.cli :only [cli]])
  (:gen-class))

(defn ^:private as-int    [arg] (Integer. arg))
(defn ^:private as-float  [arg] (Float.   arg))
(defn ^:private as-style  [arg] (styles   arg))
(defn ^:private as-option [arg] (options  arg))

(defn ^:private parse [args]
  (cli args
    ["-h" "--help" "Show help" :flag :true :default false]
    ["-T" "--time" "expiration time" :parse-fn as-int]
    ["-S" "--stock" "stock price" :parse-fn as-int]
    ["-K" "--strike" "strike price" :parse-fn as-int]
    ["-n" "--steps" "height of the binomial tree, number of steps" :parse-fn as-int]
    ["-r" "--interest" "risk-free interest rate" :parse-fn as-float]
    ["-d" "--sigma" "volatility of the price (standard deviation)" :parse-fn as-float]
    ["-q" "--yield" "dividend yield (dividend-price ratio)" :parse-fn as-float]
    ["-f" "--style" "style (or family) of the option, either 'american' or 'european'" :parse-fn as-style]
    ["-o" "--option" "type of the option, either 'call' or 'put'" :parse-fn as-option]))

(def ^:private required
  [:time :stock :strike :interest :sigma :yield :steps :style :option])

(defn ^:private extract [required options]
  (map #(options %) required))

(defn ^:private try-start [args]
  (let [[options args banner] (parse args)
        parameters (extract required options)]
    (cond (:help options)
            (do (println banner)
                (System/exit 0))
          (some nil? parameters)
            (do (println "Missing required parameter(s). Input was :")
                (println (zipmap required parameters))
                (println banner)
                (System/exit 1))
          :else
            (do (println "Starting bopm with following configuration")
                (println (zipmap required parameters))
                (apply start-bopm parameters))
    )))

(defn -main
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (println (try-start args)))
