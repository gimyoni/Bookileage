package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.xdevapi.Statement;

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
			"책 번호", "제목", "id", "출판사", "저자", "장르", "대출일", "반납일", "대출 가능 여부"
	};
	private DefaultTableModel borrow_model = new DefaultTableModel(columns, 0);
	private DefaultTableModel overdue_model = new DefaultTableModel(columns, 0);
	
	JLabel name_label;	
	public static JLabel id_label;
	JLabel borrow_cnt_label;
	JLabel return_cnt_label;
	JLabel overdue_cnt_label;
	JLabel email_label;
	
	private String dbPw;
	public static String dbId;
	
	MyPagePanel(Library library, String id){
		setBounds(0, 0, 1280, 720); // 위치와 크기 지정
		setLayout(null);
		setVisible(true);
		setBackground(Color.WHITE);
		
		dbId = id;
		
		JLabel info_label = new JLabel("회원정보", JLabel.CENTER);
		info_label.setBounds(50, 50, 300, 50);
		info_label.setFont(new Font("monospaced", Font.BOLD, 24));
		info_label.setOpaque(true);
		info_label.setBackground(Color.GREEN);
		add(info_label);
		
		name_label = new JLabel("얌얌얌");
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
		
		borrow_cnt_label = new JLabel("대출 가능 권수 : " + "8권");
		borrow_cnt_label.setBounds(50, 230, 300, 50);
		borrow_cnt_label.setFont(new Font("monospaced", Font.PLAIN, 24));
		borrow_cnt_label.setOpaque(true);
		borrow_cnt_label.setBackground(Color.GREEN);
		add(borrow_cnt_label);
		
		return_cnt_label = new JLabel("반납 가능 권수 : " + "1권");
		return_cnt_label.setBounds(50, 280, 300, 50);
		return_cnt_label.setFont(new Font("monospaced", Font.PLAIN, 24));
		return_cnt_label.setOpaque(true);
		return_cnt_label.setBackground(Color.GREEN);
		add(return_cnt_label);
		
		overdue_cnt_label = new JLabel("연체된 책 권수 : " + "3권");
		overdue_cnt_label.setBounds(50, 330, 300, 50);
		overdue_cnt_label.setFont(new Font("monospaced", Font.PLAIN, 24));
		overdue_cnt_label.setOpaque(true);
		overdue_cnt_label.setBackground(Color.GREEN);
		add(overdue_cnt_label);
		
		email_label = new JLabel("purun030515@gmail.com");
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
		change_pw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String lastPw = JOptionPane.showInputDialog(null, "현재 비밀번호를 입력해주세요", "비밀번호 변경", JOptionPane.QUESTION_MESSAGE);
                if(lastPw != null) {
                	if(lastPw.equals(dbPw)){
                		String newPw = JOptionPane.showInputDialog(null, "새로운 비밀번호를 입력해주세요", "비밀번호 변경", JOptionPane.QUESTION_MESSAGE);
                        if(newPw != null) {
                            String checkNewPw = JOptionPane.showInputDialog(null, "다시 한 번 새로운 비밀번호를 입력해주세요", "비밀번호 변경", JOptionPane.QUESTION_MESSAGE);
                            if(newPw.equals(checkNewPw)) {
                            	boolean flag = changePw(newPw);
                            	if(flag == true) {
                            		JOptionPane.showMessageDialog(null, "비밀번호가 변경되었습니다", "비밀번호 변경", JOptionPane.PLAIN_MESSAGE);
                            	}else {
                            		JOptionPane.showMessageDialog(null, "비밀번호를 변경하지 못했습니다", "비밀번호 변경", JOptionPane.ERROR_MESSAGE);
                            	}
                            }else {
                        		JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다", "비밀번호 변경", JOptionPane.ERROR_MESSAGE);
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "새로운 비밀번호가 입력되지 않았습니다", "비밀번호 변경", JOptionPane.ERROR_MESSAGE);
                        }
                	}else {
                		JOptionPane.showMessageDialog(null, "현재 비밀번호가 틀렸습니다", "비밀번호 변경", JOptionPane.ERROR_MESSAGE);
                	}
                }else if(lastPw == null){
                    JOptionPane.showMessageDialog(null, "현재 비밀번호가 입력되지 않았습니다", "비밀번호 변경", JOptionPane.ERROR_MESSAGE);
                }else {
                	
                }
			}
		});
		
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
		borrow_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				library.myPagePanel.setVisible(false);
				library.borrowPanel.setVisible(true);
			}
		});
		
		JButton return_btn = new JButton("반납하기");
		return_btn.setBounds(700, 50, 300, 70);
		return_btn.setFont(new Font("monospaced", Font.PLAIN, 32));
		return_btn.setOpaque(true);
		return_btn.setBackground(new Color(239, 180, 103));
		add(return_btn);
		return_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				library.myPagePanel.setVisible(false);
				library.returnPanel.setVisible(true);
			}
		});
		
		JButton out_btn = new JButton("로그아웃");
		out_btn.setBounds(1020, 50, 220, 70);
		out_btn.setFont(new Font("monospaced", Font.PLAIN, 32));
		out_btn.setOpaque(true);
		out_btn.setBackground(new Color(239, 180, 103));
		add(out_btn);
		out_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				library.myPagePanel.setVisible(false);
				library.loginPanel.setVisible(true);
			}
		});
		
		JLabel borrow_table_title = new JLabel("대출목록");
		borrow_table_title.setBounds(380, 150, 150, 35);
		borrow_table_title.setFont(new Font("monospaced", Font.PLAIN, 18));
		borrow_table_title.setOpaque(true);
		borrow_table_title.setBackground(Color.GREEN);
		add(borrow_table_title);
		
		JTable borrow_table = new JTable(borrow_model);
		borrow_table.setModel(borrow_model);
		JScrollPane borrow_scrollpane = new JScrollPane(borrow_table);
		borrow_scrollpane.setBounds(380, 185, 650, 210);
		borrow_scrollpane.setOpaque(false);
		add(borrow_scrollpane);
		borrow_select();
		
		JLabel overdue_table_title = new JLabel("연체목록");
		overdue_table_title.setBounds(380, 420, 150, 35);
		overdue_table_title.setFont(new Font("monospaced", Font.PLAIN, 18));
		overdue_table_title.setOpaque(true);
		overdue_table_title.setBackground(Color.GREEN);
		add(overdue_table_title);
		overdue_select();
		
		JTable overdue_table = new JTable(overdue_model);
		overdue_table.setModel(overdue_model);
		JScrollPane overdue_scrollpane = new JScrollPane(overdue_table);
		overdue_scrollpane.setBounds(380, 455, 650, 210);
		overdue_scrollpane.setOpaque(false);
		add(overdue_scrollpane);
	}
	public void borrow_select() {
		PreparedStatement pstmt = null;
		Connection con = null;
		String query = "select * from book where (date(now()) - return_date) <= 14";
		ResultSet rs = null;
		
		try {
			UserDAO dao = new UserDAO();
			con = dao.getConn();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println("b : " + rs.getString("id") + " " + dbId);
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
					borrow_model.addRow(insert_data);
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
	public void overdue_select() {
		PreparedStatement pstmt = null;
		Connection con = null;
		String query = "select * from book where (date(now()) - return_date) > 14";
		ResultSet rs = null;
		try {
			UserDAO dao = new UserDAO();
			con = dao.getConn();
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println("o : " + rs.getString("id") + " " + dbId);
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
					overdue_model.addRow(insert_data);
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
	public String getDbPw() {
		return dbPw;
	}
	public void setDbPw(String dbPw) {
		this.dbPw = dbPw;
	}
	public boolean changePw(String newPw) {
		Connection conn = null;
        Statement stmt = null;
        int result = 0;

        try {
        	UserDAO dao = new UserDAO();
            conn = dao.getConn();
            
            PreparedStatement pst=conn.prepareStatement("update user set password=? where id=?");

            pst.setString(1, newPw);
            pst.setString(2, this.id_label.getText());

            int numRows = pst.executeUpdate(); //insert, delete, 

            System.out.format("%d개의 행이 바꼈습니다.", numRows);

            pst.close();
            conn.close();
			
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
		return true;
	}
	public String getDbId() {
		return dbId;
	}
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}
}