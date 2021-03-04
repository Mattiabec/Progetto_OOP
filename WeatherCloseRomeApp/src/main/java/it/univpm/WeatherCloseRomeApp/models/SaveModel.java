package it.univpm.WeatherCloseRomeApp.models;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;

public class SaveModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2425071011667367166L;
	
	private Vector <City> cities;
	String dataSave;
	
	public Vector<City> getCities() {
		return cities;
	}
	public void setCities(Vector<City> cities) {
		this.cities = cities;
	}
	public String getDataSave() {
		return dataSave;
	}
	public void setDataSave(String dataSave) {
		this.dataSave = dataSave;
	}
	
	
}
