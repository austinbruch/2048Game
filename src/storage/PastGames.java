package storage;

import java.util.ArrayList;
import java.util.Collections;

public class PastGames extends ArrayList<HighScore> {
	
	private static final long serialVersionUID = 1L;

	public PastGames() {
		
	}
	
	public void addScore(HighScore score) {
		this.add(score);
		sort();
	}
	
	public void removeScore(HighScore score) {
		this.remove(score);
		sort();
	}
	
	private void sort() {
		Collections.sort(this, new CustomComparator());
	}
	
	private void sort(SortParameter parameter, boolean ascending) {
		Collections.sort(this, new CustomComparator(parameter));
		if(!ascending) {
			Collections.reverse(this);
		}
	}
}
