package in.ar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ar.binding.App;
import in.ar.entity.AppEntity;
import in.ar.service.ARService;

@RestController
public class ArController {
	@Autowired
	private ARService service;

	@PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> createCitizen(@RequestBody App c) {
		System.out.println(c);
     String status = service.createApplication(c);
     if(status != null) {
    	 return new ResponseEntity<>("success",HttpStatus.OK);
     }else {
    	 return new ResponseEntity<>("failed",HttpStatus.INTERNAL_SERVER_ERROR);
     }
	}
	
	@GetMapping(value="/apps/{userId}")
	public List<App> viewApplication(@PathVariable Long userId) {
		return service.fetchApps(userId);
	}
}
