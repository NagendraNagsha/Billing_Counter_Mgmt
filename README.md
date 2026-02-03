Project: Billing / ProcessBill (Java, Maven)

Overview
This project implements billing domain classes using Java and Maven. It uses Spring Data Redis annotations for caching/persistence of bill data. The core class shown is `src/main/java/org/billing/ProcessBill.java`.

Prerequisites
- Java 8
- Maven 3.6+
- IntelliJ IDEA 2025.2.6 (development recommended)
- Windows environment (commands shown use Windows path separators)

Build
1. Open project in IntelliJ IDEA or your preferred IDE.
2. From project root (Windows CMD or PowerShell):
   mvn clean package

Run
mvn spring-boot:run


Configuration
- Redis connection and other properties are configured via your `application.properties` or `application.yml`.
- Ensure Redis is reachable from the application. Example properties to add/verify:
  - spring.redis.host
  - spring.redis.port
  - spring.redis.password (if applicable)

Testing
- Run unit tests:
  mvn test

Important files
- `src/main/java/org/billing/ProcessBill.java` - main billing class; manages items, quantities and caches using Redis.
- `src/main/java/org/model/Item.java` - item model (expected).
- `src/main/java/org/model/ProductDetails.java` - product details model (expected).
- `src/main/java/org/util/HelperUtil.java` - helper to load product details (expected).
- `pom.xml` - Maven build and dependency configuration.
- `application.properties` or `application.yml` - runtime configuration (Redis, profiles, ports).

Contact
- Repository owner: NagendraNagsha (GitHub)

End of readme.
