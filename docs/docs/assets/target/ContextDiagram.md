```puml
@startuml

scale 0.8
top to bottom direction

!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml

SHOW_PERSON_OUTLINE()

Boundary(system, "Smart Home Context Diagram") {
    
    Boundary(interanl, "Smart Home") {
        System(smarthome, "SmartHome System", "System manages the heating and check the temperature")
    
        Person(customer, "Customer", "A user of the smart home system")
        Person(admin, "Administrator", "An administrator managing the system")
    
        Rel(customer, smarthome, "Uses the system", "JSON/HTTP")
        Rel(admin,smarthome,"Manages the system",  "JSON/HTTP")
   
    }

    System_Ext(sensors, "Third-Party Sensors", "Endpoints for retrieve/set data", "Uses json / binary data")
    System_Ext(control, "Third-Party Control Module", "Endpoints for retrieve/set data", "Uses json / binary data")
    
    Rel(control, smarthome, "Provides telemetry data", "JSON")
    Rel(sensors, control, "Operates/transfers states", "http,zigbee,bluetooth ")
    Rel(sensors, smarthome, "Operates sensor's states", "HTTP")
}

SHOW_LEGEND()
@enduml
```