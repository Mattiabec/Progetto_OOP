package it.univpm.WeatherCloseRomeApp.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Classe che testa il modello City
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
class CityTest {

	private City Napoli;

	/**
	 * Inizializza i componenti necessari a testare i metodi
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		Napoli = new City(12345L, "Napoli", 300.0, 301.0, 299.0);
	}

	/**
	 * Distrugge ciò che è stato inizializzato dal metodo setUp
	 * 
	 * @throws Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test del costruttore del modello City
	 */
	@Test
	@DisplayName("Costruttore City.")
	void testCity() {

		assertEquals(12345L, Napoli.getID());
		assertEquals("Napoli", Napoli.getName());
		assertEquals(300.0, Napoli.getTemp());
		assertEquals(299.0, Napoli.getTempMin());
		assertEquals(301.0, Napoli.getTempMax());
	}

}
