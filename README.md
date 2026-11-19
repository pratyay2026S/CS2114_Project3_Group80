# CS2114_Project3_Group80
# README – CS2114 Project 3 (Group 80)

## Overview

CareSync is a patient health-management app built by Group 80 for CS 2114 Project 3. It lets a patient track their medications and manage their doctor appointments, and it notifies listeners whenever either list changes.

The code is split into model, view and controller classes. The views are a Java Swing GUI: a login and registration window, a dashboard, a medications window and an appointments window. `Main` opens the login window. Each model class has a JUnit test class beside it.

## Features

**Patient accounts**

- Register a patient with an ID, username and password on the Create Account screen. Invalid input throws an `IllegalArgumentException` and the GUI shows an error dialog.
- Log in with the username and password. `checkLogin` checks them.
- Each patient owns one medication tracker and one appointment manager.

**Medication tracking**

- Add and remove medications (removal is by name, ignoring case).
- Each medication stores a name, dosage, frequency and quantity. A blank name, a dosage or frequency of 0 or less, or a negative quantity throws an `IllegalArgumentException`.
- Adding a medication queues one week of doses (frequency × 7) in a linked queue.
- `takeNextDose` removes the next dose and lowers that medication's quantity by one.
- `getLowSupply` lists medications with 5 or fewer doses left.

**Appointment management**

- Schedule, reschedule and cancel appointments with a doctor, date, time and location.
- Appointments in the past are rejected, and so is booking the same date and time twice.
- A blank doctor or location, or a null date or time, throws an `IllegalArgumentException`.

**Change notifications (Observer pattern)**

- Anything implementing `Observer` can register with the tracker or the manager.
- It receives a list of text summaries each time a medication or appointment is added, removed, changed or taken.

**GUI (views and controllers)**

- Four Swing windows:
  - `LoginView` has login and Create Account screens.
  - `DashboardView` has Manage Medications, Manage Appointments and Logout buttons.
  - `MedicationView` has Add Medication, Remove Medication, Take Next Dose and Back to Dashboard buttons.
  - `AppointmentView` has Schedule Appointment, Reschedule Appointment, Cancel Appointment and Back to Dashboard buttons.
- Forms open as dialogs. Errors and confirmations ("Remove …?", "Are you sure you want to cancel this appointment?") show as pop-ups.
- Three controllers: `PatientController`, `MedicationController` and `AppointmentController`. They pass user actions to the model and send errors to the views.
- `MedicationController` and `AppointmentController` register their view as an `Observer`, so the list on screen refreshes whenever the model changes.
- `Main` starts the GUI by opening the login window.

## Project structure

`Patient` is the model's entry point: it owns a `MedicationTracker` and an `AppointmentManager`. The tracker keeps its doses in a `LinkedQueue` built from `Node` objects. Controllers pass user actions to the model, and views show what the model sends them through `Observer`.

| File | Purpose |
| --- | --- |
| `Patient.java` | Account details, login check, and access to the tracker and manager |
| `Medication.java` | One medication: name, dosage, frequency, quantity, input checks, low-supply check |
| `MedicationTracker.java` | Medication list, weekly dose queue, low-supply list, observer updates |
| `Appointment.java` | One appointment: doctor, date, time, location, input checks |
| `AppointmentManager.java` | Schedule, reschedule, cancel, double-booking checks, observer updates |
| `LinkedQueue.java` | Generic linked-list queue (enqueue, dequeue, peek, size) |
| `Node.java` | Generic node used by the queue |
| `Observer.java` | Interface with `update(List<String>)` for change notifications |
| `LoginView.java` | Swing window with the login and Create Account screens |
| `DashboardView.java` | Swing dashboard that opens the medication and appointment windows and logs out |
| `MedicationView.java` | Swing window listing medications (an `Observer`), with add, remove and take-dose forms |
| `AppointmentView.java` | Swing window listing appointments (an `Observer`), with schedule, reschedule and cancel forms |
| `PatientController.java` | Registers a patient, checks login and opens the dashboard, sets up a first medication |
| `MedicationController.java` | Add, remove, update and take-dose actions; sends errors to the view |
| `AppointmentController.java` | Schedule, reschedule and cancel actions; sends errors to the view |
| `Main.java` | Starts the GUI by opening `LoginView` |
| `Referenced Library/student.jar` | CS 2114 student library, needed by the tests |

