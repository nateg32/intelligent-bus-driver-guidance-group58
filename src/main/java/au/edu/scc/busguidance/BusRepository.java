package au.edu.scc.busguidance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BusRepository {
    private static final String HEADER = "# busID;capacity;fuelLevel;fuelType";

    private final Path filePath;

    public BusRepository() {
        this(Path.of("data", "buses.txt"));
    }

    public BusRepository(Path filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    public boolean add(Bus bus) {
        if (!BusValidator.isValidBus(bus)) {
            return false;
        }

        List<Bus> buses = getAllBuses();
        if (!BusValidator.isUniqueBusID(bus.getBusID(), buses)) {
            return false;
        }

        try {
            Files.writeString(filePath, toLine(bus) + System.lineSeparator(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to append bus record.", exception);
        }
    }

    public Optional<Bus> retrieve(String busID) {
        if (busID == null || busID.trim().isEmpty()) {
            return Optional.empty();
        }

        String trimmedBusID = busID.trim();
        return getAllBuses().stream()
                .filter(bus -> trimmedBusID.equals(bus.getBusID()))
                .findFirst();
    }

    public boolean update(String busID, Bus updatedBus) {
        if (busID == null || updatedBus == null) {
            return false;
        }

        List<Bus> buses = getAllBuses();
        for (int index = 0; index < buses.size(); index++) {
            Bus currentBus = buses.get(index);
            if (busID.equals(currentBus.getBusID())) {
                if (!BusValidator.canUpdateBus(currentBus, updatedBus)) {
                    return false;
                }

                buses.set(index, updatedBus);
                writeAllBuses(buses);
                return true;
            }
        }

        return false;
    }

    public int count() {
        return getAllBuses().size();
    }

    public Path getFilePath() {
        return filePath;
    }

    private void ensureFileExists() {
        try {
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(filePath)) {
                Files.writeString(filePath, HEADER + System.lineSeparator());
            }
        } catch (IOException exception) {
            throw new RuntimeException("Failed to initialize bus storage file.", exception);
        }
    }

    private List<Bus> getAllBuses() {
        try {
            List<Bus> buses = new ArrayList<>();
            for (String line : Files.readAllLines(filePath)) {
                parseLine(line).ifPresent(buses::add);
            }
            return buses;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to read bus records.", exception);
        }
    }

    private Optional<Bus> parseLine(String line) {
        if (line == null || line.trim().isEmpty() || line.startsWith("#")) {
            return Optional.empty();
        }

        String[] parts = line.split(";", -1);
        if (parts.length != 4) {
            return Optional.empty();
        }

        try {
            String busID = parts[0].trim();
            int capacity = Integer.parseInt(parts[1].trim());
            double fuelLevel = Double.parseDouble(parts[2].trim());
            String fuelType = parts[3].trim();
            Bus bus = new Bus(busID, capacity, fuelLevel, fuelType);
            return BusValidator.isValidBus(bus) ? Optional.of(bus) : Optional.empty();
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    private void writeAllBuses(List<Bus> buses) {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        buses.stream()
                .map(this::toLine)
                .forEach(lines::add);

        try {
            Files.write(filePath, lines);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to write bus records.", exception);
        }
    }

    private String toLine(Bus bus) {
        return String.format("%s;%d;%s;%s",
                bus.getBusID(),
                bus.getCapacity(),
                Double.toString(bus.getFuelLevel()),
                bus.getFuelType());
    }
}
