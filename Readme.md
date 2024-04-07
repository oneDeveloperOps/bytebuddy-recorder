Go to record/src/main/resources/application.properties -> update database creds for postgres database
db user: postgres
db password: root

Steps to build jar for agent
1 -> cd bytebuddy
2 -> mvn install

Steps to build jar for mainApplication
1 -> cd record
2 -> sudo ./gradlew build


Currently logging record data only have to save that in a file to reuse the data for replaying.

ENV check and docker changes will publish with replayer

Execute this in root project command to run the application:

java -javaagent:opentelemetry-javaagent.jar \
-Dotel.traces.exporter=none \
-Dotel.metrics.exporter=none \
-Dotel.service.name=record \
-Dotel.javaagent.extensions=bytebuddy/target/bytebuddy-1.0-SNAPSHOT.jar
-Dotel.javaagent.debug=true -jar record/build/libs/record-0.0.1-SNAPSHOT.jar
