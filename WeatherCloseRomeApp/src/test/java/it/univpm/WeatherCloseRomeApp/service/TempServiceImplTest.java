package it.univpm.WeatherCloseRomeApp.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.univpm.WeatherCloseRomeApp.exceptions.InvalidNumberException;
import it.univpm.WeatherCloseRomeApp.models.City;
import it.univpm.WeatherCloseRomeApp.utilities.Stats;

class TempServiceImplTest {

	private TempServiceImpl service;
	private Vector<City> cities;
	private Stats stat;

	@BeforeEach
	void setUp() throws Exception {
		service = new TempServiceImpl();
		cities = new Vector<City>();
		stat = new Stats();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Costruttore City.")
	void test1() {
		City c1 = new City(12345L, "Napoli", 300.0, 301.0, 299.0);

		assertEquals(12345L, c1.getID());
		assertEquals("Napoli", c1.getName());
		assertEquals(300.0, c1.getTemp());
		assertEquals(299.0, c1.getTempMin());
		assertEquals(301.0, c1.getTempMax());
	}

	@Test
	@DisplayName("Corretta generazione dell'eccezione InvalidNumberException.")
	void test2() {
		int cnt = 51;
		InvalidNumberException e = assertThrows(InvalidNumberException.class, () -> {
			service.APICall(cnt);
		});
		assertEquals("InvalidNumberException: Numero di citt� sbagliato. Inserire un numero tra 1 e 50 (inclusi)",
				e.toString());
	}

	@Test

	void test3() {
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