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

	// ȸ������ ����Ʈ �ҷ�����
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

	// ������ ����
	public void getVMDDelete(String id) throws Exception {
		// 2. ������ ó���� ���� SQL��
		String sql = "delete from vmdmanagerjoin where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 3.DBUtil �̶�� Ŭ������ getConnection()�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// 5.SQL���� ���� �� ó�� ����� ����
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			// 5.SQL���� ������ ó�� ����� ����
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("������ ����");
				alert.setHeaderText("������ ���� �Ϸ�");
				alert.setContentText("������ ���� ����!!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("������ ����");
				alert.setHeaderText("������ ���� ����");
				alert.setContentText("������ ���� ����!!");
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

	// �����ͺ��̽����� ���� ���̺��� �÷��� ����
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from vmdmanagerjoin");

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
