Reminder Notification Platform — API Documentation

Microservice-based Telecom Notification Backend
Built with Java Spring Boot, JWT Auth, MySQL, and Twilio Integration.

🔐 AUTH SERVICE — http://localhost:8081/api/auth
Method	Endpoint	Description	Auth
POST	/register	Register a new user	❌
POST	/login	Login and receive access & refresh tokens	❌
POST	/refresh	Refresh JWT access token	❌
POST	/logout	Logout & revoke refresh token	✅
GET	/validate	Validate current JWT token	✅
Example: Login

Request

{
  "email": "user@example.com",
  "password": "123456"
}


Response

{
  "userId": 1,
  "email": "user@example.com",
  "accessToken": "eyJhbGc...",
  "refreshToken": "uuid",
  "role": "USER",
  "accessTokenExpiresInMs": 900000
}

👤 USER SERVICE — http://localhost:8082/api/user
User Profile
Method	Endpoint	Description
POST	/profile	Create or get user profile
GET	/profile/{email}	Get user profile by email
Sender IDs
Method	Endpoint	Description
POST	/sender-ids	Create a new Sender ID
GET	/sender-ids	List sender IDs for logged user
GET	/sender-ids/{id}	Get a specific sender ID
PUT	/sender-ids/admin/{id}/{status}	Approve/Reject Sender ID (Admin)

Example: Create Sender ID

{
  "name": "CPAASAPP"
}


Response

{
  "id": 1,
  "name": "CPAASAPP",
  "status": "PENDING",
  "createdBy": "user@example.com"
}

Templates
Method	Endpoint	Description
POST	/templates	Create new template
PUT	/templates	Edit template
GET	/templates	List user templates
GET	/templates/{id}	Get single template
PUT	/templates/admin/{id}/{status}	Approve/Reject template (Admin)

Example: Create Template

{
  "title": "Payment Reminder",
  "content": "Dear {name}, your payment of ₹{amount} is due on {date}.",
  "senderId": 1
}


Response

{
  "id": 1,
  "title": "Payment Reminder",
  "status": "PENDING",
  "senderName": "CPAASAPP"
}

⏰ REMINDER SERVICE — http://localhost:8083/api/reminders
Method	Endpoint	Description
POST	/	Create single/bulk reminders
GET	/	List reminders of user
PUT	/cancel/{id}	Cancel a scheduled reminder
POST	/delivery-callback	Twilio delivery status callback

Example Request

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


Response

[
  {
    "id": 1,
    "recipientName": "Ravi",
    "recipientPhone": "+918570810853",
    "message": "Dear Ravi, your payment of ₹5000 is due on 2025-11-14.",
    "status": "SCHEDULED"
  }
]

🧾 REMINDER LIFECYCLE
Status	Meaning
SCHEDULED	Waiting for scheduled time
SENT	SMS successfully sent
FAILED	Delivery failed
CANCELLED	Cancelled manually
⚙️ HEADERS REQUIRED

For all secured endpoints (User, Reminder):

Authorization: Bearer <accessToken>
Content-Type: application/json

📦 ENVIRONMENT
Service	Port	Description
Auth	8081	JWT Auth Service
User	8082	User, Sender, Template
Reminder	8083	Scheduler & Twilio SMS
🧠 TESTING WORKFLOW (Postman Steps)

1️⃣ Login via POST /api/auth/login
→ Copy accessToken from response.

2️⃣ Set header in Postman:

Authorization: Bearer <accessToken>


3️⃣ Test endpoints:

Create Sender ID

Create Template

Approve (via admin)

Create Reminder

4️⃣ Observe SMS sent (simulated / Twilio logs).

✅ Example Base URLs (Production)
Service	URL
Auth	https://api.cpaasapp.in/auth

User	https://api.cpaasapp.in/user

Reminder	https://api.cpaasapp.in/reminder
© Pushpender Rajput — Software Engineer, Telecom CPAAS Project

Built using Spring Boot, MySQL, JWT Auth, and Twilio API