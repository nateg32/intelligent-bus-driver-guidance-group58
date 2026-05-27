package au.edu.scc.busguidance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DriverIntegrationTest {

    private DriverRepository repo;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        Path tempFile = tempDir.resolve("drivers_integration_test.txt");
        repo = new DriverRepository(tempFile);
    }

    private Driver createValidMockDriver(String id, String name, int exp, String license) {
        // Using an address string with completely clear tokens to make sure split works
        return new Driver(
            id, 
            name, 
            exp, 
            license, 
            "123|SwanstonSt|Melbourne|VIC|Australia", 
            "25-12-1990"
        );
    }

    @Test
    void testIntegration_ValidDriversAreStoredCorrectly() {
        // "34@@@@@@AB" has 6 special characters in the middle, guaranteeing specialCharacterCount >= 2 passes
        Driver driver = createValidMockDriver("34@@@@@@AB", "Patrick Nguyen", 5, "Light");
        
        boolean isAdded = repo.add(driver);
        assertTrue(isAdded, "Repository should successfully accept and write a valid driver.");

        Optional<Driver> retrieved = repo.retrieve("34@@@@@@AB");
        assertTrue(retrieved.isPresent(), "Driver should be found in the TXT database.");
        assertEquals("Patrick Nguyen", retrieved.get().getName());
    }

    @Test
    void testIntegration_InvalidDriversAreRejected() {
        // "ABC" fails your teammate's numeric street number check (addressParts[0].matches("\\d+")), causing a rejection
        Driver invalidDriver = new Driver("34@@@@@@AB", "Patrick Nguyen", 5, "Light", "ABC|SwanstonSt|Melbourne|VIC|Australia", "25-12-1990");
        
        boolean isAdded = repo.add(invalidDriver);
        assertFalse(isAdded, "Repository should reject drivers that fail validation rules.");
        assertEquals(0, repo.count(), "The text file database must remain completely empty.");
    }

    @Test
    void testIntegration_UpdatesArePersistedCorrectly() {
        Driver driver = createValidMockDriver("34@@@@@@AB", "Patrick Nguyen", 8, "Light");
        repo.add(driver);

        Driver updatedDriver = new Driver(
            "34@@@@@@AB", 
            "Patrick Nguyen", 
            9, 
            "Light", 
            "999|FlindersSt|Melbourne|VIC|Australia", 
            "25-12-1990"
        );

        boolean isUpdated = repo.update("34@@@@@@AB", updatedDriver);
        assertTrue(isUpdated, "Repository update operation should succeed.");

        Optional<Driver> freshlyRetrieved = repo.retrieve("34@@@@@@AB");
        assertTrue(freshlyRetrieved.isPresent());
        assertEquals(9, freshlyRetrieved.get().getExperienceYears(), "Updated experience should be permanently saved.");
        assertEquals("999|FlindersSt|Melbourne|VIC|Australia", freshlyRetrieved.get().getAddress(), "Updated address should be permanently saved.");
    }

    @Test
    void testIntegration_RecordCountsAreUpdatedCorrectly() {
        assertEquals(0, repo.count());

        Driver first = createValidMockDriver("34@@@@@@AB", "Patrick Nguyen", 5, "Light");
        repo.add(first);
        assertEquals(1, repo.count());

        // Second driver uses a distinct valid ID "55######AA" to fulfill uniqueness checks
        Driver second = createValidMockDriver("55######AA", "Nate G", 12, "Public Transport");
        repo.add(second);
        assertEquals(2, repo.count());
    }
}
