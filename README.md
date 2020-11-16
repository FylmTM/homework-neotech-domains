# Neotech Domain

Service to retrieve domain name information.
Task description can be found [here](./TASK.md).

Below you can find:

* [TODO](#TODO) list for all completed and outstanding tasks.
* [Design Decisions](#design-decisions) section with explanations on **why** some things
  are done the way they are done.

## Design Decisions

WIP.

## TODO

* [ ] Backend
  * [ ] Integrations
    * [ ] Whois
      * [ ] Whoisxmlapi
    * [ ] Registar
      * [ ] Namecheap
  * [ ] Validation
    * [ ] Domain
      * [ ] Support non-english domains (russian, chinese, etc.)
  * [ ] Domain information retrieval
    * [ ] Cascade information retrieval
    * [ ] Cache domain information with TTL
  * [ ] Domain price retrieval
    * [ ] Support multiple providers
    * [ ] Cache domain price with TTL
  * [ ] Environments - development, testing, staging, production
  * [ ] Tests
    * [ ] Integration tests with real services under specific flag (to be run on CI only)
  * [ ] Important
    * [ ] Support multiple currencies for price
    * [ ] Store price as BigDecimal (don't loose precision)
* [ ] Frontend
  * [ ] Favicon!
  * [ ] Single HTML page with vanilla JS
  * [ ] Handle errors (connectivity, invalid response, validations)
* [ ] Improvements
  * [ ] Code quality (formatting, linter)
  * [ ] Build docker image
    * [ ] Consider Jib
  * [ ] Sane default JVM configuration (heap sizes, GC type, etc.)
    * [ ] Make sure they are docker/k8s friendly (consider non-heap memory)
  * [ ] Hardening
    * [ ] Security (CSP?, CORS?, something else?)
    * [ ] Rate limiting
    * [ ] Graceful shutdown
    * [ ] Include external service to health
  * [ ] Authentication (do we need one?)
  * [ ] Monitoring
    * [ ] Metrics (JVM, rps, cache hits/misses)
    * [ ] Tracing
  * [ ] Logging - tune existing, add application logs
  * [ ] Scaling - put some thought into type of scaling that application can support
  * [ ] UI
    * [ ] Correctly handle when service is down (show alert)
  * [ ] Normalize domain price to base currency
