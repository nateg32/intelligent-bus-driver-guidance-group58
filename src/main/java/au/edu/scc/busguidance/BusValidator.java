package au.edu.scc.busguidance;

import java.util.Collection;

public final class BusValidator {
    private BusValidator() {
    }

    public static boolean isValidBusID(String busID) {
        // Ibrahim TODO B1: implement unique, exactly 8 digits validation.
        return false;
    }

    public static boolean isUniqueBusID(String busID, Collection<Bus> existingBuses) {
        // Ibrahim TODO B1: reject duplicate bus IDs.
        return false;
    }

    public static boolean canUpdateCapacity(int currentCapacity, int updatedCapacity) {
        // Ibrahim TODO B2: capacity may stay the same or decrease, but not increase.
        return false;
    }

    public static boolean isValidBus(Bus bus) {
        // Ibrahim TODO: combine bus validation rules.
        return false;
    }

    public static boolean canUpdateBus(Bus currentBus, Bus updatedBus) {
        // Ibrahim TODO B2: implement update validation.
        return false;
    }
}
