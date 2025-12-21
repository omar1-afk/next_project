package org.noteam.nextclient.models;

public class VehicleObj {
    private int vehicleId;

    public enum vehicleType{
        VAN("van"),
        TRUCK("Truck");
        private String type;
        vehicleType(String type){
            this.type=type;
        }
        @Override
        public String toString(){
            return type;
        }
    }
    private VehicleObj.vehicleType vehicleType;
    public VehicleObj.vehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleObj.vehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public VehicleObj(int vehicleId, VehicleObj.vehicleType vehicleType) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
    }
}
