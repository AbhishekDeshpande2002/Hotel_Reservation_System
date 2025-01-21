package model;

public class Room implements IRoom{
	
	private String roomNumber;
	private Double roomPrice;
	private RoomType roomType;
	private boolean isFree;
	
	public Room(String roomNumber,Double roomPrice,RoomType roomType){
		this.roomNumber=roomNumber;
		this.roomPrice=roomPrice;
		this.roomType=roomType;
	}

	@Override
	public String getRoomNumber() {
		return roomNumber;
	}

	@Override
	public Double getRoomPrice() {
		return roomPrice;
	}

	@Override
	public RoomType getRoomType() {
		return roomType;
	}

	@Override
	public boolean isFree() {
		return isFree;
	}
	
	@Override
	public String toString() {
		return "Room Number:"+roomNumber+"\nPrice:"+roomPrice+"\nRoom Type:"+roomType+"\n";
	}
	
	
}
