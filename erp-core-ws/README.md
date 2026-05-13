# cp-core-ws -- Core Business Microservice

![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.6.7-6DB33F?logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-11-007396?logo=java&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-3.0-003545?logo=mariadb&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-6.8.3-02303A?logo=gradle&logoColor=white)

## Overview

`cp-core-ws` is the primary business-logic microservice in the Carton Plast ERP system. It manages the entire lifecycle of orders, die design and manufacturing, warehouse (celler) operations, client management, mixtures and material requests, menu/permission structures, and catalog maintenance.

This is a **multi-module Gradle project** with three submodules:

| Module | Path | Purpose |
|--------|------|---------|
| `cp-core-ws` (root) | `.` | Spring Boot application, controllers, services, repositories, entities, Flyway migrations |
| `cp-core-vo` | `cp-core-vo/` | Value Objects: request/response DTOs shared across the service |
| `cp-core-common` | `cp-core-common/` | Shared utilities: exception framework, audit base entity, specification builder, HTTP/MinIO helpers |

## Architecture

```mermaid
flowchart TD
    KC[Keycloak 18.0 OAuth2] --> CP[cp-core-ws]
    CP -->|OpenFeign| CFG[cp-config-ws]
    CFG --> MINIO[MinIO S3 Storage]
    CP --> DB[(MariaDB)]
    DB --> FW[Flyway 21 migrations]
    
    subgraph Consumers
        FE[erp-frontend Angular SPA]
        USR[cp-users-ws]
    end
    FE --> CP
    USR --> CP
```

- **Identity**: Keycloak 18.0 (bearer-only, resource `carton-plast-backend`, realm `carton-plast`)
- **Storage**: Communicates with `cp-config-ws` via OpenFeign for file uploads/downloads (MinIO-backed)
- **Database**: MariaDB with Flyway-managed schema (`cartonplast-dev`, `cartonplast_develop`, `cartonplast_test`)
- **Timezone**: `America/Guayaquil` (set at startup)

## Database Entity-Relationship Diagram

### Orders Domain

```mermaid
erDiagram
    CATCLI {
        int ID_CATCLI_CODE PK
        varchar CATCLI_NAME
        varchar CATCLI_RUC
        varchar CATCLI_ADDRESS
    }
    CATPROY {
        int ID_CATPROY_CODE PK
        varchar CATPROY_NAME
        int XID_CATCLI_CODE FK
    }
    CATSTATUS {
        varchar ID_CATSTATUS_CODE PK
        varchar CATSTATUS_NAME
        varchar CATSTATUS_TYPE
        bool CATSTATUS_IS_VISIBLE
    }
    CATPRI {
        varchar ID_CATPRI_CODE PK
        varchar CATPRI_NAME
        varchar CATPRI_COLOR
    }
    COTORD {
        int ID_COTORD_CODE PK
        varchar COTORD_CODE UK
        varchar XID_CATPRI_CODE FK
        int XID_COT_ID_COTORD_CODE FK
        int XID_CATPROY_CODE FK
        int XID_CATCLI_CODE FK
        varchar XID_CATSTATUS_CODE FK
        varchar COTORD_PRODUCT_CODE
        varchar COTORD_NAME
        int COTORD_QUANTITY
        timestamp COTORD_ORDERED_AT
        varchar COTORD_LOTE
    }

    CATCLI ||--o{ COTORD : "tiene"
    CATPROY ||--o{ COTORD : "pertenece a"
    CATSTATUS ||--o{ COTORD : "define estado"
    CATPRI ||--o{ COTORD : "define prioridad"
    COTORD ||--o{ COTORD : "auto-referencia (lotes)"
```

### Design Domain (Dies, Machines, Cyrels)

