package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepciones.ParticipanteExcepcion;
import interfaces.IParticipante;
import modelos.Participante;
import modelos.Publicacion;

public class ParticipanteDAO implements IParticipante {

	private String insertar = "INSERT INTO participante(nombre, apellido, dni, email) VALUES(?, ?, ?, ?)";
	private String listar = "SELECT * FROM participante p";
	private String verificar = "SELECT * FROM participante WHERE dni = '";
	private String eliminar = "DELETE FROM participante WHERE id = ";
	private String modificar = "UPDATE participante SET nombre = ?, apellido = ?, dni = ?, email = ? WHERE id = ";
	private String participantesConcursoConPublicacion = "SELECT DISTINCT p.id, p.nombre, p.apellido, p.dni, p.email, pub.id, pub.texto, pub.puntaje "
			+ "FROM participante p JOIN publicacion pub ON (p.id = pub.id_participante) " + "WHERE pub.id_concurso = ? "
			+ "ORDER BY pub.puntaje DESC, pub.id DESC";
	private String participantesConPublicacion = "SELECT DISTINCT p.id, p.nombre, p.apellido, p.dni, p.email, pub.id, pub.texto, pub.puntaje "
			+ "FROM participante p JOIN publicacion pub ON (p.id = pub.id_participante) "
			+ "ORDER BY pub.puntaje DESC, pub.id DESC";
	private String participantesConcurso = "SELECT p.nombre, p.apellido, p.dni, p.email "
			+ "FROM participante p JOIN inscripcion i ON (p.id = i.id_participante) " + "WHERE i.id_concurso = (?)";
	
	private String participantesConcursoDadoCodigo = "SELECT p.id, p.nombre, p.apellido, p.dni, p.email "
			+ "FROM participante p JOIN inscripcion i ON (p.id = i.id_participante) "
			+ "WHERE i.id_concurso = (SELECT id FROM concurso c WHERE c.codigo = ?)";
	
	private String noParticipantesConcursoDadoNombre = "SELECT DISTINCT p.id, p.nombre, p.apellido, p.dni, p.email " + 
			"FROM participante p  WHERE p.id NOT IN" + 
			"(SELECT DISTINCT inscripcion.id_participante FROM inscripcion LEFT JOIN concurso ON (inscripcion.id_concurso = concurso.id) " + 
			"WHERE concurso.nombre= ? )";
	
	private String id_concurso = "SELECT c.id FROM concurso c WHERE c.nombre = ?";
	private String participantesConConcurso = "SELECT DISTINCT p.nombre, p.apellido, p.dni, p.email "
			+ "FROM participante p JOIN inscripcion i ON (p.id = i.id_participante) ";
	private String participante = "SELECT * FROM participante p WHERE p.id = ?";

	/**
	 * Metodo que inserta un nuevo participanteen la base de datos.
	 * 
	 * @param participante parametro del tipo participante
	 */
	@Override
	public void insertar(Participante participante) {
		ConexionDB conexion_db = new ConexionDB();
		try (Connection connect = conexion_db.obtenerConexionBD();
				PreparedStatement statement = connect.prepareStatement(this.insertar)) {
			statement.setString(1, participante.getNombre());
			statement.setString(2, participante.getApellido());
			statement.setString(3, participante.getDni());
			statement.setString(4, participante.getEmail());
			statement.executeUpdate();
		} catch (SQLException ex) {
			if (ex.getMessage().contains("dni")) {
				throw new RuntimeException("Ya existe un participante con este dni");
			}else
				if (ex.getMessage().contains("email")) {
					throw new RuntimeException("Ya existe un participante con este email");
				}
			
		}
	}

