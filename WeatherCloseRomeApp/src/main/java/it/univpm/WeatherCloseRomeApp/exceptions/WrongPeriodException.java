package it.univpm.WeatherCloseRomeApp.exceptions;

public class WrongPeriodException extends Exception{
	
	public WrongPeriodException() {
		super("Periodo inserito incorretto. Scegliere tra: daily,weekly,monthly, oppure null se si vuole customperiod.");
	}
	public String toString() {
		return "InvalidDateException: Data inserita incorretta.";
	} 
}
