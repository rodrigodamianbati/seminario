package ventanas;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import api.Api;
import modelos.Concurso;
import modelos.Inscripcion;
import modelos.Participante;
import modelos.Publicacion;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Inscripcion_Publicacion_Administrar extends JFrame {

	private JPanel contentPane;
	private ResourceBundle bundle;
	private JTable table, table2, table3, table3b, table4, table4b;
	private DefaultTableModel model, model2, model3, model3b, model4, model4b;
	private JTextArea textArea;
	private Api api;
	
	public Inscripcion_Publicacion_Administrar(String idioma, Participante participante) {
		// Creo la api
		api = new Api();
		
		// Creo el bundle
		bundle = ResourceBundle.getBundle(idioma);
		
		setTitle(bundle.getString("inscripcion.titulo") + participante.getNombre() + " " + participante.getApellido() 
			+ " (DNI: " + participante.getDni() + ")");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1069, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConcursosParaInscribirse = new JLabel(bundle.getString("concurso.para_inscribirse"));
		lblConcursosParaInscribirse.setBounds(10, 11, 255, 14);
		contentPane.add(lblConcursosParaInscribirse);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 24, 317, 117);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("id"),
					bundle.getString("nombre"),
					bundle.getString("inscripcion.seleccionar")
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Boolean.class
				};
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			}
		);
		model = (DefaultTableModel) table.getModel();
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 196, 317, 136);
		contentPane.add(scrollPane2);
		
		table2 = new JTable();
		scrollPane2.setViewportView(table2);
		table2.setCellSelectionEnabled(true);
		table2.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("id"),
					bundle.getString("nombre"),
					bundle.getString("eliminar")
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Boolean.class
				};
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			}
		);
		model2 = (DefaultTableModel) table2.getModel();
		table2.getColumnModel().getColumn(0).setMaxWidth(0);
		table2.getColumnModel().getColumn(0).setMinWidth(0);
		table2.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table2.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		
		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setBounds(383, 24, 317, 117);
		contentPane.add(scrollPane3);
		
		table3 = new JTable();
		scrollPane3.setViewportView(table3);
		table3.setCellSelectionEnabled(true);
		table3.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("id"),
					bundle.getString("nombre"),
					bundle.getString("seleccionar")
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Boolean.class
				};
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			}
		);
		model3 = (DefaultTableModel) table3.getModel();
		table3.getColumnModel().getColumn(0).setMaxWidth(0);
		table3.getColumnModel().getColumn(0).setMinWidth(0);
		table3.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table3.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		
		JScrollPane scrollPane3b = new JScrollPane();
		scrollPane3b.setBounds(383, 196, 317, 136);
		contentPane.add(scrollPane3b);
		
		table3b = new JTable();
		scrollPane3b.setViewportView(table3b);
		table3b.setCellSelectionEnabled(true);
		table3b.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("publicaciones")
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class
				};
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			}
		);
		model3b = (DefaultTableModel) table3b.getModel();
		
		JScrollPane scrollPane4 = new JScrollPane();
		scrollPane4.setBounds(783, 24, 260, 100);
		contentPane.add(scrollPane4);
		
		table4 = new JTable();
		scrollPane4.setViewportView(table4);
		table4.setCellSelectionEnabled(true);
		table4.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("id"),
					bundle.getString("nombre"),
					bundle.getString("seleccionar")
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Boolean.class
				};
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			}
		);
		model4 = (DefaultTableModel) table4.getModel();
		table4.getColumnModel().getColumn(0).setMaxWidth(0);
		table4.getColumnModel().getColumn(0).setMinWidth(0);
		table4.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table4.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		
		JScrollPane scrollPane4b = new JScrollPane();
		scrollPane4b.setBounds(783, 232, 260, 100);
		contentPane.add(scrollPane4b);
		
		table4b = new JTable();
		scrollPane4b.setViewportView(table4b);
		table4b.setCellSelectionEnabled(true);
		table4b.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("id"),
					bundle.getString("publicacion"),
					bundle.getString("eliminar")
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Boolean.class
				};
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			}
		);
		model4b = (DefaultTableModel) table4b.getModel();
		table4b.getColumnModel().getColumn(0).setMaxWidth(0);
		table4b.getColumnModel().getColumn(0).setMinWidth(0);
		table4b.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table4b.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		
		JButton btnAgregarInscripciones = new JButton(bundle.getString("inscripcion.agregar"));
		btnAgregarInscripciones.setBounds(10, 147, 317, 23);
		contentPane.add(btnAgregarInscripciones);
		btnAgregarInscripciones.addActionListener( e -> agregarInscripcion(participante));
		
		JLabel lblConcursosInscripto = new JLabel(bundle.getString("inscripcion.inscripto"));
		lblConcursosInscripto.setBounds(10, 183, 310, 14);
		contentPane.add(lblConcursosInscripto);
		
		JButton btnEliminarInscripcion = new JButton(bundle.getString("eliminar.inscripcion"));
		btnEliminarInscripcion.setBounds(10, 343, 317, 23);
		contentPane.add(btnEliminarInscripcion);
		btnEliminarInscripcion.addActionListener( e -> eliminarInscripcion(participante.getId()));
		
		JButton btnVolver = new JButton(bundle.getString("volver.participante"));
		btnVolver.setBounds(260, 368, 457, 23);
		contentPane.add(btnVolver);
		btnVolver.addActionListener( e -> dispose());
		
		JLabel lblConcursosConInscripciones = new JLabel(bundle.getString("concurso.inscripcion_finalizado"));
		lblConcursosConInscripciones.setBounds(383, 11, 268, 14);
		contentPane.add(lblConcursosConInscripciones);
		
		JLabel lblPublicacion = new JLabel("Publicacion");
		lblPublicacion.setBounds(783, 135, 118, 14);
		contentPane.add(lblPublicacion);
		
		textArea = new JTextArea();
		textArea.setBounds(783, 146, 260, 48);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton(bundle.getString("publicacion.agregar"));
		btnNewButton.setBounds(783, 199, 260, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener( e -> this.agregarPublicacion(participante));
		
		JLabel lblConcursoSubirPublicacion = new JLabel(bundle.getString("concurso.subir_publicacion"));
		lblConcursoSubirPublicacion.setBounds(784, 11, 236, 14);
		contentPane.add(lblConcursoSubirPublicacion);
		
		JButton btnMostrarPublicaciones = new JButton(bundle.getString("publicaciones.mostrar"));
		btnMostrarPublicaciones.setBounds(383, 147, 317, 23);
		contentPane.add(btnMostrarPublicaciones);
		btnMostrarPublicaciones.addActionListener( e -> mostrarPublicaciones(participante.getId()));
		
		JButton btnEliminar = new JButton(bundle.getString("eliminar"));
		btnEliminar.setBounds(793, 343, 242, 23);
		contentPane.add(btnEliminar);
		btnEliminar.addActionListener( e-> eliminarPublicacion());
		
		this.listarConcursos();
		this.listarConcursosParticipante(participante.getId());
		this.listarConcursosInscripcionCerrada(participante.getId());
		this.listarConcursosPublicacionAbierta(participante.getId());
	}
	
	/**
	 * Metodo que devuelve todos los concursos con inscripcion abierta y las muestra en la tabla
	 */
	private void listarConcursos() {
		try {
			// Vacio la tabla
			model.setRowCount(0);
			
			// Agrego los concursos con inscripcion abierta a la tabla
			for (Concurso concurso: api.listarConcursosConInscripcionAbierta()) {
				model.addRow(new Object[]{
					concurso.getId(),
					concurso.getNombre(),
					false
				});
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	/**
	 * Metodo que inscribe a la persona en un concurso
	 * @param participante
	 */
	private void agregarInscripcion(Participante participante) {
		SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
			protected void done() {
				try {
					String mensajeError = get();
					JOptionPane.showMessageDialog(null, mensajeError);
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}

			protected String doInBackground() {
				try {
					// Variable entera que indica cantidad de checkeados
					int checkeados = cantidadCheckSeleccionados(2, 0);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						// Busco la categoria a modificar
						for (int i = 0; i < model.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table.getValueAt(i, 2).toString());
							if (checked) {
								//System.out.println(table.getValueAt(i, 0));
								//System.out.println(table.getValueAt(i, 1).toString());
								api.inscribirParticipante((int) table.getValueAt(i, 0), participante);
								/* ESTO ESTÁ TODO MAL!
								// Creo inscripcion a almacenar
								Inscripcion inscripcion = api.crearInscripcion(
										api.crearConcurso((int) table.getValueAt(i, 0), table.getValueAt(i, 1).toString()), 
										participante
								);
								
								// Pregunto si el participante ya esta inscripto
								if (api.existeInscripcion(inscripcion)) {
									return bundle.getString("inscripcion.repetida");
								}
								
								// Guardo la inscripcion
								api.nuevaInscripcion(inscripcion);
								*/
							}
						}
				
						// Pongo la inscripcion en la tabla
						listarConcursosParticipante(participante.getId());
						return bundle.getString("inscripcion.exito.agregar");
					} else {
						// Si no selecciono mas de uno o ninguno muestro mensaje en pantalla
						if (checkeados > 1) {
							return bundle.getString("seleccionar.mucho");
						} 
						return bundle.getString("seleccionar.uno");
					}
				} catch (RuntimeException e) {
					return e.getMessage();
				}
			}
		};
		worker.execute();
	}
	
	/**
	 * Metodo que elimina una inscripcion
	 */
	private void eliminarInscripcion(int id_participante) {
		SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
			protected void done() {
				try {
					String mensajeError = get();
					JOptionPane.showMessageDialog(null, mensajeError);
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}

			protected String doInBackground() {
				try {
					// Variable entera que indica cantidad de checkeados
					int checkeados = cantidadCheckSeleccionados(2, 1);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						// Busco la categoria a modificar
						for (int i = 0; i < model2.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table2.getValueAt(i, 2).toString());
							if (checked) {
								// Elimino la inscripcion
								api.eliminarInscripcion(id_participante, (int) table2.getValueAt(i, 0));
								
								// Saco la inscripcion de la tabla
								model2.removeRow(i);
							}
						}
						
						return bundle.getString("inscripcion.exito.eliminar");
					} else {
						// Si no selecciono mas de uno o ninguno muestro mensaje en pantalla
						if (checkeados > 1) {
							return bundle.getString("seleccionar.mucho");
						} 
						return bundle.getString("seleccionar.uno");
					}
				} catch (RuntimeException e) {
					return e.getMessage();
				}
			}
		};
		worker.execute();
	}
	
	/**
	 * Metodo que devuelve todos los concursos con inscripcion cerrada que se inscribio el participante
	 */
	private void listarConcursosInscripcionCerrada(int id_participante) {
		try {
			// Vacio la tabla
			model3.setRowCount(0);
			
			// Agrego los concursos con inscripcion cerrada a la tabla
			for (Concurso concurso: api.listarConcursosConInscripcionCerradaInscripto(id_participante)) {
				model3.addRow(new Object[]{
					concurso.getId(),
					concurso.getNombre(),
					false
				});
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	/**
	 * Metodo que verifica concurso seleccionado para mostrar publicaciones
	 */
	private void mostrarPublicaciones(int id_participante) {
		SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
			protected void done() {
				try {
					String mensajeError = get();
					JOptionPane.showMessageDialog(null, mensajeError);
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}

			protected String doInBackground() {
				try {
					// Variable entera que indica cantidad de checkeados
					int checkeados = cantidadCheckSeleccionados(2, 2);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						// Busco el concurso a mostrar publicaciones
						for (int i = 0; i < model3.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table3.getValueAt(i, 2).toString());
							if (checked) {
								// Listo las publicaciones
								listarPublicacionesAntiguas((int) table3.getValueAt(i, 0), id_participante);
								return bundle.getString("publicacion.exito.buscar") + table3.getValueAt(i, 1).toString();
							}
						}
						
						return bundle.getString("publicacion.exito.buscar");
					} else {
						// Si no selecciono mas de uno o ninguno muestro mensaje en pantalla
						if (checkeados > 1) {
							return bundle.getString("seleccionar.mucho");
						} 
						return bundle.getString("seleccionar.uno");
					}
				} catch (RuntimeException e) {
					return e.getMessage();
				}
			}
		};
		worker.execute();
	}
	
	/**
	 * Metodo que muestra en pantalla las publicaciones del concurso seleccionado
	 */
	private void listarPublicacionesAntiguas(int id_concurso, int id_participante) {
		try {
			// Vacio la tabla
			model3b.setRowCount(0);
			
			// Agrego los concursos con inscripcion abierta a la tabla
			for (Publicacion publicacion: api.listarPublicacionesAntiguas(id_concurso, id_participante)) {
				model3b.addRow(new Object[]{
					publicacion.getTexto()
				});
			}
			
			if (model3b.getRowCount() == 0) {
				model3b.addRow(new Object[]{
					"No hay publicaciones."
				});
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	/**
	 * Metodo que devuelve todos los concursos con publicacion abierta que se inscribio el participante
	 */
	private void listarConcursosPublicacionAbierta(int id_participante) {
		try {
			// Vacio la tabla
			model4.setRowCount(0);
			
			// Agrego los concursos con inscripcion abierta a la tabla
			for (Concurso concurso: api.listarConcursosConPublicacionAbierta(id_participante)) {
				model4.addRow(new Object[]{
					concurso.getId(),
					concurso.getNombre(),
					false
				});
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	/**
	 * Metodo que elimina una publicacion
	 */
	private void eliminarPublicacion() {
		SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
			protected void done() {
				try {
					String mensajeError = get();
					JOptionPane.showMessageDialog(null, mensajeError);
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}

			protected String doInBackground() {
				try {
					// Variable entera que indica cantidad de checkeados
					int checkeados = cantidadCheckSeleccionados(2, 4);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						// Busco el concurso a mostrar publicaciones
						for (int i = 0; i < model4b.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table4b.getValueAt(i, 2).toString());
							if (checked) {
								// Elimino la publicacion
								api.eliminarPublicacion((int) table4b.getValueAt(i, 0));
								
								// Quito la publicacion de la tabla
								model4b.removeRow(i);
							}
						}
						
						return bundle.getString("publicacion.exito.eliminar");
					} else {
						// Si no selecciono mas de uno o ninguno muestro mensaje en pantalla
						if (checkeados > 1) {
							return bundle.getString("seleccionar.mucho");
						} 
						return bundle.getString("seleccionar.uno");
					}
				} catch (RuntimeException e) {
					return e.getMessage();
				}
			}
		};
		worker.execute();
	}
	
	/**
	 * Metodo que devuelve todos los concursos que esta inscripto el participante
	 */
	private void listarConcursosParticipante(int id_participante) {
		try {
			// Vacio la tabla
			model2.setRowCount(0);
			
			// Agrego los concursos con inscripcion abierta a la tabla
			for (Concurso concurso: api.listarConcursosParticipanteInscripto(id_participante)) {
				model2.addRow(new Object[]{
					concurso.getId(),
					concurso.getNombre(),
					false
				});
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	/**
	 * Metodo que agregar una publicacion en la base de datos
	 */
	private void agregarPublicacion(Participante participante) {
		SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
			protected void done() {
				try {
					String mensajeError = get();
					JOptionPane.showMessageDialog(null, mensajeError);
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}

			protected String doInBackground() {
				try {
					// Variable entera que indica cantidad de checkeados
					int checkeados = cantidadCheckSeleccionados(2, 3);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						for (int i = 0; i < model4.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table4.getValueAt(i, 2).toString());
							if (checked) {
								
								/* Esta todo mal
								// Creo el concurso
								Concurso concurso = api.crearConcurso((int) table4.getValueAt(i, 0));
								
								// Creo la publicacion
								Publicacion publicacion = api.crearPublicacion(participante, concurso, textArea.getText());
								
								// Agrego la publicacion
								api.agregarPublicacion(publicacion);
								*/
								
								api.nuevaPublicacion((int) table4.getValueAt(i, 0), participante, textArea.getText());
								
								// Pongo la inscripcion en la tabla
								listarConcursosPublicacionAbierta(participante.getId());
								listarPublicacionesConcurso(/*concurso.getId()*/(int) table4.getValueAt(i, 0), participante.getId());
							}
						}
						
						// Vacio los inputs
						textArea.setText("");
						
						return bundle.getString("publicacion.exito.agregar");
					} else {
						// Si no selecciono mas de uno o ninguno muestro mensaje en pantalla
						if (checkeados > 1) {
							return bundle.getString("seleccionar.mucho");
						} 
						return bundle.getString("seleccionar.uno");
					}
				} catch (RuntimeException e) {
					return e.getMessage();
				}
			}
		};
		worker.execute();
	}
	
	/**
	 * Metodo que lista las publicaciones del concurso con publicacion abierta
	 */
	private void listarPublicacionesConcurso(int id_concurso, int id_participante) {
		try {
			// Vacio la tabla
			model4b.setRowCount(0);
			
			// Agrego los concursos con inscripcion abierta a la tabla
			for (Publicacion publicacion: api.listarPublicacionesConcurso(id_concurso, id_participante)) {
				model4b.addRow(new Object[]{
					publicacion.getId(),
					publicacion.getTexto(),
					false
				});
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	/**
	 * Metodo que verifica cuantos check selecciono
	 */
	private int cantidadCheckSeleccionados(int columna, int m) {
		int cantidad = 0;
		DefaultTableModel mod = new DefaultTableModel();
		JTable tab = new JTable();
		switch (m) {
			case 0:
				mod = model;
				tab = table;
				break;
			case 1:
				mod = model2;
				tab = table2;
				break;
			case 2:
				mod = model3;
				tab = table3;
				break;
			case 3:
				mod = model4;
				tab = table4;
				break;
			default:
				mod = model4b;
				tab = table4b;
		}
		for (int i = 0; i < mod.getRowCount(); i++) {
			Boolean checked = Boolean.valueOf(tab.getValueAt(i, columna).toString());
			if (checked) {
				cantidad++;
			}
		}
		return cantidad;
	}
}
