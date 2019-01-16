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

	// ���� ����ϴ� �޼ҵ�
	public StoreVO Storeregiste(StoreVO svo) throws Exception {

		// 2. ������ ó���� ���� SQL��
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
			// 3. DBUtil �̶�� Ŭ������ getConnection() �޼���� �����ͺ��̽� ����
			con = DBUtil.getConnection();

			// 4. �Է� ���� ���� ������ ó���ϱ� ���� SQL������ ����
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

			// 5. SQL���� ���� �� ó�� ����� ����
			int i = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 6. �����ͺ��̽��� ���ῡ ���� ������Ʈ ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
		return sVo;
	}

	// 7.�����(storeName)�� �Է¹޾� ���� ��ȸ
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

	// ������ ������ ���� ���� �� ������ ö���� ����
	public StoreVO getStoreUpdate(StoreVO svo, int s_number) throws Exception {
		// 2.������ ó���� ���� SQL��
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
			// 3.DBUtil �̶�� Ŭ������ getConnection()�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// 4. ������ �л� ������ �����ϱ� ���Ͽ� SQL�������� ����
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, svo.getS_StoreLocation());
			pstmt.setString(2, svo.getS_Material());
			pstmt.setString(3, svo.getS_VisualSize());
			pstmt.setString(4, svo.getGender());
			pstmt.setString(5, svo.getImageView());
			pstmt.setInt(6, s_number);

			// 5.SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�������� ����");
				alert.setHeaderText("�������� ���� �Ϸ�");
				alert.setContentText("�������� ���� ����!!");
				alert.showAndWait();

				retval = new StoreVO();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� ����");
				alert.setHeaderText("�������� ���� ����");
				alert.setContentText("�������� ���� ����!!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 6.�����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
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
		// 2. ������ ó���� ���� SQL��
		String sql = "delete from storemanagement where s_number = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 3.DBUtil �̶�� Ŭ������ getConnection()�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// 5.SQL���� ���� �� ó�� ����� ����
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, s_number);
			System.out.println("");
			// 5.SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� ����");
				alert.setHeaderText("���� ���� �Ϸ�");
				alert.setContentText("���� ���� ����!!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� ����");
				alert.setHeaderText("���� ���� ����");
				alert.setContentText("���� ���� ����!!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 6.�����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
	}

	// ���� ��ü ����Ʈ
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

	// �����ͺ��̽����� ���� ���̺��� �÷��� ����
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from storemanagement");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ResultSetMetaData ��ü ���� ����
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