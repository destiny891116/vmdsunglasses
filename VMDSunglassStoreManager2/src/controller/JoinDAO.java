package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.JoinVO;

public class JoinDAO {

	// VMD 관리자 등록
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
				alert.setTitle("VMD 관리 등록");
				alert.setHeaderText("VMD 등록 완료");
				alert.setContentText("VMD 관리 등록 성공!!");
				alert.showAndWait();
				joinSucess = true;
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("VMD 관리 등록");
				alert.setHeaderText("VMD 관리 등록 실패");
				alert.setContentText("VMD 관리 등록 실패!!");
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
				// 데이터베이스에 연결되어있던 오브젝트 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
		return joinSucess;
	}

	// 아이디 중복 체크
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
				idCheckResult = true; // 중복된 아이디가 있다.
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
