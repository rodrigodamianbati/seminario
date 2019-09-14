package modelos;

public class Categoria {

	// Variables
	private int id;
	private String nombre;
	
	// Constructores
	public Categoria() { }
	
	public Categoria(String nombre) {
		validarCategoria(nombre);
		this.nombre = nombre;
	}
	
	public Categoria(int id, String nombre) {
		validarCategoria(nombre);
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * Metodo que valida que el nombre no este vacio.
	 * 
	 * @param nombre
	 *            parametro del tipo String
	 */
	private void validarCategoria(String nombre) {
		if (nombre == "" || nombre.isEmpty() || nombre.length() == 0){
			throw new RuntimeException("Ingrese un nombre");
		}
	}
	
	/**
	 * Metodo para mostrar la categoria
	 * 
	 * @return String
	 * 				Devuelve un string de categoria
	 */
	public String toString() {
		return nombre;
	}
	
	// Getters
	public int getId() {
		return this.id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
}
