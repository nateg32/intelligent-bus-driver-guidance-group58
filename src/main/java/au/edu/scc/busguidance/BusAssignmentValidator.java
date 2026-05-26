package au.edu.scc.busguidance;

import java.time.LocalDate;

public final class BusAssignmentValidator {
    private BusAssignmentValidator() {
    }

    public static boolean canDriverOperateBus(Driver driver, Bus bus) {
        // Batu TODO B3-B5: implement eligibility using today's date.
        return false;
    }

    public static boolean canDriverOperateBus(Driver driver, Bus bus, LocalDate date) {
        // Batu TODO B3: drivers older than 50 cannot drive buses with capacity 50 or more.
        // Batu TODO B4: electric buses require at least 5 years of driver experience.
        // Batu TODO B5: electric/hybrid buses require Heavy or PublicTransport licence.
        return false;
    }
}
