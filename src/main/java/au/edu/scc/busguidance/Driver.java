package au.edu.scc.busguidance;

import java.util.Objects;

public class Driver {
    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType;
    private String address;
    private String birthdate;

    public Driver(String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {
        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Driver driver)) {
            return false;
        }
        return experienceYears == driver.experienceYears
                && Objects.equals(driverID, driver.driverID)
                && Objects.equals(name, driver.name)
                && Objects.equals(licenseType, driver.licenseType)
                && Objects.equals(address, driver.address)
                && Objects.equals(birthdate, driver.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverID, name, experienceYears, licenseType, address, birthdate);
    }
}
