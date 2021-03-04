package it.univpm.WeatherCloseRomeApp.service;

import java.io.IOException;
import java.util.Vector;

import org.json.simple.JSONObject;

import it.univpm.WeatherCloseRomeApp.models.City;

public interface TempService {
	public org.json.simple.JSONObject APICall(int cnt);
	public org.json.simple.JSONArray getJSONList(int cnt);
	public Vector<City> getVector(int cnt);
	public void save() throws IOException, ClassNotFoundException;
	public void saveEvery5Hours();
	public org.json.simple.JSONArray stats() throws IOException, ClassNotFoundException;
	//public static City findByID(long id, Vector <City> c);
	public org.json.simple.JSONArray orderStats(String s);
	public void scambia(int i1, int i2,org.json.simple.JSONArray jarr);
}
