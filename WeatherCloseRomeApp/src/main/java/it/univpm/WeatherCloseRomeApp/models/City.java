package it.univpm.WeatherCloseRomeApp.models;

import java.io.Serializable;
import java.util.Vector;

public class City implements Serializable {

	private long ID ;
	private String name;
	private double temp;
	private double tempMax;
	private double tempMin;
	private Vector <Double> tempForstats;
	private Vector <Double> tempMaxForStats;
	private Vector <Double> tempMinForStats;
	
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

	@Override
	public String toString() {
		return "City [ID=" + ID + ", name=" + name + ", temp=" + temp + ", tempMax=" + tempMax + ", tempMin=" + tempMin
				+ ", tempForstats=" + tempForstats + ", tempMaxForStats=" + tempMaxForStats + ", tempMinForStats="
				+ tempMinForStats + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (ID != other.ID)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(temp) != Double.doubleToLongBits(other.temp))
			return false;
		if (tempForstats == null) {
			if (other.tempForstats != null)
				return false;
		} else if (!tempForstats.equals(other.tempForstats))
			return false;
		if (Double.doubleToLongBits(tempMax) != Double.doubleToLongBits(other.tempMax))
			return false;
		if (tempMaxForStats == null) {
			if (other.tempMaxForStats != null)
				return false;
		} else if (!tempMaxForStats.equals(other.tempMaxForStats))
			return false;
		if (Double.doubleToLongBits(tempMin) != Double.doubleToLongBits(other.tempMin))
			return false;
		if (tempMinForStats == null) {
			if (other.tempMinForStats != null)
				return false;
		} else if (!tempMinForStats.equals(other.tempMinForStats))
			return false;
		return true;
	}
	
	
}
