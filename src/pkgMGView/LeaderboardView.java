package pkgMGView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import pkgEnum.GameState;
import pkgMGModel.LeaderboardModel.ResultMover;
import pkgEnum.Game;
import pkgMover.Mover;

/**
 * The GUI view and handlers for Leaderboard. 
 * The only interactions the user has is with the TextField for inputting their name and button for submitting it
 * 
 * @author Ryan Peters
 *
 */
public class LeaderboardView extends MinigameView{
	
	//one-tick booleans
	private boolean isLeaderBoardMade = false;
	private boolean scoreSaved = false;

	private ArrayList<String> curseWords;
	private final String CW_PATH = "Data/curseWords.txt";
	
	//GUI elements
	private GridPane gp;
	private Label title;
	private TextField input;
	private Button btnSubmit;
	private Label userScore;
	
	//GUI elements attributes
	private int panePadding = 25;
	private String titleString = "LEADERBOARD";
	private String userScorePrefix = "Your Score is:\n";
	private String inputPrompt = "Enter a 3 Letter Name";
	
	
	public LeaderboardView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.LEADERBOARD);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;

		scene.addEventFilter(MouseEvent.ANY, eventHandler);
	}
	
	
	/**
	 * Handle update actions needed per tick. On the first tick only, load the results from <code>movers</code> and create the leaderboard GUI.
	 * <p>
	 * All other actions are handled through the input TextField
	 * 
	 * @author Ryan Peters
	 * @param movers	list of ResultMovers containing the top 10 high scores (to be parsed)
	 * @param gs		GameState enum from Model (ignored for LeaderboardView)
	 * @param score		cumulative score from all Minigames reflective of the user's total score
	 * @param time		amount of time (int) left (ignored for LeaderboardView)
	 */
	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		if (!isLeaderBoardMade) {//load leaderboard GUI
			makeLeaderBoard(movers, score);
			try {
				curseWords = loadCurseWords();
			} catch (IOException e) {}
			isLeaderBoardMade = true;
		}		 
	}
	
	/**
	 * Creates the GUI for Leaderboard. The GUI is a 4-column gridpane with a Label title <code>title</code> followed by
	 * two Labels per row (left being name of past scores/users, right being the score of past scores/users), and the final
	 * row consisting of a Label displaying the current user score, an input TextField for name, and a Submit button.
	 * <p>
	 * The first (0) and last (3) column are used as padding from the edges and should not contain any <code>Node</code>
	 * 
	 * @author Ryan Peters
	 * @param movers	list of ResultMovers loaded from csv file in LeaderboardModel 
	 * @param score		user's total score to be displayed in last row 
	 */
	private void makeLeaderBoard(ArrayList<Mover> movers, int score) {
		//set up gridpane
		gp = new GridPane();
		gp.setPrefHeight(backgroundHeight);
		gp.setPrefWidth(backgroundWidth);
	    gp.setPadding(new Insets(panePadding, panePadding, panePadding, panePadding));
	    gp.setVgap(panePadding/2);
	    
	    ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(20);
        gp.getColumnConstraints().addAll(col1,col2,col3,col4);
	    
	    
	    //create title
        createTitle();
	    gp.add(title, 0, 0, 4, 1);
	    GridPane.setHalignment(title, HPos.CENTER);
	    
	    //create results
	    int row = 1;
	    Label namelbl;
	    Label scorelbl;
	    for (Mover m : movers) {
	    	namelbl = createName((ResultMover) m);
	    	gp.add(namelbl, 1, row);
	    	GridPane.setHalignment(namelbl, HPos.LEFT);
	    	scorelbl = createScore((ResultMover) m);
	    	gp.add(scorelbl, 2, row);
	    	GridPane.setHalignment(scorelbl, HPos.RIGHT);
	    	row++;
	    }
	    
	    //create user score label
	    userScore = new Label(userScorePrefix + Integer.toString(score));
	    userScore.setAlignment(Pos.CENTER);
	    userScore.setWrapText(true);
	    userScore.setTextAlignment(TextAlignment.CENTER);
	    formatLeaderboardLabel(userScore, 30);
	    gp.add(userScore, 1, row);
	   
	    //create input field and submit
	    input = new TextField();
	    input.setPromptText(inputPrompt);
	    gp.add(input, 2, row);
	    btnSubmit = new Button("SUBMIT");
	    btnSubmit.setOnMouseClicked(e -> {
	    	String inputName = input.getText();
	    	if (!scoreSaved && saveScore(input.getText())) {
	    		btnSubmit.setId(inputName.toUpperCase() + "," + score);
	    		isLeaderBoardMade = false; //causes next tick to reload new leaderboard (if new highscore from user)
	    		gp.getChildren().clear();
	    		me = e;
	    	}
	    });
	    gp.add(btnSubmit, 3, row);
	    
	    
	    
	    root.getChildren().add(gp);
	}
	
	/**
	 * Creates the Label (title) for the Leaderboard GUI with specific formatting saving it to the attribute <code>title</code>
	 * 
	 * @author Ryan Peters
	 */
	private void createTitle() {
		title = new Label(titleString);
		title.setTextAlignment(TextAlignment.CENTER);
		title.setAlignment(Pos.CENTER);
		formatLeaderboardLabel(title, 40);
	}
	
	/**
	 * Creates the Label for the name of past user's on the high score list aligned to the left side
	 * 
	 * @author Ryan Peters
	 * @param rm	the <code>ResultMover</code>
	 * @return		the formatting Label to be inserted into the GUI
	 */
	private Label createName(ResultMover rm) {
		Label lbl = new Label(rm.getResultName());
		lbl.setTextAlignment(TextAlignment.LEFT);
		lbl.setAlignment(Pos.BASELINE_LEFT);
		formatLeaderboardLabel(lbl, 20);
		return lbl;
	}
	
	/**
	 * Creates the label for the score ofthe past user's on the high score list aligned to the right side
	 * 
	 * @author		Ryan Peters
	 * @param rm	the <code>ResultMover</code>
	 * @return		the formatting Label to be inserted into the GUI
	 */
	private Label createScore(ResultMover rm) {
		Label lbl = new Label(Integer.toString(rm.getResultScore()));
		lbl.setTextAlignment(TextAlignment.RIGHT);
		lbl.setAlignment(Pos.BASELINE_RIGHT);
		formatLeaderboardLabel(lbl, 20);
		return lbl;
	}
	
	/**
	 * Formats the input Label with a preset style consistent throughout the Leaderboard GUI with the ability to set font size
	 * 
	 * @author 			Ryan Peters
	 * @param lbl		Label to be formatted
	 * @param fontSize	size of font to be set for the Label
	 */
	private void formatLeaderboardLabel(Label lbl, int fontSize) {
		lbl.setStyle(" -fx-font-weight: bold; -fx-font-size: " + Integer.toString(fontSize));
	}
	
	/**
	 * Attempts to save the score and 3-letter name the user has input, returning true if the name was accepted, false if rejected.
	 * Catches exceptions from <code>parseName(name)</code> that reflect a bad (described more in parseName() ) name and set's the 
	 * input TextField with an appropriate message to the type of bad name.
	 * 
	 *  @author 	Ryan Peters
	 *  @returns	true if score saved, false if not (dependent on whether user name passes tests)
	 */
	private boolean saveScore(String name) {

		try {
			if (checkName(name)) {
				input.setText("Name and Score saved!");
				scoreSaved = true;
				return true;
			}
		} catch (LongerThan3Exception e) {
			input.setText("");
			input.setPromptText("Can only be 3 Letters");
		} catch (IsCurseWordException e) {
			input.setText("");
			input.setPromptText("Appropriate names only");
		} catch (IsNotLetterException e) {
			input.setText("");
			input.setPromptText("Must be only letters");
		} catch (ScoreAlreadySavedException e) {
			input.setText("");
			input.setPromptText("Score already saved!");
		}
		
		return false;
	}
	
	/**
	 * Determines whether the input String name from the user is a valid name according to the following specifications:
	 * 		-the name has not already been saved into results
	 * 		-the name is 3 long
	 * 		-the name is not a known curse word (from curseWords.txt)
	 * 		-the name is all letters (A-Z,a-z)
	 * If the name violates any of these specifications, a relative custom exception is thrown where it is caught and handled by saveScore()
	 * 		
	 * @author 		Ryan Peters
	 * @param name	user input name from TextField
	 * @return		true if name is accepted, false is name violates a specification
	 * @throws LongerThan3Exception
	 * @throws IsCurseWordException
	 * @throws IsAlreadyNameException
	 * @throws IsNotLetterException
	 * @throws ScoreAlreadySavedException
	 */
	private boolean checkName(String name) throws LongerThan3Exception, IsCurseWordException, IsNotLetterException, ScoreAlreadySavedException {
		
		if (scoreSaved) {
			throw new ScoreAlreadySavedException();
		}
		
		if (name.length() == 3) {
			throw new LongerThan3Exception();
		}
		
		for (String w : curseWords) {
			if (name.toUpperCase().equals(w.toUpperCase())) {
				throw new IsCurseWordException();
			} 
		}
		
		for (int i = 0; i < name.length(); i++) {
			if ((int)name.charAt(i) < 65 || (int)name.charAt(i) > 122) {
				throw new IsNotLetterException();
			}
		}
		
		return true;
	}
	
	/**
	 * Loads the list of 3-letter (possible) "curse words" into an ArrayList. 
	 * 
	 * @author 		Ryan Peters
	 * @return		list of curse words
	 * @throws IOException
	 */
	private ArrayList<String> loadCurseWords() throws IOException{
		File file = new File(CW_PATH); 
		BufferedReader br = null;
		ArrayList<String> words = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(file)); 
			String w; 
			while ((w = br.readLine()) != null)  {
				words.add(w);
			}
		} catch (IOException e) {
		} finally {
			br.close();
		}
		return words;
	}
	
	private class LongerThan3Exception extends Exception {}
	private class IsCurseWordException extends Exception {}
	private class IsNotLetterException extends Exception {}
	private class ScoreAlreadySavedException extends Exception {}
	

	//unused inherited methods from MinigameView
	void startTimer(int ms) {}
	void stopTimer() {}
	void setUpListeners() {}
	void importImages() {}
	void drawTutorial(int step) {}
	void updateTutorialStep(MouseEvent me) {}

}