```mermaid
erDiagram
    CATFAB {
        int ID_CATFAB_CODE PK
        varchar CATFAB_NAME
        varchar CATFAB_DESCRIPTION
    }
    CATSTATUS {
        varchar ID_CATSTATUS_CODE PK
        varchar CATSTATUS_NAME
    }
    CATPRODTRO {
        int ID_CATPRODTRO_CODE PK
        varchar XID_CATSTATUS_CODE FK
        varchar CATPRODTRO_PTROQ UK
        varchar CATPRODTRO_NAME
    }
    CATTRO {
        int ID_CATTRO_CODE PK
        int XID_CATFAB_CODE FK
        varchar XID_CATSTATUS_CODE FK
        int XID_CATPRODTRO_CODE FK
        varchar CATTRO_NAME
        decimal CATTRO_QUANTITY
    }
    CATMAQ_CAT {
        int ID_CATMAQ_CAT_CODE PK
        varchar CATMAQ_CAT_NAME
    }
    CATMAQ {
        int ID_CATMAQ_CODE PK
        int XID_CATMAQ_CAT_CODE FK
        varchar CATMAQ_NAME UK
        double CATMAQ_WIDTH_EXT
        bool CATMAQ_HAS_DESB
    }
    CATMAQ_TRO {
        int XID_CATTRO_CODE PK
        int XID_CATMAQ_CODE PK
    }

    CATFAB ||--o{ CATTRO : fabrica
    CATSTATUS ||--o{ CATTRO : "estado del troquel"
    CATPRODTRO ||--o{ CATTRO : "producto troquelado"
    CATSTATUS ||--o{ CATPRODTRO : "estado del producto"
    CATMAQ_CAT ||--o{ CATMAQ : cataloga
    CATTRO ||--o{ CATMAQ_TRO : "se fabrica en"
    CATMAQ ||--o{ CATMAQ_TRO : "usa troquel"
```

### Warehouse (Celler) Domain

```mermaid
erDiagram
    CDTLOC {
        int ID_CDTLOC_CODE PK
        varchar CDTLOC_LOCATION
        varchar CDTLOC_DESCRIPTION
    }
    CDTDOC {
        int ID_CDTDOC_CODE PK
        varchar CDTDOC_NAME
        varchar CDTDOC_DESCRIPTION
    }
    CDTOPT {
        int ID_CDTOPT_CODE PK
        int XID_CDTDOC_CODE FK
        varchar CDTOPT_NAME
    }
    CDTCAT {
        int ID_CDTCAT_CODE PK
        varchar CDTCAT_NAME
    }
    CDTTIP {
        int ID_CDTTIP_CODE PK
        varchar CDTTIP_NAME
    }
    CDTCELL {
        int ID_CDTCELL_CODE PK
        varchar CDTCELL_NUM_DOC
        timestamp CDTCELL_DATE
        varchar CDTCELL_REASON
        bool CDTCELL_STATE
    }
    CDTCELL_DET {
        int ID_CDTCELL_DET_CODE PK
        int XID_CDTCELL_CODE FK
        int XID_CDTCAT_ID FK
        int XID_CDTLOC_CODE FK
        int XID_CDTDOC_CODE FK
        varchar CDTCELL_DET_LOTE
        int CDTCELL_DET_AMOUNT
        double CDTCELL_DET_WEIGHT
    }

    CDTDOC ||--o{ CDTOPT : opciones
    CDTCELL ||--o{ CDTCELL_DET : detalla
    CDTCAT ||--o{ CDTCELL_DET : material
    CDTLOC ||--o{ CDTCELL_DET : ubicacion
    CDTDOC ||--o{ CDTCELL_DET : "tipo documento"
```

### Mixture Domain

```mermaid
erDiagram
    COTORD {
        int ID_COTORD_CODE PK
        varchar COTORD_CODE
    }
    CATTRO {
        int ID_CATTRO_CODE PK
        varchar CATTRO_NAME
    }
    CDTCAT {
        int ID_CDTCAT_CODE PK
    }
    CETMIX {
        int ID_CETMIX_CODE PK
        int XID_COTORD_CODE FK
        int XID_CATTRO_CODE FK
        int CETMIX_PREPARE
        int CETMIX_NUMBER
        varchar CETMIX_MIXTURE
        double CETMIX_TOTAL
    }
    CETMIX_DET {
        int ID_CETMIX_DET_CODE PK
        int XID_CETMIX_CODE FK
        int XID_CDTCAT_CODE FK
        double CETMIX_DET_PERCENT
        double CETMIX_DET_TOTAL
    }

    COTORD ||--o{ CETMIX : "asociada a"
    CATTRO ||--o{ CETMIX : "usa troquel"
    CETMIX ||--o{ CETMIX_DET : compuesta_por
    CDTCAT ||--o{ CETMIX_DET : "material usado"
```

## Domain Entity Class Diagram

