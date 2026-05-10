# Page Service

Frontend/Web слой системы **HedgehogOps**.

## Назначение

- пользовательский веб-интерфейс;
- отображение данных из backend-сервисов;
- orchestration запросов через инфраструктуру микросервисов.

## Технологии

- Spring Boot
- Spring Web
- Thymeleaf
- Spring Cloud Config Client
- Eureka Client

## Порт и healthcheck

- Порт: `8085`
- Health: `GET http://localhost:8085/actuator/health`

## Зависимости при запуске

- `mongo`
- `config-server`
- `discovery-server`

## Локальная сборка

```bash
mvn -DskipTests clean package
```

## Запуск в Docker Compose

Из корня репозитория:

```bash
docker compose up -d page-service
docker compose logs -f page-service
```