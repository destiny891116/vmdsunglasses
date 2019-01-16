package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.FurnitureVO;
import model.ItemVO;

public class ItemController implements Initializable {

	@FXML
	private TableView<ItemVO> tableView_ItemStock = new TableView<>();
	@FXML
	private TableView<FurnitureVO> tableView_ReleaceStock2 = new TableView<>(); // 집기출고테이블

	// 집기 등록 : 콤보박스
	@FXML
	private ComboBox<String> cb_I_BrandName;

	// 집기 등록 : 텍스트필드
	@FXML
	private TextField txt_I_SojipgiName;
	@FXML
	private TextField txt_I_ModelName;
	@FXML
	private TextField txt_I_Storage;
	@FXML
	private TextField txt_I_QuantityOfWarehoused;
	@FXML
	private TextField txt_I_Price;
	@FXML
	private TextField txt_I_CompanyName;
	@FXML
	private TextField txt_I_Search;
	@FXML
	private TextField txt_I_Search2;

	// 집기등록 : 버튼
	@FXML
	private Button btn_I_Back;
	@FXML
	private Button btn_I_Add;
	@FXML
	private Button btn_I_Delete;
	@FXML
	private Button btn_I_Release;
	@FXML
	private Button btn_I_Exit;
	@FXML
	private Button btn_I_ImageAdd;
	@FXML
	private Button btn_I_TotalList; // 전체리스트
	@FXML
	private Button btn_I_TotalList2; // 전체리스트 (집기출고)
	@FXML
	private Button btnInit; // 초기화 버튼 -> 만들어야해 FXML
	@FXML
	private Button btn_I_Search;
	@FXML
	private Button btn_I_Search2; // 집기출고테이블검색
	@FXML
	private Button btn_I_return; // 집기반납버튼
	@FXML
	private Button btn_I_Warehousing; // 입고버튼
	@FXML
	private Button btn_I_ReleaseOK; // 출고확정버튼

	// 입고일
	@FXML
	private DatePicker txt_I_ReceivingDate;
	/*
	 * @FXML private DatePicker txt_I_ForwardingDate;
	 */

	// 매장이미지박스 : 앙카펜
	@FXML
	private AnchorPane imageBox2;
	// 집기이미지파일 : 이미지뷰

	@FXML
	private ImageView I_imageView;
	// 집기이미지 : 버튼

	// PDF & EXCEL
	@FXML
	private Button btnSaveFileDir;
	@FXML
	private TextField txtSaveFileDir;
	@FXML
	private Button btnPDF;
	@FXML
	private DatePicker pckDate; // pdf 시간
	@FXML
	private Button btnBarchart;

	//
	ObservableList<ItemVO> data = FXCollections.observableArrayList();
	ObservableList<ItemVO> selectItem = null; // 테이블에서 선택한 정보 저장

	ObservableList<FurnitureVO> data2 = FXCollections.observableArrayList();
	ObservableList<FurnitureVO> selectItem2 = null; // 테이블에서 선택한 정보 저장

	boolean editDelete2 = false; // 수정할 때 확인 버튼 상태 설정
	int selectedIndex; // 테이블에서 선택한 매장 정보 인덱스 저장
	int selectedIndex2; // 테이블에서 선택한 매장 정보 인덱스 저장

	/*
	 * private Stage primaryStage; //
	 */ private Stage primaryStage2;
	String selectFileName = ""; // 이미지 파일명
	String localUrl = ""; // 이미지 파일 경로
	Image localImage;

	int I_number; // 삭제시 테이블에서 선택한 학생의 번호 저장
	File selectedFile = null;

	// 이미지 처리
	// 이미지 저장할 폴더를 매개변수로 파일 객체 생성
	private File dirSave = new File("C:/images/Item");
	// 이미지 불러올 파일을 저장할 파일 객체 선언
	private File file = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		txt_I_ReceivingDate.setValue(LocalDate.now());
		/* txt_I_ForwardingDate.setValue(LocalDate.now()); */
		// 브랜드명콤보 값 설정
		cb_I_BrandName.setItems(FXCollections.observableArrayList("VEDIVERO", "TOMFORD", "BALENCIAGA"));

		tableView_ItemStock.setEditable(false);
		tableView_ReleaceStock2.setEditable(false);

		// 재고관리목록 뷰 컬럼이름 설정
		TableColumn colI_Number = new TableColumn("NO.");
		colI_Number.setMaxWidth(30);
		colI_Number.setStyle("-fx-allignment: CENTER");
		colI_Number.setCellValueFactory(new PropertyValueFactory<>("I_number"));

		TableColumn colI_BrandName = new TableColumn<>("브랜드명");
		colI_BrandName.setMaxWidth(100);
		colI_BrandName.setCellValueFactory(new PropertyValueFactory<>("I_BrandName"));

		TableColumn colI_SojipgiName = new TableColumn("소집기명");
		colI_SojipgiName.setMaxWidth(300);
		colI_SojipgiName.setStyle("-fx-allignment: CENTER");
		colI_SojipgiName.setCellValueFactory(new PropertyValueFactory<>("I_SojipgiName"));

