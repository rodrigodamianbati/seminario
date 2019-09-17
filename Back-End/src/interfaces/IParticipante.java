package interfaces;

import java.util.List;

import excepciones.ParticipanteExcepcion;
import modelos.Participante;

public interface IParticipante {

	public void insertar(Participante participante);

	public List<Participante> devolverParticipantes();
	
	public List<Participante> devolverParticipantesConcurso(int id_concurso);
	
	public List<Participante> devolverParticipantesConcursoConPublicacion(int id_concurso);

	public boolean verificarExistente(String dni);
	
	public void modificar(Participante participante);
	
	public void eliminar(int id);

	public List<Participante> participantesPorSeleccion(String seleccion);

	public List<Participante> noParticipantesPorSeleccion(String seleccion);

	public Participante participante(int id);

	public Participante participanteConInscripciones(int id_participante);
}
