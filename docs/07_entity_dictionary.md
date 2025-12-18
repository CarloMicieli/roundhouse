# 07 — Entity Dictionary & Identifiers

This file lists primary entities and their canonical URN identifiers.

| Entity | URN Format |
|--------|------------|
| Collector | `urn:collector:{name}` |
| Scale | `urn:scale:{name}` |
| Railway Company | `urn:railway:{name}` |
| Manufacturer | `urn:manufacturer:{name}` |
| Railway Model | `urn:model:{manufacturer}-{product_code}` |
| Rolling Stock | `urn:rollingstock:{uuid}` |
| Seller | `urn:seller:{type}:{name}` |
| Wish List | `urn:wishlist:{name}` |
| Consist | `urn:consist:{name}` |
| Collection | `urn:collection:{uuid}` |
| Collection Item | `urn:collection-item:{uuid}` |
| Maintenance Card | `urn:maintenance-card:{uuid}` |
| Owned Rolling Stock | `urn:owned-rollingstock:{uuid}` |

## Data Entities

The documentation below describes the domain entities used across Roundhouse in plain, readable paragraphs rather than long property lists. Where applicable, the canonical identifier format is mentioned so implementers know how instances are referenced.

**Manufacturer**

A Manufacturer represents the company or brand that produces model railway products and related accessories. In Roundhouse this is a catalog-level entity used to group products and to power searches and filters (for example, collectors often browse by brand). Manufacturers include parent/child relationships (sub-brands) and may represent historical or active companies; they are referenced by models rather than embedding full company details in each product.

**Railway company**

A Railway Company is the real-world operator or owner that a model represents (for example: DB, SNCF). This entity is used to tag rolling stock and to drive era- and livery-specific searches. Railway companies (identified by URNs like `urn:railway:{name}`) are lightweight reference objects (name, country, status) and are reused across models and rolling-stock entries.

**Scale**

Scale captures the modeling scale (H0, N, Z, etc.) and its physical characteristics (ratio, track gauge). Scales are used to validate and filter models and rolling stock and serve as a stable reference set seeded with the application.

**RailwayModel**

A RailwayModel is the product-level entity — the catalog entry a manufacturer publishes. A model can represent a single vehicle or a packaged set (starter set, multi-coach set). In Roundhouse a RailwayModel ties together manufacturer, product code, human-readable title and descriptive text, scale and category, and a stable versioning field so seeded catalog data can be migrated safely. Models act as containers for one-or-more Rolling Stock definitions rather than describing each physical unit owned by a collector.

**Rolling Stock**

Rolling Stock entries describe the individual vehicles that appear inside a RailwayModel (for example: a locomotive, a specific coach, or a freight wagon). These records carry the domain attributes used by depot and consist views (category, railway company, era, livery, road number and relevant technical notes). Rolling Stock items have an implementation identifier (for instance a URN derived from model + road number or a UUID) and may include category-specific details (locomotive vs passenger car vs freight car), but the presentation here focuses on intent: a template for vehicles that can be owned, placed in consists, and maintained.

**Seller**

A Seller represents the source of purchases — shops or private sellers/collectors. Sellers are referenced from purchase records so users can track where an item was bought and mark favourite vendors for quick selection when adding new collection items.

**Favourites (shops, scales, railway companies)**

Favourites link a collector to preferred shops, modeling scales or railway companies; they are small lookup records that improve UX by surfacing commonly used choices when creating or editing items.

**Collector**

The Collector entity represents the owner/profile — the person using the app. It stores preferences (preferred currency, measurement system, favourite scales/companies/eras) and a pointer to their personal collection(s) and wish lists.

**Collection & Collection Items**

A Collection is a logical grouping owned by a Collector; Collection Items are the purchase records inside it. Each Collection Item records the fact that the collector acquired a particular RailwayModel (or set) at a given time and price, and it links to the Seller. Identifiers follow the URN convention (for example `urn:collection-item:{uuid}`).

Physical units derived from that purchase (the individual coaches/wagons inside a set) are represented via the rolling-stock templates and, when needed, by owned-unit records in a separate ownership layer so maintenance and per-unit customisation can be tracked.

**Owned Rolling Stock**

