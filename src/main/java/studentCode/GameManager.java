package studentCode;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import professorCode.AbstractDictionary;
import professorCode.AbstractGameManager;
import professorCode.IOManager;
import professorCode.TurnData;

import java.io.IOException;
import java.util.Date;

/**
 * This class is your application's brain. It will be used by the GhostSkeleton class to get letter
 * and location of the letter for each game turn. 
 * 
 * 
 * @author Student
 *
 */

public class GameManager extends AbstractGameManager {

	private Label label;
	private Button addToFrontButton;
	private Button addToBackButton;
	private char nextLetter;
	private int nextLetterLocation;
	boolean addFront;

	public int newMinWordLength;

	public GameManager(Stage primaryStage, IOManager ioManager, String teamName, int minWordLength, AbstractDictionary dictionary) {
		super(teamName, ioManager);


		this.newMinWordLength = minWordLength;
		Button button = new Button();
		button.setText("Click me");
		label = new Label();
		label.setText("Nothing new");

		// Set the action for the buttons
		button.setOnAction((event) -> {
			TurnData turnData = TurnData.create(getTeamName(),nextLetter,addFront);
			submitTurn(turnData);
		});
		addToFrontButton.setOnAction(event -> onAddToFrontButtonClicked());
		addToBackButton.setOnAction(event -> onAddToBackButtonClicked());

		// Add the label and buttons to a StackPane
		StackPane pane = new StackPane();

		pane.getChildren().addAll(label, addToFrontButton, addToBackButton);
		StackPane.setAlignment(addToFrontButton, Pos.BOTTOM_LEFT);
		StackPane.setAlignment(addToBackButton, Pos.BOTTOM_RIGHT);
		StackPane.setAlignment(button, Pos.BOTTOM_CENTER);

		// Set the scene and show the stage
		Scene scene = new Scene(pane, 500, 100);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void onAddToFrontButtonClicked () {
		addFront = true;
		TurnData turnData = TurnData.create(getTeamName(), nextLetter, true);

		submitTurn(turnData);
	}

	private void onAddToBackButtonClicked () {

		addFront = false;
		TurnData turnData = TurnData.create(getTeamName(), nextLetter, false);
		submitTurn(turnData);
	}



	/**
	 * This method is called every time it is your turn. You are given the current word fragment
	 * of the game. In this method you will figure out the next letter and the location of the
	 * letter to be played.
	 *
	 * This function is NOT executed on the GUI thread, do not make any changes to your GUI in this
	 * function.
	 *
	 * @param fragment The current ordered collection of letters that have been played in the game.
	 */

	@Override
	public void onTurn(String fragment) throws IOException {
		var fileManager = new FileManager(fragment);
		var dictionary = new Dictionary(fileManager.getFilePath(), fileManager);

		// Determine the next letter and its location
		char nextLetter = 'a';
		int nextLetterLocation;
		if (!dictionary.containsWordsThatStartWith(fragment, fragment.length(), true)) {
			// If there are no words that start with the given fragment, add a letter that will not form a valid word
			nextLetter = (char) (Math.random() * 26 + 'a');
		} else {
			// Otherwise, choose a letter that will lead to the fewest possible words
			int minNumWords = Integer.MAX_VALUE;
			for (char c = 'a'; c <= 'z'; c++) {
				String candidateFragment = fragment + c;
				int numWords = dictionary.countWordsThatStartWith(candidateFragment, candidateFragment.length(), true);
				if (numWords < minNumWords) {
					minNumWords = numWords;
					nextLetter = c;
				}
			}
		}

		// Determine the location of the next letter
		nextLetterLocation = fragment.length();

		// Save the letter and location in local variables
		this.nextLetter = nextLetter;
		this.nextLetterLocation = nextLetterLocation;
	}


	/**
	 * This method is invoked immediately after your onTurn function has successfully completed. This is
	 * where you should update your GUI with the necessary graphical changes.
	 *
	 * This function is executed on the GUI thread.
	 *
	 *
	 * @param fragment The current ordered collection of letters that have been played in the game.
	 */
	public void updateGUI(String fragment){

		long time = System.currentTimeMillis();
		Date date = new Date(time);

		label.setText("Something has happened at: "+date);
	}


}
