# Nike.com Stock Checker

A web application that enables users to track the stock status of chosen items from nike.com, and sends a desktop notification when the item is in stock. Users add an item to the SQL database by inputting its URL along with the desired item size, returning the stock status and the image of the desired item. The app conducts automated fetch requests of the item stock status every 10 minutes from the nike.com website. Users can further edit item details, delete items and opt in/out of desktop notifications.

The web app was built using Spring for the RESTful backend and Angular for the Javascript frontend. The Selenium WebDriver Java library is utilised to parse the site HTML to fetch item information. The app is adapted for the use of a SQL database, to which CRUD operations are performed.

## Requirements
Java 17 
MySQL server instance running in the background - server properties should be configured in:
```
nike-stock-checker/spring-backend/src/main/resources/application.properties
```
Maven 3.9+ for package dependencies

## How to run
1) Within the root folder, in cmd run:
```
mvn clean install
```
This should create a .jar file within the `spring-backend` folder.

2) Go to the `spring-backend` folder and execute the .jar file in cmd.

3) On your web browser, visit the following URL to access the application:
```
https://localhost:8080
```
