package au.edu.scc.busguidance;

import java.util.Collection;

public final class DriverValidator {
    private DriverValidator() {
    }

    public static boolean isValidDriverID(String driverID) {
        // Nathaniel TODO D1: implement driverID validation.
        return false;
    }

    public static boolean isUniqueDriverID(String driverID, Collection<Driver> existingDrivers) {
        // Nathaniel TODO D1: reject duplicate driver IDs.
        return false;
    }

    public static boolean isValidAddress(String address) {
        // Nathaniel TODO D2: implement Street Number|Street Name|Suburb|State|Country validation.
        return false;
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
}
