# Gateway Service

API Gateway системы.

## Назначение

- единая точка входа для клиентских запросов;
- маршрутизация запросов к внутренним сервисам;
- базовый security-фильтр и инфраструктурная обвязка.

## Технологии

- Spring Boot
- Spring Cloud Gateway (MVC)
- Spring Security
- Spring Cloud Config Client
- Eureka Client

## Порт и healthcheck

- Порт: `8080`
- Health: `GET http://localhost:8080/actuator/health`

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
docker compose up -d gateway-service
docker compose logs -f gateway-service
```