		TableColumn colI_ModelName = new TableColumn("모델명");
		colI_ModelName.setMaxWidth(300);
		colI_ModelName.setStyle("-fx-allignment: CENTER");
		colI_ModelName.setCellValueFactory(new PropertyValueFactory<>("I_ModelName"));

		TableColumn colI_Storage = new TableColumn("보관장소");
		colI_Storage.setMaxWidth(180);
		colI_Storage.setStyle("-fx-allignment: CENTER");
		colI_Storage.setCellValueFactory(new PropertyValueFactory<>("I_Storage"));

		TableColumn colI_QuantityOfWarehoused = new TableColumn("현재수량");
		colI_QuantityOfWarehoused.setMaxWidth(110);
		colI_QuantityOfWarehoused.setStyle("-fx-allignment: CENTER");
		colI_QuantityOfWarehoused.setCellValueFactory(new PropertyValueFactory<>("I_QuantityOfWarehoused"));

		TableColumn colI_Price = new TableColumn("단  가");
		colI_Price.setMaxWidth(110);
		colI_Price.setStyle("-fx-allignment: CENTER");
		colI_Price.setCellValueFactory(new PropertyValueFactory<>("I_Price"));

		TableColumn colI_CompanyName = new TableColumn("입고처");
		colI_CompanyName.setMaxWidth(160);
		colI_CompanyName.setStyle("-fx-allignment: CENTER");
		colI_CompanyName.setCellValueFactory(new PropertyValueFactory<>("I_CompanyName"));

		TableColumn colI_ReceivingDate = new TableColumn("입고일");
		colI_ReceivingDate.setMaxWidth(160);
		colI_ReceivingDate.setStyle("-fx-allignment: CENTER");
		colI_ReceivingDate.setCellValueFactory(new PropertyValueFactory<>("I_ReceivingDate"));

		TableColumn colI_imageView = new TableColumn("이미지파일");
		colI_imageView.setMaxWidth(140);
		colI_imageView.setStyle("-fx-allignment: CENTER");
		colI_imageView.setCellValueFactory(new PropertyValueFactory<>("I_imageView"));

		// 입고 테이블
		tableView_ItemStock.getColumns().addAll(colI_Number, colI_BrandName, colI_SojipgiName, colI_ModelName,
				colI_Storage, colI_QuantityOfWarehoused, colI_Price, colI_CompanyName, colI_ReceivingDate,
				colI_imageView);

		// 출고 리스트 테이블 뷰 컬럼이름 설정
		TableColumn colI_Number2 = new TableColumn("NO.");
		colI_Number2.setMaxWidth(30);
		colI_Number2.setStyle("-fx-allignment: CENTER");
		colI_Number2.setCellValueFactory(new PropertyValueFactory<>("I_number"));

		TableColumn colI_BrandName2 = new TableColumn<>("브랜드명");
		colI_BrandName2.setMaxWidth(100);
		colI_BrandName2.setCellValueFactory(new PropertyValueFactory<>("I_BrandName"));

		TableColumn colI_SojipgiName2 = new TableColumn("소집기명");
		colI_SojipgiName2.setMaxWidth(300);
		colI_SojipgiName2.setStyle("-fx-allignment: CENTER");
		colI_SojipgiName2.setCellValueFactory(new PropertyValueFactory<>("I_SojipgiName"));

		TableColumn colI_ModelName2 = new TableColumn("모델명");
		colI_ModelName2.setMaxWidth(300);
		colI_ModelName2.setStyle("-fx-allignment: CENTER");
		colI_ModelName2.setCellValueFactory(new PropertyValueFactory<>("I_ModelName"));

		//
		TableColumn colI_StoreName = new TableColumn("매장명");
		colI_StoreName.setMaxWidth(300);
		colI_StoreName.setStyle("-fx-allignment: CENTER");
		colI_StoreName.setCellValueFactory(new PropertyValueFactory<>("I_StoreName"));

		//
		TableColumn colI_ForwardingQuantity = new TableColumn("출고수량");
		colI_ForwardingQuantity.setMaxWidth(300);
		colI_ForwardingQuantity.setStyle("-fx-allignment: CENTER");
		colI_ForwardingQuantity.setCellValueFactory(new PropertyValueFactory<>("I_ForwardingQuantity"));

		TableColumn colI_ForwardingDate = new TableColumn("출고일");
		colI_ForwardingDate.setMaxWidth(180);
		colI_ForwardingDate.setStyle("-fx-allignment: CENTER");
		colI_ForwardingDate.setCellValueFactory(new PropertyValueFactory<>("I_ForwardingDate"));

		// 출고 테이블
		tableView_ReleaceStock2.getColumns().addAll(colI_Number2, colI_BrandName2, colI_SojipgiName2, colI_ModelName2,
				colI_StoreName, colI_ForwardingQuantity, colI_ForwardingDate);

		// 매장 전체 정보
		totalList();
		tableView_ItemStock.setItems(data);
		totalList2();
		tableView_ReleaceStock2.setItems(data2);