## How to run

**Model.** The model runs on its own, with no view. The six JUnit test classes are the driver. All 112 tests pass.

| Test class | Tests | Result | Covers |
| --- | --- | --- | --- |
| `LinkedQueueTest` | 16 | Pass | `enqueue`, `dequeue`, `peek`, `isEmpty`, `getSize`, first-in first-out order, null, empty-queue, refill and interleaved cases, an `Integer` queue |
| `MedicationTest` | 23 | Pass | Getters, setters, `isRunningLow` boundaries, bad input (blank or null name, zero or negative dosage and frequency, negative quantity) |
| `MedicationTrackerTest` | 22 | Pass | Add, remove (including ignoring case and removal from the queue), take dose, zero quantity, low supply, observers |
| `AppointmentTest` | 15 | Pass | Getters, setters, past-date check, bad input (blank or null doctor and location, null date or time) |
| `AppointmentManagerTest` | 15 | Pass | Schedule, reschedule, cancel, double-booking, bad index, observers |
| `PatientTest` | 21 | Pass | Constructor checks, login, getters, setters |

**GUI.** Run `Main.java` in Eclipse. The login window opens.

1. Click **Register**, enter a patient ID (a positive number), a username and a password, and click **Create Account**.
2. Log in with that username and password. The dashboard opens.
3. Click **Manage Medications** to add, remove or take doses, or **Manage Appointments** to schedule, reschedule or cancel. Dates use `YYYY-MM-DD` and times use `HH:MM`.

The model does not depend on any view.

## Reflection

The spec planned seven model classes, four views and three controllers. We built the model and its tests first, as this checkpoint asks. We then built all four views as a Swing GUI, wrote all three controllers, and added a `Main` class that starts the app. We also added the input validation and bad-input tests that our last checkpoint listed as next steps.

### What changed from the spec

| Area | Spec said | What we built | Why (what prompted it) |
| --- | --- | --- | --- |
| Views and controllers | `LoginView`, `DashboardView`, `MedicationView`, `AppointmentView`, plus three controllers | All four views as Swing windows, and all three controllers | Built after the model, as Deliverable 3 asks. The GUI was our biggest unknown, so we first used console views to test the wiring, then replaced them with a Swing GUI. |
| `LinkedQueue.enqueue` | Described as adding to the front | Adds to the back; `dequeue` and `peek` use the front | The scope picked first-in, first-out for doses, and that only works if entries join at the back. The spec wording was a slip. |
| Queue API | `enqueue`, `dequeue`, `peek`, `isEmpty` | The same four, plus `getSize()`. `enqueue(null)` throws `IllegalArgumentException`; `dequeue` and `peek` on an empty queue throw `IllegalStateException` | Writing the empty-queue and null tests showed the queue needed defined failures. |
| `Node` class | Not in the class design, which only says the queue uses linked nodes | `Node<T>` with `getData`, `getNext`, `setNext` | A linked queue needs a node type. |
| Weekly doses | `MedicationTracker` tracks daily and weekly medications | `addMedication` queues frequency × 7 doses | A queue of doses needs a fixed amount to hold, so we chose one week. |
| Bad-input handling | `scheduleAppointment` prompts for a valid date and suggests the next open slot; empty bottle prompts a refill | Model throws `IllegalArgumentException` or `IllegalStateException`; the GUI shows the error in a pop-up | Our MVC rule is that no class spans two roles, so the model reports the error and the view shows it. The next-slot suggestion and refill prompt are not built yet. |
| Validation location | Past-date check in `AppointmentManager` | Past-date check is in `AppointmentManager` (`scheduleAppointment`, `reschedule`), `Appointment.setDateTime`, and again in `AppointmentView` before it calls the controller. Blank-field and null checks are in `Medication` and `Appointment` | Added while building `reschedule` and then the GUI forms. The copies in `setDateTime` and `AppointmentView` are now redundant. |
| Login | Checks username and password | `Patient.checkLogin` compares the patient's name (used as the username) and password; `null` returns false | Login was added to the MVP in our revised scope for security. |
| Main class | Not in the spec | `Main` opens the login window | The app needs a starting point, so we added one. |

