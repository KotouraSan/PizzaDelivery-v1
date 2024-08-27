# Pizzeria Backend 🍕

This project is the backend for a pizzeria management system, developed using Spring Boot and Gradle. The application offers advanced functionalities for managing clients, orders, and pizzas, with a focus on implementing new and unique features beyond basic CRUD operations.

## Technologies Used

- **Java**: The primary programming language.
- **Spring Boot**: For building the backend application.
- **Gradle**: For managing project dependencies and build configurations.
- **PostgreSQL**: Used as the database system for storing all data.
- **Spring Security**: For securing API endpoints and managing user authentication.
- **Lombok**: To reduce boilerplate code by automatically generating getters, setters, constructors, etc.
- **JWT (JSON Web Token)**: For handling authentication and authorization via tokens.
- **Jakarta Transaction**: For managing database transactions.
- **Redis**: Implemented for caching to improve performance.

## Project Structure ⚙️

### Controllers

- **AuthController**: Handles authentication-related requests such as signing in and refreshing tokens.
- **ClientController**: Manages operations related to clients, including creating, updating, deleting, and retrieving client information.
- **OrderController**: Facilitates order creation, allowing clients to select pizzas and process their orders.
- **PizzaController**: Manages the creation and retrieval of pizzas in the system.

### Services

- **ClientService**: Contains business logic related to client management, including user authentication and token management.
- **OrderServiceImpl**: Handles the creation of orders, including calculating the total price and associating orders with clients.
- **PizzaServiceImpl**: Responsible for managing the retrieval and creation of pizzas.

### Entities

- **ClientEntity**: Represents a client in the system, containing personal details and associated orders.
- **OrderEntity**: Represents an order, containing selected pizzas and the total price.
- **PizzaEntity**: Represents a pizza, including its name, description, and price.

## Version v1 Updates

In version v1 of the project, the following updates were made:

- **Caching with Redis**: Added caching through Redis to improve application performance.
- **Code Improvements**: Refactored and improved the code for several requests.
- **Unit Testing**: Began studying and implementing unit tests, writing several tests for different classes.
- **Load Testing**: Conducted load tests using JMeter, with a load of 4500 users over 30 seconds. The total number of requests was 36,000, with an average request time of 26 milliseconds.

![image_2024-08-21_15-17-06](https://github.com/user-attachments/assets/71de1344-2f24-4b9b-b371-6c3d93bcc1ae)


## Installation

To set up this project locally:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/pizzeria-backend.git
   cd pizzeria-backend
   ```

2. **Set up the database**:
   - Install PostgreSQL and create a database for the project.
   - Configure your `application.yaml` file with your PostgreSQL credentials.

3. **Build the project**:
   ```bash
   ./gradlew build
   ```

4. **Run the project**:
   ```bash
   ./gradlew bootRun
   ```

5. **Access the API**:
   - The API will be accessible at `http://localhost:8080/api/v1/`.


## Usage

- **Authentication**: Use the `/api/v1/auth/sign-in` endpoint to authenticate and obtain a JWT token.
- **Client Management**: Use the endpoints under `/api/v1/client/` to manage client data.
- **Order Management**: Use the `/api/v1/orders/makeOrder` endpoint to create new orders.
- **Pizza Management**: Use the `/api/v1/pizza/newPizza` endpoint to add new pizzas to the system.

## Troubleshooting 🛠️

**Possible Issues with `@Mapping` Annotations**

If you encounter errors related to the following lines in `ClientMapper` or `PizzaMapper`:

```java
@Mapping(source = "password", target = "password")
@Mapping(source = "name", target = "name")
```

or if you see an error like `rawPassword is null`, follow these steps:

1. **Remove the problematic lines**:
   - Temporarily delete these lines from the respective mapper files.

2. **Run the application**:
   - Start your application and wait for it to fully load.

3. **Reinsert the lines**:
   - Once the application is running, reinsert the removed lines back into the mapper files.

4. **Restart the application**:
   - Finally, restart the application.

This process should help resolve the issues related to these annotations.

## Postman Request Examples 📮

### 1. Registering a New User

To register a new user, send a POST request to:

`http://localhost:8080/api/v1/client/newClient`

**Request Body:**

```json
{
    "firstName": "{{$randomFirstName}}",
    "lastName": "{{$randomLastName}}",
    "phoneNumber": 1,
    "address": "{{$randomStreetAddress}}",
    "username": "first",
    "password": "first",
    "role": "ROLE_ADMIN"
}
```

### 2. Obtaining a JWT Token

To obtain a JWT token, send a POST request to:

`http://localhost:8080/api/v1/auth/sign-in`

**Request Body:**

```json
{
    "username": "first",
    "password": "first"
}
```

### 3. Creating a New Pizza

To create a new pizza, send a POST request to:

`http://localhost:8080/api/v1/pizza/newPizza`

**Request Body:**

```json
{
    "name": "Pepperoni",
    "description": "Традиционная итальянская пицца «Пепперони» — это сбалансированное сочетание тоненькой лепешки с кисло-сладким соусом и остренькой колбаской пепперони.",
    "price": 100
}
```

### 4. Creating an Order with Authorization

To create an order for a user by adding a pizza, send a POST request with authorization to:

`http://localhost:8080/api/v1/orders/makeOrder?pizzaNames=Pepperoni`

### 5. Viewing All Users and Their Orders

To view all users and their orders, send a GET request with authorization to:

`http://localhost:8080/api/v1/client/displayall`

### 6. Changing Profile Image

To change a user's profile image, send a POST request to:

`http://localhost:8080/api/v1/client/first/profileImageUrl`

**Request Body:**

```json
{
    "https://link-to-your-image.com/image.jpg"
}
```
