package it.univpm.WeatherCloseRomeApp.utilities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import it.univpm.WeatherCloseRomeApp.exceptions.InvalidDateException;
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

	private TempServiceImpl tempServiceImpl = new TempServiceImpl();
	private String path = System.getProperty("user.dir") + "/database.dat";

	/**
	 * Metodo che restituisce le date in cui sono presenti dati relativi le
	 * temperature salvati nel file "database.dat"
	 * 
	 * @return Vector<String> dateAvailable
	 * @throws IOException            se si sono verificati errori durante la
	 *                                lettura/scrittura del file
	 * @throws FileNotFoundException  se il tentativo di aprire "database.dat" nel
	 *                                path è fallito
	 * @throws ClassNotFoundException se la classe segnalata non è visibile dal
	 *                                metodo
	 */
	public Vector<String> DateDisponibili() throws FileNotFoundException, IOException, ClassNotFoundException {

		Vector<String> dateAvailable = new Vector<String>();
		File f = new File(path);

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
		return dateAvailable;
	}

	/**
	 * Metodo che verifica che ci siano dei dati salvati nel file "database.dat" nei
	 * giorni di interesse
	 * 
	 * @param startDate       rappresenta la data iniziale
	 * @param numDays         rappresenta il "period" (daily, weekly, monthly,
	 *                        custom)
	 * @param dateDisponibili rappresenta un vettore con all'interno le date
	 *                        presenti nel file "database.dat"
	 * @return boolean flag
	 */
	public boolean databaseWidth(String startDate, int numDays, Vector<String> dateDisponibili) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		boolean flag = true;
		int incr = 0;
		for (int i = 0; i < numDays; i++) {
			c.add(Calendar.DATE, incr);
			String v = sdf.format(c.getTime());
			if (!dateDisponibili.contains(v))
				flag = false;
			incr = 1;
		}
		return flag;
	}

	/**
	 * Metodo che mette in un vettore di tipo stringa, le date di interesse
	 * 
	 * @param startDate rappresenta la data iniziale
	 * @param numDays   rappresenta il "period" (daily, weekly, monthly, custom)
	 * @return Vector<String> ret
	 */
	public Vector<String> dateForStats(String startDate, int numDays) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Vector<String> ret = new Vector<String>();
		try {
			c.setTime(sdf.parse(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int incr = 0;
		for (int i = 0; i < numDays; i++) {
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
	 * @param cnt       rappresenta il numero di città di cui vogliamo conoscere le
	 *                  informazioni relative la temperatura
	 * @param startDate rappresenta la data iniziale
	 * @param numDays   rappresenta il "period" (daily, weekly, monthly, custom)
	 * @param name      rappresenta il nome della città
	 * @return JSONArray contenente un JSONObject per ogni città filtrata con le
	 *         proprie statistiche
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
	public org.json.simple.JSONArray filterPeriod(int cnt, String startDate, int numDays, String name)
			throws IOException, ClassNotFoundException, ShortDatabaseException, InvalidNumberException,
			InvalidDateException {

		Filter filter = new Filter();
		Vector<String> date = filter.DateDisponibili();
		Vector<City> cities = tempServiceImpl.getVector(cnt);
		Vector<String> datedavalutare = filter.dateForStats(startDate, numDays);
		org.json.simple.JSONArray jarr = new org.json.simple.JSONArray();
		File f = new File(path);

		if (!date.contains(startDate)) {
			InvalidDateException e = new InvalidDateException();
			throw e;
		} else {
			if (filter.databaseWidth(startDate, numDays, date)) {
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
	 * Metodo che restituisce le date da valutare per il filtraggio di statistiche
	 * ogni "customperiod" giorni
	 * 
	 * @param startDate rappresenta la data iniziale
	 * @param numDays   rappresenta il "customperiod"
	 * @return Vector<String> ret
	 */
	public Vector<String> jumpingDate(String startDate, int numDays) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Vector<String> ret = new Vector<String>();
		try {
			c.setTime(sdf.parse(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < ret.capacity(); i++) {
			if (i == 0)
				c.add(Calendar.DATE, 0);
			else
				c.add(Calendar.DATE, numDays);
			String v = sdf.format(c.getTime());
			ret.add(v);
		}
		return ret;
	}

	/**
	 * Metodo che filtra le statistiche ogni "customperiod" giorni
	 * 
	 * @param cnt       rappresenta il numero di città di cui vogliamo conoscere le
	 *                  informazioni relative la temperatura
	 * @param startDate rappresenta la data iniziale
	 * @param numDays   rappresenta il "customperiod"
	 * @param name      rappresenta il nome della città
	 * @return JSONArray composto da JSONObject per ogni città filtrata con le
	 *         proprie statistiche
	 * @throws InvalidDateException   se non si hanno dati nel database della data
	 *                                inserita
	 * @throws IOException            se si sono verificati errori durante la
	 *                                lettura/scrittura del file
	 * @throws ClassNotFoundException se la classe segnalata non è visibile dal
	 *                                metodo
	 * @throws InvalidNumberException se "cnt" è maggiore di 50 o minore di 1
	 */
	public org.json.simple.JSONArray jumpPeriod(int cnt, String startDate, int numDays, String name)
			throws InvalidDateException, IOException, ClassNotFoundException, InvalidNumberException {

		Filter filter = new Filter();
		Vector<String> date = filter.DateDisponibili();
		Vector<City> cities = tempServiceImpl.getVector(cnt);
		Vector<String> dateNecessarie = filter.jumpingDate(startDate, numDays);
		org.json.simple.JSONArray jarr = new org.json.simple.JSONArray();
		File f = new File(path);

		if (!date.contains(startDate)) {
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
	 * Metodo che verifica che startDate sia precedente a endDate
	 * 
	 * @param startDate rappresenta la data iniziale
	 * @param endDate   rappresenta la data finale
	 * @return boolean
	 */
	public boolean afterDay(String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(sdf.parse(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			c2.setTime(sdf.parse(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c1.before(c2);
	}

}