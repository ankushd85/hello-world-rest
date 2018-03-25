package com.org.app.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pfe456
 *
 */
public class RESTDemoBase implements Serializable{

	private static final long serialVersionUID = 1357613839976277048L;
	
	private List<Error> errors = new ArrayList<Error>();

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
}
