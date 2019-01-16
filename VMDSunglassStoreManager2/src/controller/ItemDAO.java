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
import model.StoreVO;

public class ItemDAO {

	// ���� ����ϴ� �޼ҵ�
	public ItemVO Itemregiste(ItemVO ivo) throws Exception {
		// 2. ������ ó���� ���� SQL��
		String day = ivo.getI_ReceivingDate();

		StringBuffer sql = new StringBuffer();
		sql.append("insert into ItemInventoryManagement ");
		sql.append(
				"(I_number, I_BrandName, I_SojipgiName, I_ModelName, I_Storage, I_QuantityOfWarehoused, I_Price, I_CompanyName, I_ReceivingDate, I_imageView)");
		sql.append(" values (ItemInventoryManagement_seq.nextval, ?, ?, ?, ?, ?, ?, ?, to_date('" + day
				+ "', 'YYYY-MM-DD'), ?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		ItemVO iVo = ivo;
		try {
			// 3. DBUtil �̶�� Ŭ������ getConnection() �޼���� �����ͺ��̽� ����
			con = DBUtil.getConnection();

			// 4. �Է� ���� ���� ������ ó���ϱ� ���� SQL������ ����
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, iVo.getI_BrandName());
			pstmt.setString(2, iVo.getI_SojipgiName());
			pstmt.setString(3, iVo.getI_ModelName());
			pstmt.setString(4, iVo.getI_Storage());
			pstmt.setString(5, iVo.getI_QuantityOfWarehoused());
			pstmt.setString(6, iVo.getI_Price());
			pstmt.setString(7, iVo.getI_CompanyName());
			pstmt.setString(8, iVo.getI_imageView());

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
		return iVo;
	}

	// 7.�����(storeName)�� �Է¹޾� ���� ��ȸ
	public ItemVO getItemCheck(String I_SojipgiName) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ItemInventoryManagement where I_SojipgiName like ");
		sql.append("? order by I_number desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemVO iVo = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + I_SojipgiName + "%");

			rs = pstmt.executeQuery();
			if (rs.next()) {
				iVo = new ItemVO();
				iVo.setI_number(rs.getInt("I_number"));
				iVo.setI_BrandName(rs.getString("I_BrandName"));
				iVo.setI_SojipgiName(rs.getString("I_SojipgiName"));
				iVo.setI_ModelName(rs.getString("I_ModelName"));
				iVo.setI_Storage(rs.getString("I_Storage"));
				iVo.setI_QuantityOfWarehoused(rs.getString("I_QuantityOfWarehoused"));
				iVo.setI_Price(rs.getString("s_LocationDate") + "");
				iVo.setI_CompanyName(rs.getString("I_CompanyName"));
				iVo.setI_ReceivingDate(rs.getDate("I_ReceivingDate") + "");
				iVo.setI_imageView(rs.getString("I_imageView"));
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
		return iVo;
	}

	public void getItemDelete(int I_number) throws Exception {
		// 2. ������ ó���� ���� SQL��
		String sql = "delete from ItemInventoryManagement where I_number = ?";
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
	public ArrayList<ItemVO> getItemTotal() {
		ArrayList<ItemVO> list = new ArrayList<ItemVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select I_number, I_BrandName, ");
		sql.append("I_SojipgiName, I_ModelName, I_Storage, ");
		sql.append("I_QuantityOfWarehoused, I_Price, I_CompanyName, ");
		sql.append("I_ReceivingDate, I_imageView ");
		sql.append("from ItemInventoryManagement order by I_number desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemVO iVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				iVo = new ItemVO();
				iVo.setI_number(rs.getInt("I_number"));
				iVo.setI_BrandName(rs.getString("I_BrandName"));
				iVo.setI_SojipgiName(rs.getString("I_SojipgiName"));
				iVo.setI_ModelName(rs.getString("I_ModelName"));
				iVo.setI_Storage(rs.getString("I_Storage"));
				iVo.setI_QuantityOfWarehoused(rs.getString("I_QuantityOfWarehoused"));
				iVo.setI_Price(rs.getString("I_Price"));
				iVo.setI_CompanyName(rs.getString("I_CompanyName"));
				iVo.setI_ReceivingDate(rs.getDate("I_ReceivingDate") + "");
				iVo.setI_imageView(rs.getString("I_imageView"));

				list.add(iVo);
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

	// ������ ������ �԰� ����
	public ItemVO getWarehousingUpdate(ItemVO ivo, int i_number) throws Exception {
		// 2.������ ó���� ���� SQL��

		StringBuffer sql = new StringBuffer();
		sql.append("update ItemInventoryManagement set ");
		sql.append(" I_QuantityOfWarehoused=? ");
		sql.append(" where I_number = ?");

		Connection con = null;
		PreparedStatement pstmt = null;
		ItemVO retval = null;

		try {
			// 3.DBUtil �̶�� Ŭ������ getConnection()�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// 4. ������ �л� ������ �����ϱ� ���Ͽ� SQL�������� ����
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, ivo.getI_QuantityOfWarehoused());
			pstmt.setInt(2, i_number);

			// 5.SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� �԰�");
				alert.setHeaderText("�����԰� �Ϸ�");
				alert.setContentText("�����԰� ����!!");
				alert.showAndWait();

				retval = new ItemVO();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� �԰�");
				alert.setHeaderText("�����԰� ����");
				alert.setContentText("�����԰� ����!!");
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

	// ������ ���� ��� �� ������ ����
	public ItemVO getItemUpdate(ItemVO ivo, int i_number) throws Exception {
		// 2.������ ó���� ���� SQL��

		StringBuffer sql = new StringBuffer();
		sql.append("update ItemInventoryManagement set ");
		sql.append(" I_QuantityOfWarehoused=? ");
		sql.append(" where I_number = ?");

		Connection con = null;
		PreparedStatement pstmt = null;
		ItemVO retval = null;

		try {
			// 3.DBUtil �̶�� Ŭ������ getConnection()�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// 4. ������ �л� ������ �����ϱ� ���Ͽ� SQL�������� ����
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, ivo.getI_QuantityOfWarehoused());
			pstmt.setInt(2, i_number);

			// 5.SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� �԰�");
				alert.setHeaderText("�����԰� �Ϸ�");
				alert.setContentText("�����԰� ����!!");
				alert.showAndWait();

				retval = new ItemVO();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���� �԰�");
				alert.setHeaderText("�����԰� ����");
				alert.setContentText("�����԰� ����!!");
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

	// �����ͺ��̽����� ���� ���̺��� �÷��� ����
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ItemInventoryManagement");

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