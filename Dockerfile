FROM adoptopenjdk:11-jdk-hotspot
ADD target/locative-0.0.1-SNAPSHOT.jar locative-0.0.1-SNAPSHOT.jar
EXPOSE 8899
ENTRYPOINT [ "java", "-jar", "locative-0.0.1-SNAPSHOT.jar" ]