```puml
@startuml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml
'!include ../templates/C4_Component.puml

title Component diagram for Smart Home System - User management

Container_Boundary(SmartHomeSystem, "SmartHome System") {
    Container(ms_users, "User management", "Java, Spring", "Handles user information")
    ContainerDb(users_db, "User database", "PostgreSQL", "Stores users  information")
}

Container(ms_users, "User management", "Java, Spring") {
    Component(AuthController, "AuthController", "Handles authentication and authorization")
    Component(UserController, "UserController", "Manages user profiles")
    Component(ServiceLayer, "Service Layer", "Business logic")
    Component(RepositoryLayer, "Repository Layer", "Data access logic")
}

Rel(AuthController,ServiceLayer,"Calls business logic")
Rel(UserController,ServiceLayer,"Calls business logic")
Rel(ServiceLayer,RepositoryLayer,"Reads/Writes data")
Rel(RepositoryLayer,users_db,"Reads/Writes user data")

SHOW_LEGEND()

@enduml
```