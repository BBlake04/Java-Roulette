
public class Player implements CreatePlayer{
	
	String playerName, casinoName;
	
	public Player(){
		
	}
	
	@Override
	public void setName(String name) {
		this.playerName = name;
	}

	@Override
	public String getName() {
		return playerName;
	}

	@Override
	public void setNickname(String name) {
		this.casinoName = name;
	}

	@Override
	public String getNickname() {
		return casinoName;
	}

}
