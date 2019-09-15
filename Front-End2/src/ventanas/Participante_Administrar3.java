package ventanas;

import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

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
public class Participante_Administrar3 extends JFrame {

	private JPanel contentPane;
	private ResourceBundle bundle;
	//table y model
	private JTable table;
	private DefaultTableModel model;
	//table y model
	private JTextField nombre;
	private JTextField apellido;
	private JTextField dni;
	private JTextField email;
	private Api api;
	private JScrollPane scrollPane;
	private TableColumnManager tcm;

	public Participante_Administrar3(String idioma) {
		// Creo la api
		api = new Api();
		
		// Creo el bundle
		bundle = ResourceBundle.getBundle(idioma);
		
		setTitle(bundle.getString("participante.titulo"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setBounds(100, 100, 663, 495);
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
		btnNewButton.addActionListener(e -> dispose() );
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 191, 627, 250);
		contentPane.add(scrollPane);
		
	
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("id"), 
					bundle.getString("nombre"),
					bundle.getString("apellido"),
					bundle.getString("dni"),
					bundle.getString("email"),
					bundle.getString("modificar"), 
					bundle.getString("eliminar"),
					bundle.getString("ver_publicaciones")
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Object.class, Object.class, Object.class, Boolean.class, Boolean.class, Boolean.class
				};
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				
				public boolean isCellEditable(int row, int column) {
					return column == 0 ? false : true;
			    }
			}
		);
		model = (DefaultTableModel) table.getModel();
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		
		JLabel lblListaParticipantes = new JLabel(bundle.getString("listadoparticipante.titulo"));
		lblListaParticipantes.setBounds(236, 165, 384, 14);
		contentPane.add(lblListaParticipantes);
		
		String[] elementos = new String[50]; 
		elementos[0] = "todos";
		List<Concurso> listadoConcursos = api.listarConcursos();
		int i = 1;
		for (Concurso concurso : listadoConcursos) {
			elementos[i] = concurso.getNombre();
			i++;
		}
		
		JComboBox comboBox = new JComboBox(elementos);
		comboBox.setBounds(662, 191, 134, 24);
//		String[] elementos = {"todos", "con publicaciones", "con inscripciones"};
//		comboBox = new JComboBox(elementos);
		contentPane.add(comboBox);
		comboBox.addActionListener (e -> listarParticipantesSeleccion((String)comboBox.getSelectedItem()));
		
		JLabel lblParticipantes = new JLabel("Participantes");
		lblParticipantes.setBounds(662, 165, 112, 15);
		contentPane.add(lblParticipantes);
		
//		JButton btnNewButton_1 = new JButton(bundle.getString("eliminar"));
//		btnNewButton_1.setBounds(10, 345, 202, 23);
//		contentPane.add(btnNewButton_1);
//		btnNewButton_1.addActionListener(e -> eliminarParticipantes());
		
		table.getColumn(bundle.getString("eliminar")).setCellRenderer(new ButtonRenderer());
	    table.getColumn(bundle.getString("eliminar")).setCellEditor(new ButtonEditor(new JCheckBox()));
		
//		JButton btnNewButton_2 = new JButton(bundle.getString("modificar"));
//		btnNewButton_2.setBounds(215, 345, 202, 23);
//		contentPane.add(btnNewButton_2);
//		btnNewButton_2.addActionListener(e -> modificarParticipantes());
		
		table.getColumn(bundle.getString("modificar")).setCellRenderer(new ButtonRenderer());
	    table.getColumn(bundle.getString("modificar")).setCellEditor(new ButtonEditor(new JCheckBox()));
	 
		
//		JButton btn = new JButton(bundle.getString("participante.lista_inscripcion_publicacion"));
//		btn.setBounds(427, 346, 210, 23);
//		contentPane.add(btn);
//		btn.addActionListener( e -> agregarInscripciones(idioma));
	    
	    table.getColumn(bundle.getString("ver_publicaciones")).setCellRenderer(new ButtonRenderer());
	    table.getColumn(bundle.getString("ver_publicaciones")).setCellEditor(new ButtonEditor(new JCheckBox()));
	    
	    tcm = new TableColumnManager(table);
	    
		this.listarParticipantesSeleccion("todos");
		
