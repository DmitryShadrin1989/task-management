# Task Service

Сервис управления задачами и их жизненным циклом.

## Назначение

- создание, изменение и получение задач;
- хранение данных задач в MongoDB;
- участие в межсервисном взаимодействии через Eureka/Feign.

## Технологии

- Spring Boot
- Spring Web
- Spring Data MongoDB
- Spring Cloud Config Client
- Eureka Client
- OpenFeign

## Порт и healthcheck

- Порт: `8087`
- Health: `GET http://localhost:8087/actuator/health`

## Зависимости при запуске

- `mongo`
- `config-server`
- `discovery-server`

## Локальная сборка

```bash
./mvnw -DskipTests clean package
```

Если wrapper недоступен:

```bash
mvn -DskipTests clean package
```

## Запуск в Docker Compose

Из корня репозитория:

```bash
docker compose up -d task-service
docker compose logs -f task-service
```