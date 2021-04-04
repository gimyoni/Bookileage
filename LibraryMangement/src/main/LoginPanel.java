package main;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


class LoginPanel extends JPanel implements ActionListener, ItemListener{
	
	static JTextField tf_login_id;
	static JPasswordField pf_login_pw;
	static JButton btn_login;
	ButtonGroup login_opt_group;
	JRadioButton ra1, ra2;
	static int chk=0; // 0이면 회원, 1이면 관리자
	
	LoginPanel(Library library){
		setBounds(0, 0, 1280, 720); // 위치와 크기 지정
		setLayout(null);
		setBackground(Color.WHITE);
				
		login_opt_group = new ButtonGroup();
        ra1 = new JRadioButton("회원", true);
        ra1.setBounds(540, 100, 100, 50);
        ra1.setBackground(Color.white);
        
        ra2 = new JRadioButton("관리자", false);
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
        
        btn_login = new JButton("로그인");
        btn_login.setBounds(600, 420, 100, 100);
        add(btn_login);
        
		ra1.addItemListener(this);
		ra2.addItemListener(this);
      
        btn_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btn_login) {
					if(chk==0) {
						new UserLogin(library);
						System.out.println("회원 로그인");
					}else if(chk==1) {
						new AdminLogin(library);
						System.out.println("관리자 로그인");
					}
				}				
			}
			
		});
	
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange()==ItemEvent.SELECTED) {
			if(e.getSource()==ra1) {
				chk=0;
				System.out.println("회원체쿠");
			}
			else if(e.getSource()==ra2) {
				chk=1;
				System.out.println("관리자 체쿠");
			}
		}
	}
	
	
	public static JTextField getTf_login_id() {
		return tf_login_id;
	}

	public static JPasswordField getPf_login_pw() {
		return pf_login_pw;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}