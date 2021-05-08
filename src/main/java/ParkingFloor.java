import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ParkingFloor  implements Serializable {
    public static final long TotalTruckSlots = 1;
    public static final long TotalBikeSlots = 2;
    HashMap<VehicleType, ArrayList<ParkingSlot>> availableParkingSlots = new HashMap<>();
    HashMap<VehicleType, ArrayList<ParkingSlot>> parkedSlots = new HashMap<>();
    public long floorNumber;
    public ParkingFloor(long slots, long floorNumber){
        this.floorNumber = floorNumber;
        if(slots > 0){
            long slotNumber = 1, truckSlots = TotalTruckSlots, bikeSlots = TotalBikeSlots;
            availableParkingSlots.put(VehicleType.TRUCK, new ArrayList<>());
            availableParkingSlots.put(VehicleType.BIKE, new ArrayList<>());
            availableParkingSlots.put(VehicleType.CAR, new ArrayList<>());

            parkedSlots.put(VehicleType.TRUCK, new ArrayList<>());
            parkedSlots.put(VehicleType.BIKE, new ArrayList<>());
            parkedSlots.put(VehicleType.CAR, new ArrayList<>());

            if(slotNumber <= slots && truckSlots > 0){
                ParkingSlot truck = new ParkingSlot(VehicleType.TRUCK, slotNumber);
                ArrayList<ParkingSlot> availableParkingSlotsTrucks = availableParkingSlots.get(VehicleType.TRUCK);
                availableParkingSlotsTrucks.add(truck);
                slotNumber++;
                truckSlots--;
                while(slotNumber <= slots && truckSlots > 0){
                    ParkingSlot newTruck = SerializationUtils.clone(truck);
                    newTruck.slotNumber = slotNumber;
                    availableParkingSlotsTrucks.add(newTruck);
                    slotNumber++;
                    truckSlots--;
                }
            }
            if(slotNumber <= slots && bikeSlots > 0) {
                ParkingSlot bike = new ParkingSlot(VehicleType.BIKE, slotNumber);
                ArrayList<ParkingSlot> availableParkingSlotsBikes = availableParkingSlots.get(VehicleType.BIKE);
                availableParkingSlotsBikes.add(bike);
                slotNumber++;
                bikeSlots--;
                while (slotNumber <= slots && bikeSlots > 0) {
                    ParkingSlot newBike = SerializationUtils.clone(bike);
                    newBike.slotNumber = slotNumber;
                    availableParkingSlotsBikes.add(newBike);
                    slotNumber++;
                    bikeSlots--;
                }
            }
            if(slotNumber <= slots) {
                ParkingSlot car = new ParkingSlot(VehicleType.CAR, slotNumber);
                ArrayList<ParkingSlot> availableParkingSlotsCars = availableParkingSlots.get(VehicleType.CAR);
                availableParkingSlotsCars.add(car);
                slotNumber++;
                while (slotNumber <= slots) {
                    ParkingSlot newCar = SerializationUtils.clone(car);
                    newCar.slotNumber = slotNumber;
                    availableParkingSlotsCars.add(newCar);
                    slotNumber++;
                }
            }

        }
    }
}
