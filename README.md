# Neotech Domain

Service to retrieve domain name information.
Task description can be found [here](./TASK.md).

Below you can find:

* [TODO](#TODO) list for all completed and outstanding tasks.
* [Design Decisions](#design-decisions) section with explanations on **why** some things
  done the way they are done.

## Development

```
# Run all tests
./gradlew test
```

## Build & Run

```
./gradlew bootJar
java -jar libs/app.jar
```

## Design Decisions

### Configuration

Application defines different sets of configuration, to allow customizing
behaviour without re-builds (through any means that Spring Boot supports, like env variables)

This includes:

- Specifying Whois service to use
- Configuring each Whois service
- Specifying which Registrar services to enable
- Configuring each Registrar service

### Price for one year

I have made an assumption that we are interested only in prices for one year duration.
If that's not the case (talk to PO), then we need extend information returned by registrar to include
prices with their durations.

**Update:**

Apparently some TLD's are not providing price for 1 year (ai, nu). For those we will first available price.
I need to revisit this decision later.


### TLDs

I am using to name both top-level and second-level domain names.

## TODO

This is effectively a mix of planned work & everything that I encountered on the go.

* [ ] Backend
  * [ ] Integrations
    * [x] Whois
      * [x] Whoisxmlapi
    * [ ] Registar
      * [ ] Namecheap
  * [x] Validation
    * [x] Domain
      * [x] Support non-english domains (russian, chinese, etc.)
  * [ ] Domain information retrieval
    * [ ] Information retrieval
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
  * [ ] Consider cascading whois information retrieval with multiple providers
  * [ ] Consider handling errors at integration points (e.g. whois service)
  * [ ] Spend more time exploring "whoisxmlapi" output, to make information retrieval more stable (handle errors, and different formats)
  * [ ] Start mock servers lazily (to avoid running them when they are not needed)
  * [ ] Support request batching
  * [ ] Add API documentation (e.g. Swagger)
  * [ ] Create mock web server for Namecheap API's
  * [ ] Fully translate namecheap responses to Kotlin
  * [ ] Include handling of error responses into error decoder for feign
  * [ ] More testing for namecheap
  * [ ] Be more specific in all cases why information is missing for TLD
  * [ ] More tests for non-ascii domains
  * [ ] Use snapshots to test large serializations (e.g. domain prices for namecheap)
  * [ ] Avoid using xml mapper holder to hide XML configuration from spring (named autowired / qualifier)
  * [ ] Find premium domain on namecheap for testing
  * [ ] Re-visit returning prices for 1 year idea
  * [ ] Handle separately any errors and expected errors (e.g. tld not supported) for domain check
  * [ ] Add possibility to specify what TLD's registar supports
  * [ ] Drop "Service" postfix from integrations, it's annoying
  * [ ] Consider making domain status service cacheable
  * [ ] Consider making whois service cacheable
  * [ ] Implement application specific error decoder, to cover failure test cases
  * [ ] Add body decoding in app client errordecoder for integration tests
