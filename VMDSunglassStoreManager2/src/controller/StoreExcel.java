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
		// 워크북생성
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 워크시트생성
		XSSFSheet sheet = workbook.createSheet();
		// 행생성
		XSSFRow row = sheet.createRow(0);
		// 셀생성
		XSSFCell cell;

		// 헤더정보구성
		cell = row.createCell(0);
		cell.setCellValue("번호");

		cell = row.createCell(1);
		cell.setCellValue("매장구분");

		cell = row.createCell(2);
		cell.setCellValue("매장구분");

		cell = row.createCell(3);
		cell.setCellValue("브랜드명");

		cell = row.createCell(4);
		cell.setCellValue("매장명");

		cell = row.createCell(5);
		cell.setCellValue("매장위치");

		cell = row.createCell(6);
		cell.setCellValue("입점일");

		cell = row.createCell(7);
		cell.setCellValue("철수일");

		cell = row.createCell(8);
		cell.setCellValue("재질");

		cell = row.createCell(9);
		cell.setCellValue("비쥬얼사이즈");

		cell = row.createCell(10);
		cell.setCellValue("영상여부");

		cell = row.createCell(11);
		cell.setCellValue("업체명");

		cell = row.createCell(12);
		cell.setCellValue("공사금액");

		cell = row.createCell(13);
		cell.setCellValue("이미지파일명");

		// 리스트의 사이즈만큼 로우를 생성
		StoreVO vo;
		for (int ri = 0; ri < list.size(); ri++) {
			vo = list.get(ri);

			// 행생성
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

		// 입력된 내용 파일로 쓰기
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
