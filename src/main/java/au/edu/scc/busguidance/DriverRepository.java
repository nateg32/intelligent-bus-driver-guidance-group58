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
        
        // Split globally without a limit to capture every single pipe segment
        String[] tokens = line.split("\\|");
        if (tokens.length < 6) return Optional.empty();

        String id = tokens[0].trim();
        String name = tokens[1].trim();
        int experience = Integer.parseInt(tokens[2].trim());
        String license = tokens[3].trim();
        
        // The last element is always the birthdate
        String birthdate = tokens[tokens.length - 1].trim();
        
        // Rebuild the 5-part address from all the internal tokens in the middle
        StringBuilder addressBuilder = new StringBuilder();
        for (int i = 4; i < tokens.length - 1; i++) {
            addressBuilder.append(tokens[i].trim());
            if (i < tokens.length - 2) {
                addressBuilder.append("|");
            }
        }
        String address = addressBuilder.toString();

        Driver driver = new Driver(id, name, experience, license, address, birthdate);
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
        
        if (!DriverValidator.isValidDriver(driver)) {
            return false;
        }

        List<Driver> drivers = getAllDrivers();
        if (!DriverValidator.isUniqueDriverID(driver.getDriverID(), drivers)) {
            return false;
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

        if (!DriverValidator.canUpdateDriver(existingDriver, updatedDriver)) {
            return false;
        }

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