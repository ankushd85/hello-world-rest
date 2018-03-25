package com.org.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author pfe456
 * App config to load configurations from application.properties
 */
@Component
@ConfigurationProperties()
public class AppConfig {

	private String user;
	private String password;
	private String isProxy;
	private int proxyPort;
	private String proxyURL;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsProxy() {
		return isProxy;
	}
	public void setIsProxy(String isProxy) {
		this.isProxy = isProxy;
	}
	public int getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}
	public String getProxyURL() {
		return proxyURL;
	}
	public void setProxyURL(String proxyURL) {
		this.proxyURL = proxyURL;
	}
	
}
