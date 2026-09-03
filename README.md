<div align="center">

# 📋 Smart Task Manager App

### *A smart way to organize tasks, priorities, and productivity*

<br/>

![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Language](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Database](https://img.shields.io/badge/Database-Room-FF6F00?style=for-the-badge&logo=sqlite&logoColor=white)
![UI](https://img.shields.io/badge/UI-Material%20Design-757575?style=for-the-badge&logo=materialdesign&logoColor=white)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-4A90E2?style=for-the-badge)

<br/>

> **Smart Task Manager** — not just a to-do list, but a productivity companion.

</div>

---

## 🚀 Overview

**Smart Task Manager** is a modern Android productivity application designed to help users **plan, prioritize, and track tasks efficiently.**

Built using **Kotlin**, **XML**, and **Material Design**, the app focuses on **clean UI, offline reliability, and smart task insights**.

Unlike traditional to-do apps, Smart Task Manager combines **priority-based task management**, **visual statistics**, and **timely notifications** to keep users focused and organized.

---

## ✨ Key Features

<table>
<tr>
<td width="50%">

### ✅ Task Management
- Add, update, delete, and complete tasks
- Task attributes:
  - 📝 Title & Description
  - 📅 Due Date & Time
  - 🎯 Priority (Essential / Important / Flexible)
  - 🗂️ Category
  - ✔️ Completion Status

</td>
<td width="50%">

### 🎯 Priority-Based Workflow
- Chip-based priority selection
- Color-coded task importance
- Smart organization for better decision-making

### 💾 Offline First
- Uses **Room Database**
- No internet required
- Fast and reliable local storage

</td>
</tr>
<tr>
<td width="50%">

### 📊 Visual Statistics & Reports
- Pie charts for:
  - Completed vs Pending tasks
  - Priority distribution
- Real-time updates using **LiveData**
- Interactive charts powered by **MPAndroidChart**

</td>
<td width="50%">

### 🔔 Smart Notifications
- Automatic reminders using **WorkManager**
- Notifications scheduled before task deadlines
- Android 13+ notification permission handling

### 🎨 Clean & Modern UI
- Material Components
- ViewBinding for smooth UI handling
- Navigation Drawer & Fragment-based architecture
- Custom icons, gradients, and animations

</td>
</tr>
</table>

---

## 🛠️ Tech Stack

| Layer | Technology |
|:------|:-----------|
| 💻 Language | Kotlin |
| 🎨 UI | XML, Material Design |
| 🏗️ Architecture | MVVM |
| 🗄️ Database | Room |
| ⚡ Async Tasks | Coroutines |
| 📊 Charts | MPAndroidChart |
| 🔔 Notifications | WorkManager |
| 🧭 Navigation | Navigation Component |
| 🔗 Binding | ViewBinding |

---

## 🧩 App Architecture (MVVM)

```
┌─────────────────────────────────────────┐
│              UI Layer                   │
│     (Fragments + Activities)            │
└────────────────┬────────────────────────┘
                 │ observes
┌────────────────▼────────────────────────┐
│            ViewModel Layer              │
│     (LiveData + Business Logic)         │
└────────────────┬────────────────────────┘
                 │ calls
┌────────────────▼────────────────────────┐
│           Repository Layer              │
│       (Single Source of Truth)          │
└────────────────┬────────────────────────┘
                 │ queries
┌────────────────▼────────────────────────┐
│           Room Database                 │
│        (Local SQLite Storage)           │
└─────────────────────────────────────────┘
```

> This architecture ensures clean separation of concerns, better testability, and lifecycle-aware UI updates.

---

## 📱 Screens Included

| # | Screen |
|:--|:-------|
| 1 | 🌟 Splash Screen |
| 2 | 📖 Intro / Onboarding Screen |
| 3 | 🏠 Home (Task List + Status Chart) |
| 4 | ➕ Add Task Screen |
| 5 | ✏️ Edit Task Screen |
| 6 | 📊 Statistics Report Screen |
| 7 | ⚙️ Settings |
| 8 | ℹ️ About Us |

---

## ⚙️ Requirements

### 📱 Hardware
- Minimum **2GB RAM**
- Android device with **720p+** display

### 💻 Software
- **Android 8.0 (Oreo)** or above
- Android Studio
- Kotlin support enabled

---

## 🧪 Testing

- ✅ Unit Testing for ViewModels
- ✅ Manual UI Testing
- ✅ Navigation flow testing
- ✅ Database CRUD operation validation
- ✅ Notification scheduling verification

---

## 🚧 Challenges Faced

- ⚡ Managing lifecycle-aware notifications
- 📊 Synchronizing charts with LiveData
- 🎨 Designing a clean yet functional UI
- 📅 Handling date & time validations

---

## 🔮 Future Enhancements

- ☁️ Cloud sync (Firebase)
- 🔐 User authentication
- 🤝 Task sharing & collaboration
- 🌙 Dark mode support
- 🤖 AI-based task suggestions
- 📅 Calendar sync

---

## 👨‍💻 Developer

<div align="center">

**Roshanlal Maddheshiya**
🎓 BCA (5th Semester) — University of Allahabad
📍 Prayagraj, Uttar Pradesh

</div>

---

## 📜 License

This project is developed for **academic and learning purposes.**
You are free to use, modify, and enhance it with proper credit.

---

<div align="center">

### ⭐ If you found this project helpful, please give it a star!

*Smart Task Manager — not just a to-do list, but a productivity companion.*

</div>
