# Enterprise Order & Packaging ERP System

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7-6DB33F?logo=spring-boot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-13-DD0031?logo=angular&logoColor=white)
![Keycloak](https://img.shields.io/badge/Keycloak-18.0-0081C4?logo=keycloak&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-3.0-003545?logo=mariadb&logoColor=white)

> A highly scalable, microservices-based Enterprise Resource Planning (ERP) system tailored for order management, manufacturing workflows, and B2B logistics.

## 📖 Executive Summary

This project is a comprehensive enterprise management platform designed to digitize and optimize complex manufacturing and logistics operations. Built using a modern **Microservices Architecture**, the system offers a robust administrative backend, highly secure identity management, and a responsive, dynamic web interface.

The solution successfully handles complete order lifecycles, configuration management, and role-based access control across multiple organizational levels.

## 🏗 System Architecture

The platform follows a modular microservices architecture to ensure high availability, fault tolerance, and independent scalability of domains.

```mermaid
graph TD
    %% Users
    Admin[Admin Users]
    Client[B2B Clients]

    %% API Gateway / Frontend
    WebApp[Angular Web Client \n (erp-frontend)]
    
    %% Infrastructure
    Keycloak[Keycloak Identity Server]
    MinIO[MinIO Object Storage]
    
    %% Microservices
    subgraph Microservices Cluster
        UsersWS(Users Service \n erp-users-ws)
        ConfigWS(Config & File Service \n erp-config-ws)
        CoreWS(Core Business Service \n erp-core-ws)
        
        subgraph Core Modules
            Celler[Celler Module]
            Order[Order Module]
            Design[Design Module]
        end
        CoreWS --- Celler
        CoreWS --- Order
        CoreWS --- Design
    end

    %% Databases
    DB1[(Users DB - MariaDB)]
    DB2[(Config DB - MariaDB)]
    DB3[(Core DB - MariaDB)]

    %% Connections
    Admin --> WebApp
    Client --> WebApp
    
    WebApp -- OAuth2/OIDC --> Keycloak
    WebApp -- HTTP/REST --> UsersWS
    WebApp -- HTTP/REST --> ConfigWS
    WebApp -- HTTP/REST --> CoreWS
    
    UsersWS --> Keycloak
    UsersWS --> DB1
    ConfigWS --> DB2
    ConfigWS --> MinIO
    CoreWS --> DB3
```

## 🚀 Key Features

* **Identity & Access Management (IAM):** Fully integrated with **Keycloak** (v18) for JWT-based OAuth2 authentication and granular Role-Based Access Control (RBAC).
* **Order & Workflow Management:** Dedicated microservices to handle B2B client orders, custom packaging designs, and celler/warehouse management.
* **Centralized Configuration & Storage:** Integrated **MinIO** for scalable, S3-compatible object storage (handling invoices, design files, and documentation).
* **Advanced Reporting:** Dynamic PDF and Excel report generation utilizing **JasperReports**.
* **Database Versioning:** Automated schema migrations and version control powered by **Flyway**.
* **Responsive UI:** A feature-rich Single Page Application (SPA) built with **Angular 13**, **PrimeNG**, and **Chart.js** for real-time analytics dashboards.

## 🛠 Technology Stack

### Backend
* **Java 11**
* **Spring Boot (2.6 / 2.7):** Core framework for all microservices.
* **Spring Cloud OpenFeign:** Declarative REST clients for inter-service communication.
* **Spring Security:** End-to-end security via Keycloak adapters.
* **MapStruct & Lombok:** Boilerplate reduction and fast object mapping.
* **JasperReports:** Business intelligence and document generation.

### Frontend
* **Angular 13**
* **PrimeNG / PrimeFlex:** Comprehensive UI component library and CSS utility toolkit.
* **RxJS:** Reactive programming for complex asynchronous event handling.
* **Ngx-Translate:** Full i18n support for multi-language capabilities.
* **FullCalendar:** Interactive scheduling and timeline management.

### Infrastructure & Data
* **MariaDB:** Relational databases for all services.
* **Flyway:** Database migration and state management.
* **Docker:** Containerization of microservices.
* **Jenkins:** CI/CD pipelines (Jenkinsfiles provided per service).

## 📂 Repository Structure

The project is structured as a monorepo containing the following discrete domains:

| Directory | Description |
| :--- | :--- |
| `/erp-frontend` | The Angular 13 frontend application serving both Admins and Clients. |
| `/erp-core-ws` | A multi-module Spring Boot application managing the core business logic (`orders`, `design`, `celler`). |
| `/erp-users-ws` | The user management service, acting as a bridge to Keycloak for identity provisioning. |
| `/erp-config-ws` | Handles system configurations and integration with MinIO for file storage. |

## 🚦 Getting Started

### Prerequisites
* JDK 11
* Node.js (v12/v14) & Angular CLI 13
* Docker & Docker Compose (for infrastructure services)

### Local Development Setup

1. **Infrastructure Configuration:**
   Ensure Keycloak, MariaDB, and MinIO are running locally. (A `docker-compose.yml` can be utilized for this).

2. **Backend Services:**
   Navigate into each backend service directory and boot it via Gradle:
   ```bash
   cd erp-core-ws
   ./gradlew bootRun
   ```

3. **Frontend Application:**
   Install dependencies and start the Angular development server:
   ```bash
   cd erp-frontend
   npm install
   npm run start
   ```

## 🔒 License & Usage
This is a portfolio project demonstrating Staff-level software engineering, microservices architecture, and enterprise application development. All specific client configurations and proprietary business data have been anonymized.
