import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class GUI extends Application {
	
	static Label label2 = new Label("Guest");
	
	static Label label7 = new Label("The Devil's Dice");
	
	static Label label3 = new Label();
	
    static Label label4 = new Label("The wheel isn't spinning.");
    
    static Label label6 = new Label();
    
    static Label listOfGuesses = new Label("None");
    
    static TextField guessHere = new TextField();
    
    static Button btn1 = new Button();
		
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Roulette"); // Building the stage
		
		Middle.initializeHashmap(); // Putting hashmap keys and values here.
		Back.firstConnect(); // Creating table and connecting to SQLite here.
		
        btn1.setText("Play");
        
        Button btn2 = new Button();
        btn2.setText("View previous guesses");
        
        Button btn3 = new Button();
        btn3.setText("How to play");
        
        Button btn4 = new Button();
        btn4.setText("Change names");
        
        Label label1 = new Label("Guess a number!");
        
        guessHere.setPromptText("1-10");
        guessHere.setEditable(false);
        
        Label label5 = new Label("Winning number is:");
        
        guessHere.textProperty().addListener(new ChangeListener<String>() { // Detect if textfield has anything that isn't 1-10 and remove it.
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("[1-9]|10")) {
                	Platform.runLater(() -> { 
                        guessHere.clear(); 
                    }); 
                }
            }
        });
        
        // Button actions below.
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Middle.count();
			}
        });
        
        btn2.setOnAction(new EventHandler<ActionEvent>() { // Have this select from SQL database or something.
            @Override
            public void handle(ActionEvent event) {
				Stage pastGuesses = new Stage();
				pastGuesses.setTitle("Past Guesses");
				Pane pane = new Pane();
				
				Back.getTenGuesses();
				
				pane.getChildren().addAll(listOfGuesses);
				pastGuesses.setScene(new Scene(pane, 200, 200));
		        pastGuesses.setResizable(true);
		        pastGuesses.show();
			}
        });
        
        btn3.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
				Stage rules = new Stage();
				rules.setTitle("How to play");
				Pane pane = new Pane();
				
				String str = "How you play in this application is by clicking the play\nbutton and entering a number in the text field. If you don't\n"
						+ "enter a number before the timer at the top runs out, the\ntext field will be locked and you will lose. You can only\nenter"
						+ " integers between 1 and 10. When the time runs out,\nif you guessed correctly, you will win a prize.";
				Label theRules = new Label(str);
				theRules.setWrapText(true);
				
				pane.getChildren().addAll(theRules);
				rules.setScene(new Scene(pane, 303, 100));
		        rules.setResizable(false);
		        rules.show();
			}
        });
        
        btn4.setOnAction(new EventHandler<ActionEvent>() { // Have this select from SQL database to alter input.
            @Override
            public void handle(ActionEvent event) {
				Stage nameChange = new Stage();
				nameChange.setTitle("Change the names here");
				Pane pane = new Pane();
				
				Label playerNameHere = new Label("Enter Player Name");
				Label casinoNameHere = new Label("Enter Casino Name");
				
				TextField playerName = new TextField();
				TextField casinoName = new TextField();
				playerName.setPromptText("Player Name");
				casinoName.setPromptText("Casino Name");
				
				Button btn5 = new Button();
		        btn5.setText("Confirm");
				
				playerNameHere.setLayoutX(0);
				playerNameHere.setLayoutY(10);
				playerName.setLayoutX(0);
				playerName.setLayoutY(30);
				casinoNameHere.setLayoutX(0);
				casinoNameHere.setLayoutY(70);
				casinoName.setLayoutX(0);
				casinoName.setLayoutY(90);
				btn5.setLayoutX(50);
				btn5.setLayoutY(130);
				pane.getChildren().addAll(playerNameHere, casinoNameHere, playerName, casinoName, btn5);
				nameChange.setScene(new Scene(pane, 150, 150));
		        nameChange.setResizable(false);
		        nameChange.show();
		        
		        btn5.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	Player player = new Player();
		            	
		            	player.setName(playerName.getText());
		            	player.setNickname(casinoName.getText());
		            	
		            	Platform.runLater(() -> { 
	                        changeLabel2Text("" + player.getName());
	                        changeLabel7Text("" + player.getNickname());
	                        nameChange.close();
	                    });
					}
		        });
			}
        });
        
        Pane root = new Pane();
        // Manual layout below.
        btn1.setLayoutX(235);
        btn1.setLayoutY(285);
        btn2.setLayoutX(0);
        btn2.setLayoutY(50);
        btn3.setLayoutX(0);
        btn3.setLayoutY(100);
        btn4.setLayoutX(0);
        btn4.setLayoutY(150);
        label1.setLayoutX(210);
        label1.setLayoutY(225);
        label2.setLayoutX(0);
        label2.setLayoutY(0);
        label3.setLayoutX(200);
        label3.setLayoutY(320);
        label4.setLayoutX(165);
        label4.setLayoutY(160);
        label5.setLayoutX(200);
        label5.setLayoutY(185);
        label6.setLayoutX(250);
        label6.setLayoutY(206);
        label7.setLayoutX(0);
        label7.setLayoutY(18);
        guessHere.setLayoutX(230);
        guessHere.setLayoutY(250);
        guessHere.setPrefColumnCount(3);
        //End of manual layout. Set stage below.
        root.getChildren().addAll(btn1, btn2, btn3, btn4, label1, label2, label3, label4, label5, label6, label7, guessHere);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
        
	}
	//Everything below is pretty much just setters.
	public static void changeLabel2Text(String text){
		
		label2.setText(text);
		
	}
	
	public static void changeLabel3Text(String text){
		
		label3.setText(text);
		
	}
	
	public static void changeLabel4Text(String text){
		
		label4.setText(text);
		
	}
	
	public static void changeLabel6Text(String text){
		
		label6.setText(text);
		
	}
	
	public static void changeLabel7Text(String text){
		
		label7.setText(text);
		
	}
	
	public static void changeListOfGuesses(String text){
		
		listOfGuesses.setText(text);
		
	}
	
	public static void changeTFEditability(boolean active){
		
		guessHere.setEditable(active);
		
	}
	
	public static void changeButtonVisiblity(boolean active){
		
		btn1.setVisible(active);
		
	}
	
	public static int getGuess(){
		int number;
		
		number = Integer.parseInt(guessHere.getText());
		
		return number;
	}
	
	public static void clearGuess(){
		Platform.runLater(() -> { 
            guessHere.clear(); 
        }); 
	}
	
}
