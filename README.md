# HedgehogOps

Микросервисная система для управления задачами, пользователями, досками и комментариями (продукт **HedgehogOps**).
Сервисы регистрируются в Eureka, конфигурация централизована через Config Server, межсервисное взаимодействие выполняется по HTTP/Feign.

## Состав проекта

- `config-server` — централизованная конфигурация.
- `discovery-server` — service discovery (Eureka).
- `gateway-service` — единая точка входа и маршрутизация.
- `page-service` — web UI.
- `user-service` — управление пользователями.
- `task-service` — управление задачами.
- `board-service` — управление досками.
- `comment-service` — управление комментариями.
- `batch-service` — batch/shell-операции и инициализация данных.
- `mongo` + `mongo-express` — хранилище и UI для MongoDB.

## Требования

- Docker + Docker Compose.
- Для локальной сборки сервисов без Docker: JDK 17.
  - Во всех сервисах включен `maven-enforcer-plugin`, сборка на другом JDK будет остановлена.

## Быстрый старт (рекомендуется)

Из корня репозитория:

```bash
docker compose build --no-cache
docker compose up -d
docker compose ps
```

Остановка:

```bash
docker compose down --remove-orphans
```

## Полезные команды

Логи конкретного сервиса:

```bash
docker compose logs -f comment-service
```

Проверка health:

```bash
curl http://localhost:8888/actuator/health   # config-server
curl http://localhost:8761/actuator/health   # discovery-server
curl http://localhost:8080/actuator/health   # gateway-service
```

## Порты сервисов

- `8080` — `gateway-service`
- `8081` — `mongo-express`
- `8085` — `page-service`
- `8086` — `user-service`
- `8087` — `task-service`
- `8088` — `comment-service`
- `8089` — `board-service`
- `8090` — `batch-service`
- `8761` — `discovery-server`
- `8888` — `config-server`
- `27017` — `mongo`

## Локальная сборка без Docker

Если нужно собрать JAR вручную:

```bash
cd <service-directory>
mvn -DskipTests clean package
```

Для сервисов с Maven Wrapper можно использовать `./mvnw`.

## Контакты

Вопросы и предложения: `dmitry.shadrin.alex1989@gmail.com`.
