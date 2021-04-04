package admin;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Library;

public class AdminPagePanel extends JPanel {

	JLabel hello;
	
	public AdminPagePanel(Library library) {
		setBounds(0, 0, 1280, 720); // À§Ä¡¿Í Å©±â ÁöÁ¤
		setLayout(null);
		setBackground(Color.WHITE);
		
		hello = new JLabel("Â§");
		hello.setFont(new Font("³ª´®°íµñ ExtraBold", Font.BOLD, 35));
		hello.setBounds(1150, 35, 500, 50);
		add(hello);
	}
}
