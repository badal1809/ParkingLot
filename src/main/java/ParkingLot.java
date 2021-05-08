import org.apache.commons.lang3.SerializationUtils;
import java.util.ArrayList;

public class ParkingLot {
    public String id;
    public long floorCount;
    public long slotsPerFloor;
    public ArrayList<ParkingFloor> floors = new ArrayList<>();
    private static volatile ParkingLot instance = null;
    //Create the parking lot.
    //Add floors to the parking lot.
    //Add a parking lot slot to any of the floors.
    private ParkingLot(String parkingLotID, long parkingLotFloorCount, long slots) {
        if(instance != null){
            throw new RuntimeException("Use getInstance method to create");
        }
        id = parkingLotID;
        floorCount = parkingLotFloorCount;
        slotsPerFloor = slots;
        if(floorCount > 0){
            long floorNumber = 1;
            ParkingFloor parkingFloor = new ParkingFloor(slots, floorNumber);
            floors.add(parkingFloor);
            floorNumber++;
            while(floorNumber <= floorCount){
                ParkingFloor newParkingFloor = SerializationUtils.clone(parkingFloor);
                newParkingFloor.floorNumber = floorNumber;
                floors.add(newParkingFloor);
                floorNumber++;
            }
        }
    }
    public static ParkingLot getInstance(){
        if(instance == null){
            throw new RuntimeException("Call init method first");
        }
        return instance;
    }
    public static void init(String parkingLotID, long parkingLotFloorCount, long slots){
        if(instance == null){
            synchronized (ParkingLot.class){
                if(instance == null){
                    instance = new ParkingLot(parkingLotID,parkingLotFloorCount, slots);
                }
            }
        }else {
            throw new RuntimeException("Already initialized");
        }
    }
}
