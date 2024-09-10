@startuml
'!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml
!include ../templates/C4_Component.puml

title Component diagram for Smart Home System - Device Management

'skinparam linetype ortho

AddElementTag("queue", $shape=RoundedBoxShape(), $bgColor="#b6ccde", $fontColor="white", $legendText="event sourcing")
AddElementTag("storage", $shape=RoundedBoxShape(), $bgColor="lightSkyBlue", $fontColor="white", $legendText="storage")
AddRelTag("async", $textColor=$ARROW_FONT_COLOR, $lineColor=$ARROW_COLOR, $lineStyle=DashedLine())

Container_Boundary(SmartHomeSystem, "SmartHome System") {
    Container(DeviceManagement, "Device Management", "Java, Spring", "Processes device management")
    ContainerQueue(Kafka, "Kafka", "kafka", "Event souring for aggregate and store telemetry events", $tags = "queue")
    ContainerDb(DeviceDatabase, "Devices database", "PostgreSQL", "Stores devices and modules information", $tags = "storage")
}

Container(DeviceManagement, "", "Java, Spring") {
    Component(RegistrationController, "Registration Controller", "Handles requests to register devices")
    Component(RegistrationService, "Registration Service", "Handles registration business logic")
    Component(DeviceController, "Device Controller", "Handles messages from devices")
    Component(DeviceServiceLayer, "Device Service Layer", "Processes business logic")
    Component(RepositoryLayer, "Repository Layer", "Data access logic")
    Component(EventConsumer, "Event Consumer", "Subscribes to events")
}

Rel(RegistrationController, RegistrationService, "Calls register logic")
Rel(RegistrationService, RepositoryLayer, "Stores new devices")
Rel(DeviceController, DeviceServiceLayer, "Calls business logic")
Rel(EventConsumer, DeviceServiceLayer, "Calls business logic")
Rel(Kafka, EventConsumer, "Subscribe to events", $tags = "async")
Rel(DeviceServiceLayer, RepositoryLayer, "Call repository logic")
Rel(RepositoryLayer, DeviceDatabase, "Reads/Writes device's information")


SHOW_LEGEND()

@enduml