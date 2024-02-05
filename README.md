# Dice Game Project

The following RestAPI was developed as part of the Java programming bootcamp at ITAcademy Barcelona.
<br><br>It's a simple Dice Game where two randomnly obtained dices must sum 7 in order to win.
<br><br>This API uses MySQL for persistence. On S0502_MultiDB you can find the same API using a combination of MongoDB and MySQL for persistence.

## Table Of Contents
1. [Requirements](#req)
2. [Functionality](#func)
3. [Usage](#use)
    1. [Data Persistance](#persistance)
       - [Database Configuration](#mysql)
4. [Author](#author)

## 1. Requirements <a name = "req"></a>
- Java Development Kit (JDK) 8 or higher
- MySQL database and JDBC Driver for persistance

## 2. Functionality <a name = "func"></a>
You can send requests using Postman (or any other). The following requests are supported:
-
   
## 3. Usage <a name = "use"></a>
Clone the repository to your local machine: 
```java Cloning repo
git clone https://github.com/SandyMrtss/S0502_MySQL.git
```
Before executing the program, read **3.i. Data persistance**.
<br><br>To execute the program, use a Java IDE (I used IntelliJ IDE). Can also be done from the command line as you would with any maven project. Upon running the program, follow the on-screen prompts to execute the program's various functionalitites.

### 3.i. Data persistance <a name = "persistance"></a>
#### Database Configuration <a name = "mysql"></a>
1. Ensure your MySQL server is running
2. You can either use the database in this repository or you can create your own.
> The SQL scripts needed to use the given database are located in 'src/main/resources'.
> <br>There you can find the scripts to create the database.
3. Update the **'application.properties'** file with your MySQL connection details.
<br><br>Example:
```
CONNECTION_URL=jdbc:mysql://localhost:3306/flowershop
USER=user
PASSWORD=password
```

## 4. Author <a name = "author"></a>
This project was developed by:
[@SandyMrtss](https://github.com/SandyMrtss)

