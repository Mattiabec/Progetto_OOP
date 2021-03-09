package it.univpm.WeatherCloseRomeApp.utilities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.univpm.WeatherCloseRomeApp.controller.TempController;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidDateException;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidFieldException;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidNumberException;
import it.univpm.WeatherCloseRomeApp.exceptions.ShortDatabaseException;
import it.univpm.WeatherCloseRomeApp.exceptions.WrongPeriodException;
import it.univpm.WeatherCloseRomeApp.models.FilterBody;
import it.univpm.WeatherCloseRomeApp.service.TempServiceImpl;

class StatsAndFiltersTest {

	private Stats stat;
	private Filter filter;
	private TempController controller;

	@BeforeEach
	void setUp() throws Exception {
		stat = new Stats();
		filter = new Filter();
		controller = new TempController();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Corretta esecuzione di InvalidFieldException")
	void IFEtest() {
		InvalidFieldException e = assertThrows(InvalidFieldException.class, () -> {
			stat.orderStats("max", 3);
		});
		assertEquals("InvalidFieldException: campo errato.", e.toString());

	}

	@Test
	@DisplayName("Corretta esecuzione di InvalidDateException")
	void IDETest() {
		String data = "12-12-1999";
		Vector<String> datedisponibili = filter.DateDisponibili();
		if (!datedisponibili.contains(data)) {
			InvalidDateException e = assertThrows(InvalidDateException.class, () -> {
				filter.filterPeriod(50, data, 7, "");
			});
			assertEquals("InvalidDateException: Data inserita incorretta.", e.toString());
		}
	}

	@Test
	@DisplayName("Corretta esecuzione ShortDatabaseException")
	void SDETest() {
		int numdays = 30;
		Vector<String> datedisponibili = filter.DateDisponibili();
		String data0 = datedisponibili.get(0);
		ShortDatabaseException e = assertThrows(ShortDatabaseException.class, () -> {
			filter.filterPeriod(50, data0, numdays, "");
		});
		assertEquals("ShortDatabaseException: database insufficente.", e.toString());
	}
	
	@Test
	@DisplayName("Corretta esecuzione WrongPeriodException")
	void WPETest() {
		FilterBody filtering = new FilterBody();
		filtering.setPeriod("mensile");
		filtering.setCount(5);
		filtering.setData("2021-03-05");
		WrongPeriodException e = assertThrows(WrongPeriodException.class, () -> {
			controller.filters(filtering, "");
		});
		assertEquals("WrongPeriodException: periodo inserito incorretto.", e.toString());
	}

}