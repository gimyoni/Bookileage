package user;

import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;

import user.UserDAO;
import user.UserDTO;

public class UserProc extends JFrame implements ActionListener {

	JPanel p;
	JTextField tf_id, tf_name, tf_borrow_cnt, tf_return_cnt, tf_overdue_cnt, tf_maximum, tf_email;
	JPasswordField pf_password; // 비밀번호
	JButton btnInsert, btnCancel, btnUpdate, btnDelete; // 가입, 취소, 수정 , 탈퇴 버튼

	GridBagLayout gb;
	GridBagConstraints gbc;
	UserList mList;

	public UserProc() { // 가입용 생성자
		createUI(); // UI작성해주는 메소드
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
		// 프레임 위 앱 아이콘
		ImageIcon icon = new ImageIcon("images/logo_2.png");
		setIconImage(icon.getImage());
	}// 생성자

	public UserProc(UserList mList) { // 가입용 생성자
		createUI(); // UI작성해주는 메소드
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
		this.mList = mList;

		// 프레임 위 앱 아이콘
		ImageIcon icon = new ImageIcon("images/logo_2.png");
		setIconImage(icon.getImage());
	}// 생성자

	public UserProc(String id, UserList mList) { // 수정/삭제용 생성자
		createUI();
		btnInsert.setEnabled(false);
		btnInsert.setVisible(false);
		this.mList = mList;

		System.out.println("id=" + id);

		UserDAO dao = new UserDAO();
		UserDTO vMem = dao.getUserDTO(id);
		viewData(vMem);

		// 프레임 위 앱 아이콘
		ImageIcon icon = new ImageIcon("images/logo_2.png");
		setIconImage(icon.getImage());

	}// id를 가지고 생성

	// MemberDTO 의 회원 정보를 가지고 화면에 셋팅해주는 메소드
	private void viewData(UserDTO vMem) {

		String id = vMem.getId();
		String name = vMem.getName();
		String password = vMem.getPassword();
		int borrow_cnt = vMem.getBorrow_cnt();
		int return_cnt = vMem.getReturn_cnt();
		int overdue_cnt = vMem.getOverdue_cnt();
		int maximum = vMem.getMaximum();
		String email = vMem.getEmail();
		System.out.print(id + name + password + borrow_cnt + return_cnt + overdue_cnt + maximum + email);

		// 화면에 세팅
		tf_id.setText(id);
		tf_name.setText(name);
		tf_id.setEditable(false); // 편집 안되게
		pf_password.setText(password); // 비밀번호는 안보여준다.
		tf_borrow_cnt.setText(String.valueOf(borrow_cnt));
		tf_return_cnt.setText(String.valueOf(return_cnt));
		tf_maximum.setText(String.valueOf(maximum));
		tf_email.setText(email);
	}// viewData

