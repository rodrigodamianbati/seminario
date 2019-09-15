package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import api.Api;
import modelos.Participante;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class Prueba extends JFrame {
	
	private ResourceBundle bundle;
	private JPanel contentPane;
	private JLabel label;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JLabel label_1;
	private JButton buttonIngresar;
	private JButton buttonEliminar;
	private JButton button;
	private JTable table;
	private Api api;
	private String idioma;
	
	/*
	 * private JPanel contentPane;
	private ResourceBundle bundle;
	private JTable table, table2;
	private DefaultTableModel model, model2, model3, model3b, model4, model4b;
	private Api api;
	 */

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prueba frame = new Prueba();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} */

	/**
	 * Create the frame.
	 */
	public Prueba(/*String idioma, Participante participante*/) {
		
		this.bundle = ResourceBundle.getBundle("properties/mensajes_es_AR");
		this.idioma = "properties/mensajes_es_AR";
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 543, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel((String) null);
		label.setBounds(22, 0, 255, 14);
		contentPane.add(label);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 12, 317, 117);
		contentPane.add(scrollPane);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(22, 172, 317, 136);
		contentPane.add(scrollPane_1);
		
		label_1 = new JLabel((String) null);
		label_1.setBounds(22, 162, 310, 14);
		contentPane.add(label_1);
		
		buttonIngresar = new JButton((String) null);
		buttonIngresar.setBounds(22, 127, 317, 23);
		contentPane.add(buttonIngresar);
		
		buttonEliminar = new JButton((String) null);
		buttonEliminar.setBounds(22, 308, 317, 23);
		contentPane.add(buttonEliminar);
		
		button = new JButton((String) null);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(87, 336, 392, 23);
		contentPane.add(button);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(351, 12, 116, 24);
		contentPane.add(comboBox);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prueba frame = new Prueba();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
