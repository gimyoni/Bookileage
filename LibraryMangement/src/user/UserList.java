package user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import user.UserDAO;

public class UserList extends JFrame implements MouseListener, ActionListener {

	Vector v;
	Vector cols;
	DefaultTableModel model;
	JTable jTable;
	JScrollPane pane;
	JPanel pbtn;
	JButton btnInsert;

	public UserList() {
		super("유저 목록");
		// v=getMemberList();
		// MemberDAO
		UserDAO dao = new UserDAO();
		v = dao.getUserList();
		System.out.println("v=" + v);
		cols = getColumn();

		model = new DefaultTableModel(v, cols);

		// jTable = new JTable(v,cols);
		jTable = new JTable(model);
		jTable.setRowHeight(30);
		jTable.setFont(new Font("인터파크고딕 L", Font.PLAIN, 18));
		pane = new JScrollPane(jTable);
		jTable.setBackground(new Color(255, 199, 194));
		jTable.setOpaque(false);
		pane.setOpaque(false);
		pane.getViewport().setBackground(new Color(255, 234, 232));
		add(pane);
		
		pbtn = new JPanel();
		pbtn.setBackground(new Color(255, 211, 207));
		btnInsert = new JButton("지점장 추가");
		btnInsert.setBorderPainted(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setFocusPainted(false);
		btnInsert.setOpaque(false);
		btnInsert.setFont(new Font("인터파크고딕 M", Font.PLAIN, 24));
		pbtn.add(btnInsert);
		add(pbtn, BorderLayout.SOUTH);

		jTable.addMouseListener(this); // 리스너 등록
		btnInsert.addActionListener(this); // 회원가입버튼 리스너 등록

		setBounds(300, 200, 1262, 655);
		setVisible(true);
		
		// 프레임 위 앱 아이콘
//		ImageIcon icon = new ImageIcon("images/logo_2.png");
//		setIconImage(icon.getImage());
	}// end 생성자

	// JTable의 컬럼
	public Vector getColumn() {
		Vector col = new Vector();
		col.add("아이디");
		col.add("이름");
		col.add("비밀번호");
		col.add("대출가능책수");
		col.add("반납해야하는 책수");
		col.add("연체된 책수");
		col.add("최대 책 수");
		col.add("이메일");

		return col;
	}// getColumn

	// Jtable 내용 갱신 메서드
	public void jTableRefresh() {
		UserDAO dao = new UserDAO();
		DefaultTableModel model = new DefaultTableModel(dao.getUserList(), getColumn());
		jTable.setModel(model);
	}

	public static void main(String[] args) {
		new UserList();
	}// main

	@Override
	public void mouseClicked(MouseEvent e) {
		// mouseClicked 만 사용
		int r = jTable.getSelectedRow();
		String id = (String) jTable.getValueAt(r, 1);
		// System.out.println("id="+id);
		UserProc mem = new UserProc(id, this); // 아이디를 인자로 수정창 생성

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 버튼을 클릭하면
		if (e.getSource() == btnInsert) {
			new UserProc(this);
		}
	}
}