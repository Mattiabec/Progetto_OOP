package it.univpm.WeatherCloseRomeApp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import it.univpm.WeatherCloseRomeApp.model.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class TempServiceImpl {
	
	int cnt;
	String urlstr;
	List<City> cities;
	String inline;
	

	public JSONObject mainCall(int cnt) {
		
			
			urlstr = "https://api.openweathermap.org/data/2.5/find?lat=41.902782&lon=12.496365&cnt="+cnt+"&appid=008c7fc03fb19021c703f488733a8695";
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
	
	public org.json.simple.JSONArray getList(int cnt) {
		
		TempServiceImpl temps= new TempServiceImpl();
		JSONObject jobj=temps.mainCall(cnt);
		org.json.simple.JSONArray ritorno= new org.json.simple.JSONArray();		
		
		org.json.simple.JSONArray weatherArray = new org.json.simple.JSONArray();
		weatherArray = (org.json.simple.JSONArray) jobj.get("list");
		org.json.simple.JSONObject support;
		double temp, tempMin, tempMax;
		Long id;
		String name;
		Vector <City> cities = new Vector <City>(); 
		
		
		for (int i=0; i<weatherArray.size(); i++) {
			support = (JSONObject) weatherArray.get(i);
			name = (String) support.get("name");
			id = (Long) support.get("id");
			JSONObject jsup = (JSONObject) support.get("main");
			temp = (double) jsup.get("temp");
			tempMin= (double) jsup.get("temp_min");
			tempMax = (double) jsup.get("temp_max");
			City tempCity= new City(id, name, temp, tempMax,tempMin);
			cities.add(tempCity);
		}
		
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
	
	



}
