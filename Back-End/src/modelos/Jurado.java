package modelos;

import java.util.concurrent.ThreadLocalRandom;

public class Jurado {

	// Variables
	private int id;
	private String nombre;
	private String apellido;
	
	// Constructores
	public Jurado(){ }
	
	public Jurado(String nombre, String apellido) {
		validarJurado(nombre, apellido);
		this.nombre = nombre;
		this.apellido = apellido;
	}
	
	public Jurado(int id, String nombre, String apellido) {
		validarJurado(nombre, apellido);
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	/**
	 * Metodo que valida que el nombre y apellido del jurado no este vacio.
	 * 
	 * @param nombre
	 *            parametro del tipo String
	 *            
	 * @param apellido
	 *            parametro del tipo String
	 */
	private void validarJurado(String nombre, String apellido) {
		if (nombre.isEmpty() || nombre == "") {
			throw new RuntimeException("Ingrese un nombre");
		}
		if (apellido.isEmpty() || apellido == "") {
			throw new RuntimeException("Ingrese un apellido");
		}
	}
	
	public String toString() {
		return this.nombre + " " + this.apellido;
	}
	
	// Getters
	public int getId() {
		return this.id;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public String getApellido(){
		return this.apellido;
	}

	public void evaluar(Publicacion publicacion) {
		// TODO Auto-generated method stub
		int randomInt = ThreadLocalRandom.current().nextInt(1, 10);
		publicacion.puntuar(randomInt);
	}
	
}