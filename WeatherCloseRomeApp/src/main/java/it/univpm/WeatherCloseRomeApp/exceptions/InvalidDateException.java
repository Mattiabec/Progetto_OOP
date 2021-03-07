package it.univpm.WeatherCloseRomeApp.exception;

public class InvalidDateException extends Exception{

	public InvalidDateException() {
		super("Data inserita incorretta. Controllare la rotta \"/date\" per le date disponibili.");
	}
	public String toString() {
		return "InvalidDateException: Data inserita incorretta.";
	} 
}
