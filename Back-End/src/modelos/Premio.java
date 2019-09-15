package modelos;

public class Premio {

	// Variables
	private int id;
	private String nombre;
	
	// Constructores
	public Premio() { }
	
	public Premio(String nombre) {
		validarPremio(nombre);
		this.nombre = nombre;
	}
	
	public Premio(int id, String nombre) {
		validarPremio(nombre);
		this.id = id;
		this.nombre = nombre;
	}
	
	/**
	 * Metodo que valida que el nombre del premio no este vacio.
	 * 
	 * @param nombre
	 *            parametro del tipo String
	 *            
	 */
	private void validarPremio(String nombre) {
		if (nombre == "" || nombre.isEmpty()) {
			throw new RuntimeException("Ingrese un nombre");
		}
	}
	
	public String toString() {
		return this.nombre;
	}
	
	// Getters
	public int getId() {
		return this.id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
}
