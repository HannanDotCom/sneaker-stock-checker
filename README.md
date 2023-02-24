# Sneaker Stock Checker

## Use Case

The proliferation of 'sneaker culture' has led to a rise in consumer interest in sneakers from major brands such as Nike, Adidas etc. The build up of 'hype' behind the release of new styles and colourways has meant that certain designs sell out within minutes. This app is designed to counter this problem by offering a way for users to be automatically updated when their desired shoe comes back into stock with a desktop notification.

## Description

A web application that enables users to track the stock status of chosen items from nike.com, and sends a desktop notification when the item is in stock. Users add an item to the SQL database by inputting its URL along with the desired item size, returning the stock status and the image of the desired item. The app conducts automated fetch requests of the item stock status every 10 minutes from the nike.com website. Users can further edit item details, delete items and opt in/out of desktop notifications.

The web app was built using Spring for the RESTful backend and Angular for the Javascript frontend. The Selenium WebDriver Java library is utilised to parse the site HTML to fetch item information. The app is adapted for the use of a SQL database, to which CRUD operations are performed.

## Video Demo

https://user-images.githubusercontent.com/93732140/221067363-998b58f1-88ce-44ec-a532-d5836a150894.mp4


## Requirements
Java 17 

MySQL server instance running in the background - server properties should be configured in:
```
nike-stock-checker/spring-backend/src/main/resources/application.properties
```
Maven 3.9+ for package dependencies

Google Chrome browser and ChromeDriver installed

**NOTE**
The file location of ChromeDriver must be added to the Windows PATH variable for Selenium to work.

Pre-installation of node.js and its packages is unnecessary as this is conducted during build time in the next step.

## How to run
1) Within the root folder, in cmd run:
```
mvn clean install
```

This should create a .jar file within the newly created `spring-backend/target`.

2) Go to `spring-backend/target` and execute the .jar file in cmd.

3) On your web browser, visit the following URL to access the application:
```
https://localhost:8080
```

## Future updates
- Extend to other sneaker brand websites
- User authentication and verification

## Acknowledgements 

This project and the creator are not affiliated, associated, authorized, endorsed by, or in any way officially connected with Nike or nike.com.
All names, marks, emblems and images are registered trademarks of their respective owners.
