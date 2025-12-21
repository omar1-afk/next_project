package org.noteam.nextclient.models;

public class DriverObj {
    private int driverId;
    private String name;

    public DriverObj(int driverId, String name) {
        this.driverId = driverId;
        this.name = name;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
