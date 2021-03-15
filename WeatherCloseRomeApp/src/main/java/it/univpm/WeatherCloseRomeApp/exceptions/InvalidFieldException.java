package it.univpm.WeatherCloseRomeApp.exceptions;

/**
 * Classe che segnala una eccezione se il "field" inserito non esiste
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
public class InvalidFieldException extends Exception{

	/**
	 * Costruttore di InvalidFieldException
	 */
	public InvalidFieldException() {
		super("Campo errato. I campi disponibili sono: Massimo, Minimo, Media, Varianza.");
	}
	
	/**
	 * Metodo che ritorna una rappresentazione testuale (stringa) dell'oggetto
	 */
	public String toString() {
		return "InvalidFieldException: campo errato.";
	}
	
}