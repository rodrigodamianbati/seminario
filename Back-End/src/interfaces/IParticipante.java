package interfaces;

import java.util.List;

import modelos.Participante;

public interface IParticipante {

	public void insertar(Participante participante);

	public List<Participante> devolverParticipantes();
	
	public List<Participante> devolverParticipantesConcurso(int id_concurso);
	
	public List<Participante> devolverParticipantesConcursoConPublicacion(int id_concurso);

	public boolean verificarExistente(String dni);
	
	public void modificar(Participante participante);
	
	public void eliminar(int id);
}
