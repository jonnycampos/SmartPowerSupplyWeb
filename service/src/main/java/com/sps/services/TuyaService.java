package com.sps.services;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.sps.configuration.DeviceSimulatorConfiguration;
import com.sps.configuration.LabelingConfiguration;
import com.sps.configuration.TuyaConfiguration;
import com.sps.repository.ElectricalSampleRepository;
import com.sps.services.electricaldata.bo.ElectricalSample;
import com.sps.services.electricaldata.bo.ElectricalSampleList;
import com.sps.util.SPSUtils;




@Configuration
@EnableConfigurationProperties({TuyaConfiguration.class})
@Service
public class TuyaService {

	Logger logger = LoggerFactory.getLogger(TuyaService.class);

    @Autowired
    private TuyaConfiguration tuyaConfig;

    @Autowired
    private ElectricalSampleRepository repositorySample;
    
	
    
    /**
     * Get from Tuya Cloud the electrical samples from the initial to the end timestamps
     * @param initialTime - Timestamp initial
     * @param endTime - Timestamp end
     * @return
     */
	public ElectricalSampleList getDataFromDevice(LocalDateTime initialTime, LocalDateTime endTime) {
		
		try {
			String timestamp = String.valueOf(System.currentTimeMillis());
			
			//Get Token API
			String token = getToken(timestamp);
			
			//Use timestamp init and log size to call the api n times with proper timestamps
			Long initialTimestamp = SPSUtils.fromDateToTimestamp(initialTime);
			Long endTimestamp = SPSUtils.fromDateToTimestamp(endTime);
			while (initialTimestamp != null) {
				initialTimestamp = extractDataFromDevice(initialTimestamp, endTimestamp, token); 
			}
			
		} catch (Exception e) {
			logger.error("Error accesing Tuya Cloud ", e);
		}
        
		return null;
	}



	private Long extractDataFromDevice(Long initialTime, Long endTime, String token)
			throws IOException, InterruptedException {
		String timestamp;
		timestamp = String.valueOf(System.currentTimeMillis());
		String uri = tuyaConfig.getApiURL()+ "devices/" +tuyaConfig.getDefaultDeviceId()+ "/logs";
		uri = uri + "?";
		uri = uri + "start_row_key" + "=" + tuyaConfig.getGetLogsParamsStartRowKey();
		uri = uri + "&" + "type" + "=" + tuyaConfig.getGetLogsParamsType();
		uri = uri + "&" + "size" + "=" + tuyaConfig.getGetLogsParamsSize();
		uri = uri + "&" + "start_time" + "=" + initialTime;
		uri = uri + "&" + "end_time" + "=" + endTime;
		logger.info("Get data from device: " + uri);
		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
		HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
		        .header("Content-Type", "application/json")
		        .header("client_id", tuyaConfig.getClientId())
		        .header("sign", calcSign(tuyaConfig.getClientId(), tuyaConfig.getSecret(), token, timestamp))
		        .header("t", timestamp)
		        .header("sign_method", "HMAC-SHA256")
		        .header("access_token", token)
		        .GET().build();
		HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
		
		JSONObject jsonObject=  new JSONObject(response.body()).getJSONObject("result");
		logger.info("Response:" + jsonObject);
		JSONArray array = jsonObject.getJSONArray("logs");
		storeElectricalSamples(array);
		
		//TODO: Extract data from jsonObject, decodify and store in repository
		if (jsonObject.getBoolean("has_next")) {
			//We need to return the last timestamp
			JSONObject lastObject = array.getJSONObject(array.length()-1);
			return lastObject.getLong("event_time");
		} else {
			return null;	
		}
	}