	private void createUI() {
		this.setTitle("회원정보");
		gb = new GridBagLayout();
		setLayout(gb);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		// 아이디
		JLabel id = new JLabel("아이디 ");
		id.setFont(new Font("인터파크고딕 M", Font.PLAIN, 18));
		tf_id = new JTextField(20);
		tf_id.setFont(new Font("인터파크고딕 L", Font.PLAIN, 18));
		gbAdd(id, 0, 0, 1, 1);
		gbAdd(tf_id, 1, 0, 3, 1);

		// 이름
		JLabel name = new JLabel("이름 : ");
		name.setFont(new Font("인터파크고딕 M", Font.PLAIN, 18));
		tf_name = new JTextField(20);
		tf_name.setFont(new Font("인터파크고딕 L", Font.PLAIN, 18));
		// 그리드백에 붙이기
		gbAdd(name, 0, 1, 1, 1);
		gbAdd(tf_name, 1, 1, 3, 1);

		// 비밀번호
		JLabel password = new JLabel("비밀번호 : ");
		password.setFont(new Font("인터파크고딕 M", Font.PLAIN, 18));
		pf_password = new JPasswordField(20);
//		pfPwd.setFont(new Font("인터파크고딕 L", Font.PLAIN, 18));
		gbAdd(password, 0, 2, 1, 1);
		gbAdd(pf_password, 1, 2, 3, 1);

		// borrow_cnt
		JLabel borrow_cnt = new JLabel("borrow_cnt :");
		borrow_cnt.setFont(new Font("인터파크고딕 M", Font.PLAIN, 18));
		tf_borrow_cnt = new JTextField(20);
		tf_borrow_cnt.setFont(new Font("인터파크고딕 L", Font.PLAIN, 18));
		gbAdd(borrow_cnt, 0, 4, 1, 1);
		gbAdd(tf_borrow_cnt, 1, 4, 3, 1);

		// return_cnt
		JLabel return_cnt = new JLabel("return_cnt : ");
		return_cnt.setFont(new Font("인터파크고딕 M", Font.PLAIN, 18));
		tf_return_cnt = new JTextField(20);
		tf_return_cnt.setFont(new Font("인터파크고딕 L", Font.PLAIN, 18));
		// 그리드백에 붙이기
		gbAdd(return_cnt, 0, 5, 1, 1);
		gbAdd(tf_return_cnt, 1, 5, 3, 1);

		// overdue_cnt
		JLabel overdue_cnt = new JLabel("overdue_cnt : ");
		overdue_cnt.setFont(new Font("인터파크고딕 M", Font.PLAIN, 18));
		tf_overdue_cnt = new JTextField(5);
		tf_overdue_cnt.setFont(new Font("인터파크고딕 L", Font.PLAIN, 18));
		gbAdd(overdue_cnt, 0, 6, 1, 1);
		gbAdd(tf_overdue_cnt, 1, 6, 3, 1);
		
		// maximum
		JLabel maximum = new JLabel("maximum : ");
		maximum.setFont(new Font("인터파크고딕 M", Font.PLAIN, 18));
		tf_maximum = new JTextField(5);
		tf_maximum.setFont(new Font("인터파크고딕 L", Font.PLAIN, 18));
		gbAdd(maximum, 0, 7, 1, 1);
		gbAdd(tf_maximum, 1, 7, 3, 1);
		
		// email
		JLabel email = new JLabel("email : ");
		maximum.setFont(new Font("인터파크고딕 M", Font.PLAIN, 18));
		tf_email = new JTextField(5);
		tf_email.setFont(new Font("인터파크고딕 L", Font.PLAIN, 18));
		gbAdd(email, 0, 8, 1, 1);
		gbAdd(tf_email, 1, 8, 3, 1);

		// 버튼
		JPanel pButton = new JPanel();
//		pButton.setBackground(new Color(255, 199, 194));
		btnInsert = new JButton("가입");
		btnInsert.setFont(new Font("인터파크고딕 M", Font.PLAIN, 22));
		btnUpdate = new JButton("수정");
		btnUpdate.setFont(new Font("인터파크고딕 M", Font.PLAIN, 22));
		btnDelete = new JButton("탈퇴");
		btnDelete.setFont(new Font("인터파크고딕 M", Font.PLAIN, 22));
		btnCancel = new JButton("취소");
		btnCancel.setFont(new Font("인터파크고딕 M", Font.PLAIN, 22));
		pButton.add(btnInsert);
		pButton.add(btnUpdate);
		pButton.add(btnDelete);
		pButton.add(btnCancel);
		gbAdd(pButton, 0, 10, 5, 1);
		
		btnInsert.setBorderPainted(false);
		btnInsert.setContentAreaFilled(false);
		btnInsert.setFocusPainted(false);
		btnInsert.setOpaque(false);
		
		btnUpdate.setBorderPainted(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setFocusPainted(false);
		btnUpdate.setOpaque(false);
		
		btnDelete.setBorderPainted(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setFocusPainted(false);
		btnCancel.setOpaque(false);
		
		btnCancel.setBorderPainted(false);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setFocusPainted(false);
		btnCancel.setOpaque(false);

		// 버튼에 감지기를 붙이자
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDelete.addActionListener(this);

		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //System.exit(0) //프로그램종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // dispose(); //현재창만 닫는다.
		setBounds(681, 275, 500, 500);

	}// createUI

	// 그리드백레이아웃에 붙이는 메소드
	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gb.setConstraints(c, gbc);
		gbc.insets = new Insets(2, 2, 2, 2);
		add(c, gbc);
	}// gbAdd

	public static void main(String[] args) {
		new UserProc();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnInsert) {
			insertMember();
			System.out.println("insertMember() 호출 종료");
		} else if (ae.getSource() == btnCancel) {
			this.dispose(); // 창닫기 (현재창만 닫힘)
			// system.exit(0)=> 내가 띄운 모든 창이 다 닫힘
		} else if (ae.getSource() == btnUpdate) {
			UpdateMember();
		} else if (ae.getSource() == btnDelete) {
			// int x = JOptionPane.showConfirmDialog(this,"정말 삭제하시겠습니까?");
			int x = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);

			if (x == JOptionPane.OK_OPTION) {
				deleteMember();
			} else {
				JOptionPane.showMessageDialog(this, "삭제를 취소하였습니다.");
			}
		}

