# Baniola — Car Rental Web App

Baniola is a car rental platform built with Spring Boot. Customers browse available cars, book them, and manage their reservations. Admins approve or reject bookings from a separate panel.

## Features

- Customer registration and login
- Browse cars and check availability
- Create, edit, or cancel a reservation
- Admin panel to confirm or refuse bookings
- Responsive UI, works on mobile and desktop

## Stack

- **Backend:** Spring Boot, Spring MVC, Spring Data JPA
- **Database:** MySQL
- **Frontend:** HTML, CSS, JavaScript
- **Build:** Maven

## Getting Started

### Requirements

- Java 17+
- MySQL 8+
- Maven (or use the included `mvnw` wrapper)

### Setup

1. Clone the repo:
   ```bash
   git clone https://github.com/amine-smaali/BaniolaForCarRental.git
   cd BaniolaForCarRental
   ```

2. Create a MySQL database:
   ```sql
   CREATE DATABASE baniola;
   ```

3. Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/baniola
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. Run the app:
   ```bash
   ./mvnw spring-boot:run
   ```

5. Open `http://localhost:8080` in your browser.

## Project Structure

```
BaniolaForCarRental/
├── src/
│   ├── main/
│   │   ├── java/       # controllers, services, entities
│   │   └── resources/  # templates, static assets, application.properties
│   └── test/
├── .mvn/wrapper/
├── mvnw / mvnw.cmd
└── pom.xml
```
