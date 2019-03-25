FROM openjdk:11-jdk-slim
ENV _JAVA_OPTIONS "-Xms768m -Xmx1024m -XX:+UseG1GC"
RUN apt-get update && \
    apt-get install -y bash && \
    rm -rf /var/cache/apt/archives
COPY build/libs/dius-shopping.jar /application.jar
ENTRYPOINT ["java", "-jar", "/application.jar"]