```mermaid
classDiagram
    class Order {
        +Long id
        +String code
        +String productCode
        +String name
        +Integer quantity
        +LocalDateTime orderedAt
        +String lote
        +Client client
        +Project project
        +CatalogStatus status
        +CatalogPriority priority
        +Order parentOrder
        +LocalDateTime estimatedDeliveredAt
        +String clientOrderCode
        +String observation
        +String cancellationReason
    }
    class Die {
        +Long id
        +String name
        +BigDecimal quantity
        +String dsbMultiple
        +String observations
        +BigDecimal separationL
        +BigDecimal separationW
        +BigDecimal borderL
        +BigDecimal borderW
        +Manufacturer manufacturer
        +CatalogStatus status
        +DieProduct dieProduct
        +List~Machine~ machines
    }
    class Celler {
        +Long id
        +String numDoc
        +LocalDateTime date
        +String reason
        +Boolean state
        +List~CellerDetail~ details
    }
    class Mixture {
        +Long id
        +Integer prepare
        +Integer preMixture
        +Integer number
        +String mixture
        +BigDecimal total
        +Order order
        +Die die
        +List~MixtureDetail~ details
    }
    class Client {
        +Long id
        +String name
        +String ruc
        +String address
        +String phone
        +String imagePath
    }
    class Menu {
        +Long id
        +String name
        +String icon
        +String routerLink
        +Integer order
        +Menu parent
        +List~Menu~ children
    }
    class CatalogStatus {
        +String id
        +String name
        +String backgroundColor
        +String color
        +String type
        +Boolean isVisible
        +Boolean isDefault
    }
    class Permission {
        +Long id
        +String role
        +Menu menu
        +Boolean canCreate
        +Boolean canRead
        +Boolean canUpdate
        +Boolean canDelete
    }

    Order --> Client
    Order --> CatalogStatus
    Order --> CatalogPriority
    Die --> CatalogStatus
    Die --> Manufacturer
    Mixture --> Order
    Mixture --> Die
    Mixture --> MixtureDetail
    Menu --> Menu : parent
    Permission --> Menu
```

## Flow Diagrams

### Order Lifecycle

```mermaid
stateDiagram-v2
    [*] --> Pendiente: Created
    Pendiente --> En_Produccion: Start Production
    En_Produccion --> Completado: Finish
    En_Produccion --> Pendiente: Cancel
    Pendiente --> Cancelado: Cancel
    Completado --> [*]
    Cancelado --> [*]

    note right of Pendiente: Initial state
    note right of En_Produccion: Production started
    note right of Completado: Order fulfilled
```

### Warehouse Document Flow

```mermaid
flowchart LR
    subgraph Ingresos
        CIB[CIB - Comprobante Ingreso Bodega]
        TM1[TM1 - Traslado Material Ingreso]
        CEP[CEP - Egreso Planta]
    end
    subgraph Egresos
        CEB[CEB - Comprobante Egreso Bodega]
        TM5[TM5 - Traslado Material Egreso]
    end
    subgraph Internos
        MOV[MOV - Movimiento Interno]
    end

    CIB -->|Material entra a bodega| STOCK[(Inventario)]
    TM1 -->|Material de masterbatch| STOCK
    CEP -->|Devolucion planta a bodega| STOCK
    CEB -->|Material sale de bodega| STOCK
    TM5 -->|Material a masterbatch| STOCK
    MOV -->|Cambio de ubicacion| STOCK
```

### PDF Report Generation Flow

```mermaid
sequenceDiagram
    participant FE as Frontend
    participant CP as cp-core-ws
    participant DB as MariaDB
    participant CFG as cp-config-ws
    participant MINIO as MinIO

    FE->>CP: GET /api/v1/celler/generate-pdf?type=CIB&id=123
    CP->>DB: Query celler + details
    DB-->>CP: Celler data
    CP->>CP: Fill JasperReport template
    CP-->>FE: Return PDF bytes
    Note over CP: Templates: CIB.jrxml, CEB.jrxml,<br/>REQUISICION.jrxml, PREMEZCLA.jrxml

    FE->>CP: POST /api/v1/dies/{id}/documents (multipart)
    CP->>CFG: POST /api/v1/files/upload
    CFG->>MINIO: putObject(bucket, file)
    MINIO-->>CFG: OK
    CFG-->>CP: FileRes (url, path)
    CP->>DB: Save document reference
    CP-->>FE: Document created
```

## Domain Packages

| Package | Path | Responsibility |
|---------|------|---------------|
| `design` | `.../design/` | Dies, machines, cyrels, printers, homopolymers, colors (A/B/catalog/type/folio), thickness, manufacturer, die products, die documents, cyrel documents |
| `orders` | `.../orders/` | Order CRUD, status transitions (start/cancel), code generation, search |
| `celler` | `.../celler/` | Warehouse: locations, documents, materials, types, celler details, option documents |
| `materialrequest` | `.../materialrequest/` | Material requests (requisiciones) and production turns |
| `mixture` | `.../mixture/` | Mixture formulas and mixture details (premezclas) |
| `client` | `.../client/` | Client management with logo upload |
| `menu` | `.../menu/` | Dynamic menu tree and permission assignment |
| `catalog` | `.../catalog/` | Status and priority catalogs |
| `project` | `.../project/` | Project management (client-linked) |
| `handler` | `.../handler/` | Global REST exception handlers |

