FROM bellsoft/liberica-openjdk-alpine
RUN sed -i 's/https/http/g' /etc/apk/repositories && \
    apk update && \
    apk add --no-cache curl ca-certificates && \
    update-ca-certificates
COPY /target/config-server.jar /app/config-server.jar
ENTRYPOINT ["java", "-jar", "/app/config-server.jar"]