### Did the model stand alone?

Yes. No model class refers to a view or controller class, and all 112 tests run without one. The only connection back from the model is the `Observer` interface: the tracker and manager call `update(List<String>)` on whatever registered, so a view can implement it without the model knowing what it is.

The other direction is not as clean. The GUI views read model objects directly: `MedicationView` builds `Medication` objects and calls `tracker.getMedications()`, and `AppointmentView` builds `Appointment` objects and calls `getAppointments()`. That is allowed in MVC, but the views do more than just show what they are sent.

Two things in the model still lean toward the view. `notifyObservers` in both managers builds display text such as "Doctor: ..., Date: ..." itself, and exception messages are worded for the user. Both should move to the view or controller.

### What happened to our unknowns

Our scope listed three unknowns: connecting backend data to a GUI and sending notifications when due, building a GUI with a page for each MVP part, and making the GUI work on a phone.

| Unknown | Status | Detail |
| --- | --- | --- |
| Getting backend changes to the front end | Figured out | The Observer pattern. `MedicationTracker` and `AppointmentManager` keep observer lists and call `update` after every change. Both are tested, and the medication and appointment windows refresh their lists from it. |
| Notifications when something is due | Still open | `getLowSupply()` finds medications with 5 or fewer left, but nothing pushes it to the user, and nothing triggers a reminder when a dose is due. |
| Multi-page GUI | Figured out | A Swing GUI with a window for each MVP part: login and registration, dashboard, medications and appointments. The Back buttons return to the dashboard. |
| GUI on a phone | Still open | Swing runs on desktop only. |

### What doesn't work yet

This section checks every bad-input case from our scope against the code.

**Handled**

| Scope case | Result now | Where |
| --- | --- | --- |
| Wrong username or password | `checkLogin` returns false, null input returns false, and the GUI shows an error pop-up | `Patient.checkLogin`, `PatientController.login` |
| Appointment in the past | `IllegalArgumentException`, shown as a pop-up | `AppointmentManager.scheduleAppointment`, `reschedule`; `Appointment.setDateTime`; `AppointmentView` |
| Double-booking a slot | `IllegalArgumentException`, shown as a pop-up | `AppointmentManager.scheduleAppointment`, `reschedule` |
| Dose from an empty queue or empty bottle | `IllegalStateException`; the GUI shows an error pop-up | `MedicationTracker.takeNextDose`, `MedicationController.takeDose` |
| Cancel or reschedule an appointment that doesn't exist | `IllegalArgumentException`. In the GUI you can only pick an existing appointment from the list | `AppointmentManager.cancel`, `reschedule`; `AppointmentView` |
| Negative pill count or dosage | `IllegalArgumentException` for a dosage or frequency of 0 or less and for a negative quantity | `Medication` constructor and setters |
| Blank required fields | `Patient` rejects a blank name or password, `Medication` a blank name, and `Appointment` a blank doctor or location or a null date or time | `Patient`, `Medication`, `Appointment` |

## Next Steps

For Deliverable 4 we will finish the MVP first, then add a skip-and-requeue feature for doses.

### Finishing the MVP, in order

1. Fix login and logout so the account is kept and logging in before registering shows an error.
2. Make the presentation and the demo, and practice both before presenting.

Done since the last checkpoint: validation and bad-input tests for `Medication` and `Appointment`, and `Main` and the dashboard now use the patient's own `MedicationTracker` and `AppointmentManager`.

### Change feature: skip and requeue the next dose

A patient can skip the next dose. It moves to the back of the queue and the medication's quantity does not drop. This changes queue order, which is model state, and a patient reaches it through the controller and view, so it is more than a new button on existing logic.

