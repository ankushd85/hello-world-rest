package com.org.app.service;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.org.app.config.AppConfig;

/**
 * @author pfe456
 *
 */
@Component
@ConfigurationProperties()
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RESTDemoAPICallTemplateService {

	@Autowired
	private AppConfig config;
	
	/**
	 * Returns RestTemplate object for application which can be used
	 * for making rest api calls
	 * 
	 * @return RestTemplate object
	 */
	public RestTemplate getRestTemplateBuilder(){
		// Set default HTTP Request settings
		SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		
		// 1 min timeout for connection
		clientHttpRequestFactory.setConnectTimeout(60000); 
		
		//2 min timeout for reading response
		clientHttpRequestFactory.setReadTimeout(120000); 
		
		if(Boolean.parseBoolean(config.getIsProxy())){
			Authenticator.setDefault(
					   new Authenticator() {
					      @Override
					      public PasswordAuthentication getPasswordAuthentication() {
					         return new PasswordAuthentication(
					        		 config.getUser(), config.getPassword().toCharArray());
					      }
					   }
					);
			
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(config.getProxyURL(), config.getProxyPort()));
			clientHttpRequestFactory.setProxy(proxy);
		}
		
		return new RestTemplate(clientHttpRequestFactory);
		
	}
	
}
