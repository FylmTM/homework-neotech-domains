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
    * [ ] Metrics (JVM, rps, cache hits/misses, external service stability)
    * [ ] Tracing
  * [ ] Logging - tune existing, add application logs
  * [ ] Scaling - put some thought into type of scaling that application can support
  * [ ] UI
    * [ ] Correctly handle when service is down (show alert)
  * [ ] Normalize domain price to base currency
  * [ ] Make services configurable through application configuration
  * [ ] Consider using declarative HTTP client (retrofit, feign, ktor client (?))
  * [ ] Make HTTP requests to external services retryable
  * [ ] Tidy-up gradle build file (extract plugin configurations, versions)
  * [ ] Correctly handle IPv4 and IPv6 when extracting domain name
  * [ ] Consider limiting parsed hostname to 3 labels (to reduce cache misses)
  * [ ] Consider providing user-friendly API where you can pass URL
