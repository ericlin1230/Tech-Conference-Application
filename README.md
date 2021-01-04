# Tech Conference Application

Tech Conference Application is a project for CSC207 Software Design course at University of Toronto during the Fall 2020 Semester. The application allows users to sign up for accounts as different user types such as Organizer, Speaker, User, VIP. Each user types contain different functions of their own. Organizers are able to create and manage events and rooms, speakers are able to speak at different events, users and VIPs are able to sign up at different events. Every user has the ability to message and add other users to their contacts. The data is stored locally and PDF output of the event schedule and room is available.

## Main Contributions:
* Eric Lin: erichungyu.lin@mail.utoronto.ca
  * Event functionality and program compilation
* Haoying Shen: haoying.shen@mail.utoronto.ca
  * User functionality
* Ryan Wang: ryanjun.wang@mail.utoronto.ca
  * Messaging/Request functionality
* Temilade Adeleye: temilade.adeleye@mail.utoronto.ca
  * Data saving features
* Zihao Shen: zihao.sheng@mail.utoronto.ca
  * Room functionality and UI design

## Documentation

Documentation can be accessed through the gh-pages branch or at https://restia1230.github.io/Tech-Conference-Application/

## Installation

How to install and run the program in IntelliJ.

Instructions to try out the functionality.

Input instructions are displayed within the program, this readme only contains the basics to running the program

### How to run the program:
1. To run the program, run the main class

2. For best results RESET the database the first time you run the program.


### Notes on user creation:
1. Username must be greater than 5 characters and cannot have a space

2. Speakers are made through an option in the organizer

3. The name and password can be blank if you wish


### Notes on creating events:
1. Can only be done by organizers

2. The default event begins at eventID: 1 and then it increases as you make more events,
(so the second event you make will have eventID: 2)

3. The default room a event takes place in is 1, when the program begins there is only a single room
to make more rooms use the add room function and then room 2 will be added and so forth.

4. Deleting events will not reset the eventID count, so should you delete event 1,
before making your second event, the second event will have eventID 2

5. Currently the event times begin at 9 and the latest an event can be added is 17 representing 5pm

6. In the phase 1 version the end time does not matter, as all events are considered 1 hour long regardless


### Notes on messaging:
1. Everyone is able to message everyone without restrictions, should you know the username of the other person

2. You are suggested certain recipients such as those on your contacts list, or attending/speaking at the same
event

3. Organizers can see all users and can mass message various groups


### Notes on how to add maven dependencies to run the ConvertToPDF.java class:
1. Add maven support
- Right-click your project and select Add Framework Support
- Select Maven from the options in the dialog and click OK

2. Open pom.xml

3. Copy and paste this in the pom.xml file:

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>groupId</groupId>
    <artifactId>group_0408</artifactId>
    <version>1.0-SNAPSHOT</version>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>7</source>
                    <target>7</target>
                </configuration>
            </plugin>
        </plugins>
    </build>


    <dependencies>
        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>kernel</artifactId>
            <version>7.0.0</version>
        </dependency>

        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>layout</artifactId>
            <version>7.0.0</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.18</version>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>

        </dependency>
    </dependencies>
</project>

4. Load the maven changes

5. The maven dependencies should have been added to the project by now,
but to confirm check the external libraries there should be maven external libraries.
