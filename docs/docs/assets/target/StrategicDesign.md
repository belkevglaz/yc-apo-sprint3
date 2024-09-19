```puml
@startuml
scale 0.7
top to bottom direction

!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml
'!include ../templates/C4_Component.puml

Boundary(c1_strategic_design, "Smart Home Strategic Design") {

    Boundary(c1_location_domain, "Location management", "Domain") {
        Container(c2_houses, "Houses management", "Context") {
            Component(c3_settl_management, "Settlements management", "Context")
            Component(c3_house_management, "Houses management")
        }
        Container(c2_user, "Users management", "Context") {
            Component(c3_user_management, "Manage users", "Context")
    '        Component(c3_user_profile, "Manage user profile", "Context")
        }
    }
    
    Boundary(c1_telemetry_domain, "Telemetry management", "Domain") {
        Container(c2_tele, "Collect telemetry", "Subdomain") {
            Component(c3_tele_store, "Collect and store telemetry data", "Context")
            Component(c3_tele_report, "History and reports", "Context")
            Component(c3_tele_notification, "Notify users about state changes", "Context")
        }
        
    }
    
    Boundary(c1_device_domain, "Device management", "Domain") {
        Container(c2_device, "Devices management", "Subdomain") {
            Component(c3_device_management, "Devices management", "Context")
            Component(c3_device_control, "Control devices", "Context")
        }
        Container(c2_automation, "Automation management", "Subdomain") {
            Component(c3_automation_management, "Scenario management & runs", "Context")
        }
    }
}

SHOW_LEGEND()

@enduml
```