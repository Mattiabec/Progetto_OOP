package it.univpm.WeatherCloseRomeApp.exceptions;

/**
 * Classe che segnala una eccezione se il numero di città (=cnt) è maggiore di 50 o minore di 1
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */

public class InvalidNumberException extends Exception{

	/**
	 * Costruttore di InvalidNumberException
	 */
	public InvalidNumberException(){
		super("Numero di città sbagliato. Inserire un numero tra 1 e 50 (inclusi)");
	}
	
	/**
	 * Metodo che ritorna una rappresentazione testuale (stringa) dell'oggetto
	 */
	public String toString() {
		return "InvalidNumberException: Numero di città sbagliato. Inserire un numero tra 1 e 50 (inclusi)";
	} 
}