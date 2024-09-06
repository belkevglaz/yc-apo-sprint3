```puml
@startuml
title SmartHome Context Diagram

top to bottom direction

!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Context.puml

Person(customer, "Customer", "A user of the smart home system")
Person(admin, "Administrator", "An administrator managing the system")

System(smarthome, "SmartHome System", "System manages the heating and check the temperature")

System_Ext(sensors, "Third-Party Sensors", "Endpoints for retrieve/set data", "Uses mqtt,bluetooth protocols")
System_Ext(control, "Third-Party Control Module", "Endpoints for retrieve/set data", "Uses rest,mqtt,bluetooth protocols")

Rel(customer, smarthome, "Uses the system")
Rel(admin,smarthome,"Manages the system")

Rel(control, smarthome, "Transfer data", "Uses kafka protocol")
Rel(sensors, control, "Transfer data", "Uses mqtt,bluetooth protocols")
Rel(smarthome,sensors,"Processes heating data")

@enduml
```