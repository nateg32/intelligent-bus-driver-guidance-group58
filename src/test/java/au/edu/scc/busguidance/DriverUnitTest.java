package au.edu.scc.busguidance;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DriverUnitTest {
    @Test
    void validDriverIdIsAccepted() {
        assertTrue(DriverValidator.isValidDriverID("23@@abCDXY"));
    }

    @Test
    void driverIdShorterThanTenCharactersIsRejected() {
        assertFalse(DriverValidator.isValidDriverID("23@@abCXY"));
    }

    @Test
    void driverIdLongerThanTenCharactersIsRejected() {
        assertFalse(DriverValidator.isValidDriverID("23@@abCDXYZ"));
    }

    @Test
    void driverIdWithFirstDigitBelowTwoIsRejected() {
        assertFalse(DriverValidator.isValidDriverID("13@@abCDXY"));
    }

    @Test
    void driverIdWithZeroInFirstTwoDigitsIsRejected() {
        assertFalse(DriverValidator.isValidDriverID("20@@abCDXY"));
    }

    @Test
    void driverIdWithoutTwoSpecialCharactersInMiddleIsRejected() {
        assertFalse(DriverValidator.isValidDriverID("23@aabCDXY"));
    }

    @Test
    void driverIdWithWhitespaceAsSpecialCharacterIsRejected() {
        assertFalse(DriverValidator.isValidDriverID("23@ abCDXY"));
    }

    @Test
    void driverIdWithoutUppercaseFinalLettersIsRejected() {
        assertFalse(DriverValidator.isValidDriverID("23@@abCDxy"));
    }

    @Test
    void uniqueDriverIdIsAccepted() {
        List<Driver> existingDrivers = List.of(driver("23@@abCDXY"));

        assertTrue(DriverValidator.isUniqueDriverID("24##efGHZZ", existingDrivers));
    }

    @Test
    void duplicateDriverIdIsRejected() {
        List<Driver> existingDrivers = List.of(driver("23@@abCDXY"));

        assertFalse(DriverValidator.isUniqueDriverID("23@@abCDXY", existingDrivers));
    }

    @Test
    void nullDriverIdIsNotUnique() {
        List<Driver> existingDrivers = List.of(driver("23@@abCDXY"));

        assertFalse(DriverValidator.isUniqueDriverID(null, existingDrivers));
    }

    @Test
    void nullExistingDriverListIsNotUnique() {
        assertFalse(DriverValidator.isUniqueDriverID("23@@abCDXY", null));
    }

    @Test
    void validAddressIsAccepted() {
        assertTrue(DriverValidator.isValidAddress("12|King Street|Melbourne|VIC|Australia"));
    }

    @Test
    void addressMissingFieldIsRejected() {
        assertFalse(DriverValidator.isValidAddress("12|King Street|Melbourne|VIC"));
    }

    @Test
    void addressUsingWrongSeparatorIsRejected() {
        assertFalse(DriverValidator.isValidAddress("12,King Street,Melbourne,VIC,Australia"));
    }

    @Test
    void addressWithBlankFieldIsRejected() {
        assertFalse(DriverValidator.isValidAddress("12||Melbourne|VIC|Australia"));
    }

    @Test
    void addressWithNonNumericStreetNumberIsRejected() {
        assertFalse(DriverValidator.isValidAddress("Twelve|King Street|Melbourne|VIC|Australia"));
    }

    @Test
    void addressContainingSemicolonIsRejected() {
        assertFalse(DriverValidator.isValidAddress("12|King Street|Melbourne|VIC|Australia;"));
    }

    @Test
    void validBirthdateIsAccepted() {
        assertTrue(DriverValidator.isValidBirthdate("15-04-1999"));
    }

    @Test
    void birthdateUsingWrongFormatIsRejected() {
        assertFalse(DriverValidator.isValidBirthdate("1999-04-15"));
    }

    @Test
    void impossibleBirthdateIsRejected() {
        assertFalse(DriverValidator.isValidBirthdate("31-02-1999"));
    }

    @Test
    void birthdateWithInvalidMonthIsRejected() {
        assertFalse(DriverValidator.isValidBirthdate("15-13-1999"));
    }

    @Test
    void leapYearBirthdateIsAccepted() {
        assertTrue(DriverValidator.isValidBirthdate("29-02-2000"));
    }

    @Test
    void nonLeapYearFebruaryTwentyNinthIsRejected() {
        assertFalse(DriverValidator.isValidBirthdate("29-02-2001"));
    }

    @Test
    void birthdateWithSingleDigitDayIsRejected() {
        assertFalse(DriverValidator.isValidBirthdate("5-04-1999"));
    }

    private Driver driver(String driverID) {
        return new Driver(
                driverID,
                "Nathaniel",
                6,
                "Heavy",
                "12|King Street|Melbourne|VIC|Australia",
                "15-04-1999");
    }
}
