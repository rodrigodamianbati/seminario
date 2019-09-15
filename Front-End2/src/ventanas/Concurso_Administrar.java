package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.toedter.calendar.JDateChooser;

import api.Api;
import modelos.Categoria;
import modelos.Concurso;

import javax.swing.JComboBox;
import javax.swing.AbstractAction;
import javax.swing.JButton;

/**
 * JFrame Pantalla para administrar los concursos.
 * 
 * @author Rodrigo Batillier
 *
*/

@SuppressWarnings("serial")
public class Concurso_Administrar extends JFrame {

	private JPanel contentPane;
	private ResourceBundle bundle;
	private DefaultTableModel model;
	private JTable table; JTextField codigo;
	private JTextField nombre;
	private JTextField hashtag;
	private JComboBox<Categoria> categoria;
	private JComboBox<Integer> horaInicioInscripcion, horaFinInscripcion, horaInicioPublicacion, horaFinPublicacion;
	private JDateChooser fechaAperturaInscripcion, fechaCierreInscripcion, fechaAperturaPublicacion, fechaCierrePublicacion;
	private Api api;

	public Concurso_Administrar(String idioma) {	
		// Creo la api
		api = new Api();
		
		// Creo el bundle
		bundle = ResourceBundle.getBundle(idioma);
		
		setTitle(bundle.getString("concurso.titulo"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1134, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCodigo = new JLabel(bundle.getString("codigo"));
		lblCodigo.setBounds(21, 11, 61, 14);
		contentPane.add(lblCodigo);
		
		codigo = new JTextField();
		codigo.setBounds(21, 25, 175, 20);
		contentPane.add(codigo);
		codigo.setColumns(10);
		
		JLabel lblNombre = new JLabel(bundle.getString("nombre"));
		lblNombre.setBounds(221, 11, 64, 14);
		contentPane.add(lblNombre);
		
		nombre = new JTextField();
		nombre.setBounds(219, 25, 136, 20);
		contentPane.add(nombre);
		nombre.setColumns(10);
		
		fechaAperturaInscripcion = new JDateChooser();
		fechaAperturaInscripcion.setBounds(379, 25, 175, 20);
		contentPane.add(fechaAperturaInscripcion);
		fechaAperturaInscripcion.setDateFormatString("dd/MM/yyyy");
		
		JLabel lblFeachaAperturaInscripcion = new JLabel(bundle.getString("concurso.fecha_apertura_inscripcion"));
		lblFeachaAperturaInscripcion.setBounds(381, 11, 194, 14);
		contentPane.add(lblFeachaAperturaInscripcion);
		
		JLabel lblFechaCierreInscripcion = new JLabel(bundle.getString("concurso.fecha_cierre_inscripcion"));
		lblFechaCierreInscripcion.setBounds(379, 56, 175, 14);
		contentPane.add(lblFechaCierreInscripcion);
		
		fechaCierreInscripcion = new JDateChooser();
		fechaCierreInscripcion.setBounds(379, 69, 175, 20);
		contentPane.add(fechaCierreInscripcion);
		
		JLabel lblFechaInicioPublicacion = new JLabel(bundle.getString("concurso.fecha_inicio_publicacion"));
		lblFechaInicioPublicacion.setBounds(757, 11, 175, 14);
		contentPane.add(lblFechaInicioPublicacion);
		
		fechaAperturaPublicacion = new JDateChooser();
		fechaAperturaPublicacion.setBounds(757, 25, 175, 20);
		contentPane.add(fechaAperturaPublicacion);
		
		JLabel lblFechaCierrePublicacion = new JLabel(bundle.getString("concurso.fecha_cierre_publicacion"));
		lblFechaCierrePublicacion.setBounds(757, 56, 175, 14);
		contentPane.add(lblFechaCierrePublicacion);
		
		fechaCierrePublicacion = new JDateChooser();
		fechaCierrePublicacion.setBounds(757, 69, 175, 20);
		contentPane.add(fechaCierrePublicacion);
		
		JLabel lblHashtag = new JLabel(bundle.getString("hashtag"));
		lblHashtag.setBounds(21, 56, 78, 14);
		contentPane.add(lblHashtag);
		
		hashtag = new JTextField();
		hashtag.setBounds(21, 69, 175, 20);
		contentPane.add(hashtag);
		hashtag.setColumns(10);
		
		JLabel lblCategoria = new JLabel(bundle.getString("categoria"));
		lblCategoria.setBounds(221, 56, 64, 14);
		contentPane.add(lblCategoria);
		
		categoria = new JComboBox<Categoria>();
		categoria.setBounds(221, 69, 136, 20);
		contentPane.add(categoria);
		
		JButton btnVolverALa = new JButton(bundle.getString("volver.principal"));
		btnVolverALa.setBounds(290, 387, 568, 23);
		contentPane.add(btnVolverALa);
		btnVolverALa.addActionListener( e -> dispose() );
		
		JButton btnAgregar = new JButton(bundle.getString("concurso.agregar"));
		btnAgregar.setBounds(379, 102, 369, 23);
		contentPane.add(btnAgregar);
		btnAgregar.addActionListener( e -> guardarConcurso());
		
		JLabel lblHoraInicioInscripcion = new JLabel(bundle.getString("concurso.horaInicioInscripcion"));
		lblHoraInicioInscripcion.setBounds(585, 11, 163, 14);
		contentPane.add(lblHoraInicioInscripcion);
		
		JLabel lblHoraFinInscripcion = new JLabel(bundle.getString("concurso.horaFinInscripcion"));
		lblHoraFinInscripcion.setBounds(585, 56, 163, 14);
		contentPane.add(lblHoraFinInscripcion);
		
		JLabel lblHoraInicioPublicacion = new JLabel(bundle.getString("concurso.horaInicioPublicacion"));
		lblHoraInicioPublicacion.setBounds(942, 11, 163, 14);
		contentPane.add(lblHoraInicioPublicacion);
		
		JLabel lblHoraFinInscripcion_1 = new JLabel(bundle.getString("concurso.horaFinInscripcion"));
		lblHoraFinInscripcion_1.setBounds(942, 56, 163, 14);
		contentPane.add(lblHoraFinInscripcion_1);
		
		horaInicioInscripcion = new JComboBox<Integer>();
		horaInicioInscripcion.setBounds(585, 25, 163, 20);
		contentPane.add(horaInicioInscripcion);
		cargarHorarios(horaInicioInscripcion);
		
		horaFinInscripcion = new JComboBox<Integer>();
		horaFinInscripcion.setBounds(585, 69, 163, 20);
		contentPane.add(horaFinInscripcion);
		cargarHorarios(horaFinInscripcion);
		
		horaInicioPublicacion = new JComboBox<Integer>();
		horaInicioPublicacion.setBounds(942, 25, 163, 20);
		contentPane.add(horaInicioPublicacion);
		cargarHorarios(horaInicioPublicacion);
		
		horaFinPublicacion = new JComboBox<Integer>();
		horaFinPublicacion.setBounds(942, 69, 163, 20);
		contentPane.add(horaFinPublicacion);
		cargarHorarios(horaFinPublicacion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 186, 1095, 142);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("id"), 
					bundle.getString("codigo"),
					bundle.getString("nombre"),
					bundle.getString("hashtag"),
					bundle.getString("id.categoria"),
					bundle.getString("categoria.nombre"),
					bundle.getString("inscripcion.fecha_inicio"),
					bundle.getString("inscripcion.hora_inicio"),
					bundle.getString("inscripcion.fecha_fin"),
					bundle.getString("inscripcion.hora_fin"),
					bundle.getString("publicacion.fecha_inicio"),
					bundle.getString("publicacion.hora_inicio"),
					bundle.getString("publicacion.fecha_fin"),
					bundle.getString("publicacion.hora_fin"),
					bundle.getString("concurso.corregido"),
					bundle.getString("listar.puestos_participantes"),
					bundle.getString("modificar"),
					bundle.getString("eliminar")
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, 
					Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, 
					Object.class, Boolean.class, Boolean.class, Boolean.class
				};
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				
				public boolean isCellEditable(int row, int column) {
					return column == 1 || column == 0 || column == 4 || column == 14 ? false : true;
			    }
			}
		);
		model = (DefaultTableModel) table.getModel();
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(4).setMaxWidth(0);
		table.getColumnModel().getColumn(4).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);
		
