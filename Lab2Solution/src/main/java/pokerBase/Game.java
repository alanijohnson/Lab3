package pokerBase;

import java.util.ArrayList;
import java.util.UUID;

public class Game {
	
	private UUID GameID;
	private ArrayList<Player> GamePlayers = new ArrayList<Player>();
	private UUID TableID;	
		
	
	public Game()
	{
		GameID = UUID.randomUUID();
	}


	public UUID getGameID() {
		return GameID;
	}


	public void setGameID(UUID gameID) {
		GameID = gameID;
	}


	public ArrayList<Player> getGamePlayers() {
		return GamePlayers;
	}


	public void setGamePlayers(ArrayList<Player> gamePlayers) {
		GamePlayers = gamePlayers;
	}


	public UUID getTableID() {
		return TableID;
	}


	public void setTableID(UUID tableID) {
		TableID = tableID;
	}
	
	public void AddPlayerToGame(Table table, Player player){
		GamePlayers.add(player);
		table.AddPlayerToTable(player);
	}
	
	
}