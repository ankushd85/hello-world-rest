package com.org.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author pfe456
 *
 */
@Component
public class RESTDemoBonusService {

	/**
	 * @param Int num
	 * @return List of Fibonacci with the first N(num) Fibonacci numbers 
	 * @throws Exception
	 */
	public List<Integer> getFibonacciArray(Integer num) throws Exception
    {
		if (num == 0){
			
			return Arrays.asList(0);
	       
	    }else if(num == 1){
	    	
	    	return Arrays.asList(0,1);
	    	
	    }else{
	    	
	    	List<Integer> fibList = new ArrayList<Integer>(num+1);
			
	    	fibList.add(0);
	    	fibList.add(1);
			
			int a = 0, b = 1, c;
	    	for (int i = 2; i <= num; i++)
	        {
	    		c = a + b;
	            a = b;
	            b = c;
	            
	            // Break once it goes negative
	        /*    if(b<0)
	            	break; */
	            
	            fibList.add(b);
	        }
	          
	        return fibList;
	    }
    }
}
