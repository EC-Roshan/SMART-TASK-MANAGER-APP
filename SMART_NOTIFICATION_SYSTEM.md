# Smart Notification System 🔔

## 🎯 How It Works Now

Your notification system is now **SMART** - it adapts based on how much time is left until the task
is due!

---

## 📋 Two Notification Modes

### Mode 1: Tasks Due in MORE than 24 Hours ⏰

**Notification sent: 24 hours BEFORE due time**

**Example:**

- Task due: Dec 30 at 2:00 PM
- Notification sent: Dec 29 at 2:00 PM
- Message: "Task 'Your Task' is due tomorrow!"

### Mode 2: Tasks Due in LESS than 24 Hours ⚡

**Notification sent: AT the due time**

**Examples:**

**5 minutes:**

- Task due: Today at 2:05 PM (current time: 2:00 PM)
- Notification sent: Today at 2:05 PM (in 5 minutes)
- Message: "Task 'Your Task' is due NOW!"

**2 hours:**

- Task due: Today at 4:00 PM (current time: 2:00 PM)
- Notification sent: Today at 4:00 PM (in 2 hours)
- Message: "Task 'Your Task' is due NOW!"

**30 minutes:**

- Task due: Today at 2:30 PM (current time: 2:00 PM)
- Notification sent: Today at 2:30 PM (in 30 minutes)
- Message: "Task 'Your Task' is due NOW!"

---

## 🔄 How the System Decides

```
Create Task
    ↓
Calculate: Due Time - Current Time = Time Difference
    ↓
Is Time Difference > 24 hours?
    ↓
YES → Schedule notification 24 hours before due time
NO → Schedule notification AT due time
```

---

## 📱 What You'll See (Toast Messages)

### For Tasks Due in MORE than 24 Hours:

```
✅ "Reminder set for 24 hours before due time"
```

### For Tasks Due in LESS than 24 Hours:

```
✅ "Reminder set for 5h 30m from now"
✅ "Reminder set for 2h from now"
✅ "Reminder set for 45 minutes from now"
✅ "Reminder set for less than 1 minute"
```

---

## 🎬 Real-World Examples

### Example 1: Homework Due Next Week

**Scenario:**

- Current time: Dec 25 at 10:00 AM
- Task: "Complete homework"
- Due date: Dec 30 at 3:00 PM

**Result:**

- ✅ Notification scheduled for: Dec 29 at 3:00 PM
- 📱 Toast: "Reminder set for 24 hours before due time"
- 🔔 Notification message: "Task 'Complete homework' is due tomorrow!"

---

### Example 2: Meeting in 30 Minutes

**Scenario:**

- Current time: Today at 2:00 PM
- Task: "Team meeting"
- Due date: Today at 2:30 PM

**Result:**

- ✅ Notification scheduled for: Today at 2:30 PM
- 📱 Toast: "Reminder set for 30 minutes from now"
- 🔔 Notification message: "Task 'Team meeting' is due NOW!"

---

### Example 3: Call Someone in 5 Minutes

**Scenario:**

- Current time: Today at 3:00 PM
- Task: "Call mom"
- Due date: Today at 3:05 PM

**Result:**

- ✅ Notification scheduled for: Today at 3:05 PM
- 📱 Toast: "Reminder set for 5 minutes from now"
- 🔔 Notification message: "Task 'Call mom' is due NOW!"

---

### Example 4: Task in 10 Hours

**Scenario:**

- Current time: Today at 8:00 AM
- Task: "Doctor appointment"
- Due date: Today at 6:00 PM (in 10 hours)

**Result:**

- ✅ Notification scheduled for: Today at 6:00 PM
- 📱 Toast: "Reminder set for 10h from now"
- 🔔 Notification message: "Task 'Doctor appointment' is due NOW!"

---

## 📊 Time Calculation Logic

### Formula:

```kotlin
timeDifference = dueTime - currentTime

if (timeDifference > 24 hours) {
    notificationTime = dueTime - 24 hours
    message = "due tomorrow!"
} else {
    notificationTime = dueTime
    message = "due NOW!"
}
```

### Time Display Logic:

