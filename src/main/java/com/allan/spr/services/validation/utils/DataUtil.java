package com.allan.spr.services.validation.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtil {
	
	public static String localDateFormat (LocalDate date) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		return date.format(formatter);
		
	}

}
