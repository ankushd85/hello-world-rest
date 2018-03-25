package com.org.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pfe456
 * Main Controller for root url to provide info
 */
@RestController
public class RESTDemoInfoController {

	/**
	 * This method provides info to user on root url
	 * @return 
	 * 		String message
	 */
	@GetMapping("/")
	public ResponseEntity<String> getInfo(){
		return new ResponseEntity<String>("<html><body><h2>"
				+ "Please visit <a href='https://github.com/ankushd85/hello-world-rest'>Here</a> "+
					" for more info </h2></body></html>", HttpStatus.OK);
	}
}