```kotlin
hours = timeDifference / (60 * 60 * 1000)
minutes = (timeDifference % (60 * 60 * 1000)) / (60 * 1000)

// Examples:
// 2h 30m → "2h 30m from now"
// 3h 0m → "3h from now"
// 0h 45m → "45 minutes from now"
// 0h 0m → "less than 1 minute"
```

---

## 🛡️ Safety Features

### 1. Past Time Detection ⚠️

If you try to set a task with due time in the past:

```
❌ "Due time is in the past!"
```

No notification scheduled.

### 2. Notification Settings Respect ✅

If notifications are disabled in settings:

- No notification scheduled (silent)
- Task still saved

### 3. Task Cancellation 🔄

When you update or delete a task:

- Old notification is cancelled
- New notification is scheduled (if updated)

---

## 🧪 How to Test

### Test 1: Long-term Task (>24 hours)

1. Create a task
2. Set due date: Tomorrow (any time)
3. Save task
4. ✅ See: "Reminder set for 24 hours before due time"

### Test 2: Short-term Task (5 minutes)

1. Create a task
2. Set due date: Today
3. Set due time: Current time + 5 minutes
    - Example: If now is 2:00 PM, set 2:05 PM
4. Save task
5. ✅ See: "Reminder set for 5 minutes from now"
6. ⏰ Wait 5 minutes
7. 🔔 Notification appears!

### Test 3: Short-term Task (2 hours)

1. Create a task
2. Set due date: Today
3. Set due time: Current time + 2 hours
    - Example: If now is 2:00 PM, set 4:00 PM
4. Save task
5. ✅ See: "Reminder set for 2h from now"

### Test 4: Very Short Task (1 minute) - QUICK TEST!

1. Create a task
2. Set due date: Today
3. Set due time: Current time + 1 minute
4. Save task
5. ✅ See: "Reminder set for 1 minutes from now"
6. ⏰ Wait 1 minute
7. 🔔 Notification appears! (Quick test!)

---

## 📍 Modified Files

### 1. AddTaskFragment.kt

**Changes:**

- ✅ Smart time detection logic
- ✅ Calculates time difference
- ✅ Adapts notification scheduling
- ✅ Human-readable time display

### 2. HomeFragment.kt

**Changes:**

- ✅ Same smart logic for task updates
- ✅ Cancels old notifications
- ✅ Schedules new smart notifications

---

## 🎯 Notification Messages

### Long-term (>24 hours):

```
🔔 "Task 'Your Task Name' is due tomorrow!"
```

### Short-term (<24 hours):

```
🔔 "Task 'Your Task Name' is due NOW!"
```

---

## 💡 Benefits of This System

1. ✅ **Smart** - Adapts to task urgency
2. ✅ **Flexible** - Works for any time range
3. ✅ **User-friendly** - Clear feedback messages
4. ✅ **Practical** - Perfect for quick reminders (5 min tasks)
5. ✅ **Consistent** - Works for both short and long tasks

---

## 📊 Summary Table

| Task Due In | Notification Time | Message | Example |
|-------------|-------------------|---------|---------|
| 5 days | 4 days before | "due tomorrow!" | Project deadline |
| 2 days | 1 day before | "due tomorrow!" | Assignment |
| 10 hours | At due time | "due NOW!" | Meeting |
| 2 hours | At due time | "due NOW!" | Appointment |
| 30 min | At due time | "due NOW!" | Quick call |
| 5 min | At due time | "due NOW!" | Urgent task |

---

## ✅ What Changed

### Before:

- ❌ Only 24-hour advance notification
- ❌ Couldn't schedule tasks < 24 hours away
- ❌ Message: "Due date is too soon to set a reminder"

### After:

- ✅ Smart detection (>24h vs <24h)
- ✅ Can schedule any task (even 5 minutes away!)
- ✅ Helpful time remaining messages
- ✅ Works for urgent quick tasks

---

## 🎉 Now You Can:

1. ✅ Set a task for 5 minutes from now → Get notified in 5 minutes!
2. ✅ Set a task for 2 hours from now → Get notified in 2 hours!
3. ✅ Set a task for tomorrow → Get notified 24 hours before!
4. ✅ Set a task for next week → Get notified 24 hours before!

**Your notification system is now SMART and FLEXIBLE!** 🚀
