# 08 — ER Model and Flows

This document shows a conceptual E/R diagram and flow diagrams that match the SQL entities used in the project requirements. The diagram reflects the current SQL definitions for Collector, Scale, RailwayCompany, Manufacturer, RailwayModel, RollingStock, Collection and related tables, and Maintenance tables.

## ER Diagram (aligned with SQL entities)

```mermaid
erDiagram
    COLLECTOR {
        INTEGER id PK
        TEXT name
        TEXT preferred_currency
        TEXT preferred_measure
    }

    SCALE {
        TEXT id PK          "URN: urn:scale:{name}"
        TEXT name
        TEXT ratio
        TEXT track_gauge
        REAL gauge_mm
        REAL gauge_in
        TEXT standard       -- enum: NEM, NMRA, British, Japanese
        TEXT description
        TEXT created_at -- ISO 8601 (UTC)
        TEXT last_modified_at -- ISO 8601 (UTC)
        INTEGER version
    }

    RAILWAY_COMPANY {
        TEXT id PK          "URN: urn:railway:{name}"
        TEXT name
        TEXT registered_company_name
        TEXT country
        TEXT status
        TEXT website_url
        TEXT description
        TEXT created_at -- ISO 8601 (UTC)
        TEXT last_modified_at -- ISO 8601 (UTC)
        INTEGER version
    }

    MANUFACTURER {
        TEXT id PK          "URN: urn:manufacturer:{name}"
        TEXT name
        TEXT registered_company_name
        TEXT kind
        TEXT status
        TEXT email
        TEXT website_url
        TEXT phone_number
        TEXT street_address
        TEXT city
        TEXT state
        TEXT postal_code
        TEXT country
        TEXT created_at -- ISO 8601 (UTC)
        TEXT last_modified_at -- ISO 8601 (UTC)
        INTEGER version
    }

    RAILWAY_MODEL {
        TEXT id PK          "URN: urn:model:{manufacturer}-{product_code}"
        TEXT manufacturer_id FK
        TEXT product_code
        TEXT description
        TEXT detailed_description
        TEXT delivery_date
        TEXT delivery_state
        TEXT scale_id FK
        TEXT power_method
        TEXT category
        TEXT created_at -- ISO 8601 (UTC)
        TEXT last_modified_at -- ISO 8601 (UTC)
        INTEGER version
    }

    ROLLING_STOCK {
        TEXT id PK          "URN: urn:rollingstock:{uuid}"
        TEXT railway_model_id FK
        TEXT category
        TEXT railway_company_id FK
        REAL length_mm
        REAL length_in
        TEXT era
        TEXT type_name
        TEXT road_number
        TEXT description
        TEXT detailed_description
        BOOLEAN dummy
        TEXT locomotive_type
        TEXT depot_name
        TEXT livery
        TEXT series
        TEXT control
        TEXT dcc_socket_type
        TEXT coupler_socket_type
        BOOLEAN has_close_coupler
        BOOLEAN has_digital_controller_coupler
        REAL min_radius
        TEXT railcar_type
        TEXT emu_type
        TEXT passenger_car_type
        TEXT service_level
        TEXT freight_car_type
        TEXT body_shell_type
        TEXT chassis_type
    }

    COLLECTION {
        TEXT id PK          "URN: urn:collection:{uuid}"
        INTEGER collector_id FK
        TEXT name
        TEXT description
        TEXT created_at -- ISO 8601 (UTC)
        TEXT last_modified_at -- ISO 8601 (UTC)
        INTEGER version
    }

    COLLECTION_ITEM {
        TEXT id PK          "URN: urn:collection-item:{uuid}"
        TEXT collection_id FK
        TEXT railway_model_id FK
        REAL price
        TEXT currency
        TEXT seller_id FK
        TEXT added_at -- ISO 8601 date (YYYY-MM-DD)
        TEXT removed_at -- ISO 8601 date (YYYY-MM-DD)
    }

    OWNED_ROLLING_STOCK {
        TEXT id PK            "URN: urn:owned-rollingstock:{uuid}"
        TEXT collection_item_id FK
        TEXT rolling_stock_id FK
        TEXT serial_number
        TEXT custom_name
        TEXT dcc_address
        TEXT condition
        TEXT created_at -- ISO 8601 (UTC)
        TEXT last_modified_at -- ISO 8601 (UTC)
    }

    WISHLIST {
        TEXT id PK          "URN: urn:wishlist:{name}"
        TEXT name
        TEXT description
        TEXT created_at -- ISO 8601 (UTC)
        TEXT last_modified_at -- ISO 8601 (UTC)
        INTEGER version
    }

    WISHLIST_ITEM {
        TEXT id PK          "URN: urn:wishlist-item:{name}"
        TEXT wishlist_id FK
        TEXT railway_model_id FK
        REAL desired_price
        TEXT currency
        TEXT seller_id FK
        TEXT priority
        TEXT added_at -- ISO 8601 date (YYYY-MM-DD)
        TEXT removed_at -- ISO 8601 date (YYYY-MM-DD)
    }

    SELLER {
        TEXT id PK          "URN: urn:seller:{type}:{name}"
        TEXT name
        TEXT type
        TEXT email
        TEXT website_url
        TEXT phone_number
        TEXT street_address
        TEXT city
        TEXT state
        TEXT postal_code
        TEXT country
        TEXT created_at -- ISO 8601 (UTC)
        TEXT last_modified_at -- ISO 8601 (UTC)
        INTEGER version
    }

    FAVOURITE_SELLER {
        INTEGER id PK
        INTEGER collector_id FK
        TEXT seller_id FK
        TEXT created_at -- ISO 8601 (UTC)
    }

    FAVOURITE_SCALE {
        INTEGER id PK
        INTEGER collector_id FK
        TEXT scale_id FK
        TEXT created_at -- ISO 8601 (UTC)
    }

    FAVOURITE_RAILWAY_COMPANY {
        INTEGER id PK
        INTEGER collector_id FK
        TEXT railway_company_id FK
        TEXT created_at -- ISO 8601 (UTC)
    }

    FAVOURITE_ERA {
        INTEGER id PK
        INTEGER collector_id FK
        TEXT era
        TEXT created_at -- ISO 8601 (UTC)
    }

    CONSIST {
        TEXT id PK          "URN: urn:consist:{name}"
        TEXT name
        TEXT consist_type
        TEXT era
        INTEGER year_start
        INTEGER year_end
        TEXT route_origin
        TEXT route_destination
        TEXT description
        TEXT created_at -- ISO 8601 (UTC)
        TEXT last_modified_at -- ISO 8601 (UTC)
        INTEGER version
    }

    CONSIST_ROLLING_STOCK {
        INTEGER id PK
        TEXT consist_id FK
        INTEGER position
        TEXT category
        TEXT subcategory
        TEXT road_name
        TEXT road_number
        TEXT service_level
        TEXT notes
    }

    MAINTENANCE_CARD {
        INTEGER id PK        "URN: urn:maintenance-card:{uuid}"
        TEXT owned_rolling_stock_id FK
        TEXT maintenance_status
        TEXT last_maintenance_date
        TEXT next_maintenance_date
        BOOLEAN digital_decoder_installed
        TEXT decoder_manufacturer
        TEXT decoder_model
        TEXT decoder_address
        TEXT decoder_installation_date
        TEXT decoder_features
        TEXT created_at -- ISO 8601 (UTC)
        TEXT last_modified_at -- ISO 8601 (UTC)
    }

    MAINTENANCE_EVENT {
        INTEGER id PK
        INTEGER maintenance_card_id FK
        TEXT date_performed
        TEXT maintenance_type
        TEXT notes
        TEXT performed_by
        TEXT created_at
    }

    %% Relationships

    COLLECTOR ||--o{ COLLECTION : "owns"
    COLLECTOR ||--o{ WISHLIST : "creates"
    COLLECTION ||--o{ COLLECTION_ITEM : "contains"
    RAILWAY_MODEL ||--o{ ROLLING_STOCK : "includes"
    MANUFACTURER ||--o{ RAILWAY_MODEL : "produces"
    SCALE ||--o{ RAILWAY_MODEL : "scales"
    RAILWAY_COMPANY ||--o{ ROLLING_STOCK : "operates"
    RAILWAY_MODEL ||--o{ COLLECTION_ITEM : "collected"
    SELLER ||--o{ COLLECTION_ITEM : "sold_by"
    WISHLIST ||--o{ WISHLIST_ITEM : "contains"
    RAILWAY_MODEL ||--o{ WISHLIST_ITEM : "wished"
    SELLER ||--o{ WISHLIST_ITEM : "wished_from"
    FAVOURITE_SELLER ||--o{ SELLER : "liked"
    FAVOURITE_SCALE ||--o{ SCALE : "liked"
    FAVOURITE_RAILWAY_COMPANY ||--o{ RAILWAY_COMPANY : "liked"
    CONSIST ||--o{ CONSIST_ROLLING_STOCK : "has"
    MAINTENANCE_CARD ||--o{ MAINTENANCE_EVENT : "records"
```

