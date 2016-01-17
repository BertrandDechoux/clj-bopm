# clj-bopm

Binomial options pricing model ([BOPM](http://en.wikipedia.org/wiki/Binomial_options_pricing_model)) in clojure.

## Installation

Download from https://github.com/BertrandDechoux/clj-bopm

## Usage

This requires ([leiningen](https://github.com/technomancy/leiningen)).
You can then either run the application directly with leiningen...

```
lein run [args]
```

Or, by creating first a jar containing all the dependencies (ie 'uberjar'), run it like any jar.

```
lein ubjerjar
java -jar target/clj-bopm-0.1.0-SNAPSHOT-standalone.jar [args]
```

And if you only want to run the test
```
lein midje
```

## Options

```
lein run -- -h
Usage:

 Switches               Default  Desc                                                             
 --------               -------  ----                                                             
 -h, --no-help, --help  false    Show help                                                        
 -T, --time                      expiration time                                                  
 -S, --stock                     stock price                                                      
 -K, --strike                    strike price                                                     
 -n, --steps                     height of the binomial tree, number of steps                     
 -r, --interest                  risk-free interest rate                                          
 -d, --sigma                     volatility of the price (standard deviation)                     
 -q, --yield                     dividend yield (dividend-price ratio)                            
 -f, --style                     style (or family) of the option, either 'american' or 'european' 
 -o, --option                    type of the option, either 'call' or 'put'
```

## License

Copyright © 2013 Bertrand Dechoux

Distributed under the Eclipse Public License, the same as Clojure.
