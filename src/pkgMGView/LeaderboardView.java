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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import pkgEnum.GameState;
import pkgMGModel.LeaderboardModel;
import pkgMGModel.LeaderboardModel.ResultMover;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public class LeaderboardView extends MinigameView{
	
	private boolean isLeaderBoardMade = false;
	private ArrayList<Mover> mvrs;
	private ArrayList<String> curseWords;
	private final String CW_PATH = "Data/curseWords.txt";
	private int score;
	private boolean scoreSaved = false;
	
	private GridPane gp;
	private Label title;
	private TextField input;
	private Button btnSubmit;
	private Label userScore;
	
	
	private int panePadding = 25;
	private String titleString = "LEADERBOARD";
	private final String USER_SCORE_PREFIX = "Your Score is:\n";
	private String inputPrompt = "Enter a 3 Letter Name";
	
	
	public LeaderboardView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.LEADERBOARD);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;

		scene.addEventFilter(MouseEvent.ANY, eventHandler);
	}
	
	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		mvrs = movers;
		this.score = score;
		if (!isLeaderBoardMade) {
			makeLeaderBoard(movers, score);
			try {
				curseWords = loadCurseWords();
			} catch (IOException e) {}
			isLeaderBoardMade = true;
		}
		if (me != null && me.getEventType() == MouseEvent.MOUSE_CLICKED) {
			System.out.println(me.getSource());
		}
		 
	}
	
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
	    gp.add(createTitle(), 0, 0, 4, 1);
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
	    userScore = new Label(USER_SCORE_PREFIX + Integer.toString(score));
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
	    btnSubmit.setId("SUBMIT");
	    btnSubmit.setOnMouseClicked(e -> {
	    	me = e;
	    	String inputName = input.getText();
	    	if (!scoreSaved && saveScore(input.getText())) {
	    		System.out.println("SAVING SCORE: " + input.getText());
	    		btnSubmit.setId(inputName.toUpperCase() + "," + score);
	    	}
	    });
	    gp.add(btnSubmit, 3, row);
	    
	    
	    
	    root.getChildren().add(gp);
	}
	
	private Label createTitle() {
		title = new Label(titleString);
		title.setTextAlignment(TextAlignment.CENTER);
		title.setAlignment(Pos.CENTER);
		formatLeaderboardLabel(title, 40);
		
		return title;
	}
	
	private Label createName(ResultMover rm) {
		Label lbl = new Label(rm.getResultName());
		lbl.setTextAlignment(TextAlignment.LEFT);
		lbl.setAlignment(Pos.BASELINE_LEFT);
		formatLeaderboardLabel(lbl, 20);
		return lbl;
	}
	
	private Label createScore(ResultMover rm) {
		Label lbl = new Label(Integer.toString(rm.getResultScore()));
		lbl.setTextAlignment(TextAlignment.RIGHT);
		lbl.setAlignment(Pos.BASELINE_RIGHT);
		formatLeaderboardLabel(lbl, 20);
		return lbl;
	}
	
	private void formatLeaderboardLabel(Label lbl, int fontSize) {
		lbl.setStyle(" -fx-font-weight: bold; -fx-font-size: " + Integer.toString(fontSize));
	}
	
	private boolean saveScore(String name) {

		try {
			if (parseName(name)) {
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
		} catch (IsAlreadyNameException e) {
			input.setText("");
			input.setPromptText("Choose another name");
		} catch (IsNotLetterException e) {
			input.setText("");
			input.setPromptText("Must be only letters");
		} catch (ScoreAlreadySavedException e) {
			input.setText("");
			input.setPromptText("Score already saved!");
		}
		
		return false;
	}
	
	private boolean parseName(String name) throws 
			LongerThan3Exception, IsCurseWordException, IsAlreadyNameException, IsNotLetterException, ScoreAlreadySavedException {
		
		if (scoreSaved) {
			throw new ScoreAlreadySavedException();
		}
		
		if (name.length() > 3) {
			throw new LongerThan3Exception();
		}
		
		for (Mover m : mvrs) {
			ResultMover rm = (ResultMover) m;
			if (name.toUpperCase().equals(rm.getResultName())) {
				throw new IsAlreadyNameException();
			}
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
	private class IsAlreadyNameException extends Exception {}
	private class IsNotLetterException extends Exception {}
	private class ScoreAlreadySavedException extends Exception {}
	

	//unused inherited methods from MinigameView
	void startTimer(int ms) {}
	void stopTimer() {}
	void setUpListeners() {}
	void importImages() {}
	void draw(ArrayList<Mover> movers) {}
	void drawTutorial(int step) {}
	void updateTutorialStep(MouseEvent me) {}

}
