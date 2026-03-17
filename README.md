# 🌐 Social Media REST API

A full-featured **Social Media REST API** built with **Spring Boot 3.4.3**, **Java 21**, and **MySQL**. This project includes user authentication, posts, comments, likes, follows, and a direct messaging system with clean DTO responses and global exception handling.

---

## 🚀 Tech Stack

| Technology | Version |
|-----------|---------|
| Java | 21 |
| Spring Boot | 3.4.3 |
| Spring Security | 6.4.3 |
| MySQL | 8.0.45 |
| Hibernate JPA | 6.6.8 |
| BCrypt | Spring Security |
| Maven | 3.x |

---

## ✨ Features

- ✅ User Registration and Login with BCrypt password hashing
- ✅ Spring Security configuration
- ✅ Post creation and fetching with likes and comments
- ✅ Follow and unfollow system
- ✅ Like and unlike posts with duplicate prevention
- ✅ Comment on posts
- ✅ Direct messaging between users
- ✅ Unread messages and mark as seen
- ✅ User stats (followers and following count)
- ✅ ApiResponse wrapper for consistent API responses
- ✅ GlobalExceptionHandler with proper HTTP status codes
- ✅ ResponseStatusException for 404, 400, 401 error handling
- ✅ Password hidden from all API responses using @JsonProperty

---

## 📁 Project Structure
```
SocialApp/
├── src/main/java/com/example/SocialApp/
│   ├── Config/
│   │   └── SecurityConfig.java
│   ├── controllers/
│   │   ├── CommentController.java
│   │   ├── FollowController.java
│   │   ├── LikeController.java
│   │   ├── MessageController.java
│   │   ├── PostController.java
│   │   └── UserController.java
│   ├── DTOs/
│   │   ├── CommentResponseDTO.java
│   │   ├── LoginRequestDTO.java
│   │   ├── LoginResponseDTO.java
│   │   ├── MessageDTO.java
│   │   ├── PostResponseDTO.java
│   │   ├── UserDTO.java
│   │   └── UserResponseDTO.java
│   ├── models/
│   │   ├── Comment.java
│   │   ├── Follow.java
│   │   ├── Like.java
│   │   ├── Message.java
│   │   ├── Post.java
│   │   └── User.java
│   ├── repository/
│   │   ├── CommentRepository.java
│   │   ├── FollowRepository.java
│   │   ├── LikeRepository.java
│   │   ├── MessageRepository.java
│   │   ├── PostRepository.java
│   │   └── UserRepository.java
│   ├── services/
│   │   ├── AuthService.java
│   │   ├── CommentService.java
│   │   ├── FollowService.java
│   │   ├── LikeService.java
│   │   ├── MessageService.java
│   │   ├── PostService.java
│   │   └── UserService.java
│   └── Utility/
│       ├── ApiResponse.java
│       └── GlobalExceptionHandler.java
├── sql/
│   ├── db.sql
│   ├── users.sql
│   ├── posts.sql
│   ├── comments.sql
│   ├── likes.sql
│   ├── follows.sql
│   └── messages.sql
├── pom.xml
└── README.md
```

---

## 📊 Database Schema

| Table | Description |
|-------|-------------|
| users | Stores user information |
| posts | Stores posts created by users |
| comments | Stores comments on posts |
| likes | Stores likes on posts |
| follows | Stores follow relationships |
| messages | Stores direct messages between users |

---

## 🔗 API Endpoints

### 👤 User
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /user | Get all users |
| POST | /user | Register new user |
| GET | /user/{id} | Get user by ID |
| GET | /user/{id}/stats | Get user stats |
| POST | /user/login | Login user |

### 📝 Posts
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /posts | Get all posts |
| GET | /posts/{id} | Get post by ID |
| GET | /posts/users/{userId} | Get posts by user |

### 💬 Comments
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /comments | Add comment |
| GET | /comments/post/{postId} | Get comments by post |
| DELETE | /comments/{id} | Delete comment |

### ❤️ Likes
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /likes | Add like |
| GET | /likes/post/{postId} | Get likes by post |
| GET | /likes/user/{userId} | Get likes by user |
| DELETE | /likes/{id} | Remove like |

### 👥 Follows
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /follows | Follow user |
| GET | /follows/followers/{id} | Get followers |
| GET | /follows/following/{id} | Get following |
| DELETE | /follows/{id} | Unfollow user |

### 💌 Messages
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /messages | Send message |
| GET | /messages/{id} | Get message by ID |
| GET | /messages/sender/{id} | Get messages by sender |
| GET | /messages/receiver/{id} | Get messages by receiver |
| GET | /messages/between/{id1}/{id2} | Get conversation |
| GET | /messages/unseen/{id} | Get unread messages |
| PATCH | /messages/{id}/seen | Mark as seen |
| DELETE | /messages/{id} | Delete message |

---

## ⚙️ Setup and Installation

### Prerequisites
- Java 21
- MySQL 8.0
- Maven 3.x

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/Xaryansonawane/Social-Media-REST-API.git
cd Social-Media-REST-API
```

**2. Create the database**
```sql
CREATE DATABASE SocialApp_DB;
```

**3. Configure application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/SocialApp_DB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080
```

**4. Run the application**
```bash
mvn spring-boot:run
```

**5. Test the API**
```
GET http://localhost:8080/posts
POST http://localhost:8080/user/login
```

---

## 📱 Sample API Response
```json
{
    "success": true,
    "message": "LOGIN SUCCESSFUL!",
    "data": {
        "message": "LOGIN SUCCESSFUL!",
        "userId": 1,
        "username": "Xaryansonawane",
        "email": "aryanvipinsonawane@gmail.com"
    },
    "error": null
}
```

---

## 🗄️ Sample Data

| Entity | Count |
|--------|-------|
| Users | 27 |
| Posts | 80 |
| Comments | 88 |
| Likes | 349 |
| Follows | 91 |
| Messages | 30 |

---

## 👨‍💻 Author

**Aryan Sonawane**
- GitHub: [@Xaryansonawane](https://github.com/Xaryansonawane)
- LeetCode: [Xaryansonawane](https://leetcode.com/u/Xaryansonawane/)
- Instagram: [@Xaryansonawane](https://instagram.com/Xaryansonawane)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
