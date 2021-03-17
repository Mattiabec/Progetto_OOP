package it.univpm.WeatherCloseRomeApp.utilities;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.univpm.WeatherCloseRomeApp.controller.TempController;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidDateException;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidFieldException;
import it.univpm.WeatherCloseRomeApp.exceptions.ShortDatabaseException;
import it.univpm.WeatherCloseRomeApp.exceptions.WrongPeriodException;
import it.univpm.WeatherCloseRomeApp.models.FilterBody;
import it.univpm.WeatherCloseRomeApp.service.TempServiceImpl;

/**
 * Classe che testa alcuni metodi della classe Filter
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
class FilterTest {

	private TempController controller;
	private Stats stat;
	private Filter filter;
	private org.json.simple.JSONArray jarr;
	private TempServiceImpl tempServiceImpl;
	private String data;
	private int numdays;
	private FilterBody filtering;

	/**
	 * Inizializza i componenti necessari a testare i metodi
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		controller = new TempController();
		stat = new Stats();
		filter = new Filter();
		jarr = new org.json.simple.JSONArray();
		tempServiceImpl = new TempServiceImpl();
		data = "12-12-1999";
		numdays = 30;
		filtering = new FilterBody(5, "mensile", "2021-03-05", "", 0, "");
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
	 * Test metodo DatabaseWidth
	 */
	@Test
	@DisplayName("Corretta valutazione di ampiezza database")
	void DatabaseWidthTest() {
		Vector<String> dateDisponibili = new Vector<String>();
		String data1 = "2021-03-08";
		String data2 = "2021-03-09";
		String data3 = "2021-03-10";
		String data4 = "2021-03-11";
		dateDisponibili.add(data1);
		dateDisponibili.add(data2);
		dateDisponibili.add(data3);
		dateDisponibili.add(data4);
		int numdays = 2;
		String startDate1 = data1;
		String startDate2 = data4;
		assertTrue(filter.databaseWidth(startDate1, numdays, dateDisponibili), "Must be true");
		assertFalse(filter.databaseWidth(startDate2, numdays, dateDisponibili), "Must be false");
	}

	/**
	 * Test metodo AfterDay
	 */
	@Test
	@DisplayName("Corretta consecuzione di giorni")
	void ADTest() {

		String startDate = "2021-03-06";
		String endDate1 = "2021-03-11";
		String endDate2 = "2021-03-03";
		assertTrue(filter.afterDay(startDate, endDate1), "Must be true");
		assertFalse(filter.afterDay(startDate, endDate2), "Must be false");
	}

	/**
	 * Test dell'eccezione InvalidFieldException
	 */
	@Test
	@DisplayName("Corretta esecuzione di InvalidFieldException")
	void IFEtest() {

		jarr = tempServiceImpl.getJSONList(22);
		org.json.simple.JSONArray jreturn = stat.orderStats("max", jarr);
		org.json.simple.JSONObject jobj = (JSONObject) jreturn.get(0);
		InvalidFieldException e = new InvalidFieldException();
		assertEquals(e.toString(), jobj.get("ERROR"));
	}

	/**
	 * Test dell'eccezione InvalidDataException
	 */
	@Test
	@DisplayName("Corretta esecuzione di InvalidDateException")
	void IDETest() {

		Vector<String> datedisponibili = null;
		try {
			datedisponibili = filter.DateDisponibili();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
		if (!datedisponibili.contains(data)) {
			InvalidDateException e = assertThrows(InvalidDateException.class, () -> {
				filter.filterPeriod(50, data, 7, "");
			});
			assertEquals(
					"InvalidDateException: Data inserita incorretta. Controllare la rotta \"/date\" per le date disponibili.",
					e.toString());
		}
	}

	/**
	 * Test dell'eccezione ShortDatabaseException
	 */
	@Test
	@DisplayName("Corretta esecuzione ShortDatabaseException")
	void SDETest() {

		Vector<String> datedisponibili = null;
		try {
			datedisponibili = filter.DateDisponibili();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
		String data0 = datedisponibili.get(0);
		ShortDatabaseException e = assertThrows(ShortDatabaseException.class, () -> {
			filter.filterPeriod(50, data0, numdays, "");
		});
		assertEquals(
				"ShortDatabaseException: Database non contiene abbastanza informazioni. Scegliere un periodo ragionevole.",
				e.toString());
	}

	/**
	 * Test dell'eccezione WrongPeriodException
	 */
	@Test
	@DisplayName("Corretta esecuzione WrongPeriodException")
	void WPETest() {

		jarr = controller.filters(filtering, "");
		org.json.simple.JSONObject jobj = (JSONObject) jarr.get(0);
		WrongPeriodException e = new WrongPeriodException();
		assertEquals(e.toString(), jobj.get("ERROR"));
	}

}
