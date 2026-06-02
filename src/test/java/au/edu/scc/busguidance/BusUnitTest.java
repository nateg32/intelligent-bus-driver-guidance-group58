package au.edu.scc.busguidance;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    private Bus bus(String busID, int capacity, double fuelLevel, String fuelType) {
        return new Bus(busID, capacity, fuelLevel, fuelType);
    }
}
