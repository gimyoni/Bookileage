package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import book.BookDAO;
import user.UserDAO;

public class MyPageGUI {
	public MyPageGUI() {
	}
	public static void main(String[] args) {
		MyPageGUI my_page_gui = new MyPageGUI();
	}
}

class MyPagePanel extends JPanel{
	
	String[] columns = {
			"책 번호", "제목", "id", "출판사", "저자", "대출 가능 여부", "빌린 날짜", "장르"
	};
	private DefaultTableModel borrow_model = new DefaultTableModel(columns, 0);
	private DefaultTableModel overdue_model;
	
	String[] str = {"책 번호", "제목", "id", "출판사", "저자", "대출 가능 여부", "빌린 날짜", "장르"};
	
	JLabel id_label;
	
	MyPagePanel(){
		setBounds(0, 0, 1280, 720); // 위치와 크기 지정
		setLayout(null);
		setVisible(true);
		setBackground(Color.WHITE);
		
		JLabel info_label = new JLabel("회원정보", JLabel.CENTER);
		info_label.setBounds(50, 50, 300, 50);
		info_label.setFont(new Font("monospaced", Font.BOLD, 24));
		info_label.setOpaque(true);
		info_label.setBackground(Color.GREEN);
		add(info_label);
		
		JLabel name_label = new JLabel("얌얌얌");
		name_label.setBounds(50, 120, 300, 50);
		name_label.setFont(new Font("monospaced", Font.PLAIN, 24));
		name_label.setOpaque(true);
		name_label.setBackground(Color.GREEN);
		add(name_label);
		
		id_label = new JLabel("아이디입니당");
		id_label.setBounds(50, 170, 300, 50);
		id_label.setFont(new Font("monospaced", Font.PLAIN, 24));
		id_label.setOpaque(true);
		id_label.setBackground(Color.GREEN);
		add(id_label);
		
		JLabel borrow_cnt_label = new JLabel("대출 가능 권수 : " + "8권");
		borrow_cnt_label.setBounds(50, 230, 300, 50);
		borrow_cnt_label.setFont(new Font("monospaced", Font.PLAIN, 24));
		borrow_cnt_label.setOpaque(true);
		borrow_cnt_label.setBackground(Color.GREEN);
		add(borrow_cnt_label);
		
		JLabel return_cnt_label = new JLabel("반납 가능 권수 : " + "1권");
		return_cnt_label.setBounds(50, 280, 300, 50);
		return_cnt_label.setFont(new Font("monospaced", Font.PLAIN, 24));
		return_cnt_label.setOpaque(true);
		return_cnt_label.setBackground(Color.GREEN);
		add(return_cnt_label);
		
		JLabel overdue_cnt_label = new JLabel("연체된 책 권수 : " + "3권");
		overdue_cnt_label.setBounds(50, 330, 300, 50);
		overdue_cnt_label.setFont(new Font("monospaced", Font.PLAIN, 24));
		overdue_cnt_label.setOpaque(true);
		overdue_cnt_label.setBackground(Color.GREEN);
		add(overdue_cnt_label);
		
		JLabel email_label = new JLabel("purun030515@gmail.com");
		email_label.setBounds(50, 380, 300, 50);
		email_label.setFont(new Font("monospaced", Font.PLAIN, 24));
		email_label.setOpaque(true);
		email_label.setBackground(Color.GREEN);
		add(email_label);
		
		JButton change_pw = new JButton("비밀번호 변경");
		change_pw.setBounds(50, 440, 220, 40);
		change_pw.setFont(new Font("monospaced", Font.PLAIN, 20));
		change_pw.setOpaque(true);
		change_pw.setBackground(Color.GREEN);
		add(change_pw);
		
		JButton change_email = new JButton("이메일 주소 변경");
		change_email.setBounds(50, 490, 220, 40);
		change_email.setFont(new Font("monospaced", Font.PLAIN, 20));
		change_email.setOpaque(true);
		change_email.setBackground(new Color(239, 180, 103));
		add(change_email);
		
		JButton borrow_btn = new JButton("대출하기");
		borrow_btn.setBounds(380, 50, 300, 70);
		borrow_btn.setFont(new Font("monospaced", Font.PLAIN, 32));
		borrow_btn.setOpaque(true);
		borrow_btn.setBackground(new Color(239, 180, 103));
		add(borrow_btn);
		
		JButton return_btn = new JButton("반납하기");
		return_btn.setBounds(700, 50, 300, 70);
		return_btn.setFont(new Font("monospaced", Font.PLAIN, 32));
		return_btn.setOpaque(true);
		return_btn.setBackground(new Color(239, 180, 103));
		add(return_btn);
		
		JButton out_btn = new JButton("나가기");
		out_btn.setBounds(1020, 50, 220, 70);
		out_btn.setFont(new Font("monospaced", Font.PLAIN, 32));
		out_btn.setOpaque(true);
		out_btn.setBackground(new Color(239, 180, 103));
		add(out_btn);
		
		JLabel borrow_table_title = new JLabel("대출목록");
		borrow_table_title.setBounds(380, 150, 150, 35);
		borrow_table_title.setFont(new Font("monospaced", Font.PLAIN, 18));
		borrow_table_title.setOpaque(true);
		borrow_table_title.setBackground(Color.GREEN);
		add(borrow_table_title);
		
		String [][] data = {{"11", "11", "11", "11", "11", "11", "11", "11"},
				{"11", "11", "11", "11", "11", "11", "11", "11"},
				{"11", "11", "11", "11", "11", "11", "11", "11"}};
		
		JTable borrow_table = new JTable(borrow_model);
		borrow_table.setModel(borrow_model);
		JScrollPane borrow_scrollpane = new JScrollPane(borrow_table);
		borrow_scrollpane.setBounds(380, 185, 650, 210);
		borrow_scrollpane.setOpaque(false);
		add(borrow_scrollpane);
		select();
		
		JLabel overdue_table_title = new JLabel("연체목록");
		overdue_table_title.setBounds(380, 420, 150, 35);
		overdue_table_title.setFont(new Font("monospaced", Font.PLAIN, 18));
		overdue_table_title.setOpaque(true);
		overdue_table_title.setBackground(Color.GREEN);
		add(overdue_table_title);
		
		DefaultTableModel overdue_model = new DefaultTableModel(data, columns);
		JTable overdue_table = new JTable(overdue_model);
		overdue_table.setModel(overdue_model);
		JScrollPane overdue_scrollpane = new JScrollPane(overdue_table);
		overdue_scrollpane.setBounds(380, 455, 650, 210);
		overdue_scrollpane.setOpaque(false);
		add(overdue_scrollpane);
	}
	public void select() {
		PreparedStatement pstmt = null;
		Connection con = null;
		String query = "select * from book";
		ResultSet rs = null;
		
		try {
			UserDAO dao = new UserDAO();
			con = dao.getConn();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("book_number"));
				System.out.println(rs.getString("book_name"));
				System.out.println(rs.getString("id"));
				System.out.println(rs.getString("publisher"));
				System.out.println(rs.getString("author"));
				System.out.println(rs.getInt("check"));
				System.out.println(rs.getDate("borrow_date"));
				System.out.println(rs.getString("genre"));
				Object insert_data[] = {
					String.valueOf(rs.getInt("book_number")),
					rs.getString("book_name"),
					rs.getString("id"),
					rs.getString("publisher"),
					rs.getString("author"),
					String.valueOf(rs.getInt("check")),
					String.valueOf(rs.getDate("borrow_date")),
					rs.getString("genre")
				};
				borrow_model.addRow(insert_data);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}