Owned Rolling Stock represents the actual physical units a collector owns. When a user purchases a model (especially multi-car sets or multiple quantities), the system may create one or more Owned Rolling Stock records that reference the Rolling Stock template and the originating Collection Item. These records allow per-unit tracking of maintenance history, decoder installation and address, serial numbers or custom names, condition, and unit-specific notes — details that vary from one physical example of a product to another. A suggested identifier format is `urn:owned-rollingstock:{uuid}`; Owned Rolling Stock entries are the primary attachment point for Maintenance Cards and per-unit operational data, and they are what users typically place into Consists when they want to model "my exact train" rather than a generic formation.

**Wish List & Wish List Items**

Wish Lists are named lists of desired RailwayModels. Items in a wish list capture which model is wanted, optional target price and preferred seller information, and they can be promoted to a Collection Item when the purchase happens.

**Consist**

A Consist models a train formation: an ordered sequence of rolling stock entries that represents a real-world train composition during a chosen era or route. Consists are built from rolling-stock templates or from owned units, allowing collectors to represent "my exact train" or a generic historical formation.

**Maintenance Card**

A Maintenance Card centralises the upkeep and digital-configuration information for an owned unit: maintenance status and history, and digital decoder setup (where applicable). Maintenance Cards use URNs such as `urn:maintenance-card:{uuid}`. Maintenance Cards are intended to attach to owned, physical units so each piece of equipment can carry its own maintenance timeline and technical notes.

## Enumerations

### Seller Type
* _Name_: Seller Type
* _Description_: The type of seller entity from which a model or set can be purchased.
* _Values_:
    * `COLLECTOR` – Private individual or hobbyist
    * `SHOP` – Retailer or commercial seller

### Track Gauge
* _Name_: Track Gauge
* _Description_: The type of track gauge for a scale.
* _Values_:
    * `BROAD` – Broad gauge track
    * `NARROW` – Narrow gauge track
    * `STANDARD` – Standard gauge track

### Railway Company Status
* _Name_: Railway Company Status
* _Description_: The operational status of a railway company.
* _Values_:
    * `ACTIVE` – Currently operating
    * `INACTIVE` – No longer operating

### Manufacturer Kind
* _Name_: Manufacturer Kind
* _Description_: The kind of manufacturer.
* _Values_:
    * `BRASS_METAL_MODELS` – Specialist, often hand-built models
    * `INDUSTRIAL` – Mass-market manufacturer

### Manufacturer Status
* _Name_: Manufacturer Status
* _Description_: The operational status of a manufacturer.
* _Values_:
    * `ACTIVE` – Company is in business
    * `MERGED` – Company has merged with another
    * `OUT_OF_BUSINESS` – Company has ceased operations

### Delivery State
* _Name_: Delivery State
* _Description_: The delivery state of a railway model.
* _Values_:
    * `ANNOUNCED` – Model announced, not yet available
    * `AVAILABLE` – Model is available for purchase
    * `CANCELLED` – Model was cancelled
    * `UNKNOWN` – Status not specified

### Power Method
* _Name_: Power Method
* _Description_: The power method used by a railway model.
* _Values_:
    * `AC` – Alternating current power
    * `DC` – Direct current power
    * `TRIX_EXPRESS` – Trix Express system

### Model Category
* _Name_: Model Category
* _Description_: The category of a railway model.
* _Values_:
    * `ELECTRIC_MULTIPLE_UNIT` – Self-propelled electric trainset
    * `FREIGHT_CAR` – Car for goods or cargo
    * `LOCOMOTIVE` – Self-propelled engine
    * `PASSENGER_CAR` – Car for passengers
    * `RAILCAR` – Self-propelled single car
    * `STARTER_SET` – Beginner's set with track and controller
    * `TRAIN_SET` – Boxed set of multiple cars/locos

### Rolling Stock Category
* _Name_: Rolling Stock Category
* _Description_: The category of a rolling stock item.
* _Values_:
    * `ELECTRIC_MULTIPLE_UNIT` – Self-propelled electric trainset
    * `FREIGHT_CAR` – Car for goods or cargo
    * `LOCOMOTIVE` – Self-propelled engine
    * `PASSENGER_CAR` – Car for passengers
    * `RAILCAR` – Self-propelled single car

