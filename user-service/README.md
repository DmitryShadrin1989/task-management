# User Service

Сервис управления пользователями.

## Назначение

- хранение и предоставление данных пользователей;
- поддержка доменной логики, связанной с пользователями;
- участие в межсервисных вызовах.

## Технологии

- Spring Boot
- Spring Web
- Spring Data MongoDB
- Spring Cloud Config Client
- Eureka Client

## Порт и healthcheck

- Порт: `8086`
- Health: `GET http://localhost:8086/actuator/health`

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
docker compose up -d user-service
docker compose logs -f user-service
```