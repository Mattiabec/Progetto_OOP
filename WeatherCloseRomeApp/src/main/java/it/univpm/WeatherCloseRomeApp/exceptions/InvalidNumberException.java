package it.univpm.WeatherCloseRomeApp.exception;

public class InvalidNumberException extends Exception{

	public InvalidNumberException(){
		super("Numero di città sbagliato. Inserire un numero tra 1 e 50 (inclusi)");
	}
	
	public String toString() {
		return "InvalidNumberException: Numero di città sbagliato. Inserire un numero tra 1 e 50 (inclusi)";
	} 
}
