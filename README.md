# Restaurant Management System

The Restaurant Management System is a web application designed to streamline and optimize the process of placing orders in a restaurant. This system enables end-users to easily place orders through an intuitive website interface. All orders are then processed by the restaurant staff using an order management and processing interface.

Key Features:

* Real-time Order Placement: The "Order" functionality operates in real-time, meaning when a user places an order, it is instantly reflected in the list of orders for the restaurant staff. This allows staff to promptly respond to orders and ensures quick customer service.

* User-Friendly Order Interface: Users can easily select desired dishes and beverages from the available menu list. The interface allows adding the required quantity of items and finalizing orders with a convenient cart feature.

* Order Processing by Staff: Restaurant staff have access to an order management interface. They can track new orders, accept or reject them, and mark them as fulfilled. This facilitates effective communication between customers and staff.

* WebSocket Notifications: The system employs WebSocket notifications to send real-time alerts to staff about new orders. This helps ensure prompt order processing and enhances service quality.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites - Required software
* Java 17
* PostgreSQL

### Installing

Clone this repository to your local machine using:

```shell
git clone https://github.com/vasilpetrus/RestaurantManagementSystem
```
Create a database and set values in environment variables:
* DATABASE_URL
* DATABASE_USERNAME
* DATABASE_PASSWORD

Run application and open [Swagger](http://localhost:8080/swagger-ui/index.html#/)

## Web interfaces

The client part of the web application. Restaurant menu and order form:
* Main website [Restaurant menu](http://localhost:8080/)

Login to the web service for restaurant employees:
* Enter to the orders system [Orders](http://localhost:8080/login)
  

_Default login: admin password: admin_