package it.univpm.WeatherCloseRomeApp.models;

import java.io.Serializable;
import java.util.Vector;

/**
 * Classe che descrive il modello di una città con i suoi parametri interni
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
public class City implements Serializable {

	private static final long serialVersionUID = 6214288722856378619L;

	/**
	 * Attributi principali
	 */
	private long ID;
	private String name;
	private double temp, tempMax, tempMin;

	/**
	 * Vettore contenente le temperature di una città per fare statistiche
	 */
	private Vector<Double> tempForstats = new Vector<Double>();

	/**
	 * Attributi usati per calcolare le statistiche
	 */
	private double max, min, media, varianza;

	/**
	 * Costruttore classe City senza parametri
	 */
	public City() {
		this.name = null;
		this.ID = 0L;
		this.temp = 0;
		this.tempMax = 0;
		this.tempMin = 0;
		this.tempForstats = new Vector<Double>();
	}

	/**
	 * Costruttore classe City con tutti i parametri
	 * 
	 * @param id      id della città
	 * @param name    Nome della città
	 * @param temp    Temperatura della città
	 * @param tempMax Temperatura massima della città
	 * @param tempMin Temperatura minima della città
	 */
	public City(long id, String name, double temp, double tempMax, double tempMin) {
		super();
		ID = id;
		this.name = name;
		this.temp = temp;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}

	/**
	 * Metodo che restituisce id della città
	 * 
	 * @return id
	 */
	public long getID() {
		return ID;
	}

	/**
	 * Metodo che setta l'id della città
	 * 
	 * @param id
	 */
	public void setID(long id) {
		ID = id;
	}

	/**
	 * Metodo che restituisce il nome della città
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Metodo che setta il nome della città
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Metodo che restituisce la temperatura di una città
	 * 
	 * @return temp
	 */
	public double getTemp() {
		return temp;
	}

	/**
	 * Metodo che setta la temperatura di una città
	 * 
	 * @param temp
	 */
	public void setTemp(double temp) {
		this.temp = temp;
	}

	/**
	 * Metodo che restituisce la temperatura massima di una città
	 * 
	 * @return tempMax
	 */
	public double getTempMax() {
		return tempMax;
	}

	/**
	 * Metodo che setta la temperatura massima di una città
	 * 
	 * @param tempMax
	 */
	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

	/**
	 * Metodo che restituisce la temperatura minima di una città
	 * 
	 * @return tempMin
	 */
	public double getTempMin() {
		return tempMin;
	}

	/**
	 * Metodo che setta la temperatura minima di una città
	 * 
	 * @param tempMin
	 */
	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	/**
	 * Metodo che restituisce il valore massimo di tutte le temperature
	 * 
	 * @return max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * Metodo che setta su max il massimo valore di temperatura della città
	 */
	public void setMax() {
		for (Double d : tempForstats) {
			if (d > max) {
				this.max = d;
			}
		}
	}

	/**
	 * Metodo che restituisce il valore minimo di tutte le temperature
	 * 
	 * @return min
	 */
	public double getMin() {
		return min;
	}

	/**
	 * Metodo che setta su min il minimo valore di temperatura della città
	 */
	public void setMin() {
		this.min = 1000;
		for (Double d : tempForstats) {
			if (d < min) {
				this.min = d;
			}
		}
	}

	/**
	 * Metodo che restituisce la media delle temperature della città
	 * 
	 * @return media
	 */
	public double getMedia() {
		return media;
	}

	/**
	 * Metodo che setta su media la media delle temperature della città
	 */
	public void setMedia() {
		int length = tempForstats.size();
		double sum = 0;
		for (Double d : tempForstats) {
			sum += d;
		}
		this.media = sum / length;
	}

	/**
	 * Metodo che restituisce la varianza delle temperature della città
	 * 
	 * @return varianza
	 */
	public double getVarianza() {
		return varianza;
	}

	/**
	 * Metodo che setta su varianza la varianza delle temperature della città
	 */
	public void setVarianza() {
		int length = tempForstats.size();
		double sqm = 0;
		if (this.media != 0) {
			for (Double d : this.tempForstats) {
				sqm += (d - media) * (d - media);
			}
		}
		this.varianza = sqm / length;
	}

	/**
	 * Metodo che restituisce tempForStats
	 * 
	 * @return tempForStats
	 */
	public Vector<Double> getTempForstats() {
		return tempForstats;
	}

	/**
	 * Metodo che setta tempForStats
	 * 
	 * @param tempForStats
	 */
	public void setTempForstats(Vector<Double> tempForstats) {
		this.tempForstats = tempForstats;
	}

	/**
	 * Override del metodo toString
	 * 
	 * @return String che rappresenta la città
	 */
	@Override
	public String toString() {
		return "City [ID=" + ID + ", name=" + name + ", temp=" + temp + ", tempMax=" + tempMax + ", tempMin=" + tempMin
				+ ", tempForstats=" + tempForstats + ", max=" + max + ", min=" + min + ", media=" + media
				+ ", varianza=" + varianza + "]";
	}

}