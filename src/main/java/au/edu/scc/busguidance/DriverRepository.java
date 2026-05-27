package au.edu.scc.busguidance;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverRepository {
    private final Path filePath;

    public DriverRepository() {
        this(Path.of("data", "drivers.txt"));
    }

    public DriverRepository(Path filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize text database storage file.", e);
        }
    }

    private Optional<Driver> parseLine(String line) {
        if (line == null || line.trim().isEmpty() || line.startsWith("driverID|")) {
            return Optional.empty();
        }
        
        String[] tokens = line.split("\\|", 6);
        if (tokens.length < 6) return Optional.empty();

        Driver driver = new Driver();
        driver.setDriverID(tokens[0].trim());
        driver.setName(tokens[1].trim());
        driver.setExperienceYears(Integer.parseInt(tokens[2].trim()));
        driver.setLicenseType(tokens[3].trim());
        driver.setAddress(tokens[4].trim());
        driver.setBirthdate(tokens[5].trim());
        return Optional.of(driver);
    }

    private String toLine(Driver driver) {
        return String.format("%s|%s|%d|%s|%s|%s",
                driver.getDriverID(),
                driver.getName(),
                driver.getExperienceYears(),
                driver.getLicenseType(),
                driver.getAddress(),
                driver.getBirthdate());
    }

    private List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line).ifPresent(drivers::add);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error executing read operations on text database.", e);
        }
        return drivers;
    }

    private void writeAllDrivers(List<Driver> drivers) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath.toFile(), false))) {
            for (Driver d : drivers) {
                pw.println(toLine(d));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing state maps to disk.", e);
        }
    }

    public boolean add(Driver driver) {
        if (driver == null || driver.getDriverID() == null) return false;
        
        DriverValidator.validateDriver(driver);

        List<Driver> drivers = getAllDrivers();
        for (Driver d : drivers) {
            if (d.getDriverID().equalsIgnoreCase(driver.getDriverID())) {
                throw new IllegalArgumentException("Duplicate driver IDs are not allowed.");
            }
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath.toFile(), true))) {
            pw.println(toLine(driver));
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to append driver record.", e);
        }
    }

    public Optional<Driver> retrieve(String driverID) {
        if (driverID == null || driverID.trim().isEmpty()) {
            return Optional.empty();
        }
        return getAllDrivers().stream()
                .filter(d -> d.getDriverID().equals(driverID.trim()))
                .findFirst();
    }

    public boolean update(String driverID, Driver updatedDriver) {
        if (driverID == null || updatedDriver == null) return false;

        List<Driver> drivers = getAllDrivers();
        int targetIndex = -1;
        Driver existingDriver = null;

        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getDriverID().equals(driverID)) {
                existingDriver = drivers.get(i);
                targetIndex = i;
                break;
            }
        }

        if (existingDriver == null) {
            return false;
        }

        // D5: Immutable Field Validations
        if (!existingDriver.getDriverID().equals(updatedDriver.getDriverID())) {
            throw new IllegalArgumentException("The driverID cannot be modified during update operations.");
        }
        if (!existingDriver.getName().equals(updatedDriver.getName())) {
            throw new IllegalArgumentException("The name cannot be modified during update operations.");
        }

        // D4: License Update Restriction Validation
        if (existingDriver.getExperienceYears() > 10) {
            if (!existingDriver.getLicenseType().equals(updatedDriver.getLicenseType())) {
                throw new IllegalArgumentException("License type cannot be changed during updates if experience exceeds 10 years.");
            }
        }

        DriverValidator.validateDriver(updatedDriver);

        drivers.set(targetIndex, updatedDriver);
        writeAllDrivers(drivers);
        return true;
    }

    public int count() {
        return getAllDrivers().size();
    }

    public Path getFilePath() {
        return filePath;
    }
}