	/**
	 * Metodo que devuelve todos los participantes.
	 * 
	 * @return lista de objetos Participante
	 */
	public List<Participante> devolverParticipantes() {
		ConexionDB conexion_db = new ConexionDB();
		List<Participante> lista = new ArrayList<Participante>();
		try (Connection connect = conexion_db.obtenerConexionBD();
				PreparedStatement statement = connect.prepareStatement(this.listar)) {
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					lista.add(new Participante(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"),
							rs.getString("dni"), rs.getString("email")));
				}
				return lista;
			} catch (SQLException e) {
				throw new RuntimeException("Error al cargar la lista de participantes");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al cargar la lista de participantes");
		}
	}

	/**
	 * Metodo que verifica si existe un participante en la base de datos por su dni
	 * .
	 * 
	 * 
	 * @param dni dni del aprticipante a verificar
	 */
	@Override
	public boolean verificarExistente(String dni) {
		ConexionDB conexion_db = new ConexionDB();
		List<Participante> lista = new ArrayList<Participante>();
		try (Connection connect = conexion_db.obtenerConexionBD();
				PreparedStatement statement = connect.prepareStatement(this.verificar + dni + "'")) {
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					lista.add(new Participante(rs.getString("nombre"), rs.getString("apellido"), rs.getString("dni"),
							rs.getString("email")));
				}
				if (lista.size() == 0) {
					return false;
				}
				return true;
			} catch (SQLException e) {
				throw new RuntimeException("Error en sentencia buscar participante existente");
			}
		} catch (SQLException ex) {
			throw new RuntimeException("Error en sentencia buscar participante existente");
		}
	}

	/**
	 * Metodo que modifica un participante en la base de datos.
	 * 
	 * @param participante Variable de tipo participante
	 * @throws ParticipanteExcepcion 
	 * 
	 */
	public void modificar(Participante participante){
		ConexionDB conexion_db = new ConexionDB();
		try (Connection connect = conexion_db.obtenerConexionBD();
				PreparedStatement statement = connect.prepareStatement(this.modificar + participante.getId())) {
			statement.setString(1, participante.getNombre());
			statement.setString(2, participante.getApellido());
			statement.setString(3, participante.getDni());
			statement.setString(4, participante.getEmail());
			statement.executeUpdate();
		} catch (SQLException ex) {
			if(ex.getMessage().contains("dni")) {
				throw new RuntimeException("Ya existe un participante con este dni");
			}else
				if (ex.getMessage().contains("email")) {
					throw new RuntimeException("Ya existe un participante con este email");
				}
		}
	}

	/**
	 * Metodo que elimina un participante en la base de datos.
	 * 
	 * @param id id de la participante a eliminar
	 */
	public void eliminar(int id) {
		ConexionDB conexion_db = new ConexionDB();
		try (Connection connect = conexion_db.obtenerConexionBD();
				PreparedStatement statement = connect.prepareStatement(this.eliminar + id)) {
			statement.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("No se puede eliminar al participante porque ya cargo al menos una publicacion.");
		}
	}

	/**
	 * Metodo que lista los participantes de un concurso en la base de datos.
	 * 
	 * @param id_concurso Variable de tipo int
	 * 
	 * @return Lista de participantes
	 */
	public List<Participante> devolverParticipantesConcursoConPublicacion(int id_concurso) {
		ConexionDB conexion_db = new ConexionDB();
		List<Participante> lista = new ArrayList<Participante>();
		try (Connection connect = conexion_db.obtenerConexionBD();
				PreparedStatement statement = connect.prepareStatement(this.participantesConcursoConPublicacion)) {
			statement.setInt(1, id_concurso);
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					Participante participante = new Participante(rs.getInt("p.id"), rs.getString("p.nombre"),
							rs.getString("p.apellido"), rs.getString("p.dni"));
					Publicacion publicacion = new Publicacion(rs.getInt("pub.id"), rs.getString("pub.texto"),
							rs.getInt("pub.puntaje"));
					participante.agregarPublicacion(publicacion);
					lista.add(participante);
				}
				return lista;
			} catch (SQLException e) {
				throw new RuntimeException("Error al cargar la lista de participantes con su publicacion");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al cargar la lista de participantes ocn su publicacion");
		}
	}

	@Override
	public List<Participante> devolverParticipantesConcurso(int id_concurso) {
		ConexionDB conexion_db = new ConexionDB();
		List<Participante> lista = new ArrayList<Participante>();
		try (Connection connect = conexion_db.obtenerConexionBD();
				PreparedStatement statement = connect.prepareStatement(this.participantesConcurso)) {
			statement.setInt(1, id_concurso);
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					Participante participante = new Participante(rs.getString("p.nombre"), rs.getString("p.apellido"),
							rs.getString("p.dni"), rs.getString("p.email"));
					lista.add(participante);
				}
				return lista;
			} catch (SQLException e) {
				throw new RuntimeException("Error al cargar la lista de participantes del concurso");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al cargar la lista de participantes del concurso");
		}
	}

	@Override
	public List<Participante> participantesPorSeleccion(String seleccion) {
		ConexionDB conexion_db = new ConexionDB();
		List<Participante> lista = new ArrayList<Participante>();
		if (seleccion.equals("todos")) {
			try (Connection connect = conexion_db.obtenerConexionBD();
					PreparedStatement statement = connect.prepareStatement(this.listar)) {

				try (ResultSet rs = statement.executeQuery();) {
					while (rs.next()) {
						Participante participante = new Participante(rs.getInt("id"), rs.getString("nombre"),
								rs.getString("apellido"), rs.getString("dni"), rs.getString("email"));
						lista.add(participante);
					}
					return lista;
				} catch (SQLException e) {
					throw new RuntimeException("Error al cargar la lista de participantes del concurso");
				}
			} catch (SQLException e) {
				throw new RuntimeException("Error al cargar la lista de participantes del concurso");
			}
		} else {
			try (Connection connect = conexion_db.obtenerConexionBD();
					PreparedStatement statement = connect.prepareStatement(this.participantesConcursoDadoCodigo)) {
				statement.setString(1, seleccion);
				System.out.println(statement);
				
				try (ResultSet rs = statement.executeQuery();) {
					while (rs.next()) {
						Participante participante = new Participante(rs.getInt("id"), rs.getString("nombre"),
								rs.getString("apellido"), rs.getString("dni"), rs.getString("email"));
						lista.add(participante);
					}
					return lista;
				} catch (SQLException e) {
					throw new RuntimeException("Error al cargar la lista de participantes del concurso");
				}
			} catch (SQLException e) {
				throw new RuntimeException("Error al cargar la lista de participantes del concurso");
			}
		}
	}

	@Override
	public List<Participante> noParticipantesPorSeleccion(String seleccion) {
		ConexionDB conexion_db = new ConexionDB();
		List<Participante> lista = new ArrayList<Participante>();
		try (Connection connect = conexion_db.obtenerConexionBD();
				PreparedStatement statement = connect.prepareStatement(this.noParticipantesConcursoDadoNombre)) {
			statement.setString(1, seleccion);
			System.out.println(statement);
			
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					Participante participante = new Participante(rs.getInt("id"), rs.getString("nombre"),
							rs.getString("apellido"), rs.getString("dni"), rs.getString("email"));
					lista.add(participante);
				}
				return lista;
			} catch (SQLException e) {
				throw new RuntimeException("Error al cargar la lista de participantes del concurso");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al cargar la lista de participantes del concurso");
		}

	}

	@Override
	public Participante participante(int id_participante) {
		ConexionDB conexion_db = new ConexionDB();
		Participante p = new Participante();
		try (Connection connect = conexion_db.obtenerConexionBD();
				PreparedStatement statement = connect.prepareStatement(this.participante)) {
			statement.setInt(1, id_participante);
			try (ResultSet rs = statement.executeQuery();) {
				while (rs.next()) {
					p = new Participante(rs.getInt("p.id"),rs.getString("p.nombre"), rs.getString("p.apellido"),
							rs.getString("p.dni"), rs.getString("p.email"));
				}
				return p;
			} catch (SQLException e) {
				throw new RuntimeException("Error al cargar la lista de participantes del concurso");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error de conexion");
		}
	}

}
