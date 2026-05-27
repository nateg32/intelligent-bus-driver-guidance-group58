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
        // Point the repository to a safe, temporary file that gets wiped after tests run
        Path tempFile = tempDir.resolve("drivers_integration_test.txt");
        repo = new DriverRepository(tempFile);
    }

    private Driver createValidMockDriver(String id, String name, int exp, String license) {
        // Creates a driver using your teammate's exact parameterized constructor
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
        // Task 3.1: Verify valid drivers are stored correctly and retrieved via flat-file I/O
        Driver driver = createValidMockDriver("34#$FFGGAB", "Patrick Nguyen", 5, "Light");
        
        boolean isAdded = repo.add(driver);
        assertTrue(isAdded, "Repository should successfully accept and write a valid driver.");

        Optional<Driver> retrieved = repo.retrieve("34#$FFGGAB");
        assertTrue(retrieved.isPresent(), "Driver should be found in the TXT database.");
        assertEquals("Patrick Nguyen", retrieved.get().getName(), "Retrieved name should match the stored record.");
    }

    @Test
    void testIntegration_InvalidDriversAreRejected() {
        // Task 3.2: Verify invalid drivers are rejected without corrupting or appending to the text file
        // This driver uses an invalid address format (missing pipe separators) which fails DriverValidator
        Driver invalidDriver = new Driver("34#$FFGGAB", "Patrick Nguyen", 5, "Light", "Invalid Address Format No Pipes", "25-12-1990");
        
        boolean isAdded = repo.add(invalidDriver);
        assertFalse(isAdded, "Repository should reject drivers that fail validation rules.");
        assertEquals(0, repo.count(), "The text file database must remain completely empty.");
    }

    @Test
    void testIntegration_UpdatesArePersistedCorrectly() {
        // Task 3.3: Verify updates are persisted correctly and rewrite the underlying TXT file properly
        Driver driver = createValidMockDriver("34#$FFGGAB", "Patrick Nguyen", 8, "Light");
        repo.add(driver);

        // Update mutable fields (experience and address) while leaving ID and Name identical (D5 constraint)
        Driver updatedDriver = new Driver(
            "34#$FFGGAB", 
            "Patrick Nguyen", 
            9, 
            "Light", 
            "999|FlindersSt|Melbourne|VIC|Australia", 
            "25-12-1990"
        );

        boolean isUpdated = repo.update("34#$FFGGAB", updatedDriver);
        assertTrue(isUpdated, "Repository update operation should succeed.");

        // Read directly back from the file to confirm persistence across system states
        Optional<Driver> freshlyRetrieved = repo.retrieve("34#$FFGGAB");
        assertTrue(freshlyRetrieved.isPresent());
        assertEquals(9, freshlyRetrieved.get().getExperienceYears(), "Updated experience should be permanently saved.");
        assertEquals("999|FlindersSt|Melbourne|VIC|Australia", freshlyRetrieved.get().getAddress(), "Updated address should be permanently saved.");
    }

    @Test
    void testIntegration_RecordCountsAreUpdatedCorrectly() {
        // Task 3.4: Verify record counts are updated correctly as tracking entities populate the flat file
        assertEquals(0, repo.count(), "Initial clean slate file count should be exactly 0.");

        Driver first = createValidMockDriver("34#$FFGGAB", "Patrick Nguyen", 5, "Light");
        repo.add(first);
        assertEquals(1, repo.count(), "Count should increment to 1 after adding the first driver.");

        Driver second = createValidMockDriver("55@&MMPPAA", "Nate G", 12, "Public Transport");
        repo.add(second);
        assertEquals(2, repo.count(), "Count should increment to 2 after adding a second driver.");
    }
}
