package com.org.app.beans;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author pfe456
 *
 */
public class Error implements Serializable {

	private static final long serialVersionUID = -1321560863574217018L;

	private int code;
	private String userMessage;
	private String developerText;
	private String moreInfo;
	
	public Error(){}
	
	public Error(int code, String userMessage, String developerText, String moreInfo){
		this.code = code;
		this.userMessage = userMessage;
		this.developerText = developerText;
		this.moreInfo = moreInfo;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	public String getDeveloperText() {
		return developerText;
	}
	public void setDeveloperText(String developerText) {
		this.developerText = developerText;
	}
	public String getMoreInfo() {
		return moreInfo;
	}
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}
	
	 @Override
	    public String toString() {
	    	ObjectMapper mapper = new ObjectMapper();
	    	
	    	String jsonString = "";
			try {
				mapper.enable(SerializationFeature.INDENT_OUTPUT);
				jsonString = mapper.writeValueAsString(this);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
	    	return jsonString;
	    }
}
