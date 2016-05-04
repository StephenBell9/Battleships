import java.util.Scanner;

public class Player 
{
////////CLASS VARIABLES ////////
	private String PlayerName;
	private boolean Loser = false;
	private Fleet BattleFleet;
	private Grid PlayerChart;

//////// PLAYER CONSTRUCTOR	////////
	Player(String InputPlayerName, int InputFleetSize, Scanner in)
	{
		PlayerName = InputPlayerName;
		ReadyPlayer(in);
		SetPlayerChart(InputPlayerName);
		SetBattleFleet(InputFleetSize, PlayerChart, in);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); // "Clear Screen"
	}

//////// SETTERS ////////
	
	void SetPlayerLoser(boolean InputLoser)
		{Loser = InputLoser;}
	
	void SetBattleFleet(int InputFleetSize, Grid PlayerChart, Scanner in)
		{BattleFleet = new Fleet(InputFleetSize, PlayerChart, in);}
	
	void SetPlayerChart(String InputPlayerName)
		{PlayerChart = new Grid(InputPlayerName);}
		
/////// GETTERS ////////
	Grid GetPlayerChart()
		{return PlayerChart;}
	
	String GetPlayerName()
		{return PlayerName;}
	
	Fleet GetBattleFleet()
		{return BattleFleet;}
	
	boolean GetPlayerLoser()
		{return Loser;}	
	
	boolean GetFleetStatus()
		{return BattleFleet.GetIsFleetAfloat();}
	
//////// OTHER METHODS ////////
	void ReadyPlayer(Scanner in)
		{
		System.out.println("Ready Player: " + GetPlayerName());
		System.out.println("Press any number and then Enter");
		in.nextInt();
		System.out.println("Go");
		}
	
	void Attack(int InputFleetSize, Scanner in)
	{
		System.out.println("Select X coordinate of attack. "); int xX = in.nextInt(); // Get coords from user
		System.out.println("Select Y coordinate of attack. "); int yY = in.nextInt(); // Get coords from user
		
		switch (PlayerChart.GetGridCoordinateState(xX, yY))
		{
			case 0:
				{System.out.println("Miss!"); PlayerChart.SetGridCoordinateState(xX,  yY, 3); break;}
			case 1:
				{
					System.out.println("Hit!"); PlayerChart.SetGridCoordinateState(xX,  yY, 2); 
					BattleFleet.GetShipAtCoordinates(xX, yY, PlayerChart, InputFleetSize);
						if(BattleFleet.GetIsFleetAfloat() == false)
							{
								System.out.println("Fleet Sunk!"); 
								//EndGame = true;
							} 
					break;
				}
			case 2:
				{System.out.println("Ship already hit here!"); break;}
			case 3:
				{System.out.println("Already missed here!"); break;}
		}
		PlayerChart.DisplayGridCoordinateStates();
	}

}
