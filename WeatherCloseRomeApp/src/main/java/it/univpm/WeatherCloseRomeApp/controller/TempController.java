package it.univpm.WeatherCloseRomeApp.controller;

import java.io.IOException;

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
	

	@GetMapping(value = "/save")
	public void saving() {
		
		try {
			tempservice.save();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GetMapping(value = "/stats")
	public org.json.simple.JSONArray statistiche() throws ClassNotFoundException, IOException{
		
			return tempservice.stats();
		
	}
	
}
