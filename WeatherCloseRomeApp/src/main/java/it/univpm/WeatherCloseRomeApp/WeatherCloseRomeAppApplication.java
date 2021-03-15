package it.univpm.WeatherCloseRomeApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe contenente il main che avvia l'applicazione Sring
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
@SpringBootApplication
public class WeatherCloseRomeAppApplication {

	/**
	 * Metodo main
	 * 
	 * @param args rappresentano gli argomenti passati dall'utente all'avvio
	 */
	public static void main(String[] args) {
		SpringApplication.run(WeatherCloseRomeAppApplication.class, args);
	}

}
