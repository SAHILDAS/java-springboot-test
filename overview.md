# 📝 Spring Boot Blog Platform – Backend Architecture Plan

## 📌 Overview

This project is a **production-ready backend system** built using **Spring Boot** that supports:

- User Authentication & Authorization  
- Blog creation & management  
- Draft / Published blogs  
- Public & Private visibility  
- Blog likes & comments  
- Secure APIs for frontend integration  
- PostgreSQL persistence  
- Dockerized deployment  
- Unit & integration testing  

This backend is designed to be **frontend-agnostic** and **scalable**.

---

## 🧱 Tech Stack

| Layer        | Technology |
|-------------|------------|
| Language     | Java 17 |
| Framework    | Spring Boot |
| Security     | Spring Security + JWT |
| Database     | PostgreSQL |
| ORM          | Spring Data JPA |
| Validation   | Hibernate Validator |
| Testing      | JUnit 5, Mockito |
| API Docs     | Swagger (OpenAPI) |
| Container    | Docker & Docker Compose |
| Migrations   | Flyway |

---

## 🗂️ High-Level Modules

auth → login / register / jwt
user → profile, status
blog → create, edit, publish, view
comment → blog comments
like → blog likes
common → exceptions, utils, responses



---


---

## 👤 User Capabilities

- Register / Login  
- Active / Inactive status  
- Own blogs  
- Like & comment on public blogs  

---

## ✍️ Blog Capabilities

### Blog States

| State     | Description |
|----------|-------------|
| DRAFT     | Saved but not visible |
| PUBLISHED | Publicly visible |
| PRIVATE   | Visible only to author |

### Blog Visibility

- **PUBLIC** → visible to all users  
- **PRIVATE** → visible only to owner  

---

## 🗄️ Database Design

### USERS, BLOGS, COMMENTS, LIKES, 

```sql
users (
  id UUID PK,
  name,
  email UNIQUE,
  password,
  is_active,
  created_at
)


blogs (
  id UUID PK,
  title,
  content,
  status ENUM('DRAFT','PUBLISHED'),
  visibility ENUM('PUBLIC','PRIVATE'),
  author_id UUID FK,
  created_at,
  updated_at
)

comments (
  id UUID PK,
  blog_id UUID FK,
  user_id UUID FK,
  comment_text,
  created_at
)


likes (
  id UUID PK,
  blog_id UUID FK,
  user_id UUID FK,
  created_at,
  UNIQUE(blog_id, user_id)
)
```



## 🔌 API Design (Backend Contract)

This section defines the REST API contracts exposed by the backend.  
All APIs are **JSON-based**, **JWT secured**, and follow RESTful conventions.

---

## 📝 Blog APIs

| Method | Endpoint | Description | Access |
|------|----------|-------------|--------|
| POST | `/api/blogs` | Create a new blog | Authenticated |
| PUT | `/api/blogs/{id}` | Update an existing blog | Blog Owner |
| PATCH | `/api/blogs/{id}/publish` | Publish a blog | Blog Owner |
| PATCH | `/api/blogs/{id}/visibility` | Change blog visibility (PUBLIC / PRIVATE) | Blog Owner |
| GET | `/api/blogs/{id}` | Get blog details by ID | Public / Owner |
| GET | `/api/blogs` | List blogs with pagination | Public |

### Notes
- Pagination supported using `page`, `size`, and `sort`
- Private blogs are accessible **only to the owner**
- Draft blogs are not visible publicly

---

## 💬 Comment APIs

| Method | Endpoint | Description | Access |
|------|----------|-------------|--------|
| POST | `/api/blogs/{id}/comments` | Add a comment to a blog | Authenticated |
| GET | `/api/blogs/{id}/comments` | Get comments for a blog | Public |

### Notes
- Comments allowed only on **published public blogs**
- Pagination supported for comment listing

---

## ❤️ Like APIs

| Method | Endpoint | Description | Access |
|------|----------|-------------|--------|
| POST | `/api/blogs/{id}/like` | Like a blog | Authenticated |
| DELETE | `/api/blogs/{id}/like` | Remove like from a blog | Authenticated |

### Notes
- One like per user per blog
- Duplicate likes are prevented at DB level

---

## 🔐 Security & Authorization Rules

| Action | Required Role |
|------|---------------|
| Create blog | Authenticated User |
| Edit blog | Blog Owner |
| Publish blog | Blog Owner |
| View public blogs | Anyone |
| View private blog | Blog Owner |
| Like blog | Authenticated User |
| Comment on blog | Authenticated User |

---

## 📦 Standard API Response Format

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    "id": "uuid",
    "resource": "blog"
  }
}
```

### Error Response Example

```json
{
  "success": false,
  "error": {
    "code": "ACCESS_DENIED",
    "message": "You are not allowed to access this resource"
  }
}


```