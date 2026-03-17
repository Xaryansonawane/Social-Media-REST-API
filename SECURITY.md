# 🔐 Security Policy

## 📦 Supported Versions

The following versions of Social Media REST API are currently supported with security updates:

| Version | Supported |
|---------|-----------|
| 1.0.x | ✅ |

---

## 🛡️ Security Features

This project includes the following security measures:

- ✅ **BCrypt Password Hashing** — all passwords are hashed using BCrypt before storing in database
- ✅ **@JsonProperty WRITE_ONLY** — passwords are never returned in any API response
- ✅ **Spring Security** — all endpoints protected except `/user/**`
- ✅ **CSRF Disabled** — for REST API stateless communication
- ✅ **ResponseStatusException** — proper HTTP status codes for all errors
- ✅ **Input Validation** — empty email, password and username are rejected with 400 BAD REQUEST
- ✅ **Duplicate Like Prevention** — users cannot like the same post twice
- ✅ **Self Follow Prevention** — users cannot follow themselves
- ✅ **Self Message Prevention** — users cannot send messages to themselves

---

## 🚨 Reporting a Vulnerability

If you discover a security vulnerability in this project please follow these steps:

**DO NOT** create a public GitHub issue for security vulnerabilities.

### Steps to Report

**1. Contact directly via:**
- GitHub: [@Xaryansonawane](https://github.com/Xaryansonawane)
- Instagram: [@Xaryansonawane](https://instagram.com/Xaryansonawane)

**2. Include the following in your report:**
- Description of the vulnerability
- Steps to reproduce
- Potential impact
- Suggested fix if you have one

**3. What to expect:**
- You will receive a response within **48 hours**
- If the vulnerability is confirmed it will be fixed as soon as possible
- You will be credited in the fix commit if you wish

---

## ⚠️ Known Security Limitations

As this is a learning project the following security features are not yet implemented:

- ❌ JWT Token Authentication — currently using Basic Auth
- ❌ Rate Limiting — no request rate limiting implemented
- ❌ Email Verification — no email verification on registration
- ❌ Password Reset — no forgot password feature yet
- ❌ HTTPS — only HTTP supported locally

These will be added in future versions.

---

## 🔒 Environment Variables

Never commit sensitive data to GitHub. Always use environment variables or keep your `application.properties` in `.gitignore`:
```properties
# Never commit these values
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

---

## 👨‍💻 Author

**Aryan Sonawane**
- GitHub: [@Xaryansonawane](https://github.com/Xaryansonawane)
- LeetCode: [Xaryansonawane](https://leetcode.com/u/Xaryansonawane/)
- HackerRank: [Xaryansonawane](https://www.hackerrank.com/profile/Xaryansonawane)
- Instagram: [@Xaryansonawane](https://instagram.com/Xaryansonawane)
```

Replace the default GitHub `SECURITY.md` content with this. Then commit with:
```
Added SECURITY.md - Security policy and vulnerability reporting guidelines
