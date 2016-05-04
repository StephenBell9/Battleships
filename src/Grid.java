public class Grid 
{
////////CLASS VARIABLES ////////
	private int GridSize = 4;
	private int[][] GridCoordinateStates;
	private String GridOwner;
	//private char[][] GridCoordinateStates2; // N: empty // "char": ship type // H: hit // M: miss	
	
//////// GRID CONSTRUCTOR ////////
	Grid(String InputPlayerName)
	{
		InitialSetGridCoordinateStates();
		SetGridOwner(InputPlayerName);
	}
//////// SETTERS ////////	
	void InitialSetGridCoordinateStates()
	{GridCoordinateStates = new int[GridSize][GridSize];} // Defaults to 0: empty
	
	void SetGridCoordinateState(int InputX, int InputY, int InputState)
	{GridCoordinateStates[InputX][InputY] = InputState;} // 0: empty // 1: ship // 2: hit // 3: miss
	
	void SetGridOwner(String InputPlayerName)
	{GridOwner = InputPlayerName;}
		
//////// GETTERS  ////////	
	int GetGridSize()
		{return GridSize;}
		
	int GetGridCoordinateState(int InputX, int InputY)
		{return GridCoordinateStates[InputX][InputY];}
	
	String GetGridOwner()
		{return GridOwner;}
	
//////// DISPLAY GRID ////////		
	void DisplayGridCoordinateStates()
	{
		dGridOwner();
		for(int i = 0; i < GridSize; i++) // Loops through display lines
		{
			xAxisBorder(i);
			for(int j = 0; j < GridSize; j++) 
				{System.out.print(GetGridCoordinateState(i,j)+ "  ");} // Displays constituents of line from grid
				 System.out.println("");
		}
		yAxisBorder();		
	}
	
	void DisplayGridCoordinateStatesToEnemy()
	{
		//dGridOwner();
		for(int i = 0; i < GridSize; i++) // Loops through display lines
		{
			xAxisBorder(i);
			for(int j = 0; j < GridSize; j++) 
				{
					if((GetGridCoordinateState(i,j)==2)||(GetGridCoordinateState(i,j)==3)){System.out.print(GetGridCoordinateState(i,j)+ "  ");}
					else{System.out.print("X  ");}
				} // Displays constituents of line from grid
				 System.out.println("");
		}
		yAxisBorder();		
	}

///////////////////////
	void dGridOwner()
	{
		System.out.println("");
		System.out.println("Grid Owner: " + GetGridOwner());		
	}
	
	void xAxisBorder(int ii)
	{
		if(ii<10){{System.out.print(" ");}}
		System.out.print(ii + "|   "); // displays line number or "X coordinate"		
	}
	
	void yAxisBorder()
	{
		for(int j = 0; j < (GridSize + 6); j++) // beneath main body of grid
		{
			if(j<6){System.out.print(" ");}
			else{System.out.print("_  ");} // print some lines
		}
			System.out.println("");
		for(int j = 0; j < (GridSize + 6); j++)
		{
			if(j<6){System.out.print(" ");}
			else if((j - 6)>9){System.out.print((j - 6)+" ");} // print grid y coordinates
			else{System.out.print((j - 6)+"  ");}
		}
			System.out.println("      ");
			System.out.println("      --------> N"); //  print North Pointer		
	}

}
