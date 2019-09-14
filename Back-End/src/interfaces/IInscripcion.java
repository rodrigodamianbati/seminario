package interfaces;

import modelos.Inscripcion;

public interface IInscripcion {

	public void insertar(Inscripcion i);
	
	public boolean verificarExistente(int id_participante, int id_concurso);
	
	public void eliminar(int id_participante, int id_concurso);
}
