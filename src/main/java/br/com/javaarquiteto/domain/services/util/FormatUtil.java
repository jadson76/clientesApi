package br.com.javaarquiteto.domain.services.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatUtil {
	
	public static String getDataFormatada(LocalDate data) throws ParseException {
		
		 DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");        
	      
	     return data.format(formatoSaida);
		
	}

}
