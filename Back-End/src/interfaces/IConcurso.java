package interfaces;

import java.util.List;

import modelos.Concurso;

public interface IConcurso {

	public void insertar(Concurso c);

	public List<Concurso> devolverConcursos();

	public boolean verificarExistente(String codigo);
	
	public List<Concurso> listarConcursosConInscripcionAbierta();
	
	public List<Concurso> listarConcursosParticipanteInscripto(int id_participante);
	
	public List<Concurso> listarConcursosConInscripcionCerradaInscripto(int id_participante);
	
	public List<Concurso> listarConcursosConPublicacionAbierta(int id_participante);
	
	public void modificar(Concurso c);
	
	public void eliminar(int id);
	
	public void corregir(int id);

	public boolean estadoConcurso(Concurso concurso);

	public Concurso concurso(int concursoCodigo);

	public void nuevaInscripcion(Concurso concurso);

	public Concurso concurso(String concurso);

	public Concurso concursoDadoId(int id_concurso);

	
}
