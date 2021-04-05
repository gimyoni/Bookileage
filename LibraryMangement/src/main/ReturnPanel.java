package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javafx.scene.control.Button;
import user.UserDAO;
import user.UserProc;

class ReturnPanel extends JPanel {
	
	String[] columns = {
			"책 번호", "제목", "id", "출판사", "저자", "장르", "대출일", "반납일", "대출 가능 여부"
	};
	private DefaultTableModel return_model = new DefaultTableModel(columns, 0){ 
		public boolean isCellEditable(int i, int c){ 
			return false; 
		} 
	};
	JTable return_table;
	
	private String dbId;
		
	public ReturnPanel(Library library, String id){
		setBounds(0, 0, 1280, 720); // 위치와 크기 지정
		setLayout(null);
		setVisible(true);
		setBackground(Color.WHITE);
		
		dbId = id;
		
		JLabel title = new JLabel("반납가능 목록");
		title.setBounds(540, 40, 200, 50);
		title.setFont(new Font("monospaced", Font.BOLD, 28));
		add(title);
		
		return_select();
		JTable return_table = new JTable(return_model);
		return_table.setModel(return_model);
		JScrollPane return_scrollpane = new JScrollPane(return_table);
		return_scrollpane.setBounds(250, 150, 780, 500);
		return_scrollpane.setOpaque(false);
		add(return_scrollpane);
		return_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
				int row = return_table.getSelectedRow();
				
				int book_number = Integer.parseInt((String) return_table.getValueAt(row, 0));
				String book_name = (String) return_table.getValueAt(row, 1);
				String id = (String) return_table.getValueAt(row, 2);
				String book_publisher = (String) return_table.getValueAt(row, 3);
				String book_author = (String) return_table.getValueAt(row, 4);
				String book_genre = (String) return_table.getValueAt(row, 5);
				String borrow_date = (String) return_table.getValueAt(row, 6);
				String return_date = (String) return_table.getValueAt(row, 7);
				String book_check = (String) return_table.getValueAt(row, 8);
				
				ReturnProc rp = new ReturnProc(book_number, book_name, book_publisher, book_author, book_genre, borrow_date, return_date);
			}
		});
	}
	
	public void return_select() {
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
				System.out.println(rs.getString("id") + " " + dbId);
				if(rs.getString("id").equals(dbId)) {
					Object insert_data[] = {
						String.valueOf(rs.getInt("book_number")),
						rs.getString("book_name"),
						rs.getString("id"),
						rs.getString("publisher"),
						rs.getString("author"),
						rs.getString("genre"),
						String.valueOf(rs.getDate("borrow_date")),
						String.valueOf(rs.getDate("return_date")),
						String.valueOf(rs.getInt("book_chk"))
					};
					if(rs.getInt("book_chk") == 0) {
						insert_data[8] = "가능";
					}else if(rs.getInt("book_chk") == 1) {
						insert_data[8] = "불가능";
					}else {
						
					}
					return_model.addRow(insert_data);
				}else {
					
				}
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