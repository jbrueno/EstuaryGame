package pkgEnum;


public enum Game {
	
	MAINSCREEN(0), ANIMALMATCHING(0), HSCCOUNT(30), SIDESCROLLER(0), WATERSAMPLING(0), LEADERBOARD(0);
	
	private int time;
	
	public int getTime() {
		return time;
	}
	
	private Game(int time) {
		this.time = time;
	}

}
