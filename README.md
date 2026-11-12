# CS2114_Project3_Group80
# README – CS2114 Project 3 (Group 80)

## Overview

This project is the Java back end for CareSync, a patient health-management app built by Group 80 for CS 2114 Project 3. It lets a patient track their medications and manage their doctor appointments, and it notifies listeners whenever either list changes.

The code has no user interface yet. It holds the data model and logic, and each class has a JUnit test class beside it.

## Features

**Patient accounts**

- Create a patient with an ID, name and password. Invalid input throws an `IllegalArgumentException`.
- Check login credentials with `checkLogin`.
- Each patient owns one medication tracker and one appointment manager.

**Medication tracking**

- Add and remove medications (removal is by name, ignoring case).
- Each medication stores a name, dosage, frequency and quantity.
- Adding a medication queues one week of doses (frequency × 7) in a linked queue.
- `takeNextDose` removes the next dose and lowers that medication's quantity by one.
- `getLowSupply` lists medications with 5 or fewer doses left.

**Appointment management**

- Schedule, reschedule and cancel appointments with a doctor, date, time and location.
- Appointments in the past are rejected, and so is booking the same date and time twice.

**Change notifications (Observer pattern)**

- Anything implementing `Observer` can register with the tracker or the manager.
- It receives a list of text summaries each time a medication or appointment is added, removed, changed or taken.

## Project structure

`Patient` is the entry point: it owns a `MedicationTracker` and an `AppointmentManager`. The tracker keeps its doses in a `LinkedQueue` built from `Node` objects.

| File | Purpose |
| --- | --- |
| `Patient.java` | Account details, login check, and access to the tracker and manager |
| `Medication.java` | One medication: name, dosage, frequency, quantity, low-supply check |
| `MedicationTracker.java` | Medication list, weekly dose queue, low-supply list, observer updates |
| `Appointment.java` | One appointment: doctor, date, time, location |
| `AppointmentManager.java` | Schedule, reschedule, cancel, double-booking checks, observer updates |
| `LinkedQueue.java` | Generic linked-list queue (enqueue, dequeue, peek, size) |
| `Node.java` | Generic node used by the queue |
| `Observer.java` | Interface with `update(List<String>)` for change notifications |
| `Referenced Library/student.jar` | CS 2114 student library, needed by the tests |

## How to run

**Model.** The model runs on its own, with no view. There is no `main` method, so the six JUnit test classes are the driver.

| Test class | Tests | Result | Covers |
| --- | --- | --- | --- |
| `LinkedQueueTest` | 10 | Pass | Core queue methods: `enqueue`, `dequeue`, `peek`, `isEmpty`, plus null, empty-queue and refill cases |
| `MedicationTest` | 9 | Pass | Getters, setters, `isRunningLow` |
| `MedicationTrackerTest` | 14 | Pass | Add, remove, take dose, low supply, observers |
| `AppointmentTest` | 9 | Pass | Getters, setters, past-date check |
| `AppointmentManagerTest` | 15 | Pass | Schedule, reschedule, cancel, double-booking, observers |
| `PatientTest` | 17 | Pass | Constructor checks, login, getters, setters |

**Thin view.** Not built yet. This repo has no view or controller class, so there is nothing to launch. See Reflection and Next Steps.

## Reflection

The spec planned seven model classes, four views and three controllers. We built the model and its tests first, as this checkpoint asks, and the views and controllers are still to do.

### What changed from the spec

