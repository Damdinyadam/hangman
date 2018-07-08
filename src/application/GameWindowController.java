package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class GameWindowController {

    @FXML private Label nicklabel;
	String tryb; 

	  
	    @FXML
	    private Button startResetButton;
	    @FXML
	    private Label warningPromptLabel;
	    @FXML
	    private TextField enterWordTextField;
	    @FXML
	    private TextField enterGuessTextField;
	    @FXML
	    private ImageView hangmanImageView;
	    @FXML
	    private Label displayedWordLabel;

	    /**
	     * Displays incorrect letters guessed
	     */
	    @FXML
	    private ImageView incorrect1;
	  
	    @FXML
	    private ImageView incorrect2;

	    @FXML
	    private ImageView incorrect3;

	    @FXML
	    private ImageView incorrect4;

	    @FXML
	    private ImageView incorrect5;

	    @FXML
	    private ImageView incorrect6;
	    
	    
	



	    /**
	     * The Model for this Model-View-Controller application
	     */
	    private Model model = new Model();

	    /**
	     * Specifies whether the game is in progress or not
	     */
	    private boolean gameInProgress;

	    /**
	     * The filename for the initial picture to display
	     */
	    private String imageName = "Hangman_0.png";

	    
	    /**
	     * Called when the GUI is set up to display the correct picture, specify which textfields
	     * should be editable, and the text in the textfields upon launching the program
	     */
	    public final void initialize() {
	        gameInProgress = false;
	        startResetButton.setVisible(false);
	        enterWordTextField.setText("");
	        enterGuessTextField.setText("");
	        enterWordTextField.setEditable(true);
	        enterGuessTextField.setEditable(false);
	        displayedWordLabel.setVisible(false);
	        try {
	            hangmanImageView.setImage(new Image(imageName));
	        } catch (NullPointerException npe) {
	            System.out.println("brak image");
	        } catch (IllegalArgumentException iae) {
	            System.out.println("Image zaginal,lub nieobslugiwany plik");
	        }
	    }

	    /**
	     * Recieves the word from the user, verifying that only characters are entered
	     *  and the length is below 18 characters before sending the correct word to the
	     *  model
	     * @param keyPressed    the key the user pressed. Used to verify the user entered
	     *                      the word
	     */
	    @FXML
	    private void getWord(KeyEvent keyPressed) {
	        if (!gameInProgress) {
	            if (keyPressed.getCode().equals(KeyCode.ENTER)) {
	                if (enterWordTextField.getCharacters().length() > 18) {
	                    warningPromptLabel.setText("za d³ugi wyraz");
	                } else {
	                    if (enterWordTextField.getCharacters().toString().toLowerCase().matches("[a-z]+")) {
	                        model.setCorrectString(enterWordTextField.getCharacters().toString().toLowerCase());
	                        gameInProgress = true;
	                        startResetButton.setVisible(true);
	                        enterWordTextField.setEditable(false);
	                        enterGuessTextField.setEditable(true);
	                        displayedWordLabel.setVisible(true);
	                        displayedWordLabel.setText(model.getDisplayedWordAsString());
	                        warningPromptLabel.setText("Zaczynaj");
	                    } else {
	                        warningPromptLabel.setText("Tylko litery");
	                    }
	                }
	            }
	        }
	    }

	    /**
	     * Recieves the key pressed from the user, verifies that the key was a character, and sends the character
	     * to the model
	     * @param keyPressed the KeyEvent from the enterGuessTextField text field
	     */
	    @FXML
	    private void getLetterGuess(KeyEvent keyPressed) {
	        if (gameInProgress) {
	            try {
	                if (Character.isLetter(keyPressed.getCharacter().toLowerCase().charAt(0))) {
	                    switch (model.isCorrectGuess(keyPressed.getCharacter().toLowerCase().charAt(0))) {
	                        case 1:
	                            warningPromptLabel.setText("Prawid³owo!");
	                            correctGuess();
	                            break;
	                        case 2:
	                            incorrectGuess(keyPressed.getCharacter().toLowerCase().charAt(0));
	                            warningPromptLabel.setText("Nieprawid³owo");
	                            break;
	                        case 3:
	                            warningPromptLabel.setText("Poprzednio klikniêty");
	                            break;
	                    }
	                }
	            } catch (ArrayIndexOutOfBoundsException exception) {
	                warningPromptLabel.setText("Tylko dozwolone litery");
	            } catch (StringIndexOutOfBoundsException exception) {
	                warningPromptLabel.setText("Tylko dozwolone litery");
	            }
	            if (model.getIncorrectGuessNum() == 6) {
	                gameInProgress = false;
	                warningPromptLabel.setText("Przegra³es!");
	            } else if (!model.getDisplayedWordAsString().contains("_")) {
	                warningPromptLabel.setText("Wygra³eœ!");
	                gameInProgress = false;
	            }
	            enterGuessTextField.setText("");
	        }
	    }

	    /**
	     * Updates the hidden word underneath the picture as correct letters are guessed
	     */
	    private void correctGuess() {
	        displayedWordLabel.setText(model.getDisplayedWordAsString());
	    }

	    /**
	     * Updates the hangman picture and the incorrect letter bank when incorrect letters
	     * are guessed.
	     * @param letter
	     */
	    private void incorrectGuess(char letter) {
	        char tempImageName[] = imageName.toCharArray();
	        int numToChange = Character.getNumericValue(tempImageName[8]);
	        numToChange++;
	        tempImageName[8] = Integer.toString(numToChange).charAt(0);
	        String tempImageNameString = "";
	        for (int tempImageNameCount = 0; tempImageNameCount < tempImageName.length; tempImageNameCount++) {
	            tempImageNameString = tempImageNameString + tempImageName[tempImageNameCount];
	        }
	        imageName = tempImageNameString;
	        hangmanImageView.setImage(new Image(imageName));
	        ImageView[] addIncorrectLetters = {incorrect1, incorrect2, incorrect3, incorrect4, incorrect5, incorrect6};
	        String imageName = "image/" + Character.toString(letter) + ".png";
	        addIncorrectLetters[model.getIncorrectGuessNum() - 1].setImage(new Image(imageName));
	        addIncorrectLetters[model.getIncorrectGuessNum() - 1].setVisible(true);
	    }

	    /**
	     * Resets the text fields, buttons, hangman picture, and incorrect letter bank before starting a new game
	     */
	    @FXML
	    private void resetGame() {
	        model.resetGame();
	        gameInProgress = false;
	        startResetButton.setVisible(false);
	        enterWordTextField.setText("");
	        enterGuessTextField.setText("");
	        enterWordTextField.setEditable(true);
	        enterGuessTextField.setEditable(false);
	        incorrect1.setVisible(false);
	        incorrect2.setVisible(false);
	        incorrect3.setVisible(false);
	        incorrect4.setVisible(false);
	        incorrect5.setVisible(false);
	        incorrect6.setVisible(false);
	        displayedWordLabel.setText("");
	        warningPromptLabel.setText("");
	        imageName = "Hangman_0.png";
	        hangmanImageView.setImage(new Image(imageName));
	    }


	    
	    public void setMain(Stage primaryStage){
	   	}
	    	public void setnick1(String name) {
	    		nicklabel.setText(name);
	    	}
	    	public void setmode(String mode) {
	    		tryb=String.valueOf(mode);
	    	}
}


