# 🔔 Real-Time Notification System

A distributed microservices backend that sends real-time notifications and emails when users register or log in. Built with Spring Boot, Apache Kafka, and Docker.

---

## 📌 About The Project

This system demonstrates event-driven microservices architecture. When a user registers or logs in, an event is published to Kafka. Two independent services consume that event — one saves the notification to MySQL, the other sends an email via Mailtrap.

---

## ⚙️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 3.x | Backend framework |
| Spring Security + JWT | Authentication and authorization |
| Apache Kafka | Event streaming between services |
| Spring Data JPA | Database interaction |
| MySQL | Persistent storage |
| JavaMailSender + Mailtrap | Email delivery |
| Docker + Docker Compose | Kafka infrastructure |
| Lombok | Boilerplate reduction |
| Maven | Dependency management |

---

## 🏗️ Architecture

```
User (Postman)
      ↓
user-service (port 8081)
  → Spring Security + JWT
  → publishes event to Kafka
      ↓
Kafka Topic: "notification-events"
      ↓                    ↓
notification-service    email-service
(port 8082)             (port 8083)
saves to MySQL          sends email via Mailtrap
```

### Microservices:

```
notification-system/
├── user-service/           → registration, login, JWT, Kafka producer
├── notification-service/   → Kafka consumer, saves to MySQL
├── email-service/          → Kafka consumer, sends emails
├── docker-compose.yml      → runs Kafka + Zookeeper
└── init.sql                → creates MySQL databases
```

---

## 🚀 Features

- ✅ User registration and login with JWT authentication
- ✅ Role-based access control (ROLE_USER, ROLE_ADMIN)
- ✅ Event-driven architecture using Apache Kafka
- ✅ Persistent notification storage in MySQL
- ✅ Real-time email delivery via Mailtrap
- ✅ Independent microservices with separate databases
- ✅ Global exception handling across all services
- ✅ Dockerized infrastructure (Kafka + Zookeeper)
- ✅ Dockerfiles ready for full containerized deployment

---

## 📋 Prerequisites

- Java 21
- Maven
- MySQL
- Docker Desktop
- Free [Mailtrap](https://mailtrap.io) account

---

## 🔧 Setup & Installation

### Step 1 — Clone the repository

```bash
git clone https://github.com/yourusername/notification-system.git
cd notification-system
```

### Step 2 — Create MySQL databases

```sql
CREATE DATABASE user_db;
CREATE DATABASE notification_db;
```

### Step 3 — Configure each service

Copy the example properties files:

```bash
cp user-service/src/main/resources/application.properties.example user-service/src/main/resources/application.properties

cp notification-service/src/main/resources/application.properties.example notification-service/src/main/resources/application.properties

cp email-service/src/main/resources/application.properties.example email-service/src/main/resources/application.properties
```

Fill in your real values in each `application.properties`.

### Step 4 — Start Kafka with Docker

```bash
docker-compose up -d
```

### Step 5 — Run all 3 services

Start in this order:
1. `notification-service` → port 8082
2. `email-service` → port 8083
3. `user-service` → port 8081

---

## 📡 API Endpoints

### User Service (port 8081)

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | /auth/register | Register new user | Public |
| POST | /auth/login | Login + get JWT | Public |

### Notification Service (port 8082)

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| GET | /notifications/{userId} | Get user notifications | Public |

---

## 🔄 Event Flow

### Register:
```
POST /auth/register
        ↓
user saved to MySQL (user_db)
        ↓
NotificationEvent published to Kafka
        ↓               ↓
notification saved    email sent
to MySQL              to Mailtrap ✅
```

### Kafka Event Structure:
```json
{
  "userId": "1",
  "email": "user@gmail.com",
  "type": "WELCOME",
  "message": "Welcome to our platform, John!"
}
```

---

## 🧪 Testing with Postman

**Register:**
```
POST http://localhost:8081/auth/register
Body: { "name": "John", "email": "john@gmail.com", "password": "password123" }
```

**Login:**
```
POST http://localhost:8081/auth/login
Body: { "email": "john@gmail.com", "password": "password123" }
```

**Get Notifications:**
```
GET http://localhost:8082/notifications/1
```

---

## 💾 Database Schema

### user_db.users
| Column | Type | Description |
|---|---|---|
| id | BIGINT | Primary key |
| name | VARCHAR | User's name |
| email | VARCHAR | Unique email |
| password | VARCHAR | BCrypt hashed |
| role | VARCHAR | ROLE_USER / ROLE_ADMIN |

### notification_db.notifications
| Column | Type | Description |
|---|---|---|
| id | BIGINT | Primary key |
| userId | VARCHAR | Reference to user |
| email | VARCHAR | User email |
| type | VARCHAR | WELCOME / LOGIN |
| message | VARCHAR | Notification text |
| status | VARCHAR | PENDING / SENT / FAILED |
| createdAt | DATETIME | Timestamp |

---

## 🐳 Docker

Infrastructure runs in Docker:

```bash
docker-compose up -d    # start Kafka + Zookeeper
docker-compose down     # stop containers
docker ps               # check running containers
docker logs kafka       # view Kafka logs
```

Dockerfiles are provided for all 3 services for full containerized deployment.

---

## 🔒 Security

- Passwords hashed with BCrypt
- JWT tokens for stateless authentication
- API keys stored as environment variables
- `application.properties` excluded from Git

---

## 🌱 Future Improvements

- [ ] Add API Gateway (Spring Cloud Gateway)
- [ ] Add Service Discovery (Eureka)
- [ ] Add SMS notifications (Twilio)
- [ ] Add rate limiting (Bucket4j)
- [ ] Deploy to AWS/Railway
- [ ] Add unit tests (JUnit + Mockito)
- [ ] Add Swagger API documentation

---

## 👨‍💻 Author

**Your Name**
- GitHub: [@JeevithaShreeT](https://github.com/JeevithaShreeT)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
