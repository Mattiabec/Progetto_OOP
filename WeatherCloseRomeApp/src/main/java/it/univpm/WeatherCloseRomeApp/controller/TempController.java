package it.univpm.WeatherCloseRomeApp.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.WeatherCloseRomeApp.exceptions.InvalidDateException;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidFieldException;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidNumberException;
import it.univpm.WeatherCloseRomeApp.exceptions.ShortDatabaseException;
import it.univpm.WeatherCloseRomeApp.exceptions.WrongPeriodException;
import it.univpm.WeatherCloseRomeApp.models.FilterBody;
import it.univpm.WeatherCloseRomeApp.service.TempService;
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
	private TempService tempService;
	private Stats stat = new Stats();
	private Filter filter = new Filter();

	/**
	 * Rotta di tipo GET che restituisce le informazioni relative alla temperatura
	 * attuale, nome città e id città
	 * 
	 * @param cnt rappresenta il numero di città di cui vogliamo conoscere le
	 *            informazioni relative la temperatura (default=7)
	 * @return JSONArray contenente un JSONObject per ogni città con le temperature,
	 *         nome e id
	 * @throws InvalidNumberException se "cnt" è maggiore di 50 o minore di 1
	 */
	@GetMapping(value = "/temp")
	public org.json.simple.JSONArray temp(@RequestParam(name = "number", defaultValue = "7") int cnt)
			throws InvalidNumberException {

		return tempService.getJSONList(cnt);
	}

	/**
	 *
	 * Rotta di tipo GET che salva in un file "database.dat" le informazioni
	 * relative alla temperatura attuale di tutte e 50 le città
	 * 
	 * @return JSONObject con all'interno una stringa che dice se l'operazione è
	 *         andata a buon fine
	 * @throws InvalidNumberException se "cnt" è maggiore di 50 o minore di 1
	 * @throws ClassNotFoundException se la classe segnalata non è visibile dal
	 *                                metodo
	 * @throws IOException            se si sono verificati errori durante la
	 *                                lettura/scrittura del file
	 */
	@GetMapping(value = "/save")
	public org.json.simple.JSONObject saving() throws InvalidNumberException, ClassNotFoundException, IOException {

		org.json.simple.JSONObject jret = new org.json.simple.JSONObject();
		jret = tempService.save();
		return jret;
	}

	/**
	 * Rotta di tipo GET che salva ogni 5 ore in un file "database.dat" le
	 * informazioni relative alla temperatura attuale di tutte e 50 le città
	 * 
	 * @return JSONObject con all'interno una stringa che dice se l'operazione è
	 *         andata a buon fine
	 * @throws InvalidNumberException se "cnt" è maggiore di 50 o minore di 1
	 * @throws ClassNotFoundException se la classe segnalata non è visibile dal
	 *                                metodo
	 * @throws IOException            se si sono verificati errori durante la
	 *                                lettura/scrittura del file
	 */
	@GetMapping(value = "/saveEvery5Hours")
	public org.json.simple.JSONObject save5Hours() throws InvalidNumberException, ClassNotFoundException, IOException {

		org.json.simple.JSONObject jret;
		jret = tempService.save();
		tempService.saveEvery5Hours();
		return jret;
	}

	/**
	 * Rotta di tipo GET che restituisce le statistiche senza filtri, dei dati
	 * salvati nel file "database.dat" e permette il loro ordinamento decrescente in
	 * base al paramentro scelto (Massimo, Minimo, Media, Varianza)
	 * 
	 * @param s rappresenta il parametro di interesse da ordinare
	 * @return JSONArray contenente un JSONObject per ogni città con le proprie
	 *         statistiche
	 * @throws InvalidNumberException se "cnt" è maggiore di 50 o minore di 1
	 * @throws ClassNotFoundException se la classe segnalata non è visibile dal
	 *                                metodo
	 * @throws IOException            se si sono verificati errori durante la
	 *                                lettura/scrittura del file
	 * @throws InvalidFieldException  se il parametro s inserito non esiste
	 */
	@GetMapping(value = "/stats")
	public org.json.simple.JSONArray stats(@RequestParam(name = "field", defaultValue = "") String s)
			throws InvalidNumberException, ClassNotFoundException, IOException, InvalidFieldException {

		org.json.simple.JSONArray jreturn = new org.json.simple.JSONArray();

		jreturn = stat.stats(50);
		if (!s.equals("")) {
			jreturn = stat.orderStats(s, jreturn);
		}

		return jreturn;
	}

	/**
	 * Rotta di tipo GET che restituisce le date in cui sono stati salvati dati nel
	 * file "database.dat"
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
	 * Rotta di tipo POST che restituisce le statistiche relative: uno specifico
	 * periodo (daily, weekly, monthly) o ogni determinato giorno, una specifica
	 * città, uno specifico numero di città, scrivendo in input un JSONObject del
	 * tipo:
	 * 
	 * { "count": 5, "period": "daily", "data": "2021-03-09", "customPeriod": "",
	 * "name": "" }
	 *
	 * Oppure:
	 * 
	 * { "count": 5, "period": "", "data": "2021-03-06", "customPeriod": 1, "name":
	 * "" }
	 * 
	 * Inoltre ho la possibilità di ordinare le statistiche risultanti
	 * 
	 * @param filtering rappresenta il JSONObject in input
	 * @param s         rappresenta il parametro di interesse da ordinare
	 * @return JSONArray contenente un JSONObject per ogni città filtrata
	 * @throws ClassNotFoundException se la classe segnalata non è visibile dal
	 *                                metodo
	 * @throws IOException            se si sono verificati errori durante la
	 *                                lettura/scrittura del file
	 * @throws InvalidNumberException se "cnt" è maggiore di 50 o minore di 1
	 * @throws InvalidDateException   se non si hanno dati nel database della data
	 *                                inserita
	 * @throws WrongPeriodException   se il "period" inserito è sbagliato
	 * @throws ShortDatabaseException se nel "period" scelto non si hanno dati
	 *                                sufficienti
	 * @throws InvalidFieldException  se il parametro s inserito non esiste
	 */
	@PostMapping("/filters")
	public org.json.simple.JSONArray filters(@RequestBody FilterBody filtering,
			@RequestParam(name = "field", defaultValue = "") String s)
			throws ClassNotFoundException, IOException, InvalidNumberException, InvalidDateException,
			WrongPeriodException, ShortDatabaseException, InvalidFieldException {

		org.json.simple.JSONArray jreturn = new org.json.simple.JSONArray();
		int cnt = filtering.getCount();
		String startDate = filtering.getStartDate();
		String endDate = filtering.getEndDate();
		String period = filtering.getPeriod();
		String name = filtering.getName();
		Vector<String> dateinFile = filter.DateDisponibili();
		switch (period) {
		case "Daily":
		case "DAILY":
		case "daily": {
			jreturn = filter.filterPeriod(cnt, startDate, 1, name);
			if (!s.equals(""))
				jreturn = stat.orderStats(s, jreturn);
			break;
		}

		case "Weekly":
		case "WEEKLY":
		case "weekly": {
			jreturn = filter.filterPeriod(cnt, startDate, 7, name);
			if (!s.equals(""))
				jreturn = stat.orderStats(s, jreturn);
			break;
		}

		case "Monthly":
		case "MONTHLY":
		case "monthly": {
			jreturn = filter.filterPeriod(cnt, startDate, 30, name);
			if (!s.equals(""))
				jreturn = stat.orderStats(s, jreturn);
			break;
		}

		case "custom":
		case "CUSTOM":
		case "Custom": {
			if (!endDate.equals("")) {
				if (!dateinFile.contains(endDate)) {
					throw new InvalidDateException();
				} else {
					if (!filter.afterDay(startDate, endDate)) {
						throw new InvalidDateException();
					}
					int numdays = 1;
					String incrDate = startDate;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c = Calendar.getInstance();
					try {
						c.setTime(sdf.parse(startDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					while (!incrDate.equals(endDate)) {
						c.add(Calendar.DATE, 1);
						incrDate = sdf.format(c.getTime());
						numdays++;
					}
					jreturn = filter.filterPeriod(cnt, startDate, numdays, name);
					if (!s.equals("")) {
						jreturn = stat.orderStats(s, jreturn);
					}
				}
			} else if (endDate.equals("") && filtering.getCustomPeriod() != 0) {
				jreturn = filter.jumpPeriod(cnt, startDate, filtering.getCustomPeriod(), name);
				if (!s.equals("")) {
					org.json.simple.JSONObject jdate = (JSONObject) jreturn.get(0);
					jreturn.remove(0);
					jreturn = stat.orderStats(s, jreturn);
					jreturn.add(0, jdate);
					break;
				}
			} else {
				throw new WrongPeriodException();
			}
			break;
		}

		default: {
			throw new WrongPeriodException();
		}
		}
		return jreturn;
	}

}