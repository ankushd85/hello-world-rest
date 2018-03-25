package com.org.app.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.app.beans.Error;
import com.org.app.beans.RESTDemoBase;
import com.org.app.beans.User;
import com.org.app.constants.RESTDemoConstants;
import com.org.app.dao.RESTDemoDAO;

/**
 * @author pfe456
 * Main Controller for users api
 */
@RestController
@RequestMapping("/myorg/mylob/helloworld/v1/users")
public class RESTDemoController {

	private static final Logger LOG = LoggerFactory.getLogger(RESTDemoController.class);
	
	@Autowired
	private RESTDemoDAO restDemoDAO;
	
	/**
	 * GET: API call to get all users from database
	 * 
	 * @return
	 * 		Returns map of all users from database.
	 */
	@GetMapping()
	public Collection<User> getUsers() {
		LOG.info("/getUsers request initiated.");
		return restDemoDAO.get();
	}

	/**
	 * GET: API call to get user from database for user id requested. 
	 * Sends Bad Request if user id is negative.
	 * Sends Not Found if user id not found in database
	 * 
	 * @param userId
	 * 		User Id from query parameter. Should be valid Long
	 * 
	 * @return
	 * 		User object
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<RESTDemoBase> getUser(@PathVariable(value = "userId") Long userId){
		LOG.info("/getUser request initiated.");
		
		if(userId <0){
			return getNoUserIdErrorTemplate();
		}
		
		RESTDemoBase user = restDemoDAO.get(userId);
		
		if(null != user){
			LOG.info("User Found");
			return new ResponseEntity<RESTDemoBase>(user, this.getCommonHeaders(), HttpStatus.OK);
		}else{
			LOG.info("User Not Found with user id: "+userId);
			Error error = new Error(RESTDemoConstants.APP_ERROR_CODE_USER_NOT_FOUND, 
					"Sorry, the requested resource does not exist",
					"No User with user id "+ userId +" found in the database", "https://my-error-code-details-url");
			user = new RESTDemoBase();
			user.getErrors().add(error);
			return new ResponseEntity<RESTDemoBase>(user , this.getCommonHeaders(), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * POST: API call to add user in database. 
	 * Sends Bad Request if user object sent is null or not valid.
	 * Sends Internal Error if database service is not available
	 * 
	 * @param user
	 * 		User object from request
	 * 
	 * @return
	 * 		User object
	 */
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<RESTDemoBase> addUser(@RequestBody User user){
		LOG.info("/addUser request initiated.");
		if(!validateUser(user)){
			Error error = new Error(RESTDemoConstants.APP_ERROR_CODE_BAD_REQUEST, 
					"User Name can not be null. Please send request in correct format",
					"User name not sent, Bad Request", "https://my-error-code-details-url");
			if (user == null){
				user = new User();
			}
			user.getErrors().add(error);
			
			return new ResponseEntity<RESTDemoBase>(user,this.getCommonHeaders(), HttpStatus.BAD_REQUEST);
		}
		
		User createUser = restDemoDAO.create(user);
		if(null == createUser){
			
			Error error = new Error(RESTDemoConstants.APP_ERROR_CODE_DB_SERVICE_NOT_AVAILABLE, 
					"Sorry, request can not be completed at this time. Please try again after some time.",
					"database is in maintanance mode", "https://my-error-code-details-url");
			createUser = new User();
			createUser.getErrors().add(error);
			
			return new ResponseEntity<RESTDemoBase>(createUser,this.getCommonHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<RESTDemoBase>(createUser, HttpStatus.CREATED);
		
	}
	
	/**
	 * DELETE: API call to delete user in database. 
	 * Sends Not Found if user id not present in database.
	 * 
	 * @param user
	 * 		User object from request
	 * 
	 * @return
	 * 		User object with HTTP 200 if user deleted
	 */
	@DeleteMapping("/{userId}")
	public ResponseEntity<RESTDemoBase> deleteUser(@PathVariable Long userId) {
		LOG.info("/deleteUser request initiated.");
		
		if(userId <0){
			return getNoUserIdErrorTemplate();
		}
		
		RESTDemoBase user = new RESTDemoBase();
		
		if (null == restDemoDAO.delete(userId)) {
			LOG.info("User Not Found with user id: "+userId);
			
			Error error = new Error(RESTDemoConstants.APP_ERROR_CODE_USER_NOT_FOUND, 
					"Sorry, the requested resource does not exist",
					"No User with user id "+ userId +" found in the database", "https://my-error-code-details-url");
			user.getErrors().add(error);
			return new ResponseEntity<RESTDemoBase>(user, this.getCommonHeaders() , HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<RESTDemoBase> (user, this.getCommonHeaders(), HttpStatus.OK);

	}

	/**
	 * POST: API call to add user in database. 
	 * Sends Bad Request if user object sent is null or not valid.
	 * Sends Internal Error if database service is not available
	 * 
	 * @param userId
	 * 		User id to be updated
	 * 
	 * @param user
	 * 		User object from request
	 * 
	 * @return
	 * 		User object post update
	 * 
	 */
	@PutMapping("/{userId}")
	public ResponseEntity<RESTDemoBase> updateUser(@PathVariable Long userId, @RequestBody User user) {
		LOG.info("/updateUser request initiated.");
		
		if(userId <0){
			return getNoUserIdErrorTemplate();
		}
		
		if(!validateUser(user)){
			Error error = new Error(RESTDemoConstants.APP_ERROR_CODE_BAD_REQUEST, 
					"User Name can not be null. Please send request in correct format",
					"User name not sent, Bad Request", "https://my-error-code-details-url");
			if (user == null){
				user = new User();
			}
			user.getErrors().add(error);
			
			return new ResponseEntity<RESTDemoBase>(user,this.getCommonHeaders(), HttpStatus.BAD_REQUEST);
		}
		
		user = restDemoDAO.update(userId, user);

		if (null == user) {
			LOG.info("User Not Found with user id: "+userId);
			
			Error error = new Error(RESTDemoConstants.APP_ERROR_CODE_USER_NOT_FOUND, 
					"Sorry, the requested resource does not exist",
					"No User with user id "+ userId +" found in the database", "https://my-error-code-details-url");
			
			user = new User();
			user.getErrors().add(error);
			return new ResponseEntity<RESTDemoBase>(user , this.getCommonHeaders(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<RESTDemoBase>(user, HttpStatus.OK);
	}
	
	
	/**
	 * @return
	 * 		HttpHeaders with common headers added
	 */
	private HttpHeaders getCommonHeaders(){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		responseHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
		
		return responseHeaders;
	}
	
	/**
	 * Validation method for user object
	 * 
	 * @param user
	 * 		User object
	 * 
	 * @return
	 * 		boolean if user id valid or not
	 */
	private boolean validateUser(User user){
		boolean validation = true;
		if(user == null || user.getName() == null || user.getName().isEmpty()){
			validation = false;
		}
		return validation;
	}
	
	/**
	 * @return ResponseEntity with error message template for user id less than 0
	 */
	private ResponseEntity<RESTDemoBase> getNoUserIdErrorTemplate(){
		Error error = new Error(RESTDemoConstants.APP_ERROR_CODE_BAD_REQUEST, 
				"User Id is not valid. Please send request with correct user id",
				"User Id is negative, Bad Request", "https://my-error-code-details-url");
		
		RESTDemoBase user = new RESTDemoBase();
		user.getErrors().add(error);
		
		return new ResponseEntity<RESTDemoBase>(user,this.getCommonHeaders(), HttpStatus.BAD_REQUEST);
	}
}