	/**
	 * Store in the repository a list of electrical samples from Tuya API response
	 * @param array List of objects like this:
	 * {"code":"data_transmite","event_from":"1","event_id":7,"event_time":1606071142254,"status":"1","value":"FAsWCjQAAAAAAAB5AAAAAHUAAAAAeQAAAAB5AAAAAHUAAAAAeQAAAAB9AAAAAHMAAAAAeQAAAAB9AAAA"}
	 */
	private void storeElectricalSamples(JSONArray array) {
		for (int i = 0 ; i < array.length() ; i++) {
			JSONObject electricalDataObject = array.getJSONObject(i);
			String value = electricalDataObject.getString("value");
			//From Base64 to HEX
			//FAsWCjQAAAAAAAB5AAAAAHUAAAAAeQAAAAB5AAAAAHUAAAAAeQAAAAB9AAAAAHMAAAAAeQAAAAB9AAAA
			byte[] decoded = Base64.getDecoder().decode(value);
			String hex = String.format("%040x", new BigInteger(1, decoded));
			//14 0b 16 0a 34 00 00 00 00 00 00 79 00 00 00 00 75 00 00 00 00 79 00 00 00 00 79 00 00 00 00 75 00 00 00 00 79 00 00 00 00 7d 00 00 00 00 73 00 00 00 00 79 00 00 00 00 7d 00 00 00
			//And now we can extract the data that is stored in HEX Chars
		    ArrayList<Integer> list = new ArrayList<Integer>();
			for (int n = 0; n < hex.length(); n += 2) {
		        String str = hex.substring(n, n + 2);
		        list.add(Integer.parseInt(str, 16));
		    }
			storeElectricalSamplesFromDecodedValues(list);
		 
		}
	}


	/**
	 * Once the values are decoded accordingly we can store them as electrical values
	 * @param list Of the integer values decoded
	 * 
	 */
	private void storeElectricalSamplesFromDecodedValues(ArrayList<Integer> list) {
		//First some values of the date
		Integer year = list.get(0) + 2000;
		Integer month = list.get(1);
		Integer day = list.get(2);
		Integer hours = list.get(3);
		Integer minutes = list.get(4);
		Integer seconds = list.get(6);
		Integer milliseconds = list.get(8);
		String dateWithoutMilliseconds = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
		for (int n=0; n<10; n++) {
			String dateWithMilliseconds = dateWithoutMilliseconds + "." + (milliseconds+(100*n));
			Integer v = 11 + n*5;
			Integer a = 13 + n*5;
			//We can insert this value in the repository already
			logger.info("Time to store:" + dateWithMilliseconds);
			//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s.S");
			DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		            .appendPattern("yyyy-M-d H:m:s")
		            .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 3, true)
		            .toFormatter();
			LocalDateTime time = LocalDateTime.parse(dateWithMilliseconds, formatter);
			ElectricalSample electricalSample = new ElectricalSample(time, a, v);	
			repositorySample.save(electricalSample);
			logger.info("Storing from Tuya:" + time + "," + a + "," + v);
		}
	}



	private String getToken(String timestamp) throws IOException, InterruptedException {
		String uri = tuyaConfig.getApiURL() + "token?grant_type=1";
		logger.info("Retrieving token from tuya: " + uri);
		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
		HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
				            .header("client_id", tuyaConfig.getClientId())
				            .header("sign", calcSign(tuyaConfig.getClientId(), tuyaConfig.getSecret(), null, timestamp))
				            .header("t", timestamp)
				            .header("sign_method", "HMAC-SHA256")
				            .GET().build();
		logger.info("Request: " + request);
		HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
		String responseParsed = response.body();
		logger.info("Response: " + response.body());
		String token = new JSONObject(responseParsed).getJSONObject("result").getString("access_token");
		logger.info("Token from tuya - " + token);
		return token;
	}

	
	
	/**
	 * Create the signature needed to get a token
	 * @param clientId
	 * @param secret
	 * @return
	 */
	private String calcSign(String clientId,String secret, String token, String timestamp){
	    try {
	    	logger.info("Creating sign: clientId-" + clientId + ",timestamp-"+timestamp + ",token-" +token);
	    	logger.info("String to codify -" + clientId + token + timestamp);
	    	Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
	    	SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
	    	sha256_HMAC.init(secret_key);
	    	String stringToCodify = null;
	    	if (token != null) {
	    		stringToCodify = clientId + token + timestamp;
	    	} else {
	    		stringToCodify = clientId + timestamp;
	    	}
	    	byte[] hmacBytes = sha256_HMAC.doFinal((stringToCodify).getBytes("UTF-8"));
	    	String sigHex = byteArrayToHex(hmacBytes).toUpperCase();
	    	logger.info("Sign -" + sigHex);
	    	return sigHex;
	    } catch (Exception e) {
	    	logger.error("Error creating the sign for the Tuya Token ", e);
	    	return null;
	    }
	}
	
	
	public static String byteArrayToHex(byte[] a) {
		   StringBuilder sb = new StringBuilder(a.length * 2);
		   for(byte b: a)
		      sb.append(String.format("%02x", b));
		   return sb.toString();
		}
	
}
