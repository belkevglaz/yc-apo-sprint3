```puml
@startuml 
'!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml
!include ../templates/C4_Container.puml

title Smart Home Container Diagram
top to bottom direction

AddBoundaryTag(dash, $borderStyle=DottedLine(), $shape=RoundedBoxShape(), $borderColor="#b8b8b8")
AddBoundaryTag(solidGrey, $borderColor="#fafafa")

AddElementTag("microservice", $shape=RoundedBoxShape(), $bgColor="CornflowerBlue", $fontColor="white", $legendText="microservice")
AddElementTag("storage", $shape=RoundedBoxShape(), $bgColor="lightSkyBlue", $fontColor="white", $legendText="storage")
AddElementTag("queue", $shape=RoundedBoxShape(), $bgColor="#b6ccde", $fontColor="white", $legendText="event sourcing")

AddRelTag("async", $textColor=$ARROW_FONT_COLOR, $lineColor=$ARROW_COLOR, $lineStyle=DashedLine())

SHOW_PERSON_OUTLINE()
skinparam linetype polyline
'skinparam linetype ortho

Boundary(c0, "", "") {

    Person(customer, "Customer", "A user of the system")
    Person(admin, "Administrator", "An administrator managing the system")
    
    Boundary(c2, "SmartHome") {
    
        Boundary(b1, "", "", $tags = "dash") {
            Boundary(b11, "", "", $tags = "solidGrey") {
                Container(ms_users, "User management", "Java, Spring", "User management", $tags = "microservice")
                ContainerDb(users_db, "User database", "PostgreSQL", "Stores users  information", $tags = "storage")    
            }
            Boundary(b12, "", "", $tags = "solidGrey") {
                Container(ms_houses, "Houses and settlements management", "Java, Spring", "Locations management", $tags = "microservice")
                ContainerDb(houses_db, "Locations database", "PostgreSQL", "Stores locations information", $tags = "storage")
            }    
        }
        
        Boundary(b2, "", "", $tags = "dash") {
            Container(ms_telemetry, "Telemetry handler", "Java, Spring", "Receives and stores telemetry", $tags = "microservice")
            Container(ms_history, "Telemetry reports", "Java, Spring", "Provides telemetry history", $tags = "microservice")
            ContainerQueue(kafka, "Kafka", "kafka", "Event sourcing for aggregate and store telemetry events", "Uses kafka protocol", $tags = "queue")
        }
        
        Boundary(b3, "", "", $tags = "dash") {
            Boundary(b31, "", "", $tags = "solidGrey") {
                Container(ms_devices, "Devices management", "Java, Spring", "Manages devices", $tags = "microservice")
                ContainerDb(devices_db, "Devices database", "PostgreSQL", "Stores devices and modules information", $tags = "storage")
            }
            Boundary(b32, "", "", $tags = "solidGrey") {
                Container(ms_automation, "Automation management", "Java, Spring", "Manages & runs automation scenarios", $tags = "microservice")
                ContainerDb(automation_db, "Automation database", "PostgreSQL", "Stores scenarios, schedulers, launch results", $tags = "storage")
            }
        }
    
        Container(api_gateway, API Gateway, "Kong", "Entry point for all requests to system")
        
    }
    
    System_Ext(sensors, "Third-Party Sensors", "Endpoints for retrieve/set data", "Uses mqtt,bluetooth protocols")
    System_Ext(control, "Third-Party Control Module", "Endpoints for retrieve/set data", "Uses rest,mqtt,bluetooth protocols")
    
    Rel(customer, api_gateway, "Uses the system", "JSON/HTTP")
    Rel(admin, api_gateway, "Manages the system", "JSON/HTTP")
    
    Rel(sensors, api_gateway, "Sends telemetry", "HTTP")
    Rel(control, api_gateway, "Sends telemetry", "JSON")
    Rel(sensors, control, "Sends telemetry", "zigbee/bluetooth")
    
    Rel(api_gateway, ms_users, "Uses user data", "JSON")
    Rel(api_gateway, ms_houses, "Uses locations", "JSON")
    Rel(api_gateway, ms_telemetry, "Uses telemetry", "JSON")
    Rel(api_gateway, ms_history, "Uses history", "JSON/HTTP")
    Rel(api_gateway, ms_devices, "Uses devices", "JSON")
    Rel(api_gateway, ms_automation, "Uses automation", "JSON/HTTP")
    
    Rel(ms_users, users_db, "Reads/writes users", "SQL")
    Rel(ms_houses, houses_db, "Reads/writes locations", "SQL")
    Rel(ms_automation, automation_db, "Reads/writes automations", "SQL")
    Rel(ms_devices, devices_db, "Reads/writes devices", "SQL")
    
    Rel(ms_users, ms_houses, "Reads/writes user's membership", "JSON")
    Rel(ms_telemetry, kafka, "Writes telemetry", "kafka")
    Rel(ms_history, kafka, "Reads telemetry streams", "kafka")
    Rel(kafka, ms_automation, "Subscribe to", "event", $tags = "async")
    Rel(ms_automation, ms_devices, "Reads devices info", "JSON")
    Rel(ms_devices, sensors, "Manages sensor's states", "JSON/HTTP")
    Rel(ms_devices, control, "Manages module's states", "HTTP")
    Rel(kafka, ms_devices, "Subscribe to", "event", $tags = "async")


}


SHOW_LEGEND()
@enduml
```