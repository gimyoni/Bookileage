package main;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LoginGUI {
	public LoginGUI() {
	}
	public static void main(String[] args) {
		LoginPanel login_panel = new LoginPanel();
	}
}

class LoginPanel extends JPanel{
	
	static JTextField tf_login_id;
	static JPasswordField pf_login_pw;
	
	LoginPanel(){
		setBounds(0, 0, 1280, 720); // 위치와 크기 지정
		setLayout(null);
		setVisible(true);
		setBackground(Color.WHITE);
				
		ButtonGroup login_opt_group = new ButtonGroup();
        JRadioButton ra1 = new JRadioButton("회원", true);
        ra1.setBounds(540, 100, 100, 50);
        ra1.setBackground(Color.white);
        JRadioButton ra2 = new JRadioButton("관리자", false);
        ra2.setBounds(640, 100, 100, 50);
        ra2.setBackground(Color.white);
   
        login_opt_group.add(ra1);
        login_opt_group.add(ra2);
        add(ra1);
        add(ra2);
        
        tf_login_id = new JTextField();
        tf_login_id.setBounds(490, 200, 300, 70);
        add(tf_login_id);
        pf_login_pw = new JPasswordField();
        pf_login_pw.setBounds(490, 300, 300, 70);
        add(pf_login_pw);
        
        JButton btn_login = new JButton("로그인");
        btn_login.setBounds(600, 420, 100, 100);
        add(btn_login);
        
        btn_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UserLogin();				
			}
		});
	}

	public static JTextField getTf_login_id() {
		return tf_login_id;
	}

	public static JPasswordField getPf_login_pw() {
		return pf_login_pw;
	}
}