package it.univpm.WeatherCloseRomeApp.utilities;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Vector;

import org.json.simple.JSONObject;

import it.univpm.WeatherCloseRomeApp.models.City;
import it.univpm.WeatherCloseRomeApp.models.SaveModel;
import it.univpm.WeatherCloseRomeApp.service.TempServiceImpl;

public class Stats {
	
	TempServiceImpl tempser = new TempServiceImpl();
	
	
	
	public org.json.simple.JSONArray stats(int cnt) throws IOException, ClassNotFoundException{
		
		String path = System.getProperty("user.dir")+"/database.dat";
		File f = new File(path);
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
		Vector <SaveModel> fromFile = new Vector <SaveModel>();
		Vector <City> forStats = new Vector <City>();
		TempServiceImpl tempser = new TempServiceImpl();
		forStats= tempser.getVector(cnt);
		fromFile = (Vector <SaveModel>) in.readObject();
		Iterator<SaveModel> iter = fromFile.iterator();
		while (iter.hasNext()) {
			SaveModel tmp = iter.next();
			Vector <City> cities= tmp.getCities();
			Iterator<City> iterCity = cities.iterator();
			while(iterCity.hasNext()) {
				City c = iterCity.next();
				double temperatura = c.getTemp();
				City c1 = findByID(c.getID(),forStats);
				c1.getTempForstats().add(temperatura);
			}
		}
		Iterator<City> iterForStats = forStats.iterator();
		
		org.json.simple.JSONArray jarr = new org.json.simple.JSONArray();
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
	
	
	public org.json.simple.JSONArray orderStats(String s, int cnt){
		Stats stat = new Stats();
		org.json.simple.JSONArray jarr = new org.json.simple.JSONArray();
		try {
			jarr = stat.stats(cnt);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean scambio = true;
		if (s.equals("Massimo") || s.equals("MASSIMO") || s.equals("massimo")) {
			while (scambio) {
			scambio = false;
			for (int i=0; i<jarr.size()-1; i++) {
				org.json.simple.JSONObject jobj = (JSONObject) jarr.get(i);
				org.json.simple.JSONObject jobjsucc = (JSONObject) jarr.get(i+1);
				double paramsucc= (double) jobjsucc.get("Massimo");
				double param = (double) jobj.get("Massimo");
				if ( paramsucc> param) {scambia(i,i+1,jarr); scambio = true;}
			}
		}
		}
		else if (s.equals("Minimo") || s.equals("MINIMO") || s.equals("minimo")) {
			while (scambio) {
			scambio = false;
			for (int i=0; i<jarr.size()-1; i++) {
				org.json.simple.JSONObject jobj = (JSONObject) jarr.get(i);
				org.json.simple.JSONObject jobjsucc = (JSONObject) jarr.get(i+1);
				double paramsucc= (double) jobjsucc.get("Minimo");
				double param = (double) jobj.get("Minimo");
				if ( paramsucc> param) {scambia(i,i+1,jarr); scambio = true;}
			}
		}
		}
		else if (s.equals("Media") || s.equals("MEDIA") || s.equals("media")) {
			while (scambio) {
			scambio = false;
			for (int i=0; i<jarr.size()-1; i++) {
				org.json.simple.JSONObject jobj = (JSONObject) jarr.get(i);
				org.json.simple.JSONObject jobjsucc = (JSONObject) jarr.get(i+1);
				double paramsucc= (double) jobjsucc.get("Media");
				double param = (double) jobj.get("Media");
				if ( paramsucc> param) {scambia(i,i+1,jarr); scambio = true;}
			}
		}
		}
		else if (s.equals("Varianza") || s.equals("VARIANZA") || s.equals("varianza")) {
			while (scambio) {
			scambio = false;
				for (int i=0; i<jarr.size()-1; i++) {
					org.json.simple.JSONObject jobj = (JSONObject) jarr.get(i);
					org.json.simple.JSONObject jobjsucc = (JSONObject) jarr.get(i+1);
					double paramsucc= (double) jobjsucc.get("Varianza");
					double param = (double) jobj.get("Varianza");
					if ( paramsucc> param) {scambia(i,i+1,jarr); scambio = true;}
				}
			}
		}
		return jarr;
	}
	
	
	public void scambia(int i1, int i2,org.json.simple.JSONArray jarr) {
		org.json.simple.JSONObject j1 = (JSONObject) jarr.get(i1);
		org.json.simple.JSONObject jsupp = new org.json.simple.JSONObject();
		org.json.simple.JSONObject j2 = (JSONObject) jarr.get(i2);
		
		jsupp.put("name", j2.get("name"));
		jsupp.put("Massimo", j2.get("Massimo"));
		jsupp.put("Minimo", j2.get("Minimo"));
		jsupp.put("Media", j2.get("Media"));
		jsupp.put("Varianza", j2.get("Varianza"));
		jsupp.put("id", j2.get("id"));
		
		j2.put("name", j1.get("name"));					//mette i parametri di j1 su j2
		j2.put("Massimo", j1.get("Massimo"));
		j2.put("Minimo", j1.get("Minimo"));
		j2.put("Media", j1.get("Media"));
		j2.put("Varianza", j1.get("Varianza"));
		j2.put("id", j2.get("id"));
		
		j1.put("name", jsupp.get("name"));
		j1.put("Massimo", jsupp.get("Massimo"));
		j1.put("Minimo", jsupp.get("Minimo"));
		j1.put("Media", jsupp.get("Media"));
		j1.put("Varianza", jsupp.get("Varianza"));
		j1.put("id", j1.get("id"));
	}
	
	
}