### Locomotive Type
* _Name_: Locomotive Type
* _Description_: The type of locomotive.
* _Values_:
    * `DIESEL_LOCOMOTIVE` – Powered by diesel engine
    * `ELECTRIC_LOCOMOTIVE` – Powered by electric motor
    * `SHUNTING_LOCOMOTIVE` – For yard/switching duties
    * `STEAM_LOCOMOTIVE` – Powered by steam engine

### Railcar Type
* _Name_: Railcar Type
* _Description_: The type of railcar.
* _Values_:
    * `POWER_CAR` – Motorized car in a trainset
    * `TRAILER_CAR` – Non-powered car in a trainset

### EMU Type
* _Name_: EMU Type
* _Description_: The type of electric multiple unit.
* _Values_:
    * `DRIVING_CAR` – Car with driver's cab, controls train but may not be powered
    * `HIGH_SPEED_TRAIN` – EMU designed for high-speed service
    * `MOTOR_CAR` – Powered car with traction motors
    * `POWER_CAR` – Main powered car in the EMU
    * `TRAILER_CAR` – Non-powered car, no traction motors
    * `TRAIN_SET` – Complete EMU set, may include multiple car types

### Control
* _Name_: Control
* _Description_: The digital control capability of a model.
* _Values_:
    * `NO_DCC` – No digital decoder, analog only
    * `DCC_READY` – Prepared for DCC, socket for decoder
    * `DCC_FITTED` – Digital decoder installed
    * `DCC_SOUND` – Digital decoder with sound functions

### Socket Type
* _Name_: Socket Type
* _Description_: The type of NEM digital decoder socket for DCC or digital control, as per NEM standards.
* _Values_:
    * `NEM_651` – 6-pin, small scale
    * `NEM_652` – 8-pin, standard
    * `NEM_654` – 21-pin, PluX
    * `NEM_658` – 22-pin, PluX22
    * `NEM_660` – 21MTC
    * `NEXT18` – Next18 socket
    * `WIRE` – Hardwired
    * `NONE` – No socket

### Passenger Car Type
* _Name_: Passenger Car Type
* _Description_: The type of passenger car.
* _Values_:
    * `BAGGAGE_CAR` – Car for luggage and parcels
    * `COMBINE_CAR` – Car combining passenger and baggage sections
    * `COMPARTMENT_COACH` – Coach with individual compartments
    * `DINING_CAR` – Car with restaurant or dining facilities
    * `DOUBLE_DECKER` – Two-level passenger car
    * `DRIVING_TRAILER` – Passenger car with driver's cab (no engine)
    * `LOUNGE` – Car with lounge or observation seating
    * `OBSERVATION_CAR` – Car with panoramic windows, often at train end
    * `OPEN_COACH` – Coach with open seating (no compartments)
    * `RAILWAY_POST_OFFICE` – Car for mail sorting and transport
    * `SLEEPING_CAR` – Car with beds or sleeping compartments

### Service Level
* _Name_: Service Level
* _Description_: The service level of a passenger car.
* _Values_:
    * `FIRST_CLASS` – Premium passenger accommodation
    * `MIXED_FIRST_SECOND_CLASS` – Both first and second class
    * `MIXED_FIRST_SECOND_CLASS_THIRD_CLASS` – Mixed first, second and third class
    * `SECOND_CLASS` – Standard passenger accommodation
    * `MIXED_SECOND_THIRD_CLASS` – Both second and third class
    * `THIRD_CLASS` – Basic passenger accommodation

### Freight Car Type
* _Name_: Freight Car Type
* _Description_: The type of freight car.
* _Values_:
    * `AUTO_TRANSPORT_CAR` – For transporting automobiles
    * `BRAKE_WAGON` – Equipped with handbrake, often for train end
    * `CONTAINER_CAR` – Carries shipping containers
    * `COVERED_FREIGHT_CAR` – Enclosed car for general goods
    * `DEEP_WELL_FLAT_CAR` – Low-floor car for tall/large loads
    * `DUMP_CAR` – For bulk materials, can tip to unload
    * `GONDOLA` – Open-topped car for bulk goods
    * `HEAVY_GOODS_WAGON` – For very heavy or oversized cargo
    * `HINGED_COVER_WAGON` – Covered car with hinged roof for loading
    * `HOPPER_WAGON` – For bulk goods, unloads from bottom
    * `REFRIGERATOR_CAR` – Insulated, for perishable goods
    * `SILO_CONTAINER_CAR` – For powders or granules, with silo containers
    * `SLIDE_TARPAULIN_WAGON` – Covered with sliding tarpaulin for easy access
    * `SLIDING_WALL_BOXCAR` – Boxcar with sliding walls for loading
    * `SPECIAL_TRANSPORT_CAR` – For special or unusual loads
    * `STAKE_WAGON` – Flat car with stakes for logs or pipes
    * `SWING_ROOF_WAGON` – Roof swings open for loading/unloading
    * `TANK_CAR` – For liquids or gases
    * `TELESCOPE_HOOD_WAGON` – Covered car with telescoping hood for coils or sheet metal

