package au.edu.scc.busguidance;

import java.nio.file.Path;
import java.util.Optional;

public class BusRepository {
    private final Path filePath;

    public BusRepository() {
        this(Path.of("data", "buses.txt"));
    }

    public BusRepository(Path filePath) {
        this.filePath = filePath;
    }

    public boolean add(Bus bus) {
        // Ibrahim TODO: validate and append bus to the TXT file.
        return false;
    }

    public Optional<Bus> retrieve(String busID) {
        // Ibrahim TODO: read the TXT file and return the matching bus.
        return Optional.empty();
    }

    public boolean update(String busID, Bus updatedBus) {
        // Ibrahim TODO: apply B2 update rules and persist changes to TXT file.
        return false;
    }

    public int count() {
        // Ibrahim TODO: count non-header bus records in the TXT file.
        return 0;
    }

    public Path getFilePath() {
        return filePath;
    }
}
