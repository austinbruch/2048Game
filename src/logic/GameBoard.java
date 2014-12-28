package logic;

public class GameBoard {

	private int[][] boardMatrix;
	
	public GameBoard() {
		boardMatrix = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				boardMatrix[i][j] = 0;
			}
		}
	}
	
	public void setValueAtPosition(int row, int column, int value) {
		if(row < 4 && row >= 0) {
			if(column < 4 && column >= 0) {
				boardMatrix[row][column] = value;
			} else {
				System.out.println("Column value " + column + " is out of range.");
			}
		} else {
			System.out.println("Row value " + row + " is out of range.");
		}
	}
	
	public int getValueAtPosition(int row, int column) {
		if(row < 4 && row >= 0) {
			if(column < 4 && column >= 0) {
				return boardMatrix[row][column];
			} else {
				System.out.println("Column value " + column + " is out of range.");
			}
		} else {
			System.out.println("Row value " + row + " is out of range.");
		}
		return -1;
	}
	
	public String toString() {
		String toReturn  = "";
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				toReturn += Integer.toString(boardMatrix[i][j]) + "\t"; 
			}
			toReturn += "\n";
		}
		
		return toReturn;
	}
	
	public static void main(String... args) {
		GameBoard gb = new GameBoard();
		
		System.out.println(gb);
		
		gb.setValueAtPosition(1, 1, 1024);
		gb.setValueAtPosition(3, 3, 4);
		
		System.out.println(gb);
		
	}
}
