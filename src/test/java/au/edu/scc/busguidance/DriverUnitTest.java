package au.edu.scc.busguidance;

import org.junit.jupiter.api.Test;

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
}
