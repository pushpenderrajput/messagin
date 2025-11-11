# Reminder Notification Platform – API Documentation

**Notification Service Backend** built with **Spring Boot Microservices**, **JWT Auth**, **MySQL**, and **Twilio**.

This system allows users to:
- Manage profiles and sender IDs  
- Create message templates  
- Schedule SMS reminders (single or bulk)  
- Send notifications automatically via Twilio  

---

## 🏗 Architecture Overview

| Service | Port | Description |
|----------|------|-------------|
| **Auth Service** | `8081` | Handles user authentication & JWT management |
| **User Service** | `8082` | Manages user profiles, sender IDs & templates |
| **Reminder Service** | `8083` | Schedules & sends reminders (via Twilio) |
| **Database** | MySQL | Independent DB per service |

---

## 🔐 AUTH SERVICE (`http://localhost:8081/api/auth`)

### **Endpoints**

| Method | Endpoint | Description | Auth |
|--------|-----------|-------------|------|
| `POST` | `/register` | Register new user | ❌ |
| `POST` | `/login` | Login user & issue tokens | ❌ |
| `POST` | `/refresh` | Refresh access token | ❌ |
| `POST` | `/logout` | Logout & revoke token | ✅ |
| `GET` | `/validate` | Validate current JWT | ✅ |

### **Example: Register**

**Request Payload**
```json
{
    "email":"pushpenderrajputsp@gmail.com",
    "password":"Pushpender@21",
    "role": "ADMIN"
}
```
### **Example: Login**
**Request Payload**
```json
{
  "email": "user@example.com",
  "password": "123456"
}

```
**Response**
```json
{
  "userId": 1,
  "email": "user@example.com",
  "accessToken": "eyJhbGc...",
  "refreshToken": "uuid",
  "role": "USER",
  "accessTokenExpiresInMs": 900000
}
```

---

## 👤 USER SERVICE (`http://localhost:8082/api/user`)
### **User Profile:**
| Method | Endpoint           | Description                    |
| ------ | ------------------ | ------------------------------ |
| `POST` | `/profile`         | Create or get user profile     |
| `GET`  | `/profile/{email}` | Retrieve user profile by email |


### **Save User Profile**
**Request Payload**
```json
{
    "fullName":"Pushpender Rajput",
    "email":"pushpender.rajput@gmail.com",
    "isActive":true,
    "countryCode":"91",
    "isAdmin":false
}
```

---
### **Sender IDs**
### **Endpoints**
| Method | Endpoint                          | Description                              |
| ------ | --------------------------------- | ---------------------------------------- |
| `POST` | `/sender-ids`                     | Create new sender ID (default `PENDING`) |
| `GET`  | `/sender-ids`                     | List sender IDs created by current user  |
| `GET`  | `/sender-ids/{id}`                | Get sender ID by ID                      |
| `GET`  | `/sender-ids/admin`               | List all sender IDs (Admin)              |
| `PUT`  | `/sender-ids/admin/{id}/{status}` | Approve/Reject sender ID (Admin only)    |

### **Create Sender ID**
**Request Payload**
```json
{
    "name": "CPAAS"
}
```

**Response**
```json
{
  "id": 1,
  "name": "CPAASAPP",
  "status": "PENDING",
  "createdBy": "user@example.com"
}
```
---

### **Templates**
**Endpoints**
| Method | Endpoint                         | Description                     |
| ------ | -------------------------------- | ------------------------------- |
| `POST` | `/templates`                     | Create a new template           |
| `PUT`  | `/templates`                     | Edit template content           |
| `GET`  | `/templates`                     | List user templates             |
| `GET`  | `/templates/{id}`                | Get template by ID              |
| `GET`  | `/templates/admin`               | List all templates (Admin)      |
| `PUT`  | `/templates/admin/{id}/{status}` | Approve/Reject template (Admin) |

### **Create Template**
**Request Payload**
```json
{
  "title": "Payment Reminder",
  "content": "Dear {name}, your payment of ₹{amount} is due on {date}.",
  "senderId": 1
}
```

