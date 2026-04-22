# Board Service

Сервис управления досками.

## Назначение

- операции с досками (создание, изменение, получение);
- хранение данных в MongoDB;
- интеграция в общую микросервисную инфраструктуру.

## Технологии

- Spring Boot
- Spring Web
- Spring Data MongoDB
- Spring Cloud Config Client
- Eureka Client
- OpenFeign

## Порт и healthcheck

- Порт: `8089`
- Health: `GET http://localhost:8089/actuator/health`

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
docker compose up -d board-service
docker compose logs -f board-service
```