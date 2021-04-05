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

class ReturnProc extends JFrame{
	
	ReturnProc(int book_number, String book_name, String book_publisher, String book_author, String book_genre, String borrow_date, String return_date){
		super("반납 상세정보");
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
		p.setBounds(0, 0, 450, 500);
		p.setLayout(null);
		add(p);
		
		JLabel book_name_t = new JLabel("제목 : ");
		book_name_t.setBounds(30, 30, 120, 50);
		book_name_t.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_name_t.setBackground(Color.pink);
		p.add(book_name_t);
		
		JLabel book_name_lb = new JLabel("책 제목부분");
		book_name_lb.setBounds(150, 30, 240, 50);
		book_name_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_name_lb.setBackground(Color.pink);
		p.add(book_name_lb);
		
		JLabel book_num_t = new JLabel("책 아이디 : ");
		book_num_t.setBounds(30, 80, 120, 50);
		book_num_t.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_num_t.setBackground(Color.pink);
		p.add(book_num_t);
				
		JLabel book_num_lb = new JLabel("책 아이디 부분");
		book_num_lb.setBounds(150, 80, 240, 50);
		book_num_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_num_lb.setBackground(Color.pink);
		p.add(book_num_lb);
		
		JLabel book_publisher_t = new JLabel("출판사 : ");
		book_publisher_t.setBounds(30, 130, 120, 50);
		book_publisher_t.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_publisher_t.setBackground(Color.pink);
		p.add(book_publisher_t);
		
		JLabel book_publisher_lb = new JLabel("책의 출판사 부분");
		book_publisher_lb.setBounds(150, 130, 240, 50);
		book_publisher_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_publisher_lb.setBackground(Color.pink);
		p.add(book_publisher_lb);
		
		JLabel book_author_t = new JLabel("저자 : ");
		book_author_t.setBounds(30, 180, 120, 50);
		book_author_t.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_author_t.setBackground(Color.pink);
		p.add(book_author_t);
		
		JLabel book_author_lb = new JLabel("책의 저자 부분");
		book_author_lb.setBounds(150, 180, 240, 50);
		book_author_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_author_lb.setBackground(Color.pink);
		p.add(book_author_lb);
		
		JLabel book_genre_t = new JLabel("장르 : ");
		book_genre_t.setBounds(30, 230, 120, 50);
		book_genre_t.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_genre_t.setBackground(Color.pink);
		p.add(book_genre_t);
		
		JLabel book_genre_lb = new JLabel("책의 장르 부분");
		book_genre_lb.setBounds(150, 230, 240, 50);
		book_genre_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		book_genre_lb.setBackground(Color.pink);
		p.add(book_genre_lb);
		
		JLabel borrow_date_t = new JLabel("대출 날짜 : ");
		borrow_date_t.setBounds(30, 280, 120, 50);
		borrow_date_t.setFont(new Font("monospaced", Font.PLAIN, 14));
		borrow_date_t.setBackground(Color.pink);
		p.add(borrow_date_t);
		
		JLabel borrow_date_lb = new JLabel("0000-00-00");
		borrow_date_lb.setBounds(150, 280, 240, 50);
		borrow_date_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		borrow_date_lb.setBackground(Color.pink);
		p.add(borrow_date_lb);
		
		JLabel return_date_t = new JLabel("반납 기한 : ");
		return_date_t.setBounds(30, 330, 120, 50);
		return_date_t.setFont(new Font("monospaced", Font.PLAIN, 14));
		return_date_t.setBackground(Color.pink);
		p.add(return_date_t);
		
		JLabel return_date_lb = new JLabel("1111-11-11");
		return_date_lb.setBounds(150, 330, 240, 50);
		return_date_lb.setFont(new Font("monospaced", Font.PLAIN, 20));
		return_date_lb.setBackground(Color.pink);
		p.add(return_date_lb);
		
		JButton borrow_btn = new JButton("반납");
		borrow_btn.setBounds(70, 400, 120, 40);
		p.add(borrow_btn);
		borrow_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag = JOptionPane.showConfirmDialog(null, book_name + "을(를) 반납하시겠습니까?", "반납 확인", JOptionPane.YES_NO_OPTION);
				if(flag == JOptionPane.YES_OPTION) {
					boolean borrow_confirm = return_book(MyPagePanel.id_label.getText(), book_num_lb.getText());
					if(borrow_confirm == true) {
						JOptionPane.showMessageDialog(null, MyPagePanel.id_label.getText() + "님, " + book_name + "이 반납되었습니다.", "반납완료", JOptionPane.PLAIN_MESSAGE);
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, MyPagePanel.id_label.getText() + "님, " + book_name + "이 반납되지 않았습니다.", "반납실패", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					// 반납하지 않겠다는 경우
				}
			}
		});
		
		JButton exit_btn = new JButton("취소");
		exit_btn.setBounds(210, 400, 120, 40);
		p.add(exit_btn);
		exit_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag = JOptionPane.showConfirmDialog(null, "반납을 취소하시겠습니까?", "반납 취소", JOptionPane.YES_NO_OPTION);
				if(flag == JOptionPane.YES_OPTION) {
					dispose();
				}else {
					// 반납을 취소하겠다냐는 물음에 no라 답한 경우
				}
			}
		});
		
		book_name_lb.setText(book_name);
		book_num_lb.setText(String.valueOf(book_number));
		book_publisher_lb.setText(book_publisher);
		book_author_lb.setText(book_author);
		book_genre_lb.setText(book_genre);
		borrow_date_lb.setText(String.valueOf(borrow_date));
		return_date_lb.setText(String.valueOf(return_date));
	}
	public boolean return_book(String id, String book_number) {
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
            
            if(book_chk == 1) {
            	Calendar cal = new GregorianCalendar();
            	Date date = new Date(cal.getTimeInMillis());
            	
            	PreparedStatement pst1 = conn.prepareStatement(""
            			+ "update book set id=?, borrow_date=?, return_date=?, book_chk=? "
            			+ "where book_number=?");
                pst1.setString(1, "library");
                pst1.setDate(2, date);
                pst1.setDate(3, date);
                pst1.setInt(4, 0);
                pst1.setInt(5, Integer.parseInt(book_number));
                int numRows = pst1.executeUpdate(); //insert, delete, 
                System.out.format("%d개 의 책이 반납되었습니다.", numRows);
                pst1.close();
            }else {
            	// check가 0일때
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