package com.maveric.csp.conversion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppConversions {

	public static String convertDateToString(LocalDateTime currentDate) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		String convertedDate = currentDate.format(dateTimeFormatter);

		return convertedDate;

	}

	public static LocalDate convertStringToDate(String followupOn) {
	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    if (followupOn != null) {
	    	 LocalDate convertedDate = LocalDate.parse(followupOn, dateTimeFormatter);
	    	  return convertedDate;
		}
		return null;
	  
	}

}
	
//	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
////	public static LocalDateTime convertStringToDate(String followupOn) {
////		// TODO Auto-generated method stub
////		return null;
////	}
////	
//	 public static LocalDateTime convertStringToDate(String dateString) {
//	        if (dateString == null || dateString.isEmpty()) {
//	            return null;
//	        }
// 
//	        return LocalDateTime.parse(dateString, formatter);
//	    }
//}
