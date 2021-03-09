package it.univpm.WeatherCloseRomeApp.service;

import java.io.IOException;
import java.util.Vector;

import org.json.simple.JSONObject;

import it.univpm.WeatherCloseRomeApp.models.City;

public interface TempService {
	
	public org.json.simple.JSONObject APICall(int cnt);

	public org.json.simple.JSONArray getJSONList(int cnt);

	public Vector<City> getVector(int cnt);

	public org.json.simple.JSONObject save() throws IOException, ClassNotFoundException;

	public org.json.simple.JSONObject saveEvery5Hours();
}
