package it.univpm.WeatherCloseRomeApp.service;

import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.univpm.WeatherCloseRomeApp.exceptions.InvalidNumberException;
import it.univpm.WeatherCloseRomeApp.models.City;
import it.univpm.WeatherCloseRomeApp.utilities.Stats;

/**
 * Classe che testa il modello City
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
class TempServiceImplTest {

	private TempServiceImpl service;
	private Stats stat;

	/**
	 * Inizializza i componenti necessari a testare i metodi
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		service = new TempServiceImpl();
		stat = new Stats();
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

		City c1 = new City(12345L, "Napoli", 300.0, 301.0, 299.0);

		assertEquals(12345L, c1.getID());
		assertEquals("Napoli", c1.getName());
		assertEquals(300.0, c1.getTemp());
		assertEquals(299.0, c1.getTempMin());
		assertEquals(301.0, c1.getTempMax());
	}

	/**
	 * Test dell'eccezione InvalidNumberException
	 */
	@Test
	@DisplayName("Corretta generazione dell'eccezione InvalidNumberException.")
	void INETest() {

		int cnt = 51;
		InvalidNumberException e = assertThrows(InvalidNumberException.class, () -> {
			service.APICall(cnt);
		});
		assertEquals("InvalidNumberException: Numero di città sbagliato. Inserire un numero tra 1 e 50 (inclusi)",
				e.toString());
	}

	/**
	 * Test del metodo scambia()
	 */
	@Test
	@DisplayName("Verifica funzionalità metodo scambia().")
	void scambiaTest() {

		org.json.simple.JSONArray jarr = new org.json.simple.JSONArray();
		org.json.simple.JSONObject jobj0 = new org.json.simple.JSONObject();
		org.json.simple.JSONObject jobj1 = new org.json.simple.JSONObject();

		jobj0.put("id", 12);
		jobj0.put("name", "Roma");
		jobj0.put("Massimo", 25);
		jobj0.put("Minimo", 22);
		jobj0.put("Media", 23);
		jobj0.put("Varianza", 11);
		jobj1.put("id", 22);
		jobj1.put("name", "Napoli");
		jobj1.put("Massimo", 67);
		jobj1.put("Minimo", 62);
		jobj1.put("Media", 66);
		jobj1.put("Varianza", 2);
		jarr.add(jobj0);
		jarr.add(jobj1);
		stat.scambia(0, 1, jarr);

		org.json.simple.JSONObject jobjsupp0 = (JSONObject) jarr.get(0);
		org.json.simple.JSONObject jobjsupp1 = (JSONObject) jarr.get(1);
		assertEquals(22, jobjsupp0.get("id"));
		assertEquals(12, jobjsupp1.get("id"));
	}

}