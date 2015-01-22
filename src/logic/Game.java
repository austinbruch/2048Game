package logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import storage.HighScore;
import storage.PastGames;
import ui.GameFrame;
import util.Pair;
import config.Constants;

public class Game {

	private GameBoard gameBoard;
	private GameFrame gameFrame;
	private int score;
	private int moves;
	private boolean valid;
	private static HighScore highScore;
	private static PastGames pastGames;

	public Game() {
		this.score = 0;
		this.moves = 0;
		this.valid = true;
		highScore = new HighScore();
	}

	public void startNewGame() {
		this.score = 0;
		this.moves = 0;
		this.valid = true;
		gameBoard = new GameBoard();
		gameFrame = new GameFrame(this);
		loadHighScore();
		gameFrame.run();

		//		int value = 2;
		//
		//		for(int i = 2; i < 4; i++) {
		//			for(int j = 0; j < 4; j++) {
		//				this.gameBoard.setValueAtPosition(i, j, value);
		//				value *= 2;
		//			}
		//		}

		//				this.gameBoard.setValueAtPosition(0, 0, 4);
		//				this.gameBoard.setValueAtPosition(0, 1, 2);
		//				this.gameBoard.setValueAtPosition(0, 2, 4);
		//				this.gameBoard.setValueAtPosition(0, 3, 4);
		//				
		//				this.gameBoard.setValueAtPosition(1, 0, 32);
		//				this.gameBoard.setValueAtPosition(1, 1, 1024);
		//				this.gameBoard.setValueAtPosition(1, 2, 2048);
		//				this.gameBoard.setValueAtPosition(1, 3, 512);
		//				
		//				this.gameBoard.setValueAtPosition(2, 0, 0);
		//				this.gameBoard.setValueAtPosition(2, 1, 0);
		//				this.gameBoard.setValueAtPosition(2, 2, 0);
		//				this.gameBoard.setValueAtPosition(2, 3, 0);
		//				
		//				this.gameBoard.setValueAtPosition(3, 0, 0);
		//				this.gameBoard.setValueAtPosition(3, 1, 0);
		//				this.gameBoard.setValueAtPosition(3, 2, 0);
		//				this.gameBoard.setValueAtPosition(3, 3, 0);


		this.addNewRandomTile();
		this.addNewRandomTile();
		updateUI();
	}

	public void move(Move direction) {


		if(this.valid) { // this checks if there are any available moves
			boolean moved = false; // see if the selected move actually made any tiles move

			switch(direction) {
			case LEFT:
				moved = moveLeft();
				break;
			case RIGHT:
				moved = moveRight();
				break;
			case UP:
				moved = moveUp();
				break;
			case DOWN:
				moved = moveDown();
				break;
			}

			if(moved) { // if any tiles moved, we need to add a new tile and update the UI 
				this.moves++;
				addNewRandomTile();
				updateUI();
			}

			if(!existNextMove()) {
				endGame(false);
			}
		}
	}

