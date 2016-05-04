public class Ship 
{
//////// CLASS VARIABLES ////////
	private String ShipName;
	private int ShipSize;
	private int[][] ShipCoordinates;
	private boolean IsShipAfloat = true;
	private int CX = 0;
	private int CY = 0;

//////// SHIP CONSTRUCTOR	////////
	public Ship(String InputShipName, int InputShipSize)
	{
		ShipName = InputShipName;
		ShipSize = InputShipSize;
		ShipCoordinates = new int[ShipSize][2];
	}

//////// SETTERS ////////		
	void SetShipCoordinates(int InputXCoordinate, int InputYCoordinate, int Inputiii)
		{
			ShipCoordinates[Inputiii][0] = InputXCoordinate;
			ShipCoordinates[Inputiii][1] = InputYCoordinate;
		}
	
	void SetIsShipAfloat(boolean InputBoolean)
		{IsShipAfloat = InputBoolean;}
	
	void SetShipCX(int InputCX)
		{CX = InputCX;}
	
	void SetShipCY(int InputCY)
		{CY = InputCY;}
	
//////// GETTERS ////////
	String GetShipName()
		{return ShipName;}
	
	int GetShipSize()
		{return ShipSize;}
	
	int GetShipXCoordinate(int Inputiii)
		{return ShipCoordinates[Inputiii][0];}

	int GetShipYCoordinate(int Inputiii)
		{return ShipCoordinates[Inputiii][1];}
	
	boolean GetIsShipAfloat()
		{return IsShipAfloat;}
	
	int GetShipCX()
	{return CX;}

	int GetShipCY()
	{return CY;}
	
//////// CHECK SHIP HEALTH ////////
	void CheckShipHealth(Grid PlayerChart)
	{
		int Hits = 0;
		for(int i = 0; i < ShipSize; i++)
		{if(PlayerChart.GetGridCoordinateState( GetShipXCoordinate(i),GetShipYCoordinate(i) ) == 2){Hits++;}}
		
		if(Hits == ShipSize){System.out.println(ShipName + " sunk!"); SetIsShipAfloat(false);}
	}
//////// CHECK SHIP COORDINATE ////////

//////// CHECK SHIP DIRECTION ////////	
}
