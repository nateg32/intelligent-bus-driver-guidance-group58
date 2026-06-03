package au.edu.scc.busguidance;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BusUnitTest {
    @Nested
    class BusIdFormatTests {
        @Test
        void validEightDigitBusIdIsAccepted() {
            assertTrue(BusValidator.isValidBusID("12345678"));
        }

        @Test
        void nullBusIdIsRejected() {
            assertFalse(BusValidator.isValidBusID(null));
        }

        @Test
        void busIdShorterThanEightCharactersIsRejected() {
            assertFalse(BusValidator.isValidBusID("1234567"));
        }

        @Test
        void busIdLongerThanEightCharactersIsRejected() {
            assertFalse(BusValidator.isValidBusID("123456789"));
        }

        @Test
        void busIdContainingLettersIsRejected() {
            assertFalse(BusValidator.isValidBusID("1234AB78"));
        }

        @Test
        void busIdContainingSpecialCharactersIsRejected() {
            assertFalse(BusValidator.isValidBusID("1234-678"));
        }

        @Test
        void busIdContainingSpacesIsRejected() {
            assertFalse(BusValidator.isValidBusID("1234 678"));
        }
    }

    @Nested
    class UniqueBusIdTests {
        @Test
        void uniqueBusIdIsAccepted() {
            List<Bus> existingBuses = List.of(bus("12345678", 45, 80.0, "Diesel"));

            assertTrue(BusValidator.isUniqueBusID("87654321", existingBuses));
        }

        @Test
        void duplicateBusIdIsRejected() {
            List<Bus> existingBuses = List.of(bus("12345678", 45, 80.0, "Diesel"));

            assertFalse(BusValidator.isUniqueBusID("12345678", existingBuses));
        }

        @Test
        void nullBusIdIsNotUnique() {
            List<Bus> existingBuses = List.of(bus("12345678", 45, 80.0, "Diesel"));

            assertFalse(BusValidator.isUniqueBusID(null, existingBuses));
        }

        @Test
        void nullExistingBusListIsNotUnique() {
            assertFalse(BusValidator.isUniqueBusID("12345678", null));
        }
    }

    @Nested
    class CapacityUpdateTests {
        @Test
        void capacityDecreaseIsAccepted() {
            assertTrue(BusValidator.canUpdateCapacity(60, 45));
        }

        @Test
        void unchangedCapacityIsAccepted() {
            assertTrue(BusValidator.canUpdateCapacity(45, 45));
        }

        @Test
        void capacityIncreaseIsRejected() {
            assertFalse(BusValidator.canUpdateCapacity(45, 60));
        }

        @Test
        void zeroUpdatedCapacityIsRejected() {
            assertFalse(BusValidator.canUpdateCapacity(45, 0));
        }

        @Test
        void negativeUpdatedCapacityIsRejected() {
            assertFalse(BusValidator.canUpdateCapacity(45, -1));
        }
    }

    @Nested
    class BusRecordValidationTests {
        @Test
        void validBusRecordIsAccepted() {
            assertTrue(BusValidator.isValidBus(bus("12345678", 45, 80.0, "Diesel")));
        }

        @Test
        void busRecordWithInvalidIdIsRejected() {
            assertFalse(BusValidator.isValidBus(bus("BUS12345", 45, 80.0, "Diesel")));
        }

        @Test
        void busRecordWithInvalidCapacityIsRejected() {
            assertFalse(BusValidator.isValidBus(bus("12345678", 0, 80.0, "Diesel")));
        }

        @Test
        void busRecordWithInvalidFuelLevelIsRejected() {
            assertFalse(BusValidator.isValidBus(bus("12345678", 45, 120.0, "Diesel")));
        }

        @Test
        void busRecordWithInvalidFuelTypeIsRejected() {
            assertFalse(BusValidator.isValidBus(bus("12345678", 45, 80.0, "Petrol")));
        }
    }

        @Nested
    class DriverAgeRestrictionTests {
        @Test
        void driverOlderThanFiftyCannotDriveBusWithCapacityFifty() {
            Driver driver = driver("34@@@@@@AB", 10, "Heavy", "01-01-1970");
            Bus bus = bus("12345678", 50, 80.0, "Diesel");

            assertFalse(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }

        @Test
        void driverOlderThanFiftyCannotDriveBusWithCapacityAboveFifty() {
            Driver driver = driver("34@@@@@@AB", 10, "Heavy", "01-01-1970");
            Bus bus = bus("12345678", 60, 80.0, "Diesel");

            assertFalse(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }

        @Test
        void driverExactlyFiftyCanDriveBusWithCapacityFifty() {
            Driver driver = driver("34@@@@@@AB", 10, "Heavy", "01-01-1976");
            Bus bus = bus("12345678", 50, 80.0, "Diesel");

            assertTrue(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }

        @Test
        void driverOlderThanFiftyCanDriveBusWithCapacityBelowFifty() {
            Driver driver = driver("34@@@@@@AB", 10, "Heavy", "01-01-1970");
            Bus bus = bus("12345678", 49, 80.0, "Diesel");

            assertTrue(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }
    }

    @Nested
    class ElectricBusExperienceRestrictionTests {
        @Test
        void electricBusRejectsDriverWithLessThanFiveYearsExperience() {
            Driver driver = driver("34@@@@@@AB", 4, "Heavy", "01-01-1990");
            Bus bus = bus("12345678", 40, 80.0, "Electricity");

            assertFalse(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }

        @Test
        void electricBusAcceptsDriverWithExactlyFiveYearsExperience() {
            Driver driver = driver("34@@@@@@AB", 5, "Heavy", "01-01-1990");
            Bus bus = bus("12345678", 40, 80.0, "Electricity");

            assertTrue(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }

        @Test
        void electricBusAcceptsDriverWithMoreThanFiveYearsExperience() {
            Driver driver = driver("34@@@@@@AB", 6, "Heavy", "01-01-1990");
            Bus bus = bus("12345678", 40, 80.0, "Electricity");

            assertTrue(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }

        @Test
        void dieselBusDoesNotUseElectricExperienceRule() {
            Driver driver = driver("34@@@@@@AB", 0, "Light", "01-01-1990");
            Bus bus = bus("12345678", 40, 80.0, "Diesel");

            assertTrue(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }
    }

    @Nested
    class DriverLicenceRestrictionTests {
        @Test
        void heavyLicenceCanOperateElectricBus() {
            Driver driver = driver("34@@@@@@AB", 5, "Heavy", "01-01-1990");
            Bus bus = bus("12345678", 40, 80.0, "Electricity");

            assertTrue(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }

        @Test
        void heavyLicenceCanOperateHybridBus() {
            Driver driver = driver("34@@@@@@AB", 5, "Heavy", "01-01-1990");
            Bus bus = bus("12345678", 40, 80.0, "Hybrid");

            assertTrue(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }

        @Test
        void publicTransportLicenceCanOperateElectricBus() {
            Driver driver = driver("34@@@@@@AB", 5, "PublicTransport", "01-01-1990");
            Bus bus = bus("12345678", 40, 80.0, "Electricity");

            assertTrue(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }

        @Test
        void publicTransportLicenceCanOperateHybridBus() {
            Driver driver = driver("34@@@@@@AB", 5, "PublicTransport", "01-01-1990");
            Bus bus = bus("12345678", 40, 80.0, "Hybrid");

            assertTrue(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }

        @Test
        void lightLicenceCannotOperateElectricBus() {
            Driver driver = driver("34@@@@@@AB", 5, "Light", "01-01-1990");
            Bus bus = bus("12345678", 40, 80.0, "Electricity");

            assertFalse(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }

        @Test
        void mediumLicenceCannotOperateHybridBus() {
            Driver driver = driver("34@@@@@@AB", 5, "Medium", "01-01-1990");
            Bus bus = bus("12345678", 40, 80.0, "Hybrid");

            assertFalse(BusAssignmentValidator.canDriverOperateBus(driver, bus, LocalDate.of(2026, 1, 1)));
        }
    }

    private Driver driver(String driverID, int experienceYears, String licenseType, String birthdate) {
        return new Driver(
                driverID,
                "Batu",
                experienceYears,
                licenseType,
                "12|King Street|Melbourne|VIC|Australia",
                birthdate);
    }

    private Bus bus(String busID, int capacity, double fuelLevel, String fuelType) {
        return new Bus(busID, capacity, fuelLevel, fuelType);
    }
}
