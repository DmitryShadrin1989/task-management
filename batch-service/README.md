# Batch Service

Сервис пакетной обработки и вспомогательных shell-операций.

## Назначение

- запуск batch-процессов;
- инициализация/подготовка данных;
- альтернативный интерфейс через Spring Shell.

## Технологии

- Spring Boot
- Spring Batch
- Spring Shell
- Spring Data MongoDB
- Spring Cloud Config Client
- Eureka Client

## Порт и healthcheck

- Порт: `8090`
- Health: `GET http://localhost:8090/actuator/health`

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
docker compose up -d batch-service
docker compose logs -f batch-service
```
