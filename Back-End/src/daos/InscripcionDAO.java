package daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import interfaces.IInscripcion;
import modelos.Inscripcion;

public class InscripcionDAO implements IInscripcion {
	
	private String insertar = "INSERT INTO inscripcion(id_participante, id_concurso, fecha_inscripcion, hora_inscripcion) VALUES(?, ?, ?, ?)";
	private String eliminar = "DELETE FROM inscripcion WHERE id_participante = ? AND id_concurso = ?";
	private String verificar = "SELECT id FROM inscripcion WHERE id_participante = ? AND id_concurso = ?";

	/**
	 * Metodo que inserta una nueva inscripcion en la base de datos.
	 * 
	 * @param inscripcion
	 *            parametro del tipo Inscripcion
	 */
	public void insertar(Inscripcion inscripcion) {
		ConexionDB conexion_db = new ConexionDB();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.insertar)) {

			statement.setInt(1, inscripcion.getParticipante().getId());
			statement.setInt(2, inscripcion.getConcurso().getId());
//			System.out.println(Date.valueOf(LocalDate.now()).toString());
			statement.setString(3, Date.valueOf(LocalDate.now()).toString());
			statement.setInt(4, LocalDateTime.now().getHour());
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Este participante ya se encuentra inscripto en este concurso");
		}
	}


	/**
	 * Metodo que elimina una inscripcion en la base de datos.
	 * 
	 * @param id_participante
	 *            id de la inscripcion a eliminar
	 * @param id_concurso
	 * 				variable de tipo int
	 */
	public void eliminar(int id_participante, int id_concurso) {
		ConexionDB conexion_db = new ConexionDB();
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.eliminar)) {
			statement.setInt(1, id_participante);
			statement.setInt(2, id_concurso);
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Error al eliminar inscripcion.");
		}
	}


	@Override
	public boolean verificarExistente(int id_participante, int id_concurso) {
		ConexionDB conexion_db = new ConexionDB();
		int id = 0;
		try (Connection connect = conexion_db.obtenerConexionBD(); PreparedStatement statement = connect.prepareStatement(this.verificar)) {
			statement.setInt(1, id_participante);
			statement.setInt(2, id_concurso);
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					id = rs.getInt("id");
				}
				if (id == 0) {
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
}