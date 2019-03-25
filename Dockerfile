FROM openjdk:11-jdk-slim
ENV _JAVA_OPTIONS "-Xms768m -Xmx1024m -XX:+UseG1GC"
RUN apt-get update && \
    apt-get install -y bash && \
    rm -rf /var/cache/apt/archives
COPY entrypoint.sh /entrypoint.sh
COPY build/libs/application.jar /application.jar
ENTRYPOINT ["/entrypoint.sh"]