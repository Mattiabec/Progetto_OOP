package it.univpm.WeatherCloseRomeApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.WeatherCloseRomeApp.service.TempServiceImpl;

@RestController
public class TempController {

	@Autowired
	TempServiceImpl tempservice;
	
	@GetMapping(value = "/temp")
	public org.json.simple.JSONArray temp(@RequestParam(name="number",defaultValue= "7") int count) {
		
		return tempservice.getJSONList(count);
		
	}
	
	//WORK IN PROGRESS
	/*@GetMapping(value = "/save")
	public org.json.simple.JSONObject saving() {
		
		return tempservice.startsaving();
	}*/
	
}
