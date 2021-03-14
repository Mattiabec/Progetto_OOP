package it.univpm.WeatherCloseRomeApp.utilities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import it.univpm.WeatherCloseRomeApp.exceptions.InvalidDateException;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidFieldException;
import it.univpm.WeatherCloseRomeApp.exceptions.InvalidNumberException;
import it.univpm.WeatherCloseRomeApp.exceptions.ShortDatabaseException;
import it.univpm.WeatherCloseRomeApp.models.City;
import it.univpm.WeatherCloseRomeApp.models.SaveModel;
import it.univpm.WeatherCloseRomeApp.service.TempServiceImpl;

/**
 * Classe che permette il filtraggio dei dati salvati nel file "database.dat"
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
public class Filter {

	TempServiceImpl tempServiceImpl = new TempServiceImpl();
	String path = System.getProperty("user.dir") + "/database.dat";

	/**
	 * Metodo che restituisce le date in cui sono presenti dati relativi le
	 * temperature salvati nel file "database.dat"
	 * 
	 * @return Vector<String> dateAvailable
	 */
	public Vector<String> DateDisponibili() {

		Vector<String> dateAvailable = new Vector<String>();
		File f = new File(path);
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			Vector<SaveModel> savings = (Vector<SaveModel>) in.readObject();

			Iterator<SaveModel> iter = savings.iterator();
			while (iter.hasNext()) {
				String tmp = iter.next().getDataSave();
				if (!(dateAvailable.contains(tmp))) {
					dateAvailable.add(tmp);
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dateAvailable;
	}

	/**
	 * Metodo che verifica che ci siano dei dati salvati nel file "database.dat" nei
	 * giorni di interesse
	 * 
	 * @param s       rappresenta la data
	 * @param numdays rappresenta
	 * @param str     rappresenta
	 * @return un boolean
	 */
	public boolean databaseWidth(String s, int numdays, Vector<String> str) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(s));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		boolean ret = true;
		int incr = 0;
		for (int i = 0; i < numdays; i++) {
			c.add(Calendar.DATE, incr);
			String v = sdf.format(c.getTime());
			if (!str.contains(v))
				ret = false;
			incr = 1;
		}
		return ret;
	}

	/**
	 * Metodo che mette in un vettore di tipo stringa, le date di interesse
	 * 
	 * @param s       rappresenta
	 * @param numdays rappresenta
	 * @return Vector<String> ret
	 */
	public Vector<String> dateForStats(String s, int numdays) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Vector<String> ret = new Vector<String>();
		try {
			c.setTime(sdf.parse(s));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int incr = 0;
		for (int i = 0; i < numdays; i++) {
			c.add(Calendar.DATE, incr);
			String v = sdf.format(c.getTime());
			ret.add(v);
			incr = 1;
		}
		return ret;
	}

	/**
	 * Metodo che filtra le statistiche di interesse
	 * 
	 * @param cnt     rappresenta
	 * @param data    rappresenta
	 * @param numdays rappresenta
	 * @param name    rappresenta
	 * @return JSONArray
	 * @throws IOException            se si sono verificati errori durante la
	 *                                lettura/scrittura del file
	 * @throws ClassNotFoundException se la classe segnalata non è visibile dal
	 *                                metodo
	 * @throws InvalidDateException   se non si hanno dati nel database della data
	 *                                inserita
	 * @throws ShortDatabaseException se nel "period" scelto non si hanno dati
	 *                                sufficienti
	 * @throws InvalidNumberException se "cnt" è maggiore di 50 o minore di 1
	 */
	public org.json.simple.JSONArray filterPeriod(int cnt, String data, int numdays, String name) throws IOException,
			ClassNotFoundException, InvalidDateException, ShortDatabaseException, InvalidNumberException {

		Filter filter = new Filter();
		Vector<String> date = filter.DateDisponibili();
		Vector<City> cities = tempServiceImpl.getVector(cnt);
		Vector<String> datedavalutare = filter.dateForStats(data, numdays);
		org.json.simple.JSONArray jarr = new org.json.simple.JSONArray();
		File f = new File(path);

		if (!date.contains(data)) {
			throw new InvalidDateException();
		} else {
			if (filter.databaseWidth(data, numdays, date)) {
				try {
					ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
					Vector<SaveModel> filedata = (Vector<SaveModel>) in.readObject();
					in.close();
					Iterator<SaveModel> iter = filedata.iterator();
					while (iter.hasNext()) {
						SaveModel save = iter.next();
						String str = save.getDataSave();
						Vector<City> tmp = save.getCities();
						if (datedavalutare.contains(str)) {

							for (int i = 0; i < cnt; i++) {
								City tmpcity = tmp.get(i);
								if (tmpcity.getName().contains(name)) {
									double temp = tmpcity.getTemp();
									City c1 = Stats.findByID(tmpcity.getID(), cities);
									c1.getTempForstats().add(temp);
								}
							}
						}
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				throw new ShortDatabaseException();
			}
		}

		Iterator<City> iterForStats = cities.iterator();

		while (iterForStats.hasNext()) {
			City c = iterForStats.next();
			if (c.getName().contains(name)) {
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
		}
		return jarr;
	}

	/**
	 * Metodo che
	 * 
	 * @param s       rappresenta
	 * @param numdays rappresenta
	 * @return Vector<String> ret
	 */
	public Vector<String> jumpingDate(String s, int numdays) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Vector<String> ret = new Vector<String>(10);
		try {
			c.setTime(sdf.parse(s));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < ret.capacity(); i++) {
			if (i == 0)
				c.add(Calendar.DATE, 0);
			else
				c.add(Calendar.DATE, numdays);
			String v = sdf.format(c.getTime());
			ret.add(v);
		}
		return ret;
	}

	/**
	 * Metodo che
	 * 
	 * @param cnt     rappresenta
	 * @param data    rappresenta
	 * @param numdays rappresenta
	 * @param name    rappresenta
	 * @return JSONArray
	 * @throws InvalidDateException   se non si hanno dati nel database della data
	 *                                inserita
	 * @throws IOException            se si sono verificati errori durante la
	 *                                lettura/scrittura del file
	 * @throws ClassNotFoundException se la classe segnalata non è visibile dal
	 *                                metodo
	 * @throws InvalidNumberException se "cnt" è maggiore di 50 o minore di 1
	 */
	public org.json.simple.JSONArray jumpPeriod(int cnt, String data, int numdays, String name)
			throws InvalidDateException, IOException, ClassNotFoundException, InvalidNumberException {

		Filter filter = new Filter();
		org.json.simple.JSONArray jarr = new org.json.simple.JSONArray();
		Vector<City> cities = tempServiceImpl.getVector(cnt);
		File f = new File(path);
		Vector<String> dateNecessarie = filter.jumpingDate(data, numdays);
		Vector<String> date = filter.DateDisponibili();
		if (!date.contains(data)) {
			throw new InvalidDateException();
		}
		Vector<String> dateArrivabili = new Vector<String>();
		for (String ss : dateNecessarie) {
			if (date.contains(ss))
				dateArrivabili.add(ss);
			else
				break;
		}
		org.json.simple.JSONObject jj = new org.json.simple.JSONObject();
		jj.put("date arrivabili", dateArrivabili);
		jarr.add(jj);

		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
		Vector<SaveModel> filedata = (Vector<SaveModel>) in.readObject();
		in.close();
		Iterator<SaveModel> iter = filedata.iterator();
		while (iter.hasNext()) {
			SaveModel save = iter.next();
			String str = save.getDataSave();
			Vector<City> tmp = save.getCities();
			if (dateArrivabili.contains(str)) {

				for (int i = 0; i < cnt; i++) {
					City tmpcity = tmp.get(i);
					double temp = tmpcity.getTemp();
					City c1 = Stats.findByID(tmpcity.getID(), cities);
					c1.getTempForstats().add(temp);
				}
			}
		}

		Iterator<City> iterForStats = cities.iterator();

		while (iterForStats.hasNext()) {
			City c = iterForStats.next();
			if (c.getName().contains(name)) {
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
		}
		return jarr;
	}

	/**
	 * Metodo che ordina le statistiche filtrate
	 * 
	 * @param s    rappresenta
	 * @param jarr rappresenta
	 * @return JSONArray
	 * @throws InvalidFieldException se il parametro s inserito non esiste
	 */
	public JSONArray orderFilterPeriod(String s, org.json.simple.JSONArray jarr) throws InvalidFieldException {
		Stats stat = new Stats();
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
						stat.scambia(i, i + 1, jarr);
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
						stat.scambia(i, i + 1, jarr);
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
						stat.scambia(i, i + 1, jarr);
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
						stat.scambia(i, i + 1, jarr);
						scambio = true;
					}
				}
			}
		} else
			throw new InvalidFieldException();
		return jarr;
	}

	/**
	 * Metodo che
	 * 
	 * @param start rappresenta
	 * @param end   rappresenta
	 * @return
	 */
	public boolean afterDay(String start, String end) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(sdf.parse(start));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			c2.setTime(sdf.parse(end));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c1.before(c2);
	}

}