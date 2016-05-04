import java.util.Scanner;
public class Game 
{
//////// CLASS VARIABLES ////////
	private int NoOfPlayers;
	private int MaxTurns = 288;
	private Player[] Player_;
	
//////// CONSTRUCTOR ////////
	Game(int InputNo)
	{
		Scanner in = new Scanner(System.in);
		NoOfPlayers = InputNo;
		Player_ = new Player[InputNo + 1];
		SetUpGame(in);
		StartGame(in);
		in.close();
	}

///////// SETTERS ////////

//////// GETTERS ////////
	int GetNoOfPlayers()
		{return NoOfPlayers;}
	
	int GetMaxTurns()
		{return MaxTurns;}

//////// OTHER METHODS ////////
	void SetUpGame(Scanner in)
		{
			System.out.println("");
			Player_[1] = new Player("Stephen", 2, in); // set up players
			Player_[2] = new Player("Michael", 2, in);
			
			if(!Player_[2].GetFleetStatus())
			{Player_[2].SetPlayerLoser(true);}
			
			if(!Player_[1].GetFleetStatus())
			{Player_[1].SetPlayerLoser(true);}
		}
	
	void StartGame(Scanner in)
		{
			for(int ttt = 0; ttt < MaxTurns; ttt++)
				{
					if(ttt%2==0)
						{
							//System.out.println("Player 1's turn");
							Player_[1].ReadyPlayer(in);
							Player_[2].GetPlayerChart().DisplayGridCoordinateStatesToEnemy();
							Player_[2].Attack(Player_[1].GetBattleFleet().GetFleetSize(), in);
						}
					else
						{
							//System.out.println("Player 2's turn");
							Player_[2].ReadyPlayer(in);
							Player_[1].GetPlayerChart().DisplayGridCoordinateStatesToEnemy();
							Player_[1].Attack(Player_[2].GetBattleFleet().GetFleetSize(), in);
						}			
				}
		}
	
}
