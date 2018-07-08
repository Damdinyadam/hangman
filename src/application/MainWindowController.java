package application;


import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowController  {
	

	

	private Stage primaryStage;

	@FXML Button nickButton;
	@FXML Button gButton;
	@FXML Button Buttonexit;
	@FXML TextField nickText;

	public String nick1;
public String mode;

	public void setMain(Main main, Stage primaryStage){
	this.primaryStage=primaryStage;


	}
	  public void gameWindow(){
		  try {
			  FXMLLoader loader=
			    new FXMLLoader(
				  Main.class.getResource("/view/GameWindow.fxml"));
			    AnchorPane gamePane=loader.load();



			    Stage gameWindowStage = new Stage();
			    gameWindowStage.setTitle("Wiœielec");
			    gameWindowStage.initModality(Modality.WINDOW_MODAL);
			    gameWindowStage.initOwner(primaryStage);
			    gameWindowStage.setMinWidth(500.0);
			    gameWindowStage.setMinHeight(500.0);
			    Scene scene=new Scene(gamePane);
			    gameWindowStage.setScene(scene);

			    GameWindowController gameWindowController=
			    		loader.getController();
			    gameWindowController.setMain(gameWindowStage);

			    gameWindowController.setnick1(String.valueOf(nick1));
			    gameWindowController.setmode(mode);
			    gameWindowStage.showAndWait();
			} catch (IOException e) {
			  e.printStackTrace();}
	  }

	
	  
@FXML
public void ntext(){//sluchacz przycisku, ktory wczytuje nick.
	nick1=nickText.getText();
}

public void game() {//sluchacz przycisku gra. Uruchamia okno gra.
	gameWindow();
}

public void exit() {//sluchacz przycisku exit.
	primaryStage.close();
}
}


