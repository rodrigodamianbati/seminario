package ventanas;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import api.Api;
import modelos.Jurado;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JButton;

/**
 * JFrame Pantalla para administrar los jurados.
 * 
 * @author Rodrigo Batillier
 *
*/

@SuppressWarnings("serial")
public class Jurado_Administrar extends JFrame {

	private JPanel contentPane;
	private ResourceBundle bundle;
	private JTextField nombre;
	private JTextField apellido;
	private JButton btnNewButton_1;
	private DefaultTableModel model;
	private JTable table;
	private JLabel lblListaJurados;
	private Api api;

	public Jurado_Administrar(String idioma) {
		
		// Creo el bundle
		bundle = ResourceBundle.getBundle(idioma);
		
		// Creo la api
		api = new Api();
		
		setTitle(bundle.getString("jurado.titulo"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 559, 429);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel(bundle.getString("nombre"));
		lblNombre.setBounds(163, 11, 68, 14);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel(bundle.getString("apellido"));
		lblApellido.setBounds(163, 36, 68, 14);
		contentPane.add(lblApellido);
		
		nombre = new JTextField();
		nombre.setBounds(226, 8, 165, 20);
		contentPane.add(nombre);
		nombre.setColumns(10);
		
		apellido = new JTextField();
		apellido.setBounds(226, 33, 165, 20);
		contentPane.add(apellido);
		apellido.setColumns(10);
		
		JButton btnNewButton = new JButton(bundle.getString("jurado.agregar"));
		btnNewButton.setBounds(142, 61, 277, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener( e -> guardarJurado());
		
		btnNewButton_1 = new JButton(bundle.getString("volver.principal"));
		btnNewButton_1.setBounds(10, 357, 523, 23);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener( e -> dispose() );
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 135, 523, 167);
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
					bundle.getString("modificar"), 
					bundle.getString("eliminar")
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Object.class, Boolean.class, Boolean.class
				};
				@SuppressWarnings({ "unchecked", "rawtypes" })
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
		
		lblListaJurados = new JLabel(bundle.getString("jurado.lista"));
		lblListaJurados.setBounds(227, 110, 111, 14);
		contentPane.add(lblListaJurados);
		
		JButton btnNewButton_2 = new JButton(bundle.getString("eliminar"));
		btnNewButton_2.setBounds(10, 313, 250, 23);
		contentPane.add(btnNewButton_2);
		btnNewButton_2.addActionListener(e -> eliminarJurados());
		
		JButton btnNewButton_3 = new JButton(bundle.getString("modificar"));
		btnNewButton_3.setBounds(267, 313, 266, 23);
		contentPane.add(btnNewButton_3);
		btnNewButton_3.addActionListener( e -> modificarJurados());
		
		this.listarJurados();
	}
	
	/**
	 * Metodo que devuelve todos los jurados y las muestra en la tabla
	 */
	private void listarJurados() {
		try {
			// Vacio la tabla
			model.setRowCount(0);
			
			// Agrego los jurados a la tabla
			for (Jurado jurado: api.listarJurados()) {
				model.addRow(new Object[]{
					jurado.getId(),
					jurado.getNombre(),
					jurado.getApellido(),
					false,
					false
				});
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	/**
	 * Metodo que guarda un jurado en la base de datos
	 */
	private void guardarJurado() {
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
					// Si el jurado existe muestro mensaje en pantalla
					if (api.existeJurado(nombre.getText(), apellido.getText())){
						return bundle.getString("jurado.repetida");
					}
					
					// Creo el jurado
					Jurado jurado = api.crearJurado(nombre.getText(), apellido.getText());
					
					// Guardo el jurado en la base de datos
					api.guardarJurado(jurado);
					
					// Vacio los inputs
					nombre.setText("");
					apellido.setText("");
					
					// Agrego el jurado a la tabla
					listarJurados();
					return bundle.getString("jurado.exito.agregar");
				} catch (RuntimeException e) {
					return e.getMessage();
				}
			}
		};
		worker.execute();
	}
	
	/**
	 * Metodo que modifica el jurado seleccionado en la base de datos
	 */
	private void modificarJurados() {
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
					int checkeados = cantidadCheckSeleccionados(3);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						
						for (int i = 0; i < model.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table.getValueAt(i, 3).toString());
							if (checked) {
								// Si el jurado no esta repetido lo modifico
								if (!api.existeJurado(table.getValueAt(i, 1).toString(), table.getValueAt(i, 2).toString())) {
									// Creo el jurado
									Jurado jurado = api.crearJurado( (int) table.getValueAt(i, 0), table.getValueAt(i, 1).toString(), 
											table.getValueAt(i, 2).toString() );
									
									// Modifico el jurado
									api.modificarJurado(jurado);
									
									// Agrego el jurado en la tabla
									listarJurados();
									return bundle.getString("jurado.exito.modificar");
								}
							}
						}
					
						listarJurados();
						
						// Si no se modifico es porque ya se encuentra en la base de datos.
						return bundle.getString("jurado.repetida");
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
	 * Metodo que elimina el jurado seleccionado 
	 */
	private void eliminarJurados() {
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
					int checkeados = cantidadCheckSeleccionados(4);
					
					// Si se selecciono un solo elemento se realiza la operacion
					if (checkeados == 1) {
						for (int i = 0; i < model.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table.getValueAt(i, 4).toString());
							if (checked) {
								// Elimino el jurado
								api.eliminarJurado((int) table.getValueAt(i, 0));
								
								// Saco el jurado de la tabla
								model.removeRow(i);
							}
						}
						
						return bundle.getString("jurado.exito.eliminar");
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
}
