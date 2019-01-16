package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.LcooperatorVO;
import model.StoreVO;

public class LcooperatorDAO {

	// 업체 등록하는 메소드
	public LcooperatorVO LcooperatorRegiste(LcooperatorVO lvo) throws Exception {

		// 데이터 처리를 위한 SQL문
		StringBuffer sql = new StringBuffer();
		sql.append("insert into Lcooperator ");
		sql.append(
				"(L_Number, L_Line, L_CompanyName, L_CorporateNumber, L_Address, L_PhoneNumber, L_FAXNumber, L_WebHard, L_Email, L_Director, L_Department, L_CellPhoneNumber, L_Director2, L_Department2, L_CellPhoneNumber2, L_Director3, L_Department3, L_CellPhoneNumber3, L_Director4, L_Department4, L_CellPhoneNumber4, L_Director5, L_Department5, L_CellPhoneNumber5)");
		sql.append(
				" values (Lcooperator_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		LcooperatorVO lVo = lvo;

		try {
			// DBUtil 이라는 클래스의 getConnection() 메서드로 데이터베이스 연결
			con = DBUtil.getConnection();

			// 입력 받은 협력업체 정보를 처리하기 위해 SQL문장으로 생성

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, lVo.getL_Line());
			pstmt.setString(2, lVo.getL_CompanyName());
			pstmt.setString(3, lVo.getL_CorporateNumber());
			pstmt.setString(4, lVo.getL_Address());
			pstmt.setString(5, lVo.getL_PhoneNumber());
			pstmt.setString(6, lVo.getL_FAXNumber());
			pstmt.setString(7, lVo.getL_WebHard());
			pstmt.setString(8, lVo.getL_Email());
			pstmt.setString(9, lVo.getL_Director());
			pstmt.setString(10, lVo.getL_Department());
			pstmt.setString(11, lVo.getL_CellPhoneNumber());
			pstmt.setString(12, lVo.getL_Director2());
			pstmt.setString(13, lVo.getL_Department2());
			pstmt.setString(14, lVo.getL_CellPhoneNumber2());
			pstmt.setString(15, lVo.getL_Director3());
			pstmt.setString(16, lVo.getL_Department3());
			pstmt.setString(17, lVo.getL_CellPhoneNumber3());
			pstmt.setString(18, lVo.getL_Director4());
			pstmt.setString(19, lVo.getL_Department4());
			pstmt.setString(20, lVo.getL_CellPhoneNumber4());
			pstmt.setString(21, lVo.getL_Director5());
			pstmt.setString(22, lVo.getL_Department5());
			pstmt.setString(23, lVo.getL_CellPhoneNumber5());

			// 5. SQL문을 수행 후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 6. 데이터베이스와 연결에 사용된 오브젝트 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
		return lVo;
	}

	public void getLcooperatorDelete(int L_number) throws Exception {
		// 2. 데이터 처리를 위한 SQL문
		String sql = "delete from Lcooperator where L_number = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 3.DBUtil 이라는 클래스의 getConnection()메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 5.SQL문을 수행 후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, L_number);

			// 5.SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("협력업체 삭제");
				alert.setHeaderText("협력업체 삭제 완료");
				alert.setContentText("협력업체 삭제 성공!!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("협력업체 삭제");
				alert.setHeaderText("협력업체 삭제 실패");
				alert.setContentText("협력업체 삭제 실패!!");
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
	public ArrayList<LcooperatorVO> getLcooperatorTotal() {
		ArrayList<LcooperatorVO> list = new ArrayList<LcooperatorVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * ");
		sql.append("from Lcooperator order by L_number desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LcooperatorVO lVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				lVo = new LcooperatorVO();
				lVo.setL_Number(rs.getInt("L_Number"));
				lVo.setL_Line(rs.getString("L_Line"));
				lVo.setL_CompanyName(rs.getString("L_CompanyName"));
				lVo.setL_CorporateNumber(rs.getString("L_CorporateNumber"));
				lVo.setL_Address(rs.getString("L_Address"));
				lVo.setL_PhoneNumber(rs.getString("L_PhoneNumber"));
				lVo.setL_FAXNumber(rs.getString("L_FAXNumber"));
				lVo.setL_WebHard(rs.getString("L_WebHard"));
				lVo.setL_Email(rs.getString("L_Email"));
				lVo.setL_Director(rs.getString("L_Director"));
				lVo.setL_Department(rs.getString("L_Department"));
				lVo.setL_CellPhoneNumber(rs.getString("L_CellPhoneNumber"));
				lVo.setL_Director2(rs.getString("L_Director2"));
				lVo.setL_Department2(rs.getString("L_Department2"));
				lVo.setL_CellPhoneNumber2(rs.getString("L_CellPhoneNumber2"));
				lVo.setL_Director3(rs.getString("L_Director3"));
				lVo.setL_Department3(rs.getString("L_Department3"));
				lVo.setL_CellPhoneNumber3(rs.getString("L_CellPhoneNumber3"));
				lVo.setL_Director4(rs.getString("L_Director4"));
				lVo.setL_Department4(rs.getString("L_Department4"));
				lVo.setL_CellPhoneNumber4(rs.getString("L_CellPhoneNumber4"));
				lVo.setL_Director5(rs.getString("L_Director5"));
				lVo.setL_Department5(rs.getString("L_Department5"));
				lVo.setL_CellPhoneNumber5(rs.getString("L_CellPhoneNumber5"));
				list.add(lVo);
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
		sql.append("select * from Lcooperator");

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