# TomEE Dev-Starter

A lightweight development starter for running an embedded Apache TomEE (OpenEJB) container from a plain `main` method. Useful for rapid prototyping and local development of Jakarta EE applications without a standalone server installation.

## Architecture

The project contains a single utility class — `EmbeddedStarter` — that wraps the Jakarta EE `EJBContainer` API. It configures HTTP port, project stage, and optional classpath filtering, then starts the container in the calling thread.

## Requirements

- **Java 25+**
- **Maven 3.6.3+**
- Apache TomEE 10.x libraries on the classpath

## Usage

Add the dependency and call `EmbeddedStarter.startTomEE(...)`:

```java
EmbeddedStarter.startTomEE("Development", "/myapp", 8080);
```

## Building

```bash
mvn clean verify
```

## Quality Plugins

| Plugin      | Purpose                                    |
|-------------|--------------------------------------------|
| Compiler    | `-Xlint:all`, fail on warnings             |
| Enforcer    | Java 25+, Maven 3.6.3+, dependency convergence, ban `javax.*` |
| Checkstyle  | Code-style checks (see `checkstyle.xml`)   |
| RAT         | Apache 2.0 license-header verification     |
| Surefire    | Test execution (JUnit Jupiter)             |
| JaCoCo      | Code-coverage reporting                    |
| Javadoc     | API documentation generation               |

## Testing

Tests use the [Dynamic CDI Test Bean Addon](https://github.com/os890/dynamic-cdi-test-bean-addon) with OpenWebBeans SE to boot a CDI container and verify bean discovery.

## License

This project is licensed under the [Apache License, Version 2.0](LICENSE).
