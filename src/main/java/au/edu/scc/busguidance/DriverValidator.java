package au.edu.scc.busguidance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.Objects;

public final class DriverValidator {
    private static final DateTimeFormatter BIRTHDATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private DriverValidator() {
    }

    public static boolean isValidDriverID(String driverID) {
        if (driverID == null || driverID.length() != 10) {
            return false;
        }

        String firstTwoCharacters = driverID.substring(0, 2);
        String middleCharacters = driverID.substring(2, 8);
        String lastTwoCharacters = driverID.substring(8, 10);

        long specialCharacterCount = middleCharacters.chars()
                .filter(character -> isSpecialCharacter((char) character))
                .count();

        return firstTwoCharacters.matches("[2-9]{2}")
                && specialCharacterCount >= 2
                && lastTwoCharacters.matches("[A-Z]{2}");
    }

    public static boolean isUniqueDriverID(String driverID, Collection<Driver> existingDrivers) {
        if (driverID == null || existingDrivers == null) {
            return false;
        }

        return existingDrivers.stream()
                .filter(Objects::nonNull)
                .noneMatch(driver -> driverID.equals(driver.getDriverID()));
    }

    public static boolean isValidAddress(String address) {
        if (address == null || address.contains(";")) {
            return false;
        }

        String[] addressParts = address.split("\\|", -1);
        if (addressParts.length != 5) {
            return false;
        }

        return addressParts[0].matches("\\d+")
                && isPresent(addressParts[1])
                && isPresent(addressParts[2])
                && isPresent(addressParts[3])
                && isPresent(addressParts[4]);
    }

    public static boolean isValidBirthdate(String birthdate) {
        if (birthdate == null) {
            return false;
        }

        try {
            LocalDate.parse(birthdate, BIRTHDATE_FORMATTER);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    public static boolean isValidDriver(Driver driver) {  
        if (driver == null) {
        return false;
        }
    
        return isValidDriverID(driver.getDriverID())
            && isValidAddress(driver.getAddress())
            && isValidBirthdate(driver.getBirthdate());
    }

    public static boolean canUpdateDriver(Driver currentDriver, Driver updatedDriver) {
        if (currentDriver == null || updatedDriver == null) {
            return false;
        }

        // D5 Rule: driverID and name must be immutable (cannot change)
        if (!currentDriver.getDriverID().equals(updatedDriver.getDriverID())) {
            return false;
        }
        if (!currentDriver.getName().equals(updatedDriver.getName())) {
            return false;
        }

        // D4 Rule: If experience is > 10, the license type cannot change
        if (currentDriver.getExperienceYears() > 10) {
            if (!currentDriver.getLicenseType().equals(updatedDriver.getLicenseType())) {
                return false;
            }
        }

        // Pass the updated fields through format validations
        return isValidAddress(updatedDriver.getAddress())
                && isValidBirthdate(updatedDriver.getBirthdate());
    }

    private static boolean isSpecialCharacter(char character) {
        return !Character.isLetterOrDigit(character) && !Character.isWhitespace(character);
    }

    private static boolean isPresent(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
