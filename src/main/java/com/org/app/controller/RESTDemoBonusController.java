package com.org.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.app.beans.Error;
import com.org.app.constants.RESTDemoConstants;
import com.org.app.service.RESTDemoBonusService;

/**
 * @author pfe456
 * Main Controller for bonus API calls
 */
@RestController
@RequestMapping("/myorg/mylob/helloworld/bonus/items/")
public class RESTDemoBonusController {
	
	private static final Logger LOG = LoggerFactory.getLogger(RESTDemoBonusController.class);

	@Autowired
	RESTDemoBonusService restDemoBonusService;
	
	/**
	 * GET: API Call to get List of Fibonacci with the first N(num) Fibonacci numbers 
	 * 
	 * @param num
	 * @return
	 * 		Json string with the first N(num) Fibonacci numbers
	 */
	@GetMapping("/v1/fibonacci/{num}")  
    public ResponseEntity<String> getFibonacciForN(@PathVariable(value = "num") Integer num){  
		try{
			if(num <0){
				Error error = new Error(RESTDemoConstants.APP_ERROR_CODE_BAD_REQUEST, 
						"Number is not valid. Please send positive integer only",
						"Number is negative, Bad Request", "https://my-error-code-details-url");
				
				return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
			}
			    
			List<Integer> fibList = restDemoBonusService.getFibonacciArray(num);
			
			if(null != fibList && !fibList.isEmpty()){
				
				return new ResponseEntity<String>(fibList.toString() ,  HttpStatus.OK);
				
			}else{
				Error error = new Error(RESTDemoConstants.APP_ERROR_CODE_SERVICE_NOT_AVAILABLE, 
						"Sorry, the requested service is not available currently. Please call 222-222-4455 for more information.",
						"Exception occured in getPosts api call.", "https://my-error-code-details-url");
				return new ResponseEntity<String>(error.toString() ,  HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage());
			
			Error error = new Error(RESTDemoConstants.APP_ERROR_CODE_SERVICE_NOT_AVAILABLE, 
					"Sorry, the requested service is not available currently. Please call 222-222-4455 for more information.",
					"Exception occured in getPosts api call.", "https://my-error-code-details-url");
			return new ResponseEntity<String>(error.toString() ,  HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }  
	
}
