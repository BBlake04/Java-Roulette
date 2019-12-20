import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Platform;

public class Back {

	public static void firstConnect(){
		Connection c = null;
	    Statement stmt = null;
	      
	      try {	//Attempts to load pastguesses.db but will create one later if it cannot find it
	    	  Class.forName("org.sqlite.JDBC");
	          c = DriverManager.getConnection("jdbc:sqlite:pastguesses.db");
	          System.out.println("Opened database successfully");

	          stmt = c.createStatement();	//Uses primary key to sort guesses chronologically and shows player name and guess number
	          String sql = "CREATE TABLE IF NOT EXISTS PASTGUESSES " +
	        		  		 "(ID INTEGER PRIMARY KEY     NOT NULL," +
	                         " PLAYER           TEXT    NOT NULL, " + 
	                         " GUESS    INT     NOT NULL)"; 
	          stmt.executeUpdate(sql);
	          stmt.close();
	          c.close();
	       } catch ( Exception e ) {
	          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	          System.exit(0);
	       }
	}
	
	public static void insertGuess(){
		Connection c = null;
		PreparedStatement stmt = null;
		String add = "INSERT INTO PASTGUESSES (PLAYER, GUESS) VALUES(?,?)";	//Insert line taking 2 parameters as a value

		try {
			Class.forName("org.sqlite.JDBC");	//Begins getting and adding the 2 parameters to the database
			c = DriverManager.getConnection("jdbc:sqlite:pastguesses.db");
			stmt = c.prepareStatement(add);
			stmt.setString(1, GUI.label2.getText());
			stmt.setInt(2, GUI.getGuess());
			stmt.executeUpdate();
			stmt.close();
			c.close();
			System.out.println("Successfully added guess to the database.\n");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.out.println("Could not add guess to the database.\n");
		}
	}
	
	public static void getTenGuesses(){
		
		StringBuilder builder = new StringBuilder();
		Connection c = null;
		PreparedStatement stmt = null;	//Line below pulls the last 10 values taken into the database.
		String sql = "SELECT * FROM (SELECT * FROM PASTGUESSES ORDER BY ID DESC LIMIT 10) ORDER BY ID ASC";

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:pastguesses.db");
			stmt = c.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){	//Formats the stringbuilder for the user to see whats in the database
				builder.append("PLAYER: " + rs.getString("PLAYER") + " GUESS: " + rs.getString("GUESS") + "\n");
			}
			String finishedString = builder.toString();
			Platform.runLater(() -> { 
				GUI.changeListOfGuesses(finishedString);
            }); 
			
			stmt.close();
			rs.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.out.println("Could not find guesses in the database.");
		}
	}
		
}
