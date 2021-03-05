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

import it.univpm.WeatherCloseRomeApp.models.City;
import it.univpm.WeatherCloseRomeApp.models.SaveModel;
import it.univpm.WeatherCloseRomeApp.service.TempServiceImpl;

public class Filter {
	
	TempServiceImpl tempser = new TempServiceImpl();
	String path= System.getProperty("user.dir")+"/database.dat";
	
	
	public Vector<String> DateDisponibili() {
		Vector <String> dateAvailable = new Vector <String>();
		File f = new File(path);
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			Vector <SaveModel> savings = (Vector <SaveModel>) in.readObject();
			Iterator <SaveModel> iter = savings.iterator();
			while (iter.hasNext()) {
				String tmp= iter.next().getDataSave();
				if (!(dateAvailable.contains(tmp))) {
					
					dateAvailable.add(tmp);}
				}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return dateAvailable;
	}
	
	public static boolean  databaseWidth(String s, int numdays, Vector<String> str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(s));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean ret = true;
		int vvv = 0;
		for (int i=0; i<numdays; i++) {
			c.add(Calendar.DATE, vvv); 
			String v = sdf.format(c.getTime());
			if (!str.contains(v)) ret = false;
			vvv=1;
		}
		return ret;
	}
	
	public static Vector<String> dateForStats(String s, int numdays){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Vector <String> ret = new Vector <String>();
		try {
			c.setTime(sdf.parse(s));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int incr=0;
		for (int i=0; i<numdays; i++) {
			c.add(Calendar.DATE, incr); 
			String v = sdf.format(c.getTime());
			ret.add(v);
			incr=1;
		}
		return ret;
	}
	
	public org.json.simple.JSONArray filterPeriod(int cnt, String data,int numdays) throws IOException, ClassNotFoundException{
		Filter filter = new Filter();
		Vector <String> date = filter.DateDisponibili();
		Vector <City> cities = tempser.getVector(cnt);
		Vector<String> datedavalutare = dateForStats(data, numdays);
		org.json.simple.JSONArray jarr = new org.json.simple.JSONArray();
		File f = new File(path);
		if (!date.contains(data)) {}//throw Exception;}
		else {
			if (databaseWidth(data, numdays, date)) {
				try {
					ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
					Vector <SaveModel> filedata = (Vector <SaveModel>) in.readObject();
					Iterator<SaveModel> iter = filedata.iterator();
					while (iter.hasNext()){
						SaveModel save= iter.next();
						String str = save.getDataSave();
						Vector <City> tmp = save.getCities();
						if (datedavalutare.contains(str)) {
							
//							Iterator<City> itercity = tmp.iterator();
							for (int i=0; i<cnt; i++) {
								City tmpcity = tmp.get(i);
								double temp = tmpcity.getTemp();
								City c1 = findByID(tmpcity.getID(), cities);
								c1.getTempForstats().add(temp);
							}
						}
					}
				}catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
			} //else ecezione database non profondo
		}
		
		Iterator<City> iterForStats = cities.iterator();
		
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
	
	
	//presente 2 volte
	public City findByID(long id, Vector <City> c) {
		Iterator<City> citer = c.iterator();
		City c1 = new City();
		boolean found = false;
		while(citer.hasNext()) {
			c1 = citer.next();
			if (c1.getID()==id) {found= true; return c1;}
		}
		return c1;
	}

}
