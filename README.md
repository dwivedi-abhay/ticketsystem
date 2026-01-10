# 🎟️ Scalable Ticket Booking & Reservation Backend

A **production-grade backend system** for ticket booking and seat reservation, inspired by large-scale platforms such as BookMyShow.  
The system is designed to handle **high concurrency, consistency, and scalability**, with a strong focus on **clean architecture and real-world backend design principles**.

---

## 🏛️ System Overview

This backend supports the complete lifecycle of ticket booking:

- Event and venue management
- Real-time seat availability
- Concurrent seat locking
- Reliable booking confirmation
- Failure-safe seat release

The system is built to prevent **double booking**, handle **thousands of concurrent users**, and maintain **data consistency** under load.

---

## 🧠 Design Philosophy

- **Explicit state modeling** instead of boolean flags
- **Database as the source of truth**
- **Cache for speed, not correctness**
- **Stateless services**
- **Clear separation of responsibilities**
- **Fail-safe defaults**

---

## 🏗️ High-Level Architecture

```
Client
  |
  v
API Layer (REST Controllers)
  |
  v
Service Layer (Business Logic)
  |
  +--> Seat Locking Layer (Redis)
  |
  v
Persistence Layer (Repositories)
  |
  v
PostgreSQL (Primary Database)
```

---

## 🧩 Modular Architecture

Each domain module follows a strict layered structure:

```
module
 ├── controller   // API endpoints
 ├── service      // Business rules
 ├── repository   // Database access
 └── domain       // Entities & state
```

This ensures maintainability, testability, and scalability.

---

## 🗄️ Domain Model

### Core Entities

- **Event** – Represents a show, movie, or match
- **Venue** – Physical location hosting events
- **Screen / Hall** – Subdivision of a venue
- **Seat** – Individual bookable unit
- **Booking** – Confirmed reservation

---

## 🔄 State-Based Seat Management

Seats transition through well-defined states:

```
AVAILABLE → LOCKED → BOOKED
```

- **AVAILABLE** – Free to be selected
- **LOCKED** – Temporarily reserved for a user
- **BOOKED** – Permanently assigned

This prevents ambiguity and simplifies concurrency handling.

---

## 🔐 Seat Locking Strategy

### Why Seat Locking Exists
Multiple users may attempt to book the same seat simultaneously.  
The system must guarantee **mutual exclusion** during seat selection.

### Implementation
- Seat locks are stored in **Redis**
- Locks have a **fixed TTL**
- Locks are user-scoped
- Expired locks are automatically released

### Key Principle
Redis manages **temporary intent**, not permanent state.

---

## 📘 Booking Workflow

1. User requests seat availability
2. User selects seats
3. Selected seats are locked in Redis
4. Payment is initiated
5. On success:
    - Booking is persisted
    - Seats are marked BOOKED in the database
    - Locks are removed
6. On failure or timeout:
    - Locks expire automatically
    - Seats return to AVAILABLE state

This flow guarantees **consistency and safety**.

---

## 🧠 Redis vs Database Responsibilities

| Concern | Redis | Database |
|------|-------|----------|
| Seat locking | ✅ | ❌ |
| Temporary state | ✅ | ❌ |
| High concurrency | ✅ | ❌ |
| Permanent storage | ❌ | ✅ |
| Source of truth | ❌ | ✅ |
| Transactions | ❌ | ✅ |

---

## 📦 Technology Stack

| Layer | Technology |
|-----|-----------|
| Language | Java |
| Framework | Spring Boot |
| Database | PostgreSQL |
| Cache | Redis |
| ORM | Spring Data JPA |
| Schema Migration | Flyway |
| Build Tool | Gradle |
| Containerization | Docker |

---

## 🧪 Data Consistency & Concurrency

- Database transactions ensure atomic bookings
- Redis locks prevent race conditions
- Idempotent operations protect against retries
- Time-based expiration avoids deadlocks

---

## 🧱 Engineering Principles Applied

- Single Responsibility Principle
- Open/Closed Principle
- Explicit state machines
- Stateless services
- Defensive programming
- Scalability-first design

---

## ⚠️ Failure Handling

The system safely handles:

- Payment failures
- User abandonment
- Network retries
- Redis restarts
- Partial system outages

No permanent corruption or double booking can occur.

---

## 📈 Scalability Considerations

- Horizontal scaling of services
- Redis clustering support
- Database read replicas
- Stateless API layer
- Clear boundaries for future sharding

---

## 🧾 Summary

This project demonstrates how real-world ticket booking systems:

- Handle massive concurrency
- Prevent double booking
- Balance performance with correctness
- Separate transient and persistent state
- Scale without sacrificing reliability

It serves as a **reference implementation** for designing robust, production-ready backend systems.
