# Проектная работа по курсу "Архитектор ПО" 

## Спринт 3

### Часть 1. Документация

Документация: [docs](docs)

Сборка документации
```shell
sh ./docs/build.sh
```

Описание архитектуры и схемы доступны в mkdocs документации [доступен в браузере](http://127.0.0.1:8000/).

### Часть 2. Разработка MVP

В рамках практики реализации были разработаны 3 микросервиса, которые реализуют механизм взаимодействия сервисов,
изображенный на диаграмме [DynamicDiagram](docs/docs/assets/target/DynamicDiagram.md)

  
- Регистрация устройства (датчика). Мета информация по датчику сохраняется в БД.

```shell
## Регистрация датчика
curl -X POST --location "http://localhost:8081/api/v1/sensors" \
    -H "Content-Type: application/json" \
    -d '{
          "name": "Temperature Sensor 7V0QPVvR0K",
          "manufacturer": "Acara",
          "serialNumber": "cSghiUGYVN",
          "houseId": "Settl-1-House-1",
          "type": "sensor"
        }'
```

- Создание подписки на события по заданному дому. После создания подписки, создается слушатель kafka-топика для событий конкретного дома.

```shell
## Администратор создает подписку для данного дома
curl -X POST --location "http://localhost:8083/api/v1/subscription" \
    -H "Content-Type: application/json" \
    -d '{
          "houseId": "Settl-1-House-1"
        }'
```

- Пример отправки события телеметрии, пришедшего от датчика. Событие трансформируется и записывается в kafka-топик, 
    откуда будет вычитано соответсвующей подпиской.

```shell
## Датчик шлет событие
curl -X POST --location "http://localhost:8082/api/v1/telemetry" \
    -H "Content-Type: application/json" \
    -d '{
          "deviceId": "LtTSrViuQp",
          "moduleId": "MzsEJSlPvI",
          "value": "26.5"
        }'
```

В результате в сервисе `devices-service` происходит вызов командного ресурса, для изменения состояния удаленного датчика (эмуляция.)
При выполнении запросов в логах сервиса как результат, должны появиться сообщения, которые символизируют об отработке потока и получении 
сервисом `devices-serivce` управляющей команды на изменение состояния на основе события от датчика.
```
[ru.bel.ypa.res.ExecutorResource] (executor-thread-1) ⏭ Executing command: [SetTargetTemperature = 26.5] for device id [LtTSrViuQp]
```


Документация OpenAPI доступна на ресурсах сервисов.

[Device Management OpenAPI](http://localhost:8081/q/openapi)   
[Telemetry Handler OpenAPI](http://localhost:8082/q/openapi)   
[Automation Management OpenAPI](http://localhost:8083/q/openapi)   

Swagger-ui также доступен на ресурсах сервисов.

[Device Management Swagger](http://localhost:8081/q/swagger-ui/)  
[Telemetry Handler Swagger](http://localhost:8082/q/swagger-ui/)  
[Automation Management Swagger](http://localhost:8083/q/swagger-ui/)  