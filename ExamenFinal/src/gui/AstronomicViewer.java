package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import astronomy.Constellation;
import database.DBException;
import database.DBManager;
import test.ConstelacionTest;

/**
 * Esta clase contiene la ventana principal de la aplicación.
 *
 */
public class AstronomicViewer extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DBManager dbManager; // gestor de base de datos

	private ConstellationListModel constellationListModel; // modelo del JList de constelaciones
	private JList<Constellation> constellationJList; // JList de constelaciones
	private ConstellationInfoPanel constellationInfoPanel; // panel de información
	private JLabel totalStarsInfo; // label con información del número de estrellas totales

	// PANEL PARA LA TABLA
	private JPanel panelContenedorTabla = new JPanel();
	// VARIABLE PARA PODER USAR LA CLASE TABLAMAX
	private TablaMax tm;

	// OTRO JPANEL
	private JPanel panelTabla = new JPanel();

	// BOOLEANS PARA:
	// - VER LA TABLA DE TODAS LAS ESTRELLAS.
	// - VER LA TABLA DE LAS ESTRELLAS VISIBLES.
	// - ORDENAR LAS TABLAS.
	private static boolean activarLaTablaDeEstrella = false;
	private static boolean visible = false;
	private static boolean ordenado = false;

	private static boolean constelacionesOrdenar;

	public AstronomicViewer() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(720, 480);
		setTitle("Visor astron�mico");

		addWindowListener(this);

		try {
			dbManager = new DBManager();
			dbManager.open();
			
			// CREA LA TABLA
			tm = new TablaMax(dbManager);
			panelContenedorTabla.setVisible(true);
			panelContenedorTabla.setBorder(new LineBorder(Color.LIGHT_GRAY));
		} catch (DBException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		// se prepara la UI
		prepareMenuBar();
		prepareConstellationList();
		prepareMainPanel();

		// se carga la lista de constelaciones
		loadConstellations();

		setVisible(true);
	}

	private void prepareMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());

		mainPanel.add(panelContenedorTabla);

		constellationInfoPanel = new ConstellationInfoPanel();
		mainPanel.add(constellationInfoPanel, BorderLayout.NORTH);

		add(mainPanel, BorderLayout.CENTER);

	}

	// JMENUBAR DONDE SE INTEGRAN LAS COSAS
	private void prepareMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileJMenu = new JMenu("Fichero");
		menuBar.add(fileJMenu);

		JMenu constelacionMenu = new JMenu("Constelacion");
		menuBar.add(constelacionMenu);

		// ORDENAR LAS CONSTELACIONES
		// ORDENAR POR ESTRELLAS
		JMenuItem ordenarConstelaciones = new JMenuItem("Por Estrellas");
		ordenarConstelaciones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// CREA UN ARRAYLIST PARA AÑADIR LAS 
				ArrayList<Constellation> nuevo = new ArrayList<>();

				// RECORRE LA LISTA DE CONSTELACIONES QUE HA CREADO UNAI (LA LISTA DE LA IZQUIERDA DE LA VENTANA)
				for (int i = 0; i < constellationListModel.size(); i++) {
					// AÑADE AL ARRAYLIST LA CONSTELACION
					nuevo.add(constellationListModel.get(i));
				}
				
				// USA EL COLLECTIONS.SORT PARA COMPARAR EL ARRAYLIST DE CONSTELACIONES (PRIMERA PARTE), CON EL COMPARADOR DE CANTIDAD.
				Collections.sort(nuevo, new ComparadorConstelacionesCantidad());
				
				// VUELVE A CREAR LA LISTA D ELA IZQUIRDA ESTA VEZ ORDENADA.
				for (int i = 0; i < constellationListModel.size(); i++) {
					constellationListModel.set(i, nuevo.get(i));
					validate();
					repaint();
				}

			}
		});
		constelacionMenu.add(ordenarConstelaciones);
		
		// POR NOMBRE
		// MISMO PROCEDIMIENTO QUE NATES (125-151)
		JMenuItem ordenarConstelaciones2 = new JMenuItem("Por Nombre");
		ordenarConstelaciones2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Constellation> nuevo = new ArrayList<>();

				for (int i = 0; i < constellationListModel.size(); i++) {
					nuevo.add(constellationListModel.get(i));
				}

				Collections.sort(nuevo, new ComparadorConstelacionesAlfabeto());

				for (int i = 0; i < constellationListModel.size(); i++) {
					constellationListModel.set(i, nuevo.get(i));
					validate();
					repaint();
				}

			}
		});
		constelacionMenu.add(ordenarConstelaciones2);

		// SALIR
		JMenuItem exitItem = new JMenuItem("Salir");
		fileJMenu.add(exitItem);

		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		
		
		// CREA LA TABLA DE ESTRELLAS
		JMenu star = new JMenu("Stars");
		menuBar.add(star);

		JMenuItem tablaStar = new JMenuItem("Integrar estrellas");
		tablaStar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// SI EL BOOLEAN ACTIVARLATABLAESTRELLA == FALSE
				if (!activarLaTablaDeEstrella) {
					visible = false;
					activarLaTablaDeEstrella = true;
					// BORRA EL PANEL DE TABLA.
					panelTabla.removeAll();
					repaint();
					validate();
					JOptionPane.showMessageDialog(null, "Se ha activado la tabla de estrellas.");

				} else {
					activarLaTablaDeEstrella = false;
					visible = false;
					panelTabla.removeAll();
					repaint();
					validate();
					JOptionPane.showMessageDialog(null, "Se ha desactivado la tabla de estrellas.");
				}

			}
		});
		star.add(tablaStar);
		
		// CREA LA TABLA DE ESTRELLAS VISIBLES
		JMenuItem tablaStarVsible = new JMenuItem("Integrar estrellas visibles");
		tablaStarVsible.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!visible) {
					visible = true;
					activarLaTablaDeEstrella = true;
					panelTabla.removeAll();
					repaint();
					validate();
					JOptionPane.showMessageDialog(null, "Se ha activado la tabla de estrellas visibles.");

				} else {
					visible = false;
					activarLaTablaDeEstrella = false;
					panelTabla.removeAll();
					repaint();
					validate();
					JOptionPane.showMessageDialog(null, "Tabla de estrellas inactiva.");
				}

			}
		});
		star.add(tablaStarVsible);

		JMenuItem ordenar = new JMenuItem("Ordenar");
		ordenar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ordenado) {
					ordenado = true;
					panelTabla.removeAll();
					repaint();
					validate();
					JOptionPane.showMessageDialog(null, "Tyler te lo he ordenado.");
				} else {
					JOptionPane.showMessageDialog(null, "Ya est� ordenado joe Tyler que pesau <3");
				}

			}
		});
		star.add(ordenar);

	}

	private void exit() {
		try {
			dbManager.close();
			System.exit(0);
		} catch (DBException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void prepareConstellationList() {
		constellationListModel = new ConstellationListModel();
		constellationJList = new JList<Constellation>(constellationListModel);
		constellationJList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		JScrollPane constellationScrollPane = new JScrollPane(constellationJList);

		JPanel constellationPanel = new JPanel();
		constellationPanel.setLayout(new BorderLayout());
		JPanel centeringPanel = new JPanel(new GridBagLayout());
		centeringPanel.add(new JLabel("Constelaciones"));
		constellationPanel.add(centeringPanel, BorderLayout.NORTH);
		constellationPanel.add(constellationScrollPane, BorderLayout.CENTER);

		JPanel totalStarsPanel = new JPanel();
		JLabel totalStarsLabel = new JLabel("Estrellas: ");
		totalStarsInfo = new JLabel();
		totalStarsPanel.add(totalStarsLabel);
		totalStarsPanel.add(totalStarsInfo);

		constellationPanel.add(totalStarsPanel, BorderLayout.SOUTH);

		add(constellationPanel, BorderLayout.WEST);

		constellationJList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				updateUI();
			}
		});
	}

	// AQUI SE CREAN LAS TABLAS
	private void updateUI() {
		if (constellationJList.getSelectedIndex() != -1) {
			Constellation constellation = constellationJList.getSelectedValue();
			
			// AQUI CREA LA TABLA DE TODAS LAS ESTRELLAS
			if (activarLaTablaDeEstrella && !visible) {
				panelContenedorTabla.removeAll();
				panelTabla = tm.panelTabla(constellation.getName(), false, ordenado);
				panelContenedorTabla.add(panelTabla);
				repaint();
				validate();
			
			// AQUI SE CREA LA TABLA DE LAS ESTRELLAS VISIBLES
			} else if (activarLaTablaDeEstrella && visible) {
				panelContenedorTabla.removeAll();
				panelTabla = tm.panelTabla(constellation.getName(), true, ordenado);
				panelContenedorTabla.add(panelTabla);
				repaint();
				validate();
			}

			constellationInfoPanel.setConstellation(constellation);
		} else {
			constellationInfoPanel.clear();
		}
	}

	private void loadConstellations() {
		try {
			int total = 0;

			for (Constellation constellation : dbManager.getConstellations()) {
				constellationListModel.addElement(constellation);
				total += constellation.getStars();
			}

			totalStarsInfo.setText(String.valueOf(total));
		} catch (DBException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new AstronomicViewer();
			}

		});
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		exit();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

}
