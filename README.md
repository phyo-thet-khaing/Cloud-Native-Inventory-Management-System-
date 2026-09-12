# Cloud Native Inventory Management System

A cloud-native inventory management system built with a microservices architecture and an automated CI/CD pipeline. The system provides a centralized platform for managing products, suppliers, purchases, sales, and stock operations with role-based access control.

---

## Table of Contents

- [Introduction](#introduction)
- [Project Overview](#project-overview)
- [Objectives](#objectives)
- [System Requirements](#system-requirements)
  - [Software Requirements](#software-requirements)
  - [Hardware Requirements](#hardware-requirements)
- [Non-Functional Requirements](#non-functional-requirements)
- [Microservice Architecture Design](#microservice-architecture-design)
- [Entities](#entities)
- [Entity Relationships](#entity-relationships)
- [Diagrams](#diagrams)
- [User Roles & Features](#user-roles--features)
- [Implementation](#implementation)
- [Conclusion](#conclusion)
- [Future Improvements](#future-improvements)

---

## Introduction

Inventory management plays an important role in modern businesses by helping organizations efficiently control products, suppliers, purchases, sales, and stock information. Effective inventory management allows businesses to maintain accurate stock levels, reduce operational costs, and improve decision-making.

However, many traditional inventory management processes still rely on manual record-keeping, which can lead to problems such as inaccurate stock information, difficulty tracking product movements, and delays in managing purchase and sales transactions.

To address these challenges, this project proposes a **Cloud Native Inventory Management System** that provides a centralized platform for managing inventory-related operations. The system follows a **microservices architecture** approach, where different business functions are developed as independent services. By using cloud-native technologies, containerization, and automated CI/CD pipelines, the system aims to improve scalability, maintainability, and deployment efficiency.

---

## Project Overview

The Cloud Native Inventory Management System is a web-based application designed to efficiently manage products, suppliers, purchases, sales, and inventory operations. The system aims to improve inventory accuracy, reduce manual processes, and provide better control over business operations.

The system provides role-based access control for three types of users:

- **Admin** — Manages users, roles, products, categories, suppliers, and system reports.
- **Warehouse Manager** — Manages warehouse operations, purchases, stock receiving, inventory levels, and stock movements.
- **Sales Staff** — Manages sales transactions and checks product availability.

The system is developed using a cloud-native approach with microservices architecture. Each business function is implemented as an independent service to improve scalability, maintainability, and deployment flexibility. Automated CI/CD pipelines and containerized deployment are used to support efficient software delivery and system management.

---

## Objectives

- To develop an inventory management system that efficiently manages products, suppliers, purchases, sales, and stock information.
- To reduce manual record-keeping errors and improve data accuracy.
- To provide real-time monitoring of inventory levels and stock movements.
- To improve the efficiency of warehouse and sales operations.
- To provide role-based access control for Admin, Warehouse Manager, and Sales Staff.
- To generate accurate reports for better decision-making.

---

## System Requirements

### Software Requirements

| Component | Technology |
|---|---|
| Frontend | TypeScript with Tailwind CSS / Flowbite |
| Backend | Spring Boot (Java) |
| Database | MySQL 8.0 or higher |
| ORM / Persistence | Hibernate / JPA |
| Containerization | Docker |
| API Testing Tool | Postman |
| Version Control | Git & GitHub |
| IDE / Development Tools | Visual Studio Code, IntelliJ IDEA / Eclipse |
| Build Tool | Maven (or Gradle) |

### Hardware Requirements

| Component | Specification |
|---|---|
| **Server (Minimum)** | 2 vCPU, 4GB RAM, 50GB SSD |
| **Server (Recommended)** | 4 vCPU, 8GB RAM, 100GB SSD |
| **Admin PC** | Windows 10/11, Intel Core i5, 8GB RAM (16GB recommended), 256GB SSD |
| **Warehouse Staff PC** | Windows 10/11, Intel Core i3, 4GB RAM, 128GB SSD |
| **Showroom Staff PC** | Windows 10/11, Intel Core i3, 4GB RAM, 128GB SSD |
| **Developer PC** | Intel Core i5/i7 or AMD Ryzen 5+, 16GB RAM, 512GB SSD |
| **User Devices** | Any desktop, laptop, tablet, or smartphone with a modern web browser |
| **Network** | Stable internet connection, minimum 10 Mbps |
| **Backup Storage** | External HDD, NAS, or cloud storage with at least 100GB capacity |

---

## Non-Functional Requirements

### Security
- Secure login using username and password.
- Role-based access control for Admin, Warehouse Manager, and Sales Staff.
- Encrypt sensitive data to prevent unauthorized access.

### Reliability
- Maintain data consistency during transactions.
- Ensure accurate stock updates without data loss.
- Provide backup support for data recovery.

### Performance
- Fast response time for user requests.
- Support multiple users accessing the system simultaneously.
- Handle large inventory data efficiently.

### User-Friendly
- Simple and easy-to-use interface.
- Clear navigation between system modules.
- Well-structured and readable layout.

---

## Microservice Architecture Design

| Microservice | Responsible Entities | Main Functions |
|---|---|---|
| **User Service** | User, Role, Authentication | Manage users and roles, Login, JWT, Security |
| **Product Service** | Product, Category | Manage product information |
| **Supplier Service** | Supplier, Purchase Order, Purchase Order Details | Manage suppliers and purchasing |
| **Location Service** | Location | Manage warehouse and showroom locations |
| **Inventory Service** | Inventory, Stock Transaction | Manage stock and inventory movement |
| **Notification Service** | — | Send low-stock alerts and system notifications |
| **Config Server** | — | Manage centralized configuration |
| **Service Discovery (Eureka)** | — | Register and discover microservices |
| **API Gateway** | — | Provide single entry point for all services |

---

## Entities

1. User
2. Role
3. Category
4. Product
5. Supplier
6. Location
7. Inventory
8. Purchase Order
9. Purchase Order Details
10. Stock Transaction

### User

| Column | Type | Constraint | Description |
|---|---|---|---|
| Id | BIGINT | PK, AUTO_INCREMENT | User ID |
| Role-id | BIGINT | FK, NOT NULL | References the assigned role |
| Name | VARCHAR(100) | NOT NULL | User name |
| Email | VARCHAR(100) | UNIQUE, NOT NULL | User email |
| Password | VARCHAR(255) | NOT NULL | Encrypted password |
| Phone | VARCHAR(20) | | Phone number |
| Status | ENUM | DEFAULT 1 | Active / Inactive |
| Created-at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | |

### Role

| Column | Type | Constraint | Description |
|---|---|---|---|
| Id | BIGINT | PK, AUTO_INCREMENT | Role ID |
| Name | VARCHAR(50) | UNIQUE, NOT NULL | Role name |

### Category

| Column | Type | Constraint | Description |
|---|---|---|---|
| Id | INT | PK, AUTO_INCREMENT | Category ID |
| Name | VARCHAR(100) | NOT NULL | Category name |
| Description | TEXT | NOT NULL | Category description |

### Product

| Column | Type | Constraint | Description |
|---|---|---|---|
| Id | INT | PK, AUTO_INCREMENT | Product ID |
| Category-id | INT | FK | Product category |
| Supplier-id | INT | FK | References the product supplier |
| Name | VARCHAR(100) | NOT NULL | Product name |
| Unit | VARCHAR(20) | | Unit |
| Price | DECIMAL(10,2) | | Price |
| Status | ENUM('ACTIVE','INACTIVE') | DEFAULT 'ACTIVE' | Product status |
| Created-at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Created date |

### Supplier

| Column | Type | Constraint | Description |
|---|---|---|---|
| Id | INT | PK, AUTO_INCREMENT | Supplier ID |
| Name | VARCHAR(100) | NOT NULL | Supplier name |
| Phone | VARCHAR(20) | | Supplier phone |
| Address | TEXT | | Supplier address |
| Created-at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Created date |

### Location

| Column | Type | Constraint | Description |
|---|---|---|---|
| Id | INT | PK, AUTO_INCREMENT | Warehouse ID |
| Name | VARCHAR(100) | NOT NULL | Warehouse name |
| Address | VARCHAR(255) | DEFAULT 'ACTIVE' | Physical address of the location |
| Created-at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Created date |

### Inventory

| Column | Type | Constraint | Description |
|---|---|---|---|
| Id | INT | PK, AUTO_INCREMENT | Unique identifier for inventory record |
| Product-id | INT | FK | References the product |
| Location-id | INT | FK | References the warehouse or showroom |
| Quantity | DECIMAL(10,2) | | Available stock quantity |
| Purchase date | | | |
| Created-at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Created date |

### Stock Transaction

| Column | Type | Constraint | Description |
|---|---|---|---|
| ID | INT | PK | Unique identifier for each transaction |
| Product-id | INT | FK | References the product |
| From-location-id | INT | FK | Source |
| To-location-id | INT | FK | Destination |
| Quantity | DECIMAL(10,2) | | Changed quantity |
| User | INT | FK | User who performed transaction |
| Transaction-date | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Transaction date |

### Purchase Order

| Column | Type | Constraint | Description |
|---|---|---|---|
| Id | INT | PK | Unique identifier for each purchase order |
| Supplier-id | INT | FK | Reference to the supplier |
| Order-date | DATE | | Date when the purchase order is created |
| Status | VARCHAR(50) or ENUM | | Pending, Approved, Completed, Cancelled |
| Total-amount | DECIMAL(10,2) | | Total cost of the purchase order |
| Created-by | INT | FK | User who creates the purchase order |
| Created-at | TIMESTAMP | | Date and time when the purchase order is created |

### Purchase Order Details

| Column | Type | Constraint | Description |
|---|---|---|---|
| Id | INT | PK | Unique identifier for each purchase order detail |
| Purchase-order-id | INT | FK | Reference to the related purchase order |
| Product-id | INT | FK | Reference to the purchased product |
| Quantity | INT | | Number of products ordered |
| Unit-price | DECIMAL(10,2) | | Price of one product unit |
| Subtotal | DECIMAL(10,2) | | Total price (quantity × unit price) |

---

## Entity Relationships

| From | Relationship | To |
|---|---|---|
| Role | 1 : Many | User |
| User | 1 : Many | Stock Transaction |
| Category | 1 : Many | Product |
| Supplier | 1 : Many | Product |
| Supplier | 1 : Many | Purchase Order |
| User | 1 : Many | Purchase Order |
| Purchase Order | 1 : Many | Purchase Order Details |
| Product | 1 : Many | Purchase Order Details |
| Product | 1 : Many | Inventory |
| Location | 1 : Many | Inventory |
| Product | 1 : Many | Stock Transaction |
| Location | 1 : Many | Stock Transaction |

---

## Diagrams

The following diagrams are included in the project documentation:

- **ER Diagram** — Entity-relationship diagram of the database.
- **Conceptual Design** — High-level conceptual model.
- **System Flow Diagram** — Overall system flow.
- **Use Case Diagram** — Use cases for Admin, Warehouse Staff, and Showroom Staff.
- **Class Diagram** — Class structure of the system.
- **Sitemap** — Navigation structure for Admin, Warehouse Staff, and Sales Staff.
- **Screen Design / Wireframe** — Available on [Figma](https://www.figma.com/design/o3xGtyrafWb2jmcZnjwaYo/Inventory-Management?node-id=0-1).

---

## User Roles & Features

### Admin

1. Login to the system.
2. Manage user accounts (Create, Update, Delete, View).
3. Assign user roles (Admin, Warehouse Staff, Showroom Staff).
4. Manage product categories.
5. Manage products (Add, Update, Delete, View).
6. Manage suppliers.
7. Create purchase orders.
8. Manage purchase order details.
9. Manage warehouse and showroom locations.
10. View inventory levels at all locations.
11. View stock transaction history.
12. Generate inventory reports.
13. Search products and inventory.
14. View dashboard statistics (Total Products, Total Stock, Low Stock Items).

### Warehouse Staff

1. Login to the system.
2. View product information.
3. View supplier information.
4. View purchase orders.
5. Receive products from suppliers.
6. Record stock-in transactions.
7. Record stock-out transactions.
8. Update inventory quantities.
9. Manage inventory levels.
10. Transfer products between locations.
11. View stock transaction history.
12. Search products and inventory.
13. Monitor low-stock products.
14. View inventory reports.

### Showroom Staff

1. Login to the system.
2. View available products.
3. Search products.
4. Check product availability.
5. View showroom inventory.
6. Request products from the warehouse.
7. Receive transferred products.
8. View stock transaction history.
9. View purchase order status.
10. View product details.
11. View dashboard information.

---

## Implementation

The Cloud Native Inventory Management System was developed using a microservices architecture to ensure scalability, maintainability, and flexibility. The system was divided into independent services, including:

- User Service
- Product Service
- Supplier Service
- Inventory Service
- Authentication Service

Each microservice is responsible for a specific business function and communicates through RESTful APIs. **Spring Boot** was used for developing the backend microservices, while **MySQL** was utilized for data storage.

Service discovery and centralized configuration management were implemented to simplify communication and configuration across services. An **API Gateway** was used as a single entry point for client requests and to route traffic to the appropriate microservices.

**Containerization** was achieved using **Docker**, allowing each service to run consistently across different environments. **Docker Compose** was used for local orchestration and deployment of system components.

To support DevOps practices, an automated **CI/CD pipeline** was implemented using **Jenkins**. The pipeline automatically performed:

- Source code retrieval
- Dependency installation
- Application building
- Automated testing
- Code quality analysis
- Docker image creation
- Deployment

**SonarQube** was integrated to perform static code analysis and maintain code quality standards throughout the development lifecycle.

The frontend application provides user interfaces for managing products, categories, suppliers, purchase orders, locations, and inventory records. The system was tested to ensure functional correctness, service integration, and deployment reliability.

---

## Conclusion

This project successfully developed a Cloud Native Inventory Management System using Microservices Architecture and an Automated CI/CD Pipeline. The adoption of microservices improved modularity, scalability, and maintainability by separating business functionalities into independent services.

The integration of Docker containerization enabled consistent application deployment across different environments, while the automated CI/CD pipeline streamlined software delivery through continuous integration, testing, quality verification, and deployment. These practices reduced manual intervention and improved development efficiency.

The system provides comprehensive inventory management capabilities, including product management, supplier management, inventory tracking, location management, and reporting. The cloud-native approach ensures that the system can be easily extended and deployed in modern cloud environments.

---

## Future Improvements

- Kubernetes-based container orchestration
- Real-time notifications
- Advanced analytics dashboards
- Barcode integration
- Cloud platform deployment

---

## License

This project is developed for academic purposes. Please refer to the project repository for licensing details.
