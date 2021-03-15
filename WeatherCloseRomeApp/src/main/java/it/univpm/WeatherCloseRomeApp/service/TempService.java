package it.univpm.WeatherCloseRomeApp.service;

import java.io.IOException;
import java.util.Vector;

import it.univpm.WeatherCloseRomeApp.exceptions.InvalidNumberException;
import it.univpm.WeatherCloseRomeApp.models.City;

/**
 * Interfaccia TempService che viene implementata da TempServiceImpl e contiene
 * i metodi richiamati da TempController
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
public interface TempService {

	public org.json.simple.JSONObject APICall(int cnt) throws InvalidNumberException;

	public org.json.simple.JSONArray getJSONList(int cnt) throws InvalidNumberException;

	public Vector<City> getVector(int cnt) throws InvalidNumberException;

	public org.json.simple.JSONObject save() throws InvalidNumberException;

	public void saveEvery5Hours();
}