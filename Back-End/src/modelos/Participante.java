package modelos;

public class Participante {

	// Variables
	private int id;
	private String nombre;
	private String apellido;
	private String email;
	private String dni;
	private Publicacion publicacion;

	//Constructores
	public Participante() {}
	
	public Participante(int id) {
		this.id = id;
	}
	
	public Participante(int id, String nombre, String apellido, String dni, String email) {
		validarParticipante(nombre, apellido, email, dni);
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.dni = dni;
	}
	
	public Participante(String nombre, String apellido, String dni, String email) {
		validarParticipante(nombre, apellido, email, dni);
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.dni = dni;
	}
	
	public Participante(int id, String nombre, String apellido, String dni) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}
	
	public boolean equals(Participante p) {
		return (this.dni.equals(p.dni));
	}
	
	/**
	 * Metodo que valida que el nombre, apellido, email y dni no esten vacios.
	 * 
	 * @param nombre
	 *            parametro del tipo String
	 * @param apellido
	 *            parametro del tipo String
	 * @param dni
	 *            parametro del tipo String
	 * @param email
	 *            parametro del tipo String   
	 *         
	 */
	private void validarParticipante(String nombre, String apellido, String email, String dni) {
		if (nombre == "" || nombre.isEmpty()) {
			throw new RuntimeException("Ingrese un nombre");
		}
		if(apellido == "" || apellido.isEmpty()) {
			throw new RuntimeException("Ingrese un apellido");
		}
		if(dni == "" || dni.isEmpty()) {
			throw new RuntimeException("Ingrese un dni");
		}
		if (dni.length() > 8) {
			throw new RuntimeException("El numero de dni es muy largo");
		}
		if(email == "" || email.isEmpty()) {
			throw new RuntimeException("Ingrese un email");
		}
	}
	
	public void agregarPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}
	
	// Getters
	public String getEmail() {
		return this.email;
	}
	
	public String getDni() {
		return this.dni;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getApellido() {
		return this.apellido;
	}

	public int getId() {
		return this.id;
	}
	
	public Publicacion getPublicacion() {
		return this.publicacion;
	}
	
	public String toString() {
		return "id: " + id + " " + " nombre: " + nombre + " apellido: " + apellido + " email: " + email + " dni " + dni;
	}
}
