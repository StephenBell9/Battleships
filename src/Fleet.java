import java.util.Scanner;

public class Fleet 
{
//////// CLASS VARIABLES ////////
	private Ship[] Shiplist;
	private boolean IsFleetAfloat = true;
	private int FleetSize;
	
//////// FLEET CONSTRUCTOR ////////
	Fleet(int InputFleetSize, Grid PlayerChart, Scanner in)
		{
			FleetSize = InputFleetSize;
			Shiplist = new Ship[FleetSize];
			SetFleetRoster();
			SetFleetPosition(InputFleetSize, PlayerChart, in);
		}
	
//////// SETTERS ////////
	void SetFleetRoster()
		{
			int FleetArraySize = FleetSize - 1;
			switch(FleetArraySize)
				{
					case 6:
						Shiplist[6] = new Ship("Carrier_1", 5);
					case 5:
						Shiplist[5] = new Ship("Destroyer_1", 4);
					case 4:
						Shiplist[4] = new Ship("Submarine_1", 3);
					case 3:
						Shiplist[3] = new Ship("Battleship_1", 3);
					case 2:
						Shiplist[2] = new Ship("Battleship_2", 3);
					case 1:
						Shiplist[1] = new Ship("Patrol_Boat_1", 2);
					case 0:
						Shiplist[0] = new Ship("Patrol_Boat_2", 2); break;		
				}// FleetArraySize switch finish
		}// SetFleetRoster finish
	
	void SetIsFleetAfloat(boolean InputIsFleetAfloat)
		{IsFleetAfloat = InputIsFleetAfloat;}

////////GETTERS ////////
	int GetFleetSize()
	{return FleetSize;}
	
	boolean GetIsFleetAfloat()
		{
			int ShipCount = 0;
			for(int i = 0; i < FleetSize; i++)
			{
				if(Shiplist[i].GetIsShipAfloat())
				{ShipCount++;}
			}
			
			if(ShipCount == 0){IsFleetAfloat = false;}
			return IsFleetAfloat;
		}

//////// OTHER METHODS /////////
	
	int GetShipAtCoordinates(int InputX, int InputY, Grid PlayerChart, int InputFleetSize)//checks which ship is at trageted coordinates
	{
		int ShipID = 0;
		for(int i = 0; i < InputFleetSize; i++)// loop through ships
		{
			for(int j = 0; j < Shiplist[i].GetShipSize(); j++) // loop through coords
			{
				if((Shiplist[i].GetShipXCoordinate(j) == InputX)&&(Shiplist[i].GetShipYCoordinate(j) == InputY))
					{ShipID = i;}
			}
		}
		return ShipID;
	}

