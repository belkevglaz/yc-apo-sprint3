@startuml
title FitLife SmartHouse Code Diagram

top to bottom direction

!include ../templates/C4_Code.puml

class Role {
+String role
+String description
}

class User {
+String name
+String email
+List<Role> roles
+void register()
+void login()
}

User "1" -- "0..*" Role : include

class Settlement {
+String name
+String description
+List<House> houses
+boolean create()
+boolean addHouse()
}
Settlement "1" -- "0..*" House : has

class House {
+String name
+Settlement settlement
+List<Users> users
+boolean create()
+boolean addUser()
}
House "1" -- "1..*" User : contains

'---

class Telemetry {
+String deviceId
+String moduleId
+Date createdAt
+Device createdBy
+String payload
}

'--- Device

abstract class Device {
+String serialNumber
+String payload
+String description
}

enum DeviceType {
+String id
+String name
}

class Module<T> {
+String id
+DeviceType type
+List<Device> devices
+House house
+T state
+boolean register()
+void remove()
+void updateDevice(Device)
}
Module --|> Device
Module "1..*" -- "1" DeviceType : defines
Module "1" -- "1..*" Sensor : contains
House "1" -- "1..*" Module : contains

class Sensor<T> {
+String id
+DeviceType type
+House house
+T state
+boolean register()
+boolean remove()
}
Sensor --|> Device
Sensor "1..*" -- "1" DeviceType : defines
House "1" -- "1..*" Sensor : contains

'--- Automation

class ScenarioTemplate {
+String template
}

interface Scenario <T> {
+T run()
+void stop()
}

abstract class AbstractScenario<T> {
+Date startTimestamp
+Date endTimestamp
+User createdBy
+Date lastStart
+boolean lastRun
+T run()
+void stop()
}
AbstractScenario --|> Scenario : implements

class RunnableScenario {
+String id
+List<Device> devices
+ScenarioTemplate template
void run(Event)
}
RunnableScenario --|> AbstractScenario : inherits
RunnableScenario "1" -- "1..*" ScenarioTemplate : has

class ScheduledScenario {
+String id
+List<Device> devices
+Date startDatetime
+ScenarioTemplate template
+T run()
}
ScheduledScenario --|> AbstractScenario : inherits
ScheduledScenario "1" -- "1..*" ScenarioTemplate : has



@enduml