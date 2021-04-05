package main;

import javax.swing.JFrame;

import admin.AdminPagePanel;

import java.awt.Toolkit;

public class Library extends JFrame{
	private int width = 1280, height = 750;
	
	//새로 추가한 것들 210405
	public BorrowPanel borrowPanel = new BorrowPanel(this);
	public ReturnPanel returnPanel;
	//새로 추가한 것들 210405
	public LoginPanel loginPanel = new LoginPanel(this);
	public AdminPagePanel adminPagePanel = new AdminPagePanel(this);
	public MyPagePanel myPagePanel;
	
	public Library() {
		
		super("도서관 어쩌구");
		setSize(width, height);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane();
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		kit.getImage("images/icon/book_icon.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon/book_icon.png"));

		//새로 추가한 것들 210405
		add(borrowPanel);
		borrowPanel.setVisible(false);
		
//		add(returnPanel);
//		returnPanel.setVisible(false);
		//새로 추가한 것들 210405
		
		add(loginPanel);
		loginPanel.setVisible(true);
		
		add(adminPagePanel);
		adminPagePanel.setVisible(false);
		
//		add(myPagePanel);
//		myPagePanel.setVisible(false);
		
	}
}