| Area | Spec said | What we built | Why (what prompted it) |
| --- | --- | --- | --- |
| Views and controllers | `LoginView`, `DashboardView`, `MedicationView`, `AppointmentView`, plus three controllers | None yet | Model first, as Deliverable 3 asks. The GUI was our biggest unknown in the scope. |
| `LinkedQueue.enqueue` | Described as adding to the front | Adds to the back; `dequeue` and `peek` use the front | The scope picked first-in, first-out for doses, and that only works if entries join at the back. The spec wording was a slip. |
| Queue API | `enqueue`, `dequeue`, `peek`, `isEmpty` | The same four, plus `getSize()`. `enqueue(null)` throws `IllegalArgumentException`; `dequeue` and `peek` on an empty queue throw `IllegalStateException` | Writing the empty-queue and null tests showed the queue needed defined failures. |
| `Node` class | Not in the class design, which only says the queue uses linked nodes | `Node<T>` with `getData`, `getNext`, `setNext` | A linked queue needs a node type. |
| Weekly doses | `MedicationTracker` tracks daily and weekly medications | `addMedication` queues frequency × 7 doses | A queue of doses needs a fixed amount to hold, so we chose one week. |
| Bad-input handling | `scheduleAppointment` prompts for a valid date and suggests the next open slot; empty bottle prompts a refill | Model throws `IllegalArgumentException` or `IllegalStateException` and nothing more | Our MVC rule is that no class spans two roles, so the model reports the error and the view will do the prompting. |
| Validation location | Past-date check in `AppointmentManager` | Past-date check is in both `AppointmentManager` (`scheduleAppointment`, `reschedule`) and `Appointment.setDateTime` | Added while building `reschedule`. The copy in `setDateTime` is now redundant. |
| Login | Checks username and password | `Patient.checkLogin` compares the patient's name and password; `null` returns false | Login was added to the MVP in our revised scope for security. |

### Did the model stand alone?

Yes. No model class refers to a view or controller class, and all 74 tests run without one. The only connection is the `Observer` interface: the tracker and manager call `update(List<String>)` on whatever registered, so a view can implement it without the model knowing what it is.

Two things in the model still lean toward the view. `notifyObservers` in both managers builds display text such as "Doctor: ..., Date: ..." itself, and exception messages are worded for the user. Both should move to the view or controller when we build them.

### What happened to our unknowns

Our scope listed three unknowns: connecting backend data to a GUI and sending notifications when due, building a GUI with a page for each MVP part, and making the GUI work on a phone.

| Unknown | Status | Detail |
| --- | --- | --- |
| Getting backend changes to the front end | Figured out | The Observer pattern. `MedicationTracker` and `AppointmentManager` keep observer lists and call `update` after every change. Both are tested. |
| Notifications when something is due | Still open | `getLowSupply()` finds medications with 5 or fewer left, but nothing pushes it to the user, and nothing triggers a reminder when a dose is due. |
| Multi-page GUI | Still open | No view exists yet. |

### What doesn't work yet

This table checks every bad-input case from our scope against the code.

| Scope case | Result now | Where |
| --- | --- | --- |
| Wrong username or password | Handled: `checkLogin` returns false, and null input returns false | `Patient.checkLogin` |
| Appointment in the past | Handled: `IllegalArgumentException` | `AppointmentManager.scheduleAppointment`, `reschedule`; `Appointment.setDateTime` |
| Double-booking a slot | Handled: `IllegalArgumentException`. The next-open-slot suggestion is not built | `AppointmentManager.scheduleAppointment`, `reschedule` |
| Dose from an empty queue or empty bottle | Handled: `IllegalStateException`. The refill prompt is view work | `MedicationTracker.takeNextDose` |
| Cancel or reschedule an appointment that doesn't exist | Handled: `IllegalArgumentException` | `AppointmentManager.cancel`, `reschedule` |
| Dose marked as taken twice | Not handled: there is no record of taken doses, so a second tap takes the next queued dose | `MedicationTracker.takeNextDose` |
| Negative pill count or dosage | Not handled: `Medication` accepts any number | `Medication` constructor and setters |
| Blank required fields | Partly handled: `Patient` rejects a blank name or password. Medication name, doctor and location are not checked, and a null date or time passed to `scheduleAppointment` throws `NullPointerException` | `Patient` constructor; `Medication`, `Appointment` |
| Chatbot out-of-scope question | Not built: the chatbot is a stretch goal | None |

Other gaps:

- No view, controllers or driver, so the MVP cannot be run end to end yet.
- `takeNextDose` only looks at the front dose. If that medication is out, every dose behind it is blocked.
- The queue holds one week of doses. After that `takeNextDose` throws "No doses remaining" even when quantity is left, and nothing re-queues them.
- `Medication.isRunningLow(int)` takes the quantity as a parameter instead of using the medication's own quantity.
- `Medication`, `Appointment` and `Node` have no bad-input tests because they have no validation yet.
- Passwords are stored as plain text.

## Next Steps

For Deliverable 4 we will finish the MVP first, then add a skip-and-requeue feature for doses.

### Finishing the MVP, in order

