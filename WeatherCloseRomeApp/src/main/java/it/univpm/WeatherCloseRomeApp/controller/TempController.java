package it.univpm.WeatherCloseRomeApp.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.WeatherCloseRomeApp.models.FilterBody;
import it.univpm.WeatherCloseRomeApp.service.TempServiceImpl;
import it.univpm.WeatherCloseRomeApp.utilities.Stats;

@RestController
public class TempController {

	@Autowired
	TempServiceImpl tempservice;
	Stats stat = new Stats();
	
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
	
	@GetMapping(value = "/saveEvery5Hours")
    public void save5Hours() {
		
		tempservice.saveEvery5Hours();
		
	}
	
	@GetMapping(value = "/stats")
	public org.json.simple.JSONArray stats(@RequestParam(name="field",defaultValue = "") String s) {
		
		org.json.simple.JSONArray jreturn= new org.json.simple.JSONArray();
		if (s.equals("")) {
			try {
			jreturn= stat.stats();
			}
			catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
		else
			jreturn= stat.orderStats(s);
		return jreturn;
	}
	
	@GetMapping("/date")
	public org.json.simple.JSONArray datedisponibili(){
		org.json.simple.JSONArray jreturn = new org.json.simple.JSONArray();
		Vector<String> datestr = tempservice.DateDisponibili();
		Iterator<String> iterstr = datestr.iterator();
		while(iterstr.hasNext()) {
			org.json.simple.JSONObject jobj = new org.json.simple.JSONObject();
			jobj.put("data", iterstr.next());
			jreturn.add(jobj);
		}
		return jreturn;
	}
	
	
	
	@PostMapping("/filters")
	public org.json.simple.JSONArray filters(@RequestBody FilterBody filtering) throws ClassNotFoundException, IOException{
		org.json.simple.JSONArray jreturn = new org.json.simple.JSONArray();
		int cnt = filtering.getCount();
		String data = filtering.getData();
		if (filtering.getPeriod().equals("")) {
			jreturn=stat.stats(cnt);
		}
		switch(filtering.getPeriod()) {
		case "Daily":
		case"DAILY":
		case"daily":
			if (cnt!=0) {
				jreturn = tempservice.filterPeriod(cnt,data,1);
			}
			
		}
		return jreturn;
	}
	
}
