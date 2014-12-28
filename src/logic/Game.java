package logic;

import java.util.ArrayList;
import java.util.Random;

import ui.GameFrame;
import util.Pair;

public class Game {

	private GameBoard gameBoard;
	private GameFrame gameFrame;
	private static int best;
	private int score;
	
	public Game() {
		this.score = 0;
	}

	public void startNewGame() {
		this.score = 0;
		gameBoard = new GameBoard();
		gameFrame = new GameFrame(this);
		gameFrame.run();
		//		gameBoard.setValueAtPosition(1, 0, 2);
		//		gameBoard.setValueAtPosition(1, 1, 2);
		gameBoard.setValueAtPosition(1, 2, 2);
		gameBoard.setValueAtPosition(1, 3, 2);
		gameBoard.setValueAtPosition(2, 2, 2);
		gameBoard.setValueAtPosition(2, 3, 2);
		updateUI();
	}


	public void moveLeft() {

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 0; j < 3; j++) { // shift from right to left
				for(int k = j; k >= 0; k--) {
					if(gameBoard.getValueAtPosition(i, k) == 0) {
						gameBoard.setValueAtPosition(i, k, gameBoard.getValueAtPosition(i, k+1));
						gameBoard.setValueAtPosition(i, k+1, 0);
					}
				}
			}
		}

		// each row is independent
		for(int i = 0; i < 4; i++) { // each row
			for(int j = 0; j < 3; j++) { // right to left
				if(gameBoard.getValueAtPosition(i, j) == gameBoard.getValueAtPosition(i, j+1)) {
					gameBoard.setValueAtPosition(i, j, gameBoard.getValueAtPosition(i, j) * 2);
					
					// increment the score with the value of the new square created
					this.score += gameBoard.getValueAtPosition(i, j);
					
					gameBoard.setValueAtPosition(i, j+1, 0);
				}
			}
		}

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 0; j < 3; j++) { // shift from right to left
				for(int k = j; k >= 0; k--) {
					if(gameBoard.getValueAtPosition(i, k) == 0) {
						gameBoard.setValueAtPosition(i, k, gameBoard.getValueAtPosition(i, k+1));
						gameBoard.setValueAtPosition(i, k+1, 0);
					}
				}
			}
		}

		addNewRandomTile();
	}

	public void moveRight() {

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 3; j > 0; j--) { // shift from left to right
				for(int k = j; k <= 3; k++) {
					if(gameBoard.getValueAtPosition(i, k) == 0) {
						gameBoard.setValueAtPosition(i, k, gameBoard.getValueAtPosition(i, k-1));
						gameBoard.setValueAtPosition(i, k-1, 0);
					}
				}
			}
		}

		// each row is independent
		for(int i = 0; i < 4; i++) { // each row
			for(int j = 3; j > 0; j--) { // left to right
				if(gameBoard.getValueAtPosition(i, j) == gameBoard.getValueAtPosition(i, j-1)) {
					gameBoard.setValueAtPosition(i, j, gameBoard.getValueAtPosition(i, j) * 2);
					
					// increment the score with the value of the new square created
					this.score += gameBoard.getValueAtPosition(i, j);
					
					gameBoard.setValueAtPosition(i, j-1, 0);
				}
			}
		}

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 3; j > 0; j--) { // shift from left to right
				for(int k = j; k <= 3; k++) {
					if(gameBoard.getValueAtPosition(i, k) == 0) {
						gameBoard.setValueAtPosition(i, k, gameBoard.getValueAtPosition(i, k-1));
						gameBoard.setValueAtPosition(i, k-1, 0);
					}
				}
			}
		}

		addNewRandomTile();
	}

	public void moveUp() {

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 0; j < 3; j++) { // shift from right to left
				for(int k = j; k >= 0; k--) {
					if(gameBoard.getValueAtPosition(k, i) == 0) {
						gameBoard.setValueAtPosition(k, i, gameBoard.getValueAtPosition(k+1, i));
						gameBoard.setValueAtPosition(k+1, i, 0);
					}
				}
			}
		}

		// each column is independent
		for(int i = 0; i < 4; i++) { // each column
			for(int j = 0; j < 3; j++) { // top to bottom
				if(gameBoard.getValueAtPosition(j, i) == gameBoard.getValueAtPosition(j+1, i)) {
					gameBoard.setValueAtPosition(j, i, gameBoard.getValueAtPosition(j, i) * 2);
					
					// increment the score with the value of the new square created
					this.score += gameBoard.getValueAtPosition(j, i);
					
					gameBoard.setValueAtPosition(j+1, i, 0);
				}
			}
		}

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 0; j < 3; j++) { // shift from right to left
				for(int k = j; k >= 0; k--) {
					if(gameBoard.getValueAtPosition(k, i) == 0) {
						gameBoard.setValueAtPosition(k, i, gameBoard.getValueAtPosition(k+1, i));
						gameBoard.setValueAtPosition(k+1, i, 0);
					}
				}
			}
		}

		addNewRandomTile();	
	}

	public void moveDown() {

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 3; j > 0; j--) { // shift from left to right
				for(int k = j; k <= 3; k++) {
					if(gameBoard.getValueAtPosition(k, i) == 0) {
						gameBoard.setValueAtPosition(k, i, gameBoard.getValueAtPosition(k-1, i));
						gameBoard.setValueAtPosition(k-1, i, 0);
					}
				}
			}
		}

		// each column is independent
		for(int i = 0; i < 4; i++) { // each column
			for(int j = 3; j > 0; j--) { // bottom to top
				if(gameBoard.getValueAtPosition(j, i) == gameBoard.getValueAtPosition(j-1, i)) {
					gameBoard.setValueAtPosition(j, i, gameBoard.getValueAtPosition(j, i) * 2);
					
					// increment the score with the value of the new square created
					this.score += gameBoard.getValueAtPosition(j, i);
					
					gameBoard.setValueAtPosition(j-1, i, 0);
				}
			}
		}

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 3; j > 0; j--) { // shift from left to right
				for(int k = j; k <= 3; k++) {
					if(gameBoard.getValueAtPosition(k, i) == 0) {
						gameBoard.setValueAtPosition(k, i, gameBoard.getValueAtPosition(k-1, i));
						gameBoard.setValueAtPosition(k-1, i, 0);
					}
				}
			}
		}

		addNewRandomTile();	
	}

	private void addNewRandomTile() {
		int value;
		ArrayList<Pair<Integer,Integer>> empties = new ArrayList<Pair<Integer,Integer>>();

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(this.gameBoard.getValueAtPosition(i, j) == 0) {
					empties.add(new Pair<Integer,Integer>(new Integer(i), new Integer(j)));
				}
			}
		}

		Random random = new Random();
		int twoOrFour = random.nextInt(10) + 1;

		if(twoOrFour == 1) {
			value = 4;
		} else {
			value = 2;
		}

		int pair = random.nextInt(empties.size());
		Pair<Integer, Integer> position = empties.get(pair);

		this.gameBoard.setValueAtPosition(position.getFirst(), position.getSecond(), value);

		updateUI();
	}

	private void updateUI() {
		gameFrame.updateUI(gameBoard);
		gameFrame.setScore(this.score);
		if(this.score > best) {
			best = this.score;
			gameFrame.setBest(best);
		}
	}

	public static void main(String... args) {
		Game game = new Game();
		game.startNewGame();
	}
}