## Controllers (31 total)

### design (13)
| Controller | Endpoint | Operations |
|-----------|----------|------------|
| `DieController` | `GET/POST/PUT/DELETE /api/v1/dies` | CRUD dies, search by die product |
| `MachineController` | `/api/v1/machines` | Extrusion machine CRUD |
| `CyrelController` | `/api/v1/cyrels` | Cyrel (printing sleeve) CRUD |
| `PrinterController` | `/api/v1/printers` | Printer CRUD |
| `HomoPolymerController` | `/api/v1/homopolymers` | Homopolymer CRUD |
| `ColorAController` | `/api/v1/colors/a` | Color A CRUD |
| `ColorBController` | `/api/v1/colors/b` | Color B CRUD |
| `ColorCatalogController` | `/api/v1/colors/catalog` | Color catalog CRUD |
| `ColorTypeController` | `/api/v1/colors/types` | Color type CRUD |
| `ThicknessController` | `/api/v1/thickness` | Thickness CRUD |
| `ManufacturerController` | `/api/v1/manufacturers` | Manufacturer CRUD |
| `DieProductController` | `/api/v1/die-products` | Die product CRUD |
| `DieDocumentController` | `/api/v1/dies/{dieId}/documents` | Die document upload/list |
| `CyrelDocumentController` | `/api/v1/cyrels/{cyrelId}/documents` | Cyrel document upload/list |
| `ProjectController` | `GET /api/v1/projects` | List all, search by client, find by code |

### orders (1)
| Controller | Endpoint | Operations |
|-----------|----------|------------|
| `OrderController` | `POST/GET/PUT /api/v1/orders` | CRUD, search, cancel, start, generate code, find by lot/status |

### celler (7)
| Controller | Endpoint | Operations |
|-----------|----------|------------|
| `CellerController` | `/api/v1/celler` | Warehouse document CRUD |
| `CellerDetailController` | `/api/v1/celler/details` | Warehouse line items |
| `LocationController` | `/api/v1/celler/locations` | Warehouse locations CRUD |
| `DocumentController` | `/api/v1/celler/documents` | Document type CRUD |
| `OptionDocumentController` | `/api/v1/celler/option-documents` | Option document CRUD |
| `MaterialController` | `/api/v1/celler/materials` | Material CRUD |
| `TypeMaterialController` | `/api/v1/celler/type-materials` | Material type CRUD |

### materialrequest (2)
| Controller | Endpoint | Operations |
|-----------|----------|------------|
| `MaterialRequestController` | `/api/v1/material-requests` | CRUD material requests |
| `TurnController` | `/api/v1/turns` | CRUD production turns |

### mixture (1)
| Controller | Endpoint | Operations |
|-----------|----------|------------|
| `MixtureController` | `/api/v1/mixtures` | CRUD mixtures and details |

### client (1)
| Controller | Endpoint | Operations |
|-----------|----------|------------|
| `ClientController` | `GET/POST/DELETE /api/v1/clients` | CRUD clients with logo upload |

### menu (2)
| Controller | Endpoint | Operations |
|-----------|----------|------------|
| `MenuController` | `/api/v1/menus` | Dynamic menu tree |
| `PermissionController` | `/api/v1/permissions` | Permission CRUD |

### catalog (2)
| Controller | Endpoint | Operations |
|-----------|----------|------------|
| `CatalogStatusController` | `/api/v1/catalogs/statuses` | Order status catalog |
| `CatalogPriorityController` | `/api/v1/catalogs/priorities` | Order priority catalog |

## Database Migrations (Flyway)

21 incremental migrations under `src/main/resources/db/migration/`. All tables use Spanish/abbreviated naming.

