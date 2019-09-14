package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.IPuesto;
import modelos.Concurso;
import modelos.Participante;
import modelos.Premio;
import modelos.Puesto;

public class PuestoDAO implements IPuesto {
	
	private String insertar = "INSERT INTO puesto(posicion, id_participante, id_premio, id_concurso) VALUES(?, ?, ?, ?)";
	private String listar = "SELECT pues.id, pues.posicion, pues.id_participante, part.nombre AS nombre_participante, "
			+ "part.apellido, part.dni, pues.id_premio, prem.nombre AS nombre_premio, pues.id_concurso, "
			+ "con.nombre AS nombre_concurso "
			+ "FROM puesto pues JOIN premio prem ON (pues.id_premio = prem.id) JOIN concurso con ON "
			+ "(pues.id_concurso = con.id) LEFT JOIN participante part ON (pues.id_participante = part.id) "
			+ "WHERE id_concurso = ? "
			+ "ORDER BY pues.posicion ASC";
	private String verificar = "SELECT posicion FROM puesto WHERE id_concurso = ? and posicion = ?";
	private String eliminar = "DELETE FROM puesto WHERE id = ? and id_concurso = ?";
	private String asignarGanador = "UPDATE puesto SET id_participante = ? WHERE id = ?";

	/**
	 * Metodo que inserta un nuevo puesto en la base de datos.
	 * 
	 * @param puesto
	 *            parametro del tipo Puesto
	 */
	public void insertar(Puesto puesto) {
		ConexionDB conexion_db = new ConexionDB();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.insertar)) {
			statement.setString(1, puesto.getPosicion());
			statement.setString(2, null);
			statement.setInt(3, puesto.getPremio().getId());
			statement.setInt(4, puesto.getConcurso().getId());
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Error en insertar nuevo puesto");
		}
	}

	/**
	 * Metodo que devuelve todos los puestos.
	 * 
	 * @return lista de objetos Puesto
	 */
	public List<Puesto> puestos(int id) {
		ConexionDB conexion_db = new ConexionDB();
		List<Puesto> lista = new ArrayList<Puesto>();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.listar)) {
			statement.setInt(1, id);
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					Participante participante = new Participante(rs.getInt("id_participante"), rs.getString("nombre_participante"),
							rs.getString("apellido"), String.valueOf(rs.getInt("dni")));
					Premio premio = new Premio(rs.getInt("id_premio"), rs.getString("nombre_premio"));
					Concurso concurso = new Concurso(rs.getInt("id_concurso"), rs.getString("nombre_concurso"));
					lista.add( new Puesto(rs.getInt("id"), rs.getString("posicion"), participante, premio, concurso));
				}
				return lista;
			} catch (SQLException e) {
				throw new RuntimeException("Error al cargar la lista de puestos");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al cargar la lista de puestos");
		}
	}

	/**
	 * Metodo que verifica si existe un puesto para el concurso en la base de datos.
	 * 
	 * @param id_concurso
	 *            parametro de tipo int
	 * @param posicion
	 *            parametro de tipo String
	 */
	public boolean verificarExistente(int id_concurso, String posicion) {
		ConexionDB conexion_db = new ConexionDB();
		List<Puesto> lista = new ArrayList<Puesto>();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.verificar)) {
			statement.setInt(1, id_concurso);
			statement.setString(2, posicion);
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					lista.add(new Puesto(rs.getString("posicion")));
				}
				if (lista.size() == 0) {
					return false;
				} 
				return true;
			} catch (SQLException e) {
				throw new RuntimeException("Error al buscar puesto existente");
			}
		} catch (SQLException ex) {
			throw new RuntimeException("Error al buscar puesto existente");
		}
	}

	/**
	 * Metodo que elimina un puesto en la base de datos.
	 * 
	 * @param id
	 *            id del puesto a eliminar
	 * @param id_concurso
	 *            id del concurso           
	 */
	@Override
	public void eliminar(int id, int id_concurso) {
		ConexionDB conexion_db = new ConexionDB();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.eliminar)) {
			statement.setInt(1, id);
			statement.setInt(2, id_concurso);
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Error al eliminar puesto.");
		}
	}
	
	public void asignarGanador(int id_puesto, int id_participante) {
		ConexionDB conexion_db = new ConexionDB();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.asignarGanador)) {
			statement.setInt(1, id_participante);
			statement.setInt(2, id_puesto);
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Error al asignar ganador.");
		}
	}
}
