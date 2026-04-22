# Comment Service

Сервис комментариев для задач.

## Назначение

- хранение и выдача комментариев;
- создание и обновление комментариев;
- интеграция с `user-service` и `task-service` через Feign-клиенты.

## Технологии

- Spring Boot
- Spring Web
- Spring Data MongoDB
- Spring Cloud Config Client
- Eureka Client
- OpenFeign

## Порт и healthcheck

- Порт: `8088`
- Health: `GET http://localhost:8088/actuator/health`

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
docker compose up -d comment-service
docker compose logs -f comment-service
```

## Основные REST endpoints

- `GET /api/comment`
- `GET /api/comment/{id}`
- `POST /api/comment`
- `PUT /api/comment/{id}`