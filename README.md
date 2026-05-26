# Intelligent Bus Driver Guidance System - Starter Template

This repository is a starter template for Software Assignment 2. It contains the Maven structure, required class files, required test files, placeholder documentation files, TXT data files, and GitHub Actions workflow file.

The actual implementation and tests still need to be completed by the team. Each teammate should work on their own branch and make visible commits under their own GitHub account.

## Requirements

- Java 17
- Maven 3.9+

## Run Tests

```bash
mvn test
```

The starter tests are disabled placeholders. As each teammate implements their section, they should replace the placeholder tests with real JUnit 5 tests.

## Team Responsibilities

| Team member | Main responsibility | Files/areas to work on |
| --- | --- | --- |
| Nathaniel | Project setup and driver validation | Complete `Driver.java`, `DriverValidator.java`, D1-D3 unit tests in `DriverUnitTest.java`, test case table rows for D1-D3, and 2 user stories |
| Patrick Nguyen | Driver repository and driver update rules | Complete `DriverRepository.java`, TXT persistence for drivers, `add`, `retrieve`, `update`, `count`, D4-D5 unit tests, driver integration tests, related test case table rows, and 2 user stories |
| Ibrahim Ibqal | Bus model, bus repository, and bus validation | Complete `Bus.java`, `BusValidator.java`, `BusRepository.java`, TXT persistence for buses, B1-B2 unit tests, bus integration tests, related test case table rows, and 2 user stories |
| Batu | Driver-bus eligibility, CI, and video coordination | Complete `BusAssignmentValidator.java`, B3-B5 eligibility tests, GitHub Actions verification, final test run, video coordination notes, related test case table rows, and 2 user stories |

## Team Workflow

Each teammate should use their own branch:

```bash
git checkout -b nathaniel-driver-validation
git checkout -b patrick-driver-repository
git checkout -b ibrahim-bus-repository
git checkout -b batu-bus-eligibility-ci
```

After making changes:

```bash
mvn test
git add .
git commit -m "Describe your section"
git push -u origin your-branch-name
```

Then open a pull request into `main`.

## Assignment Deliverables To Complete

- Java Maven project with JUnit 5
- `Driver` and `Bus` model classes
- `DriverRepository` and `BusRepository`
- TXT file storage in `data/drivers.txt` and `data/buses.txt`
- Driver validation rules D1-D5
- Bus and driver-bus eligibility rules B1-B5
- At least 15 driver unit tests
- At least 15 bus unit tests
- At least 4 driver integration tests
- At least 4 bus integration tests
- Test case table in `docs/TestCaseTable.md`
- 8 user stories and 24 acceptance criteria in `docs/UserStories.md`
- GitHub Actions workflow in `.github/workflows/maven.yml`
- Passing local tests and passing GitHub Actions tests
- Demo video showing local tests, GitHub Actions, and commit history

## Project Structure

```text
src/main/java/au/edu/scc/busguidance/
  Driver.java
  Bus.java
  DriverValidator.java
  BusValidator.java
  BusAssignmentValidator.java
  DriverRepository.java
  BusRepository.java

src/test/java/au/edu/scc/busguidance/
  DriverUnitTest.java
  BusUnitTest.java
  DriverIntegrationTest.java
  BusIntegrationTest.java

data/
  drivers.txt
  buses.txt

docs/
  TestCaseTable.md
  UserStories.md
```

## TXT Formats

Drivers use:

```text
driverID;name;experienceYears;licenseType;address;birthdate
```

Buses use:

```text
busID;capacity;fuelLevel;fuelType
```

Addresses use the assignment format:

```text
Street Number|Street Name|Suburb|State|Country
```
