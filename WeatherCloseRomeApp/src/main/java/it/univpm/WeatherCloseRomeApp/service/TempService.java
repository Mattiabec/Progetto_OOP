package it.univpm.WeatherCloseRomeApp.service;

import java.util.Vector;
import it.univpm.WeatherCloseRomeApp.models.City;

public interface TempService {
	Vector<City> getList(int cnt);
}
