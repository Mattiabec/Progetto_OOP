package it.univpm.WeatherCloseRomeApp.models;

public class City {
	
	private String name;
	private long id;
	private double temp;
	private double temp_min;
	private double temp_max;
	/**
	 * 
	 */
	public City() {
		super();
	}
	/**
	 * @param name
	 * @param id
	 * @param temp
	 * @param temp_min
	 * @param temp_max
	 */
	public City(String name, int id, double temp, double temp_min, double temp_max) {
		super();
		this.name = name;
		this.id = id;
		this.temp = temp;
		this.temp_min = temp_min;
		this.temp_max = temp_max;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the temp
	 */
	public double getTemp() {
		return temp;
	}
	/**
	 * @param temp the temp to set
	 */
	public void setTemp(double temp) {
		this.temp = temp;
	}
	/**
	 * @return the temp_min
	 */
	public double getTemp_min() {
		return temp_min;
	}
	/**
	 * @param temp_min the temp_min to set
	 */
	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min;
	}
	/**
	 * @return the temp_max
	 */
	public double getTemp_max() {
		return temp_max;
	}
	/**
	 * @param temp_max the temp_max to set
	 */
	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}
	
	@Override
	public String toString() {
		return "City [name=" + name + ", id=" + id + ", temp=" + temp + ", temp_min=" + temp_min + ", temp_max="
				+ temp_max + "]";
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
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(temp) != Double.doubleToLongBits(other.temp))
			return false;
		if (Double.doubleToLongBits(temp_max) != Double.doubleToLongBits(other.temp_max))
			return false;
		if (Double.doubleToLongBits(temp_min) != Double.doubleToLongBits(other.temp_min))
			return false;
		return true;
	}

}
