package pkgMGModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pkgMover.Mover;

/**
 * Model for Leaderboard that loads scores, sending them to LeaderboardView, and saves scores from LeaderBoardView
 * @author Ryan Peters
 *
 */
public class LeaderboardModel extends MinigameModel{
	private static final long serialVersionUID = 14L;
	final private String HIGHSCORES_PATH = "Data/highScores.csv";
	final private int MAX_NUM_SCORES = 10;
	private boolean areResultedImported = false;
	private boolean resultSaved = false;

	/**
	 * Default constructor for LeaderboardModel
	 */
	public LeaderboardModel() {}
	
	/**
	 * Per-tick handler/called every tick. On the first tick only, import results from csv file.
	 * If Submit Button from LeaderboardView is clicked, save scores to the csv file
	 * 
	 * @author Ryan Peters
	 */
	@Override
	public void update(MouseEvent me) {
		if (!areResultedImported) {
			 try {
				 createResults(readHighScores());
			 } catch (IOException e) {}
			areResultedImported = true;
		}
		
		if (me != null) {
			if (me.getEventType() == MouseEvent.MOUSE_CLICKED || me.getEventType() == MouseEvent.MOUSE_PRESSED) {
				try {
					Button b = ((Button) me.getSource());
					if (!resultSaved) {
						addNewResult(new ResultMover(b.getId()));
						resultSaved = true;
					}
					
				} catch (ClassCastException e) {} 
			}
		}
	}
	
	/**
	 * Reads in stored values (name,score) from HIGHSCORES_PATH csv into an array of Strings to be stored into ResultMovers elsewhere
	 * 
	 * @author Ryan Peters
	 * @return	list of scores
	 * @throws IOException 
	 */
	private String[] readHighScores() throws IOException{
		String[] results = new String[MAX_NUM_SCORES];
		String result;
		int i = 0;
		BufferedReader csvReader = null;
		try {
			csvReader = new BufferedReader(new FileReader(HIGHSCORES_PATH));

			while ((result = csvReader.readLine()) != null && i < MAX_NUM_SCORES) {
			    results[i] = result;
			    i++;			    
			}
			csvReader.close();
		} catch (IOException e) {
		} finally {
			csvReader.close();
		}
		
		return results;
	}
	
	
	/**
	 * Takes an array of Strings and parses it into ResultMovers, packages for data transfer from Model to View for Leaderboard.
	 * All ResultMovers are stored into an ArrayList which is then sorted by score-order (for listing on LeaderboardView)
	 * 
	 * @author Ryan Peters
	 * @param results	array of Strings[] of the type name,score
	 */
	private void createResults(String[] results) {
		ArrayList<ResultMover> rms = new ArrayList<ResultMover>();
		for (String result : results) {
			rms.add(new ResultMover(result));
		}
		Collections.sort(rms);
		movers = new ArrayList<Mover>(rms);
	}
	
	/**
	 * Writes the high scores (movers) to the HIGHSCORES_PATH csv file
	 * 
	 * @author Ryan Peters
	 * @throws IOException
	 */
	private void saveHighScores() throws IOException {
		FileWriter csvWriter = null;
		try {
			csvWriter = new FileWriter(HIGHSCORES_PATH);
			
			for (Mover m : movers) {
				ResultMover rm = (ResultMover) m;
				csvWriter.append(rm.getValue());
				csvWriter.append("\n");
			}
		} catch (IOException e) {	
		} finally {
			csvWriter.flush();
			csvWriter.close();
		}
	}
	
	/**
	 * Adds a new ResultMover to movers, sorts the movers, truncates the list to only 10, and writes to csv file.
	 * In other words, add a new result in order to the list and if its the top 10, it will be saved and written
	 * 
	 * 
	 * @author Ryan Peters
	 * @param rm	the new ResultMover to add
	 */
	private void addNewResult(ResultMover rm) {
		ArrayList<ResultMover> rms = new ArrayList<ResultMover>();
		for (Mover m : movers) {
			rms.add((ResultMover)m);
		}
		rms.add(rm);
		Collections.sort(rms);
		
		movers = new ArrayList<Mover>(rms.subList(0, MAX_NUM_SCORES));

		try {
			saveHighScores();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * Pseudo-mover that serves to transport high score data from Model to View and back
	 * The value field is set to the result = name,score
	 * 
	 * @author Ryan Peters
	 *
	 */
	public class ResultMover extends Mover implements Comparable<ResultMover>{
		private static final long serialVersionUID = 23L;
		public ResultMover(String result) {
			super(0,0,0,0,0,0,result);
		}

		public String getResultName() {
			return super.getValue().split(",")[0];
		}
		
		public int getResultScore() {
			return Integer.parseInt(super.getValue().split(",")[1]);
		}
		
		public void setResult(String name, int score) {
			super.setValue(name + "," + Integer.toString(score));
		}
		
		
		/**
		 * Order by score first, then alphabetically if there is a tie
		 * 
		 * @author Ryan Peters
		 * @param rm	the ResultMover to compare against
		 * @return	int to decide which is first/last/etc
		 */
		@Override
		public int compareTo(ResultMover rm) {
			int iCmp = Integer.compare(rm.getResultScore(), this.getResultScore());
			return (iCmp == 0) ? (this.getResultName()).compareTo(rm.getResultName()) : iCmp;
		}
	}
}
