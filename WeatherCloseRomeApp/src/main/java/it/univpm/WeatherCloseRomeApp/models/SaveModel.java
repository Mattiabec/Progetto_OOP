package it.univpm.WeatherCloseRomeApp.models;

import java.io.Serializable;
import java.util.Vector;

/**
 * 
 * @author Mattia
 *
 */
public class SaveModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2425071011667367166L;

	private Vector<City> cities;
	String dataSave;

	/**
	 * 
	 * @return
	 */
	public Vector<City> getCities() {
		return cities;
	}

	/**
	 * 
	 * @param cities
	 */
	public void setCities(Vector<City> cities) {
		this.cities = cities;
	}

	/**
	 * 
	 * @return
	 */
	public String getDataSave() {
		return dataSave;
	}

	/**
	 * 
	 * @param dataSave
	 */
	public void setDataSave(String dataSave) {
		this.dataSave = dataSave;
	}

}