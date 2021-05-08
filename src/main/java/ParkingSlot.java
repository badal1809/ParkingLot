import java.io.Serializable;
import java.util.ArrayList;

public class ParkingSlot extends ArrayList<ParkingSlot> implements Serializable {
    private VehicleType vehicleType;
    public long slotNumber;
    public Vehicle vehicle = null;

    public ParkingSlot(VehicleType vehicleType, long slotNumber) {
        this.vehicleType = vehicleType;
        this.slotNumber = slotNumber;
    }
}
