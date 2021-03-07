package it.univpm.WeatherCloseRomeApp.exceptions;

public class InvalidFieldException extends Exception{

	public InvalidFieldException() {
		super("Campo errato. I campi disponibili sono massimo,minimo,media,varianza.");
	}
	
	public String toString() {
		return "InvalidFieldException: campo errato.";
	}
	
}
