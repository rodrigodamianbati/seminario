package daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class ConexionDB {
	
	private ResourceBundle bundle;

	/**
	* Metodo para conectar con la base de datos
	* 
	* @return Conexion
	*/
	public Connection obtenerConexionBD() {
		try {
			bundle = ResourceBundle.getBundle("properties/db");
			String url = bundle.getString("url");
			String user = bundle.getString("user");
			String password = bundle.getString("password");
			String driver = bundle.getString("driver");
			Class.forName(driver);
				
			Connection connection = DriverManager.getConnection(url, user, password);
				
			return connection;
		} catch (Exception ex) {
			throw new RuntimeException("Error al tratar de conectar con la base de datos."
					+ "\nPor favor ingrese mas tarde.");
		}
	}
}
