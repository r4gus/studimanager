# Studimanager

A Course-Schedule/ productivity application written in Java.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.



### Prerequisites

What things you need to install the software and how to install them

1. Java version 11 or newer
2. JavaFx 12
3. Jackson library

##### 1. Java version 11.04 or later recommended

Under Ubuntu:
Use the following commands to install Open JDK
```
$ sudo apt update && sudo apt upgrade
$ sudo apt install default-jdk
$ java --version
```
If you wan't to install Oracle JDK 11 follow the below commands
```
$ sudo add-apt-repository ppa:linuxuprising/java
$ sudo apt update
$ sudo apt install oracle-java11-installer
```

---

##### 2. This application uses JavaFx 12
Download the appropriate version [here](https://gluonhq.com/products/javafx/).

---

##### 3. Jackson library
Visit [maven.org](https://search.maven.org/search?q=jackson-core), search for
**jackson-core** and download: __jackson-core__, __jackson-databind__ and
__jackson-annotations__ ( Group ID must be: **com.fasterxml.jackson.core**).

### Installing

A step by step series of examples that tell you how to get a development env running

##### IntelliJ
Open Intellij and create a new project:
```
New Project -> Project from Version Controll -> Git -> enter URL: https://github.com/r4gus/studimanager -> clone
```

###### 1. Add the required libraries
In the newly created project go to: __File -> Project Structure -> Libraries -> + -> java__
and enter:

```
/path/to/javafx-sdk-12.0.1/lib
```

Also add the following libraries the same way:

```
/home/sugar/Documents/jackson/jackson-annotations-2.10.0.pr1.jar
/home/sugar/Documents/jackson/jackson-core-2.10.0.pr1.jar
/home/sugar/Documents/jackson/jackson-databind-2.10.0.pr1.jar
```

---

###### 2. Add VM options (Required for JavaFx)
Go to __Run -> Edit Configurations -> Application -> Main__ and add the following to the __VM__ options:

__Linux/ Mac__
```
--module-path /path/to/javafx-sdk-12.0.1/lib --add-modules javafx.controls,javafx.fxml
```

__Windows__
```
--module-path "\path\to\javafx-sdk-12.0.2\lib" --add-modules javafx.controls,javafx.fxml
```

---

###### 3. Add Junit 5

Just let Intellij import Junit for you.


## Running the tests

##### IntelliJ
Every major Package should have an __mypackage.test__ folder.

Follow these instructions to run the automated tests:
```
Right Click on test -> Run Tests in 'mypackage.test'
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [JavaFx 12](https://rometools.github.io/rome/) - Used to build the GUI
* [Jackson](https://github.com/FasterXML/jackson) - Used for data processing

## Contributing

Under development.

## Versioning


## Authors

* **David Sugar** - *Initial work* - [r4gus](https://github.com/r4gus)
* **Lukas Mendel** - *Initial work* - [LukasMendel](https://github.com/LukasMendel)

## License


## Acknowledgments
* Big __thanks__ to the coffee machine that keeps me going (r4gus)

