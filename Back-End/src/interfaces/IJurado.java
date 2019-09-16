package interfaces;

import java.util.List;

import modelos.Jurado;

public interface IJurado {

	public void insertar(Jurado jurado);

	public List<Jurado> devolverJurados();

	public boolean verificarExistente(String nombre, String apellido);
	
	public void modificar(Jurado jurado);
	
	public void eliminar(int id);

	public Jurado jurado(int id_jurado);
}
