package it.univpm.WeatherCloseRomeApp.utilities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Vector;

import org.json.simple.JSONObject;

import it.univpm.WeatherCloseRomeApp.exceptions.InvalidFieldException;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidNumberException;
import it.univpm.WeatherCloseRomeApp.models.City;
import it.univpm.WeatherCloseRomeApp.models.SaveModel;
import it.univpm.WeatherCloseRomeApp.service.TempServiceImpl;

/**
 * Classe che permette la statistica dei dati salvati nel file "database.dat"
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
public class Stats {

	TempServiceImpl tempServiceImpl = new TempServiceImpl();
	String path = System.getProperty("user.dir") + "/database.dat";

	/**
	 * Metodo che fa statistica non ordinata delle temperature fino a quel momento
	 * salvate nel file "database.dat", calcolando il valore di Massimo, Minimo,
	 * Media e Varianza per ogni città
	 * 
	 * @param cnt rappresenta il numero di città di cui vogliamo conoscere le
	 *            informazioni relative la temperatura
	 * @return JSONArray con statistiche di Massimo, Minimo, Media e Varianza delle
	 *         temperature fino a quel momento salvate
	 */
	public org.json.simple.JSONArray stats(int cnt) {

		File f = new File(path);

		org.json.simple.JSONArray jarr = new org.json.simple.JSONArray();
		Vector<SaveModel> fromFile = new Vector<SaveModel>();
		Vector<City> forStats = new Vector<City>();

		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			forStats = tempServiceImpl.getVector(cnt);
			fromFile = (Vector<SaveModel>) in.readObject();
			in.close();
		} catch (IOException | InvalidNumberException | ClassNotFoundException e) {
			org.json.simple.JSONObject jerr = new org.json.simple.JSONObject();
			jerr.put("ERROR", e.toString());
			jarr.add(jerr);
			return jarr;
		}

		Iterator<SaveModel> iter = fromFile.iterator();
		while (iter.hasNext()) {
			SaveModel saveModel = iter.next();
			Vector<City> cities = saveModel.getCities();

			Iterator<City> iterCity = cities.iterator();
			for(int i=0; i<cnt; i++) {
				City c = iterCity.next();
				double temperatura = c.getTemp();
				City c1 = findByID(c.getID(), forStats);
				c1.getTempForstats().add(temperatura);
			}
		}

		Iterator<City> iterForStats = forStats.iterator();
		while (iterForStats.hasNext()) {
			City c = iterForStats.next();
			c.setMax();
			c.setMin();
			c.setMedia();
			c.setVarianza();

			org.json.simple.JSONObject jobj = new org.json.simple.JSONObject();
			jobj.put("name", c.getName());
			jobj.put("id", c.getID());
			jobj.put("Massimo", c.getMax());
			jobj.put("Minimo", c.getMin());
			jobj.put("Media", c.getMedia());
			jobj.put("Varianza", c.getVarianza());
			jarr.add(jobj);
		}
		return jarr;
	}

	/**
	 * Metodo che permette l'ordinamento decrescente delle statistiche scegliendo il
	 * parametro da ordinare: Massimo, Minimo, Media, Varianza
	 * 
	 * @param s   rappresenta il "field" di interesse da ordinare
	 * @param cnt rappresenta il numero di città di cui vogliamo conoscere le
	 *            informazioni relative la temperatura
	 * @return JSONArray ordinato
	 */
	public org.json.simple.JSONArray orderStats(String s, org.json.simple.JSONArray jarr) {

		boolean scambio = true;
		if (s.equals("Massimo") || s.equals("MASSIMO") || s.equals("massimo")) {
			while (scambio) {
				scambio = false;
				for (int i = 0; i < jarr.size() - 1; i++) {
					org.json.simple.JSONObject jobj = (JSONObject) jarr.get(i);
					org.json.simple.JSONObject jobjsucc = (JSONObject) jarr.get(i + 1);
					double paramsucc = (double) jobjsucc.get("Massimo");
					double param = (double) jobj.get("Massimo");
					if (paramsucc > param) {
						scambia(i, i + 1, jarr);
						scambio = true;
					}
				}
			}
		} else if (s.equals("Minimo") || s.equals("MINIMO") || s.equals("minimo")) {
			while (scambio) {
				scambio = false;
				for (int i = 0; i < jarr.size() - 1; i++) {
					org.json.simple.JSONObject jobj = (JSONObject) jarr.get(i);
					org.json.simple.JSONObject jobjsucc = (JSONObject) jarr.get(i + 1);
					double paramsucc = (double) jobjsucc.get("Minimo");
					double param = (double) jobj.get("Minimo");
					if (paramsucc > param) {
						scambia(i, i + 1, jarr);
						scambio = true;
					}
				}
			}
		} else if (s.equals("Media") || s.equals("MEDIA") || s.equals("media")) {
			while (scambio) {
				scambio = false;
				for (int i = 0; i < jarr.size() - 1; i++) {
					org.json.simple.JSONObject jobj = (JSONObject) jarr.get(i);
					org.json.simple.JSONObject jobjsucc = (JSONObject) jarr.get(i + 1);
					double paramsucc = (double) jobjsucc.get("Media");
					double param = (double) jobj.get("Media");
					if (paramsucc > param) {
						scambia(i, i + 1, jarr);
						scambio = true;
					}
				}
			}
		} else if (s.equals("Varianza") || s.equals("VARIANZA") || s.equals("varianza")) {
			while (scambio) {
				scambio = false;
				for (int i = 0; i < jarr.size() - 1; i++) {
					org.json.simple.JSONObject jobj = (JSONObject) jarr.get(i);
					org.json.simple.JSONObject jobjsucc = (JSONObject) jarr.get(i + 1);
					double paramsucc = (double) jobjsucc.get("Varianza");
					double param = (double) jobj.get("Varianza");
					if (paramsucc > param) {
						scambia(i, i + 1, jarr);
						scambio = true;
					}
				}
			}
		} else {
			org.json.simple.JSONArray jerrA = new org.json.simple.JSONArray();
			org.json.simple.JSONObject jerr = new org.json.simple.JSONObject();
			InvalidFieldException e = new InvalidFieldException();
			jerr.put("ERROR", e.toString());
			jerrA.add(jerr);
			return jerrA;
		}
		return jarr;
	}

	/**
	 * Metodo che scambia la posizione di due oggetti successivi all'interno del
	 * JSONArray
	 * 
	 * @param i1   rappresenta la posizione nel JSONArray attuale
	 * @param i2   rappresenta la posizione nel JSONArray successiva
	 * @param jarr rappresenta il JSONArray
	 */
	public void scambia(int i1, int i2, org.json.simple.JSONArray jarr) {

		org.json.simple.JSONObject j1 = (JSONObject) jarr.get(i1);
		org.json.simple.JSONObject jsupp = new org.json.simple.JSONObject();
		org.json.simple.JSONObject j2 = (JSONObject) jarr.get(i2);

		jsupp.put("name", j2.get("name"));
		jsupp.put("Massimo", j2.get("Massimo"));
		jsupp.put("Minimo", j2.get("Minimo"));
		jsupp.put("Media", j2.get("Media"));
		jsupp.put("Varianza", j2.get("Varianza"));
		jsupp.put("id", j2.get("id"));

		j2.put("name", j1.get("name"));
		j2.put("Massimo", j1.get("Massimo"));
		j2.put("Minimo", j1.get("Minimo"));
		j2.put("Media", j1.get("Media"));
		j2.put("Varianza", j1.get("Varianza"));
		j2.put("id", j1.get("id"));

		j1.put("name", jsupp.get("name"));
		j1.put("Massimo", jsupp.get("Massimo"));
		j1.put("Minimo", jsupp.get("Minimo"));
		j1.put("Media", jsupp.get("Media"));
		j1.put("Varianza", jsupp.get("Varianza"));
		j1.put("id", jsupp.get("id"));
	}

	/**
	 * Metodo che restituisce una città presente nel vettore di tipo City con lo
	 * stesso id posto in ingresso
	 * 
	 * @param id rappresenta id della città da cercare nel Vector<City>
	 * @param c  rappresenta un vettore di tipo City
	 * @return oggetto City del vettore con stesso id
	 */
	public static City findByID(long id, Vector<City> c) {

		City c1 = new City();
		Iterator<City> citer = c.iterator();
		while (citer.hasNext()) {
			c1 = citer.next();
			if (c1.getID() == id) {
				return c1;
			}
		}
		return c1;
	}

}