package logic;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import ui.GameFrame;
import util.Pair;

public class Game {

	private GameBoard gameBoard;
	private GameFrame gameFrame;
	private static int best;
	private int score;
	private boolean valid;
	private static HighScore highScore;

	public Game() {
		this.score = 0;
		this.valid = true;
	}

	public void startNewGame() {
		this.score = 0;
		this.valid = true;
		gameBoard = new GameBoard();
		gameFrame = new GameFrame(this);
		gameFrame.run();
		
//		int value = 2;
//		
//		for(int i = 0; i < 4; i++) {
//			for(int j = 0; j < 4; j++) {
//				this.gameBoard.setValueAtPosition(i, j, value);
//				value *= 2;
//			}
//		}
		
//		this.gameBoard.setValueAtPosition(0, 0, 4);
//		this.gameBoard.setValueAtPosition(0, 1, 2);
//		this.gameBoard.setValueAtPosition(0, 2, 4);
//		this.gameBoard.setValueAtPosition(0, 3, 4);
//		
//		this.gameBoard.setValueAtPosition(1, 0, 32);
//		this.gameBoard.setValueAtPosition(1, 1, 1024);
//		this.gameBoard.setValueAtPosition(1, 2, 2048);
//		this.gameBoard.setValueAtPosition(1, 3, 4096);
//		
//		this.gameBoard.setValueAtPosition(2, 0, 2);
//		this.gameBoard.setValueAtPosition(2, 1, 4);
//		this.gameBoard.setValueAtPosition(2, 2, 2);
//		this.gameBoard.setValueAtPosition(2, 3, 4);
//		
//		this.gameBoard.setValueAtPosition(3, 0, 4);
//		this.gameBoard.setValueAtPosition(3, 1, 2);
//		this.gameBoard.setValueAtPosition(3, 2, 4);
//		this.gameBoard.setValueAtPosition(3, 3, 2);
//		
		
		this.addNewRandomTile();
		this.addNewRandomTile();
		updateUI();
	}

	public void move(Move direction) {

		if(this.valid) { // this checks if there are any available moves
			// TODO: still need to check if the selected move is actually valid or not
			switch(direction) {
			case LEFT:
				moveLeft();
				break;
			case RIGHT:
				moveRight();
				break;
			case UP:
				moveUp();
				break;
			case DOWN:
				moveDown();
				break;
			}

			addNewRandomTile();

			updateUI();

			if(!existNextMove()) {
				endGame();
			}
		}
	}


	private void moveLeft() {

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

	}

	private void moveRight() {

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

	}

	private void moveUp() {

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

	}

	private void moveDown() {

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

		if(empties.size() > 0) {
			int pair = random.nextInt(empties.size());
			Pair<Integer, Integer> position = empties.get(pair);
			
			this.gameBoard.setValueAtPosition(position.getFirst(), position.getSecond(), value);
		}

	}

	private void updateUI() {
		gameFrame.updateUI(gameBoard);
		gameFrame.setScore(this.score);
		if(this.score > best) {
			best = this.score;
			gameFrame.setBest(best);
		}
	}

	private boolean existNextMove() {

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				int current = gameBoard.getValueAtPosition(i, j);
				if(current == 0) { // if there are any blank spaces, then we know there is at least 1 valid move remaining
					return true;
				}

				try {
					if(gameBoard.getValueAtPosition(i-1, j) == current) {
						// if the cell above the current cell has the same value, we are good
						return true;
					}
				} catch (IndexOutOfBoundsException e) {
					// eat this
				}

				try {
					if(gameBoard.getValueAtPosition(i+1, j) == current) {
						// if the cell below the current cell has the same value, we are good
						return true;
					}
				} catch (IndexOutOfBoundsException e) {
					// eat this
				}

				try {
					if(gameBoard.getValueAtPosition(i, j-1) == current) {
						// if the cell to the left of the current cell has the same value, we are good
						return true;
					}
				} catch (IndexOutOfBoundsException e) {
					// eat this
				}

				try {
					if(gameBoard.getValueAtPosition(i, j+1) == current) {
						// if the cell to the right of the current cell has the same value, we are good
						return true;
					}
				} catch (IndexOutOfBoundsException e) {
					// eat this
				}
			}
		}
		
		return false;
	}

	private void endGame() {
		// when we end the game, brick the board so moves no longer work
		this.valid = false;
		gameFrame.endGameDialog();
	}
	
	private void saveHighScore() {
		HighScore high = new HighScore();
		high.setScore(this.score);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		high.setTimestamp(now);
		
//		File highScoreFile = new File("/highScore.txt");
		
	}
	
	private void loadHighScore() {
		
	}

	public static void main(String... args) {
		Game game = new Game();
		game.startNewGame();
	}
}