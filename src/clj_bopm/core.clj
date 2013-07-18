;; In finance, the binomial options pricing model (BOPM) provides
;; a generalizable numerical method for the valuation of options.
;; The binomial model was first proposed by Cox, Ross and Rubinstein (1979).
;; Confer [wikipedia BOPM page](http://en.wikipedia.org/wiki/Binomial_options_pricing_model).

(ns clj-bopm.core
  (:use [incanter.core]))

;; The computation being resource hungry, reflection should be avoided
;; and overflow checks will also be disabled.
;; Knowing if the latter is wise is another story.

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

;; ## Types of options

;; This project will deal with only two types of options : call and put.

(defn call-option
  "A call option offers the right to buy (without any obligation)
   an underlying instrument at a fixed price called the strike price (K).
   Obviously, the exercise is interesting if this price is below the
   current price called the spot price (K < S) and should be performed
   when the gain is greater than when holding.
   Confer [wikipedia call option page](http://en.wikipedia.org/wiki/Call_option)"
  [hold ksdiff] (max hold (- ksdiff)))

(defn put-option
  "A put option offers the right to sell (without any obligation)
   an underlying instrument at a fixed price called the strike price (K).
   Obviously, the exercise is interesting if this price is above the
   current price called the spot price (K > S) and should be performed
   when the gain is greater than when holding.
   Confer [wikipedia put option page](http://en.wikipedia.org/wiki/Put_option)"
  [hold ksdiff] (max hold ksdiff))

(defn wait-option
  "Under specific circumstances, an option might not be allowed to be
   exercised. In that case, its value is its holding value, by definition."
  [hold ksdiff] hold)

(def options {"call" call-option, "put" put-option})
;; ## Styles of options

;; This project will deal with only two styles or families of options :
;; American and European. Confer 
;; [wikipedia option style page](http://en.wikipedia.org/wiki/Option_style#American_and_European_options).

(defn american-style
  "An American (call or put) option can be exercised at any time
   before the expiration date."
  [option] option)

(defn european-style
  "A European (call or put) action can only be exercised at
   the expiration date."
  [option] wait-option)

(def styles {"american" american-style, "european" european-style})

;; ## The algorithm

;; `TODO` fix support of MathJax within marginalia?
;; equations would be useful at this point... 

(defn burn-bopm
  "The core of the algorithm is simply a nested loop.
  It uses a local mutable primitive array for performance reason."
  [n init-step step]
  (let [nodes (float-array (inc n))]
        (do
          (doseq [i (range (inc n))]
            (aset-float nodes i
              (init-step i)))
          (doseq [j (reverse (range n)), i (range (inc j))]
            (aset-float nodes i
              (step (aget nodes i) (aget nodes (inc i)) i j)
            ))
          (first nodes))))

(defn run-bopm
 [S0 K p decay up n style option]
 {:pre  [(every? pos? [S0 K decay n])
         (<= 1 up)(<= 0 p 1)
         (every? #(not (nil? %)) [style option])]}
     (let [expiration-val #(option 0 %)
       tmp-val (style option)
       diffks #($= K - S0 * (pow up ($= 2 * %1 - %2)))
       dpu (* decay p)
       dpd (* decay (- 1 p))
       init-step #(expiration-val (diffks % n))
       step (fn [cd cu j i] (tmp-val ($= dpu * cu + dpd * cd) (diffks i j)))]
       (burn-bopm n init-step step)))

(defn start-bopm
  [T S K r sigma q n style option]
  {:pre  [(every? pos? [T S K r sigma q n])
          (every? #(not (nil? %)) [style option])]}
  (let [deltaT (/ T n)
        up ($= (exp sigma * (sqrt deltaT)))
        rqdiff (- r q)
        p (/ ($= up * (exp rqdiff * deltaT) - 1) ($= up * up - 1)) 
	decay ($= (exp (- r) * deltaT))]
        (run-bopm S K p decay up n style option)))

