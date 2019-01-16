package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.JoinVO;

public class JoinDAO {

	// VMD ������ ���
	public boolean getTeacherRegiste(JoinVO jvo) throws Exception {

		String sql = "insert into vmdmanagerjoin" + "(id, password, name, position, phonenumber)" + " values "
				+ "( ?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean joinSucess = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, jvo.getId());
			pstmt.setString(2, jvo.getPassword());
			pstmt.setString(3, jvo.getName());
			pstmt.setString(4, jvo.getPosition());
			pstmt.setString(5, jvo.getPhoneNumber());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("VMD ���� ���");
				alert.setHeaderText("VMD ��� �Ϸ�");
				alert.setContentText("VMD ���� ��� ����!!");
				alert.showAndWait();
				joinSucess = true;
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("VMD ���� ���");
				alert.setHeaderText("VMD ���� ��� ����");
				alert.setContentText("VMD ���� ��� ����!!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
			e.printStackTrace();
		} finally {
			try {
				// �����ͺ��̽��� ����Ǿ��ִ� ������Ʈ ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
		return joinSucess;
	}

	// ���̵� �ߺ� üũ
	public boolean getIdCheck(String idCheck) throws Exception {
		String sql = "select * from vmdmanagerjoin where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean idCheckResult = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, idCheck);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				idCheckResult = true; // �ߺ��� ���̵� �ִ�.
			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}

		return idCheckResult;
	}
}
