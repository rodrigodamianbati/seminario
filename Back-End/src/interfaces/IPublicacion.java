package interfaces;

import java.util.List;

import modelos.Publicacion;

public interface IPublicacion {

	public void insertar(Publicacion p);
	
	public List<Publicacion> devolverPublicacionesAntiguas(int id_concurso, int id_participante);
	
	public List<Publicacion> listarPublicacionesConcurso(int id_concurso, int id_participante);

	public void asignarJuradoPuntaje(int puntaje, int id_publicacion, int id_jurado);
	
	public void eliminar(int id);
}
