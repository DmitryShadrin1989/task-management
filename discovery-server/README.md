# Discovery Server

Сервис регистрации и обнаружения сервисов (Eureka).

## Назначение

- регистрация микросервисов;
- хранение и выдача информации о доступных инстансах;
- поддержка service-to-service коммуникаций через service discovery.

## Технологии

- Spring Boot
- Spring Cloud Netflix Eureka Server
- Spring Cloud Config Client

## Порт и healthcheck

- Порт: `8761`
- Health: `GET http://localhost:8761/actuator/health`
- UI Eureka: `http://localhost:8761`

## Зависимости при запуске

- `config-server` (обязателен).

## Локальная сборка

```bash
mvn -DskipTests clean package
```

## Запуск в Docker Compose

Из корня репозитория:

```bash
docker compose up -d discovery-server
docker compose logs -f discovery-server
```