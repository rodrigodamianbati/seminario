package ventanas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import api.Api;
import modelos.Concurso;
import modelos.Participante;

@SuppressWarnings("serial")
public class Inscripcion_Publicacion_Administrar2 extends JFrame {

	private JPanel contentPane;
	private ResourceBundle bundle;
	private JTable table, table2;
	private DefaultTableModel model, model2, model3, model3b, model4, model4b;
	private Api api;
	
	private Action action;
	private int mnemonic;
	private Border originalBorder;
	private Border focusBorder;

	private JButton renderButton;
	private JButton editButton;
	private Object editorValue;
	private boolean isButtonColumnEditor;
	
	public Inscripcion_Publicacion_Administrar2(String idioma, Participante participante, int column) {
		
		/**
		 *  Create the ButtonColumn to be used as a renderer and editor. The
		 *  renderer and editor will automatically be installed on the TableColumn
		 *  of the specified column.
		 *
		 *  @param table the table containing the button renderer/editor
		 *  @param action the Action to be invoked when the button is invoked
		 *  @param column the column to which the button renderer/editor is added
		 */		
			this.table = table;
			this.action = action;

			renderButton = new JButton();
			editButton = new JButton();
			editButton.setFocusPainted( false );
			editButton.addActionListener( (ActionListener) this );
			originalBorder = editButton.getBorder();
			//setFocusableWindowState( new LineBorder(Color.BLUE) );

			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(column).setCellRenderer( (TableCellRenderer) this );
			columnModel.getColumn(column).setCellEditor( (TableCellEditor) this );
			table.addMouseListener( (MouseListener) this );
		
			
		
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
		
		
		this.listarConcursos();
		this.listarConcursosParticipante(participante.getId());
		this.listarConcursosInscripcionCerrada(participante.getId());
		//this.listarConcursosPublicacionAbierta(participante.getId());
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
	 *  Get foreground color of the button when the cell has focus
	 *
	 *  @return the foreground color
	 */
	public Border getFocusBorder()
	{
		return focusBorder;
	}

	/**
	 *  The foreground color of the button when the cell has focus
	 *
	 *  @param focusBorder the foreground color
	 */
	public void setFocusBorder(Border focusBorder)
	{
		this.focusBorder = focusBorder;
		editButton.setBorder( focusBorder );
	}

	public int getMnemonic()
	{
		return mnemonic;
	}

	/**
	 *  The mnemonic to activate the button when the cell has focus
	 *
	 *  @param mnemonic the mnemonic
	 */
	public void setMnemonic(int mnemonic)
	{
		this.mnemonic = mnemonic;
		renderButton.setMnemonic(mnemonic);
		editButton.setMnemonic(mnemonic);
	}

	public Component getTableCellEditorComponent(
		JTable table, Object value, boolean isSelected, int row, int column)
	{
		if (value == null)
		{
			editButton.setText( "" );
			editButton.setIcon( null );
		}
		else if (value instanceof Icon)
		{
			editButton.setText( "" );
			editButton.setIcon( (Icon)value );
		}
		else
		{
			editButton.setText( value.toString() );
			editButton.setIcon( null );
		}

		this.editorValue = value;
		return editButton;
	}

	public Object getCellEditorValue()
	{
		return editorValue;
	}

//
//  Implement TableCellRenderer interface
//
	public Component getTableCellRendererComponent(
		JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		if (isSelected)
		{
			renderButton.setForeground(table.getSelectionForeground());
	 		renderButton.setBackground(table.getSelectionBackground());
		}
		else
		{
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}

		if (hasFocus)
		{
			renderButton.setBorder( focusBorder );
		}
		else
		{
			renderButton.setBorder( originalBorder );
		}

//		renderButton.setText( (value == null) ? "" : value.toString() );
		if (value == null)
		{
			renderButton.setText( "" );
			renderButton.setIcon( null );
		}
		else if (value instanceof Icon)
		{
			renderButton.setText( "" );
			renderButton.setIcon( (Icon)value );
		}
		else
		{
			renderButton.setText( value.toString() );
			renderButton.setIcon( null );
		}

		return renderButton;
	}

//
//  Implement ActionListener interface
//
	/*
	 *	The button has been pressed. Stop editing and invoke the custom Action
	 */
	public void actionPerformed(ActionEvent e)
	{
		int row = table.convertRowIndexToModel( table.getEditingRow() );
		fireEditingStopped();

		//  Invoke the Action

		ActionEvent event = new ActionEvent(
			table,
			ActionEvent.ACTION_PERFORMED,
			"" + row);
		action.actionPerformed(event);
	}

private void fireEditingStopped() {
		// TODO Auto-generated method stub
		
	}

	//
//  Implement MouseListener interface
//
	/*
	 *  When the mouse is pressed the editor is invoked. If you then then drag
	 *  the mouse to another cell before releasing it, the editor is still
	 *  active. Make sure editing is stopped when the mouse is released.
	 */
    public void mousePressed(MouseEvent e)
    {
    	if (table.isEditing()
		&&  table.getCellEditor() == this)
			isButtonColumnEditor = true;
    }

    public void mouseReleased(MouseEvent e)
    {
    	if (isButtonColumnEditor
    	&&  table.isEditing())
    		table.getCellEditor().stopCellEditing();

		isButtonColumnEditor = false;
    }

    public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
	
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
				default:
				mod = model3;
			JTable table3 = null;
			tab = table3;
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
