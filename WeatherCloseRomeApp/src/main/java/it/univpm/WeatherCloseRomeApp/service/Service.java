package it.univpm.WeatherCloseRomeApp.service;

public interface Service {
	
	public abstract JSONObject mainCall(int cnt);
	public abstract JSONArray getList(int cnt);

}