//		JButton btnNewButton = new JButton(bundle.getString("eliminar"));
//		btnNewButton.setBounds(21, 340, 334, 23);
//		contentPane.add(btnNewButton);
//		btnNewButton.addActionListener( e -> eliminarConcurso());
//		
//		JButton btnModificar = new JButton(bundle.getString("modificar"));
//		btnModificar.setBounds(379, 340, 369, 23);
//		contentPane.add(btnModificar);
//		btnModificar.addActionListener( e -> modificarConcurso() );
		
//		JButton btnAgregarPuestos = new JButton(bundle.getString("listar.puestos_participantes"));
//		btnAgregarPuestos.setBounds(757, 340, 348, 23);
//		contentPane.add(btnAgregarPuestos);
//		btnAgregarPuestos.addActionListener( e -> agregarPuestos(idioma));
		
		JLabel lblListado = new JLabel(bundle.getString("concurso.lista"));
		lblListado.setBounds(503, 161, 163, 14);
		contentPane.add(lblListado);
		
		this.cargarCategorias();
		this.listarConcursos();
		
		AbstractAction eliminarConcurso = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, "�Desea eliminar este concurso?",
						"Seleccione una opcion...", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

				// 0=yes, 1=no, 2=cancel
				//System.out.println(input);
				try {
					if (input == 0) {
						JTable table = (JTable) e.getSource();
						int modelRow = Integer.valueOf(e.getActionCommand());
						
						api.eliminarConcurso((int) table.getValueAt(modelRow, 0));
						
						// Saco el concurso de la tabla
						model.removeRow(modelRow);
					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				}finally {
					listarConcursos();
				}
			}
		};
		
		ButtonColumn buttonColumn = new ButtonColumn(table, eliminarConcurso, 17);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
		AbstractAction modificar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, "�Desea modificar este concurso?",
						"Seleccione una opcion...", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

				// 0=yes, 1=no, 2=cancel
				//System.out.println(input);
				try {
					if (input == 0) {
						JTable table = (JTable) e.getSource();
						int modelRow = Integer.valueOf(e.getActionCommand());
	
		
						// Valido las fechas si estan vacias
						validarFecha(table.getValueAt(modelRow, 6).toString(), bundle.getString("vacio.fechaAperturaInscripcion"));
						validarFecha(table.getValueAt(modelRow, 8).toString(), bundle.getString("vacio.fechaCierreInscripcion"));
						validarFecha(table.getValueAt(modelRow, 10).toString(), bundle.getString("vacio.fechaAperturaPublicacion"));
						validarFecha(table.getValueAt(modelRow, 12).toString(), bundle.getString("vacio.fechaCierrePublicacion"));
						
						// Devuelvo la categoria
						Categoria categoria = api.categoria(table.getValueAt(modelRow, 5).toString());
						
						// Creo el concurso
						Concurso concurso = api.crearConcurso(
								(int) table.getValueAt(modelRow, 0), table.getValueAt(modelRow, 1).toString(), table.getValueAt(modelRow, 2).toString(), 
								table.getValueAt(modelRow, 3).toString(), categoria,
								LocalDate.parse(table.getValueAt(modelRow, 6).toString()),
								LocalDate.parse(table.getValueAt(modelRow, 8).toString()),
								LocalDate.parse(table.getValueAt(modelRow, 10).toString()),
								LocalDate.parse(table.getValueAt(modelRow, 12).toString()),
								Integer.parseInt(table.getValueAt(modelRow, 7).toString()), 
								Integer.parseInt(table.getValueAt(modelRow, 9).toString()),
								Integer.parseInt(table.getValueAt(modelRow, 11).toString()),
								Integer.parseInt(table.getValueAt(modelRow, 13).toString()), 
								Boolean.valueOf(table.getValueAt(modelRow, 14).toString())
						);
						
						// Modifico el concurso
						api.modificarConcurso(concurso, table.getValueAt(modelRow, 5).toString());
						listarConcursos();
					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				}finally {
					listarConcursos();
				}
			}
		};
		
		ButtonColumn buttonColumn1 = new ButtonColumn(table, modificar, 16);
		buttonColumn1.setMnemonic(KeyEvent.VK_D);
		
		AbstractAction listarPuestosYparticipantes = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
