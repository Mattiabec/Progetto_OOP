package it.univpm.WeatherCloseRomeApp.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Service;

import it.univpm.WeatherCloseRomeApp.models.SaveModel;
import it.univpm.WeatherCloseRomeApp.models.City;

@Service
public class TempServiceImpl implements TempService{
	
	
	String API_KEY = "008c7fc03fb19021c703f488733a8695";
	

	public JSONObject APICall(int cnt) {
		
			String urlstr;
			urlstr = "https://api.openweathermap.org/data/2.5/find?lat=41.902782&lon=12.496365&cnt="+cnt+"&appid="+API_KEY;
			JSONObject jobj = null;
			
			
			try {
				
				URL url = new URL(urlstr);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-Type", "application/json; utf-8");
				conn.setRequestProperty("Accept", "application/json");
				conn.connect();
				int responsecode = conn.getResponseCode(); 
				
				if (responsecode != 200)
					throw new RuntimeException("HttpResponseCode: " +responsecode);
				else
					{
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
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}finally {
						in.close();
					}
				
				}
				} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return jobj;

	} 
	
	public org.json.simple.JSONArray getJSONList(int cnt) {
		
		TempServiceImpl temps= new TempServiceImpl();
		JSONObject jobj=temps.APICall(cnt);
		org.json.simple.JSONArray ritorno= new org.json.simple.JSONArray();		
		
		org.json.simple.JSONArray weatherArray = new org.json.simple.JSONArray();
		weatherArray = (org.json.simple.JSONArray) jobj.get("list");
		org.json.simple.JSONObject support;
		double temp, tempMin, tempMax;
		long id;
		String name;
		Vector <City> cities = new Vector <City>(); 
		
		
		cities = temps.getVector(cnt);
		
		Iterator<City> iter = cities.iterator();
		while(iter.hasNext()) {
			
			org.json.simple.JSONObject jnew= new JSONObject();
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
	
	public Vector<City> getVector(int cnt){
		
		TempServiceImpl temps= new TempServiceImpl();
		JSONObject jobj=temps.APICall(cnt);
		org.json.simple.JSONArray ritorno= new org.json.simple.JSONArray();		
		
		org.json.simple.JSONArray weatherArray = new org.json.simple.JSONArray();
		weatherArray = (org.json.simple.JSONArray) jobj.get("list");
		org.json.simple.JSONObject support;
		double temp = 0;
		double tempMin, tempMax;
		long id;
		String name;
		Vector <City> cities = new Vector <City>(); 
		
		
		for (int i=0; i<weatherArray.size(); i++) {
			support = (JSONObject) weatherArray.get(i);
			name = (String) support.get("name");
			id = (long) support.get("id");
			JSONObject jsup = (JSONObject) support.get("main");
			if ( jsup.get("temp") instanceof Long )
            {
              long convert = (long) jsup.get("temp");
              temp = (double) convert;
            }
            else if ( jsup.get("temp") instanceof Double )
            {
                temp = (double) jsup.get("temp");
            }
			tempMin= (double) jsup.get("temp_min");
			tempMax = (double) jsup.get("temp_max");
			City tempCity= new City(id, name, temp, tempMax,tempMin);
			cities.add(tempCity);
		}
		return cities;
	}
	
	
	public void save() throws IOException, ClassNotFoundException{
		String path= System.getProperty("user.dir")+"/database.dat";
		File f = new File(path);
		TempServiceImpl tempser = new TempServiceImpl();
		Vector <City> cities = new Vector <City>();
		cities = tempser.getVector(50);
		Vector <SaveModel> savings = new Vector <SaveModel>();
		SaveModel saveobj = new SaveModel();
		saveobj.setCities(cities);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String today = date.format(new Date());
		saveobj.setDataSave(today);
		if (!f.exists()) {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			savings.add(saveobj);
			out.writeObject(savings);
			out.close();
		}
		else {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			savings = (Vector <SaveModel>)in.readObject();
			savings.add(saveobj);
			in.close();
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			savings.add(saveobj);
			out.writeObject(savings);
			out.close();
		}
	}
	
	public void saveEvery5Hours() {
		ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
		schedule.scheduleAtFixedRate(new Runnable() {
			
			public void run() {
				try {
					save();
				} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			}
		}, 0,5 ,TimeUnit.HOURS);
	}
	
	public Vector<String> DateDisponibili() {
		Vector <String> dateAvailable = new Vector <String>();
		String path= System.getProperty("user.dir")+"/database.dat";
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
		TempServiceImpl tempser = new TempServiceImpl();
		Vector <String> date = tempser.DateDisponibili();
		Vector <City> cities = tempser.getVector(cnt);
		Vector<String> datedavalutare = dateForStats(data, numdays);
		org.json.simple.JSONArray jarr = new org.json.simple.JSONArray();
		File f = new File("C:\\Users\\Aless\\Documents\\PROGETTI_pao\\database.dat");
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

