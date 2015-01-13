package storage;

import java.util.Comparator;

public class CustomComparator implements Comparator<HighScore>{

	private SortParameter parameter;
	
	public CustomComparator() {
		parameter = SortParameter.SCORE;
	}
	
	public CustomComparator(SortParameter parameter) {
		this.parameter = parameter; 
	}
	
	@Override
	public int compare(HighScore arg0, HighScore other) {
		switch(this.parameter) {
		case SCORE:
			if(arg0.getScore() ==  other.getScore()) {
				return 0;
			} else if(arg0.getScore() > other.getScore()) {
				return 1;
			} else {
				return -1;
			}
		case TIMESTAMP:
			return arg0.getTimestamp().compareTo(other.getTimestamp());
		default:
			if(arg0.getScore() ==  other.getScore()) {
				return 0;
			} else if(arg0.getScore() > other.getScore()) {
				return 1;
			} else {
				return -1;
			}
		}
	}

}
