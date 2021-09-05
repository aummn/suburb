Introduction
=========================
This project 'suburb' employs SpringBoot and Java to provides several APIs for Suburb resource management.
Any questions, please contact James at (canglangke001@gmail.com).


APIs
=========================================================
1. Create suburb:                   /api/suburb/add
2. Get suburb info by post code:    /api/suburb/postcode/{postcode}
3. Get post code by suburb name:    /api/suburb/name/{name}
4. Get suburb info by Id:           /api/suburb/id/{id}

Suburb creation API '/api/suburb/add' is protected by Spring security with basic authentication.
User/password/roles are defined in Security config file in project folder 'com.aummn.suburb.config'.




Tech Stack:
=========================================================
Rx Java, Spring Security, Spring Boot, Spring Data, 
Lombok, Swagger, Mockito, Junit, Maven


Project Structure
=========================================================
This project is a Maven project with following folders

src/main/java      -  source code 
src/test/java      -  test code
src/main/resources -  property file and logging file
dev-tools          -  Postman test collection file

com.aummn.suburb.resource              - Suburb resource exposing rest APIs
com.aummn.suburb.resource.dto.request  - Inbound DTO class holding input Suburb info for Suburb creation
com.aummn.suburb.resource.dto.response - Outbound DTO class for suburb detail retrieval

com.aummn.suburb.service               - Suburb services
com.aummn.suburb.service.dto.request   - inbound DTO class for Suburb service
com.aummn.suburb.service.dto.response  - outbound DTO class from Suburb service to Suburb resource

com.aummn.suburb.repo      - Suburb Repository for persisting/retrieving Suburb info
com.aummn.suburb.entity    - entity class folder

com.aummn.suburb.config    - Security config and Swagger config
com.aummn.suburb.exception - Global exception handling
com.aummn.suburb.validator - input method parameter validation

Logging
=========================================================
 Logging is enabled via @Slf4j for exception handling and normal info output.
 Logging config can be found in file 'logback-spring.xml' in folder 'src/main/resources'


Building
=========================================================
Since this is a Maven based project, it can be easily packaged as an executable jar with Maven command.

Please ensure Maven is installed and configured, then go to project folder, 
for example '/c/dev/git/projects/suburb', type the following Maven command to package the application:

Administrator@AUMMN MINGW64 /c/dev/git/projects/suburb
$ mvn clean package


This would create the executable jar file 'suburb-1.0.0-SNAPSHOT.jar' in target folder

[INFO] --- maven-jar-plugin:3.2.0:jar (default-jar) @ suburb ---
[INFO] Building jar: C:\dev\git\projects\suburb\target\suburb-1.0.0-SNAPSHOT.jar




Launching
=========================================================
Ensure java is installed and configured, go to the folder containing jar file 'suburb-1.0.0-SNAPSHOT.jar',
type the following Java command to launch the Suburb API application:

java -jar suburb-1.0.0-SNAPSHOT.jar



API testing
=========================================================
It can be done by various tools, for example, Postman.
Postman collection file 'Suburb APIs.postman_collection.json' is in project folder 'dev-tools' and
 can be imported into another Postman. After launching Suburb API, then you can play with Suburb APIs, 
 please remember change the host name and port to suit your needs.


ToDo
=========================================================
Future work may involve E2E test, Consumer-Driven Contracts, Hypermedia API Support etc.









 