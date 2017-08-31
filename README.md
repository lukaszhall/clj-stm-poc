# clj-stm-poc

Proof of concept for STM usage (atoms, agents, STM)  in management of
finite stateful resources, such as limited connection resources for
DB access.

## Requirements

### Following must be installed:
* Java
* Leiningen


## Usage

### Standalone Server (manual testing)

1. `$  lein ring server-headless`
2. Access Swagger via [http://localhost:3000/index.html#/api](http://localhost:3000/index.html#/api)

### Automated Testing of concurrent connections
1. `$  lein test`


## Thoughts

### STM investigation results

Due to the try/retry-on-change nature of STM and Agents, proper usage
requires pure (side-effect free) functions. As such, concurrency
mechanics specific to CLJ fit poorly when designing an I/O based
concurrency operators. (To further emphasize this necessity, `io!` is
provided as a safeguard for libraries against any injected operations
causing side effects.)

Several other options exist for writing concurrency safe code.

* Standard Java concurrency libraries are still available and provided
within the clj `locking` libraries

* Atoms/Refs may still be used to create transaction states, but the
  resulting code will resemble typical imperative code blocks rather
  than functional ones.

* Erlang-style Actor systems may be used through several available
  packages, the most notable of which is the Akka wrapper
  [okku](https://github.com/gaverhae/okku)


## License

Copyright © 2017
