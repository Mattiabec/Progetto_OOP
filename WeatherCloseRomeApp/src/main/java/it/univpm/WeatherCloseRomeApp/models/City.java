package it.univpm.WeatherCloseRomeApp.models;

import java.io.Serializable;
import java.util.Vector;

public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6214288722856378619L;
	
	private long ID ;
	private String name;
	private double temp;
	private double tempMax;
	private double tempMin;
	private Vector <Double> tempForstats = new Vector <Double>();
	private double max,min,media,varianza;
	
	
	public City() {
		this.name=null;
		this.ID=0L;
		this.temp=0;
		this.tempMax= 0;
		this.tempMin=0;
		this.tempForstats=new Vector <Double>();
	}
	
	
	public City(long iD, String name, double temp, double tempMax, double tempMin) {
		super();
		ID = iD;
		this.name = name;
		this.temp = temp;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}
	
	public double getMax() {
		return max;
	}
	
	public void setMax() {
		for (Double d: tempForstats) {
			if (d>max) {this.max =d;}
		}
	}
	
	public double getMin() {
		return min;
	}

	public void setMin() {
		this.min = 1000;
		for (Double d: tempForstats) {
			if (d<min) {this.min =d;}
		}
	}
	
	public double getMedia() {
		return media;
	}
	
	public void setMedia() {
		int length = tempForstats.size();
		double sum = 0;
		for (Double d: tempForstats) {
			sum += d;
		}
		this.media= sum/length;
	}
	
	/**
	 * @return the varianza
	 */
	public double getVarianza() {
		return varianza;
	}


	/**
	 * @param varianza the varianza to set
	 */
	public void setVarianza() {
		int length = tempForstats.size();
		double sqm = 0;
		if(this.media !=0) {
			for(Double d : this.tempForstats) {
				sqm += (d-media)*(d-media);
			}
		}
		
		this.varianza = sqm/length;
	}

	/**
	 * @return the tempForstats
	 */
	public Vector<Double> getTempForstats() {
		return tempForstats;
	}


	/**
	 * @param tempForstats the tempForstats to set
	 */
	public void setTempForstats(Vector<Double> tempForstats) {
		this.tempForstats = tempForstats;
	}


	@Override
	public String toString() {
		return "City [ID=" + ID + ", name=" + name + ", temp=" + temp + ", tempMax=" + tempMax + ", tempMin=" + tempMin
				+ ", tempForstats=" + tempForstats + ", max=" + max + ", min=" + min + ", media=" + media
				+ ", varianza=" + varianza + "]";
	}
	
	
}
