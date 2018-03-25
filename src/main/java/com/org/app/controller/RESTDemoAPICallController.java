package com.org.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.app.beans.Error;
import com.org.app.constants.RESTDemoConstants;
import com.org.app.service.RESTDemoAPICallTemplateService;

/**
 * @author pfe456
 * Main Controller for external API calls
 */
@RestController
@RequestMapping("/myorg/mylob/helloworld/external/apis")
public class RESTDemoAPICallController {

	private static final Logger LOG = LoggerFactory.getLogger(RESTDemoAPICallController.class);
	
	@Autowired
	RESTDemoAPICallTemplateService restDemoAPICallTemplateService;
	
	/**
	 * GET: API Call to hit external api and get posts
	 * 
	 * @return Json String in response from getposts external api call
	 */
	@GetMapping("/v1/posts")  
    public ResponseEntity<String> getPosts(){  
		ResponseEntity<String> res = null;
		try{
			res = restDemoAPICallTemplateService.getRestTemplateBuilder().
					getForEntity("https://jsonplaceholder.typicode.com/posts", String.class);
			
		
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage());
			
			Error error = new Error(RESTDemoConstants.APP_ERROR_CODE_SERVICE_NOT_AVAILABLE, 
					"Sorry, the requested service is not available currently. Please call 222-222-4455 for more information.",
					"Exception occured in getPosts api call.", "https://my-error-code-details-url");
			return new ResponseEntity<String>(error.toString() ,  HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return res;
    }  
}
