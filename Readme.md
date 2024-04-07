Go to record/src/main/resources update database creds for postgres 
currently password is root for postgres user;

Steps to build jar for agent
1 -> cd bytebuddy
2 -> mvn install

Steps to build jar for mainApplication
1 -> cd record
2 -> sudo ./gradlew build


go to root folder and execute currently logging record data only have to
save that in a file to reuse the data for replaying

execute this command to run the application:

java -javaagent:opentelemetry-javaagent.jar \
-Dotel.traces.exporter=none \
-Dotel.metrics.exporter=none \
-Dotel.service.name=record \
-Dotel.javaagent.extensions=bytebuddy/target/bytebuddy-1.0-SNAPSHOT.jar
-Dotel.javaagent.debug=true -jar record/build/libs/record-0.0.1-SNAPSHOT.jar