# 🤝 Contributing to Social Media REST API

First of all, thank you for taking the time to contribute! 🎉
Every contribution is appreciated and helps make this project better.

---

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Coding Guidelines](#coding-guidelines)
- [Commit Message Guidelines](#commit-message-guidelines)
- [Pull Request Process](#pull-request-process)
- [Reporting Bugs](#reporting-bugs)
- [Suggesting Features](#suggesting-features)

---

## 📜 Code of Conduct

By participating in this project, you agree to maintain a respectful and inclusive environment for everyone. Be kind, be helpful, and be constructive.

---

## 💡 How Can I Contribute?

There are many ways you can contribute to this project:

- 🐛 **Report bugs** — found something broken? Let us know!
- ✨ **Suggest features** — have a great idea? We would love to hear it!
- 🔧 **Fix bugs** — pick an open issue and submit a fix
- 📝 **Improve documentation** — help make the docs clearer
- 🧪 **Write tests** — help improve code coverage
- 🔍 **Code review** — review open pull requests

---

## 🚀 Getting Started

### Prerequisites
- Java 21
- MySQL 8.0
- Maven 3.x
- Git

### Fork and Clone

**1. Fork the repository**

Click the **Fork** button on the top right of the repository page.

**2. Clone your fork**
```bash
git clone https://github.com/YOUR_USERNAME/Social-Media-REST-API.git
cd Social-Media-REST-API
```

**3. Add upstream remote**
```bash
git remote add upstream https://github.com/Xaryansonawane/Social-Media-REST-API.git
```

**4. Create the database**
```sql
CREATE DATABASE SocialApp_DB;
```

**5. Configure application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/SocialApp_DB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080
```

**6. Run the application**
```bash
mvn spring-boot:run
```

---

## 📁 Project Structure
```
SocialApp/
├── src/main/java/com/example/SocialApp/
│   ├── Config/          → Security configuration
│   ├── controllers/     → REST API endpoints
│   ├── DTOs/            → Data Transfer Objects
│   ├── models/          → JPA Entity classes
│   ├── repository/      → Spring Data JPA repositories
│   ├── services/        → Business logic
│   └── Utility/         → ApiResponse and GlobalExceptionHandler
├── sql/                 → SQL scripts for database setup
├── pom.xml              → Maven dependencies
└── README.md            → Project documentation
```

---

## 📏 Coding Guidelines

Please follow these guidelines when contributing:

### Java Code Style
- Use **camelCase** for variable and method names
- Use **PascalCase** for class names
- Use **UPPER_SNAKE_CASE** for constants
- Always add proper **getters and setters**
- Keep methods **short and focused** — one method, one responsibility

### API Response
- Always wrap responses in **ApiResponse** wrapper
- Always use **CAPITAL LETTERS** for response messages
- Always use proper **HTTP status codes**:
  - `200 OK` — success
  - `201 CREATED` — resource created
  - `400 BAD REQUEST` — invalid input
  - `401 UNAUTHORIZED` — invalid credentials
  - `404 NOT FOUND` — resource not found
  - `500 INTERNAL SERVER ERROR` — server error

### Exception Handling
- Always use **ResponseStatusException** instead of RuntimeException
- Always include the **resource ID** in the error message
- Example:
```java
throw new ResponseStatusException(
    HttpStatus.NOT_FOUND, "USER NOT FOUND WITH ID: " + id
);
```

### DTOs
- Never return raw entity objects that contain sensitive data like passwords
- Always use **DTOs** for API responses
- Use **@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)** on password fields

---

## 📝 Commit Message Guidelines

Follow this format for commit messages:
```
type: short description

Extended description (optional)
```

### Types
| Type | Description |
|------|-------------|
| `feat` | New feature |
| `fix` | Bug fix |
| `docs` | Documentation changes |
| `refactor` | Code refactoring |
| `test` | Adding tests |
| `chore` | Maintenance tasks |

### Examples
```
feat: Add JWT token authentication
fix: Fix duplicate like prevention bug
docs: Update API endpoints in README
refactor: Replace RuntimeException with ResponseStatusException
test: Add unit tests for UserService
```

---

## 🔄 Pull Request Process

**1. Create a new branch**
```bash
git checkout -b feature/your-feature-name
```

**2. Make your changes**

Write clean, well-documented code following the coding guidelines above.

**3. Test your changes**

Make sure the application runs without errors:
```bash
mvn spring-boot:run
```

**4. Commit your changes**
```bash
git add .
git commit -m "feat: Add your feature description"
```

**5. Push to your fork**
```bash
git push origin feature/your-feature-name
```

**6. Open a Pull Request**

- Go to your fork on GitHub
- Click **New Pull Request**
- Select your branch
- Fill in the PR template
- Click **Create Pull Request**

### Pull Request Checklist
- [ ] Code follows the coding guidelines
- [ ] Application runs without errors
- [ ] API responses use ApiResponse wrapper
- [ ] Proper HTTP status codes are used
- [ ] No sensitive data like passwords exposed in responses
- [ ] README updated if new endpoints added

---

## 🐛 Reporting Bugs

To report a bug please create an issue with:

- **Title** — clear and descriptive
- **Description** — what happened vs what you expected
- **Steps to reproduce** — how to reproduce the bug
- **Error message** — paste the full error from console
- **Environment** — Java version, MySQL version, OS

### Bug Report Template
```
**Bug Description:**
A clear description of the bug.

**Steps to Reproduce:**
1. Send POST request to /user/login
2. With wrong password
3. See error

**Expected Behavior:**
Should return 401 UNAUTHORIZED

**Actual Behavior:**
Returns 500 INTERNAL SERVER ERROR

**Error Message:**
Paste error here

**Environment:**
- Java: 21
- MySQL: 8.0.45
- OS: Windows 11
```

---

## 💡 Suggesting Features

To suggest a new feature please create an issue with:

- **Title** — clear and descriptive
- **Problem** — what problem does this feature solve?
- **Solution** — how should it work?
- **API Design** — what endpoint and response should it have?

### Feature Request Template
```
**Feature Description:**
Add JWT token based authentication

**Problem:**
Currently there is no token based auth system.
Users have to send credentials with every request.

**Proposed Solution:**
After login return a JWT token.
User sends token in Authorization header for protected routes.

**API Design:**
POST /user/login
Response: { "token": "eyJhbGc..." }

GET /posts
Headers: { "Authorization": "Bearer eyJhbGc..." }
```

---

## 📬 Contact

**Aryan Sonawane**
- GitHub: [@Xaryansonawane](https://github.com/Xaryansonawane)
- LeetCode: [Xaryansonawane](https://leetcode.com/u/Xaryansonawane/)
- HackerRank: [Xaryansonawane](https://www.hackerrank.com/profile/Xaryansonawane)
- Instagram: [@Xaryansonawane](https://instagram.com/Xaryansonawane)

---

Thank you for contributing! Together we can make this project even better! 🚀⚙️
