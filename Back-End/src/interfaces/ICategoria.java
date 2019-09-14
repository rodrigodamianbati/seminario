package interfaces;

import java.util.List;
import modelos.Categoria;

public interface ICategoria {
	
	public void insertar(Categoria c);
	
	public boolean verificarExistente(String nombre);

	public List<Categoria> categorias();
	
	public Categoria categoria(String nombre);
	
	public void modificar(Categoria categoria);
	
	public void eliminar(int id);
}