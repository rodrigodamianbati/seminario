package excepciones;

import java.sql.SQLException;

public class ParticipanteExcepcion extends SQLException{
	
	
	 public ParticipanteExcepcion(String errorMessage) {
	        super(errorMessage);
	        if (errorMessage.contains("dni")) {
	        	throw new RuntimeException("este dni ya existe entre los usuarios");
	        }else {
	        	if (errorMessage.contains("email")) {
	        		throw new RuntimeException("este email ya existe entre los usuarios");
	        	}
	        }
  	
	 }
	

}
