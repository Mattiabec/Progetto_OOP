package it.univpm.WeatherCloseRomeApp.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Service;

import it.univpm.WeatherCloseRomeApp.models.SaveModel;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidNumberException;
import it.univpm.WeatherCloseRomeApp.models.City;

/**
 * Classe che implementa TempService in cui vengono implementati i metodi di
 * base del programma
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
@Service
public class TempServiceImpl implements TempService {

	/**
	 * Key univoca per ogni utente necessaria per comunicare con l'API OpenWeather
	 */
	private String API_KEY = "008c7fc03fb19021c703f488733a8695";
	private String message = "DEFAULT";

	/**
	 * Metodo che chiama l'API
	 * 
	 * @param cnt rappresenta il numero di città di cui vogliamo conoscere le
	 *            informazioni relative la temperatura
	 * @return JSONObject
	 * @throws InvalidNumberException 
	 */
	public JSONObject APICall(int cnt) throws InvalidNumberException {

		JSONObject jobj = null;
		org.json.simple.JSONObject jerr= new org.json.simple.JSONObject();
		if (cnt <= 0 || cnt > 50) {
			InvalidNumberException e = new InvalidNumberException();
			throw e;
		} else {
			String urlstr;
			urlstr = "https://api.openweathermap.org/data/2.5/find?lat=41.902782&lon=12.496365&cnt=" + cnt + "&appid="
					+ API_KEY;

			try {
				URL url = new URL(urlstr);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-Type", "application/json; utf-8");
				conn.setRequestProperty("Accept", "application/json");
				conn.connect();
				int responsecode = conn.getResponseCode();

				if (responsecode != 200)
					throw new RuntimeException("HttpResponseCode: " + responsecode);
				else {
					InputStream in = conn.getInputStream();

					String data = "";
					String line = "";
					try {
						InputStreamReader inR = new InputStreamReader(in);
						BufferedReader buf = new BufferedReader(inR);

						while ((line = buf.readLine()) != null) {
							data += line;
						}

						try {
							jobj = (JSONObject) JSONValue.parseWithException(data);

						} catch (org.json.simple.parser.ParseException e) {
							jerr.put("ERROR",e.toString());
							return jerr;
						}
					} finally {
						in.close();
					}
				}
			} catch (IOException e) {
				jerr.put("ERROR",e.toString());
				return jerr;
			}
		}
		return jobj;
	}

	/**
	 * Metodo che mette in un JSONArray, gli oggetti di tipo City del vettore cities
	 * 
	 * @param cnt rappresenta il numero di città di cui vogliamo conoscere le
	 *            informazioni relative la temperatura
	 * @return JSONArray con un JSONObject per ogni città
	 */
	public org.json.simple.JSONArray getJSONList(int cnt) {

		TempServiceImpl tempServiceImpl = new TempServiceImpl();
		org.json.simple.JSONArray ritorno = new org.json.simple.JSONArray();
		org.json.simple.JSONObject jerr= new org.json.simple.JSONObject();

		Vector<City> cities = new Vector<City>();
		
		try {
			cities = tempServiceImpl.getVector(cnt);
		} catch (InvalidNumberException e) {
			jerr.put("ERROR", e.toString());
			ritorno.add(jerr);
			return ritorno;
		}


		Iterator<City> iter = cities.iterator();
		while (iter.hasNext()) {
			org.json.simple.JSONObject jnew = new JSONObject();
			City city = iter.next();

			jnew.put("id", city.getID());
			jnew.put("name", city.getName());
			jnew.put("temp", city.getTemp());
			jnew.put("tempMax", city.getTempMax());
			jnew.put("tempMin", city.getTempMin());

			ritorno.add(jnew);
		}
		return ritorno;
	}

	/**
	 * Metodo che ricava dalla chiamata dell'API solo i dati relativi al nome, id,
	 * temp, tempMax, tempMin e li mette in un vettore di tipo City chiamato cities
	 * 
	 * @param cnt rappresenta il numero di città di cui vogliamo conoscere le
	 *            informazioni relative la temperatura
	 * @return Vector<City> cities con all'interno il modello City di ogni città
	 * @throws InvalidNumberException 
	 */
	public Vector<City> getVector(int cnt) throws InvalidNumberException {

		TempServiceImpl tempServiceImpl = new TempServiceImpl();
		JSONObject jobj;
		jobj = tempServiceImpl.APICall(cnt);


		org.json.simple.JSONArray weatherArray = new org.json.simple.JSONArray();
		weatherArray = (org.json.simple.JSONArray) jobj.get("list");

		org.json.simple.JSONObject support;
		double temp = 0;
		double tempMin, tempMax;
		long id;
		String name;
		Vector<City> cities = new Vector<City>();

		for (int i = 0; i < weatherArray.size(); i++) {
			support = (JSONObject) weatherArray.get(i);
			name = (String) support.get("name");
			id = (long) support.get("id");
			JSONObject jsup = (JSONObject) support.get("main");

			if (jsup.get("temp") instanceof Long) {
				long convert = (long) jsup.get("temp");
				temp = (double) convert;
			} else if (jsup.get("temp") instanceof Double) {
				temp = (double) jsup.get("temp");
			}

			tempMin = (double) jsup.get("temp_min");
			tempMax = (double) jsup.get("temp_max");

			City tempCity = new City(id, name, temp, tempMax, tempMin);
			cities.add(tempCity);
		}
		return cities;
	}

	/**
	 * Metodo che salva all'interno del file "database.dat" le informazioni relative
	 * le temperature attuali di tutte e 50 le città disponibili
	 * 
	 * @return JSONObject con una stringa che contiene data, path e conferma di avvenuto salvataggio o 
	 * fail in caso di save non avvenuto
	 * @throws InvalidNumberException se "cnt" è maggiore di 50 o minore di 1
	 */
	public JSONObject save() throws InvalidNumberException {

		org.json.simple.JSONObject jret = new org.json.simple.JSONObject();
		String path = System.getProperty("user.dir") + "/database.dat";
		File f = new File(path);

		TempServiceImpl tempServiceImpl = new TempServiceImpl();
		Vector<City> cities = new Vector<City>();
		cities = tempServiceImpl.getVector(50);

		Vector<SaveModel> savings = new Vector<SaveModel>();
		SaveModel saveobj = new SaveModel();
		saveobj.setCities(cities);

		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String today = date.format(new Date());
		saveobj.setDataSave(today);

		if (!f.exists()) {
			ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));

				savings.add(saveobj);
				out.writeObject(savings);
				message = "OK";
				out.close();
			} catch (IOException e) {
				message = "ERROR";
				jret.put("STATUS", message);
				e.printStackTrace();
				return jret;
			}
		} else {
			ObjectInputStream in;
			try {
				in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
				savings = (Vector<SaveModel>) in.readObject();
				in.close();
				ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
				savings.add(saveobj);
				out.writeObject(savings);
				message = "OK";
				out.close();
			} catch (IOException | ClassNotFoundException e) {
				message = "ERROR";
				jret.put("STATUS", message);
				e.printStackTrace();
				return jret;
			}

		}
		if (message.equals("OK")) {
			jret.put("TIME", today);
			jret.put("STATUS", message);
			jret.put("PATH", path);
		}

		return jret;
	}

	/**
	 * Metodo che richiama il metodo save() ogni 5 ore
	 */
	public void saveEvery5Hours() {

		ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
		schedule.scheduleAtFixedRate(new Runnable() {

			public void run() {
				try {
					save();
				} catch (InvalidNumberException e) {
					e.printStackTrace();
				}
			}
		}, 5, 5, TimeUnit.HOURS);
	}

}