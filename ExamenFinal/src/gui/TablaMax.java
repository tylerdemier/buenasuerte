package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import astronomy.ComparadorEstrellas;
import astronomy.Constellation;
import astronomy.Star;
import database.DBException;
import database.DBManager;

public class TablaMax{


	private static TreeMap<String, ArrayList<Star>> hashConstelaciones = new TreeMap<>();
	private static TreeMap<String, ArrayList<Star>> hashConstelacionesVisibles = new TreeMap<>();


	public TablaMax(DBManager db) {

		try {

			List<Constellation> listaConstelaciones = db.getConstellations();
			List<ArrayList<Star>> estrellas = new ArrayList<ArrayList<Star>>();

			for (Constellation constellation : listaConstelaciones) {
				List<Star> estrellasPorConstelacion = db.getStars(constellation);
				estrellas.add((ArrayList<Star>) estrellasPorConstelacion);
			}

			for (ArrayList<Star> linkedList : estrellas) {
				for (Star estrella : linkedList) {

					if(hashConstelaciones.containsKey(estrella.getConstellation().getName())) {
						ArrayList<Star> array = new ArrayList<>();
						array = hashConstelaciones.get(estrella.getConstellation().getName());
						array.add(estrella);
						hashConstelaciones.replace(estrella.getConstellation().getName(), array);
					} else {
						ArrayList<Star> nuevo = new ArrayList<>();
						nuevo.add(estrella);
						hashConstelaciones.put(estrella.getConstellation().getName(), nuevo);
					}

				}
			}
			
			
			List<ArrayList<Star>> estrellasVisibles = new ArrayList<ArrayList<Star>>();

			for (Constellation constellation  : listaConstelaciones) {
				List<Star> estrellasPorConstelacion = db.getVisibleStars(constellation);
				estrellasVisibles.add((ArrayList<Star>) estrellasPorConstelacion);
			}
			
			
			for (ArrayList<Star> arraylist  : estrellasVisibles) {
				for (Star estrella : arraylist) {

					if(hashConstelacionesVisibles.containsKey(estrella.getConstellation().getName())) {
						ArrayList<Star> array = new ArrayList<>();
						array = hashConstelacionesVisibles.get(estrella.getConstellation().getName());
						array.add(estrella);
						hashConstelacionesVisibles.replace(estrella.getConstellation().getName(), array);

					} else {
						ArrayList<Star> nuevo = new ArrayList<>();
						nuevo.add(estrella);
						hashConstelacionesVisibles.put(estrella.getConstellation().getName(), nuevo);
					}
				}
			}
			

		} catch (DBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		

	}


	public JPanel panelTabla(String c, boolean visible, boolean ordenado) {

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());


		DefaultTableModel modelo = new DefaultTableModel(new Object[] { "CONSTELACION", "NOMBRE", "RA", "DEC",
				"DISTANCIA", "MAGNITUD", "LUMINOSIDAD", "TIPO_ESPECTRAL" }, 0);
		JTable tabla = new JTable(modelo);
		tabla.setEnabled(false);
		JScrollPane scroll = new JScrollPane(tabla);

		scroll.setPreferredSize(new Dimension(500,250));

		if(visible) {
			for (Star estrella : hashConstelacionesVisibles.get(c)) {
				Object[] coso = {estrella.getConstellation(), estrella.getName(), estrella.getRa(), estrella.getDec(), 
						estrella.getDistance(), estrella.getMagnitude(), estrella.getLuminosity(), estrella.getSpectralType() };

				modelo.addRow(coso);
			}
		} else {
			for (Star estrella : hashConstelaciones.get(c)) {
				Object[] coso = {estrella.getConstellation(), estrella.getName(), estrella.getRa(), estrella.getDec(), 
						estrella.getDistance(), estrella.getMagnitude(), estrella.getLuminosity(), estrella.getSpectralType() };

				modelo.addRow(coso);
			}
		}

		panel.add(scroll, BorderLayout.CENTER);
		panel.setVisible(true);

		JPanel panelSouth = new JPanel();
		JButton botonExcel = new JButton("Excel-ear tabla");
		panelSouth.add(botonExcel);
		panel.add(panelSouth, BorderLayout.SOUTH);

		botonExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					String nombre = JOptionPane.showInputDialog(null, "Introduce un nombre para el archivo: ");

					if(nombre == null) {
						nombre = c;
					}

					PrintStream ps = new PrintStream(nombre + ".csv");
					ps.print("Constelación");
					ps.print(";");
					ps.print("Nombre");
					ps.print(";");
					ps.print("RA");
					ps.print(";");
					ps.print("DEC");
					ps.print(";");
					ps.print("Distancia");
					ps.print(";");
					ps.print("Magnitud");
					ps.print(";");
					ps.print("Luminosidad");
					ps.print(";");
					ps.print("Tipo Espectral");
					ps.println(";");

					for (int i = 0; i < modelo.getRowCount(); i++) {
						for (int j = 0; j < modelo.getColumnCount(); j++) {
							ps.print(modelo.getValueAt(i, j));
							ps.print(";");
						}
						ps.println("");
						
					}

					JOptionPane.showMessageDialog(null, "Se ha guardado en:" + nombre + ".csv");

				} catch (FileNotFoundException e2) {
					JOptionPane.showMessageDialog(null, "No existe el archivo.");
					e2.printStackTrace();
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});

		if(visible) {
			JLabel labelNumero = new JLabel("Estrellas visibles totales = " + hashConstelacionesVisibles.get(c).size());
			panelSouth.add(labelNumero);
		} else {
			JLabel labelNumero = new JLabel("Estrellas totales = " + hashConstelaciones.get(c).size());
			panelSouth.add(labelNumero);
		}
		
		if(ordenado) {
			for (ArrayList<Star> e : hashConstelaciones.values()) {
				Collections.sort(e, new ComparadorEstrellas());
			}
			for (ArrayList<Star> e : hashConstelacionesVisibles.values()) {
				Collections.sort(e, new ComparadorEstrellas());
			}
		}


		return panel;

	}

}