| Class | Role | Prediction |
| --- | --- | --- |
| `MedicationTracker` | Model | Changes: new `skipNextDose()` that calls `peek`, `dequeue` and `enqueue` on the queue and then `notifyObservers()`. Throws `IllegalStateException` on an empty queue. |
| `MedicationController` | Controller | Changes: new `skipDose()` that calls `skipNextDose()`. |
| `MedicationView` | View | Changes: a Skip Next Dose button wired to `skipDose()`. Its `update(List<String>)` stays the same. |
| `LinkedQueue`, `Node` | Model | Should not change: `enqueue`, `dequeue` and `peek` already cover it. |
| `Medication`, `Patient`, `Observer` | Model | Should not change: `skipNextDose()` never calls `setQuantity`. |
| `Appointment`, `AppointmentManager`, `AppointmentView`, `AppointmentController` | Model, view, controller | Should not change: appointments are separate from doses. |
| `LoginView`, `DashboardView` | View | Should not change: neither shows doses. |

After Deliverable 4 we will compare this table with what we actually had to edit.

### Stretch goals

- **Committing to:** pushing the low-supply notification to the user. `getLowSupply()` already exists, so the work is sending it through `Observer` to the GUI.
- **Dropping:** the chatbot. None of us has built one, and our revised scope made it a last-resort goal.

### New tests needed

| Method | Normal case | Bad-input case |
| --- | --- | --- |
| `MedicationTracker.skipNextDose` | The skipped dose moves to the back and quantity is unchanged | Empty queue throws `IllegalStateException` |
| `MedicationTracker.takeNextDose` | A dose is taken when the first medication is out but others are not | All medications out throws `IllegalStateException` |
| `AppointmentManager.scheduleAppointment` | Next open slot is found after a double-booking | None new: null date or time is already rejected by `Appointment` |
| `AppointmentManager.reschedule` | Valid new date and time are stored | Null date or time throws `IllegalArgumentException`, not `NullPointerException` |
| `PatientController.login` | Correct login opens the dashboard | Login before registering shows an error instead of crashing |
| `MedicationController`, `AppointmentController` | Each call reaches the model and an observer is notified | A model error reaches the view without a crash |

### Who does what

Owners below are the same as what each of us did on the spec.

| Item | Owner | Status |
| --- | --- | --- |
| Validation and bad-input tests for `Medication` and `Appointment` | Abhinav | Done |
| Make `Main` and the dashboard use the patient's own `AppointmentManager` | Abhinav | Done |
| Fix login and logout | All | To do |
| Make and practice the presentation and the demo | Abigail, Pratyay and Abhinav | To do |

## Attribution

Team: Abigail, Pratyay and Abhinav.

| File | Who | How |
| --- | --- | --- |
| `Patient.java` | Pratyay, Abigail | Pratyay wrote; Abigail reviewed |
| `Medication.java` | Abhinav | Wrote |
| `MedicationTracker.java` | Pratyay, Abhinav | Pratyay wrote; Abhinav reviewed |
| `Appointment.java` | Abhinav | Wrote |
| `AppointmentManager.java` | Abhinav, Abigail | Abhinav wrote; Abigail revised |
| `LinkedQueue.java` | Pratyay | Wrote |
| `Node.java` | Pratyay | Wrote |
| `Observer.java` | Abigail | Wrote |
| `LoginView.java` | Abhinav | Wrote (GenAI-assisted) |
| `DashboardView.java` | Abigail | Wrote (GenAI-assisted) |
| `MedicationView.java` | Pratyay | Wrote (GenAI-assisted) |
| `AppointmentView.java` | Abhinav | Wrote (GenAI-assisted) |
| `PatientController.java` | Abigail | Wrote (GenAI-assisted) |
| `MedicationController.java` | Abhinav | Wrote (GenAI-assisted) |
| `AppointmentController.java` | Pratyay | Wrote (GenAI-assisted) |
| `Main.java` | Pratyay | Wrote (GenAI-assisted) |
| `PatientTest.java` | Abigail | Wrote |
| `MedicationTest.java` | Abhinav | Wrote |
| `MedicationTrackerTest.java` | Pratyay, Abigail | Pratyay wrote; Abigail tested |
| `AppointmentTest.java` | Abhinav | Wrote |
| `AppointmentManagerTest.java` | Abigail | Wrote |
| `LinkedQueueTest.java` | Pratyay, Abhinav | Pratyay wrote; Abhinav reviewed |

### GenAI note

We used AI to help draft this README and summarize our key points. We also used it to go over all six test classes (112 tests, all passing) and to compare the code with the spec and the scope's bad-input cases. We used it to create the view and controller classes, including the Swing GUI.
