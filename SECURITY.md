# 🔒 Security Policy

---

## 📱 Social Media REST API – Security

A fully functional, production-structured **RESTful backend** for a social media platform built with **Spring Boot 3.4.3, Spring Security, JWT Authentication, Spring Data JPA, and MySQL**.

This document explains how security is handled in this project and how vulnerabilities should be reported responsibly.

---

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square\&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen?style=flat-square\&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.4.3-blue?style=flat-square\&logo=springsecurity)
![JWT](https://img.shields.io/badge/JWT-Authentication-purple?style=flat-square\&logo=jsonwebtokens)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square\&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.x-red?style=flat-square\&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

---

## 📌 Overview

Security is a core part of this project. This API follows industry-standard practices such as:

* JWT-based authentication
* BCrypt password hashing
* Input validation
* Secure API design
* DTO-based data protection

---

## ✅ Supported Versions

| Version              | Supported |
| -------------------- | --------- |
| Latest (main branch) | ✅ Yes     |
| Older versions       | ❌ No      |

> Always use the latest version to stay secure.

---

## 🚨 Reporting a Vulnerability

If you discover a security issue, **do NOT open a public issue**.

📧 Email: **[aryanvipinsonawane@gmail.com](mailto:aryanvipinsonawane@gmail.com)**
📌 Subject: `Security Vulnerability Report - SocialApp`

### Include:

* Vulnerability description
* Steps to reproduce
* Impact
* Suggested fix (optional)

⏱️ Response time: **48–72 hours**

---

## 🛡️ Security Features

### 🔐 JWT Authentication

* Secure login using JWT tokens
* Stateless authentication (no sessions)
* Token required for protected routes
* Token expiry: 24 hours

---

### 🔑 Password Protection

* BCrypt hashing used
* No plain-text password storage
* Hidden in responses using:

```java
@JsonProperty(access = WRITE_ONLY)
```

---

### 🚫 Unauthorized Access Handling

* JWT filter validates every request
* Invalid/missing token → `401 UNAUTHORIZED`
* Clean JSON error responses

---

### 🧠 Input Validation

* Empty fields are rejected
* Proper HTTP status codes returned:

  * 400 BAD REQUEST
  * 401 UNAUTHORIZED
  * 404 NOT FOUND

---

### 📁 File Upload Security

* Only image files allowed
* Validation:

```java
contentType.startsWith("image/")
```

* Max size: **5MB**

---

### 🔄 Business Logic Protection

* ❌ Duplicate Like Prevention
* ❌ Self Follow Prevention
* ❌ Self Messaging Prevention
* ❌ Duplicate Email Prevention

---

### 📦 DTO Security

* No raw entities exposed
* Sensitive fields hidden
* Clean API responses

---

### ⚠️ Global Exception Handling

* Centralized error handling using `@ControllerAdvice`
* Prevents internal data leaks

Example response:

```json
{
  "success": false,
  "message": "ERROR MESSAGE",
  "data": null,
  "error": "ERROR CODE"
}
```

---

## ⚠️ Known Limitations

* ❗ No refresh tokens
* ❗ No rate limiting
* ❗ No HTTPS enforcement (dev mode)
* ❗ No account lock mechanism
* ❗ No email verification

---

## 🚀 Recommended Improvements

For production use:

* ✅ Enable HTTPS (SSL/TLS)
* ✅ Add refresh tokens
* ✅ Implement rate limiting
* ✅ Add OAuth2 login
* ✅ Email verification system
* ✅ Account lock after failed attempts
* ✅ Logging & monitoring

---

## 🔐 Best Practices Followed

* Principle of Least Privilege
* Stateless authentication
* Layered architecture (Controller → Service → Repository)
* Input validation
* No sensitive data exposure
* Consistent API response format

---

## 🙌 Final Note

Security is not a one-time task — it’s an ongoing process.

If you find any issues or have suggestions, feel free to report or contribute.

---

⭐ Stay secure. Build smart.
