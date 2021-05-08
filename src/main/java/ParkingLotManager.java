import java.util.ArrayList;
import java.util.stream.Collectors;

public class ParkingLotManager {
    private ParkingLot parkingLot;
    private ParkingTicket parkingTicket;
    public ParkingLotManager(ParkingLot parkingLot, ParkingTicket parkingTicket) {
        this.parkingLot = parkingLot;
        this.parkingTicket = parkingTicket;
    }
    public void parkVehicle(String vehicleType, String regNo, String color){
        long slotNo = Long.MAX_VALUE;
        ParkingSlot requiredSlot = null;
        for(ParkingFloor parkingFloor : parkingLot.floors){
            ArrayList<ParkingSlot> currentSlots = parkingFloor.availableParkingSlots.get(VehicleType.valueOf(vehicleType));
            slotNo = Long.MAX_VALUE;
            for(ParkingSlot slot: currentSlots){
                if(slotNo > slot.slotNumber){
                    requiredSlot = slot;
                    slotNo = slot.slotNumber;
                }
            }
            if(requiredSlot != null){
                currentSlots.remove(requiredSlot);
                requiredSlot.vehicle = new Vehicle(VehicleType.valueOf(vehicleType), regNo, color);
                parkingFloor.parkedSlots.get(VehicleType.valueOf(vehicleType)).add(requiredSlot);
                System.out.println("Parked vehicle. Ticket ID: "+parkingTicket.generateTicket(parkingFloor.floorNumber, requiredSlot.slotNumber));
                return;
            }
        }
        System.out.println("Parking Lot Full");
    }
    public void unparkVehicle(String ticketId){
        String[] ticketDetails = ticketId.split("_");
        if(!isValid(ticketDetails)){
            System.out.println("Invalid Ticket");
            return;
        }
        VehicleType vehicleType = VehicleType.CAR;
        if(Integer.parseInt(ticketDetails[2]) < ParkingFloor.TotalTruckSlots){
            vehicleType = VehicleType.TRUCK;
        }else if(Integer.parseInt(ticketDetails[2]) < ParkingFloor.TotalBikeSlots){
            vehicleType = VehicleType.BIKE;
        }
        ArrayList<ParkingSlot> parkedSlots =  this.parkingLot.floors.get(Integer.parseInt(ticketDetails[1])-1).parkedSlots.get(vehicleType);
        ParkingSlot requiredSlot = null;
        for(ParkingSlot slot: parkedSlots){
            if(slot.slotNumber == Long.parseLong(ticketDetails[2])){
                requiredSlot = slot;
                break;
            }
        }
        this.parkingLot.floors.get(Integer.parseInt(ticketDetails[1])-1).availableParkingSlots.get(vehicleType).add(requiredSlot);
        System.out.println("Unparked vehicle with Registration Number: "+ requiredSlot.vehicle.registrationNumber+" and Color: "+ requiredSlot.vehicle.color);
        requiredSlot.vehicle = null;
    }

    private boolean isValid(String[] ticketDetails) {
        if(ticketDetails.length != 3){
            return false;
        }
        if(ticketDetails[0].compareTo(parkingLot.id) != 0){
            return false;
        }
        try {
            int floor = Integer.parseInt(ticketDetails[1]);
            long slotNumber = Long.parseLong(ticketDetails[2]);
            if(floor < 1 || slotNumber < 1 || parkingLot.floors.size() < floor || parkingLot.slotsPerFloor < slotNumber){
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void display(String displayType, String vehicleType){
        switch (displayType){
            case "free_count":
                for(ParkingFloor parkingFloor : this.parkingLot.floors){
                    System.out.println("No. of free slots for "+vehicleType+" on Floor "+parkingFloor.floorNumber+": "+parkingFloor.availableParkingSlots.get(VehicleType.valueOf(vehicleType)).size());
                }
                break;
            case "free_slots":
                for(ParkingFloor parkingFloor : this.parkingLot.floors){
                    String joinedSlots = parkingFloor.availableParkingSlots.get(VehicleType.valueOf(vehicleType)).stream().map(s -> Long.toString(s.slotNumber)).collect(Collectors.joining(", "));
                    System.out.println("No. of free slots for "+vehicleType+" on Floor "+parkingFloor.floorNumber+": "+joinedSlots);
                }
                break;
            case "occupied_slots":
                for(ParkingFloor parkingFloor : this.parkingLot.floors){
                    String joinedSlots = parkingFloor.parkedSlots.get(VehicleType.valueOf(vehicleType)).stream().map(s -> Long.toString(s.slotNumber)).collect(Collectors.joining(", "));
                    System.out.println("No. of free slots for "+vehicleType+" on Floor "+parkingFloor.floorNumber+": "+joinedSlots);
                }
                break;
        }
    }
}


//park_vehicle <vehicle_type> <reg_no> <color>
//unpark_vehicle <ticket_id>
//display <display_type> <vehicle_type>
//Possible values of display_type: free_count, free_slots, occupied_slots
//exit

//Given a vehicle, it finds the first available slot, books it, creates a ticket, parks the vehicle, and finally returns the ticket.
//Unparks a vehicle given the ticket id.
//Displays the number of free slots per floor for a specific vehicle type.
//Displays all the free slots per floor for a specific vehicle type.
//Displays all the occupied slots per floor for a specific vehicle type.