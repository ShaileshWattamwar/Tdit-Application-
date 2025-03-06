#This is an application of ecommerce backend




## Features

*  **JWT Authentication**: Secure login/logout with token blacklisting.
*  **Role-Based Access Control**:
  * `USER`: Manage own profile.
  * `ADMIN`: Full user management privileges.
*  **CRUD Operations**: Create, read, update, and delete users.
*  **Validation**: Password matching, email format, and role constraints.
*  **Error Handling**: Custom exceptions with structured responses.

---

## Technologies

* **Spring Boot 3.3.9**
* **Spring Security**
* **Spring Data JPA**
* **PostgreSQL**
* **JSON Web Tokens (JWT)**
* **Lombok** (boilerplate reduction)
* **Maven**

---

## Prerequisites

* Java 17
* PostgreSQL



---

## Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-repo/authservice.git
   cd authservice
   ```

2. **Database Configuration**:
   
   Create a PostgreSQL database named `authservice`.

   Update `src/main/resources/application.properties`:
   
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/authservice
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   jwt.secret=your-secret-key
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

---

## API Documentation

### Register a User
* **URL**: `POST /user/register`
* **Request Body:**
   ```json
   {
     "userName": "testuser",
     "email": "test@example.com",
     "password": "Password@123",
     "confirmPassword": "Password@123",
     "role": "USER"
   }
   ```

#### Login

* **URL**: `POST /auth/login`
* **Access**: Public

**Request:**
   ```json
   {
     "userName": "user123",
     "password": "Password@123"
   }
   ```

**Success Response:**
   ```json
   {
     "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
   }
   ```

#### Logout

* **URL**: `POST /auth/logout`
* **Headers**: `Authorization: Bearer <JWT_TOKEN>`

**Response:**
   ```json
   "Logged out successfully"
   ```

---

## Running the Application

### Local Setup
   ```bash
   mvn spring-boot:run
   ```


   ```

---

## Testing REST APIs



### Get Current User
* **URL**: `GET /user/me`
* **Headers**: `Authorization: Bearer <JWT_TOKEN>`

---

## Expected Results

### Registration Success
   ```json
   HTTP 201 Created
   "User registered successfully"
   ```

### Login Success
   ```json
   HTTP 200 OK
   {
     "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
   }
   ```

---


