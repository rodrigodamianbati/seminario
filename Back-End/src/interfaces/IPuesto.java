package interfaces;

import java.util.List;

import modelos.Puesto;

public interface IPuesto {

	public void insertar(Puesto puesto);

	public List<Puesto> puestos(int id);

	public boolean verificarExistente(int id, String posicion);
	
	public void asignarGanador(int id_puesto, int id_participante);
	
	public void eliminar(int id, int id_concurso);
}
