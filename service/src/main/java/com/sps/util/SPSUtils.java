package com.sps.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;




public class SPSUtils {

	
	
	
	public static double mean(List<Integer> m) {
		double sum = 0;
	    for (int i = 0; i < m.size(); i++) {
	        sum += m.get(i);
	    }
	    return sum / m.size();
	}
	
	
	public static double median(List<Integer> m) {
	    int middle = m.size()/2;
	    if (m.size()%2 == 1) {
	        return m.get(middle);
	    } else {
	        return (m.get(middle) + m.get(middle)) / 2.0;
	    }
	}
	
	
	public static int mode(List<Integer> m) {
	    int maxValue = 0;
	    int maxCount = 0;

	    for (int i = 0; i < m.size(); ++i) {
	        int count = 0;
	        for (int j = 0; j < m.size(); ++j) {
	            if (m.get(j) == m.get(i)) ++count;
	        }
	        if (count > maxCount) {
	            maxCount = count;
	            maxValue = m.get(i);
	        }
	    }

	    return maxValue;
	}
	
	public static long fromDateToTimestamp(LocalDateTime localDateTime) {
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();	
		long timeInMillis = instant.toEpochMilli(); 
		return timeInMillis;
	}
}