## Ownership and Maintenance Flow

This flow reflects the SQL model above:

```mermaid
flowchart TD
    M[Define Railway Model (catalog entry)] --> RS[Define Rolling Stock templates]
    U[User purchases a RailwayModel] --> CI[Create CollectionItem (purchase record)]
    CI -->|records purchase of| RM(RailwayModel)
    CI -->|may reference seller| S(Seller)
    RS -->|templates for| RM
    RS -->|are referenced by| CI
    RM --> MC[Maintenance Card] 
    RS --> MC
    MC --> ME[Maintenance Events]
```

Notes
- The SQL schema allows Maintenance Cards to be associated with an owned physical unit via `owned_rolling_stock_id` (FK to `OWNED_ROLLING_STOCK.id`). For broader use cases a Maintenance Card may still reference a `RailwayModel` or `RollingStock` template using `model_id` (polymorphic target). Implementation may alternatively use explicit `target_id` + `target_type` columns; the diagram expresses the logical relationship.
- Identifiers (URNs) used in the schema:
  - Scale: `urn:scale:{name}`
  - Railway company: `urn:railway:{name}`
  - Manufacturer: `urn:manufacturer:{name}`
  - Railway model: `urn:model:{manufacturer}-{product_code}`
  - Rolling stock: `urn:rollingstock:{uuid}`
  - Collection Item: `urn:collection-item:{uuid}`
  - Owned rolling stock: `urn:owned-rollingstock:{uuid}`
  - Seller: `urn:seller:{type}:{name}`
  - Wishlist: `urn:wishlist:{name}`

- For DB design notes and indexes, see `docs/req.md` and `docs/roundhouse_requirements.md`.
