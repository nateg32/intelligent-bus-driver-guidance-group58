package au.edu.scc.busguidance;

import java.nio.file.Path;
import java.util.Optional;

public class DriverRepository {
    private final Path filePath;

    public DriverRepository() {
        this(Path.of("data", "drivers.txt"));
    }

    public DriverRepository(Path filePath) {
        this.filePath = filePath;
    }

    public boolean add(Driver driver) {
        // Patrick TODO: validate and append driver to the TXT file.
        return false;
    }

    public Optional<Driver> retrieve(String driverID) {
        // Patrick TODO: read the TXT file and return the matching driver.
        return Optional.empty();
    }

    public boolean update(String driverID, Driver updatedDriver) {
        // Patrick TODO: apply D4-D5 update rules and persist changes to TXT file.
        return false;
    }

    public int count() {
        // Patrick TODO: count non-header driver records in the TXT file.
        return 0;
    }

    public Path getFilePath() {
        return filePath;
    }
}
