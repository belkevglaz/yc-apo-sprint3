```puml
@startuml

top to bottom direction

'!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml
!include ../templates/C4_Component.puml

Boundary(c1_strategic_design, "Smart Home Tactical Design") {

    Boundary(c1_location_domain, "Domain: Location management", "") {
        Container(c211, "Microservice: User management", "", "Authenticates user\nManage user's profile")
        Container(c212, "Microservice: Settlements and houses", "", "Manages settlements\n Manage houses and sharing between users")
    }
    
    Boundary(c1_telemetry_domain, "Domain: Telemetry management", "") {
        Container(c221, "Microservice: Telemetry handler", "", "Collects, transforms and stores telemetry")
        Container(c223, "Microservice: History and reports", "", "Produces history reports\n Provides audit reports")
    }
    
    Boundary(c1_device_domain, "Domain: Device management", "") {
        Container(c231, "Microservice: Manage automations & runs", "", "Provides possibility to create and run automations")
        Container(c233, "Microservice: Manage & control devices", "", "Performs devices registration\n Control device's states")
    }
}

SHOW_LEGEND()

@enduml
```
