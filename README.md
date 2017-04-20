# Projeto cadastro de campanhas

## Information

- Porta da aplicação :8080
- Para iniciar a aplicação execute : --> gradle bootRun 
- Para ver a documentação das APIS rest veja --> /documentation/index.html
- Para log favor consultar o arquivo camapanha.log criado na raiz da aplicaçao
- Java code coverage : 

## Technologies and frameworks used and reasons

**For creating the Project I use:**

- **Gradle** - _A build system that is a quantum leap for build technology in the Java (JVM) world. Gradle provides:_
    - A very flexible general purpose build tool like Ant.
    - Switchable, build-by-convention frameworks a la Maven. But we never lock you in!
    - Very powerful dependency management (based on Apache Ivy).
    - Groovy build scripts.
    - A rich domain model for describing your build.              


- **Spring boot** - _Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can
 “just run”. We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. Most Spring Boot applications need very little Spring configuration_.
    - Provide a radically faster and widely accessible getting started experience for all Spring development.
    - Be opinionated out of the box, but get out of the way quickly as requirements start to diverge from the defaults.
    - Provide a range of non-functional features that are common to large classes of projects (e.g. embedded servers, security,
      metrics, health checks, externalized configuration).
    - Absolutely no code generation and no requirement for XML configuration.

**For creating products I use:**
 - **MongoDB | Spring Data Mongo | Spring Data Rest** 
 
 - **Spring Data Rest** - _Spring Data REST builds on top of Spring Data repositories and automatically exports those as REST resources. It leverages hypermedia to allow clients to find functionality exposed by the repositories and integrates these resources into related hypermedia based functionality automatically._

## Default status codes

For the resources exposed(Product), I use set of default status codes:

- 200 OK - for plain GET requests.

- 201 Created - for POST and PUT requests that create new resources.

- 204 No Content - for PUT, PATCH, and DELETE requests if the configuration is set to not return response bodies for resource updates (RepositoryRestConfiguration.returnBodyOnUpdate). If the configuration value is set to include responses for PUT, 200 OK will be returned for updates, 201 Created will be returned for resource created through PUT.

##Resource discoverability
- A core principle of HATEOAS is that resources should be discoverable through the publication of
links that point to the available resources. There are a few competing de-facto standards of how 
to represent links in JSON. By default, **Spring Data REST** uses **HAL** to render responses. HAL defines 
links to be contained in a property of the returned document.

- Resource discovery starts at the top level of the application. By issuing a request to the root 
URL under which the Spring Data REST application is deployed, the client can extract a set of 
links from the returned JSON object that represent the next level of resources that are available
to the client.

For example, to discover what resources are available at the root of the application, issue an HTTP GET to the root URL:

curl -v http://localhost:8080/api  
```
< HTTP/1.1 200 OK
< Content-Type: application/hal+json
```

```
{
  "_links": {
    "products": {
      "href": "http://localhost:8080/api/products{?page,size,sort}",
      "templated": true
    },
    "profile": {
      "href": "http://localhost:8080/api/profile"
    }
  }
}
```

    
- **MongoDB** - _MongoDB is an open-source document database that provides high performance, high availability, and automatic scaling. A record in MongoDB is a document, which is a data structure composed of field and value pairs.
 MongoDB documents are similar to JSON objects. The values of fields may include other documents, arrays, and arrays of documents. _
    - Documents (i.e. objects) correspond to native data types in many programming languages.
    - Embedded documents and arrays reduce need for expensive joins.
    - Dynamic schema supports fluent polymorphism. 
 

- **Swagger** - _Swagger is a powerful open source framework backed by a large ecosystem of tools that helps you design,
 build, document, and consume your RESTful APIs._


- **AssertJ** - _AssertJ core is a Java library that provides a fluent interface for writing assertions. Its main goal is 
to improve test code readability and make maintenance of tests easier._
  
