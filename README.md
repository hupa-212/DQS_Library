# 📚 Library Bookstore

<div align="center">

![Library System](https://img.shields.io/badge/Library-Management%20System-blue)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-green)
![Vue](https://img.shields.io/badge/Vue-3.5.22-4FC08D)
![TypeScript](https://img.shields.io/badge/TypeScript-5.9-blue)
![License](https://img.shields.io/badge/License-MIT-brightgreen)

A modern library bookstore built with **Spring Boot** (Backend) and **Vue 3 + TypeScript** (Frontend).

[Features](#-features) • [Installation](#-installation--run) • [API](#-api-endpoints) • [Documentation](#-additional-documentation)

</div>

---

## 📋 Table of Contents

- [Introduction](#-introduction)
- [Features](#-features)
- [Technologies Used](#-technologies-used)
- [System Requirements](#-system-requirements)
- [Installation & Run](#-installation--run)
- [Project Structure](#-project-structure)
- [API Endpoints](#-api-endpoints)
- [Authentication & Authorization](#-authentication--authorization)
- [Usage Guide](#-usage-guide)
- [Troubleshooting](#-troubleshooting)
- [Additional Documentation](#-additional-documentation)

---

## 🎯 Introduction

**Library bookstore** is a comprehensive solution for managing a digital library, including:

- ✅ **Book Management** - Add, edit, delete, search books
- ✅ **Category Management** - Categorize books
- ✅ **User Management** - Registration, login, profile management
- ✅ **Authorization System** - RBAC (Role-Based Access Control)
- ✅ **Order Management** - Create and manage orders
- ✅ **User-Friendly Interface** - Modern, responsive UI/UX

---

## 🚀 Features

### 📖 Book Management

- ✨ CRUD (Create, Read, Update, Delete) operations
- 🔍 Advanced search with filtering by category, author, price
- 📊 Inventory management
- 🏷️ Add/manage book categories
- 📸 Upload book cover images

### 👥 User Management

- 📝 New account registration
- 🔐 Secure login with JWT Token
- 👤 View & update personal profile
- 🔑 Role-based authorization (ADMIN, USER, GUEST)
- 🗑️ Soft delete for users

### 📦 Order Management

- 📋 Create new orders
- 🔍 View order history
- 📊 Detailed order information
- 💰 Automatic total price calculation

### 🎨 User Interface

- 📱 Responsive design (Mobile, Tablet, Desktop)
- ⚡ Smooth experience with Vite
- 🎨 Beautiful UI with Element Plus
- 🌐 Multi-language support (in preparation)

---

## 🛠️ Technologies Used

### Backend

| Technology      | Version | Description                    |
| --------------- | ------- | ------------------------------ |
| Java            | 21      | Programming language           |
| Spring Boot     | 3.5.7   | Web framework                  |
| Spring Security | Latest  | Authentication & authorization |
| Spring Data JPA | Latest  | ORM & database queries         |
| MySQL           | 8.0+    | Database                       |
| JWT             | -       | Token-based authentication     |
| Lombok          | 1.18.30 | Reduce boilerplate code        |
| MapStruct       | 1.6.3   | DTO Mapping                    |

### Frontend

| Technology   | Version | Description                      |
| ------------ | ------- | -------------------------------- |
| Vue.js       | 3.5.22  | Progressive JavaScript framework |
| TypeScript   | ~5.9.0  | Typed JavaScript                 |
| Vite         | 7.1.11  | Next-gen build tool              |
| Element Plus | 2.11.5  | Vue 3 UI Component Library       |
| Vue Router   | 4.6.3   | Official router                  |
| Pinia        | 3.0.3   | State management                 |
| Axios        | 1.13.1  | HTTP client                      |
| ESLint       | 9.37.0  | Code quality                     |
| Prettier     | 3.6.2   | Code formatter                   |

---

## 📋 System Requirements

### Backend

- **Java 21** or higher
- **Maven 3.6+** (or use included Maven Wrapper)
- **MySQL 8.0+**
- **Git**

### Frontend

- **Node.js** 20.19.0 or ≥ 22.12.0
- **npm** 10.0+
- **Modern Browser** (Chrome, Firefox, Safari, Edge)

---

## ⚙️ Installation & Run

### 🔧 Quick Start (Fastest Way)

#### Step 1: Clone Repository

```bash
git clone https://github.com/hupa-212/DQS_Library.git
cd DQS_Library
```

#### Step 2: Setup Backend

```bash
cd library_backend

# Configure MySQL database
# 1. Open MySQL and run:
# CREATE DATABASE bookstore_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 2. Update file: src/main/resources/application.properties
# - Change spring.datasource.username
# - Change spring.datasource.password

# Build & Run
./mvnw spring-boot:run
```

Backend runs at: **http://localhost:8080**

#### Step 3: Setup Frontend

```bash
cd ../library_frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

Frontend runs at: **http://localhost:5173**

---

### 📝 Detailed Setup

#### Backend - Detailed Steps

**1. Clone & Navigate**

```bash
git clone https://github.com/hupa-212/DQS_Library.git
cd DQS_Library/library_backend
```

**2. Configure Database**

```sql
-- Create database
CREATE DATABASE bookstore_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Or with different root password
CREATE DATABASE bookstore_db
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

**3. Configure Application Properties**

```bash
# Open file: src/main/resources/application.properties
```

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=false

# Server Configuration
server.port=8080
spring.application.name=bookstore_be

# JWT Configuration
jwt.signer-key=ZTy3D5rFgCTLP/oJgc5XvFmQHx2k4UB7ncZmWOPXpsLv66FuwjeQ7qfo7sLw8IcH
```

**4. Build & Run**

```bash
# Build project (first time will download dependencies)
./mvnw clean build

# Run application
./mvnw spring-boot:run

# Or run JAR directly
java -jar target/bookstore_be-0.0.1-SNAPSHOT.jar
```

#### Frontend - Detailed Steps

**1. Navigate & Install**

```bash
cd library_frontend

# Install all dependencies (first time)
npm install
```

**2. Configure API URL** (optional)

```bash
# Open: src/api/bookAPI.ts

# Ensure API_BASE_URL points correctly:
const API_BASE_URL = 'http://localhost:8080/api'
```

**3. Run Development Server**

```bash
npm run dev

# Output:
# ➜  Local:   http://localhost:5173/
# ➜  press h to show help
```

**4. Build Production** (when ready to deploy)

```bash
npm run build

# Output generated in folder: dist/
```

---

## 📁 Project Structure

```
DQS_Library/
├── library_backend/                    # Backend - Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/api/bookstore/library_backend/
│   │   │   │   ├── LibraryBackendApplication.java
│   │   │   │   ├── config/
│   │   │   │   │   ├── SecurityConfig.java        # Spring Security
│   │   │   │   │   ├── JwtTokenProvider.java      # JWT Token
│   │   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   │   └── DataInitializer.java       # Data setup
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthenticationController.java
│   │   │   │   │   ├── BookController.java
│   │   │   │   │   ├── CategoryController.java
│   │   │   │   │   ├── UserController.java
│   │   │   │   │   ├── RoleController.java
│   │   │   │   │   ├── PermissionController.java
│   │   │   │   │   └── ...
│   │   │   │   ├── dto/                  # Data Transfer Objects
│   │   │   │   │   ├── request/
│   │   │   │   │   └── response/
│   │   │   │   ├── exception/            # Custom Exceptions
│   │   │   │   ├── mapper/               # DTO Mappers
│   │   │   │   ├── model/                # JPA Entities
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Book.java
│   │   │   │   │   ├── Category.java
│   │   │   │   │   ├── Role.java
│   │   │   │   │   ├── Permission.java
│   │   │   │   │   ├── Order.java
│   │   │   │   │   └── OrderDetail.java
│   │   │   │   ├── repository/           # Data Access Layer
│   │   │   │   └── service/              # Business Logic
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── META-INF/
│   │   └── test/
│   ├── pom.xml                           # Maven Configuration
│   ├── mvnw & mvnw.cmd                   # Maven Wrapper
│   ├── HELP.md
│   └── README.md
│
├── library_frontend/                    # Frontend - Vue 3
│   ├── src/
│   │   ├── api/
│   │   │   └── bookAPI.ts               # API calls
│   │   ├── assets/                      # Static files
│   │   ├── components/
│   │   │   ├── admin/
│   │   │   │   ├── AddBookForm.vue      # Add book form
│   │   │   │   ├── AdminHeader.vue      # Admin header
│   │   │   │   └── AdminMenu.vue        # Admin menu
│   │   │   ├── book/
│   │   │   │   └── BookFilter.vue       # Book filter
│   │   │   └── common/
│   │   │       ├── BookItem.vue         # Book item
│   │   │       ├── Header.vue           # Common header
│   │   │       ├── Footer.vue           # Common footer
│   │   │       └── Slider.vue           # Carousel
│   │   ├── layouts/
│   │   │   ├── AdminLayout.vue          # Admin layout
│   │   │   └── UserLayout.vue           # User layout
│   │   ├── pages/
│   │   │   ├── book/
│   │   │   │   ├── BookList.vue         # Book list
│   │   │   │   ├── BookManagement.vue   # Book management
│   │   │   │   └── ManageListBook.vue   # Admin view
│   │   │   └── home/
│   │   │       ├── Home.vue             # Home page
│   │   │       └── AdminHome.vue        # Admin dashboard
│   │   ├── router/
│   │   │   └── index.ts                 # Vue Router
│   │   ├── stores/
│   │   │   └── counter.ts               # Pinia store
│   │   ├── App.vue                      # Root component
│   │   ├── main.ts                      # Entry point
│   │   └── shims-vue.d.ts
│   ├── public/                          # Static public files
│   ├── package.json
│   ├── vite.config.ts
│   ├── tsconfig.json
│   ├── eslint.config.ts
│   ├── index.html
│   └── README.md
│
├── postman/                             # API testing collection
│   └── Library_API.postman_collection.json
│
├── README.md                            # This file
└── .vscode/
    └── launch.json                      # VS Code configuration
```

---

## 🔌 API Endpoints

### 🔐 Authentication

```http
POST   /api/auth/register          Register new account
POST   /api/auth/login             Login
POST   /api/auth/refresh           Refresh token
```

### 📚 Books

```http
GET    /api/books                  Get book list (paginated)
GET    /api/books/{id}             Get book details
GET    /api/books/search           Search books
POST   /api/books                  Create new book (Admin)
PUT    /api/books/{id}             Update book (Admin)
DELETE /api/books/{id}             Delete book (Admin)
GET    /api/books/category/{categoryId}  Get books by category
```

### 🏷️ Categories

```http
GET    /api/categories             Get all categories
GET    /api/categories/{id}        Get category details
POST   /api/categories             Create category (Admin)
PUT    /api/categories/{id}        Update category (Admin)
DELETE /api/categories/{id}        Delete category (Admin)
```

### 👥 Users

```http
GET    /api/users                  Get user list (Admin)
GET    /api/users/{id}             Get user details
GET    /api/users/me               Get current user info
PUT    /api/users/{id}             Update user info
DELETE /api/users/{id}             Delete user (Admin)
```

### 🔑 Roles & Permissions

```http
GET    /api/roles                  Get all roles
GET    /api/roles/{id}             Get role details
POST   /api/roles                  Create role (Admin)
PUT    /api/roles/{id}             Update role (Admin)
DELETE /api/roles/{id}             Delete role (Admin)

GET    /api/permissions            Get all permissions
POST   /api/permissions            Create permission (Admin)
```

### 📦 Orders

```http
GET    /api/orders                 Get order list
GET    /api/orders/{id}            Get order details
POST   /api/orders                 Create new order
PUT    /api/orders/{id}            Update order
DELETE /api/orders/{id}            Delete order
```

---

## 🔐 Authentication & Authorization

### JWT Authentication Flow

```
┌─────────────────────────────────────────────────────────┐
│ 1. Client sends login request                            │
│    POST /api/auth/login                                 │
│    { "username": "user", "password": "pass" }           │
└─────────────┬───────────────────────────────────────────┘
              │
┌─────────────▼───────────────────────────────────────────┐
│ 2. Server verifies credentials                          │
│    - Check user & password                              │
│    - Verify roles & permissions                         │
└─────────────┬───────────────────────────────────────────┘
              │
┌─────────────▼───────────────────────────────────────────┐
│ 3. Server returns JWT Token                             │
│    { "token": "eyJhbGciOiJIUzI1NiIs..." }              │
└─────────────┬───────────────────────────────────────────┘
              │
┌─────────────▼───────────────────────────────────────────┐
│ 4. Client saves token & sends with request              │
│    Authorization: Bearer eyJhbGciOiJIUzI1NiIs...       │
└─────────────┬───────────────────────────────────────────┘
              │
┌─────────────▼───────────────────────────────────────────┐
│ 5. Server verifies token for each request               │
│    - Check signature                                    │
│    - Check expiration                                   │
│    - Check authorization                                │
└─────────────┬───────────────────────────────────────────┘
              │
         ✅ Accept / ❌ Reject
```

### Default Roles

| Role      | Permissions     | Description                          |
| --------- | --------------- | ------------------------------------ |
| **ADMIN** | ✅ Full access  | Administrator - Access all functions |
| **USER**  | ✅ Limited      | Regular user - View/manage own data  |
| **GUEST** | ⚠️ Very limited | Guest - View books only              |

### Demo Accounts

| Role  | Email               | Password |
| ----- | ------------------- | -------- |
| ADMIN | admin@bookstore.com | admin123 |
| USER  | user@bookstore.com  | user123  |

---

## 📖 Usage Guide

### 👨‍💼 For Administrators

#### 1. Admin Login

```
URL: http://localhost:5173
Email: admin@bookstore.com
Password: admin123
```

#### 2. Manage Books

- Navigate: **Admin Menu** → **Books Management**
- Add new book: Click **Add Book** → Fill form
- View list: Click **List Books**
- Edit: Click **Edit** on book to modify
- Delete: Click **Delete** (soft delete)

#### 3. Manage Categories

- Navigate: **Admin Menu** → **Categories**
- CRUD categories same as books

#### 4. Manage Users

- Navigate: **Admin Menu** → **Users**
- View list, manage roles, delete users

### 👤 For Regular Users

#### 1. Register Account

- Click **Register** on home page
- Fill form: Email, Username, Password
- Click **Register**

#### 2. Login

- Click **Login**
- Enter Email & Password
- Click **Login**

#### 3. Browse Books

- Home page displays book list
- Use **Search Bar** to find
- Use **Filters** to filter by category, price
- Click book to view details

#### 4. Create Orders

- View book details → Click **Add to Order**
- Go to **My Orders** to view orders

---

## 🧪 Testing

### Backend - Unit Tests

```bash
cd library_backend
./mvnw test
```

### Backend - Integration Tests

```bash
./mvnw verify
```

### Frontend - Unit Tests (if available)

```bash
cd library_frontend
npm run test
```

### API Testing with Postman

```
1. Open Postman
2. Import file: postman/Library_API.postman_collection.json
3. Select requests to test
4. Click Send
```

---

## 🚨 Troubleshooting

### ❌ Backend Issues

#### Error: "Cannot connect to database"

```bash
✅ Check MySQL is running:
   - Windows: services.msc → MySQL80 (or your version)
   - Mac: brew services list | grep mysql

✅ Check database exists:
   mysql -u root -p
   SHOW DATABASES;

✅ Check credentials in application.properties
```

#### Error: "Port 8080 already in use"

```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill process (Windows)
taskkill /PID <PID> /F

# Or change port in application.properties:
server.port=8081
```

#### Error: "JWT Token Invalid"

```bash
✅ Check token format: "Bearer <token>" (not "bearer")
✅ Check token expiration
✅ Check JWT secret key correct in application.properties
```

### ❌ Frontend Issues

#### Error: "Cannot GET /api/books"

```bash
✅ Check backend is running (http://localhost:8080)
✅ Check API URL correct in src/api/bookAPI.ts
✅ Check CORS enabled in backend (SecurityConfig.java)
```

#### Error: "Port 5173 already in use"

```bash
npm run dev -- --port 5174
```

#### Error: "Module not found"

```bash
# Delete node_modules and reinstall
rm -rf node_modules package-lock.json
npm install
```

#### Error: "Blank page after login"

```bash
✅ Check browser console (F12)
✅ Check token saved in localStorage
✅ Check router configuration
```

---

## 📚 Additional Documentation

### Backend

- [Spring Boot Official Docs](https://spring.io/projects/spring-boot)
- [Spring Security Guide](https://spring.io/projects/spring-security)
- [Spring Data JPA Reference](https://spring.io/projects/spring-data-jpa)
- [JWT Token Reference](https://jwt.io/)

### Frontend

- [Vue 3 Documentation](https://vuejs.org/)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
- [Vite Official Guide](https://vitejs.dev/guide/)
- [Element Plus Component Library](https://element-plus.org/)
- [Vue Router Guide](https://router.vuejs.org/)
- [Pinia State Management](https://pinia.vuejs.org/)

### Database

- [MySQL Official Docs](https://dev.mysql.com/doc/)
- [Hibernate ORM Guide](https://hibernate.org/orm/documentation/)

---

## 📝 Main Scripts

### Backend Scripts

```bash
# Development
./mvnw spring-boot:run              # Run application

# Build & Package
./mvnw clean build                  # Full build
./mvnw clean package                # Build JAR file

# Testing
./mvnw test                         # Run unit tests
./mvnw verify                       # Run integration tests
./mvnw clean test                   # Clean & test

# Cleaning
./mvnw clean                        # Delete target folder
```

### Frontend Scripts

```bash
# Development
npm run dev                         # Start dev server
npm run type-check                  # TypeScript checking
npm run lint                        # ESLint checking

# Build
npm run build                       # Build production
npm run build-only                  # Build only (skip type-check)

# Code Quality
npm run format                      # Format code with Prettier
npm run lint -- --fix               # Fix ESLint errors

# Preview
npm run preview                     # Preview production build
```

---

## 🎓 Main Models / Entities

### User

```typescript
{
  id: number
  username: string          // Unique
  email: string            // Unique
  password: string         // Encrypted
  firstName: string
  lastName: string
  roles: Role[]            // Many-to-Many
  createdAt: Date
  updatedAt: Date
  isDeleted: boolean
}
```

### Book

```typescript
{
  id: number;
  title: string;
  author: string;
  isbn: string;
  description: string;
  price: number;
  quantity: number;
  coverImageUrl: string;
  category: Category; // Many-to-One
  createdAt: Date;
  updatedAt: Date;
  isDeleted: boolean;
}
```

### Category

```typescript
{
  id: number
  name: string
  description: string
  books: Book[]           // One-to-Many
  createdAt: Date
  isDeleted: boolean
}
```

### Order

```typescript
{
  id: number
  user: User             // Many-to-One
  totalPrice: number
  status: 'PENDING' | 'CONFIRMED' | 'SHIPPED' | 'DELIVERED'
  orderDetails: OrderDetail[]  // One-to-Many
  createdAt: Date
  updatedAt: Date
}
```

### Role

```typescript
{
  id: number
  name: string           // 'ADMIN', 'USER', 'GUEST'
  description: string
  permissions: Permission[]  // Many-to-Many
}
```

---

## 🤝 Contributing

We welcome community contributions!

### How to Contribute

1. **Fork the repository**

   ```bash
   git clone https://github.com/hupa-212/DQS_Library.git
   ```

2. **Create feature branch**

   ```bash
   git checkout -b feature/AmazingFeature
   ```

3. **Commit changes**

   ```bash
   git add .
   git commit -m 'Add some AmazingFeature'
   ```

4. **Push to branch**

   ```bash
   git push origin feature/AmazingFeature
   ```

5. **Open Pull Request**
   - Describe changes in detail
   - Reference issues (if any)

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2025 DQS Library Contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software...
```

---

## 👥 Authors & Contributors

### Core Team

- **Huỳnh Minh Trí** (@hupa-212) - Lead Developer
- **Your Team Members** - Frontend/Backend Developers

### Contributors

Thanks to everyone who contributed to this project!

---

## 📞 Contact & Support

### Get in Touch

- 📧 **Email:** your-email@example.com
- 🐛 **Bug Reports:** [GitHub Issues](https://github.com/hupa-212/DQS_Library/issues)
- 💬 **Discussions:** [GitHub Discussions](https://github.com/hupa-212/DQS_Library/discussions)
- 🌐 **Website:** https://your-website.com

### FAQ

**Q: How to reset database?**
A:

```sql
DROP DATABASE bookstore_db;
CREATE DATABASE bookstore_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

**Q: Forgot admin password?**
A: Delete user from database and re-run DataInitializer or create new user via registration.

**Q: How to change JWT expiration time?**
A: Find in SecurityConfig.java, change the expiration value.

---

## 🎉 Acknowledgments

- **Spring Boot Team** - Excellent framework
- **Vue.js Team** - Progressive framework
- **Element Plus** - UI Components
- **Community** - Support & feedback

---

## 📊 Project Status

| Component     | Status         | Progress |
| ------------- | -------------- | -------- |
| Backend API   | ✅ Active      | 80%      |
| Frontend UI   | ✅ Active      | 75%      |
| Documentation | ✅ In Progress | 90%      |
| Testing       | 🔄 In Progress | 50%      |
| Deployment    | 📋 Planning    | 20%      |

---

## 🗓️ Changelog

### v1.0.0 (November 2025)

- ✨ Initial release
- 📚 Basic CRUD operations for Books
- 👥 User authentication & authorization
- 🛒 Order bookstore
- 🎨 Responsive UI with Vue 3

---

**Last Updated:** November 5, 2025

**Version:** 1.0.0

---

<div align="center">

Made with ❤️ by [Your Team]

⭐ Star us on GitHub if you find this helpful!

[Back to Top](#-library-management-system)

</div>
