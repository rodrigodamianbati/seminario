package ventanas;

import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import api.Api;
import modelos.Concurso;
import modelos.Participante;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.table.TableModel;
import javax.swing.JComboBox;

/**
 * JFrame Pantalla para administrar los participantes.
 * 
 * @author Rodrigo Batillier
 *
 */

@SuppressWarnings("serial")
public class Participante_Administrar extends JFrame {

	private JPanel contentPane;
	private ResourceBundle bundle;
	// table y model
	private JTable table;
	private DefaultTableModel model;
	private DefaultTableModel model_1;
	// table y model
	private JTextField nombre;
	private JTextField apellido;
	private JTextField dni;
	private JTextField email;
	private Api api;
	private JScrollPane scrollPane;
	private TableColumnManager tcm;
	private JTable table_1 = new JTable();
	private JLabel lblInscriptos = new JLabel("Inscriptos");
	private JScrollPane scrollPane_1 = new JScrollPane();
	private JComboBox<Concurso> comboBoxConcursos;

	public Participante_Administrar(Api ap, String idioma) {
		// Creo la api
		api = ap;

		// Creo el bundle
		bundle = ResourceBundle.getBundle(idioma);

		setTitle(bundle.getString("participante.titulo"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// setBounds(100, 100, 663, 495);
		setBounds(125, 125, 828, 619);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel(bundle.getString("nombre"));
		lblNombre.setBounds(256, 11, 61, 14);
		contentPane.add(lblNombre);

		JLabel lblApellido = new JLabel(bundle.getString("apellido"));
		lblApellido.setBounds(256, 33, 61, 14);
		contentPane.add(lblApellido);

		JLabel lblDni = new JLabel(bundle.getString("dni"));
		lblDni.setBounds(256, 59, 61, 14);
		contentPane.add(lblDni);

		JLabel lblEmail = new JLabel(bundle.getString("email"));
		lblEmail.setBounds(256, 83, 61, 14);
		contentPane.add(lblEmail);

		nombre = new JTextField();
		nombre.setBounds(335, 9, 181, 20);
		contentPane.add(nombre);
		nombre.setColumns(10);

		apellido = new JTextField();
		apellido.setBounds(335, 31, 181, 20);
		contentPane.add(apellido);
		apellido.setColumns(10);

		dni = new JTextField();
		dni.setBounds(335, 57, 181, 20);
		contentPane.add(dni);
		dni.setColumns(10);
		dni.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				// Verificar si la tecla pulsada no es un digito
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});

		email = new JTextField();
		email.setBounds(335, 81, 181, 20);
		contentPane.add(email);
		email.setColumns(10);

		JButton btnAgregarParticipante = new JButton(bundle.getString("participante.agregar"));
		btnAgregarParticipante.setBounds(304, 109, 225, 23);
		contentPane.add(btnAgregarParticipante);
		btnAgregarParticipante.addActionListener(e -> guardarParticipante());

		JButton btnNewButton = new JButton(bundle.getString("volver.principal"));
		btnNewButton.setToolTipText("");
		btnNewButton.setBounds(29, 559, 627, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(e -> dispose());

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 164, 644, 200);
		contentPane.add(scrollPane);

		Concurso concursoBox = new Concurso("todos");
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { bundle.getString("id"), bundle.getString("nombre"), bundle.getString("apellido"),
						bundle.getString("dni"), bundle.getString("email"), bundle.getString("modificar"),
						bundle.getString("eliminar"), bundle.getString("concurso.inscribir"), bundle.getString("concurso.publicaciones") }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Object.class, Object.class, Object.class, Object.class, Object.class,
					Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return column == 0 ? false : true;
			}
		});
		model = (DefaultTableModel) table.getModel();
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		
		table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

		JLabel lblListaParticipantes = new JLabel(bundle.getString("listadoparticipante.titulo"));
		lblListaParticipantes.setBounds(272, 144, 384, 14);
		contentPane.add(lblListaParticipantes);

		//COMBOBOX
		Object[] elementos = new Object[50];
		elementos[0] = concursoBox;
		List<Concurso> listadoConcursos = api.listarConcursos();
		int i = 1;
		for (Concurso concurso : listadoConcursos) {
			elementos[i] = concurso;
			i++;
		}

		comboBoxConcursos = new JComboBox(elementos);
		comboBoxConcursos.setBounds(662, 164, 134, 24);

		contentPane.add(comboBoxConcursos);
		comboBoxConcursos.addActionListener(e -> listarParticipantesSeleccion(((Concurso) comboBoxConcursos.getSelectedItem()).getCodigo()));

		JLabel lblParticipantes = new JLabel("Participantes");
		lblParticipantes.setBounds(664, 144, 112, 15);
		contentPane.add(lblParticipantes);

		table.getColumn(bundle.getString("eliminar")).setCellRenderer(new ButtonRenderer());
		table.getColumn(bundle.getString("eliminar")).setCellEditor(new ButtonEditor(new JCheckBox()));

		table.getColumn(bundle.getString("modificar")).setCellRenderer(new ButtonRenderer());
		table.getColumn(bundle.getString("modificar")).setCellEditor(new ButtonEditor(new JCheckBox()));


		table.getColumn(bundle.getString("concurso.inscribir")).setCellRenderer(new ButtonRenderer());
		table.getColumn(bundle.getString("concurso.inscribir")).setCellEditor(new ButtonEditor(new JCheckBox()));
		
		tcm = new TableColumnManager(table);

		this.listarParticipantesSeleccion("todos");

		AbstractAction delete = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, bundle.getString("participante.desea_eliminar"),
						bundle.getString("seleccionar"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

				// 0=yes, 1=no, 2=cancel
				try {
					if (input == 0) {
						JTable table = (JTable) e.getSource();
						int modelRow = Integer.valueOf(e.getActionCommand());
						
						api.eliminarParticipante((int) table.getValueAt(modelRow, 0));

						((DefaultTableModel) table.getModel()).removeRow(modelRow);
					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				}finally {
					listarParticipantesSeleccion(((Concurso) comboBoxConcursos.getSelectedItem()).getCodigo());
				}
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(table, delete, 6);
		buttonColumn.setMnemonic(KeyEvent.VK_D);

		AbstractAction modificar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, bundle.getString("participante.desea_modificar"),
						bundle.getString("seleccionar"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

				// 0=yes, 1=no, 2=cancel
				System.out.println(input);
				try {
					if (input == 0) {
						JTable table = (JTable) e.getSource();
						int modelRow = Integer.valueOf(e.getActionCommand());

						// Modifico el participante
						api.modificarParticipante((int) table.getValueAt(modelRow, 0),
								table.getValueAt(modelRow, 1).toString(), table.getValueAt(modelRow, 2).toString(),
								table.getValueAt(modelRow, 3).toString(), table.getValueAt(modelRow, 4).toString());
						JOptionPane.showMessageDialog(null, bundle.getString("participante.exito.modificar"));

					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				} finally {
					listarParticipantesSeleccion(((Concurso) comboBoxConcursos.getSelectedItem()).getCodigo());
				}
			}
		};

		ButtonColumn buttonColumn1 = new ButtonColumn(table, modificar, 5);
		buttonColumn1.setMnemonic(KeyEvent.VK_D);

		AbstractAction eliminarInscripcion = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, bundle.getString("inscripcion.desea_eliminar"),
						bundle.getString("seleccionar"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

				// 0=yes, 1=no, 2=cancel
				System.out.println(input);
				try {
					if (input == 0) {
						JTable table = (JTable) e.getSource();
						int modelRow = Integer.valueOf(e.getActionCommand());

						
						api.eliminarInscripcion((int) table.getValueAt(modelRow, 0), ((Concurso) comboBoxConcursos.getSelectedItem()).getCodigo());

						JOptionPane.showMessageDialog(null, bundle.getString("inscripcion.exito.eliminar"));

					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				} finally {
					listarParticipantesSeleccion(((Concurso) comboBoxConcursos.getSelectedItem()).getCodigo());
				}
			}
		};
		
		
		AbstractAction inscribir = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, bundle.getString("inscripcion.desea_inscribir"),
						bundle.getString("seleccionar"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

				// 0=yes, 1=no, 2=cancel
				try {
					if (input == 0) {
						JTable table = (JTable) e.getSource();
						int modelRow = Integer.valueOf(e.getActionCommand());
						api.inscribirParticipante(((Concurso) comboBoxConcursos.getSelectedItem()).getCodigo(), (int) table.getValueAt(modelRow, 0));
						JOptionPane.showMessageDialog(null, bundle.getString("inscripcion.exito.inscribir"));

					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				} finally {
					listarParticipantesSeleccion(((Concurso) comboBoxConcursos.getSelectedItem()).getCodigo());
				}
			}
		};
		ButtonColumn buttonColumn3 = new ButtonColumn(table, inscribir, 7);
		buttonColumn3.setMnemonic(KeyEvent.VK_D);

		scrollPane_1.setBounds(12, 386, 681, 171);
		contentPane.add(scrollPane_1);
		
		contentPane.revalidate();
		contentPane.repaint();
		
		scrollPane_1.setViewportView(table_1);
		
		table_1.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { bundle.getString("id"), bundle.getString("nombre"), bundle.getString("apellido"),
						bundle.getString("dni"), bundle.getString("email"), bundle.getString("concurso.eliminar.inscripcion")}) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Object.class, Object.class, Object.class, Object.class, Object.class,
					Boolean.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return column == 0 ? false : true;
			}
		});
		model_1 = (DefaultTableModel) table_1.getModel();
		table_1.getColumnModel().getColumn(0).setMaxWidth(0);
		table_1.getColumnModel().getColumn(0).setMinWidth(0);
		
		table_1.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table_1.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		
	
		table_1.getColumn(bundle.getString("concurso.eliminar.inscripcion")).setCellRenderer(new ButtonRenderer());
		table_1.getColumn(bundle.getString("concurso.eliminar.inscripcion")).setCellEditor(new ButtonEditor(new JCheckBox()));
		
		lblInscriptos.setBounds(288, 365, 161, 15);
		contentPane.add(lblInscriptos);
		
		lblInscriptos.setVisible(false);
		
		ButtonColumn buttonColumn2 = new ButtonColumn(table_1, eliminarInscripcion, 5);
		buttonColumn2.setMnemonic(KeyEvent.VK_D);
		
		AbstractAction publicaciones = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				try {
						JTable table = (JTable) e.getSource();
						int modelRow = Integer.valueOf(e.getActionCommand());
						System.out.println((int) table.getValueAt(modelRow, 0));
						System.out.println(table.getValueAt(modelRow, 1).toString());
						System.out.println(table.getValueAt(modelRow, 2).toString());
						System.out.println(table.getValueAt(modelRow, 3).toString());
						System.out.println(table.getValueAt(modelRow, 4).toString());
						
						Publicacion_Administrar publicaciones = new Publicacion_Administrar(api, idioma,
								api.participanteConInscripciones((int) table.getValueAt(modelRow, 0)));
						publicaciones.setVisible(true);
						
					
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				}
			}
		};
		ButtonColumn buttonColumn4 = new ButtonColumn(table, publicaciones, 8);
		buttonColumn4.setMnemonic(KeyEvent.VK_D);
		
	}

	private void listarParticipantesSeleccion(String seleccion) {
		try {
			// Vacio la tabla
			model.setRowCount(0);
			// Agrego los participantes a la tabla
			for (Participante participante : api.listarNoParticipantesPorSeleccion(seleccion)) {
				model.addRow(new Object[] { participante.getId(), participante.getNombre(), participante.getApellido(),
						participante.getDni(), participante.getEmail(), bundle.getString("modificar"),
						bundle.getString("eliminar"), bundle.getString("concurso.inscribir"), bundle.getString("concurso.publicaciones")});
			}
			
			if (seleccion != "todos") {
				lblInscriptos.setVisible(true);
				scrollPane_1.setVisible(true);
				table_1.setVisible(true);
				// Vacio la tabla
				model_1.setRowCount(0);
				for (Participante participante : api.listarParticipantesPorSeleccion(seleccion)) {
					model_1.addRow(new Object[] { participante.getId(), participante.getNombre(), participante.getApellido(),
							participante.getDni(), participante.getEmail(), bundle.getString("concurso.eliminar.inscripcion")});
				}
			}
			else {
				if ((lblInscriptos.isVisible()) && (scrollPane_1.isVisible())) {
					lblInscriptos.setVisible(false);
					scrollPane_1.setVisible(false);
				}
			}
			
			if ((seleccion.equals("todos"))) {
				if (table.getColumnModel().getColumn(7).getMaxWidth() != 0) {
					
					table.getColumnModel().getColumn(5).setMaxWidth(2147483647);
					table.getColumnModel().getColumn(5).setMinWidth(30);
					table.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(2147483647);
					table.getTableHeader().getColumnModel().getColumn(5).setMinWidth(30);
					//
					table.getColumnModel().getColumn(6).setMaxWidth(2147483647);
					table.getColumnModel().getColumn(6).setMinWidth(30);
					table.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(2147483647);
					table.getTableHeader().getColumnModel().getColumn(6).setMinWidth(30);
					//
					table.getColumnModel().getColumn(7).setMaxWidth(0);
					table.getColumnModel().getColumn(7).setMinWidth(0);
					table.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
					table.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);
				}
			} else {
				if (table.getColumnModel().getColumn(7).getMaxWidth() == 0) {
					//
					table.getColumnModel().getColumn(7).setMaxWidth(2147483647);
					table.getColumnModel().getColumn(7).setMinWidth(30);
					table.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(2147483647);
					table.getTableHeader().getColumnModel().getColumn(7).setMinWidth(30);
				}
				
				table.getColumnModel().getColumn(5).setMaxWidth(0);
				table.getColumnModel().getColumn(5).setMinWidth(0);
				table.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
				table.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
				//
				table.getColumnModel().getColumn(6).setMaxWidth(0);
				table.getColumnModel().getColumn(6).setMinWidth(0);
				table.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
				table.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	/**
	 * Metodo que devuelve todos los participantes y las muestra en la tabla
	 */
	public void listarParticipantes() {
		try {
			// Vacio la tabla
			model.setRowCount(0);

			// Agrego los participantes a la tabla
			for (Participante participante : api.listarParticipantes()) {
				model.addRow(new Object[] { participante.getId(), participante.getNombre(), participante.getApellido(),
						participante.getDni(), participante.getEmail(), bundle.getString("modificar"),
						bundle.getString("eliminar"), bundle.getString("concurso.inscribir"), });
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	/**
	 * Metodo que guarda un participante en la base de datos
	 */
	private void guardarParticipante() {
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

					api.nuevoParticipante(nombre.getText(), apellido.getText(),
							dni.getText(), email.getText());
					// Vacio los inputs
					vaciarInputs();

					// Agrego el participante en la tabla
					listarParticipantes();
					return bundle.getString("participante.exito.agregar");
				} catch (RuntimeException e) {
					return e.getMessage();
				}
			}
		};
		worker.execute();
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
	 * Metodo que vacia los inputs
	 */
	private void vaciarInputs() {
		nombre.setText("");
		apellido.setText("");
		dni.setText("");
		email.setText("");
	}
}
