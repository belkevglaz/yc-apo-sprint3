```puml
@startuml DynamicDiagram
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Dynamic.puml
scale 0.85

!pragma layout smetana

AddElementTag("queue", $shape=RoundedBoxShape(), $bgColor="#b6ccde", $fontColor="black", $legendText="event sourcing")
AddRelTag("async", $textColor=$ARROW_FONT_COLOR, $lineColor=$ARROW_COLOR, $lineStyle=DashedLine())

System_Ext(sensors, "Third-Party Sensors", "Endpoints for retrieve/set data", "Uses json / binary data")
System_Ext(control, "Third-Party Control Module", "Endpoints for retrieve/set data", "Uses rest,mqtt,bluetooth protocols")

Container(api_gateway, API Gateway, "Kong", "- Handles telemetry requests\n- Routes to telemetry service")
ContainerQueue(kafka, "Kafka", "kafka", "Event sourcing for aggregate and store telemetry events", "Uses kafka protocol", $tags = "queue")


Boundary(telemetry, "Microservice: Telemetry Handler") {
    Component(SensorsController, "Sensors Controller", "Java, Spring", "- Handles messages")
    Component(ServiceLayer, "Service Layer", "Java, Spring", "- Perform transformation\n- Remove unnecessary data\n- Convert values", "")
    Component(EventPublisher, "Event publisher", "Publish events to Kafka topic")
}


Rel(sensors, api_gateway, "Changes state", "")

Rel(api_gateway, SensorsController, "Routes event", "")
Rel(SensorsController, ServiceLayer, "Sends event to", "")
Rel(ServiceLayer, EventPublisher, "Sends prepared event to ", "")
Rel_Neighbor(EventPublisher, kafka, "Publish to", "", $tags="async")



Boundary(automation, "Microservice: Automation management") {
    Component(EventConsumer, "EventConsumer", "Java, Spring", "- Consumes event")
    Component(ScenarioService, "Scenario Service", "Java, Spring", "- Get scenario\- Get scenario template\n- Compile scenario template", "")
    Component(Launcher, "Launcher", "Java, Spring", "-Launch scenario\n- Save launch logs\n- Save launch result", "")
    Component(DeviceClient, "Device Service client", "Java, Spring", "- Find device\n- Calls control command" )
}
    

Rel_Neighbor(kafka, EventConsumer, "Consumes to", $tags="async")
Rel(EventConsumer, ScenarioService, "Sends to")
Rel(ScenarioService, Launcher, "Launches scenario")
Rel(Launcher, DeviceClient, "Calls to")

Boundary(devices, "Microservice: Device management") {
    Component(CommandController, "Command Controller", "Java, Spring","- Handles command\n- Transfer to execute")
    Component(CommandExecutor, "Command Executor", "Java, Spring","- Send commands or new state")
}


Rel_Neighbor(DeviceClient, CommandController, "Calls to")
Rel(CommandController, CommandExecutor, "Calls to")
Rel(CommandExecutor, control, "Sets state to")

@enduml

```
