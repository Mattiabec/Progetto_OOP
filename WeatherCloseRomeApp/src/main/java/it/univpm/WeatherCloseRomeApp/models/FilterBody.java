package it.univpm.WeatherCloseRomeApp.models;

/**
 * 
 * @author Mattia
 *
 */
public class FilterBody {

	private int count;
	private String period;
	private String data; // Formato data : yyyy-MM-dd
	private String name;
	private int customPeriod;
	

	/**
	 * 
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 
	 * @return
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * 
	 * @param period
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * 
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
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
	 * @return the customPeriod
	 */
	public int getCustomPeriod() {
		return customPeriod;
	}

	/**
	 * @param customPeriod the customPeriod to set
	 */
	public void setCustomPeriod(int customPeriod) {
		this.customPeriod = customPeriod;
	}

}