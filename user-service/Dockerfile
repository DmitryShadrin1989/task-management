FROM bellsoft/liberica-openjdk-alpine
RUN sed -i 's/https/http/g' /etc/apk/repositories && \
    apk update && \
    apk add --no-cache curl ca-certificates && \
    update-ca-certificates
COPY /target/user-service-0.1.jar /app/user-service-0.1.jar
ENTRYPOINT ["java", "-jar", "/app/user-service-0.1.jar"]