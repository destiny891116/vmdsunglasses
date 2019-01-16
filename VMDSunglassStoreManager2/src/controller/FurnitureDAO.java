package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.FurnitureVO;
import model.ItemVO;

public class FurnitureDAO {

	// ������ ���⸦ ���
	public FurnitureVO getItemUpdate(FurnitureVO fvo) throws Exception {
		// 2.������ ó���� ���� SQL��
		/* String day2 = fvo.getI_ForwardingDate(); */

		StringBuffer sql = new StringBuffer();
		sql.append("insert into FurnitureForwardingManagement ");
		sql.append(
				"(I_number, I_BrandName, I_SojipgiName, I_ModelName, I_StoreName, I_ForwardingQuantity, I_ForwardingDate)");
		sql.append(" values (Furniture_seq.nextval, ?, ?, ?, ?, ?, ?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		FurnitureVO fVo = fvo;

		try {
			// 3.DBUtil �̶�� Ŭ������ getConnection()�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// 4. ������ �л� ������ �����ϱ� ���Ͽ� SQL�������� ����
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, fVo.getI_BrandName());
			pstmt.setString(2, fVo.getI_SojipgiName());
			pstmt.setString(3, fVo.getI_ModelName());
			pstmt.setString(4, fVo.getI_StoreName());
			pstmt.setString(5, fVo.getI_ForwardingQuantity());
			pstmt.setString(6, fVo.getI_ForwardingDate() + "");

			// 5.SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� ��� �Ϸ�");
				alert.setHeaderText("������� �Ϸ�");
				alert.setContentText("������� ����!!");
				alert.showAndWait();

				fVo = new FurnitureVO();

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
		return fVo;
	}

	// 7.�����(storeName)�� �Է¹޾� ���� ��ȸ
	public FurnitureVO getItemCheck2(String I_StoreName) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from FurnitureForwardingManagement where I_StoreName like ");
		sql.append("? order by I_number desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FurnitureVO fVo = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + I_StoreName + "%");

			rs = pstmt.executeQuery();
			if (rs.next()) {
				fVo = new FurnitureVO();
				fVo.setI_number(rs.getInt("I_number"));
				fVo.setI_BrandName(rs.getString("I_BrandName"));
				fVo.setI_SojipgiName(rs.getString("I_SojipgiName"));
				fVo.setI_ModelName(rs.getString("I_ModelName"));
				fVo.setI_StoreName(rs.getString("I_StoreName"));
				fVo.setI_ForwardingQuantity(rs.getString("I_ForwardingQuantity"));
				fVo.setI_ForwardingDate(rs.getString("I_ForwardingDate") + "");

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
		return fVo;
	}

	// 7.�����(storeName)�� �ҷ�����
	public ArrayList<String> getStroeName() {
		ArrayList<String> list = new ArrayList<String>();

		StringBuffer sql = new StringBuffer();
		String get;

		sql.append("select s_StoreName from storemanagement ");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				get = rs.getString(1);
				list.add(get);

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

	// ���Ȯ��
	public FurnitureVO getReleaseOKUpdate(FurnitureVO fvo, String i_SojipgiName) throws Exception {
		String sql = "update ItemInventoryManagement set I_QuantityOfWarehoused= I_QuantityOfWarehoused - ? where I_SojipgiName = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		FurnitureVO retval = null;
		System.out.println("�������ߵ���?");
		try {
			con = DBUtil.getConnection();

			// ������ ������ �����ϱ� ���Ͽ� SQL���� ����
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fvo.getI_SojipgiName());
			pstmt.setString(2, fvo.getI_ForwardingQuantity());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��ǰ ���");
				alert.setHeaderText("���� ���� �Ϸ�");
				alert.setContentText("��� ����!");
				alert.showAndWait();

				retval = new FurnitureVO();
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

	// ���Ȯ��
	public FurnitureVO getReturnUpdate(FurnitureVO fvo, String i_SojipgiName) throws Exception {
		String sql = "update ItemInventoryManagement set I_QuantityOfWarehoused= I_QuantityOfWarehoused + ? where I_SojipgiName = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		FurnitureVO retval = null;
		System.out.println("�������ߵ���?");
		try {
			con = DBUtil.getConnection();

			// ������ ������ �����ϱ� ���Ͽ� SQL���� ����
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fvo.getI_SojipgiName());
			pstmt.setString(2, fvo.getI_ForwardingQuantity());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��ǰ ���");
				alert.setHeaderText("���� ���� �Ϸ�");
				alert.setContentText("��� ����!");
				alert.showAndWait();

				retval = new FurnitureVO();
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

	// ���� �ݳ�
	public void getItemReturn(int I_number) throws Exception {
		// 2. ������ ó���� ���� SQL��
		String sql = "delete from FurnitureForwardingManagement where I_number = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 3.DBUtil �̶�� Ŭ������ getConnection()�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// 5.SQL���� ���� �� ó�� ����� ����
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, I_number);
			System.out.println("");
			// 5.SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� �ݳ�");
				alert.setHeaderText("���� �ݳ� �Ϸ�");
				alert.setContentText("���� �ݳ� ����!!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� �ݳ�");
				alert.setHeaderText("���� �ݳ� ����");
				alert.setContentText("���� �ݳ� ����!!");
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
	public ArrayList<FurnitureVO> getFurnitureTotal() {
		ArrayList<FurnitureVO> list3 = new ArrayList<FurnitureVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select I_number, I_BrandName, ");
		sql.append("I_SojipgiName, I_ModelName, I_StoreName, ");
		sql.append("I_ForwardingQuantity, I_ForwardingDate ");
		sql.append("from FurnitureForwardingManagement order by I_number desc");

		Connection con2 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		FurnitureVO fVo = null;

		try {
			con2 = DBUtil.getConnection();
			pstmt2 = con2.prepareStatement(sql.toString());
			rs2 = pstmt2.executeQuery();
			while (rs2.next()) {
				fVo = new FurnitureVO();
				fVo.setI_number(rs2.getInt("I_number"));
				fVo.setI_BrandName(rs2.getString("I_BrandName"));
				fVo.setI_SojipgiName(rs2.getString("I_SojipgiName"));
				fVo.setI_ModelName(rs2.getString("I_ModelName"));
				fVo.setI_StoreName(rs2.getString("I_StoreName"));
				fVo.setI_ForwardingQuantity(rs2.getString("I_ForwardingQuantity"));
				fVo.setI_ForwardingDate(rs2.getDate("I_ForwardingDate") + "");

				list3.add(fVo);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs2 != null)
					rs2.close();
				if (pstmt2 != null)
					pstmt2.close();
				if (con2 != null)
					con2.close();
			} catch (SQLException se) {

			}
		}
		return list3;
	}

	// �����ͺ��̽����� ���� ���̺��� �÷��� ���� (����)
	public ArrayList<String> getColumnName2() {
		ArrayList<String> columnName2 = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from FurnitureForwardingManagement");

		Connection con2 = null;
		PreparedStatement pstmt = null;
		ResultSet rs2 = null;
		// ResultSetMetaData ��ü ���� ����
		ResultSetMetaData rsmd2 = null;
		try {
			con2 = DBUtil.getConnection();
			pstmt = con2.prepareStatement(sql.toString());
			System.out.println("");
			rs2 = pstmt.executeQuery();
			rsmd2 = rs2.getMetaData();
			int cols = rsmd2.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				columnName2.add(rsmd2.getColumnName(i));
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs2 != null)
					rs2.close();
				if (pstmt != null)
					pstmt.close();
				if (con2 != null)
					con2.close();
			} catch (SQLException se) {

			}
		}
		return columnName2;
	}

}