	void SetFleetPosition(int InputFleetSize, Grid PlayerChart, Scanner in)
	{
		for(int i = 0; i < InputFleetSize; i++)
			{SetShipPlace(PlayerChart, in, i);}
			PlayerChart.DisplayGridCoordinateStates();
	}
		
//////// EXPERIMENTAL SET SHIP POSITION METHOD ////////
	void SetShipPlace(Grid PlayerChart, Scanner in, int ShiplistI)
	{
		PlayerChart.DisplayGridCoordinateStates();
		int[] StartCoordinates = SetStartPosition(PlayerChart, in, ShiplistI);
		int[] EndCoordinates = SetEndPosition(PlayerChart, in, ShiplistI, StartCoordinates);

		Shiplist[ShiplistI].SetShipCoordinates(StartCoordinates[0], StartCoordinates[1], 0); // actual start coordinate
		Shiplist[ShiplistI].SetShipCoordinates(EndCoordinates[0], EndCoordinates[1], (Shiplist[ShiplistI].GetShipSize()-1));
		PlayerChart.SetGridCoordinateState(StartCoordinates[0], StartCoordinates[1], 1);
		PlayerChart.SetGridCoordinateState(EndCoordinates[0], EndCoordinates[1], 1);
		
		int AMX = StartCoordinates[0] + Shiplist[ShiplistI].GetShipCX(); //variables to hold the coordinates in between the start and end  // "temp middle X"
		int AMY = StartCoordinates[1] + Shiplist[ShiplistI].GetShipCY(); // "middle y"
		
		for(int i = 1; i < (Shiplist[ShiplistI].GetShipSize()-1); i++)
			{
				Shiplist[ShiplistI].SetShipCoordinates( AMX, AMY, i);
				PlayerChart.SetGridCoordinateState(AMX, AMY, 1);
				AMX += Shiplist[ShiplistI].GetShipCX();
				AMY += Shiplist[ShiplistI].GetShipCY();
			}
		//PlayerChart.DisplayGridCoordinateStates();
	}//SetPlace end	

////////CHECKS GIVEN COORDINATES ARE EMPTY ARE ON THE GRID AND NOT SURROUNDED BY INVALID DIRECTIONS ////////
	int[] SetStartPosition(Grid PlayerChart, Scanner in, int ShiplistI)
	{
		int[] TestStartCoordinates = new int[2]; 
		System.out.println("Starting x coordinate of "+ Shiplist[ShiplistI].GetShipName() +"?"); int TSX = in.nextInt(); // "Temp Start X"
		System.out.println("Starting y coordinate of "+ Shiplist[ShiplistI].GetShipName() +"?"); int TSY = in.nextInt(); // Get coords from user  
		if(IsCoordinateOK(TSX,TSY, PlayerChart)==false){System.out.println("Invalid Placement!"); return SetStartPosition(PlayerChart, in, ShiplistI);}
		
		int TCX;
		int TCY;
		int TTSX = TSX;
		int TTSY = TSY;
		int DCheck = 0;
		for(int i = 1; i <= 4; i++) // test all 4 directions
			{
				TCX = 0;
				TCY = 0;
				
				switch(i)
				{
				case 1:
					{TCY = 1; break;} // North
				case 2:
					{TCX = 1; break;} // East
				case 3:
					{TCY = -1; break;} // South
				case 4:
					{TCX = -1; break;} // West				
				}
				
				for(int j = 1; j <= Shiplist[ShiplistI].GetShipSize(); j++) // test all possible ship coordinates in this direction
				{
					TTSX += TCX;
					TTSY += TCY;
					if(IsCoordinateOK(TTSX,TTSY, PlayerChart)==false){DCheck++;}
				}
			}
		if(DCheck == 4){System.out.println("Invalid Placement!"); return SetStartPosition(PlayerChart, in, ShiplistI);} // if all 4 directions invalid try again
		
		TestStartCoordinates[0] = TSX;
		TestStartCoordinates[1] = TSY;
		return TestStartCoordinates;
	}
	
//////// SETS DIRECTION AND END COORDINATES ///////
	int[] SetEndPosition(Grid PlayerChart, Scanner in, int ShiplistI, int[] StartCoordinates)
	{
		int[] TestEndCoordinates = new int[2]; 
		int TEX = StartCoordinates[0]; //variables to hold the end coordinates // "Temp end X"
		int TEY = StartCoordinates[1]; // initially set to same as start  // "end Y"
	
		int InputDirection = 0;
		System.out.println("Direction of Placement? North: 1  East: 2  South: 3  West: 4  "); // Get direction from user
		InputDirection = in.nextInt();
	
		int TCX = 0;
		int TCY = 0; // variables to hold change in value of coordinates per coordinate from start 
		
		switch (InputDirection) //and then adjusted based on chosen direction
		{
			case 0:
			{System.out.println("Something went wrong!");} break;
			case 1:
			{TEY += (Shiplist[ShiplistI].GetShipSize() - 1); TCY = 1; break;} // North
			case 2:
			{TEX += (Shiplist[ShiplistI].GetShipSize() - 1); TCX = 1; break;} // East
			case 3:
			{TEY -= (Shiplist[ShiplistI].GetShipSize() - 1); TCY = -1; break;} // South
			case 4:
			{TEX -= (Shiplist[ShiplistI].GetShipSize() - 1); TCX = -1; break;} // West
		}
		
		int TMEX = StartCoordinates[0] + TCX; // test test middle to end X coord
		int TMEY = StartCoordinates[1] + TCY;
		boolean DirectionOK = true;
		for(int ttt = 1; ttt <= Shiplist[ShiplistI].GetShipSize(); ttt++) // test all possible ship coordinates in this direction including end
		{
			TMEX += TCX;
			TMEY += TCY;
			if(IsCoordinateOK(TMEX,TMEY, PlayerChart)==false){DirectionOK = false;}	
		}
		if(DirectionOK == false){System.out.println("Invalid Placement!"); return SetEndPosition(PlayerChart, in, ShiplistI, StartCoordinates);}
		
		Shiplist[ShiplistI].SetShipCX(TCX);
		Shiplist[ShiplistI].SetShipCY(TCY);		
		TestEndCoordinates[0] = TEX;
		TestEndCoordinates[1] = TEY;
		return TestEndCoordinates;
	}

//////// CHECKS GIVEN COORDINATES ARE EMPTY AND ON THE GRID ////////
	boolean IsCoordinateOK(int nX, int nY, Grid PlayerChart)
	{
		boolean bCheck = true;
		if((nX < 0)||(nX >= PlayerChart.GetGridSize())||(nY < 0)||(nY >= PlayerChart.GetGridSize()))
		{
			//System.out.println("Invalid Placement!");
			//System.out.println("Please set coordinates in the range from (0,0) to (" + (PlayerChart.GetGridSize() - 1) + "," + (PlayerChart.GetGridSize() - 1) + ")");
			//System.out.println("Not on another Ship");
			bCheck = false;
		}
		else if(PlayerChart.GetGridCoordinateState(nX,nY) == 1)
		{
			//System.out.println("There's already a ship here!");
			//System.out.println("Please try available coordinates.");
			bCheck = false;
		}		
		return bCheck;
	}
	
}// Fleet Class finish
