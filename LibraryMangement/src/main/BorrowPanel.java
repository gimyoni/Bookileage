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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javafx.scene.control.Button;
import user.UserDAO;
import user.UserProc;

class BorrowPanel extends JPanel {
	
	String[] columns = {
			"책 번호", "제목", "출판사", "저자", "장르", "대출 가능 여부"
	};
	private DefaultTableModel search_model = new DefaultTableModel(columns, 0){ 
		public boolean isCellEditable(int i, int c){ 
			return false; 
		} 
	};
	JTable search_table;
	
	
	public BorrowPanel(Library library){
		setBounds(0, 0, 1280, 720); // 위치와 크기 지정
		setLayout(null);
		setVisible(true);
		setBackground(Color.WHITE);
		
		JLabel title = new JLabel("도서 검색", JLabel.CENTER);
		title.setBounds(540, 40, 200, 50);
		title.setFont(new Font("monospaced", Font.BOLD, 28));
		add(title);
		
		String[] options = {"책 번호", "제목", "저자", "출판사", "장르"};
		JComboBox<String> choose_opt = new JComboBox<String>(options);
		choose_opt.setBounds(250, 120, 120, 45);
		choose_opt.setFont(new Font("monospaced", Font.BOLD, 22));
		add(choose_opt);
		
		JTextField search_tf = new JTextField();
		search_tf.setBounds(370, 120, 380, 45);
		search_tf.setFont(new Font("monospaced", Font.BOLD, 22));
		add(search_tf);
		
		JButton search_btn = new JButton("검색");
		search_btn.setBounds(750, 120, 200, 45);
		search_btn.setFont(new Font("monospaced", Font.BOLD, 22));
		add(search_btn);
		search_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(search_tf.getText() == "" || search_tf.getText() == null) {
					JOptionPane.showMessageDialog(null, "검색어를 입력해주세요", "검색어 없음", JOptionPane.ERROR_MESSAGE);
				}else {
					System.out.println(choose_opt.getSelectedItem().toString());
					System.out.println(search_tf.getText());
					search_books(choose_opt.getSelectedIndex(), search_tf.getText());
				}
			}
		});
		
		search_table = new JTable(search_model);
		search_table.setModel(search_model);
		search_table.getTableHeader().setFont(new Font(null, Font.PLAIN, 16));
		search_table.setFont(new Font(null, Font.BOLD, 14));
		search_table.setRowHeight(30);
		JScrollPane search_scrollpane = new JScrollPane(search_table);
		search_scrollpane.setBounds(250, 200, 780, 450);
		search_scrollpane.setOpaque(false);
		add(search_scrollpane);
		search_table.getTableHeader().setReorderingAllowed(false);
		search_table.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
		search_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				System.out.println("table 클릭!!");
				
				int row = search_table.getSelectedRow();
				
				int book_number = Integer.parseInt((String) search_table.getValueAt(row, 0));
				String book_name = (String) search_table.getValueAt(row, 1);
				String book_publisher = (String) search_table.getValueAt(row, 2);
				String book_author = (String) search_table.getValueAt(row, 3);
				String book_genre = (String) search_table.getValueAt(row, 4);
				String book_check = (String) search_table.getValueAt(row, 5);

				BorrowProc bp = new BorrowProc(book_number, book_name, book_publisher, book_author, book_genre, book_check); // 아이디를 인자로 수정창 생성
			}
		});
	}
	
	public void search_books(int option, String search_keyword) {
		PreparedStatement pstmt = null;
		Connection con = null;
		String[] db_opt_name = {"book_number", "book_name", "publisher", "author", "genre"};
		String query = "select * from book where " + db_opt_name[option] + "=" + search_keyword;
		ResultSet rs = null;
		try {
			UserDAO dao = new UserDAO();
			con = dao.getConn();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Object insert_data[] = {
					String.valueOf(rs.getInt("book_number")),
					rs.getString("book_name"),
					rs.getString("publisher"),
					rs.getString("author"),
					rs.getString("genre"),
					String.valueOf(rs.getInt("book_chk"))
				};
				if(rs.getInt("book_chk") == 0) {
					insert_data[5] = "가능";
				}else if(rs.getInt("book_chk") == 1) {
					insert_data[5] = "불가능";
				}else {
					
				}
				search_model.addRow(insert_data);
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