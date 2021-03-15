package it.univpm.WeatherCloseRomeApp.models;

import java.io.Serializable;
import java.util.Vector;

/**
 * Classe che descrive il modello per il salvataggio
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
public class SaveModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2425071011667367166L;

	/**
	 * Vettore cities
	 */
	private Vector<City> cities;

	/**
	 * 
	 */
	private String dataSave;

	/**
	 * Metodo che restituisce il vettore cities
	 * 
	 * @return cities
	 */
	public Vector<City> getCities() {
		return cities;
	}

	/**
	 * Metodo che setta il vettore cities
	 * 
	 * @param cities
	 */
	public void setCities(Vector<City> cities) {
		this.cities = cities;
	}

	/**
	 * Metodo che restituisce dataSave
	 * 
	 * @return dataSave
	 */
	public String getDataSave() {
		return dataSave;
	}

	/**
	 * Metodo che setta dataSave
	 * 
	 * @param dataSave
	 */
	public void setDataSave(String dataSave) {
		this.dataSave = dataSave;
	}

}