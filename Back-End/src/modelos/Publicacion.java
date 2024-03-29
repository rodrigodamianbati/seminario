package modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Publicacion {

	// Variables
	private int id;
	private Participante participante;
	private Concurso concurso;
	private String texto;
	private int puntaje;
	private Jurado jurado;
	private LocalDate fecha;
	private int hora;
	
	// Constructores
	public Publicacion() { }
	
	public Publicacion(Participante participante, Concurso concurso, String texto) {
		validarPublicacion(texto);
		this.participante = participante;
		this.concurso = concurso;
		this.texto = texto;
		this.fecha = LocalDate.now();
		this.hora = LocalDateTime.now().getHour();
	}
	
	private void validarPublicacion(String texto) {
		if (texto.isEmpty() || texto == "") {
			throw new RuntimeException("Ingrese un texto para la publicacion");
		}
	}
	
	public Publicacion(int id, String texto) {
		this.id = id;
		this.texto = texto;
	}
	
	public Publicacion(int id, String texto, int puntaje) {
		this.id = id;
		this.texto = texto;
		this.puntaje = puntaje;
	}
	
	public Publicacion(String texto) {
		this.texto = texto;
	}
	
	
	// Getters
	public int getId() {
		return this.id;
	}
	
	public LocalDate getFecha() {
		return this.fecha;
	}
	
	public int getHora() {
		return this.hora;
	}
	
	public String getTexto() {
		return this.texto;
	}
	
	public Participante getParticipante() {
		return this.participante;
	}
	
	public Concurso getConcurso() {
		return this.concurso;
	}
	
	public int getPuntaje() {
		return this.puntaje;
	}
	
	public Jurado getJurado() {
		return this.jurado;
	}

	public void evaluar() {
		// TODO Auto-generated method stub
		this.jurado.evaluar(this);
	}

	public void puntuar(int numero) {
		// TODO Auto-generated method stub
		this.puntaje = numero;
	}

	public void asignarParticipante(Participante participante2) {
		// TODO Auto-generated method stub
		this.participante = participante2;
	}
}
