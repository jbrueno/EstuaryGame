package pkgMGModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pkgMover.Mover;

public class LeaderboardModel extends MinigameModel{
	
	final private String HIGHSCORES_PATH = "Data/highScores.csv";
	final private int MAX_NUM_SCORES = 10;
	private boolean areResultedImported = false;
	private boolean resultSaved = false;

	public LeaderboardModel() {
		 movers = new ArrayList<Mover>();
	}
	
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
	 * Reads in stored CSV's 
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
	
	private void createResults(String[] results) {
		ArrayList<ResultMover> rms = new ArrayList<ResultMover>();
		for (String result : results) {
			rms.add(new ResultMover(result));
		}
		Collections.sort(rms);
		movers = new ArrayList<Mover>(rms);
	}
	
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
	
	private void addNewResult(ResultMover rm) {
		ArrayList<ResultMover> rms = new ArrayList<ResultMover>();
		for (Mover m : movers) {
			rms.add((ResultMover)m);
		}
		rms.add(rm);
		Collections.sort(rms);
		
		movers = new ArrayList<Mover>(rms.subList(0, 10));

		try {
			saveHighScores();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * Pseudo-mover that serves to transport high score data from Model to View only
	 * 
	 * @author Ryan Peters
	 *
	 */
	public class ResultMover extends Mover implements Comparable<ResultMover>{
		public ResultMover(String result) {
			super(0,0,0,0,0,0,result);
		}

		public String getResultName() {
			return super.getValue().split(",")[0];
		}
		
		public int getResultScore() {
			System.out.println(super.getValue());
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
