package it.univpm.WeatherCloseRomeApp.exception;

public class ShortDatabaseException extends Exception{

	public ShortDatabaseException() {
		super("Database non contiene abbastanza informazioni. Scegliere un periodo più breve.");
	}
	public String toString() {
		return "ShortDatabaseException: database insufficente.";
	}
	
}
