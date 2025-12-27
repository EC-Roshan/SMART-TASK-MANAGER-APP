# Quick Test Guide - Smart Notifications 🚀

## ⚡ FASTEST Way to Test (1 Minute!)

### Test: 1-Minute Notification

1. **Open the app**
2. **Create a new task:**
    - Title: "Test Notification"
    - Due Date: **Today's date**
    - Due Time: **Current time + 1 minute**
        - Example: If now is 2:30:45, set 2:31:00
3. **Save the task**
4. **You'll see:** "Reminder set for 1 minutes from now"
5. **Wait 1 minute** ⏰
6. **🔔 NOTIFICATION APPEARS!** ✅

---

## 🧪 All Test Scenarios

### Scenario 1: 5-Minute Task (Quick)

```
Current Time: 2:00 PM
Set Due Time: 2:05 PM
Wait: 5 minutes
Result: Notification at 2:05 PM ✅
Message: "Task 'Your Task' is due NOW!"
```

### Scenario 2: 30-Minute Task

```
Current Time: 2:00 PM
Set Due Time: 2:30 PM
Wait: 30 minutes
Result: Notification at 2:30 PM ✅
```

### Scenario 3: 2-Hour Task

```
Current Time: 2:00 PM
Set Due Time: 4:00 PM
Wait: 2 hours
Result: Notification at 4:00 PM ✅
```

### Scenario 4: Tomorrow (Long-term)

```
Current Time: Dec 25, 2:00 PM
Set Due Time: Dec 26, 2:00 PM
Wait: Until Dec 25, 2:00 PM (24h before)
Result: Notification 24 hours before ✅
Message: "Task 'Your Task' is due tomorrow!"
```

---

## 📱 What to Look For

### When Creating Task:

**Short-term (<24h):**

```
Toast: "Reminder set for Xh Ym from now"
or
Toast: "Reminder set for X minutes from now"
```

**Long-term (>24h):**

```
Toast: "Reminder set for 24 hours before due time"
```

### When Notification Arrives:

**Short-term:**

```
🔔 Title: Task Name
📝 Message: "Task 'Task Name' is due NOW!"
```

**Long-term:**

```
🔔 Title: Task Name
📝 Message: "Task 'Task Name' is due tomorrow!"
```

---

## ✅ Success Checklist

After creating a task, you should see:

- ✅ Toast message with time remaining
- ✅ Task saved in list
- ✅ No error messages

When notification appears:

- ✅ Notification icon in status bar
- ✅ Notification shows task title
- ✅ Notification shows correct message
- ✅ Tapping notification opens app
- ✅ Notification auto-cancels after tap

---

## 🎯 Recommended Test Order

1. **Test 1-minute task** (fastest verification) ���
2. **Test 5-minute task** (quick verification)
3. **Test 30-minute task** (if you have time)
4. **Test tomorrow task** (long-term verification)

---

## 🔍 Troubleshooting

### Notification doesn't appear?

**Check 1:** Is notification permission granted?

- Settings → Allow notifications

**Check 2:** Is notification toggle ON in app settings?

- App → Settings → Notification switch = ON

**Check 3:** Is due time correct?

- Due time must be in the future
- Check 24-hour format (14:00 = 2 PM)

**Check 4:** Battery optimization?

- Settings → Apps → Smart Task Manager → Battery → Unrestricted

**Check 5:** Did you wait long enough?

- 1-minute task needs exactly 1 minute wait time

---

## 💡 Tips

1. **Use 1-minute tasks for quick testing**
2. **Make sure time format is correct** (HH:mm:ss)
3. **Keep app in background** (don't force close)
4. **Check notification permission** is granted
5. **Notification toggle in Settings** must be ON

---

## 🎉 Expected Results

### ✅ Working Correctly If:

1. Toast shows time remaining
2. Notification appears at correct time
3. Notification message is correct
4. Tapping notification opens app
5. No error messages

### ❌ Problem If:

1. No toast message appears
2. Toast says "Due time is in the past!"
3. Notification never appears
4. Error message appears

---

## 📞 Quick Debug

If notification doesn't work:

```
1. Check: Permission granted? YES/NO
2. Check: Toggle ON in settings? YES/NO
3. Check: Due time in future? YES/NO
4. Check: Waited long enough? YES/NO
5. Check: Battery unrestricted? YES/NO
```

If all YES → Should work! ✅
If any NO → Fix that first!

---

## 🚀 Start Testing!

**Easiest test: 1-minute notification**

Just set a task for 1 minute from now and wait! 🔔
