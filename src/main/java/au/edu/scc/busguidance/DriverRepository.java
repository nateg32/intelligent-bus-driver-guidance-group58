package au.edu.scc.busguidance;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class DriverRepository {
    private final Path filePath;

    public DriverRepository() {
        this(Path.of("data", "drivers.txt"));
    }

    public DriverRepository(Path filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    // Ensures the data directory and file exist safely before I/O operations
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

    // Parses a line into a Driver object, ignoring empty lines or headers safely
    private Optional<Driver> parseLine(String line) {
        if (line == null || line.trim().isEmpty() || line.startsWith("driverID|")) {
            return Optional.empty();
        }
        
        // Splitting by pipe with a limit of 6 to keep internal address pipes intact
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

    // Converts a Driver entity into a standardized pipe-delimited text string
    private String toLine(Driver driver) {
        return String.format("%s|%s|%d|%s|%s|%s",
                driver.getDriverID(),
                driver.getName(),
                driver.getExperienceYears(),
                driver.getLicenseType(),
                driver.getAddress(),
                driver.getBirthdate());
    }

    // Rules D1-D3 Creation Validations
    private void validateDriverCreationRules(Driver driver) {
        // D1: Driver ID Rules
        String id = driver.getDriverID();
        if (id == null || id.length() != 10) {
            throw new IllegalArgumentException("Driver ID must be exactly 10 characters long."); [cite: 51]
        }
        char first = id.charAt(0);
        char second = id.charAt(1);
        if (first < '2' || first > '9' || second < '2' || second > '9') {
            throw new IllegalArgumentException("The first two characters must be digits between 2 and 9."); [cite: 52]
        }
        int specialCharCount = 0;
        for (int i = 2; i <= 7; i++) {
            char ch = id.charAt(i);
            if (!Character.isLetterOrDigit(ch)) {
                specialCharCount++;
            }
        }
        if (specialCharCount < 2) {
            throw new IllegalArgumentException("There must be at least two special characters between characters 3 and 8."); [cite: 53]
        }
        if (!Pattern.matches("[A-Z]{2}", id.substring(8, 10))) {
            throw new IllegalArgumentException("The last two characters must be uppercase letters (A-Z)."); [cite: 54]
        }

        // D2: Address Format Check
        String address = driver.getAddress();
        if (address == null || address.split("\\|").length != 5) {
            throw new IllegalArgumentException("Address must follow format: Street Number | Street Name | City | State | Country"); [cite: 57]
        }

        // D3: Birthday Format Check
        String bday = driver.getBirthdate();
        if (bday == null || !Pattern.matches("^\\d{2}-\\d{2}-\\d{4}$", bday)) {
            throw new IllegalArgumentException("Birthdate must follow the format: DD-MM-YYYY"); [cite: 59]
        }
    }

    // Reads all current text file records into memory list helper
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

    // Rewrites file cache to store modified tracking states
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
        
        validateDriverCreationRules(driver);

        List<Driver> drivers = getAllDrivers();
        for (Driver d : drivers) {
            if (d.getDriverID().equalsIgnoreCase(driver.getDriverID())) {
                throw new IllegalArgumentException("Duplicate driver IDs are not allowed."); [cite: 50]
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
            return false; // Driver not found to update
        }

        // D5: Immutable Field Validations
        if (!existingDriver.getDriverID().equals(updatedDriver.getDriverID())) {
            throw new IllegalArgumentException("The driverID cannot be modified during update operations."); [cite: 64]
        }
        if (!existingDriver.getName().equals(updatedDriver.getName())) {
            throw new IllegalArgumentException("The name cannot be modified during update operations."); [cite: 64]
        }

        // D4: License Update Restriction Validation
        if (existingDriver.getExperienceYears() > 10) {
            if (!existingDriver.getLicenseType().equals(updatedDriver.getLicenseType())) {
                throw new IllegalArgumentException("License type cannot be changed during updates if experience exceeds 10 years."); [cite: 61]
            }
        }

        // Verify that format of changed variable fields remains correct (Address/Bday)
        validateDriverCreationRules(updatedDriver);

        // Swap out item inside memory list state and commit down to storage text stream
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
