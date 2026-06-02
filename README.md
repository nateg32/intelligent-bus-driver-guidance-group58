# Intelligent Bus Driver Guidance System

Group 58 Java Maven project for Assignment 4.

This repository contains the source code, tests, TXT data files, and GitHub Actions workflow for the Intelligent Bus Driver Guidance System. Written documentation such as the test case table, user stories, acceptance criteria, and statement of contributions is maintained in the shared Google Doc, not in this repository.

## Requirements

- Java 17
- Maven 3.9+

## Run Tests

```bash
mvn test
```

All final code should pass locally and in GitHub Actions before submission.

## Team Workflow

All team commits are made directly to `main`.

Before committing:

```bash
git pull origin main
mvn test
git status
```

Then commit and push:

```bash
git add .
git commit -m "Describe the completed task"
git push origin main
```

## Team Responsibilities

| Team member | Main responsibility | Files/areas |
| --- | --- | --- |
| Nathaniel | Driver model and D1-D3 driver validation | `Driver.java`, `DriverValidator.java`, D1-D3 tests in `DriverUnitTest.java` |
| Patrick Nguyen | Driver repository and D4-D5 update rules | `DriverRepository.java`, D4-D5 tests, `DriverIntegrationTest.java` |
| Ibrahim Ibqal | Bus model, bus repository, and B1-B2 validation | `Bus.java`, `BusValidator.java`, `BusRepository.java`, `BusIntegrationTest.java` |
| Batu | Driver-bus eligibility, GitHub Actions, and video evidence | `BusAssignmentValidator.java`, B3-B5 tests, `.github/workflows/maven.yml`, final demo video |

## Assignment Requirements

- Maven-based Java project
- JUnit 5 tests
- Human-readable TXT or JSON storage
- `Driver` and `Bus` model classes
- `DriverRepository` and `BusRepository`
- Driver rules D1-D5
- Bus and driver-bus eligibility rules B1-B5
- At least 15 driver unit tests
- At least 15 bus unit tests
- At least 4 driver integration tests
- At least 4 bus integration tests
- GitHub Actions workflow that runs tests on push or pull request
- Final video showing local tests, GitHub Actions tests, and commit history
- Google Doc containing the test case table, user stories, acceptance criteria, and contribution documentation

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

.github/workflows/
  maven.yml
```

## Data Format

Drivers are stored in `data/drivers.txt`.

```text
driverID;name;experienceYears;licenseType;address;birthdate
```

Buses are stored in `data/buses.txt`.

```text
busID;capacity;fuelLevel;fuelType
```

Addresses follow the assignment format:

```text
Street Number|Street Name|City|State|Country
```
