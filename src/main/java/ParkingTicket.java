public class ParkingTicket {
    private final String parkingLotName;
    private final ParkingLot parkingLot;
    public ParkingTicket(String parkingLotName, ParkingLot parkingLot) {
        this.parkingLotName = parkingLotName;
        this.parkingLot = parkingLot;
    }
    public String generateTicket(long floor, long slot){
        if(floor < 1 || slot < 1 || parkingLot.floors.size() < floor || parkingLot.slotsPerFloor < slot){
            return new RuntimeException("Invalid Ticket").toString();
        }
        return parkingLotName+"_"+floor+"_"+slot;
    }
}
