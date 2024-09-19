```puml
@startuml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml
'!include ../templates/C4_Component.puml

title Component diagram for Smart Home System - Telemetry Handler

'skinparam linetype ortho

AddElementTag("queue", $shape=RoundedBoxShape(), $bgColor="#b6ccde", $fontColor="white", $legendText="event sourcing")

Container_Boundary(SmartHomeSystem, "SmartHome System") {
    Container(ms_telemetry, "Telemetry Handler", "Java, Quarkus", "Handles external devices messages")
    ContainerQueue(kafka, "Kafka", "kafka", "Event sourcing for aggregate and store telemetry events", $tags = "queue")
}

Container(ms_telemetry, "", "Java, Quarkus") {
    Component(ModulesController, "ModulesController", "Handles messages from modules")
    Component(SensorsController, "SensorsController", "Handles messages directly from sensors")
    Component(ServiceLayer, "Service Layer", "Transformation messages business logic")
    Component(EventPublisher, "Event publisher", "Publish events to Kafka topic")
}

Rel(ModulesController,ServiceLayer,"Calls transform logic")
Rel(SensorsController,ServiceLayer,"Calls transform logic")
Rel(ServiceLayer,EventPublisher,"Reads/Writes data")
Rel(EventPublisher, kafka, "Reads/Writes message data")

SHOW_LEGEND()

@enduml
```