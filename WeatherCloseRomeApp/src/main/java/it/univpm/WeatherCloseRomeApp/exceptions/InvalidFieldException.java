package it.univpm.WeatherCloseRomeApp.exceptions;

/**
 * Classe che segnala una eccezione se il parametro inserito non esiste
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */

public class InvalidFieldException extends Exception{

	/**
	 * Costruttore di InvalidFieldException
	 */
	public InvalidFieldException() {
		super("Campo errato. I campi disponibili sono massimo,minimo,media,varianza.");
	}
	
	/**
	 * Metodo che ritorna una rappresentazione testuale (stringa) dell'oggetto
	 */
	public String toString() {
		return "InvalidFieldException: campo errato.";
	}
	
}