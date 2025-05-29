# TrackerWebApp
TrackerWebApp is a comprehensive tool designed for health enthusiasts like myself who appreciate the importance of tracking daily nutrient intake. This application provides detailed information about a wide range of nutrients, including daily intake recommendations and which foods are rich in specific nutrients. 

The application also includes a feature to track your personal achievements and progress over time. 

## Features

- Nutrition Information Provider:
This feature offers comprehensive information about a wide array of nutrients. It includes detailed descriptions, daily intake recommendations, and statistics on which foods are particularly rich in specific nutrients. This allows users to gain a thorough understanding of their nutritional intake and make informed dietary choices.

- Nutrition Tracker:
This feature allows you to track your daily nutrient intake, similar to MyFitnessPal. It also provides additional statistics for a more detailed view of your nutrition.

- Achievement Tracker:
This versatile feature enables you to track any goal with a measurable target. For instance, if your goal is to swim 5 km, you can easily log and track this. The Achievement Tracker provides detailed daily, weekly, monthly, and yearly progress statistics, making it simple to monitor your progress towards any goal.

For visual details about the application, please refer to the images folder in this repository where you'll find screenshots showcasing various features of the application.

## Project Technology Stack

### Front-End
- **Library**: React (18.2.0)
- **Routing and State Management**: React Router DOM (6.18.0)
- **HTTP Client**: Axios
- **Visualization**: D3.js
- **Database Interaction**: MongoDB (via client libraries)

### Back-End
- **Language**: Java 17
- **Architecture**: Microservices
- **Build Tool**: Maven
- **Framework**: Spring Boot (3.1.4)
- **Cloud Framework**: Spring Cloud (2022.0.3)
- **Database**: MySQL
- **Database Migration**: Flyway
- **Messaging**: Spring AMQP
- **Monitoring and Tracing**: Micrometer, Prometheus, Zipkin

## API Reference

The API is fully documented with Postman and Swagger:

- Swagger Documentation: The API also includes Swagger documentation, which provides an interactive interface for exploring the API. You can access the Swagger UI at `/swagger-ui.html` endpoint when you run the application. 
**Note:** There were some issues with the Swagger UI. The exception message inaccurately states that the app throws a stack trace, which is not the case. Due to additional problems with Swagger, it is recommended to use Postman instead.

## Installation

The application is containerized using Docker and can be easily set up with Docker Compose. Follow these steps to get it running:

1. Run the `docker-compose` file. This will automatically download the necessary Docker images from the project's Docker repository and start the application.

Please ensure that you have Docker and Docker installed on your machine before proceeding.

2. Download the MongoDB data for all embedded foods from here: [Mongo Data](https://drive.google.com/file/d/1ET8DJ1abIAuj_iTWZOPZZZDvDodgK5rx/view?usp=sharing)

3. In the docker-compose file, on line 67, replace this line ("./runProjectConfig/mongo_food_data") with the path where you downloaded the MongoDB data.

**Note:** When you first start the application, it may take a while for the Eureka server and gateway to fetch all the servers. During this time, you may see an error message in the console, and the server may return a 503 error. This is normal and should resolve itself within about a minute.