### Body Shell
* _Name_: Body Shell
* _Description_: The material type for body shell.
* _Values_:
    * `METAL_DIE_CAST` – Made from metal die casting
    * `PLASTIC` – Made from plastic

### Chassis Type
* _Name_: Chassis Type
* _Description_: The material type for chassis.
* _Values_:
    * `METAL_DIE_CAST` – Made from metal die casting
    * `PLASTIC` – Made from plastic

### Priority
* _Name_: Priority
* _Description_: The priority of a wish list item.
* _Values_:
    * `HIGH` – Highest priority
    * `NORMAL` – Normal priority
    * `LOW` – Lowest priority

### Epoch
* _Name_: Epoch
* _Description_: The historical railway era or epoch classification for rolling stock and models.
* _Values_:
    * `I` – Early railways (approx. 1835–1920)
    * `II` – Grouping and nationalization (approx. 1920–1945)
    * `IIa` – Early part of Epoch II
    * `IIb` – Later part of Epoch II
    * `III` – Postwar, steam/diesel transition (approx. 1945–1970)
    * `IIIa` – Early part of Epoch III
    * `IIIb` – Later part of Epoch III
    * `IV` – Modernization, UIC numbering (approx. 1970–1990)
    * `IVa` – Early part of Epoch IV
    * `IVb` – Later part of Epoch IV
    * `V` – Privatization, new liveries (approx. 1990–2006)
    * `Vm` – Modern sub-epoch of V
    * `VI` – Contemporary era (approx. 2007–present)

### Coupler Socket Type
* _Name_: Coupler Socket Type
* _Description_: The type of coupler socket, following NEM standards for model railway couplers.
* _Values_:
    * `NEM_355` – Coupler pocket for Z scale (1:220)
    * `NEM_356` – Coupler pocket for N scale (1:160)
    * `NEM_357` – Coupler pocket for TT scale (1:120)
    * `NEM_358` – Coupler pocket for H0e/H0m narrow gauge
    * `NEM_359` – Coupler pocket for H0 scale (1:87), standard
    * `NEM_360` – Coupler pocket for O scale (1:45)
    * `NEM_361` – Coupler pocket for 1 scale (1:32)
    * `NEM_362` – Universal close coupler pocket (widely used in H0)
    * `NEM_363` – Coupler pocket for G scale (1:22.5)
    * `NEM_365` – Coupler pocket for narrow gauge and special applications

### Consist Type
* _Name_: Consist Type
* _Description_: The type of train formation represented by a consist.
* _Values_:
    * `FREIGHT_TRAIN` – Freight train consist
    * `HIGH_SPEED_TRAIN` – High speed train consist
    * `PASSENGER_TRAIN` – Passenger train consist

### Maintenance Status
* _Name_: Maintenance Status
* _Description_: The current maintenance state of a model or rolling stock item.
* _Values_:
    * `UP_TO_DATE` – Maintenance is up to date
    * `DUE` – Maintenance is due
    * `OVERDUE` – Maintenance is overdue
    * `UNKNOWN` – Maintenance status is unknown

### Maintenance Type
* _Name_: Maintenance Type
* _Description_: The type of maintenance activity performed on a model or rolling stock item.
* _Values_:
    * `CLEANING` – Cleaning of the model or rolling stock
    * `INSPECTION` – Routine inspection
    * `LUBRICATION` – Lubrication of moving parts
    * `OTHER` – Other maintenance activity
    * `REPAIR` – Repair of faults or damage
    * `UPGRADE` – Upgrade or replacement of components

