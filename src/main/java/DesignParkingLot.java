import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DesignParkingLot {
    private final ParkingLotManager parkingLotManager;
    public DesignParkingLot(String parkingLotID, long parkingLotFloorCount, long slots) {
        ParkingLot.init(parkingLotID, parkingLotFloorCount, slots);
        ParkingTicket parkingTicket = new ParkingTicket(parkingLotID, ParkingLot.getInstance());
        this.parkingLotManager = new ParkingLotManager(ParkingLot.getInstance(), parkingTicket);
    }


    //create_parking_lot <parking_lot_id> <no_of_floors> <no_of_slots_per_floor>
    //park_vehicle <vehicle_type> <reg_no> <color>
    //unpark_vehicle <ticket_id>
    //display <display_type> <vehicle_type>
    //Possible values of display_type: free_count, free_slots, occupied_slots
    //exit

    public static void main(String[] args) throws IOException {
        System.out.println("Hello");
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //String[] parkingLotDetails = bufferedReader.readLine().split(" ");
        DesignParkingLot designParkingLot = null; //= new DesignParkingLot(Long.parseLong(parkingLotDetails[1]), Long.parseLong(parkingLotDetails[2]), Long.parseLong(parkingLotDetails[3]));
        while(true){
            String[] parkingLotDetails = bufferedReader.readLine().split(" ");
            if(parkingLotDetails[0].toLowerCase().compareTo("exit") == 0){
                break;
            }
            switch (parkingLotDetails[0]){
                case "create_parking_lot":
                    designParkingLot = new DesignParkingLot(parkingLotDetails[1], Long.parseLong(parkingLotDetails[2]), Long.parseLong(parkingLotDetails[3]));
                    break;
                case "park_vehicle":
                    designParkingLot.parkingLotManager.parkVehicle(parkingLotDetails[1], parkingLotDetails[2], parkingLotDetails[3]);
                    break;
                case "unpark_vehicle":
                    designParkingLot.parkingLotManager.unparkVehicle(parkingLotDetails[1]);
                    break;
                case "display":
                    designParkingLot.parkingLotManager.display(parkingLotDetails[1], parkingLotDetails[2]);
                    break;
                default:
                    break;
            }
        }



    }
}
