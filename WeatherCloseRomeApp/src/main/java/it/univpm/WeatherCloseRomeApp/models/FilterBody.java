package it.univpm.WeatherCloseRomeApp.models;

/**
 * Classe che descrive il modello del JSONObject utilizzato per fare il
 * filtraggio
 * 
 * @author Mattia Beccerica, Alessandro Fermanelli, Giulio Gattari
 */
public class FilterBody {

	/**
	 * Numero delle città (cnt)
	 */
	private int count;

	/**
	 * Periodo da considerare: daily, weekly, monthly
	 */
	private String period;

	/**
	 * Data di inizio filtraggio (formato: YYYY-MM-DD)
	 */
	private String data;

	/**
	 * Nome della città da filtrare
	 */
	private String name;

	/**
	 * Numero intero.....
	 */
	private int customPeriod;

	/**
	 * Metodo che restituisce il numero delle città
	 * 
	 * @return count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Metodo che setta count
	 * 
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Metodo che restituisce period
	 * 
	 * @return period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * Metodo che setta period
	 * 
	 * @param period
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * Metodo che restituisce data
	 * 
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * Metodo che setta data
	 * 
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Metodo che restituisce name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Metodo che setta name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Metodo che restituisce customPeriod
	 * 
	 * @return customPeriod
	 */
	public int getCustomPeriod() {
		return customPeriod;
	}

	/**
	 * Metodo che setta customPeriod
	 * 
	 * @param customPeriod
	 */
	public void setCustomPeriod(int customPeriod) {
		this.customPeriod = customPeriod;
	}

}