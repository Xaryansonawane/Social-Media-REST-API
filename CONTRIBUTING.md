# 🤝 Contributing to Social Media REST API

---

## 📱 Social Media REST API – Contribution Guide

A fully functional, production-structured **RESTful backend** built with **Spring Boot, Spring Security, JWT, JPA, and MySQL**.

We welcome contributions from everyone — whether you're a beginner or an experienced developer 💙

---

[![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square\&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen?style=flat-square\&logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.4.3-blue?style=flat-square\&logo=springsecurity)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JWT-0.12.7-purple?style=flat-square\&logo=jsonwebtokens)](https://github.com/jwtk/jjwt)
[![MySQL](https://img.shields.io/badge/MySQL-8.0.45-blue?style=flat-square\&logo=mysql)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.x-red?style=flat-square\&logo=apachemaven)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](LICENSE)

---

## 🙌 Welcome

First of all, thank you for taking the time to contribute! 🎉
Every contribution — big or small — helps improve this project.

---

## 📋 Table of Contents

* Code of Conduct
* Ways to Contribute
* Getting Started
* Project Structure
* Coding Guidelines
* Commit Guidelines
* Pull Request Process
* Reporting Bugs
* Suggesting Features

---

## 📜 Code of Conduct

Please maintain a respectful and collaborative environment:

* Be kind and supportive
* Give constructive feedback
* Respect different perspectives

---

## 💡 Ways to Contribute

You can contribute in many ways:

* 🐛 Report bugs
* ✨ Suggest new features
* 🔧 Fix issues
* 📝 Improve documentation
* 🧪 Add test cases
* 🔍 Review pull requests

---

## 🚀 Getting Started

### Prerequisites

* Java 21
* MySQL 8.0
* Maven 3.x
* Git

---

### 🔧 Setup Steps

**1. Fork the repository**

Click the **Fork** button on GitHub.

---

**2. Clone your fork**

```bash
git clone https://github.com/YOUR_USERNAME/Social-Media-REST-API.git
cd Social-Media-REST-API
```

---

**3. Add upstream repository**

```bash
git remote add upstream https://github.com/Xaryansonawane/Social-Media-REST-API.git
```

---

**4. Create database**

```sql
CREATE DATABASE SocialApp_DB;
```

---

**5. Configure application.properties**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/SocialApp_DB
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

---

**6. Run the project**

```bash
mvn spring-boot:run
```

---

## 📁 Project Structure

```
SocialApp/
├── controllers/     → API endpoints
├── services/        → Business logic
├── repository/      → Database layer
├── models/          → Entities
├── DTOs/            → Response objects
├── Utility/         → ApiResponse & Exception Handler
└── config/          → Security configuration
```

---

## 📏 Coding Guidelines

### Java Style

* camelCase → variables & methods
* PascalCase → class names
* UPPER_CASE → constants

---

### Best Practices

* Keep methods short and clean
* Follow single responsibility principle
* Write readable code

---

### API Rules

* Always use **ApiResponse** wrapper
* Use proper HTTP status codes:

| Code | Meaning      |
| ---- | ------------ |
| 200  | Success      |
| 201  | Created      |
| 400  | Bad Request  |
| 401  | Unauthorized |
| 404  | Not Found    |
| 500  | Server Error |

---

### Exception Handling

```java
throw new ResponseStatusException(
    HttpStatus.NOT_FOUND,
    "USER NOT FOUND WITH ID: " + id
);
```

---

### DTO Rules

* Never return raw entities
* Hide sensitive data (passwords)
* Use DTOs for all responses

---

## 📝 Commit Guidelines

### Format

```
type: short description
```

### Types

| Type     | Description      |
| -------- | ---------------- |
| feat     | New feature      |
| fix      | Bug fix          |
| docs     | Documentation    |
| refactor | Code improvement |
| test     | Testing          |
| chore    | Maintenance      |

---

### Example

```
feat: Add JWT authentication
fix: Prevent duplicate likes
docs: Update README
```

---

## 🔄 Pull Request Process

**1. Create branch**

```bash
git checkout -b feature/your-feature
```

---

**2. Make changes**

Write clean and tested code.

---

**3. Commit**

```bash
git commit -m "feat: Add new feature"
```

---

**4. Push**

```bash
git push origin feature/your-feature
```

---

**5. Open PR**

* Go to GitHub
* Click **New Pull Request**
* Submit your changes

---

### ✅ Checklist

* Code follows guidelines
* No errors in application
* API responses are consistent
* No sensitive data exposed
* Documentation updated

---

## 🐛 Reporting Bugs

Create an issue with:

* Description
* Steps to reproduce
* Expected vs actual behavior
* Error logs
* Environment details

---

## 💡 Suggesting Features

Include:

* Problem description
* Proposed solution
* API design

---

## 📬 Contact

**Aryan Sonawane**

* GitHub: https://github.com/Xaryansonawane
* LeetCode: https://leetcode.com/u/Xaryansonawane/
* HackerRank: https://www.hackerrank.com/profile/Xaryansonawane
* Instagram: https://instagram.com/Xaryansonawane

---

## 🙏 Final Note

Your contributions make this project better for everyone.

⭐ If you like this project, consider giving it a star!

🚀 Happy Coding!
