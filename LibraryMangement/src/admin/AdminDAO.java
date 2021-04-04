package admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

//DB 처리
public class AdminDAO {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/library?useSSL=false";

	private static final String USER = "root"; // DB ID
	private static final String PASS = "1234"; // DB 패스워드
//	Manager_List mList;

	public AdminDAO() {

	}

//	public UserDAO(Manager_List mList) {
//		this.mList = mList;
//		System.out.println("DAO=>" + mList);
//	}

	/** DB연결 메소드 */
	public Connection getConn() {
		Connection con = null;

		try {
			Class.forName(DRIVER); // 1. 드라이버 로딩
			con = DriverManager.getConnection(URL, USER, PASS); // 2. 드라이버 연결

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	/** 한사람의 회원 정보를 얻는 메소드 */
	public AdminDTO getAdminDTO(String id) {

		AdminDTO dto = new AdminDTO();

		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과

		try {

			con = getConn();
			String sql = "select * from admin where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(2, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString("password"));
				dto.setAdmin_code(rs.getInt("admin_code"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}


	/** 멤버리스트 출력 */
	public Vector getAdminList() {

		Vector data = new Vector(); // Jtable에 값을 쉽게 넣는 방법 1. 2차원배열 2. Vector 에 vector추가

		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과

		try {

			con = getConn();
			String sql = "select * from admin order by id asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				String id = rs.getString("id");
				String password = rs.getString("password");
				int admin_code = rs.getInt("admin_code");


				Vector row = new Vector();
				row.add(name);
				row.add(id);
				row.add(password);
				row.add(admin_code);

				data.add(row);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}// getMemberList()

	/** 회원 등록 */
	public boolean insertAdmin(AdminDTO dto) {
		boolean ok = false;

		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령

		try {
			// 회원
			con = getConn();
			String sql = "insert into admin(" + "name, id, password, admin_code) "
					+ "values(?,?,?,?)";

			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getId());
			ps.setString(3, dto.getPassword());
			ps.setInt(4, dto.getAdmin_code());
			
			int r = ps.executeUpdate(); // 실행 -> 저장
			if (r > 0)
				ok = true; // 수정이 성공되면 ok값을 true로 변경
			if (r > 0) {
				System.out.println("가입 성공");
				ok = true;
			} else {
				System.out.println("가입 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}// insertMmeber

	/** 회원정보 수정 */
	public boolean updateAdmin(AdminDTO vMem) {
		System.out.println("dto=" + vMem.toString());
		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;
		try {

			con = getConn();
			String sql = "update admin set name=?, id=?, admin_code=? " + "where password=?";

			ps = con.prepareStatement(sql);

			ps.setString(1, vMem.getName());
			ps.setString(2, vMem.getId());
			ps.setString(3, vMem.getPassword());
			ps.setInt(4, vMem.getAdmin_code());
			
			int r = ps.executeUpdate(); // 실행 -> 수정
			// 1~n: 성공 , 0 : 실패

			if (r > 0)
				ok = true; // 수정이 성공되면 ok값을 true로 변경

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}

	/**
	 * 회원정보 삭제 : tip: 실무에서는 회원정보를 Delete 하지 않고 탈퇴여부만 체크한다.
	 */
	public boolean deleteAdmin(String id) {

		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConn();
			String sql = "delete from admin where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(2, id);
			int r = ps.executeUpdate(); // 실행 -> 삭제
			if (r > 0)
				ok = true; // 삭제됨;
			
		} catch (Exception e) {
			System.out.println(e + "-> 오류발생");
		}
		return ok;
	}

	/** DB데이터 다시 불러오기 */
	public void adminSelectAll(DefaultTableModel model) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConn();
			String sql = "select * from admin order by id asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			// DefaultTableModel에 있는 데이터 지우기
			for (int i = 0; i < model.getRowCount();) {
				model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4) };

				model.addRow(data);
			}

		} catch (SQLException e) {
			System.out.println(e + "=> adminSelectAll fail");
		} finally {

			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	public ArrayList<String> getIdList() {

		ArrayList<String> list = new ArrayList<String>();

		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과

		try {

			con = getConn();
			String sql = "select * from admin";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("id"));				
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}