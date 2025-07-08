# 📦 Thought Capsule

**Thought Capsule** is a time-locked message delivery service that lets users create and send personal messages to themselves, family, or friends — to be revealed in the future. Think of it like an emotional time machine for your words. 🔐

---

## 🚀 Features

- ✍️ Create a capsule with message, media URL, and unlock date
- 🔑 Protect messages using a 4-digit PIN
- ⏰ Automatically sends email to the recipient when the message unlocks
- 💌 Unlock capsules manually using Message ID + PIN
- 🧊 Read-once mode (message disappears after viewing)
- 📧 Email reminders with sender info, unlock time, and message content

---

## 🛠️ Tech Stack

| Layer     | Tech                               |
|-----------|------------------------------------|
| Backend   | Java, Spring Boot, Spring Data JPA |
| Database  | Oracle Database                    |
| Frontend  | HTML, CSS, JavaScript              |
| Mail      | JavaMailSender (Gmail SMTP)        |

---

## 📁 Folder Structure

📁 ThoughtCapsule/
├── 📂 src/
│   └── 📂 main/
│       ├── 📂 java/com.example.Thought_Capsule/
│       │   ├── 📂 controller/       → 📄 MessageController
│       │   ├── 📂 dto/             → 📄 MessageRequestDto, MessageResponseDto
│       │   ├── 📂 entity/          → 📄 Message
│       │   ├── 📂 repository/      → 📄 MessageRepository
│       │   ├── 📂 service/         → 📄 MessageService, EmailScheduler
│       │   └── 📄 ThoughtCapsuleApplication.java
│       └── 📂 resources/
│           └── 📄 application.properties
│
├── 📂 frontend/
│   ├── 📄 index.html      📄 create.html      📄 unlock.html
│   ├── 🎨 style.css       🎨 create.css       🎨 unlock.css
│   └── 📜 create.js       📜 unlock.js
│
├── 📄 pom.xml
└── 📄 README.md



---

## ⚙️ How It Works

1. **Create Capsule**: Enter message, unlock time, PIN, and recipient email.
2. **Store Securely**: Message is saved in the database.
3. **Unlock Time Reached**:
    - 🔁 Automatically sends message to recipient's email.
4. **Manual Unlock**:
    - Users can unlock using Message ID and PIN combo.

---

## 📬 Sample Email Preview

Hello,

You received a Thought Capsule from SIVANARAYANAN M 🎁
Created on: 8 July 2025

💌 Message ID: 44
🔐 PIN Code: 1234

📝 Message:
"Hey Future Me, remember to always stay curious, keep learning, and never give up. Whatever you're facing now — it will pass. You've come so far, and there's still so much more waiting for you. Keep pushing forward!"

— With love, your past self 💙

📎 Media Link: http://newMessage.com

――――――――――――――――――――――――――――――
📦 This message was sent via Thought Capsule
A time-locked message delivery service that lets people send memories into the future.
🔐 Secure your thoughts today — unlock them when the time is right!


---

## 🧪 Local Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/sivanarayananM/ThoughtCapsule.git
   cd ThoughtCapsule
Add your Oracle DB credentials in application.properties

Run the backend:

mvn spring-boot:run
Open index.html from the frontend/ folder in your browser

🔐 Environment Notes
SMTP (Gmail) setup required for sending emails.

Make sure Less secure apps access is enabled for the sender email (or use App Password).

🙌 Credits
Created with ❤️ by SIVANARAYANAN M

“Memories fade, but words can wait.”