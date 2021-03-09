package it.univpm.WeatherCloseRomeApp.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.WeatherCloseRomeApp.exceptions.InvalidDateException;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidNumberException;
import it.univpm.WeatherCloseRomeApp.exceptions.ShortDatabaseException;
import it.univpm.WeatherCloseRomeApp.exceptions.WrongPeriodException;
import it.univpm.WeatherCloseRomeApp.models.FilterBody;
import it.univpm.WeatherCloseRomeApp.service.TempServiceImpl;
import it.univpm.WeatherCloseRomeApp.utilities.Filter;
import it.univpm.WeatherCloseRomeApp.utilities.Stats;

/**
 * Classe controller che gestisce tutte le rotte del programma
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */

@RestController
public class TempController {

	@Autowired
	private TempServiceImpl tempservice;
	private Stats stat = new Stats();
	private Filter filter = new Filter();
	
	/**
	 * Rotta di tipo GET che restituisce le informazioni relative alla temperatura attuali, nome città e id città
	 * 
	 * @param count rappresenta il numero di città di cui vogliamo conoscere le informazioni relative la temperatura
	 * @return JSONArray contenente un JSONObject per ogni città con le temperature, nome e id
	 */
	@GetMapping(value = "/temp")
	public org.json.simple.JSONArray temp(@RequestParam(name = "number", defaultValue = "7") int count) {

		return tempservice.getJSONList(count);
	}

	/**
	 * Rotta di tipo GET che salva in un file "database.dat" le informazioni relative alla temperatura attuali
	 * 
	 * @return
	 */
	@GetMapping(value = "/save")
	public org.json.simple.JSONObject saving() {

		org.json.simple.JSONObject jret = new org.json.simple.JSONObject();
		try {
			jret= tempservice.save();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jret;
		
	}

	/**
	 * Rotta di tipo GET che salva ogni 5 ore in un file "database.dat" le informazioni relative alla temperatura attuali
	 * 
	 * @return
	 */
	@GetMapping(value = "/saveEvery5Hours")
	public void save5Hours() {

		tempservice.saveEvery5Hours();
	}

	/**
	 * Rotta di tipo GET che restituisce le statistiche senza filtri, dei dati salvati nel file "database.dat" e permette
	 * il loro ordinamento decrescente in base al paramentro scelto (Massimo, Minimo, Media, Varianza)
	 * 
	 * @param s rappresenta il parametro di interesse da ordinare
	 * @return JSONArray contenente un JSONObject per ogni città con le proprie statistiche
	 */
	@GetMapping(value = "/stats")
	public org.json.simple.JSONArray stats(@RequestParam(name = "field", defaultValue = "") String s) {

		org.json.simple.JSONArray jreturn = new org.json.simple.JSONArray();
		if (s.equals("")) {
			try {
				jreturn = stat.stats(50);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			jreturn = stat.orderStats(s, 50);
		return jreturn;
	}

	/**
	 * Rotta di tipo GET che restituisce le date in cui sono presenti dati nel file "database.dat"
	 * 
	 * @return JSONArray contente un JSONObject per ogni data
	 */
	@GetMapping("/date")
	public org.json.simple.JSONArray datedisponibili() {
		
		org.json.simple.JSONArray jreturn = new org.json.simple.JSONArray();
		Vector<String> datestr = filter.DateDisponibili();
		Iterator<String> iterstr = datestr.iterator();
		while (iterstr.hasNext()) {
			org.json.simple.JSONObject jobj = new org.json.simple.JSONObject();
			jobj.put("data", iterstr.next());
			jreturn.add(jobj);
		}
		return jreturn;
	}

	/**
	 * Rotta di tipo POST che restituisce le statistiche relative uno specifico periodo (daily, weekly, monthly), una
	 * specifica città, uno specifico numero di città, scrivendo in input un JSONObject del tipo:
	 * 
	 *	{
     *		"count": 5,
     *		"period": "daily",
     *		"data": "2021-03-06",
     *		"customPeriod":,
     *		"name": ""
	 *	}
	 *
	 * Oppure:
	 * 
	 *	{
     *		"count": 5,
     *		"period": "",
     *		"data": "2021-03-06",
     *		"customPeriod":1,
     *		"name":""
	 *	}
	 * 
	 * @param filtering rappresenta il JSONObject in input
	 * @return JSONArray contenente un JSONObject per ogni città
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InvalidNumberException
	 * @throws InvalidDateException
	 * @throws WrongPeriodException
	 * @throws ShortDatabaseException
	 */
	@PostMapping("/filters")
	public org.json.simple.JSONArray filters(@RequestBody FilterBody filtering) throws ClassNotFoundException,
			IOException, InvalidNumberException, InvalidDateException, WrongPeriodException, ShortDatabaseException {
		
		org.json.simple.JSONArray jreturn = new org.json.simple.JSONArray();
		int cnt = filtering.getCount();
		if (cnt == 0 || cnt > 50) {
			throw new InvalidNumberException();
		}
		String data = filtering.getData();
		switch (filtering.getPeriod()) {
		case "Daily":
		case "DAILY":
		case "daily": {
			jreturn = filter.filterPeriod(cnt, data, 1, filtering.getName());
			break;
		}

		case "Weekly":
		case "WEEKLY":
		case "weekly": {
			jreturn = filter.filterPeriod(cnt, data, 7, filtering.getName());
			break;
		}

		case "Monthly":
		case "MONTHLY":
		case "monthly": {
			jreturn = filter.filterPeriod(cnt, data, 30, filtering.getName());
			break;
		}

		default:
			if (filtering.getPeriod().equals("") && filtering.getCustomPeriod() != 0) {
				jreturn = filter.jumpPeriod(cnt, data, filtering.getCustomPeriod(), filtering.getName());
				break;
			} else {
				throw new WrongPeriodException();
			}
		}
		return jreturn;
	}

}
