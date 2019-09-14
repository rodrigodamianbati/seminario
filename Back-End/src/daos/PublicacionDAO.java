package daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.IPublicacion;
import modelos.Publicacion;

public class PublicacionDAO implements IPublicacion {
	
	private String insertar = "INSERT INTO publicacion(texto, id_participante, id_concurso, fecha_creacion_publicacion) VALUES (?, ?, ?, ?)";
	private String publicacionesAntiguas = "SELECT p.texto FROM publicacion p JOIN concurso c ON (p.id_concurso = c.id) "
			+ "WHERE CURDATE()+5 > DATE(c.fechaFinPublicacion) AND p.id_participante = ? AND c.id = ?";
	private String publicacionConcurso = "SELECT p.id, p.texto FROM publicacion p JOIN concurso c ON (p.id_concurso = c.id) "
			+ "WHERE p.id_participante = ? AND c.id = ?";
	private String eliminar = "DELETE FROM publicacion WHERE id = ?";
	private String asignarJurado = "UPDATE publicacion SET id_jurado = ?, puntaje = ? WHERE id = ?";

	/**
	 * Metodo que agrega una publicacion
	 */
	@Override
	public void insertar(Publicacion p) {
		ConexionDB conexion_db = new ConexionDB();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.insertar)) {
			statement.setString(1, p.getTexto());
			statement.setInt(2, p.getParticipante().getId());
			statement.setInt(3, p.getConcurso().getId());
			//statement.setString(4, Date.valueOf(LocalDate.now()).toString());
			statement.setString(4, Date.valueOf(p.getFecha()).toString());
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Error al insertar publicacion");
		}
	}

	/**
	 * Metodo que devuelve las publicaciones del concurso con publicacion finalizada
	 */
	@Override
	public List<Publicacion> devolverPublicacionesAntiguas(int id_concurso, int id_participante) {
		ConexionDB conexion_db = new ConexionDB();
		List<Publicacion> lista = new ArrayList<Publicacion>();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.publicacionesAntiguas)) {
			statement.setInt(1, id_participante);
			statement.setInt(2, id_concurso);
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					lista.add(
						new Publicacion(rs.getString("texto"))
					);
				}
				return lista;
			} catch (SQLException e) {
				throw new RuntimeException("Error al cargar la lista de publicaciones.");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al cargar la lista de publicaciones.");
		}
	}
	
	/**
	 * Metodo que devuelve una lista de las ultimas publicaciones de cada participante
	 */
	@Override
	public List<Publicacion> listarPublicacionesConcurso(int id_concurso, int id_participante) {
		ConexionDB conexion_db = new ConexionDB();
		List<Publicacion> lista = new ArrayList<Publicacion>();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.publicacionConcurso)) {
			statement.setInt(1, id_participante);
			statement.setInt(2, id_concurso);
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					lista.add(
						new Publicacion(rs.getInt("id"), rs.getString("texto"))
					);
				}
				return lista;
			} catch (SQLException e) {
				throw new RuntimeException("Error al cargar la lista de publicaciones.");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al cargar la lista de publicaciones.");
		}
	}
	
	/**
	 * Metodo que elimina una publicacion en la base de datos.
	 * 
	 * @param id
	 *            variable de tipo int
	 */
	public void eliminar(int id) {
		ConexionDB conexion_db = new ConexionDB();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.eliminar)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Error al eliminar la publicacion");
		}
	}

	@Override
	public void asignarJuradoPuntaje(int puntaje, int id_publicacion, int id_jurado) {
		ConexionDB conexion_db = new ConexionDB();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.asignarJurado)) {
			statement.setInt(1, id_jurado);
			statement.setInt(2, puntaje);
			statement.setInt(3, id_publicacion);
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Error al asignar jurado a la publicacion");
		}
	}
}