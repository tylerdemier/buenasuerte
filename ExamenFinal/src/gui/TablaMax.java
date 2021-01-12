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

public class TablaMax {

	// TREE MAPS QUE RELACIONA:
	// - NOMBRE DE CONSTELACIONES CON ESTRELLAS.
	private static TreeMap<String, ArrayList<Star>> hashConstelaciones = new TreeMap<>();
	// TREE MAP QUE RELACIONA:
	// - NOMBRE DE CONSTELACIONES CON ESTRELLAS
	private static TreeMap<String, ArrayList<Star>> hashConstelacionesVisibles = new TreeMap<>();

	// CREA EL HASHMAP
	// RECIBE:
	// - DBMANAGER DB: PARA RECIBIR DE LA BASE DE DATOS
	public TablaMax(DBManager db) {

		try {

			// CREA DOS LISTAS, UNA DE CONSTELACIONES Y OTRA DE ESTRELLAS.
			// - LISTACONSTELACIONES: RECIBE DE LA BASE DE DATOS TODAS LAS CONSTELACIONES.
			// - ESTRELLAS: ES UNA LISTA DE ARRAYLISTS DE ESTRELLAS.
			List<Constellation> listaConstelaciones = db.getConstellations();
			List<ArrayList<Star>> estrellas = new ArrayList<ArrayList<Star>>();

			// AÑADE LAS ESTRELLAS A LA LISTA
			// RECORRE LA LISTACONSTELACIONES
			for (Constellation constellation : listaConstelaciones) {
				// CREA UNA LISTA DE ESTRELLAS "ESTRELLAS POR CONSTELACION".
				// - ES UN ARRAYLIST DE ESTRELLAS.
				// - RECIBE DE LA BASE DE DATOS LAS ESTRELLAS POR CONSTELACION.
				List<Star> estrellasPorConstelacion = db.getStars(constellation);

				// AÑADE A LA LISTA ESTRELLAS EL ARRAYLIST DE ESTRELLAS POR CONSTELACION.
				estrellas.add((ArrayList<Star>) estrellasPorConstelacion);
			}

			// CREAR EL TREE MAP "HASH CONSTELACIONES":
			// RECORRE CADA ARRAYLIST DE LA LISTA ESTRELLAS
			for (ArrayList<Star> linkedList : estrellas) {
				// RECORRE CADA ESTRELLA DEL ARRAYLIST DE ESTRELLAS
				for (Star estrella : linkedList) {

					// SI EL TREE MAP TIENE LA KEY "NOMBRE DE LA CONSTELACION DE LA ESTRELLA"
					if (hashConstelaciones.containsKey(estrella.getConstellation().getName())) {
						// SE CREA UN ARRAYLIST DE ESTRELLAS
						ArrayList<Star> array = new ArrayList<>();
						// CREA UNA COPIA EXACTA DEL ARRAY QUE YA HAY EN "HASH CONSTELACIONES"
						array = hashConstelaciones.get(estrella.getConstellation().getName());
						// SE AÑADE LA ESTRELLA AL ARRAYLIST
						array.add(estrella);
						// REPLACEA EN EL TREE MAP EN LA CONSTELACION (PRIMERA PARTE), CON EL ARRAY QUE
						// HEMOS CREADO
						hashConstelaciones.replace(estrella.getConstellation().getName(), array);

						// SI EL TREE MAP NO TIENE LA KEY "NOMBRE DE LA CONSTELACION DE LA ESTRELLA"
					} else {
						// CREA UN ARRAYLIST DE ESTRELLAS
						ArrayList<Star> nuevo = new ArrayList<>();
						// AÑADE LA ESTRELLA AL ARRAYLIST
						nuevo.add(estrella);
						// AÑADE AL TREE MAP EN LA CONSTELACION (PRIMERA PARTE), EL NUEVO ARRAYLIST
						hashConstelaciones.put(estrella.getConstellation().getName(), nuevo);
					}

				}
			}

			// MISMO PROCEDIMIENTO QUE EL CODIGO ANTERIOR (55-101) PERO ESTA VEZ SOLO DE
			// ESTRELLAS VISIBLES.
			List<ArrayList<Star>> estrellasVisibles = new ArrayList<ArrayList<Star>>();

			for (Constellation constellation : listaConstelaciones) {
				List<Star> estrellasPorConstelacion = db.getVisibleStars(constellation);
				estrellasVisibles.add((ArrayList<Star>) estrellasPorConstelacion);
			}

			for (ArrayList<Star> arraylist : estrellasVisibles) {
				for (Star estrella : arraylist) {

					if (hashConstelacionesVisibles.containsKey(estrella.getConstellation().getName())) {
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

	// JPANEL QUE CREA LA TABLA, EL CSV, Y ORDENA
	// RECIBE;
	// - STRING C: EL NOMBRE DE LA CONSTELACION QUE ELEGIMOS.
	// - VISIBLE: PARA SABER SI HAY QUE SELECCIONAR LAS ESTRELLAS VISIBLES O TODAS.
	// - ORDENADO: PARA SABER SI QUEREMOS ORDENARLAS.
	public JPanel panelTabla(String c, boolean visible, boolean ordenado) {

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// EL MODELO DE LA JTABLE DONDE SE ESCRIBEN LOS NOMBRES DE LAS COLUMNAS
		DefaultTableModel modelo = new DefaultTableModel(new Object[] { "CONSTELACION", "NOMBRE", "RA", "DEC",
				"DISTANCIA", "MAGNITUD", "LUMINOSIDAD", "TIPO_ESPECTRAL" }, 0);
		JTable tabla = new JTable(modelo);
		tabla.setEnabled(false);

		// UN SCROLL PARA LA JTABLE
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setPreferredSize(new Dimension(500, 250));

		// SI QEUREMOS VER LAS ESTRELLAS VISIBLES
		if (visible) {
			// RECORRE EL HASHMAP DE LAS ESTRELLAS VISIBLES DONDE LA CONSTELACION SEA EL
			// STRING C
			for (Star estrella : hashConstelacionesVisibles.get(c)) {
				// SE CREA UN OBJECT CON EL MISMO ORDEN QUE LAS COLUMNAS DE LA JTABLE
				Object[] coso = { estrella.getConstellation(), estrella.getName(), estrella.getRa(), estrella.getDec(),
						estrella.getDistance(), estrella.getMagnitude(), estrella.getLuminosity(),
						estrella.getSpectralType() };
				// SE AÑADE
				modelo.addRow(coso);
			}

			// SI QUEREMOS TODAS LAS ESTRELLAS
		} else {
			// RECORRE EL HASHMAP DE TODAS LAS ESTRELLAS DONDE LA CONSTELACION SEA EL STRING
			// C
			for (Star estrella : hashConstelaciones.get(c)) {
				// SE CREA UN OBJECT CON EL MISMO ORDEN QUE LAS COLUMNAS DE LA JTABLE
				Object[] coso = { estrella.getConstellation(), estrella.getName(), estrella.getRa(), estrella.getDec(),
						estrella.getDistance(), estrella.getMagnitude(), estrella.getLuminosity(),
						estrella.getSpectralType() };
				// SE AÑADE
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

					if (nombre == null) {
						nombre = c;
					}

					// CREA EL CSV
					PrintStream ps = new PrintStream(nombre + ".csv");
					ps.print("Constelación");
					// CAMBIA DE COLUMNA
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
					// CAMBIA DE LINEA
					ps.println(";");

					// RECORRE LA JTABLE Y AÑADE
					// RECORRE CADA LINEA
					for (int i = 0; i < modelo.getRowCount(); i++) {
						// RECORRE CADA COLUMNA
						for (int j = 0; j < modelo.getColumnCount(); j++) {
							// LO AÑADE
							ps.print(modelo.getValueAt(i, j));
							// CAMBIA DE COLUMNA
							ps.print(";");
						}
						// CAMBIA DE LINEA
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

		// CUENTA LAS ESTRELLAS
		if (visible) {
			JLabel labelNumero = new JLabel("Estrellas visibles totales = " + hashConstelacionesVisibles.get(c).size());
			panelSouth.add(labelNumero);
		} else {
			JLabel labelNumero = new JLabel("Estrellas totales = " + hashConstelaciones.get(c).size());
			panelSouth.add(labelNumero);
		}

		// ORDENA USANDO EL COMPARADOR
		if (ordenado) {
			for (ArrayList<Star> e : hashConstelaciones.values()) {
				// EN EL NEW SE PONE EL NOMBRE DE LA CLASE QUE QUIERAS USAR COMO METODO DE COMPARAR
				Collections.sort(e, new ComparadorEstrellas());
			}
			for (ArrayList<Star> e : hashConstelacionesVisibles.values()) {
				Collections.sort(e, new ComparadorEstrellas());
			}
		}

		return panel;

	}

}
