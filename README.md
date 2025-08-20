# Getting Started

### What it is

A service to validate IBANs from a given text or a remote PDF.

### What you need
- To run: 
  - Java 21 or later (tested on 21)
  - run 'java -jar ibanvalidator.jar' to start the service
  - Swagger UI after start: http://localhost:8080/swagger-ui/index.html
  - Sample IBAN for blacklist check: DE99 1234 1234 1234 1234 12
- To open the project: 
  - IDE (service was developed in IntelliJ) 
  - Gradle (https://gradle.org/install/)

### Endpoints (GET)
- /api/v1/ibanvalidation/validate-string?input=
- /api/v1/ibanvalidation/validate-remote?url=

### Used frameworks / libraries
Spring Boot
- Webserver
- Webservice functionalities
- DI
- Global exception handler
- Mocked tests (Mockito included)

Lombok
- Generate boilerplate code

PDF Box
- PDF handling

### Service Responses
No errors (Status 200):
```
{
  "processingError": null,
  "validationErrors": null
}
```
Processing errors (500):
```
{
  "processingError": "URI is not absolute",
  "validationErrors": null
}
```
Validation failures (400):
```
{
  "processingError": "Invalid IBANs detected in the document(s).",
  "validationErrors": [
    {
      "errorMessage": "The IBAN is blacklisted.",
      "iban": "DE99123412341234123412"
    }
  ]
}
```