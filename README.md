# 📝 Blogging Website Backend

A fully functional backend system for a blogging platform built with **Java**, **Spring Boot**, and **PostgreSQL**. This backend handles everything from user authentication to post and comment management, ensuring secure, role-based access and smooth content operations.

## 🔧 Tech Stack

- **Language:** Java  
- **Framework:** Spring Boot  
- **Database:** PostgreSQL  
- **Security:** Spring Security, JWT  
- **Build Tool:** Maven  
- **Architecture:** Layered (Controller → Service → Repository)

## ✅ Features

### 🔐 User & Security
- User registration and login
- Password encryption with `BCrypt`
- JWT-based authentication
- Role-based access (Admin/User)
- Global exception handling

### 📝 Post Management
- Create, update, delete posts
- View single or all posts
- Filter posts by category
- Pagination and sorting support

### 💬 Comments
- Add, view, delete comments on posts
- Comments linked to both user and post

### 📁 Categories
- Create and manage post categories
- View posts by category

## 📂 Project Structure

- `controller/` → REST endpoints  
- `service/` → Business logic  
- `repository/` → Data access layer (Spring Data JPA)  
- `entity/` → JPA Entities (User, Post, Comment, Role, Category)  
- `dto/` → Data Transfer Objects  
- `security/` → JWT filters, config, and auth logic  
- `exception/` → Custom exception handling  

## 📌 Future Enhancements

- Image upload feature (e.g., Cloudinary, AWS S3)  
- User profile and bio  
- Admin dashboard  
- Swagger API documentation

## 🚀 How to Run

1. Clone the repo  
2. Set up PostgreSQL and update `application.properties`  
3. Run the project using your IDE or `mvn spring-boot:run`  
4. Test APIs with Postman or Swagger