//		DefaultTableModel dm = new DefaultTableModel();
//	    dm.setDataVector(new Object[][] { { "button 1", "foo" }, { "button 2", "bar" }, { "button 3", "prueba" }, { "button 4", "prueba" } },  new Object[] { "Button", "String" });
//
//	    JTable table1 = new JTable(dm);
//	    //table1.setSize(0, 0);
//	    table1.getColumn("Button").setCellRenderer(new ButtonRenderer());
//	    table1.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
//	    
//	    //JScrollPane scroll1 = new JScrollPane(table1);
//	    //scroll1.setBounds(10, 191, 627, 143);
//	    //getContentPane().add(scroll1);
//	    table1.setBounds(29, 380, 627, 167);
//		contentPane.add(table1);
		
		//table_1 = new JTable((TableModel) null);
		//table_1.setBounds(13, 380, 624, 29);
		//contentPane.add(table_1);
	    //setSize(800, 400);
	    //setVisible(true);
		
		AbstractAction delete = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	int input = JOptionPane.showConfirmDialog(null, "¿Desea eliminar este participante", "Seleccione una opcion...",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
				
			// 0=yes, 1=no, 2=cancel
		    	System.out.println(input);
		    	try {
		    	if (input == 0) {
		    		JTable table = (JTable)e.getSource();
		    		int modelRow = Integer.valueOf( e.getActionCommand() );
		    		
		    		System.out.println(modelRow);
		    		System.out.println((int) table.getValueAt(modelRow, 0));
		    		System.out.println(table.getValueAt(modelRow, 1));
		    		System.out.println(table.getValueAt(modelRow, 2));
		    		System.out.println(table.getValueAt(modelRow, 3));
		    		api.eliminarParticipante((int) table.getValueAt(modelRow, 0));
		    		
		    		((DefaultTableModel)table.getModel()).removeRow(modelRow);
		    	}
		    	} catch(Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				}
		    }
		};
		
		ButtonColumn buttonColumn = new ButtonColumn(table, delete, 6);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
		AbstractAction modificar = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e){
		    	int input = JOptionPane.showConfirmDialog(null, "¿Desea modificar este participante", "Seleccione una opcion...",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
				
			// 0=yes, 1=no, 2=cancel
		    	System.out.println(input);
		    	try {
		    	if (input == 0) {
		    		JTable table = (JTable)e.getSource();
		    		int modelRow = Integer.valueOf( e.getActionCommand() );
		    		
					// Creo el participante
					Participante participante = api.crearParticipante( (int) table.getValueAt(modelRow, 0), 
							table.getValueAt(modelRow, 1).toString(), table.getValueAt(modelRow, 2).toString(), 
							table.getValueAt(modelRow, 3).toString(), table.getValueAt(modelRow, 4).toString());
					
					System.out.println(participante);
					
					// Modifico el participante
					api.modificarParticipante(participante);
					JOptionPane.showMessageDialog(null, bundle.getString("particpante.exito.modificar"));
			
		    	}
		    	} catch(Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				} finally {
					listarParticipantesSeleccion((String)comboBox.getSelectedItem());
				}
		    }
		};
		
		ButtonColumn buttonColumn1 = new ButtonColumn(table, modificar, 5);
		buttonColumn1.setMnemonic(KeyEvent.VK_D);
	}
	
	private void listarParticipantesSeleccion(String seleccion) {
		try {
			// Vacio la tabla
			model.setRowCount(0);
//			model.getColumnModel().getColumn(5).setHeaderValue("newHeader");
			
//			JTableHeader th = table.getTableHeader();
//			TableColumnModel tcm = th.getColumnModel();
//			TableColumn tc = tcm.getColumn(0);
//			tc.setHeaderValue( "???" );
//			th.repaint();
				
				// Agrego los participantes a la tabla
				for (Participante participante: api.listarParticipantesPorSeleccion(seleccion)) {
					model.addRow(new Object[]{
						participante.getId(),
						participante.getNombre(),
						participante.getApellido(),
						participante.getDni(),
						participante.getEmail(),
						bundle.getString("modificar"), 
						bundle.getString("eliminar"),
						bundle.getString("ver")
					});
				}

			if ((seleccion.equals("todos")) && (table.getColumnCount() == 8)) {
				this.tcm.hideColumn(bundle.getString("ver_publicaciones"));
			}else {
				if (!(seleccion.equals("todos")) && (table.getColumnCount() == 7)){
					this.tcm.showColumn(bundle.getString("ver_publicaciones"));
				}	
			}
			
		} catch(Exception e) {
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
			for (Participante participante: api.listarParticipantes()) {
				model.addRow(new Object[]{
					participante.getId(),
					participante.getNombre(),
					participante.getApellido(),
					participante.getDni(),
					participante.getEmail(),
					bundle.getString("modificar"), 
					bundle.getString("eliminar"),
					bundle.getString("ver"),
				});
			}
		
		} catch(Exception e) {
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
					// Si ya existe el participante muestro mensaje
					if (api.existeParticipante(dni.getText())){
						return bundle.getString("participante.repetida");
					}
					
					// Creo el participante
					Participante participante = api.crearParticipante(nombre.getText(), apellido.getText(), dni.getText(), 
					email.getText());
					
					// Guardo el participante en la base de datos
					api.nuevoParticipante(participante);
					
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
	 * Metodo que modifica el participante seleccionado en la base de datos
	 */
	private void modificarParticipantes() {
		SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
			protected void done() {
				try {
					String mensajeError = get();
					listarParticipantes();
					JOptionPane.showMessageDialog(null, mensajeError);
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}

			protected String doInBackground() {
				try {
				
								// Si ya existe el participante muestro mensaje
								if (!api.existeParticipante(dni.getText())){
									// Creo el participante
//									Participante participante = api.crearParticipante( (int) table.getValueAt(i, 0), 
//											table.getValueAt(i, 1).toString(), table.getValueAt(i, 2).toString(), 
//											table.getValueAt(i, 3).toString(), table.getValueAt(i, 4).toString());
//									
//									// Modifico el participante
//									api.modificarParticipante(participante);
//									
//									listarParticipantes();
//									return bundle.getString("particpante.exito.modificar");
								}
						
						listarParticipantes();
						return bundle.getString("participante.repetida");
				
				} catch (RuntimeException e) {
					return e.getMessage();
				}
			}
		};
		worker.execute();
	}
	
	
	
	    public void actionPerformed(ActionEvent e)
	    {
	        JTable table = (JTable)e.getSource();
	        int modelRow = Integer.valueOf( e.getActionCommand() );
	        ((DefaultTableModel)table.getModel()).removeRow(modelRow);
	    }
	
	
	/**
	 * Metodo que elimina el participante seleccionado en la base de datos
	 */
	private void eliminarParticipantes() {
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
					int checkeados = cantidadCheckSeleccionados(6);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						// Busco el participante a eliminar
						for (int i = 0; i < model.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table.getValueAt(i, 6).toString());
							if (checked) {
								// Elimino el participante en la base de datos
								api.eliminarParticipante((int) table.getValueAt(i, 0));
								
								// Saco el participante de la tabla
								model.removeRow(i);
							}
						}
						
						return bundle.getString("participante.exito.eliminar");
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
	 * Metodo que lista las inscripciones del participante seleccionado
	 */
	private void agregarInscripciones(String idioma) {
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
					int checkeados = cantidadCheckSeleccionados(7);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						// Busco el participante a mostrar sus 9inscripciones y publicaciones
						for (int i = 0; i < model.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table.getValueAt(i, 7).toString());
//							if (checked) {
//								// Creo la pantalla
//								Inscripcion_Publicacion_Administrar inscripcion = new Inscripcion_Publicacion_Administrar(idioma,
//										api.crearParticipante((int) table.getValueAt(i, 0),  table.getValueAt(i, 1).toString(),
//										table.getValueAt(i, 2).toString(), table.getValueAt(i, 3).toString(),
//										table.getValueAt(i, 4).toString()));
//								inscripcion.setVisible(true);
//							}
						}
						return bundle.getString("inscripcion.concursos_mostrar");
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
