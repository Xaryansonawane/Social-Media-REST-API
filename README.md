# 📱 Social-Media-REST-API

> A fully functional, production-structured **RESTful backend** for a social media platform built with **Spring Boot 3.4.3**, **Spring Security**, **JWT Authentication**, **Spring Data JPA**, and **MySQL**.
>
> This project covers the complete core feature set of a modern social network — secure user authentication with JWT tokens, posts, comments, likes, follow relationships, direct messaging, and image uploads — all delivered through a clean, layered MVC architecture with standardised API responses.

<br/>

[![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.4.3-blue?style=flat-square&logo=springsecurity)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JWT-0.12.7-purple?style=flat-square&logo=jsonwebtokens)](https://github.com/jwtk/jjwt)
[![MySQL](https://img.shields.io/badge/MySQL-8.0.45-blue?style=flat-square&logo=mysql)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.x-red?style=flat-square&logo=apachemaven)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](LICENSE)

---

## 📋 Table of Contents

- [What is this project?](#-what-is-this-project)
- [Tech Stack](#-tech-stack)
- [Features](#-features)
- [How it Works — Architecture](#-how-it-works--architecture)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [API Reference](#-api-reference)
- [How to Use the API — Request and Response Examples](#-how-to-use-the-api--request-and-response-examples)
- [Authentication — How JWT Works in this Project](#-authentication--how-jwt-works-in-this-project)
- [Business Rules and Validations](#-business-rules-and-validations)
- [DTOs — Why We Never Return Raw Entities](#-dtos--why-we-never-return-raw-entities)
- [Error Handling](#-error-handling)
- [Getting Started — Run it Locally](#-getting-started--run-it-locally)
- [Configuration Reference](#-configuration-reference)
- [Sample Data](#-sample-data)
- [Roadmap](#-roadmap)
- [Author](#-author)

---

## 🌐 What is this project?

This is a **backend REST API** — think of it as the server-side engine that powers a social media app like Instagram or Twitter.

It handles everything behind the scenes:
- When a user registers or logs in — this API takes care of it
- When someone posts a photo or writes a caption — this API stores it
- When someone likes a post or follows another user — this API processes it
- When two users chat with each other — this API manages the messages

> **For beginners:** A REST API is basically a set of URLs (called endpoints) that your frontend app (mobile or web) calls to get or send data. This project provides all those endpoints for a social media platform.

---

## 🚀 Tech Stack

| Layer | Technology | What it does |
|-------|-----------|-------------|
| Language | Java 21 | The programming language used |
| Framework | Spring Boot 3.4.3 | Makes building Java web apps fast and easy |
| Web Layer | Spring MVC | Handles HTTP requests and responses |
| Security | Spring Security + JWT | Protects endpoints, handles login tokens |
| ORM | Spring Data JPA + Hibernate 6.6.8 | Connects Java code to the MySQL database |
| Database | MySQL 8.0.45 | Stores all the data |
| Build Tool | Apache Maven 3.x | Manages dependencies and builds the project |
| File Upload | Spring Multipart | Handles profile image and cover image uploads |

---

## ✨ Features

### 🔐 Authentication and Security
- ✅ **JWT Token Authentication** — Login returns a Bearer token, all protected routes require it
- ✅ **BCrypt Password Hashing** — Passwords are hashed before storing, never stored as plain text
- ✅ **Password Hidden from Responses** — `@JsonProperty(access = WRITE_ONLY)` ensures password never appears in any API response
- ✅ **Stateless Sessions** — No server-side sessions, every request is verified using the JWT token
- ✅ **Custom JWT Entry Point** — Returns clean JSON error when an unauthenticated request hits a protected route

### 👤 Users
- ✅ **User Registration** — Sign up with full name, username, email, password, bio
- ✅ **User Login** — Returns JWT token on successful login
- ✅ **Get Current User** — `/auth/me` returns the currently logged-in user from the token
- ✅ **User Profile with Image Upload** — Upload profile image and cover image during registration
- ✅ **User Stats** — Get follower and following counts for any user
- ✅ **Fetch All Users** — Returns list of all users as clean UserDTO

### 📝 Posts
- ✅ **Create Posts** — Create posts with captions and optional image URLs
- ✅ **Fetch All Posts** — Returns all posts with like count, comments and list of who liked
- ✅ **Fetch Single Post** — Get a specific post by ID
- ✅ **Fetch Posts by User** — Get all posts by a specific user

### 💬 Comments
- ✅ **Add Comments** — Comment on any post
- ✅ **Fetch Comments** — Get all comments for a specific post
- ✅ **Delete Comments** — Remove a comment, auto-decrements post comment count

### ❤️ Likes
- ✅ **Like Posts** — Like any post with duplicate like prevention
- ✅ **Unlike Posts** — Remove a like, auto-decrements post like count
- ✅ **Fetch Likes** — Get all likes for a post or all likes by a user

### 👥 Follows
- ✅ **Follow Users** — Follow another user with self-follow prevention
- ✅ **Unfollow Users** — Remove a follow relationship
- ✅ **Get Followers** — See who follows a user
- ✅ **Get Following** — See who a user is following

### 💌 Messages
- ✅ **Send Messages** — Direct message any user with self-message prevention
- ✅ **Get Conversation** — Full bidirectional chat history between two users
- ✅ **Unread Messages** — Fetch all unseen messages for a user
- ✅ **Mark as Seen** — Mark a message as read
- ✅ **Delete Messages** — Remove a message

### 🛠️ Code Quality
- ✅ **ApiResponse Wrapper** — Every endpoint returns `{ success, message, data, error }`
- ✅ **GlobalExceptionHandler** — Catches all errors and returns clean JSON responses
- ✅ **ResponseStatusException** — Proper HTTP status codes for 404, 400, 401 everywhere
- ✅ **Input Validation** — Empty email, password, username rejected with 400 BAD REQUEST
- ✅ **File Upload Validation** — Only image files allowed, max 5MB size limit

---

## 🏗️ How it Works — Architecture

> **For beginners:** When you make a request to this API, it goes through three layers before hitting the database. Think of it like ordering food — waiter takes the order (Controller), chef prepares it (Service), pantry stores the ingredients (Repository/Database).

```
📱 Client (Postman / Mobile App / Web App)
           │
           │  HTTP Request with JWT Token in header
           ▼
┌─────────────────────────────────────┐
│     JWT Filter                      │  ← Checks if token is valid before anything else
└────────────────┬────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────┐
│     Controller Layer                │  ← Receives request, calls service, returns response
│  (@RestController)                  │
└────────────────┬────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────┐
│     Service Layer                   │  ← All business logic lives here
│  (@Service)                         │    (validation, rules, DTO mapping)
└────────────────┬────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────┐
│     Repository Layer                │  ← Talks to the database
│  (JpaRepository)                   │
└────────────────┬────────────────────┘
                 │
                 ▼
┌─────────────────────────────────────┐
│     MySQL Database                  │  ← Stores all the data
└─────────────────────────────────────┘
```

**Key design decisions explained simply:**
- **Controllers** never contain business logic — they just pass requests to services and return responses
- **Services** contain all the rules — duplicate like check, self-follow guard, etc.
- **DTOs** are used everywhere — raw database entities (which contain passwords and sensitive data) are never sent to the client
- **ApiResponse** wraps every single response so the client always gets the same structure

---

## 📁 Project Structure

```
SocialApp/
│
├── src/main/java/com/example/SocialApp/
│   │
│   ├── SocialAppApplication.java          ← Entry point — starts the app
│   │
│   ├── config/
│   │   └── SecurityConfig.java            ← JWT filter chain, BCrypt bean, route protection
│   │
│   ├── Security/                          ← Everything JWT related lives here
│   │   ├── JwtService.java                ← Generates and validates JWT tokens
│   │   ├── JwtAuthenticationFilter.java   ← Runs on every request, checks the Bearer token
│   │   └── JwtAuthenticationEntryPoint.java ← Returns clean JSON when token is missing/invalid
│   │
│   ├── controllers/                       ← HTTP request handlers
│   │   ├── AuthController.java            ← /auth/register, /auth/login, /auth/me
│   │   ├── UserController.java            ← /user/** endpoints
│   │   ├── PostController.java            ← /posts/** endpoints
│   │   ├── CommentController.java         ← /comments/** endpoints
│   │   ├── LikeController.java            ← /likes/** endpoints
│   │   ├── FollowController.java          ← /follows/** endpoints
│   │   └── MessageController.java         ← /messages/** endpoints
│   │
│   ├── services/                          ← Business logic layer
│   │   ├── AuthService.java               ← Register, login, JWT generation
│   │   ├── JwtService.java                ← Token generation and validation
│   │   ├── UserService.java               ← User CRUD, image upload, stats
│   │   ├── FileUploadService.java         ← Handles image file saving to disk
│   │   ├── PostService.java               ← Post CRUD, enriched DTO assembly
│   │   ├── CommentService.java            ← Comment CRUD, count management
│   │   ├── LikeService.java               ← Like CRUD, duplicate prevention
│   │   ├── FollowService.java             ← Follow CRUD, self-follow guard
│   │   └── MessageService.java            ← Message CRUD, bidirectional chat
│   │
│   ├── repository/                        ← Database query interfaces
│   │   ├── UserRepository.java            ← findByEmail, existsByEmail
│   │   ├── PostRepository.java            ← findByUser_Id
│   │   ├── CommentRepository.java         ← findByPostId
│   │   ├── LikeRepository.java            ← countByPost_Id, existsByUser_IdAndPost_Id
│   │   ├── FollowRepository.java          ← countByFollowerId, countByFollowingId
│   │   └── MessageRepository.java         ← findBySender_Id, findByReceiver_Id
│   │
│   ├── models/                            ← JPA Entity classes (map to DB tables)
│   │   ├── User.java                      ← users table
│   │   ├── Post.java                      ← posts table
│   │   ├── Comment.java                   ← comments table
│   │   ├── Like.java                      ← likes table
│   │   ├── Follow.java                    ← follows table
│   │   └── Message.java                   ← messages table
│   │
│   ├── DTOs/                              ← Data Transfer Objects (what we send to client)
│   │   ├── AuthResponseDTO.java           ← token + user on login/register
│   │   ├── LoginRequestDTO.java           ← email + password for login
│   │   ├── LoginResponseDTO.java          ← message + token + userId + username + email
│   │   ├── UserDTO.java                   ← Safe user data (no password)
│   │   ├── UserRequestDTO.java            ← For image upload registration
│   │   ├── UserResponseDTO.java           ← userId + followers + following
│   │   ├── PostResponseDTO.java           ← Post with comments and likes
│   │   ├── CommentResponseDTO.java        ← id + text
│   │   └── MessageDTO.java                ← Full message with sender/receiver info
│   │
│   └── Utility/
│       ├── ApiResponse.java               ← Generic response wrapper { success, message, data, error }
│       └── GlobalExceptionHandler.java    ← Catches all exceptions, returns clean JSON
│
├── sql/                                   ← SQL scripts for database setup and sample data
│   ├── db.sql
│   ├── users.sql
│   ├── posts.sql
│   ├── comments.sql
│   ├── likes.sql
│   ├── follows.sql
│   └── messages.sql
│
├── uploads/                               ← Profile and cover images saved here
├── pom.xml                                ← Maven dependencies
├── README.md
├── CONTRIBUTING.md
└── SECURITY.md
```

---

## 🗄️ Database Schema

> **For beginners:** A schema is the structure of your database — what tables exist and what columns each table has.

### `users` table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT PK | Auto-generated unique ID |
| full_name | VARCHAR | User's full name e.g. Aryan Sonawane |
| username | VARCHAR | Display handle e.g. Xaryansonawane |
| email | VARCHAR | Used for login |
| password | VARCHAR | BCrypt hashed — never exposed in API |
| bio | VARCHAR | Short description about the user |
| profile_image | VARCHAR | File name of uploaded profile photo |
| cover_image | VARCHAR | File name of uploaded cover photo |
| message_count | INT | Total messages sent and received |
| is_active | BOOLEAN | Account active status, default true |
| created_at | DATETIME | When the account was created |
| updated_at | DATETIME | When the account was last updated |

### `posts` table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT PK | Auto-generated unique ID |
| user_id | BIGINT FK → users | Who created the post |
| caption | VARCHAR(1000) | The post text |
| image_url | VARCHAR | Optional image URL |
| like_count | BIGINT | Cached count of likes |
| comment_count | BIGINT | Cached count of comments |
| created_at | DATETIME | When the post was created |

### `comments` table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT PK | Auto-generated unique ID |
| post_id | BIGINT FK → posts | Which post this comment is on |
| user_id | BIGINT FK → users | Who wrote the comment |
| text | VARCHAR | The comment text |
| created_at | DATETIME | When the comment was posted |

### `likes` table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT PK | Auto-generated unique ID |
| post_id | BIGINT FK → posts | Which post was liked (NOT NULL) |
| user_id | BIGINT FK → users | Who liked the post (NOT NULL) |
| created_at | DATETIME | When the like happened |

### `follows` table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT PK | Auto-generated unique ID |
| follower_id | BIGINT FK → users | The user who is following |
| following_id | BIGINT FK → users | The user being followed |
| created_at | DATETIME | When the follow happened |

### `messages` table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT PK | Auto-generated unique ID |
| sender_id | BIGINT FK → users | Who sent the message |
| receiver_id | BIGINT FK → users | Who received the message |
| content | VARCHAR(2000) | The message text |
| is_seen | BOOLEAN | Has the message been read, default false |
| created_at | DATETIME | When the message was sent |

---

## 📡 API Reference

### Base URL
```
http://localhost:8080
```

### 🔑 Authentication Endpoints — No token needed

| Method | Endpoint | Description | Status Code |
|--------|----------|-------------|-------------|
| POST | /auth/register | Register a new user | 201 |
| POST | /auth/login | Login and get JWT token | 200 |
| GET | /auth/me | Get currently logged in user | 200 |

### 👤 User Endpoints — Token required

| Method | Endpoint | Description | Status Code |
|--------|----------|-------------|-------------|
| GET | /user | Get all users as UserDTO | 200 |
| POST | /user | Register user without image | 201 |
| POST | /user/register | Register user with image upload | 201 |
| GET | /user/{id} | Get user by ID | 200 |
| GET | /user/{id}/stats | Get user follower and following counts | 200 |
| GET | /user/image/{fileName} | Serve uploaded image file | 200 |

### 📝 Post Endpoints — Token required

| Method | Endpoint | Description | Status Code |
|--------|----------|-------------|-------------|
| GET | /posts | Get all posts with comments and likes | 200 |
| GET | /posts/{id} | Get single post by ID | 200 |
| GET | /posts/users/{userId} | Get all posts by a specific user | 200 |

### 💬 Comment Endpoints — Token required

| Method | Endpoint | Description | Status Code |
|--------|----------|-------------|-------------|
| POST | /comments | Add a comment to a post | 200 |
| GET | /comments/post/{postId} | Get all comments for a post | 200 |
| DELETE | /comments/{id} | Delete a comment | 200 |

### ❤️ Like Endpoints — Token required

| Method | Endpoint | Description | Status Code |
|--------|----------|-------------|-------------|
| POST | /likes | Like a post — duplicate safe | 200 / 400 |
| GET | /likes/post/{postId} | Get all likes for a post | 200 |
| GET | /likes/user/{userId} | Get all likes by a user | 200 |
| DELETE | /likes/{id} | Remove a like | 200 |

### 🤝 Follow Endpoints — Token required

| Method | Endpoint | Description | Status Code |
|--------|----------|-------------|-------------|
| POST | /follows | Follow a user — self follow blocked | 200 / 400 |
| GET | /follows/followers/{id} | Get all followers of a user | 200 |
| GET | /follows/following/{id} | Get all users followed by a user | 200 |
| DELETE | /follows/{id} | Unfollow a user | 200 |

### 💌 Message Endpoints — Token required

| Method | Endpoint | Description | Status Code |
|--------|----------|-------------|-------------|
| POST | /messages | Send a message | 200 |
| GET | /messages/{id} | Get message by ID | 200 |
| GET | /messages/sender/{id} | Get all messages sent by a user | 200 |
| GET | /messages/receiver/{id} | Get all messages received by a user | 200 |
| GET | /messages/between/{id1}/{id2} | Get full conversation between two users | 200 |
| GET | /messages/unseen/{id} | Get unread messages for a user | 200 |
| PATCH | /messages/{id}/seen | Mark a message as seen | 200 |
| DELETE | /messages/{id} | Delete a message | 200 |

---

## 📦 How to Use the API — Request and Response Examples

> **For beginners:** Every API response in this project follows the same structure. Once you understand it, you will always know what to expect.
>
> **Success response always looks like:**
> ```json
> { "success": true, "message": "SOME MESSAGE", "data": { ... }, "error": null }
> ```
> **Error response always looks like:**
> ```json
> { "success": false, "message": "WHAT WENT WRONG", "data": null, "error": "ERROR CODE" }
> ```

---

### Step 1 — Register

```http
POST /auth/register
Content-Type: application/json
```
```json
{
    "fullName": "Aryan Sonawane",
    "username": "Xaryansonawane",
    "email": "aryanvipinsonawane@gmail.com",
    "password": "Aryan@2008",
    "bio": "Spring Boot Developer"
}
```

**Response:**
```json
{
    "success": true,
    "message": "USER REGISTERED SUCCESSFULLY",
    "data": {
        "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhcnlhbnZpcGluc29uYXdhbmVAZ21haWwuY29tIn0...",
        "type": "Bearer",
        "user": {
            "id": 1,
            "fullName": "Aryan Sonawane",
            "username": "Xaryansonawane",
            "email": "aryanvipinsonawane@gmail.com",
            "bio": "Spring Boot Developer"
        }
    },
    "error": null
}
```

---

### Step 2 — Login

```http
POST /auth/login
Content-Type: application/json
```
```json
{
    "email": "aryanvipinsonawane@gmail.com",
    "password": "Aryan@2008"
}
```

**Response:**
```json
{
    "success": true,
    "message": "LOGIN SUCCESSFUL!",
    "data": {
        "message": "LOGIN SUCCESSFUL!",
        "token": "eyJhbGciOiJIUzM4NCJ9...",
        "userId": 1,
        "username": "Xaryansonawane",
        "email": "aryanvipinsonawane@gmail.com"
    },
    "error": null
}
```

> ⚠️ **Important:** Copy the token from this response. You need to send it in every other request like this:
> ```
> Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...
> ```

---

### Step 3 — Use the Token

**Get all posts (with Authorization header):**
```http
GET /posts
Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...
```

**Response:**
```json
{
    "success": true,
    "message": "POSTS FETCHED SUCCESSFULLY",
    "data": [
        {
            "id": 1,
            "caption": "Exploring Pune on my bike today!",
            "likeCount": 5,
            "comments": [
                { "id": 1, "text": "Amazing ride!" },
                { "id": 2, "text": "Pune roads are the best!" }
            ],
            "likedBy": ["priya_kulk", "rohit_patil", "sneha_jadh"]
        }
    ],
    "error": null
}
```

---

### Register with Image Upload

```http
POST /user/register
Content-Type: multipart/form-data
Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...
```

| Key | Type | Value |
|-----|------|-------|
| fullName | text | Aryan Sonawane |
| username | text | Xaryansonawane |
| email | text | aryanvipinsonawane@gmail.com |
| password | text | Aryan@2008 |
| bio | text | Spring Boot Developer |
| profileImage | file | select your image |
| coverImage | file | select your image |

---

### Get Conversation Between Two Users

```http
GET /messages/between/1/2
Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...
```

**Response:**
```json
{
    "success": true,
    "message": "CONVERSATION FETCHED SUCCESSFULLY",
    "data": [
        {
            "id": 1,
            "senderId": 1,
            "senderName": "Aryan Sonawane",
            "senderUsername": "Xaryansonawane",
            "receiverId": 24,
            "receiverName": "Manisha Sonawane",
            "receiverUsername": "manisha_s",
            "content": "Mummy! Miss you so much ❤️ When are you coming home?",
            "seen": false,
            "createdAt": "2026-03-16T22:48:38"
        },
        {
            "id": 2,
            "senderId": 24,
            "senderName": "Manisha Sonawane",
            "senderUsername": "manisha_s",
            "receiverId": 1,
            "receiverName": "Aryan Sonawane",
            "receiverUsername": "Xaryansonawane",
            "content": "Arya beta! I miss you too my sunshine 🌞 Come home this weekend!",
            "seen": false,
            "createdAt": "2026-03-16T22:48:38"
        }
    ],
    "error": null
}
```

---

### Error Response Examples

**Wrong password:**
```json
{
    "success": false,
    "message": "INVALID PASSWORD",
    "data": null,
    "error": "401 UNAUTHORIZED"
}
```

**User not found:**
```json
{
    "success": false,
    "message": "USER NOT FOUND WITH ID: 999",
    "data": null,
    "error": "404 NOT_FOUND"
}
```

**Trying to like a post twice:**
```json
{
    "success": false,
    "message": "POST ALREADY LIKED BY USER",
    "data": null,
    "error": "400 BAD_REQUEST"
}
```

---

## 🔒 Authentication — How JWT Works in this Project

> **For beginners:** JWT (JSON Web Token) is like a digital ID card. When you login, the server gives you a token. You show this token on every request to prove who you are. The server does not need to remember you — the token contains all the information.

Here is the exact flow in this project:

```
1. User sends POST /auth/login with email and password
         │
         ▼
2. AuthService verifies the password using BCrypt
         │
         ▼
3. JwtService generates a token containing the user's email
         │
         ▼
4. Token is returned to the client in the response
         │
         ▼
5. Client stores the token and sends it in every request:
   Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...
         │
         ▼
6. JwtAuthenticationFilter intercepts every request
         │
         ▼
7. Filter extracts token from the Authorization header
         │
         ▼
8. JwtService validates the token (is it expired? is it valid?)
         │
         ▼
9. If valid → request goes through to the Controller
   If invalid → JwtAuthenticationEntryPoint returns 401 UNAUTHORIZED
```

**Protected routes — require token:**
```
All routes EXCEPT:
  POST /auth/register
  POST /auth/login
  GET  /uploads/**
```

**Token expiry:** 24 hours (86400000 milliseconds)

---

## 🧠 Business Rules and Validations

All business rules are enforced in the **service layer** — never in controllers. This keeps controllers clean and rules testable.

### Duplicate Like Prevention
```java
// LikeService.java
boolean alreadyLiked = likeRepository.existsByUser_IdAndPost_Id(userId, postId);
if (alreadyLiked) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "POST ALREADY LIKED BY USER");
}
```

### Self Follow Prevention
```java
// FollowService.java
if (follower.getId().equals(following.getId())) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER CANNOT FOLLOW THEMSELVES");
}
```

### Self Message Prevention
```java
// MessageService.java
if (sender.getId().equals(receiver.getId())) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CANNOT SEND MESSAGE TO YOURSELF");
}
```

### Email Already Exists Prevention
```java
// AuthService.java
if (userRepository.existsByEmail(user.getEmail())) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EMAIL ALREADY EXISTS");
}
```

### Input Validation
```java
// UserService.java
if (user.getEmail() == null || user.getEmail().isEmpty()) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EMAIL CANNOT BE EMPTY");
}
```

### File Upload Validation
```java
// FileUploadService.java
// Only image files allowed
if (!contentType.startsWith("image/")) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ONLY IMAGE FILES ARE ALLOWED");
}
// Max 5MB
if (file.getSize() > 5 * 1024 * 1024) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "FILE SIZE CANNOT EXCEED 5MB");
}
```

---

## 📐 DTOs — Why We Never Return Raw Entities

> **For beginners:** A DTO (Data Transfer Object) is a simple class that holds only the data we want to send to the client. We never send raw JPA entities because they contain sensitive data like passwords and unnecessary fields.

| DTO | Fields | Why we use it |
|-----|--------|---------------|
| `AuthResponseDTO` | token, type, user | Returned on login and register with JWT token |
| `LoginResponseDTO` | message, token, userId, username, email | Returned from login endpoint |
| `UserDTO` | id, username, bio, profileImage, follower, following | Safe user info — no password |
| `UserRequestDTO` | fullName, username, email, password, bio, profileImage, coverImage | For image upload registration |
| `UserResponseDTO` | userId, followers, following | Just the stats |
| `PostResponseDTO` | id, caption, likeCount, comments, likedBy | Enriched post with all related data |
| `CommentResponseDTO` | id, text | Just the essential comment info |
| `MessageDTO` | id, senderId, senderName, senderUsername, receiverId, receiverName, receiverUsername, content, seen, createdAt | Full message with sender and receiver info |
| `LoginRequestDTO` | email, password | What the client sends to login |

---

## ⚠️ Error Handling

`GlobalExceptionHandler` with `@ControllerAdvice` catches all exceptions and returns clean JSON:

```java
@ExceptionHandler(Exception.class)
public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error("SOMETHING WENT WRONG", ex.getMessage()));
}
```

### Complete Error Reference

| Scenario | HTTP Status | Message |
|----------|-------------|---------|
| User not found | 404 NOT FOUND | USER NOT FOUND WITH ID: {id} |
| Email not found on login | 404 NOT FOUND | USER NOT FOUND WITH EMAIL: {email} |
| Wrong password | 401 UNAUTHORIZED | INVALID PASSWORD |
| Email already exists | 400 BAD REQUEST | EMAIL ALREADY EXISTS |
| Post already liked | 400 BAD REQUEST | POST ALREADY LIKED BY USER |
| User follows themselves | 400 BAD REQUEST | USER CANNOT FOLLOW THEMSELVES |
| User messages themselves | 400 BAD REQUEST | CANNOT SEND MESSAGE TO YOURSELF |
| Empty email | 400 BAD REQUEST | EMAIL CANNOT BE EMPTY |
| Empty password | 400 BAD REQUEST | PASSWORD CANNOT BE EMPTY |
| Empty username | 400 BAD REQUEST | USERNAME CANNOT BE EMPTY |
| File not an image | 400 BAD REQUEST | ONLY IMAGE FILES ARE ALLOWED |
| File too large | 400 BAD REQUEST | FILE SIZE CANNOT EXCEED 5MB |
| No JWT token sent | 401 UNAUTHORIZED | UNAUTHORIZED. PLEASE LOGIN AND SEND A VALID TOKEN |
| Unhandled exception | 500 INTERNAL SERVER ERROR | SOMETHING WENT WRONG |

---

## ⚙️ Getting Started — Run it Locally

> **For beginners:** Follow these steps exactly in order. Do not skip any step.

### What you need installed first

- **Java 21** — [Download here](https://www.oracle.com/java/technologies/downloads/)
- **MySQL 8.0** — [Download here](https://dev.mysql.com/downloads/)
- **Maven 3.x** — [Download here](https://maven.apache.org/download.cgi)
- **Postman** — [Download here](https://www.postman.com/downloads/) (to test the API)
- **IntelliJ IDEA** — [Download here](https://www.jetbrains.com/idea/) (recommended IDE)

### Step 1 — Clone the repository

```bash
git clone https://github.com/Xaryansonawane/Social-Media-REST-API.git
cd Social-Media-REST-API
```

### Step 2 — Create the MySQL database

Open MySQL Workbench or terminal and run:
```sql
CREATE DATABASE SocialApp_DB;
```

> Hibernate will automatically create all the tables when you start the app — you do not need to create them manually.

### Step 3 — Configure the application

Open `src/main/resources/application.properties` and set your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/SocialApp_DB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080

file.upload-dir=uploads
jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration=86400000
```

### Step 4 — Create uploads folder

Create an empty folder called `uploads` in the project root:
```
Social-Media-REST-API/
└── uploads/     ← create this empty folder
```

### Step 5 — Run the application

**Option A — Using IntelliJ IDEA:**
1. Open the project in IntelliJ
2. Click the green Run button
3. Wait for `Started SocialAppApplication` in the console

**Option B — Using terminal:**
```bash
mvn spring-boot:run
```

### Step 6 — Test the API

The API is now live at `http://localhost:8080`

**Quick test — Register a user:**
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Test User","username":"testuser","email":"test@gmail.com","password":"Test@123"}'
```

**Or use Postman:**
```
POST http://localhost:8080/auth/register
Body → raw → JSON
{
    "fullName": "Test User",
    "username": "testuser",
    "email": "test@gmail.com",
    "password": "Test@123"
}
```

---

## ⚙️ Configuration Reference

| Property | Default Value | Description |
|----------|---------------|-------------|
| spring.datasource.url | jdbc:mysql://localhost:3306/SocialApp_DB | MySQL connection string |
| spring.datasource.username | root | Your MySQL username |
| spring.datasource.password | — | Your MySQL password |
| spring.jpa.hibernate.ddl-auto | update | Auto creates and updates tables |
| spring.jpa.show-sql | true | Logs all SQL queries to console |
| spring.jpa.open-in-view | false | Disables lazy loading warning |
| file.upload-dir | uploads | Folder where images are saved |
| spring.servlet.multipart.max-file-size | 5MB | Max size per uploaded file |
| spring.servlet.multipart.max-request-size | 10MB | Max total request size |
| jwt.secret | — | Secret key for signing JWT tokens |
| jwt.expiration | 86400000 | Token expiry in milliseconds (24 hours) |
| server.port | 8080 | Port the app runs on |

---

## 🗄️ Sample Data

The `sql/` folder contains scripts to populate the database with sample data:

| Entity | Count |
|--------|-------|
| Users | 27 |
| Posts | 80 |
| Comments | 88 |
| Likes | 349 |
| Follows | 91 |
| Messages | 30 |

Run them in this order:
```sql
source sql/db.sql
source sql/users.sql
source sql/posts.sql
source sql/comments.sql
source sql/likes.sql
source sql/follows.sql
source sql/messages.sql
```

---

## 🛣️ Roadmap

Things planned for future versions:

- [ ] Refresh token support — so users stay logged in without re-entering credentials
- [ ] Pagination and sorting — for posts, comments, messages (using Spring `Pageable`)
- [ ] Post image upload — upload images when creating posts
- [ ] Post update and delete endpoints
- [ ] User profile update endpoint
- [ ] Search users by username or full name
- [ ] Real time messaging with WebSocket and STOMP
- [ ] Notification system — get notified when someone likes, follows, or comments
- [ ] Duplicate follow prevention — mirror the duplicate like guard
- [ ] Unit and integration tests with JUnit 5 and MockMvc
- [ ] Swagger and OpenAPI auto generated documentation
- [ ] Docker and docker-compose support
- [ ] Bean Validation with @Valid on all request bodies
- [ ] Story feature with 24 hour expiry
- [ ] Feed algorithm — show only posts from followed users

---

## 👨‍💻 Author

**Aryan Sonawane**

A 3rd year Computer Engineering student passionate about backend development and building real-world projects with Spring Boot.

- GitHub: [@Xaryansonawane](https://github.com/Xaryansonawane)
- LeetCode: [Xaryansonawane](https://leetcode.com/u/Xaryansonawane/)
- HackerRank: [Xaryansonawane](https://www.hackerrank.com/profile/Xaryansonawane)
- Instagram: [@Xaryansonawane](https://instagram.com/Xaryansonawane)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

⭐ If you found this project helpful or learned something from it, please give it a star on GitHub — it really helps!