| Migration | Tables Created | Description |
|-----------|---------------|-------------|
| V1.0 | `CATFAB`, `CATTRO`, `CATMAQ` | Manufacturers, dies (troqueles), machines |
| V1.1 | `CATSTATUS` | Order status catalog |
| V1.2 | `CBTMEN` | Menu structure |
| V1.3 | `CATHOM` | Homopolymers |
| V1.4 | `CATCOP`, `CATCOL`, `CATFOL` | Color catalogs (color A, color B, folio) |
| V1.5 | `CATTHI` | Thickness |
| V1.6 | `CATCLI` | Clients |
| V1.7 | `CATIMP` | Printers |
| V1.8 | `CATCIR` | Cyrels (printing sleeves) |
| V1.9 | `CATCTIPPRO`, `CATPRO` | Project types, projects |
| V1.10 | `CBTPER` | Permissions |
| V1.11 | `CATPRI` | Order priorities |
| V1.12 | `COTORD`, `COTORD_DET` | Orders, order details |
| V1.13 | `CDTCAT`, `CDTTIP` | Warehouse catalogs, types |
| V1.14 | `CDTLOC` | Warehouse locations |
| V1.15 | `CDTDOC` | Warehouse documents |
| V1.16 | `CDTOPT` | Warehouse option documents |
| V1.17 | `CATCIRDOC`, `CATTRODOC` | Cyrel documents, die documents |
| V1.18 | `CETMIX`, `CETMIX_DET` | Mixtures, mixture details |
| V1.19 | `CFTTURN` | Production turns |
| V1.20 | `CFTREQ`, `CFTREQ_DET` | Material requests, request details |
| V1.21 | `CATMAQ` (inserts) | Extrusion machine seed data |

## Naming Convention for Tables

- `CAT*` -- Catalogs (manufacturers, dies, machines, statuses, colors, etc.)
- `CBT*` -- Structure/configuration (menus, permissions)
- `COT*` -- Orders (order, order detail)
- `CDT*` -- Warehouse/celler (catalogs, types, locations, documents, options)
- `CET*` -- Production mixtures (mix, mix detail)
- `CFT*` -- Production flow (turns, requests, request details)

## JasperReports

Four report templates in `src/main/resources/receipts/`:

| File | Purpose |
|------|---------|
| `REQUISICION.jrxml` | Material request (requisicion) PDF |
| `PREMEZCLA.jrxml` | Mixture/pre-mix sheet PDF |
| `CIB.jrxml` | Internal warehouse entry (Comprobante de Ingreso a Bodega) PDF |
| `CEB.jrxml` | Internal warehouse exit (Comprobante de Egreso de Bodega) PDF |

## Build & Dependencies

**Build**: Gradle 6.8.3, Java 11, fat JAR via `bootJar`

**Key dependencies** (`build.gradle`):
- `spring-boot-starter-web` 2.6.7
- `spring-boot-starter-data-jpa` 2.6.7
- `spring-boot-starter-security` 2.6.7 (Keycloak adapter)
- `spring-cloud-starter-openfeign` 3.1.1
- `flyway-core` / `flyway-mysql` 8.0.5
- `mariadb-java-client` 3.0.4
- `mapstruct` 1.4.2.Final / `mapstruct-processor` 1.4.2.Final
- `lombok` 1.18.22
- `net.sf.jasperreports:jasperreports` 6.17.0
- `springdoc-openapi-ui` 1.6.4 (OpenAPI/Swagger)
- `org.icepear.echarts:echarts-java` 1.0.1 (chart rendering)

## Configuration Profiles

| Profile | Port | DB Name | Keycloak URL | MinIO Buckets |
|---------|------|---------|--------------|---------------|
| `local` | 8080 | `cartonplast-dev` | `https://auth-test.carton-plast.com` | `images-test`, `documents-test` |
| `develop` | 8080 | `cartonplast_develop` | `https://auth-dev.carton-plast.com` | `images-develop`, `documents-develop` |
| `test` | 8080 | `cartonplast_test` | `https://auth-test.carton-plast.com` | `images-test`, `documents-test` |
| `master` | 8080 | -- | `https://auth-test.carton-plast.com` | -- |

## CI/CD

- **Jenkinsfile**: Kubernetes pod with `dind` (Docker), `gradle:6.8.3-jdk11`, and `kustomize:v4.1.3`
- **Pipeline stages**: `test` -> `SonarQube` -> `build` (`gradle clean build -x test`) -> Docker image build -> push to registry -> ArgoCD deploy (develop/test)
- **Dockerfile**: `openjdk:11.0.15`, exposes port 8080, runs fat JAR `cp-core-ws-1.0.0.jar`

## Running Locally

```bash
cd erp-core-ws
./gradlew bootRun
# App starts at http://localhost:8080
# Active profile: local
```

## Related Services

- **cp-config-ws** -- File storage service (MinIO integration via OpenFeign)
- **cp-users-ws** -- User, person, and preferences management
- **erp-frontend** -- Angular 13 SPA consuming this API
