# Test Case Table

Complete this table as each teammate writes their tests.

## Nathaniel - Driver Unit Tests D1-D3

| ID | Type | Rule | Test/action | Expected result | Status |
| --- | --- | --- | --- | --- | --- |
| DU01 | Unit | D1 | Validate `23@@abCDXY` | Driver ID is accepted | Complete |
| DU02 | Unit | D1 | Validate null driver ID | Driver ID is rejected | Complete |
| DU03 | Unit | D1 | Validate driver ID shorter than 10 characters | Driver ID is rejected | Complete |
| DU04 | Unit | D1 | Validate driver ID longer than 10 characters | Driver ID is rejected | Complete |
| DU05 | Unit | D1 | Validate driver ID where first digit is below 2 | Driver ID is rejected | Complete |
| DU06 | Unit | D1 | Validate driver ID where one of first two digits is 0 | Driver ID is rejected | Complete |
| DU07 | Unit | D1 | Validate driver ID without two special characters in characters 3-8 | Driver ID is rejected | Complete |
| DU08 | Unit | D1 | Validate driver ID using whitespace as a special character | Driver ID is rejected | Complete |
| DU09 | Unit | D1 | Validate driver ID without uppercase final letters | Driver ID is rejected | Complete |
| DU10 | Unit | D1 | Check a new driver ID against existing driver records | Driver ID is unique | Complete |
| DU11 | Unit | D1 | Check a duplicate driver ID against existing driver records | Driver ID is not unique | Complete |
| DU12 | Unit | D1 | Check null driver ID against existing driver records | Driver ID is not unique | Complete |
| DU13 | Unit | D1 | Check driver ID against null existing driver list | Driver ID is not unique | Complete |
| DU14 | Unit | D2 | Validate `12|King Street|Melbourne|VIC|Australia` | Address is accepted | Complete |
| DU15 | Unit | D2 | Validate null address | Address is rejected | Complete |
| DU16 | Unit | D2 | Validate address missing one field | Address is rejected | Complete |
| DU17 | Unit | D2 | Validate address using commas instead of `|` separators | Address is rejected | Complete |
| DU18 | Unit | D2 | Validate address with a blank field | Address is rejected | Complete |
| DU19 | Unit | D2 | Validate address with non-numeric street number | Address is rejected | Complete |
| DU20 | Unit | D2 | Validate address containing semicolon | Address is rejected | Complete |
| DU21 | Unit | D3 | Validate `15-04-1999` | Birthdate is accepted | Complete |
| DU22 | Unit | D3 | Validate null birthdate | Birthdate is rejected | Complete |
| DU23 | Unit | D3 | Validate birthdate using `YYYY-MM-DD` format | Birthdate is rejected | Complete |
| DU24 | Unit | D3 | Validate impossible date `31-02-1999` | Birthdate is rejected | Complete |
| DU25 | Unit | D3 | Validate invalid month `15-13-1999` | Birthdate is rejected | Complete |
| DU26 | Unit | D3 | Validate leap year date `29-02-2000` | Birthdate is accepted | Complete |
| DU27 | Unit | D3 | Validate non-leap year date `29-02-2001` | Birthdate is rejected | Complete |
| DU28 | Unit | D3 | Validate single-digit day `5-04-1999` | Birthdate is rejected | Complete |

## Patrick Nguyen - Driver Rules D4-D5 And Integration Tests

| ID | Type | Rule | Test/action | Expected result | Status |
| --- | --- | --- | --- | --- | --- |
| DU08 | Unit | D4 | TODO: licence cannot change when experience is more than 10 years | TODO | Not started |
| DU09 | Unit | D4 | TODO: licence can change at allowed experience boundary | TODO | Not started |
| DU10 | Unit | D5 | TODO: driver ID cannot change during update | TODO | Not started |
| DU11 | Unit | D5 | TODO: name cannot change during update | TODO | Not started |
| DI01 | Integration | Repository | TODO: add and retrieve valid driver from TXT file | TODO | Not started |
| DI02 | Integration | Repository | TODO: invalid driver is rejected | TODO | Not started |
| DI03 | Integration | Repository | TODO: update persists to TXT file | TODO | Not started |
| DI04 | Integration | Repository | TODO: count returns correct driver total | TODO | Not started |

