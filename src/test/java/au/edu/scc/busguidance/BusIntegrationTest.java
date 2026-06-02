package au.edu.scc.busguidance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BusIntegrationTest {
    private BusRepository repository;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        repository = new BusRepository(tempDir.resolve("buses_integration_test.txt"));
    }

    @Test
    void validBusesAreStoredAndRetrievedCorrectly() {
        Bus bus = bus("12345678", 45, 80.0, "Diesel");

        assertTrue(repository.add(bus));

        Optional<Bus> retrievedBus = repository.retrieve("12345678");
        assertTrue(retrievedBus.isPresent());
        assertEquals(bus, retrievedBus.get());
    }

    @Test
    void invalidBusesAreRejected() {
        Bus invalidBus = bus("BUS12345", 45, 80.0, "Diesel");

        assertFalse(repository.add(invalidBus));

        assertEquals(0, repository.count());
        assertTrue(repository.retrieve("BUS12345").isEmpty());
    }

    @Test
    void updatesArePersistedCorrectly() throws Exception {
        repository.add(bus("12345678", 60, 80.0, "Diesel"));
        Bus updatedBus = bus("12345678", 45, 65.5, "Hybrid");

        assertTrue(repository.update("12345678", updatedBus));

        Bus retrievedBus = repository.retrieve("12345678").orElseThrow();
        assertEquals(45, retrievedBus.getCapacity());
        assertEquals(65.5, retrievedBus.getFuelLevel());
        assertEquals("Hybrid", retrievedBus.getFuelType());
        assertTrue(Files.readString(repository.getFilePath()).contains("12345678;45;65.5;Hybrid"));
    }

    @Test
    void recordCountsAreUpdatedCorrectly() {
        assertEquals(0, repository.count());

        assertTrue(repository.add(bus("12345678", 45, 80.0, "Diesel")));
        assertEquals(1, repository.count());

        assertTrue(repository.add(bus("87654321", 30, 50.0, "Electricity")));
        assertEquals(2, repository.count());
    }

    @Test
    void duplicateBusIdsAreRejected() {
        assertTrue(repository.add(bus("12345678", 45, 80.0, "Diesel")));

        assertFalse(repository.add(bus("12345678", 30, 50.0, "Hybrid")));

        assertEquals(1, repository.count());
    }

    @Test
    void capacityIncreaseUpdateIsRejected() {
        repository.add(bus("12345678", 45, 80.0, "Diesel"));

        assertFalse(repository.update("12345678", bus("12345678", 60, 80.0, "Diesel")));

        assertEquals(45, repository.retrieve("12345678").orElseThrow().getCapacity());
    }

    private Bus bus(String busID, int capacity, double fuelLevel, String fuelType) {
        return new Bus(busID, capacity, fuelLevel, fuelType);
    }
}
