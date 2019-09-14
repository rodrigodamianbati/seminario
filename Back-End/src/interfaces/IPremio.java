package interfaces;

import java.util.List;

import modelos.Premio;

public interface IPremio {

	public void insertar(Premio premio);

	public List<Premio> devolverPremios();

	public boolean verificarExistente(String nombre);
	
	public void modificar(Premio premio);
	
	public void eliminar(int id);
}
