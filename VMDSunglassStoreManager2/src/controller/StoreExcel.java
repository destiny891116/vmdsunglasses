package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.StoreVO;

public class StoreExcel {
	//
	public boolean xlsxWiter(List<StoreVO> list, String saveDir) {
		// ��ũ�ϻ���
		XSSFWorkbook workbook = new XSSFWorkbook();
		// ��ũ��Ʈ����
		XSSFSheet sheet = workbook.createSheet();
		// �����
		XSSFRow row = sheet.createRow(0);
		// ������
		XSSFCell cell;

		// �����������
		cell = row.createCell(0);
		cell.setCellValue("��ȣ");

		cell = row.createCell(1);
		cell.setCellValue("���屸��");

		cell = row.createCell(2);
		cell.setCellValue("���屸��");

		cell = row.createCell(3);
		cell.setCellValue("�귣���");

		cell = row.createCell(4);
		cell.setCellValue("�����");

		cell = row.createCell(5);
		cell.setCellValue("������ġ");

		cell = row.createCell(6);
		cell.setCellValue("������");

		cell = row.createCell(7);
		cell.setCellValue("ö����");

		cell = row.createCell(8);
		cell.setCellValue("����");

		cell = row.createCell(9);
		cell.setCellValue("����������");

		cell = row.createCell(10);
		cell.setCellValue("���󿩺�");

		cell = row.createCell(11);
		cell.setCellValue("��ü��");

		cell = row.createCell(12);
		cell.setCellValue("����ݾ�");

		cell = row.createCell(13);
		cell.setCellValue("�̹������ϸ�");

		// ����Ʈ�� �����ŭ �ο츦 ����
		StoreVO vo;
		for (int ri = 0; ri < list.size(); ri++) {
			vo = list.get(ri);

			// �����
			row = sheet.createRow(ri + 1);

			cell = row.createCell(0);
			cell.setCellValue(vo.getS_number());
			cell = row.createCell(1);
			cell.setCellValue(vo.getS_DepartmentClassification());
			cell = row.createCell(2);
			cell.setCellValue(vo.getS_DepartmentClassification2());
			cell = row.createCell(3);
			cell.setCellValue(vo.getS_BrandName());
			cell = row.createCell(4);
			cell.setCellValue(vo.getS_StoreName());
			cell = row.createCell(5);
			cell.setCellValue(vo.getS_StoreLocation());
			cell = row.createCell(6);
			cell.setCellValue(vo.getS_LocationDate());
			cell = row.createCell(7);
			cell.setCellValue(vo.getS_WithdrawalDate());
			cell = row.createCell(8);
			cell.setCellValue(vo.getS_Material());
			cell = row.createCell(9);
			cell.setCellValue(vo.getS_VisualSize());
			cell = row.createCell(10);
			cell.setCellValue(vo.getGender());
			cell = row.createCell(11);
			cell.setCellValue(vo.getS_CompanyName());
			cell = row.createCell(12);
			cell.setCellValue(vo.getS_ConstructionAmount());
			cell = row.createCell(13);
			cell.setCellValue(vo.getImageView());

		}

		// �Էµ� ���� ���Ϸ� ����
		String strReportPDFName = "Store_" + System.currentTimeMillis() + ".xlsx";
		File file = new File(saveDir + "\\" + strReportPDFName);
		FileOutputStream fos = null;

		boolean saveSuccess = false;

		try {
			fos = new FileOutputStream(file);
			if (fos != null) {
				workbook.write(fos);
				saveSuccess = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return saveSuccess;
	}//

}
