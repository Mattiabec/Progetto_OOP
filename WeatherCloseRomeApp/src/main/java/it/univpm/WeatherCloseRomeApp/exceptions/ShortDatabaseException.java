package it.univpm.WeatherCloseRomeApp.exceptions;

/**
 * Classe che segnala una eccezione se nel "period" scelto non si hanno dati sufficienti
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
public class ShortDatabaseException extends Exception{

	/**
	 * Costruttore di ShortDatabaseException
	 */
	public ShortDatabaseException() {
		super("Database non contiene abbastanza informazioni. Scegliere un periodo più breve.");
	}
	
	/**
	 * Metodo che ritorna una rappresentazione testuale (stringa) dell'oggetto
	 */
	public String toString() {
		return "ShortDatabaseException: Database non contiene abbastanza informazioni. Scegliere un periodo più breve.";
	}
	
}