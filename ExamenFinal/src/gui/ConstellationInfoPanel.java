package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import astronomy.Constellation;

/**
 * Esta clase define el panel de información de una
 * constelación
 *
 */
public class ConstellationInfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel abbrvInfo;
	private JLabel nameInfo;
	private JLabel meaningInfo;
	private JLabel originInfo;
	private JLabel starsInfo;

	public ConstellationInfoPanel() {
		super(new GridLayout(3, 2));
		
		Border border = BorderFactory.createTitledBorder("Informacion");
		setBorder(border);
		
		JPanel abbrvPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel abbrvLabel = new JLabel("Abbrv: ");
		abbrvInfo = new JLabel();
		
		abbrvPanel.add(abbrvLabel);
		abbrvPanel.add(abbrvInfo);
		
		add(abbrvPanel);
		
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLabel = new JLabel("Nombre: ");
		nameInfo = new JLabel();
		
		namePanel.add(nameLabel);
		namePanel.add(nameInfo);
		
		add(namePanel);
		
		JPanel starsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel starsLabel = new JLabel("Estrellas: ");
		starsInfo = new JLabel();
		
		starsPanel.add(starsLabel);
		starsPanel.add(starsInfo);
		
		add(starsPanel);
		
		JPanel meaningPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel meaningLabel = new JLabel("Significado: ");
		meaningInfo = new JLabel();
		
		meaningPanel.add(meaningLabel);
		meaningPanel.add(meaningInfo);
		
		add(meaningPanel);
		
		JPanel originPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel originLabel = new JLabel("Origen: ");
		originInfo = new JLabel();
		
		originPanel.add(originLabel);
		originPanel.add(originInfo);
		
		add(originPanel);
		
		add(new JPanel());
	}
	
	public void setConstellation(Constellation constellation) {
		nameInfo.setText(constellation.getName());
		abbrvInfo.setText(constellation.getAbbrv());
		meaningInfo.setText(constellation.getMeaning());
		originInfo.setText(constellation.getOrigin());
		starsInfo.setText(String.valueOf(constellation.getStars()));
	}
	
	public void clear() {
		nameInfo.setText("");
		abbrvInfo.setText("");
		meaningInfo.setText("");
		originInfo.setText("");
		starsInfo.setText("");
	}
}
