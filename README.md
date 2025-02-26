# SpaceX Dragon Rockets

A simple Java project called "Dragons". This project is built using Maven and Java 17, with JUnit 5 for testing.

## Building the Project

In order to build the project, execute the following command:

```bash
mvn clean package
```
## Running the Application
Once the JAR file is built, you can run the jar by executing the following command:

```bash
java -jar target/dragons.jar
```
This will execute example main method in the project. 
If you want to use the project just as library (i.e., no main method), you can use `JAR` correctly in other projects.

## Assumptions
1. In the specification, mission `Luna 1` has a status of `Pending`, but all rockets are listed as `On ground`, which conflicts with the requirement that at least one rocket should be `In repair`.  
   I made the rocket `Dragons2` in repair to pass all validations in the code.
2. The project contains a few tech debts that need to be fixed due to time limitations:

   
   2.1. Enums should have values. For instance, `MissionStatus.IN_PROGRESS` should be `'In progress'`, etc.  <br>
   2.2. Implement independent classes for validators. Now, all validators are private methods in `DragonsRepositoryController`. <br>
   2.3. `DragonsMain` is only for example how to use library in third projects. <br>

