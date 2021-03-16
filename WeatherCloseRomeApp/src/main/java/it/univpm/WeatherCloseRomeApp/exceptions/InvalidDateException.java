package it.univpm.WeatherCloseRomeApp.exceptions;

/**
 * Classe che segnala una eccezione se non si hanno dati nel database nella data inserita
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
public class InvalidDateException extends Exception{

	/**
	 * Costruttore di InvalidDateException
	 */
	public InvalidDateException() {
		super("Data inserita incorretta. Controllare la rotta \"/date\" per le date disponibili.");
	}
	
	/**
	 * Metodo che ritorna una rappresentazione testuale (stringa) dell'oggetto
	 */
	public String toString() {
		return "InvalidDateException: Data inserita incorretta. Controllare la rotta \"/date\" per le date disponibili.";
	} 
}