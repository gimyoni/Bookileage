package main;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import javax.swing.JOptionPane;

public class UserLogin {
	public UserLogin(Library library) {
		String query;
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rset = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false", "root", "1234");
            
            query = "SELECT id, password FROM user";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
             
			boolean result = true;
			
            while (result = rset.next()) {
                if(rset.getString("id").equals(LoginPanel.getTf_login_id().getText()) && rset.getString("password").equals(LoginPanel.getPf_login_pw().getText())){
                	JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);

                	LoginPanel.getTf_login_id().setText("");
                	LoginPanel.getPf_login_pw().setText("");
                	
                	LoginPanel.getTf_login_id().setVisible(true);
                	LoginPanel.getPf_login_pw().setVisible(true);
    				
    				library.loginPanel.setVisible(false);
    				library.myPagePanel.setVisible(true);
    				
					break;
                }
            }
            
            //로그인 실패 시
            if(result == false) {
            	JOptionPane.showMessageDialog(null, "아이디, 비밀번호가 잘못입력되었습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rset.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
}