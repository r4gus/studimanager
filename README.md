# Studimanager

A Course-Schedule/ productivity application written in Java.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.



### Prerequisites

What things you need to install the software and how to install them

1. Java version 11 or newer
2. JavaFx 12
3. Jackson library
4. controlsfx

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

---

##### 4. Add controlsfx-11.0.0
Download __controlsfx-11.0.0__ from [here](https://github.com/controlsfx/controlsfx) and add it to the existing
libraries under __File -> Project Structure -> Libraries__.


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
/path/to/jackson/jackson-annotations-2.10.0.pr1.jar
/path/to/jackson/jackson-core-2.10.0.pr1.jar
/path/to/jackson/jackson-databind-2.10.0.pr1.jar
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

## Internationalization
This program supports multiple languages through __property__ files. All files can be found at __src.config.i18n.Resource
Bundle'TimetableResourceBundle'__. To add support for a new language follow these steps:

1. Copy and rename an existing file. The naming convention is __TimetableResourceBundle_xx_XX.properties__.
For example, to support Italian name the file: ```TimetableResourceBundle_it_IT.properties```
2. Translate all values of the key-value pairs depending on the language.
3. Add the __language-code__ to   ```src.config.Language```
4. Add a new case to the switch statement in ```Main.Main``` and don't forget the ```break```

### Example

The program should support Italian:
#### 1. Copy and rename an existing file:
```
TimetableResourceBundle_it_IT.porperties
```

#### 2. Translate values into Italian
```
True=true -> True=vera
...
```

#### 3. Add IT to src.config.Language
```
public enum Language {
    EN, DE, FR, IT
}
```

#### 4. Add new case to the switch statement in Main
```
bundle = ResourceBundle.getBundle("config.i18n.TimetableResourceBundle", new Locale("it", "IT"));
break;
```

## Deployment

### 1. Create a .jar file from the project
#### IntelliJ:
First create an artifact under **File, Project Structure, Artifacts, add, jar, from Modules with dependency** and select
**Main.Launcher** as the main class. After that add a copy of the javafx-sdk directory (**File, Poject Structure,
 Artifacts, Add Copy Of, Directory Content, path/to/javafx-sdk-12.0.1**). Finally build the artifact with (**Build,
 Build Artifact, studimanager, build/ rebuild**).
 
#### Others:
coming soon...

### 2. Add the required folders
Create a project folder and move the .jar file into it. Within the newly created folder add the following sub
directories:

1. resources (should contain: icons8-cancel-48.png, icons8-info-48.png and icons8-ok-48.png)
2. log (contains the log files later on)
3. files 
4. config (has to contain: user.properties and verbose.properties)

### 3. Run the program
Run the program with:
```
java -jar <program_name>.jar
```

### Errors
Java 11 and later may not contain specific libraries required for JavaFx. If that's the case you should get an
error like: **...No suitable pipeline found...** or **...glass library missing...**.

If you can't find a proper Java version to run the application, the only way to fix this issue is to obtain 
the required libraries and add them to Java.

#### Missing libraries may be:
##### under Linux (add them to java-sdk/lib)
libprism_es2.os, libprism_sw.so, libglass.so, libglassgtk3.so, libglassgtk2.so, libjavafx_font.so,
libjavafx_font_freetype.so, libjavafx_font_pango.so

##### under Windows (add them to java-sdk\bin)
prism_d3d.dll, prism_sw.dll, javafx_font.dll, glass.dll

If you're using IntelliJ ultimate, you should get those libraries from the java version that ships with the IDE
or just use that java version in the first place.


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

