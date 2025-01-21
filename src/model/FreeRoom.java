package model;

public class FreeRoom extends Room{
	
	FreeRoom(String roomNumber, Double price, RoomType roomType,boolean isFree) {
		super(roomNumber, 0.0, roomType);
	}
	
	@Override
	public String toString() {
		return "Room Number:"+getRoomNumber()+"\nRoom Type:"+getRoomType()+"\nIs Free:"+isFree();
	}
}
