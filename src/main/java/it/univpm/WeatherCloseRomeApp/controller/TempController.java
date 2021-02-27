package it.univpm.WeatherCloseRomeApp.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.univpm.WeatherCloseRomeApp.service.TempService;
import it.univpm.WeatherCloseRomeApp.service.TempServiceImpl;

@RestController
public class TempController {

	@Autowired
	TempServiceImpl tempservice;
	
	@GetMapping(value = "/temp")
	public org.json.simple.JSONArray temp(@RequestParam(name="number",defaultValue= "7") int count) {
		
		return tempservice.getList(count);
		
	}
	
}
