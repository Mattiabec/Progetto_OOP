package it.univpm.WeatherCloseRomeApp.exceptions;

/**
 * Classe che segnala una eccezione se il "period" inserito è sbagliato
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
public class WrongPeriodException extends Exception{
	
	/**
	 * Costruttore di WrongPeriodException
	 */
	public WrongPeriodException() {
		super("Periodo inserito incorretto. Scegliere tra: daily,weekly,monthly,custom.");
	}
	
	/**
	 * Metodo che ritorna una rappresentazione testuale (stringa) dell'oggetto
	 */
	public String toString() {
		return "WrongPeriodException: Periodo inserito incorretto. Scegliere tra: daily,weekly,monthly,custom.";
	} 
}