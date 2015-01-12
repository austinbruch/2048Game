package logic;

import java.sql.Timestamp;

public class HighScore {
	
	private int score;
	private Timestamp timestamp;
	private int largestTile;
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public int getLargestTile() {
		return largestTile;
	}
	public void setLargestTile(int largestTile) {
		this.largestTile = largestTile;
	}

}