		// jTable내용 갱신 메소드 호출
		mList.jTableRefresh();

	}// actionPerformed

	private void deleteMember() {
		String id = tf_id.getText();
		if (id.length() == 0) { // 길이가 0이면
			JOptionPane.showMessageDialog(this, "비밀번호를 꼭 입력하세요!");
			return; // 메소드 끝
		}
		// System.out.println(mList);
		UserDAO dao = new UserDAO();
		boolean ok = dao.deleteUser(id);

		if (ok) {
			JOptionPane.showMessageDialog(this, "삭제완료");
			dispose();

		} else {
			JOptionPane.showMessageDialog(this, "삭제실패");

		}

	}// deleteMember

	private void UpdateMember() {

		// 1. 화면의 정보를 얻는다.
		UserDTO dto = getViewData();
		// 2. 그정보로 DB를 수정
		UserDAO dao = new UserDAO();
		boolean ok = dao.updateUser(dto);

		if (ok) {
			JOptionPane.showMessageDialog(this, "수정되었습니다.");
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "수정실패: 값을 확인하세요");
		}
	}

	private void insertMember() {

		// 화면에서 사용자가 입력한 내용을 얻는다.
		UserDTO dto = getViewData();
		UserDAO dao = new UserDAO();
		boolean ok = dao.insertUser(dto);

		if (ok) {
			JOptionPane.showMessageDialog(this, "가입이 완료되었습니다.");
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, "가입이 정상적으로 처리되지 않았습니다.");
		}
	}// insertMember

	public UserDTO getViewData() {

		// 화면에서 사용자가 입력한 내용을 얻는다.
		UserDTO dto = new UserDTO();
		String id = tf_id.getText();
		String name = tf_name.getText();
		String password = pf_password.getText();
		int borrow_cnt = Integer.parseInt(tf_borrow_cnt.getText());
		int return_cnt = Integer.parseInt(tf_return_cnt.getText());
		int overdue_cnt = Integer.parseInt(tf_overdue_cnt.getText());
		int maximum = Integer.parseInt(tf_maximum.getText());
		String email = tf_email.getText();

		// dto에 담는다.
		dto.setId(id);
		dto.setName(name);
		dto.setPassword(password);
		dto.setBorrow_cnt(borrow_cnt);
		dto.setReturn_cnt(return_cnt);
		dto.setOverdue_cnt(overdue_cnt);
		dto.setMaximum(maximum);
		dto.setEmail(email);

		return dto;
	}
}// end