**Reponse**

```json
{
  "id": 1,
  "title": "Payment Reminder",
  "content": "Dear {name}, your payment of ₹{amount} is due on {date}.",
  "status": "PENDING",
  "senderName": "CPAASAPP",
  "createdBy": "user@example.com"
}
```

---

## ⏰ REMINDER SERVICE (`http://localhost:8083/api/reminders`)
### **Endpoints**
| Method | Endpoint             | Description                      |
| ------ | -------------------- | -------------------------------- |
| `POST` | `/`                  | Create one or multiple reminders |
| `GET`  | `/`                  | List reminders for current user  |
| `PUT`  | `/cancel/{id}`       | Cancel a scheduled reminder      |
| `GET`  | `/admin`             | List all reminders (Admin)       |
| `POST` | `/delivery-callback` | Twilio delivery webhook          |

### **Schedule Single Reminder**
**Request Payload**
```json
{
  "title": "Credit Card Bill",
  "senderId": 1,
  "templateId": 1,
  "scheduledAt": "2025-11-14T10:00:00",
  "contacts": [
    {
      "recipientName": "Ravi",
      "recipientPhone": "+918570810853",
      "variables": {
        "name": "Ravi",
        "amount": "5000",
        "date": "2025-11-14"
      }
    }
  ]
}
```

**Response**
```json
[
  {
    "id": 1,
    "title": "Credit Card Bill",
    "recipientName": "Ravi",
    "recipientPhone": "+918570810853",
    "message": "Dear Ravi, your payment of ₹5000 is due on 2025-11-14.",
    "status": "SCHEDULED",
    "scheduledAt": "2025-11-14T10:00:00",
    "createdBy": "user@example.com"
  }
]
```

### **Bulk Reminder Payload**
```json
{
  "title": "Loan Payment Reminder",
  "senderId": 1,
  "templateId": 2,
  "scheduledAt": "2025-11-20T09:00:00",
  "contacts": [
    {
      "recipientName": "Amit",
      "recipientPhone": "+919876543210",
      "variables": {
        "name": "Amit",
        "amount": "3000",
        "date": "2025-11-20"
      }
    },
    {
      "recipientName": "Ravi",
      "recipientPhone": "+918570810853",
      "variables": {
        "name": "Ravi",
        "amount": "5000",
        "date": "2025-11-20"
      }
    }
  ]
}
```

**Response**
```json
[
  {
    "id": 1,
    "recipientName": "Amit",
    "message": "Dear Amit, your payment of ₹3000 is due on 2025-11-20.",
    "status": "SCHEDULED"
  },
  {
    "id": 2,
    "recipientName": "Ravi",
    "message": "Dear Ravi, your payment of ₹5000 is due on 2025-11-20.",
    "status": "SCHEDULED"
  }
]
```

## 📅 Reminder Lifecycle
| Status      | Description                |
| ----------- | -------------------------- |
| `SCHEDULED` | Waiting for scheduled time |
| `SENT`      | Message successfully sent  |
| `FAILED`    | Message sending failed     |
| `CANCELLED` | Cancelled manually         |


## ⚙️ Headers for Authorized Requests
```
Authorization: Bearer <accessToken>
Content-Type: application/json
```
 ---

## ⚙️ Environment Configuration
| Property            | Description                 | Example                 |
| ------------------- | --------------------------- | ----------------------- |
| `auth.service.url`  | Auth service validation URL | `http://localhost:8081` |
| `twilio.enabled`    | Enable Twilio integration   | `true`                  |
| `twilio.accountSid` | Twilio Account SID          | `ACxxxx`                |
| `twilio.authToken`  | Twilio Auth Token           | `xxxx`                  |
| `twilio.fromNumber` | Sender number               | `+1415XXXXXXX`          |


---
## 🧑‍💻 Author

**Pushpender Rajput**  
*pushpenderrajputsp@gmail.com*


🌐 [GitHub](https://github.com/pushpenderrajput)  
🔗 [LinkedIn](https://www.linkedin.com/in/pushpender-rajput/)






