---
name: "🐛 Bug Report"
about: "Report a bug to help improve the Social Media RESTful API"
title: "[BUG] "
labels: ["bug"]
assignees: ["Xaryansonawane"]
---

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
  "success": false,
  "message": "ERROR MESSAGE",
  "data": null,
  "error": "ERROR_CODE"
}
