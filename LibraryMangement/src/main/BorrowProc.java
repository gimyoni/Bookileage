package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;

import com.mysql.cj.xdevapi.Statement;

import book.BookDAO;
import user.UserDAO;

class BorrowProc extends JFrame{
	
	BorrowProc(int book_number, String book_name, String book_publisher, String book_author, String book_genre, String book_check){
		super("���� ������");
		setSize(400, 500);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		setBackground(Color.white);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		kit.getImage("images/icon/book_icon.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon/book_icon.png"));
		
		JPanel p = new JPanel();
		p.setBounds(0, 0, 400, 500);
		p.setLayout(null);
		add(p);
		
		JLabel book_name_t = new JLabel("���� : ");
		book_name_t.setBounds(30, 30, 120, 50);
		book_name_t.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_name_t.setBackground(Color.pink);
		p.add(book_name_t);
		
		JLabel book_name_lb = new JLabel("å ����κ�");
		book_name_lb.setBounds(150, 30, 240, 50);
		book_name_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_name_lb.setBackground(Color.pink);
		p.add(book_name_lb);
		
		JLabel book_num_t = new JLabel("å ���̵� : ");
		book_num_t.setBounds(30, 80, 120, 50);
		book_num_t.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_num_t.setBackground(Color.pink);
		p.add(book_num_t);
				
		JLabel book_num_lb = new JLabel("å ���̵� �κ�");
		book_num_lb.setBounds(150, 80, 240, 50);
		book_num_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_num_lb.setBackground(Color.pink);
		p.add(book_num_lb);
		
		JLabel book_publisher_t = new JLabel("���ǻ� : ");
		book_publisher_t.setBounds(30, 130, 120, 50);
		book_publisher_t.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_publisher_t.setBackground(Color.pink);
		p.add(book_publisher_t);
		
		JLabel book_publisher_lb = new JLabel("å�� ���ǻ� �κ�");
		book_publisher_lb.setBounds(150, 130, 240, 50);
		book_publisher_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_publisher_lb.setBackground(Color.pink);
		p.add(book_publisher_lb);
		
		JLabel book_author_t = new JLabel("���� : ");
		book_author_t.setBounds(30, 180, 120, 50);
		book_author_t.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_author_t.setBackground(Color.pink);
		p.add(book_author_t);
		
		JLabel book_author_lb = new JLabel("å�� ���� �κ�");
		book_author_lb.setBounds(150, 180, 240, 50);
		book_author_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_author_lb.setBackground(Color.pink);
		p.add(book_author_lb);
		
		JLabel book_genre_t = new JLabel("�帣 : ");
		book_genre_t.setBounds(30, 230, 120, 50);
		book_genre_t.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_genre_t.setBackground(Color.pink);
		p.add(book_genre_t);
		
		JLabel book_genre_lb = new JLabel("å�� �帣 �κ�");
		book_genre_lb.setBounds(150, 230, 240, 50);
		book_genre_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_genre_lb.setBackground(Color.pink);
		p.add(book_genre_lb);
		
		JLabel book_check_t = new JLabel("���� ���� ���� : ");
		book_check_t.setBounds(30, 280, 120, 50);
		book_check_t.setFont(new Font("monospaced", Font.PLAIN, 14));
		book_check_t.setBackground(Color.pink);
		p.add(book_check_t);
		
		JLabel book_check_lb = new JLabel("���� ���� ����");
		book_check_lb.setBounds(150, 280, 240, 50);
		book_check_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_check_lb.setBackground(Color.pink);
		p.add(book_check_lb);
		
		JButton borrow_btn = new JButton("����");
		borrow_btn.setBounds(70, 360, 120, 40);
		p.add(borrow_btn);
		borrow_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean borrow_confirm = borrow(MyPagePanel.id_label.getText(), book_num_lb.getText());
				if(borrow_confirm == true) {
					JOptionPane.showMessageDialog(null, MyPagePanel.id_label.getText() + "��, " + book_name + "�� ����Ǿ����ϴ�.", "����Ϸ�", JOptionPane.PLAIN_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, MyPagePanel.id_label.getText() + "��, " + book_name + "�� ������� �ʾҽ��ϴ�.", "�������", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton exit_btn = new JButton("���");
		exit_btn.setBounds(210, 360, 120, 40);
		p.add(exit_btn);
		exit_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		book_name_lb.setText(book_name);
		book_num_lb.setText(String.valueOf(book_number));
		book_publisher_lb.setText(book_publisher);
		book_author_lb.setText(book_author);
		book_genre_lb.setText(book_genre);
		book_check_lb.setText(book_check);
	}
	public boolean borrow(String id, String book_number) {
		Connection conn = null;
        Statement stmt = null;
        int result = 0;
        ResultSet rs;
        int book_chk = -1;
        PreparedStatement pst2;

        try {
        	BookDAO dao = new BookDAO();
            conn = dao.getConn();
            
            String query = "select * from book where book_number=";
            pst2 = conn.prepareStatement(query + book_number);
            rs = pst2.executeQuery();
                        
            if(rs.next()) {
            	book_chk = rs.getInt("book_chk");
            }
            
            if(book_chk == 0) {
            	Calendar cal = new GregorianCalendar();
            	Date borrow_date = new Date(cal.getTimeInMillis());
            	
            	PreparedStatement pst1 = conn.prepareStatement(""
            			+ "update book set id=?, borrow_date=?, return_date=?, book_chk=? "
            			+ "where book_number=?");
                pst1.setString(1, id);
                pst1.setDate(2, borrow_date);
                cal.add(Calendar.DATE, 7);
                Date return_date = new Date(cal.getTimeInMillis());
                pst1.setDate(3, return_date);
                pst1.setInt(4, 1);
                pst1.setInt(5, Integer.parseInt(book_number));
                int numRows = pst1.executeUpdate(); //insert, delete, 
                System.out.format("%d�� �� å�� ����Ǿ����ϴ�.", numRows);
                pst1.close();
            }else {
            	// check�� 1�϶�
            }
            pst2.close();
            conn.close();
			
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
		return true;
	}
}