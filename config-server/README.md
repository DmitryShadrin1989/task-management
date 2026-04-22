# Config Server

Централизованный сервер конфигураций для всех микросервисов.

## Назначение

- выдача конфигурации сервисам при старте и во время работы;
- единая точка управления application-конфигами;
- интеграция с удаленным конфигурационным репозиторием.

## Технологии

- Spring Boot
- Spring Cloud Config Server

## Порт и healthcheck

- Порт: `8888`
- Health: `GET http://localhost:8888/actuator/health`

## Зависимости при запуске

- внешнее подключение к конфигурационному Git-репозиторию (если настроено);
- другие сервисы зависят от `config-server`.

## Локальная сборка

```bash
mvn -DskipTests clean package
```

## Запуск в Docker Compose

Из корня репозитория:

```bash
docker compose up -d config-server
docker compose logs -f config-server
```