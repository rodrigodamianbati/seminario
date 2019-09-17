package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
import modelos.Premio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.AbstractAction;
import javax.swing.JButton;

/**
 * JFrame Pantalla para administrar los premios.
 * 
 * @author Rodrigo Batillier
 *
*/

@SuppressWarnings("serial")
public class Premio_Administrar extends JFrame {

	private JPanel contentPane;
	private ResourceBundle bundle;
	private JTable table;
	private DefaultTableModel model;
	private JTextField nombre;
	private Api api;

	public Premio_Administrar(Api ap, String idioma) {
		
		// Creo la api
		api = ap;
		
		// Creo el bundle
		bundle = ResourceBundle.getBundle(idioma);

		setTitle(bundle.getString("premio.titulo"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 554, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel(bundle.getString("nombre"));
		lblNombre.setBounds(62, 11, 46, 14);
		contentPane.add(lblNombre);
		
		nombre = new JTextField();
		nombre.setBounds(118, 8, 195, 20);
		contentPane.add(nombre);
		nombre.setColumns(10);
		
		JButton btnNewButton = new JButton(bundle.getString("premio.agregar"));
		btnNewButton.setBounds(323, 7, 195, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener( e -> guardarPremio());
		
		JButton btnNewButton_1 = new JButton(bundle.getString("volver.principal"));
		btnNewButton_1.setBounds(10, 262, 518, 23);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener( e -> dispose() );
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 84, 518, 115);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("id"), bundle.getString("nombre"), bundle.getString("modificar"), bundle.getString("eliminar")
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Boolean.class, Boolean.class
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
		
		JLabel lblListadoDeJurados = new JLabel(bundle.getString("premio.lista"));
		lblListadoDeJurados.setBounds(211, 59, 116, 14);
		contentPane.add(lblListadoDeJurados);
		
		this.listarPremios();
		
		AbstractAction modificar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, bundle.getString("premio.desea_modificar"),
						bundle.getString("seleccionar"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

				// 0=yes, 1=no, 2=cancel
				System.out.println(input);
				try {
					if (input == 0) {
						JTable table = (JTable) e.getSource();
						int modelRow = Integer.valueOf(e.getActionCommand());
						
						Premio premio = api.modificarPremio( (int) table.getValueAt(modelRow, 0), table.getValueAt(modelRow, 1).toString() );
						// Modifico el premio

						JOptionPane.showMessageDialog(null, bundle.getString("premio.exito.modificar"));
					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				}finally {
					listarPremios();
				}
			}
		};
		
		ButtonColumn buttonColumn1 = new ButtonColumn(table, modificar, 2);
		buttonColumn1.setMnemonic(KeyEvent.VK_D);
		
		AbstractAction eliminar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, bundle.getString("premio.desea_eliminar"),
						bundle.getString("seleccionar"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);

				// 0=yes, 1=no, 2=cancel
				System.out.println(input);
				try {
					if (input == 0) {
						JTable table = (JTable) e.getSource();
						int modelRow = Integer.valueOf(e.getActionCommand());
						
						api.eliminarPremio((int) table.getValueAt(modelRow, 0));
						
						// Saco el premio de la tabla
						model.removeRow(modelRow);
						JOptionPane.showMessageDialog(null, bundle.getString("premio.exito.eliminar"));
					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, a.getMessage());
				}finally {
					listarPremios();
				}
			}
		};
		
		ButtonColumn buttonColumn2 = new ButtonColumn(table, eliminar, 3);
		buttonColumn2.setMnemonic(KeyEvent.VK_D);
	}
	
	/**
	 * Metodo que devuelve todos los premios y las muestra en la tabla
	 */
	private void listarPremios() {
		try {
			// Vacio la tabla
			model.setRowCount(0);
			
			// Agrego los premios a la tabla
			for (Premio premio: api.listarPremios()) {
				model.addRow(new Object[]{
					premio.getId(),
					premio.getNombre(),
					"modificar",
					"eliminar"
				});
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	/**
	 * Metodo que guarda el premio en la base de datos
	 */
	private void guardarPremio() {
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
					
					// Creo el premio a guardar
					Premio premio = api.crearPremio(nombre.getText());
	
					// Vacio los inputs
					nombre.setText("");
					
					// Guardo el premio en la tabla
					listarPremios();
					return bundle.getString("premio.exito.agregar");
				} catch (RuntimeException e) {
					return e.getMessage();
				}
			}
		};
		worker.execute();
	}
	
}
