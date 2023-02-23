# Nike.com Stock Checker

A web application that enables users to track the stock status of chosen items from nike.com, and sends a desktop notification when the item is in stock. Users add an item to the SQL database by inputting its URL along with the desired item size, returning the stock status and the image of the desired item. The app conducts automated fetch requests of the item stock status every 10 minutes from the nike.com website. Users can further edit item details, delete items and opt in/out of desktop notifications.

The web app was built using Spring for the RESTful backend and Angular for the Javascript frontend. The Selenium WebDriver Java library is utilised to parse the site HTML to fetch item information. The app is adapted for the use of a SQL database, to which CRUD operations are performed.

## Requirements
