package au.edu.scc.busguidance;

import java.util.Collection;
import java.util.Objects;

public final class DriverValidator {
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
        // Nathaniel TODO D3: implement DD-MM-YYYY validation.
        return false;
    }

    public static boolean isValidDriver(Driver driver) {
        // Nathaniel/Patrick TODO: combine all driver validation rules.
        return false;
    }

    public static boolean canUpdateDriver(Driver currentDriver, Driver updatedDriver) {
        // Patrick TODO D4-D5: implement update restrictions.
        return false;
    }

    private static boolean isSpecialCharacter(char character) {
        return !Character.isLetterOrDigit(character) && !Character.isWhitespace(character);
    }

    private static boolean isPresent(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