	private boolean moveLeft() {

		boolean moved = false;

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 0; j < 3; j++) { // shift from right to left
				for(int k = j; k >= 0; k--) {
					if(gameBoard.getValueAtPosition(i, k) == 0) {
						if(gameBoard.getValueAtPosition(i, k+1) != 0) {
							moved = true;
						}
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
					if(gameBoard.getValueAtPosition(i, j) != 0) {
						moved = true;
					}

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
						//						if(gameBoard.getValueAtPosition(i, k+1) != 0) {
						//							moved = true;
						//						}
						gameBoard.setValueAtPosition(i, k, gameBoard.getValueAtPosition(i, k+1));
						gameBoard.setValueAtPosition(i, k+1, 0);
					}
				}
			}
		}

		return moved;
	}

	private boolean moveRight() {

		boolean moved = false;

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 3; j > 0; j--) { // shift from left to right
				for(int k = j; k <= 3; k++) {
					if(gameBoard.getValueAtPosition(i, k) == 0) {
						if(gameBoard.getValueAtPosition(i, k-1) != 0) {
							moved = true;
						}
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
					if(gameBoard.getValueAtPosition(i, j) != 0) {
						moved = true;
					}

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
						//						if(gameBoard.getValueAtPosition(i, k-1) != 0) {
						//							moved = true;
						//						}
						gameBoard.setValueAtPosition(i, k, gameBoard.getValueAtPosition(i, k-1));
						gameBoard.setValueAtPosition(i, k-1, 0);
					}
				}
			}
		}

		return moved;

	}

	private boolean moveUp() {

		boolean moved = false;

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 0; j < 3; j++) { // shift from right to left
				for(int k = j; k >= 0; k--) {
					if(gameBoard.getValueAtPosition(k, i) == 0) {
						if(gameBoard.getValueAtPosition(k+1, i) != 0) {
							moved = true;
						}
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
					if(gameBoard.getValueAtPosition(j, i) != 0) {
						moved = true;
					}

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
						//						if(gameBoard.getValueAtPosition(k+1, i) != 0) {
						//							moved = true;
						//						}
						gameBoard.setValueAtPosition(k, i, gameBoard.getValueAtPosition(k+1, i));
						gameBoard.setValueAtPosition(k+1, i, 0);
					}
				}
			}
		}

		return moved;

	}

	private boolean moveDown() {

		boolean moved = false;

		for(int i = 0; i < 4; i++) { // each row
			for(int j = 3; j > 0; j--) { // shift from left to right
				for(int k = j; k <= 3; k++) {
					if(gameBoard.getValueAtPosition(k, i) == 0) {
						if(gameBoard.getValueAtPosition(k-1, i) != 0) {
							moved = true;
						}
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

					if(gameBoard.getValueAtPosition(j, i) != 0) {
						moved = true;
					}

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
						//						if(gameBoard.getValueAtPosition(k-1, i) != 0) {
						//							moved = true;
						//						}
						gameBoard.setValueAtPosition(k, i, gameBoard.getValueAtPosition(k-1, i));
						gameBoard.setValueAtPosition(k-1, i, 0);
					}
				}
			}
		}

		return moved;

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
		if(this.score > highScore.getScore()) {
			highScore.setScore(this.score);
		}
		gameFrame.setMoves(this.moves);
		gameFrame.setScore(this.score);
		gameFrame.setBest(highScore.getScore());
	}

	private boolean existNextMove() {

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				int current = gameBoard.getValueAtPosition(i, j);
				if(current == 0) { // if there are any blank spaces, then we know there is at least 1 valid move remaining
					return true;
				}

				if(i != 0) {
					if(gameBoard.getValueAtPosition(i-1, j) == current) {
						// if the cell above the current cell has the same value, we are good
						return true;
					}
				}

				if(i != 3) {
					if(gameBoard.getValueAtPosition(i+1, j) == current) {
						// if the cell below the current cell has the same value, we are good
						return true;
					}
				}

				if(j != 0) {
					if(gameBoard.getValueAtPosition(i, j-1) == current) {
						// if the cell to the left of the current cell has the same value, we are good
						return true;
					}
				}

				if(j != 3) {
					if(gameBoard.getValueAtPosition(i, j+1) == current) {
						// if the cell to the right of the current cell has the same value, we are good
						return true;
					}
				}
			}
		}

		return false;
	}

	public void endGame(boolean userQuit) {
		//		System.out.println("endGame entry");
		// when we end the game, brick the board so moves no longer work
		this.valid = false;

		if(this.score > highScore.getScore()) {
			highScore.setScore(this.score);
		}
		saveHighScore();
		if(!userQuit) {
			gameFrame.endGameDialog();
		}

		//		System.out.println("endGame exit");
	}

	private void saveHighScore() {

		//		System.out.println("saveHighScore entry");

		Timestamp now = new Timestamp(System.currentTimeMillis());
		highScore.setTimestamp(now);

		try
		{
			FileOutputStream fileOut = new FileOutputStream(Constants.pathForHighScore);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			System.out.println("High Score: " + Integer.toString(highScore.getScore()));
			out.writeObject(highScore);

			out.close();
			fileOut.close();
		}
		catch(IOException i)
		{
			i.printStackTrace();
		}

		//		System.out.println("saveHighScore exit");
	}

	private void loadHighScore() {
		//		System.out.println("loadHighScore entry");
		try
		{

			FileInputStream fileIn = new FileInputStream(Constants.pathForHighScore);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			highScore = (HighScore) in.readObject();
			in.close();
			fileIn.close();
		}
		catch(IOException i)
		{
			i.printStackTrace();
		}
		catch(ClassNotFoundException c)
		{
			c.printStackTrace();
		}

		//		System.out.println("loadHighScore exit");
	}

	public static void main(String... args) {
		Game game = new Game();
		game.startNewGame();
	}
}