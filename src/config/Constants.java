package config;

import java.io.File;

public class Constants {
	
	public static String PATH_FOR_SCORES = "src" + File.separator + "scores.txt";
	public static String PATH_FOR_HIGH_SCORE = "src" + File.separator + "highScore.txt";
	
	public static String GAME_TITLE = "2048";
	
	public static int NUM_ROWS = 4;
	public static int NUM_COLUMNS = 4;
	public static int MAX_ROW = NUM_ROWS - 1;
	public static int MAX_COLUMN = NUM_COLUMNS - 1;
	
	public static int TILE_FONT_LARGE = 60;
	public static int TILE_FONT_SMALL = 40;
	public static String TILE_FONT = "Arial";

}
