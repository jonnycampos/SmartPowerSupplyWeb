package com.sps.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:tuya.properties")
@ConfigurationProperties("tuya")
@Primary
public class TuyaConfiguration {

	private String apiURL;
	private String clientId;
    private String secret;
	private String defaultDeviceId;
	
	private String getLogsParamsStartRowKey; 
	private String getLogsParamsType;
	private String getLogsParamsSize;
	
	public String getApiURL() {
		return apiURL;
	}
	public void setApiURL(String apiURL) {
		this.apiURL = apiURL;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getDefaultDeviceId() {
		return defaultDeviceId;
	}
	public void setDefaultDeviceId(String defaultDeviceId) {
		this.defaultDeviceId = defaultDeviceId;
	}
	public String getGetLogsParamsStartRowKey() {
		return getLogsParamsStartRowKey;
	}
	public void setGetLogsParamsStartRowKey(String getLogsParamsStartRowKey) {
		this.getLogsParamsStartRowKey = getLogsParamsStartRowKey;
	}
	public String getGetLogsParamsType() {
		return getLogsParamsType;
	}
	public void setGetLogsParamsType(String getLogsParamsType) {
		this.getLogsParamsType = getLogsParamsType;
	}
	public String getGetLogsParamsSize() {
		return getLogsParamsSize;
	}
	public void setGetLogsParamsSize(String getLogsParamsSize) {
		this.getLogsParamsSize = getLogsParamsSize;
	}
	
	
	
	
}
