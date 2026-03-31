---
name: "💡 Feature Request"
about: "Suggest a new feature or improvement for the Social Media RESTful API"
title: "[FEATURE] "
labels: ["enhancement"]
assignees: ["Xaryansonawane"]
---

## 💡 Feature Description

A clear and concise description of the feature you want.

---

## 🔍 Problem Statement

What problem does this feature solve?

Example:  
Currently there is no JWT authentication. Users have to send credentials with every request.

---

## ✅ Proposed Solution

How should this feature work?

Example:  
After login return a JWT token. User sends token in Authorization header for protected routes.

---

## 🌐 API Design

What endpoint and response should it have?

**Endpoint:**  
POST /auth/login  

**Headers:**  
Authorization: Bearer <JWT_TOKEN>  
Content-Type: application/json  

**Request Body:**

```json
{
  "email": "example@gmail.com",
  "password": "password123"
}
```

**Expected Response:**

```json
{
  "success": true,
  "message": "LOGIN SUCCESSFUL!",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9..."
  },
  "error": null
}
```

---

## 🔄 Alternatives Considered

Have you considered any alternative solutions or features?

---

## 📊 Impact

How would this feature improve the project?

- [ ] Security improvement  
- [ ] Performance improvement  
- [ ] New functionality  
- [ ] Better user experience  
- [ ] Code quality improvement  

---

## 📝 Additional Context

Add any other context, screenshots or references about the feature request here.
