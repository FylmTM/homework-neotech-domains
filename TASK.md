# Domains Service

Develop a service which can retrieve information about domain name.

User enters domain name on UI, system validates domain
name and shows information about this domain name.

Information if domain not exists:

* Current registar
* Expiration date

Information if domain exists:

* Price

## Requirements

* Support adding new registars
* Minimize request count to external services

## Non-functional requirements

Backend:

* Java 8+ / Kotlin
* Spring Boot
* Maven/Gradle
* HTTP, REST, JSON

Notes:

* Should be possible to run app locally on port 8080
* Should be possible to run tests and see result
* It's not important how UI looks, just make it pretty
* Validate data
* Tests!

## Services

**Whois:**

* https://www.whoisxmlapi.com/


**Registar:**

* https://www.namecheap.com/support/api/intro/
