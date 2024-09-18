```puml
@startuml DynamicDiagram
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Dynamic.puml
scale 0.85

!pragma layout smetana

AddElementTag("queue", $shape=RoundedBoxShape(), $bgColor="#b6ccde", $fontColor="black", $legendText="event sourcing")
AddElementTag("storage", $shape=RoundedBoxShape(), $bgColor="lightSkyBlue", $fontColor="white", $legendText="storage")
AddRelTag("async", $textColor=$ARROW_FONT_COLOR, $lineColor=$ARROW_COLOR, $lineStyle=DashedLine())

System_Ext(sensors, "Third-Party Sensors", "Endpoints for retrieve/set data", "Uses json / binary data")
System_Ext(control, "Third-Party Control Module/Sensor/Relay", "External hardware devices")

Container(api_gateway, API Gateway, "Kong", "- Handles telemetry requests\n- Routes to telemetry service")
ContainerQueue(kafka, "Kafka", "kafka", "Event sourcing for aggregate and store telemetry events", "Uses kafka protocol", $tags = "queue")

Boundary(telemetry, "Microservice: Telemetry Handler") {
    Component(SensorsController, "Telemetry Resource", "Java, Quarkus", "- Handles messages")
    Component(ServiceLayer, "Telemetry Service", "Java, Quarkus", "- Perform transformation\n- Remove unnecessary data\n- Convert values", "")
    Component(EventPublisher, "Kafka publisher", "Publish events to Kafka topic")
}
Boundary(automation, "Microservice: Automation management") {
    Component(EventConsumer, "Kafka Consumer", "Java, Quarkus", "- Consumes event")
    Component(ScenarioService, "Scenario Service", "Java, Quarkus", "- Get scenario\- Get scenario template\n- Compile scenario template", "")
    Component(Launcher, "Launcher", "Java, Quarkus", "-Launch scenario\n- Save launch logs\n- Save launch result", "")
    Component(DeviceClient, "Device Service client", "Java, Quarkus", "- Find device\n- Calls control command" )
    ContainerDb(automation_db, "Automation database", "PostgreSQL", "", $tags = "storage")
}
Boundary(devices, "Microservice: Device management") {
    Component(DeviceResource, "Sensor Resource", "Java, Quarkus", "- Handled sensors registration\n- Handles sensors link\n- Handles sensors management")
    Component(CommandController, "Command Controller", "Java, Quarkus","- Handles command\n- Transfer to execute")
    Component(CommandExecutor, "Command Executor", "Java, Quarkus","- Send commands or new state")
    ContainerDb(devices_db, "Devices database", "PostgreSQL", "Stores devices and modules information", $tags = "storage")
}

Rel(sensors, api_gateway, "Changes state", "")
' telemetry
Rel(api_gateway, SensorsController, "Routes event", "")
Rel(SensorsController, ServiceLayer, "Sends event to", "")
Rel_Neighbor(ServiceLayer, DeviceResource, "Validate sensor", "")
Rel(DeviceResource, devices_db, "Get sensor info", "")
Rel(ServiceLayer, EventPublisher, "Sends prepared event to ", "")
Rel_Neighbor(EventPublisher, kafka, "Publish to", "", $tags="async")

' automation
Rel_Neighbor(kafka, EventConsumer, "Consumes to", $tags="async")
Rel(EventConsumer, ScenarioService, "Sends to")
Rel(ScenarioService, automation_db, "Fetch scenario", "")
Rel(ScenarioService, Launcher, "Launches scenario")
Rel(Launcher, DeviceClient, "Calls to")

Rel_Neighbor(DeviceClient, CommandController, "Calls to")
Rel(CommandController, CommandExecutor, "Calls to")
Rel(CommandExecutor, control, "Sets state to")

@enduml

```
