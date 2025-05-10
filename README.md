### 📄 Employee Management System (Spring Boot)

A secure and feature-rich RESTful API built with Spring Boot that allows user registration, authentication, and complete employee management with role-based access control.

---

### 🚀 Features

* **User Registration & Login** with JWT Authentication
* **Role-based Authorization** (`USER`, `ADMIN`)
* Auto-create **Employee profile on user registration**
* `ADMIN` can:

  * Add, update, delete any employee
  * View all employees
* `USER` can:

  * View all employees
  * Update only their own employee record
* Global **exception handling** and meaningful error messages
* **Search** employees by name
* **List** employee names by department

---

### 🔐 Role-Based Access Control

| Endpoint            | USER | ADMIN |
| ------------------- | ---- | ----- |
| Register/Login      | ✅    | ✅     |
| View Employees      | ✅    | ✅     |
| Update Own Details  | ✅    | ✅     |
| Add Employee        | ❌    | ✅     |
| Delete Employee     | ❌    | ✅     |
| Update Any Employee | ❌    | ✅     |

---

### ⚙️ Technologies Used

* Java 17+
* Spring Boot
* Spring Security + JWT
* Maven
* REST API
* Lombok
* Exception Handling
* Postman (for API testing)

---

### 📦 API Endpoints

#### Auth

* `POST /api/auth/register` – Register a new user
* `POST /api/auth/login` – Authenticate and get JWT token

#### Employees

* `GET /api/employees` – View all employees
* `GET /api/employees/search?name=Ram` – Search by name
* `GET /api/employees/department?department=HR` – Get employee names by department
* `POST /api/employees` – Add new employee (Admin only)
* `PUT /api/employees/{id}` – Update employee details (User: only own; Admin: any)
* `DELETE /api/employees/{id}` – Delete employee (Admin only)

---

### 📁 How to Run

```bash
git clone https://github.com/Santosh9390/employeemanagementsystem.git
cd employee-management-system
./mvnw spring-boot:run
```

Use Postman or any REST client to test the endpoints.

---

### 🧪 Default Roles

* **ROLE\_USER** – Assigned on registration
* **ROLE\_ADMIN** – Needs to be set manually in the database for testing admin features

