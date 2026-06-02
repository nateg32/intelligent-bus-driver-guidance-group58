# User Stories and Acceptance Criteria

Complete 8 user stories with 3 acceptance criteria each.

## Nathaniel

### US01 - Validate Driver Identity

As a depot administrator, I want the system to validate driver IDs before saving driver records so that each driver can be uniquely and reliably identified.

1. Given a driver ID is exactly 10 characters and follows the required character pattern, when it is validated, then the system accepts the driver ID.
2. Given a driver ID has an invalid length, invalid starting digits, missing special characters, or lowercase final letters, when it is validated, then the system rejects the driver ID.
3. Given a driver ID already exists in the current driver records, when uniqueness is checked, then the system rejects the duplicate ID.

### US02 - Validate Driver Contact And Birth Details

As a depot administrator, I want the system to validate driver address and birthdate details so that driver records use a consistent format.

1. Given an address follows `Street Number|Street Name|Suburb|State|Country`, when it is validated, then the system accepts the address.
2. Given an address is missing fields, uses the wrong separator, has blank fields, or has a non-numeric street number, when it is validated, then the system rejects the address.
3. Given a birthdate follows `DD-MM-YYYY` and is a real calendar date, when it is validated, then the system accepts the birthdate.

## Patrick Nguyen

### US03 - TODO

As a TODO, I want TODO so that TODO.

1. Given TODO, when TODO, then TODO.
2. Given TODO, when TODO, then TODO.
3. Given TODO, when TODO, then TODO.

### US04 - TODO

As a TODO, I want TODO so that TODO.

1. Given TODO, when TODO, then TODO.
2. Given TODO, when TODO, then TODO.
3. Given TODO, when TODO, then TODO.

## Ibrahim Ibqal

### US05 - Validate Bus Identity

As a depot administrator, I want the system to validate bus IDs before saving bus records so that each bus can be uniquely and reliably identified.

1. Given a bus ID is exactly 8 characters and all characters are digits, when it is validated, then the system accepts the bus ID.
2. Given a bus ID is missing, too short, too long, contains letters, contains spaces, or contains special characters, when it is validated, then the system rejects the bus ID.
3. Given a bus ID already exists in the current bus records, when uniqueness is checked, then the system rejects the duplicate ID.

### US06 - Manage Bus Records

As a depot administrator, I want the system to store, retrieve, update, and count bus records in a human-readable TXT file so that bus data can be maintained without using a database system.

1. Given a valid bus record is added, when the repository retrieves the bus by ID, then the stored bus details are returned from the TXT file.
2. Given an invalid bus record or duplicate bus ID is added, when the repository validates the record, then the bus is rejected and the stored record count does not increase.
3. Given an existing bus is updated with the same or lower capacity, when the repository saves the update, then the changed bus details are persisted; given the update increases capacity, then the update is rejected.

## Batu

### US07 - TODO

As a TODO, I want TODO so that TODO.

1. Given TODO, when TODO, then TODO.
2. Given TODO, when TODO, then TODO.
3. Given TODO, when TODO, then TODO.

### US08 - TODO

As a TODO, I want TODO so that TODO.

1. Given TODO, when TODO, then TODO.
2. Given TODO, when TODO, then TODO.
3. Given TODO, when TODO, then TODO.