		// 기본 이미지
		localUrl = "/images/image_I_View.png";
		localImage = new Image(localUrl, false);
		I_imageView.setImage(localImage);

		// 전체 리스트
		btn_I_TotalList.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					data.removeAll(data);
					// 매장 전체 정보
					totalList();
				} catch (Exception e) {

				}

			}
		});

		// 집기 전체리스트
		btn_I_TotalList2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					data2.removeAll(data2);
					// 매장 전체 정보
					totalList2();
				} catch (Exception e) {

				}

			}

		});
		// 마우스커서 이벤트
		btn_I_Add.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		// 집기 정보 저장
		btn_I_Add.setOnAction(event -> {

			try {
				// 기존 내용을 지우고
				data.removeAll(data);
				ItemVO iVo = null;
				ItemDAO iDao = new ItemDAO();
				File dirMake = new File(dirSave.getAbsolutePath());

				// 이미지 저장 폴더 생성
				if (!dirMake.exists()) {
					dirMake.mkdirs();
				}

				// 이미지 파일 저장
				String fileName = imageSave(selectedFile);

				// 집기 정보 저장
				if (event.getSource().equals(btn_I_Add)) {

					iVo = new ItemVO(cb_I_BrandName.getSelectionModel().getSelectedItem(), txt_I_SojipgiName.getText(),
							txt_I_ModelName.getText(), txt_I_Storage.getText(), txt_I_QuantityOfWarehoused.getText(),
							txt_I_Price.getText(), txt_I_CompanyName.getText(),
							txt_I_ReceivingDate.getValue().toString(), fileName);

					iDao = new ItemDAO();
					iDao.Itemregiste(iVo);

					if (iDao != null) {

						totalList();

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("매장 입력");
						alert.setHeaderText(txt_I_SojipgiName.getText() + "의 매장이 성공적으로 등록되었습니다.");
						alert.setContentText("다음매장을 입력하세요.");

						btn_I_ImageAdd.setDisable(true);

						// 기본 이미지

						// 기본 이미지
						localUrl = "/images/image_I_View.png";
						localImage = new Image(localUrl, false);
						I_imageView.setImage(localImage);

						alert.showAndWait();

						cb_I_BrandName.getSelectionModel().clearSelection();
						txt_I_SojipgiName.setEditable(true);
						txt_I_ModelName.setEditable(true);
						txt_I_Storage.setEditable(true);
						txt_I_QuantityOfWarehoused.setEditable(true);
						txt_I_Price.setEditable(true);
						txt_I_CompanyName.setEditable(true);
						txt_I_ReceivingDate.setEditable(true);

					}
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("집기 정보 입력");
				alert.setHeaderText("집기 정보를 정확히 입력하시오.");
				alert.setContentText("다음에는 주의하세요!");
				alert.showAndWait();
			}

		});

		// pdf파일버튼 이벤트

		cb_I_BrandName.setOnMouseClicked(event -> handlerCbIBrandNameAction(event));
		btn_I_ImageAdd.setOnAction(event -> handlerBtnsItemAddAction(event)); // 이미지 선택창
		btn_I_Exit.setOnAction(event -> handlerBtnExitAction(event)); // 로그아웃버튼
		btn_I_Back.setOnAction(event -> handlerBtnBackAction(event));
		btn_I_Delete.setOnAction(event -> handlerBtnDeleteAction(event)); // 삭제버튼
		btn_I_Search.setOnAction(event -> handlerBtnSearchAction(event)); // 집기검색기능
		btn_I_Search2.setOnAction(event -> handlerBtnSearchAction2(event));
		tableView_ItemStock.setOnMouseClicked(event -> handlerImageViewAction2(event));
		btn_I_Release.setOnAction(event -> handlerBtnReleaseAction(event)); // 집기출고버튼
		btn_I_return.setOnAction(event -> handlerBtnbtnreturnAction(event)); // 집기반납버튼
		btn_I_Warehousing.setOnAction(event -> handlerBtnWarehousingAction(event)); // 입고처리버튼
		btn_I_ReleaseOK.setOnAction(event -> handlerBtnReleaseOKAction(event)); // 출고확정버튼
		btnPDF.setOnAction(event -> handlerBtnPDFAction(event));
		// 저장경로버튼 이벤트
		btnSaveFileDir.setOnAction(event -> handlerBtnSaveFileDirAction(event));
		// 마우스커서 이벤트

		btn_I_ImageAdd.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_I_Exit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_I_Back.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_I_Delete.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_I_Search.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_I_Search2.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_I_Release.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_I_return.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_I_Warehousing.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_I_ReleaseOK.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());

	}

	/*
	 * public void setPrimaryStage(Stage primaryStage) { this.primaryStage =
	 * primaryStage; }
	 */

	public void handlerBtnPDFAction(ActionEvent event) {

		try {
			// pdf document 선언
			// (Rectangle pageSize,float marginLeft.float marginRight,top,bottom
			Document document = new Document(PageSize.A4, 0, 0, 30, 30);
			// pdf 파일 저장공간 선언 .pdf파일이 생성, 그후 스트림으로 저장
			String strReportPDFName = "customer_" + System.currentTimeMillis() + ".pdf";
			PdfWriter.getInstance(document, new FileOutputStream(txtSaveFileDir.getText() + "\\" + strReportPDFName));
			// document를 열어 pdf문서를 쓸수있도록
			document.open();
			// 한글지원폰트 설정
			BaseFont bf = BaseFont.createFont("font/MALGUN.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 8, Font.NORMAL);
			Font font2 = new Font(bf, 14, Font.BOLD);
			// 타이틀
			Paragraph title = new Paragraph("소집기출고LIST", font2);
			// 중간정렬
			title.setAlignment(Element.ALIGN_CENTER);
			// 문서에 추가
			document.add(title);
			document.add(new Paragraph("\r\n"));
			// 생성날짜
			LocalDate date1 = LocalDate.now();
			Paragraph writeDay = new Paragraph(date1.toString(), font);
			// 오른쪽 정렬
			writeDay.setAlignment(Element.ALIGN_RIGHT);
			// 문서에 추가
			document.add(writeDay);
			document.add(new Paragraph("\r\n"));

			// 생성자에 컬럼수를 준다.
			PdfPTable table = new PdfPTable(7);
			table.setWidths(new int[] { 50, 100, 100, 100, 120, 80, 150 });
			// 컬럼타이틀

			// 컬럼 타이틀
			PdfPCell header1 = new PdfPCell(new Paragraph("번호", font));
			PdfPCell header2 = new PdfPCell(new Paragraph("브랜드명", font));
			PdfPCell header3 = new PdfPCell(new Paragraph("소집기명", font));
			PdfPCell header4 = new PdfPCell(new Paragraph("모델명", font));
			PdfPCell header5 = new PdfPCell(new Paragraph("매장명", font));
			PdfPCell header6 = new PdfPCell(new Paragraph("출고수량", font));
			PdfPCell header7 = new PdfPCell(new Paragraph("출고일", font));

			// 가로정렬
			header1.setHorizontalAlignment(Element.ALIGN_CENTER);
			header2.setHorizontalAlignment(Element.ALIGN_CENTER);
			header3.setHorizontalAlignment(Element.ALIGN_CENTER);
			header4.setHorizontalAlignment(Element.ALIGN_CENTER);
			header5.setHorizontalAlignment(Element.ALIGN_CENTER);
			header6.setHorizontalAlignment(Element.ALIGN_CENTER);
			header7.setHorizontalAlignment(Element.ALIGN_CENTER);

			// 세로 정렬
			header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header4.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header5.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header6.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header7.setVerticalAlignment(Element.ALIGN_MIDDLE);

			// 테이블에 추가
			table.addCell(header1);
			table.addCell(header2);
			table.addCell(header3);
			table.addCell(header4);
			table.addCell(header5);
			table.addCell(header6);
			table.addCell(header7);

			// DB 연결 및 리스트 선택
			FurnitureDAO fDao = new FurnitureDAO();
			FurnitureVO fVo = new FurnitureVO();
			ArrayList<FurnitureVO> list;
			list = fDao.getFurnitureTotal();
			int rowCount = list.size();

			PdfPCell cell1 = null;
			PdfPCell cell2 = null;
			PdfPCell cell3 = null;
			PdfPCell cell4 = null;
			PdfPCell cell5 = null;
			PdfPCell cell6 = null;
			PdfPCell cell7 = null;

			for (int index = 0; index < rowCount; index++) {

				fVo = list.get(index);

				cell1 = new PdfPCell(new Paragraph(fVo.getI_number() + "", font));
				cell2 = new PdfPCell(new Paragraph(fVo.getI_BrandName(), font));
				cell3 = new PdfPCell(new Paragraph(fVo.getI_SojipgiName(), font));
				cell4 = new PdfPCell(new Paragraph(fVo.getI_ModelName(), font));
				cell5 = new PdfPCell(new Paragraph(fVo.getI_StoreName(), font));
				cell6 = new PdfPCell(new Paragraph(fVo.getI_ForwardingQuantity(), font));
				cell7 = new PdfPCell(new Paragraph(fVo.getI_ForwardingDate(), font));

				// 가로 정렬
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell7.setHorizontalAlignment(Element.ALIGN_CENTER);

				// 세로정렬
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);

				// 테이블에 셀 추가
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				table.addCell(cell5);
				table.addCell(cell6);
				table.addCell(cell7);

			}
			// 문서에 테이블 추가
			document.add(table);
			document.add(new Paragraph("\r\n"));
			Alert alert = new Alert(AlertType.INFORMATION);
			// 문서 닫기 쓰기 종료
			document.close();

			txtSaveFileDir.clear();
			btnPDF.setDisable(true);

			alert.setTitle("PDF 생성");
			alert.setHeaderText("대여자 목록 PDF파일 생성 성공!");
			alert.setContentText("대여자목록 PDF 파일!");
			alert.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlerBtnSaveFileDirAction(ActionEvent event) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		final File selectedDirectory = directoryChooser.showDialog(primaryStage2);

		if (selectedDirectory != null) {
			txtSaveFileDir.setText(selectedDirectory.getAbsolutePath());

			btnPDF.setDisable(false);
		}
	}

	// 메인메뉴가기버튼
	public void handlerBtnBackAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD 메인메뉴");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_I_Back.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}

	// 집기반납 버튼 (집기 반납 후 삭제까지)
	public void handlerBtnbtnreturnAction(ActionEvent event) {
		FurnitureVO fVo = null;
		FurnitureDAO fDao = null;
		fDao = new FurnitureDAO();

		selectItem2 = tableView_ReleaceStock2.getSelectionModel().getSelectedItems();
		try {
			String ForwardingQuantity; // 출고수량
			ForwardingQuantity = selectItem2.get(0).getI_ForwardingQuantity();

			String SojipgiName; // 집기이름
			SojipgiName = selectItem2.get(0).getI_SojipgiName();

			data.remove(selectedIndex);

			fVo = new FurnitureVO(ForwardingQuantity, SojipgiName);
			System.out.println(ForwardingQuantity);
			System.err.println(SojipgiName);
			fDao = new FurnitureDAO();
			fDao.getReturnUpdate(fVo, fVo.getI_SojipgiName());

			data.removeAll(data);
			totalList();

			// 테이블명 지정 후 테이블에있는 넘버를 불러와서 지운다!
			fDao.getItemReturn(tableView_ReleaceStock2.getSelectionModel().getSelectedItem().getI_number());
			data.removeAll(data);
			data2.removeAll(data2);
			// 매장 전체 정보
			totalList();
			totalList2();
			handlerBtnInitAction(event);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("출고 반납");
			alert.setHeaderText("출고 집기목록에서 삭제할 집기를 선택하시오.");
			alert.setContentText("다음에는 주의하세요!");
			alert.showAndWait();
		}
	}

	// 출고확정버튼
	public void handlerBtnReleaseOKAction(ActionEvent event) {
		FurnitureVO fVo = null;
		FurnitureDAO fDao = null;

		selectItem2 = tableView_ReleaceStock2.getSelectionModel().getSelectedItems();

		try {
			String ForwardingQuantity; // 출고수량
			ForwardingQuantity = selectItem2.get(0).getI_ForwardingQuantity();

			String SojipgiName; // 집기이름
			SojipgiName = selectItem2.get(0).getI_SojipgiName();

			data.remove(selectedIndex);

			fVo = new FurnitureVO(ForwardingQuantity, SojipgiName);
			System.out.println(ForwardingQuantity);
			System.err.println(SojipgiName);
			fDao = new FurnitureDAO();
			fDao.getReleaseOKUpdate(fVo, fVo.getI_SojipgiName());

			data.removeAll(data);
			totalList();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("집기 입고");
			alert.setHeaderText("집기목록에서 집기를 선택하시오.");
			alert.setContentText("다음에는 주의하세요!2222");
			alert.showAndWait();
		}
	}

	// 집기입고버튼
	public void handlerBtnWarehousingAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Warehousing.fxml"));

			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btn_I_Warehousing.getScene().getWindow());
			dialog.setTitle("집기 입고");

			Parent parentEdit = (Parent) loader.load();
			ItemVO ItemEdit2 = tableView_ItemStock.getSelectionModel().getSelectedItem();
			selectedIndex = tableView_ItemStock.getSelectionModel().getSelectedIndex();

			TextField editI_Number = (TextField) parentEdit.lookup("#I_number"); // 번호
			TextField edittxt_I_QuantityOfWarehoused = (TextField) parentEdit.lookup("#txt_I_QuantityOfWarehoused"); // 현재수량
			TextField edittxt_I_AdditionalQuantity = (TextField) parentEdit.lookup("#txt_I_AdditionalQuantity"); // 추가수량
			TextField edittxt_I_Total = (TextField) parentEdit.lookup("#txt_I_Total"); // 합계

			// 수정 불가능 지정
			editI_Number.setDisable(true);
			edittxt_I_QuantityOfWarehoused.setDisable(true);
			edittxt_I_Total.setDisable(true);

			// 데이터베이스 자료 불러오기

			editI_Number.setText(ItemEdit2.getI_number() + "");
			edittxt_I_QuantityOfWarehoused.setText(ItemEdit2.getI_QuantityOfWarehoused()); // 현재수량

			Button btn_I_Reckoning = (Button) parentEdit.lookup("#btn_I_Reckoning"); // 계산버튼
			Button btn_I_Warehousing2 = (Button) parentEdit.lookup("#btn_I_Warehousing2"); // 입고버튼
			Button btn_I_Exit4 = (Button) parentEdit.lookup("#btn_I_Exit4"); // 닫기버튼

			// 입고수량 계산
			btn_I_Reckoning.setOnAction(e -> {

				if (edittxt_I_AdditionalQuantity.getText().equals("")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("입고");
					alert.setHeaderText("입고 수량을 입력하세요.");
					alert.setContentText("0보다 큰 수를 입력하세요.");
					alert.showAndWait();
				} else {
					int total = Integer.parseInt(edittxt_I_QuantityOfWarehoused.getText())
							+ Integer.parseInt(edittxt_I_AdditionalQuantity.getText());
					edittxt_I_Total.setText(total + "");

					edittxt_I_AdditionalQuantity.setDisable(true);
					btn_I_Warehousing2.setDisable(false);
				}
			});

			btn_I_Warehousing2.setOnAction(e -> {
				ItemVO iVo = null;
				ItemDAO iDao2 = null;

				TextField I_number = (TextField) parentEdit.lookup("#I_number");
				TextField I_Total = (TextField) parentEdit.lookup("#txt_I_Total");

				// 저장된걸 수정된곳으로 이동 data.remove(selectedIndex);
				data.remove(selectedIndex);

				try {

					edittxt_I_QuantityOfWarehoused.setText(ItemEdit2.getI_QuantityOfWarehoused());

					iVo = new ItemVO(Integer.parseInt(I_number.getText()), I_Total.getText());

					iDao2 = new ItemDAO();
					iDao2.getWarehousingUpdate(iVo, iVo.getI_number());
					data.removeAll(data);

					totalList();

					dialog.close();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			});

			// 취소 버튼

			btn_I_Exit4.setOnAction(e -> {
				dialog.close();
			});

			Scene scene = new Scene(parentEdit);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("집기 입고");
			alert.setHeaderText("집기목록에서 집기를 선택하시오.");
			alert.setContentText("다음에는 주의하세요!2222");
			alert.showAndWait();
		}
	}

	// 집기출고버튼
	public void handlerBtnReleaseAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/ItemRelease.fxml"));
			Parent parentEdit = (Parent) loader.load();

			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btn_I_Release.getScene().getWindow());
			dialog.setTitle("집기 출고");

			ItemVO ItemEdit = tableView_ItemStock.getSelectionModel().getSelectedItem();
			selectedIndex = tableView_ItemStock.getSelectionModel().getSelectedIndex();

			TextField editI_Number = (TextField) parentEdit.lookup("#I_number");
			TextField editcb_I_BrandName = (TextField) parentEdit.lookup("#txt_I_BrandName");
			TextField edittxt_I_SojipgiName = (TextField) parentEdit.lookup("#txt_I_SojipgiName");
			TextField edittxt_I_ModelName = (TextField) parentEdit.lookup("#txt_I_ModelName");

			ComboBox<String> edittxt_I_StoreName = (ComboBox<String>) parentEdit.lookup("#txt_I_StoreName");
			TextField edittxt_I_ForwardingQuantity = (TextField) parentEdit.lookup("#txt_I_ForwardingQuantity");
			DatePicker editdp_I_ForwardingDate = (DatePicker) parentEdit.lookup("#txt_I_ReceivingDate");

			FurnitureDAO fDao = new FurnitureDAO();
			ArrayList<String> StoreName = fDao.getStroeName();
			edittxt_I_StoreName.setItems(FXCollections.observableArrayList(StoreName));

			// 수정 불가능 지정
			editI_Number.setDisable(true);
			editcb_I_BrandName.setDisable(true);
			edittxt_I_SojipgiName.setDisable(true);
			edittxt_I_ModelName.setDisable(true);

			// 데이터베이스 자료 불러오기

			editI_Number.setText(ItemEdit.getI_number() + "");
			editcb_I_BrandName.setText(ItemEdit.getI_BrandName());
			edittxt_I_SojipgiName.setText(ItemEdit.getI_SojipgiName());
			edittxt_I_ModelName.setText(ItemEdit.getI_ModelName());
			editdp_I_ForwardingDate.setValue(LocalDate.parse(ItemEdit.getI_ReceivingDate()));

			Button btn_I_Release = (Button) parentEdit.lookup("#btn_I_Release");
			Button btn_r_Exit = (Button) parentEdit.lookup("#btn_r_Exit");

			btn_I_Release.setOnAction(e -> {
				FurnitureVO fVo = null;
				FurnitureDAO fDao2 = null;

				editcb_I_BrandName.setText(ItemEdit.getI_BrandName());
				edittxt_I_SojipgiName.setText(ItemEdit.getI_SojipgiName());
				edittxt_I_ModelName.setText(ItemEdit.getI_ModelName());

				TextField txt_I_BrandName = (TextField) parentEdit.lookup("#txt_I_BrandName");
				TextField txt_I_SojipgiName = (TextField) parentEdit.lookup("#txt_I_SojipgiName");
				TextField txt_I_ModelName = (TextField) parentEdit.lookup("#txt_I_ModelName");

				ComboBox<String> txt_I_StoreName = (ComboBox<String>) parentEdit.lookup("#txt_I_StoreName");
				TextField txt_I_ForwardingQuantity = (TextField) parentEdit.lookup("#txt_I_ForwardingQuantity");
				DatePicker txt_I_ForwardingDate = (DatePicker) parentEdit.lookup("#txt_I_ReceivingDate");

				// 저장된걸 수정된곳으로 이동 data.remove(selectedIndex);
				data.remove(selectedIndex);

				try {

					fVo = new FurnitureVO(txt_I_BrandName.getText(), txt_I_SojipgiName.getText(),
							txt_I_ModelName.getText(), txt_I_StoreName.getSelectionModel().getSelectedItem(),
							txt_I_ForwardingQuantity.getText(), txt_I_ForwardingDate.getValue().toString());

					fDao2 = new FurnitureDAO();
					fDao2.getItemUpdate(fVo);

					data.removeAll(data);
					data2.removeAll(data2);
					totalList();
					totalList2();
					dialog.close();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// 기본 이미지
				localUrl = "/images/image_I_View.png";
				localImage = new Image(localUrl, false);
				I_imageView.setImage(localImage);

			});

			// 취소 버튼

			btn_r_Exit.setOnAction(e -> {
				dialog.close();
			});

			Scene scene = new Scene(parentEdit);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("집기 출고");
			alert.setHeaderText("집기목록에서 집기를 선택하시오.");
			alert.setContentText("다음에는 주의하세요!");
			alert.showAndWait();
		}
	}

	// 집기재고검색
	public void handlerBtnSearchAction(ActionEvent event) {
		ItemVO iVo = new ItemVO();
		ItemDAO iDao = null;

		Object[][] totalData2 = null;

		String searchName = "";
		boolean searchResult = false;

		try {
			searchName = txt_I_Search.getText().trim();
			iDao = new ItemDAO();
			iVo = iDao.getItemCheck(searchName);

			if (searchName.equals("")) {
				searchResult = true;
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 정보 검색");
				alert.setHeaderText("매장명을 입력해주세요.");
				alert.setContentText("다음에는 주의하세요.");
				alert.showAndWait();
			}

			if (!searchName.equals("") && (iVo != null)) {
				ArrayList<String> title;
				ArrayList<ItemVO> list;
				title = iDao.getColumnName();
				int columnCount = title.size();

				list = iDao.getItemTotal();
				int rowCount = list.size();

				totalData2 = new Object[rowCount][columnCount];

				if (iVo.getI_SojipgiName().equals(searchName)) {
					txt_I_Search.clear();
					data.removeAll(data);
					for (int index = 0; index < rowCount; index++) {
						System.out.println(index);
						iVo = list.get(index);
						if (iVo.getI_SojipgiName().equals(searchName)) {
							System.out.println(index);
							if (iVo.getI_SojipgiName().equals(searchName)) {
								data.add(iVo);
								searchResult = true;

							}
						}
					}
				}
				if (!searchResult) {
					txt_I_Search.clear();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("매장 정보 검색");
					alert.setHeaderText(searchName + "검색하신 매장명이 리스트에 없습니다.");
					alert.setContentText("다시 검색하세요.");
					alert.showAndWait();
				}
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("매장 정보 검색 오류");
			alert.setHeaderText("매장 정보 검색에 오류가 발생하였습니다.");
			alert.setContentText("다시 하세요.");
			alert.showAndWait();

		}
	}

	// 검색버튼
	public void handlerBtnSearchAction2(ActionEvent event) {
		FurnitureVO fVo = new FurnitureVO();
		FurnitureDAO fDao = null;

		Object[][] totalData3 = null;

		String searchName = "";
		boolean searchResult = false;

		try {
			searchName = txt_I_Search2.getText().trim();
			fDao = new FurnitureDAO();
			fVo = fDao.getItemCheck2(searchName);

			if (searchName.equals("")) {
				searchResult = true;
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 정보 검색");
				alert.setHeaderText("매장명을 입력해주세요.");
				alert.setContentText("다음에는 주의하세요.");
				alert.showAndWait();
			}

			if (!searchName.equals("") && (fVo != null)) {
				ArrayList<String> title;
				ArrayList<FurnitureVO> list3;
				title = fDao.getColumnName2();
				int columnCount = title.size();

				list3 = fDao.getFurnitureTotal();
				int rowCount = list3.size();

				totalData3 = new Object[rowCount][columnCount];

				if (fVo.getI_StoreName().equals(searchName)) {
					txt_I_Search2.clear();
					data2.removeAll(data2);
					for (int index = 0; index < rowCount; index++) {
						System.out.println(index);
						fVo = list3.get(index);
						if (fVo.getI_StoreName().equals(searchName)) {
							System.out.println(index);
							if (fVo.getI_StoreName().equals(searchName)) {
								data2.add(fVo);
								searchResult = true;

							}
						}
					}
				}
				if (!searchResult) {
					txt_I_Search.clear();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("매장 정보 검색");
					alert.setHeaderText(searchName + "검색하신 매장명이 리스트에 없습니다.");
					alert.setContentText("다시 검색하세요.");
					alert.showAndWait();
				}
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("매장 정보 검색 오류");
			alert.setHeaderText("매장 정보 검색에 오류가 발생하였습니다.");
			alert.setContentText("다시 하세요.");
			alert.showAndWait();

		}
	}

	// 이미지 보이기
	public void handlerImageViewAction2(MouseEvent event) {
		// 마우스 왼쪽 클릭

		if (event.getClickCount() != 2) {
			try {

				selectItem = tableView_ItemStock.getSelectionModel().getSelectedItems();
				I_number = selectItem.get(0).getI_number();
				selectFileName = selectItem.get(0).getI_imageView();
				localUrl = "file:/C:/images/Item/" + selectFileName;
				localImage = new Image(localUrl, false);

				I_imageView.setImage(localImage);
				I_imageView.setFitHeight(310);
				I_imageView.setFitWidth(369);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("이미지 선택");
				alert.setHeaderText("이미지 정보 선택에 오류가 발생하였습니다..");
				alert.setContentText("다시 선택 하세요.");
				alert.showAndWait();

			}
			return;
		}
	}

	// 브랜드명 클릭시 초기화
	public void handlerCbIBrandNameAction(MouseEvent event) {
		init();
	}

	// 집기 재고 삭제
	public void handlerBtnDeleteAction(ActionEvent event) {
		ItemDAO iDao = null;
		iDao = new ItemDAO();
		try {
			// 테이블명 지정 후 테이블에있는 넘버를 불러와서 지운다!
			iDao.getItemDelete(tableView_ItemStock.getSelectionModel().getSelectedItem().getI_number());
			data.removeAll(data);
			// 매장 전체 정보
			totalList();
			handlerBtnInitAction(event);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("집기 삭제");
			alert.setHeaderText("집기목록에서 삭제할 집기를 선택하시오.");
			alert.setContentText("다음에는 주의하세요!");
			alert.showAndWait();
		}
	}

	// 초기화
	public void handlerBtnInitAction(ActionEvent event) {
		init();

	}

	// 초기화 메소드
	private void init() {
		cb_I_BrandName.setDisable(false);
		cb_I_BrandName.getSelectionModel().clearSelection();
		txt_I_SojipgiName.clear();
		txt_I_SojipgiName.setEditable(true);
		txt_I_ModelName.clear();
		txt_I_ModelName.setEditable(true);
		txt_I_Storage.clear();
		txt_I_Storage.setEditable(true);
		txt_I_QuantityOfWarehoused.clear();
		txt_I_QuantityOfWarehoused.setEditable(true);
		txt_I_Price.clear();
		txt_I_Price.setEditable(true);
		txt_I_CompanyName.clear();
		txt_I_CompanyName.setEditable(true);

		btn_I_Add.setDisable(false);
		btn_I_Delete.setDisable(false);
		btn_I_Release.setDisable(false);
		btn_I_ImageAdd.setDisable(false);
		btn_I_Exit.setDisable(false);

		// 기본 이미지
		localUrl = "/images/image_I_View.png";
		localImage = new Image(localUrl, false);
		I_imageView.setImage(localImage);
	}

	// 로그아웃 버튼
	public void handlerBtnExitAction(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD 관리자 로그인");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_I_Exit.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}

	// 이미지 선택창
	public void handlerBtnsItemAddAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(primaryStage2);
			if (selectedFile != null) {
				// 이미지 파일 경로
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		localImage = new Image(localUrl, false);
		I_imageView.setImage(localImage);
		I_imageView.setFitHeight(310);
		I_imageView.setFitWidth(369);
		btn_I_Add.setDisable(false);

		if (selectedFile != null) {
			selectFileName = selectedFile.getName();
		}

	}

	// 재고관리
	private void totalList() {
		Object[][] totalData;

		ItemDAO iDao = new ItemDAO();
		ItemVO iVo = new ItemVO();
		ArrayList<String> title;
		ArrayList<ItemVO> list;

		title = iDao.getColumnName();
		int columnCount = title.size();

		list = iDao.getItemTotal();
		int rowCount = list.size();
		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			iVo = list.get(index);
			data.add(iVo);
		}
	}

	// 집기 출고 전체리스트
	private void totalList2() {
		Object[][] totalData2;

		FurnitureDAO fDao = new FurnitureDAO();
		FurnitureVO fVo = new FurnitureVO();
		ArrayList<String> title;
		ArrayList<FurnitureVO> list;

		title = fDao.getColumnName2();
		int columnCount = title.size();

		list = fDao.getFurnitureTotal();
		int rowCount = list.size();
		totalData2 = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			fVo = list.get(index);
			data2.add(fVo);
		}
	}

	// 이미지 저장
	public String imageSave(File file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		try {
			// 이미지 파일명 생성
			fileName = "Item" + System.currentTimeMillis() + "_" + file.getName();
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "\\" + fileName));

			// 선택한 이미지 파일 inputStream의 마지막에 이르렀을 경우는 -1
			while ((data = bis.read()) != -1) {
				bos.write(data);
				bos.flush();

			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.getMessage();
			}
		}
		return fileName;

	}
}