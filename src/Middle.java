import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class Middle extends Thread {
	
	static HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
	
	
	public Middle(){ // If instancing is needed.
		
	}
		
	public static void count(){ // Task thread for changing labels and editing other GUI aspects.
		Task<Void> task = new Task<Void>() {
			  @Override
			  public Void call() throws Exception {
				  Platform.runLater(new Runnable() { // Before run.
				        @Override
				        public void run() {
				        	GUI.changeButtonVisiblity(false);
							GUI.changeTFEditability(true);
				        }
				      });
				  
			    int i;
			    for (i = 10; i > 0; i--) {
			      final int finalI = i;
			      Platform.runLater(new Runnable() { // During run.
			        @Override
			        public void run() {
			        	GUI.changeLabel4Text("The wheel has started spinning: " + finalI);
			        }
			      });
			      Thread.sleep(1000);
			    }
			    
			    Platform.runLater(new Runnable() { // Last run.
			        @Override
			        public void run() {
			        	GUI.changeLabel4Text("The wheel has stopped spinning.");
			        	GUI.changeButtonVisiblity(true);
						GUI.changeTFEditability(false);
						checkWin();
						Back.insertGuess();
						GUI.clearGuess();
			        }
			      });
			    
				return null;
			  }
			};
			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
			GUI.changeTFEditability(false);
			GUI.changeButtonVisiblity(true);
	}
	
	public static void checkWin(){
		int winningNum, guess;
		Random random = new Random();
		winningNum = random.nextInt(10 - 1 + 1) + 1; // 1-10
		guess = GUI.getGuess();
		
		Platform.runLater(() -> { 
            GUI.changeLabel6Text("" + winningNum); 
        }); 
		
		if (guess == winningNum){
			
			for(Map.Entry<Integer,Integer> i:hm.entrySet()){
				
				if (guess == i.getKey()){
					
					Platform.runLater(() -> { 
                        GUI.changeLabel3Text("You've won: $" + i.getValue() + "!"); 
                    }); 
					
				}
				
			}
			
		}
		
		else {
			Platform.runLater(() -> { 
                GUI.changeLabel3Text("You lost this round!"); 
            }); 
		}
		
	}
	
	public static void initializeHashmap(){
		hm.put(1, 5000);
		hm.put(2, 7500);
		hm.put(3, 8000);
		hm.put(4, 3000);
		hm.put(5, 10000);
		hm.put(6, 1000);
		hm.put(7, 77777);
		hm.put(8, 12000);
		hm.put(9, 12345);
		hm.put(10, 67890);
	}
	
}
