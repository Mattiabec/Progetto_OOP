package it.univpm.WeatherCloseRomeApp.model;

public class City {

	private Long ID ;
	private String name;
	private double temp;
	private double tempMax;
	private double tempMin;
	
	public City(Long iD, String name, double temp, double tempMax, double tempMin) {
		super();
		ID = iD;
		this.name = name;
		this.temp = temp;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
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
	
	
	
	
	
}