## Ibrahim Ibqal - Bus Unit And Integration Tests B1-B2

| ID | Type | Rule | Test/action | Expected result | Status |
| --- | --- | --- | --- | --- | --- |
| BU01 | Unit | B1 | Validate `12345678` | Bus ID is accepted | Complete |
| BU02 | Unit | B1 | Validate null bus ID | Bus ID is rejected | Complete |
| BU03 | Unit | B1 | Validate bus ID shorter than 8 characters | Bus ID is rejected | Complete |
| BU04 | Unit | B1 | Validate bus ID longer than 8 characters | Bus ID is rejected | Complete |
| BU05 | Unit | B1 | Validate bus ID containing letters | Bus ID is rejected | Complete |
| BU06 | Unit | B1 | Validate bus ID containing special characters | Bus ID is rejected | Complete |
| BU07 | Unit | B1 | Validate bus ID containing spaces | Bus ID is rejected | Complete |
| BU08 | Unit | B1 | Check a new bus ID against existing bus records | Bus ID is unique | Complete |
| BU09 | Unit | B1 | Check a duplicate bus ID against existing bus records | Bus ID is not unique | Complete |
| BU10 | Unit | B1 | Check null bus ID against existing bus records | Bus ID is not unique | Complete |
| BU11 | Unit | B1 | Check bus ID against null existing bus list | Bus ID is not unique | Complete |
| BU12 | Unit | B2 | Decrease capacity from 60 to 45 | Capacity update is accepted | Complete |
| BU13 | Unit | B2 | Keep capacity unchanged at 45 | Capacity update is accepted | Complete |
| BU14 | Unit | B2 | Increase capacity from 45 to 60 | Capacity update is rejected | Complete |
| BU15 | Unit | B2 | Update capacity to 0 | Capacity update is rejected | Complete |
| BU16 | Unit | B2 | Update capacity to a negative number | Capacity update is rejected | Complete |
| BU17 | Unit | Bus record | Validate complete bus record | Bus record is accepted | Complete |
| BU18 | Unit | Bus record | Validate bus record with invalid ID | Bus record is rejected | Complete |
| BU19 | Unit | Bus record | Validate bus record with invalid capacity | Bus record is rejected | Complete |
| BU20 | Unit | Bus record | Validate bus record with invalid fuel level | Bus record is rejected | Complete |
| BU21 | Unit | Bus record | Validate bus record with invalid fuel type | Bus record is rejected | Complete |
| BI01 | Integration | Repository | Add and retrieve valid bus from TXT file | Bus is stored and retrieved correctly | Complete |
| BI02 | Integration | Repository | Add invalid bus with invalid bus ID | Bus is rejected and count remains 0 | Complete |
| BI03 | Integration | Repository | Update bus with decreased capacity, fuel level, and fuel type | Updated values are persisted to TXT file | Complete |
| BI04 | Integration | Repository | Add two valid buses | Count returns 2 | Complete |
| BI05 | Integration | Repository | Add duplicate bus ID | Duplicate bus is rejected and count remains 1 | Complete |
| BI06 | Integration | Repository | Attempt to increase bus capacity during update | Update is rejected and original capacity remains stored | Complete |

## Batu - Driver-Bus Eligibility Tests B3-B5

| ID | Type | Rule | Test/action | Expected result | Status |
| --- | --- | --- | --- | --- | --- |
| BU06 | Unit | B3 | TODO: driver older than 50 cannot drive bus capacity 50 or more | TODO | Not started |
| BU07 | Unit | B3 | TODO: age/capacity boundary case | TODO | Not started |
| BU08 | Unit | B3 | TODO: older driver can drive smaller bus | TODO | Not started |
| BU09 | Unit | B4 | TODO: electric bus requires at least 5 years experience | TODO | Not started |
| BU10 | Unit | B4 | TODO: diesel bus does not use electric experience rule | TODO | Not started |
| BU11 | Unit | B5 | TODO: Heavy licence can operate electric/hybrid | TODO | Not started |
| BU12 | Unit | B5 | TODO: PublicTransport licence can operate electric/hybrid | TODO | Not started |
| BU13 | Unit | B5 | TODO: Light/Medium licence rejected for electric/hybrid | TODO | Not started |
