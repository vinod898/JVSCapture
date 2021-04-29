# JVSCapture
A Java app, part of VSCapture software, to download or capture data from Philips Intellivue monitors.
The software currently uses the UDP/IP protocol via  RS232 protocol via MIB port on the monitor for data logging.

## Prerequisites
Make sure you have installed all of the following prerequisites on your development machine:
* JAVA 1.8 (32 bit) - [Download & Install JAVA(32 bit)](https://www.oracle.com/in/java/technologies/javase-jre8-downloads.html).
* Maven - [Download & Install Maven](https://maven.apache.org/install.html).


## Table of content

- [Install](#Install)
    - [Windows](#Windows)
    - [Linux](#Linux)
    - [MacOs](#MacOs)
    
- [Build](#build)
   
- [Run Project](#run-Project)
- [Links](#links)

## Install

### Windows

If you want to set up JVSCapture.clone the code from git (or) download and unzip the project.Before running the project make sure you are installed Java1.8(32 bit).code won't work for Java 64 bit in Windows.
<br><br>
add java library to project build path and configure comm.jar and .dll files under the Java directory as follows.These files are provided in the ` <project_path>/lib `  directory.
<br>
 1. Install 32bit JDK.
 2. copy 'win32com.dll' to JDK_HOME\jre\bin.
 3. copy 'javax.comm.properties'to to JDK_HOME\jre\lib.
 4. copy 'comm. jar' to JDK_HOME\jre\lib\ext.
### Linux
`    `
### MacOs
`   `
## build

Build the Project using the Maven.
`mvn clean package`

## Run Project
Run the project using the below command.<br>
``java -jar target\JVSCapture.jar -mode [number] -port [port name] -interval [number] -waveset [number] -export [number] -scale [number]``
<br><br>
Eg :- `java -jar target\JVSCapture.jar -mode 1 -port COM3 -interval 2 -waveset 8 -export 1 -scale 2`
<br><br>
Run below command for Help <br> `java -jar target\JVSCapture.jar -help`
<br><br>

## Links
[Download & Install JAVA(32 bit)](https://www.oracle.com/in/java/technologies/javase-jre8-downloads.html).<br>
[Download & Install Maven](https://maven.apache.org/install.html).
