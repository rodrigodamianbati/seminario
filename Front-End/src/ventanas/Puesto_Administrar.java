package ventanas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import api.Api;
import modelos.Concurso;
import modelos.Jurado;
import modelos.Participante;
import modelos.Premio;
import modelos.Publicacion;
import modelos.Puesto;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Puesto_Administrar extends JFrame {

	private JPanel contentPane;
	private ResourceBundle bundle;
	private JTextField posicion = new JTextField();
	private JComboBox<Premio> premio = new JComboBox();
	private JComboBox<Jurado> jurado = new JComboBox();
	private JTable table, table2, table3;
	private DefaultTableModel model, model2, model3;
	private Api api;
	private JScrollPane scrollPane = new JScrollPane();
	private JScrollPane scrollPane2 = new JScrollPane();
	private JScrollPane scrollPane3 = new JScrollPane();
	private JLabel lblListaPremios = new JLabel();
//	private JLabel lblListaParticipantes = new JLabel();
	private JLabel lblPosicion = new JLabel();
//	private JButton btnEvaluarPublicaciones = new JButton();
//	private JLabel lblSeleccioneJurado = new JLabel();
	private JButton btnAgregar = new JButton();
//	private JButton btnAsignarPuestos = new JButton();
	private JLabel lblPremio = new JLabel();
	private JButton btnEliminar = new JButton();
//	private JButton btnTwittear = new JButton();
//	private JLabel lblParticipantes = new JLabel();
	private JLabel lblPosiciones = new JLabel();
	
	private TableColumn tc = new TableColumn();
	
	public Puesto_Administrar(Api ap, String idioma, Concurso concurso) {
		
		// Creo la api
		api = ap;
		
		// Creo el bundle
		bundle = ResourceBundle.getBundle(idioma);
		
		setTitle(bundle.getString("puesto.titulo") + concurso.getNombre() + " (#" + concurso.getHashtag() + ")");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setBounds(100, 100, 1022, 402);
		setBounds(100, 100, 415, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 135, 380, 144);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
					bundle.getString("id"), bundle.getString("puesto.posicion"),
					bundle.getString("id.premio"), bundle.getString("premio.nombre"),
					bundle.getString("eliminar")
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Object.class, Object.class, Boolean.class
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
		table.getColumnModel().getColumn(2).setMaxWidth(0);
		table.getColumnModel().getColumn(2).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
		
		
		JButton btnVolver = new JButton(bundle.getString("volver.concurso"));

		btnVolver.setBounds(88, 328, 250, 23);
		contentPane.add(btnVolver);
		btnVolver.addActionListener( e -> dispose() );
		
		lblListaPremios = new JLabel(bundle.getString("premio.lista"));

		lblListaPremios.setBounds(10, 110, 150, 14);
		contentPane.add(lblListaPremios);
		
		
		lblPosiciones = new JLabel(bundle.getString("puesto.posiciones"));
		lblPosiciones.setBounds(68, 0, 300, 15);
		contentPane.add(lblPosiciones);

		this.listarPuestos(concurso.getId());
		

	
		verificarConcursoCorregido(concurso);
		
	}
	
	private void verificarConcursoCorregido(Concurso concurso) {
	
		boolean estadoConcurso = api.estadoConcurso(concurso);

		
		if (!estadoConcurso) {
			
			lblPosiciones.setVisible(true);
			this.lblPosicion.setVisible(false);
			this.posicion.setVisible(false);
			this.btnAgregar.setVisible(false);
			this.lblPremio.setVisible(true);
			this.lblListaPremios.setVisible(true);
			
			this.scrollPane.setVisible(true);
//			this.
			
//			// Dia actual
			LocalDate hoy = LocalDate.now();			
//			
//			// Hora actual
			int hora = LocalTime.now().getHour();

			lblPosicion = new JLabel(bundle.getString("puesto.posicion"));
			lblPosicion.setBounds(10, 11, 150, 14);
			contentPane.add(lblPosicion);
			
			
			lblPosicion.removeAll();
			
			posicion = new JTextField();
			posicion.setBounds(10, 36, 150, 20);
			contentPane.add(posicion);
			posicion.setColumns(10);
			
			premio = new JComboBox<Premio>();
			premio.setBounds(195, 36, 156, 20);
			contentPane.add(premio);
			
			btnAgregar = new JButton(bundle.getString("puesto.agregar"));
			btnAgregar.setBounds(88, 67, 189, 23);
			contentPane.add(btnAgregar);
			btnAgregar.addActionListener( e -> guardarPuesto(concurso));
			
			lblPremio = new JLabel(bundle.getString("puesto.premio"));
			lblPremio.setBounds(199, 11, 150, 14);
			contentPane.add(lblPremio);
			
			btnEliminar = new JButton(bundle.getString("eliminar"));
			btnEliminar.setBounds(62, 291, 273, 23);
			contentPane.add(btnEliminar);
			btnEliminar.addActionListener( e -> eliminarPuesto(concurso.getId()));
		
			this.agregarPremios();
		}
	}
	

	
	/**
	 * Metodo que devuelve todos los puestos y los muestra en la tabla
	 * 
	 * @param id
	 * 			parametro de tipo int
	 */
	public void listarPuestos(int id) {
		try {
			// Se vacia la tabla
			model.setRowCount(0);
			//this.evaluarPublicaciones(concurso);
			// Se agrega los premios a la tabla
			for (Puesto puesto: api.listarPuestos(id)) {
				model.addRow(new Object[]{
					puesto.getId(),
					puesto.getPosicion(),
					puesto.getPremio().getId(),
					puesto.getPremio().getNombre(),
					false
				});
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	
	/**
	 * Metodo que guarda el puesto en la base de datos
	 */
	private void guardarPuesto(Concurso concurso) {
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
						// Se crea el Puesto
						Puesto puesto = api.crearPuesto(posicion.getText(), concurso, (Premio) premio.getSelectedItem());
						
						// Se vacian los inputs y se recargan el comboBox
						vaciarInputs();
						
						// Se agrega el concurso en la tabla
						listarPuestos(concurso.getId());
						return bundle.getString("puesto.exito.agregar");					
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
	 * Metodo que elimina un puesto seleccionado en la base de datos
	 */
	private void eliminarPuesto(int id_concurso) {
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
					// Variable entera que indica cantidad seleccionados
					int checkeados = cantidadCheckSeleccionados(4);
					
					// Si un solo elemento es seleccionado se realiza la operacion
					if (checkeados == 1) {
						// Busco puesto a eliminar
						for (int i = 0; i < model.getRowCount(); i++) {
							Boolean checked = Boolean.valueOf(table.getValueAt(i, 4).toString());
							if (checked) {
								// Se elimina el puesto
								api.eliminarPuesto((int) table.getValueAt(i, 0), id_concurso);
								
								// Se saca el puesto de la tabla
								model.removeRow(i);
							}
						}
						return bundle.getString("puesto.exito.eliminar");
					} else {
						// Si se selecciona mas de uno o ninguno se muestra mensaje
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
	 * Metodo que vacia los inputs y setea los comboBox
	 */
	private void vaciarInputs(){
		posicion.setText("");
		premio.setSelectedIndex(0);
	}
	
	/**
	 * Metodo que agrega los premios a la lista seleccionable (comboBox)
	 */
	private void agregarPremios() {
		List<Premio> lista = api.listarPremios();
		
		for (Premio prem: lista){
			premio.addItem(prem);
		}
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