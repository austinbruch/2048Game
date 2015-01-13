package storage;

import java.io.Serializable;
import java.sql.Timestamp;

public class HighScore implements Serializable{

	private static final long serialVersionUID = 1L;

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
