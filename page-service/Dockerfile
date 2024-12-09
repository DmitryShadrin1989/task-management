FROM bellsoft/liberica-openjdk-alpine
RUN sed -i 's/https/http/g' /etc/apk/repositories && \
    apk update && \
    apk add --no-cache curl ca-certificates && \
    update-ca-certificates
COPY /target/page-service.jar /app/page-service.jar
ENTRYPOINT ["java", "-jar", "/app/page-service.jar"]