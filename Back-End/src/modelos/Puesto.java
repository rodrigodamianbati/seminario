package modelos;

public class Puesto {

	// Variables
	private int id;
	private String posicion;
	private Participante participante;
	private Premio premio;
	private Concurso concurso;
	
	// Constructores
	public Puesto () { }
	
	public Puesto (String posicion) {
		this.posicion = posicion;
	}
	
	public Puesto (int id, String posicion, Participante participante, Premio premio, Concurso concurso) {
		this.id = id;
		this.posicion = posicion;
		this.participante = participante;
		this.premio = premio;
		this.concurso = concurso;
	}
	
	public Puesto (String posicion, Concurso concurso, Premio premio) {
		validarPremio(posicion);
		this.posicion = posicion;
		this.concurso = concurso;
		this.premio = premio;
	}
	
	/**
	 * Metodo que valida que la posicion no sea vacio
	 * 
	 * @param posicion
	 * 				parametro de tipo String
	 */
	public void validarPremio(String posicion) {
	
		if (posicion == "" || posicion.isEmpty()) {
			throw new RuntimeException("Ingrese una posicion");
		}
		try {
            int num = Integer.parseInt(posicion);
            if (num < 1) {
    			throw new RuntimeException("Ingrese una posicion valida");
    		}
        } catch (NumberFormatException e) {
        	throw new RuntimeException("Solo se permiten valores numericos");
        }
	}
	
	// Getters
	public int getId() {
		return this.id;
	}
	
	public String getPosicion() {
		return this.posicion;
	}
	
	public Concurso getConcurso() {
		return this.concurso;
	}
	
	public Premio getPremio() {
		return this.premio;
	}
	
	public Participante getParticipante() {
		return this.participante;
	}
}
