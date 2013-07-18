(ns clj-bopm.core-test
  (:use midje.sweet) 
  (:require [clj-bopm.core :refer :all]))

(facts "about call-option"
  (call-option 42 -41) => 42
  (call-option 42 -43) => 43
  ((american-style call-option) 42 -41) => 42
  ((american-style call-option) 42 -43) => 43
  ((european-style call-option) 42 -41) => 42
  ((european-style call-option) 42 -43) => 42
)

(facts "about put-option"
  (put-option 42 41) => 42
  (put-option 42 43) => 43
  ((american-style put-option) 42 41) => 42
  ((american-style put-option) 42 43) => 43
  ((european-style put-option) 42 41) => 42
  ((european-style put-option) 42 43) => 42
)

(facts "about wait-option"
  (wait-option 42 41) => 42
  (wait-option 42 43) => 42)

(let [S0 10, K 5, p 0.5, decay 1, up 1, n 10]
  (fact "When strike < spot for a call-option without decay and stationary"
    (run-bopm S0 K p decay up n european-style call-option)  => 5.0))

(let [S0 5, K 10, p 0.5, decay 1, up 1, n 10]
  (fact "When strike > spot for a call-option without decay and stationary"
    (run-bopm S0 K p decay up n european-style call-option)  => 0.0))

(let [S0 10, K 10, p 1, decay 1, up 1.2, n 10]
  (fact "When strike = spot for a call-option without decay but increasing"
    (run-bopm S0 K p decay up n european-style call-option)  => #(< 1 %)))

(let [S0 10, K 10, p 0, decay 1, up 1.2, n 10]
  (fact "When strike = spot for a call-option without decay but decreasing"
    (run-bopm S0 K p decay up n european-style call-option)  => 0.0))