//				int input = JOptionPane.showConfirmDialog(null, "�Desea eliminar este concurso?",
//						"Seleccione una opcion...", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

				// 0=yes, 1=no, 2=cancel
				//System.out.println(input);
				try {
//					if (input == 0) {
						JTable table = (JTable) e.getSource();
						int modelRow = Integer.valueOf(e.getActionCommand());
						
						Categoria categoria = api.crearCategoria((int) table.getValueAt(modelRow, 4), table.getValueAt(modelRow, 5).toString());
						Concurso concurso = api.crearConcurso(
								(int) table.getValueAt(modelRow, 0), table.getValueAt(modelRow, 1).toString(),
								table.getValueAt(modelRow, 2).toString(), table.getValueAt(modelRow, 3).toString(), 
								categoria, LocalDate.parse(table.getValueAt(modelRow, 6).toString()),
								LocalDate.parse(table.getValueAt(modelRow, 8).toString()), 
								LocalDate.parse(table.getValueAt(modelRow, 10).toString()),
								LocalDate.parse(table.getValueAt(modelRow, 12).toString()),
								(int) table.getValueAt(modelRow, 7), (int) table.getValueAt(modelRow, 9), (int) table.getValueAt(modelRow, 11), 
								(int) table.getValueAt(modelRow, 13), Boolean.valueOf(table.getValueAt(modelRow, 14).toString())
						);
						Puesto_Administrar puesto = new Puesto_Administrar(idioma, concurso);
						puesto.setVisible(true);
//					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				}finally {
					listarConcursos();
				}
			}
		};
		
		ButtonColumn buttonColumn2 = new ButtonColumn(table, listarPuestosYparticipantes, 15);
		buttonColumn2.setMnemonic(KeyEvent.VK_D);
		
	}
	
	/**
	 * Metodo que devuelve todos los concursos y los muestra en la tabla
	 */
	private void listarConcursos() {
		try {
			// Vacio la tabla
			model.setRowCount(0);
			
			// Agrego los concursos a la tabla
			for (Concurso concurso: api.listarConcursos()) {
				model.addRow(new Object[]{
					concurso.getId(),
					concurso.getCodigo(),
					concurso.getNombre(),
					concurso.getHashtag(),
					concurso.getCategoria().getId(),
					concurso.getCategoria().getNombre(),
					concurso.getFechaInicioInscripcion(),
					concurso.getHoraInicioInscripcion(),
					concurso.getFechaFinInscripcion(),
					concurso.getHoraFinInscripcion(),
					concurso.getFechaInicioPublicacion(),
					concurso.getHoraInicioPublicacion(),
					concurso.getFechaFinPublicacion(),
					concurso.getHoraFinPublicacion(),
					//concurso.getCorregido(),
					concurso.getEstado(),
					bundle.getString("listar.puestos_participantes"),
					"modificar",
					"eliminar"
				});		
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	/**
	 * Metodo que guarda el concurso en la base de datos
	 */
	private void guardarConcurso() {
		try {
			SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {

				@Override
				protected void done() {
					String longTaskReturnValue;
					try {
						longTaskReturnValue = get();
						JOptionPane.showMessageDialog(null, longTaskReturnValue);
					} catch (InterruptedException | ExecutionException e) {
						throw new RuntimeException(e);
					}
				}

				@Override
				protected String doInBackground() {
					try {
						// Si el concurso ya existe muestro mensaje
						if (api.existeConcurso(codigo.getText())) {
							return bundle.getString("concurso.repetida");
						}				
						
						// Valido las fechas si estan vacias
						validarFechaConcurso(fechaAperturaInscripcion.getDate(), bundle.getString("vacio.fechaAperturaInscripcion"));
						validarFechaConcurso(fechaCierreInscripcion.getDate(), bundle.getString("vacio.fechaCierreInscripcion"));
						validarFechaConcurso(fechaAperturaPublicacion.getDate(), bundle.getString("vacio.fechaAperturaPublicacion"));
						validarFechaConcurso(fechaCierrePublicacion.getDate(), bundle.getString("vacio.fechaCierrePublicacion"));
						
						// Convierto las fechas de Date a LocalDate
						LocalDate fechaAperturaIns = fechaAperturaInscripcion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						LocalDate fechaCierreIns = fechaCierreInscripcion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						LocalDate fechaAperturaPub = fechaAperturaPublicacion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						LocalDate fechaCierrePub = fechaCierrePublicacion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
						// Creo el concurso
						Concurso concurso = api.crearConcurso(codigo.getText(), nombre.getText(), hashtag.getText(), 
								(Categoria) categoria.getSelectedItem(), fechaAperturaIns, fechaCierreIns, fechaAperturaPub, 
								fechaCierrePub, horaInicioInscripcion.getSelectedIndex(), horaFinInscripcion.getSelectedIndex(), 
								horaInicioPublicacion.getSelectedIndex(), horaFinPublicacion.getSelectedIndex());
						
						// Guardo el concurso en la base de datos
						api.nuevoConcurso(concurso);
						
						// Vacio los inputs y seteo los comboBox
						vaciarInputs();
						
						// Agrego el concurso en la tabla
						listarConcursos();
						return bundle.getString("concurso.exito.agregar");					
					} catch (RuntimeException e) {
						return e.getMessage();
					}
				}
			};
			worker.execute();
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Metodo que modifica el concurso seleccionado en la base de datos
	 */
	private void modificarConcurso() {
		SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
			protected void done() {
				try {
					String mensajeError = get();
					listarConcursos();
					JOptionPane.showMessageDialog(null, mensajeError);
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}

			protected String doInBackground() {
				try {
					// Variable entera que indica cantidad de checkeados
					int checkeados = cantidadCheckSeleccionados(16);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						for (int i = 0; i < model.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table.getValueAt(i, 16).toString());
							if (checked) {
								// Verifico que exista la categoria
								if (!api.existeCategoria(table.getValueAt(i, 5).toString())) {
									return bundle.getString("categoria.no_existe");
								}
				
								// Valido las fechas si estan vacias
								validarFecha(table.getValueAt(i, 6).toString(), bundle.getString("vacio.fechaAperturaInscripcion"));
								validarFecha(table.getValueAt(i, 8).toString(), bundle.getString("vacio.fechaCierreInscripcion"));
								validarFecha(table.getValueAt(i, 10).toString(), bundle.getString("vacio.fechaAperturaPublicacion"));
								validarFecha(table.getValueAt(i, 12).toString(), bundle.getString("vacio.fechaCierrePublicacion"));
								
								// Devuelvo la categoria
								Categoria categoria = api.categoria(table.getValueAt(i, 5).toString());
								
								// Creo el concurso
								Concurso concurso = api.crearConcurso(
										(int) table.getValueAt(i, 0), table.getValueAt(i, 1).toString(), table.getValueAt(i, 2).toString(), 
										table.getValueAt(i, 3).toString(), categoria,
										LocalDate.parse(table.getValueAt(i, 6).toString()),
										LocalDate.parse(table.getValueAt(i, 8).toString()),
										LocalDate.parse(table.getValueAt(i, 10).toString()),
										LocalDate.parse(table.getValueAt(i, 12).toString()),
										Integer.parseInt(table.getValueAt(i, 7).toString()), 
										Integer.parseInt(table.getValueAt(i, 9).toString()),
										Integer.parseInt(table.getValueAt(i, 11).toString()),
										Integer.parseInt(table.getValueAt(i, 13).toString()), 
										Boolean.valueOf(table.getValueAt(i, 14).toString())
								);
								
								// Modifico el concurso
//								api.modificarConcurso(concurso);
								listarConcursos();
								return bundle.getString("concurso.exito.modificar");
							}
						}
						
						listarConcursos();
						return bundle.getString("concurso.repetida");
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
	 * Metodo que elimina el concurso seleccionado en la base de datos
	 */
	private void eliminarConcurso() {
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
					int checkeados = cantidadCheckSeleccionados(17);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						for (int i = 0; i < model.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table.getValueAt(i, 17).toString());
							if (checked) {
								// Elimino la categoria
								api.eliminarConcurso((int) table.getValueAt(i, 0));
								
								// Saco el concurso de la tabla
								model.removeRow(i);
							}
						}
						
						return bundle.getString("concurso.exito.eliminar");
					} else {
						// Si selecciono mas de uno o ninguno muestro mensaje
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
	 * Metodo que valida que una fecha no este vacia
	 */
	private void validarFechaConcurso(Date fecha, String mensaje) {
		if(fecha == null) {
			throw new RuntimeException(mensaje);
		}
	}
	
	/**
	 * Metodo que carga todas las categorias en el comboBox
	 */
	private void cargarCategorias() {
		Api api = new Api();
		List<Categoria> lista = api.listarCategorias();
		
		if (lista.isEmpty()) {
			JOptionPane.showMessageDialog(null, bundle.getString("concurso.excepcion"));
		}
		
		for (Categoria cat: lista){
			categoria.addItem(cat);
		}
	}
	
	/**
	 * Metodo que agrega los horarios a los comboBox
	 */
	@SuppressWarnings("unchecked")
	private void cargarHorarios(@SuppressWarnings("rawtypes") JComboBox combo) {
		for (int i = 0; i < 24; i++) {
			combo.addItem(i + ":00");
		}
	}
	
	/**
	 * Metodo que valida que una fecha no este vacia
	 */
	private void validarFecha(String fecha, String mensaje) {
		if(fecha.isEmpty() || fecha == "") {
			throw new RuntimeException(mensaje);
		}
	}
	
	/**
	 * Metodo que vacia los inputs y setea los comboBox
	 */
	private void vaciarInputs(){
		codigo.setText("");
		nombre.setText("");
		hashtag.setText("");
		fechaAperturaInscripcion.setCalendar(null);
		fechaCierreInscripcion.setCalendar(null);
		fechaAperturaPublicacion.setCalendar(null);
		fechaCierrePublicacion.setCalendar(null);
		horaInicioInscripcion.setSelectedIndex(0);
		horaFinInscripcion.setSelectedIndex(0);
		horaInicioPublicacion.setSelectedIndex(0);
		horaFinPublicacion.setSelectedIndex(0);
		categoria.setSelectedIndex(0);
	}
	
	/**
	 * Metodo que verifica cuantos check selecciono
	 */
	private int cantidadCheckSeleccionados(int columna) {
		int cantidad = 0;
		for (int i = 0; i < model.getRowCount(); i++) {
			Boolean checked = Boolean.valueOf(table.getValueAt(i, columna).toString());
			if (checked) {
				cantidad++;
			}
		}
		return cantidad;
	}
	/**
	 * Metodo que crea la pantalla de puestos pasandole el idioma y el concurso
	 * @param idioma
	 */
	private void agregarPuestos(String idioma) {		
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
					int checkeados = cantidadCheckSeleccionados(15);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						// Busco concurso a agregar premios
						for (int i = 0; i < model.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table.getValueAt(i, 15).toString());
							if (checked) {
								// Creo la categoria
								Categoria categoria = api.crearCategoria((int) table.getValueAt(i, 4), table.getValueAt(i, 5).toString());
								Concurso concurso = api.crearConcurso(
										(int) table.getValueAt(i, 0), table.getValueAt(i, 1).toString(),
										table.getValueAt(i, 2).toString(), table.getValueAt(i, 3).toString(), 
										categoria, LocalDate.parse(table.getValueAt(i, 6).toString()),
										LocalDate.parse(table.getValueAt(i, 8).toString()), 
										LocalDate.parse(table.getValueAt(i, 10).toString()),
										LocalDate.parse(table.getValueAt(i, 12).toString()),
										(int) table.getValueAt(i, 7), (int) table.getValueAt(i, 9), (int) table.getValueAt(i, 11), 
										(int) table.getValueAt(i, 13), Boolean.valueOf(table.getValueAt(i, 14).toString())
								);
								Puesto_Administrar puesto = new Puesto_Administrar(idioma, concurso);
								puesto.setVisible(true);
								listarConcursos();
							}
						}
						return bundle.getString("puesto.excepcion");
					} else {
						// Si no selecciono mas de uno o ninguno muestro mensaje en pantalla
						if (checkeados > 1) {
							return bundle.getString("seleccionar.mucho");
						} else {
							return bundle.getString("seleccionar.uno");
						}
					}
				} catch (RuntimeException e) {
					return e.getMessage();
				}
			}
		};
		worker.execute();
	}
}
