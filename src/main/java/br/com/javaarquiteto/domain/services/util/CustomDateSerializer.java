package br.com.javaarquiteto.domain.services.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomDateSerializer extends StdSerializer<LocalDate> {	

	private static final long serialVersionUID = 1L;
	
	private SimpleDateFormat formatter 
     = new SimpleDateFormat("dd/MM/yyyy");

   public CustomDateSerializer() {
       this(null);
   }

   public CustomDateSerializer(Class t) {
       super(t);
   }
   
   @Override
   public void serialize (LocalDate value, JsonGenerator gen, SerializerProvider arg2)
     throws IOException, JsonProcessingException {
       gen.writeString(formatter.format(value));
   }

}
