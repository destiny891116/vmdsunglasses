package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.JoinVO;

public class VMDDAO {

	// 회원가입 리스트 불러오기
	public ArrayList<JoinVO> getVMD() {
		ArrayList<JoinVO> list = new ArrayList<JoinVO>();

		JoinVO jvo = new JoinVO();

		StringBuffer sql = new StringBuffer();

		sql.append("select * from vmdmanagerjoin ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				jvo = new JoinVO();

				jvo.setId(rs.getString(1));
				jvo.setPassword(rs.getString(2));
				jvo.setName(rs.getString(3));
				jvo.setPosition(rs.getString(4));
				jvo.setPhoneNumber(rs.getString(5));
				list.add(jvo);

			}
		} catch (

		SQLException se) {
			System.out.println(se);

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {

			}

		}
		return list;
	}

	// 관리자 삭제
	public void getVMDDelete(String id) throws Exception {
		// 2. 데이터 처리를 위한 SQL문
		String sql = "delete from vmdmanagerjoin where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 3.DBUtil 이라는 클래스의 getConnection()메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 5.SQL문을 수행 후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			// 5.SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("관리자 삭제");
				alert.setHeaderText("관리자 삭제 완료");
				alert.setContentText("관리자 삭제 성공!!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("관리자 삭제");
				alert.setHeaderText("관리자 삭제 실패");
				alert.setContentText("관리자 삭제 실패!!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 6.데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
	}

	// 매장 전체 리스트
	public ArrayList<JoinVO> getVMDTotal() {
		ArrayList<JoinVO> list = new ArrayList<JoinVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select id, Password, ");
		sql.append("Name, Position, PhoneNumber ");
		sql.append("from vmdmanagerjoin order by id desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JoinVO jVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				jVo = new JoinVO();
				
				jVo.setId(rs.getString("id"));
				jVo.setPassword(rs.getString("Password"));
				jVo.setName(rs.getString("Name"));
				jVo.setPosition(rs.getString("Position"));
				jVo.setPhoneNumber(rs.getString("PhoneNumber"));
		

				list.add(jVo);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {

			}
		}
		return list;
	}

	// 데이터베이스에서 매장 테이블의 컬럼의 갯수
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from vmdmanagerjoin");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ResultSetMetaData 객체 변수 선언
		ResultSetMetaData rsmd = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			System.out.println("");
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				columnName.add(rsmd.getColumnName(i));
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {

			}
		}
		return columnName;
	}

}
