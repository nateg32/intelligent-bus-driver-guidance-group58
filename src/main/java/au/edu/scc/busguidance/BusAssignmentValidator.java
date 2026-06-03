package au.edu.scc.busguidance;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public final class BusAssignmentValidator {
    private static final DateTimeFormatter BIRTHDATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu");

    private BusAssignmentValidator() {
    }

    public static boolean canDriverOperateBus(Driver driver, Bus bus) {
        return canDriverOperateBus(driver, bus, LocalDate.now());
    }

    public static boolean canDriverOperateBus(Driver driver, Bus bus, LocalDate date) {
        if (driver == null || bus == null || date == null) {
            return false;
        }

        LocalDate birthdate;
        try {
            birthdate = LocalDate.parse(driver.getBirthdate(), BIRTHDATE_FORMATTER);
        } catch (Exception exception) {
            return false;
        }

        int age = Period.between(birthdate, date).getYears();
        String fuelType = bus.getFuelType();
        String licenseType = driver.getLicenseType();

        if (age > 50 && bus.getCapacity() >= 50) {
            return false;
        }

        if ("Electricity".equals(fuelType) && driver.getExperienceYears() < 5) {
            return false;
        }

        if ("Electricity".equals(fuelType) || "Hybrid".equals(fuelType)) {
            String normalizedLicenseType = licenseType == null ? "" : licenseType.replace(" ", "");
            return "Heavy".equals(normalizedLicenseType) || "PublicTransport".equals(normalizedLicenseType);
        }

        return true;
    }
}