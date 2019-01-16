package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
	// ���̵� �ߺ� üũ
	public boolean getLogin(String loginld, String loginPassword) throws Exception {
		String sql = "select * from vmdmanagerjoin where id = ? and password = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean loginResult = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginld);
			pstmt.setString(2, loginPassword);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				loginResult = true; // ���̵�� �н����尡 ��ġ�Ѵ�
				System.out.println("�α��� ����!!");
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �����ͺ��̽����� ���ῡ ���� ������Ʈ ����
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return loginResult;
	}
}
