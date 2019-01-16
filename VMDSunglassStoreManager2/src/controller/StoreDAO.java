package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.StoreVO;

public class StoreDAO {

	// 매장 등록하는 메소드
	public StoreVO Storeregiste(StoreVO svo) throws Exception {

		// 2. 데이터 처리를 위한 SQL문
		String day = svo.getS_LocationDate();
		String day2 = svo.getS_WithdrawalDate();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into storemanagement ");
		sql.append(
				"(s_number, s_DepartmentClassification, s_DepartmentClassification2, s_BrandName, s_StoreName, s_StoreLocation, s_LocationDate, s_WithdrawalDate, s_Material, s_VisualSize, gender, s_CompanyName, s_ConstructionAmount, imageView)");
		sql.append(" values (storemanagement_seq.nextval, ?, ?, ?, ?, ?, to_date('" + day
				+ "', 'YYYY-MM-DD'), to_date('" + day2 + "', 'YYYY-MM-DD'), ?, ?, ?, ?, ?, ?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		StoreVO sVo = svo;

		try {
			// 3. DBUtil 이라는 클래스의 getConnection() 메서드로 데이터베이스 연결
			con = DBUtil.getConnection();

			// 4. 입력 받은 매장 정보를 처리하기 위해 SQL문장을 생성
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, sVo.getS_DepartmentClassification());
			pstmt.setString(2, sVo.getS_DepartmentClassification2());
			pstmt.setString(3, sVo.getS_BrandName());
			pstmt.setString(4, sVo.getS_StoreName());
			pstmt.setString(5, sVo.getS_StoreLocation());
			pstmt.setString(6, sVo.getS_Material());
			pstmt.setString(7, sVo.getS_VisualSize());
			pstmt.setString(8, sVo.getGender());
			pstmt.setString(9, sVo.getS_CompanyName());
			pstmt.setString(10, sVo.getS_ConstructionAmount());
			pstmt.setString(11, sVo.getImageView());

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
		return sVo;
	}

	// 7.매장명(storeName)을 입력받아 정보 조회
	public StoreVO getStoreCheck(String s_StoreName) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from storemanagement where s_StoreName like ");
		sql.append("? order by s_number desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StoreVO sVo = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + s_StoreName + "%");

			rs = pstmt.executeQuery();
			if (rs.next()) {
				sVo = new StoreVO();
				sVo.setS_number(rs.getInt("s_number"));
				sVo.setS_DepartmentClassification(rs.getString("s_DepartmentClassification"));
				sVo.setS_DepartmentClassification2(rs.getString("s_DepartmentClassification2"));
				sVo.setS_BrandName(rs.getString("s_BrandName"));
				sVo.setS_StoreName(rs.getString("s_StoreName"));
				sVo.setS_StoreLocation(rs.getString("s_StoreLocation"));
				sVo.setS_LocationDate(rs.getDate("s_LocationDate") + "");
				sVo.setS_WithdrawalDate(rs.getDate("s_WithdrawalDate") + "");
				sVo.setS_Material(rs.getString("s_Material"));
				sVo.setS_VisualSize(rs.getString("s_VisualSize"));
				sVo.setGender(rs.getString("gender"));
				sVo.setS_CompanyName(rs.getString("s_CompanyName"));
				sVo.setS_ConstructionAmount(rs.getString("s_ConstructionAmount"));
				sVo.setImageView(rs.getString("imageView"));
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
		return sVo;
	}

	// 선택한 매장의 정보 수정 및 입점일 철수일 수정
	public StoreVO getStoreUpdate(StoreVO svo, int s_number) throws Exception {
		// 2.데이터 처리를 위한 SQL문
		String editDay = svo.getS_LocationDate();
		String editDay2 = svo.getS_WithdrawalDate();

		StringBuffer sql = new StringBuffer();
		sql.append("update storemanagement set ");
		sql.append(" s_StoreLocation=?, s_LocationDate=to_date('" + editDay
				+ "', 'YYYY-MM-DD'), s_WithdrawalDate=to_date('" + editDay2
				+ "', 'YYYY-MM-DD'), s_Material=?, s_VisualSize=?, gender=?, imageView=? ");
		sql.append(" where s_number = ?");

		Connection con = null;
		PreparedStatement pstmt = null;
		StoreVO retval = null;

		try {
			// 3.DBUtil 이라는 클래스의 getConnection()메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 4. 수정한 학생 정보를 수정하기 위하여 SQL문장으로 생성
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, svo.getS_StoreLocation());
			pstmt.setString(2, svo.getS_Material());
			pstmt.setString(3, svo.getS_VisualSize());
			pstmt.setString(4, svo.getGender());
			pstmt.setString(5, svo.getImageView());
			pstmt.setInt(6, s_number);

			// 5.SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("매장정보 수정");
				alert.setHeaderText("매장정보 수정 완료");
				alert.setContentText("매장정보 수정 성공!!");
				alert.showAndWait();

				retval = new StoreVO();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("매장 수정");
				alert.setHeaderText("매장정보 수정 실패");
				alert.setContentText("매장정보 수정 실패!!");
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
		return retval;
	}

	public void getStoreDelete(int s_number) throws Exception {
		// 2. 데이터 처리를 위한 SQL문
		String sql = "delete from storemanagement where s_number = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 3.DBUtil 이라는 클래스의 getConnection()메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 5.SQL문을 수행 후 처리 결과를 얻어옴
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, s_number);
			System.out.println("");
			// 5.SQL문을 수행후 처리 결과를 얻어옴
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("매장 삭제");
				alert.setHeaderText("매장 삭제 완료");
				alert.setContentText("매장 삭제 성공!!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("매장 삭제");
				alert.setHeaderText("매장 삭제 실패");
				alert.setContentText("매장 삭제 실패!!");
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
	public ArrayList<StoreVO> getStoreTotal() {
		ArrayList<StoreVO> list = new ArrayList<StoreVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select s_number, s_DepartmentClassification, ");
		sql.append("s_DepartmentClassification2, s_BrandName, s_StoreName, ");
		sql.append("s_StoreLocation, s_LocationDate, s_WithdrawalDate, ");
		sql.append("s_Material, s_VisualSize, gender, s_CompanyName, ");
		sql.append("s_ConstructionAmount, imageView from storemanagement order by s_number desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StoreVO sVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sVo = new StoreVO();
				sVo.setS_number(rs.getInt("s_number"));
				sVo.setS_DepartmentClassification(rs.getString("s_DepartmentClassification"));
				sVo.setS_DepartmentClassification2(rs.getString("s_DepartmentClassification2"));
				sVo.setS_BrandName(rs.getString("s_BrandName"));
				sVo.setS_StoreName(rs.getString("s_StoreName"));
				sVo.setS_StoreLocation(rs.getString("s_StoreLocation"));
				sVo.setS_LocationDate(rs.getDate("s_LocationDate") + "");
				sVo.setS_WithdrawalDate(rs.getDate("s_WithdrawalDate") + "");
				sVo.setS_Material(rs.getString("s_Material"));
				sVo.setS_VisualSize(rs.getString("s_VisualSize"));
				sVo.setGender(rs.getString("gender"));
				sVo.setS_CompanyName(rs.getString("s_CompanyName"));
				sVo.setS_ConstructionAmount(rs.getString("s_ConstructionAmount"));
				sVo.setImageView(rs.getString("imageView"));

				list.add(sVo);
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
		sql.append("select * from storemanagement");

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