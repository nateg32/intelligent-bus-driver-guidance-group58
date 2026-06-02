package au.edu.scc.busguidance;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public final class BusValidator {
    private static final Set<String> VALID_FUEL_TYPES = Set.of("Diesel", "Hybrid", "Electricity");

    private BusValidator() {
    }

    public static boolean isValidBusID(String busID) {
        return busID != null && busID.matches("\\d{8}");
    }

    public static boolean isUniqueBusID(String busID, Collection<Bus> existingBuses) {
        if (busID == null || existingBuses == null) {
            return false;
        }

        return existingBuses.stream()
                .filter(Objects::nonNull)
                .noneMatch(bus -> busID.equals(bus.getBusID()));
    }

    public static boolean canUpdateCapacity(int currentCapacity, int updatedCapacity) {
        return currentCapacity > 0 && updatedCapacity > 0 && updatedCapacity <= currentCapacity;
    }

    public static boolean isValidBus(Bus bus) {
        return bus != null
                && isValidBusID(bus.getBusID())
                && bus.getCapacity() > 0
                && bus.getFuelLevel() >= 0
                && bus.getFuelLevel() <= 100
                && VALID_FUEL_TYPES.contains(bus.getFuelType());
    }

    public static boolean canUpdateBus(Bus currentBus, Bus updatedBus) {
        return currentBus != null
                && updatedBus != null
                && Objects.equals(currentBus.getBusID(), updatedBus.getBusID())
                && isValidBus(updatedBus)
                && canUpdateCapacity(currentBus.getCapacity(), updatedBus.getCapacity());
    }
}
