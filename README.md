# Cucumber Market Backend Development
 

## Introduction
This is a serverside project of Cucumber Market.<br>
 

## Prerequisites
Before you continue, ensure you have met the following requirements:
* You have installed at least the version of Java 8 or later.
* You are using a Linux or Mac OS or Widnows machine.
* You don't need to have a basic understanding of Spring.

## How to install and run #1
> $ git clone https://github.com/TheSynergyTEAM/cucumberserver.git <br>
> $ cd ~/cucumbersever <br>
> $ java -jar cucumbermarket-spring-0.0.1s-SNAPSHOT.jar
> 

## How to run #2
<em>This description is based on using IntelliJ IDEA</em>
1) install IntelliJ
   https://www.jetbrains.com/idea/download/
2) run application with build.gradle
   ![](https://images.velog.io/images/hyungjungoo95/post/4bdf3ccf-2722-4a72-89cf-bb392e005f24/image.png)
   1) File -> Open -> cucumberserver
   2) select build.gradle
   3) open
3) Go to **cucumberserver/src/main/java/cucumber/market/cucumbermarketspring/**
![](https://images.velog.io/images/hyungjungoo95/post/6f6637ec-e5d4-41a8-83cd-dc3b9c97f4c5/image.png)

4) Run Application

## H2 Database settings (Option)
Go to **cucumberserver/src/main/resources/application.yml**
![](https://images.velog.io/images/hyungjungoo95/post/d2b2e05d-5639-4f8e-8a26-41065a007901/image.png)

- **none**: No change to the database structure.
- **update**: Hibernate changes the database according to the given Entity structures. - 변경된 스키마만 적용한다.
- **validate**: 변경된 스키마가 있는지 확인만 한다. 만약 변경이 있다면 Application을 종료한다.
- **create**: Creates the database every time, but don’t drop it when close. - 시작될 때만 drop하고 다시 생성한다.
- **create-drop**: Creates the database then drops it when the SessionFactory closes. - 시작과 종료에서 모두 drop한다.

## Application

### Dependencies
Todo
### MVC
Todo