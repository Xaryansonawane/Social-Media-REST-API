---

name: "🐛 Bug Report"
about: "Report a bug to help improve the Social Media RESTful API"
title: "[BUG] "
labels: ["bug"]
assignees: ["Xaryansonawane"]
-----------------------------

## 🐛 Bug Description

Provide a clear and concise description of the issue.

---

## 🔁 Steps to Reproduce

1. Send request to endpoint `...`
2. Include headers (if required):
   Authorization: Bearer <JWT_TOKEN>
3. Provide request body `...`
4. Observe the response

---

## ✅ Expected Behavior

Describe what you expected to happen.

---

## ❌ Actual Behavior

Describe what actually happened.

---

## 📋 Error Message / Logs

```
Paste full error message, stack trace, or logs here
```

---

## 🌐 API Request Details

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

**Response Received:**

```json
{
  "success": false,
  "message": "ERROR MESSAGE",
  "data": null,
  "error": "ERROR_CODE"
}
```

---

## 🔐 Authentication Context

* Is the endpoint protected? (Yes/No)
* Was a valid JWT token provided? (Yes/No)
* Token expired? (Yes/No)

---

## 💻 Environment

* Java Version: 21
* Spring Boot Version: 3.4.3
* MySQL Version: 8.0.x
* OS: Windows / macOS / Linux
* Client Tool: Postman / Browser / Curl

---

## 📸 Screenshots / Proof

Add screenshots or logs if applicable.

---

## 📝 Additional Context

Add any additional information here.

---

## ✔️ Checklist

* [ ] I have checked existing issues
* [ ] I have provided complete details
* [ ] I have included request/response
* [ ] Issue is reproducible
