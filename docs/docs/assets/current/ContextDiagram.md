```puml
@startuml
title SmartHome Context Diagram

top to bottom direction

!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Context.puml

Person(user, "User", "A user of the smart home system")
Person(admin, "Administrator", "An administrator managing the system")
System(smarthome, "SmartHome System", "System manages the heating and check the temperature")

System_Ext(sensors, "Third-Party Sensors", "Endpoints for retrieve/set data", "Uses HTTP requests")
Rel(user, smarthome, "Uses the system")
Rel(admin,smarthome,"Manages the system")
Rel(smarthome,sensors,"Processes heating data")

@enduml
```