1. Add validation to `Medication` (negative dosage, frequency or quantity; blank name) and `Appointment` (blank doctor or location; null date or time), with bad-input tests.
2. Fix the dose queue: `takeNextDose` should not let one empty medication block the rest, and the queue should refill after its first week.
3. Build `MedicationController` and `AppointmentController`, then `MedicationView` and `AppointmentView` implementing `Observer`, so the thin view runs the MVP path end to end.
4. Build `PatientController`, `LoginView` and `DashboardView`.
5. Move display text out of the model: the strings built in `notifyObservers` and the user-facing exception wording.
6. Suggest the next open slot when an appointment is double-booked.

### Change feature: skip and requeue the next dose

A patient can skip the next dose. It moves to the back of the queue and the medication's quantity does not drop. This changes queue order, which is model state, and a patient reaches it through the controller and view, so it is more than a new button on existing logic.

| Class | Role | Prediction |
| --- | --- | --- |
| `MedicationTracker` | Model | Changes: new `skipNextDose()` that calls `peek`, `dequeue` and `enqueue` on the queue and then `notifyObservers()`. Throws `IllegalStateException` on an empty queue. |
| `MedicationController` | Controller | Changes: new `skipDose()` that calls `skipNextDose()`. |
| `MedicationView` | View | Changes: a skip button wired to `skipDose()`. Its `update(List<String>)` stays the same. |
| `LinkedQueue`, `Node` | Model | Should not change: `enqueue`, `dequeue` and `peek` already cover it. |
| `Medication`, `Patient`, `Observer` | Model | Should not change: `skipNextDose()` never calls `setQuantity`. |
| `Appointment`, `AppointmentManager`, `AppointmentView`, `AppointmentController` | Model, view, controller | Should not change: appointments are separate from doses. |
| `LoginView`, `DashboardView` | View | Should not change: they only receive updates through `Observer`. |

After Deliverable 4 we will compare this table with what we actually had to edit.

### Stretch goals

- **Committing to:** pushing the low-supply notification to the user. `getLowSupply()` already exists, so the work is sending it through `Observer` to the view.
- **Dropping:** the chatbot. None of us has built one, and our revised scope made it a last-resort goal.

### New tests needed

| Method | Normal case | Bad-input case |
| --- | --- | --- |
| `MedicationTracker.skipNextDose` | The skipped dose moves to the back and quantity is unchanged | Empty queue throws `IllegalStateException` |
| `MedicationTracker.takeNextDose` | A dose is taken when the first medication is out but others are not | All medications out throws `IllegalStateException` |
| `Medication` constructor and setters | Valid values are stored | Negative dosage, frequency or quantity, and a blank name, throw `IllegalArgumentException` |
| `Appointment` constructor | Valid details are stored | Blank doctor or location, and null date or time, throw `IllegalArgumentException` |
| `AppointmentManager.scheduleAppointment` | Next open slot is found after a double-booking | Null date or time throws `IllegalArgumentException`, not `NullPointerException` |
| `MedicationController`, `AppointmentController` | Each call reaches the model and an observer is notified | A model error reaches the view without a crash |

### Who does what

Owners below are the same as what each of us did on the spec.

| Item | Owner |
| --- | --- |
| Validation and bad-input tests for `Medication` and `Appointment` | Abhinav |
| Dose queue fixes and `skipNextDose()` with its tests | Pratyay |
| `MedicationController`, `AppointmentController`, `PatientController` | Pratyay |
| `MedicationView`, `AppointmentView`, `LoginView`, `DashboardView` | Abigail |
| Next-open-slot suggestion and moving display text out of the model | Abhinav |
| Low-supply notification through `Observer` | Abigail |

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
| `PatientTest.java` | Abigail | Wrote |
| `MedicationTest.java` | Abhinav | Wrote |
| `MedicationTrackerTest.java` | Pratyay, Abigail | Pratyay wrote; Abigail tested |
| `AppointmentTest.java` | Abhinav | Wrote |
| `AppointmentManagerTest.java` | Abigail | Wrote |
| `LinkedQueueTest.java` | Pratyay, Abhinav | Pratyay wrote; Abhinav reviewed |

### GenAI note

We used AI to help draft this README to summarize our key points and format the tables and text etc.. Additionally, it was used to go over all six test classes (74 tests, all passing), compared the code with the spec and the scope's bad-input cases.
