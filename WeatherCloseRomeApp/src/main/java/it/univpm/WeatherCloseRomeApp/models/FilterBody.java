package it.univpm.WeatherCloseRomeApp.models;

public class FilterBody {

	private int count;
	private String period;
	private String data; //Formato data : yyyy-MM-dd
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
}
