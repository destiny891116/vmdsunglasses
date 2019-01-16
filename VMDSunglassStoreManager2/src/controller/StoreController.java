package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.StoreVO;

public class StoreController implements Initializable {

	@FXML
	private TableView<StoreVO> tableView_StoreStock = new TableView<>();

	// 매장등록 : 텍스트필드
	@FXML
	private TextField txt_s_StoreName; // 매장명
	@FXML
	private TextField txt_s_BrandName; // 브랜드명
	@FXML
	private TextField txt_s_StoreLocation; // 매장위치
	@FXML
	private TextField txt_s_Material; // 비쥬얼재질
	@FXML
	private TextField txt_s_VisualSize; // 비쥬얼사이즈
	@FXML
	private TextField txt_s_CompanyName; // 업체명
	@FXML
	private TextField txt_s_ConstructionAmount; // 공사금액
	@FXML
	private TextField txt_s_Search; // 매장검색

	// 매장등록 : 버튼
	@FXML
	private Button btn_s_Back;
	@FXML
	private Button btn_StoreImage;
	@FXML
	private Button btn_s_Search;
	@FXML
	private Button btn_s_Add;
	@FXML
	private Button btn_s_Edit;
	@FXML
	private Button btn_s_Delete;
	@FXML
	private Button btn_s_Exit;
	@FXML
	private Button btn_s_TotalList; // 전체리스트
	@FXML
	private Button btnInit; // 초기화 버튼 -> 만들어야해 FXML

	// 입점날짜 철수날짜 / 데이터픽커
	@FXML
	private DatePicker txt_s_LocationDate;
	@FXML
	private DatePicker txt_s_WithdrawalDate;


	// 매장구분 : 콤보박스
	@FXML
	private ComboBox<String> cb_s_DepartmentClassification;
	@FXML
	private ComboBox<String> cb_s_DepartmentClassification2;

	// 영상여부 : 라디오버튼
	@FXML
	private ToggleGroup genderGroup;
	@FXML
	private RadioButton rb_s_VideoOn;
	@FXML
	private RadioButton rb_s_VideoOff;

	// 매장이미지박스 : 앙카펜
	@FXML
	private AnchorPane imageBox;
	// 매장이미지파일 : 이미지뷰

	// 매장이미지파일 : 이미지뷰
	@FXML
	private ImageView imageView;
	// 매장이미지 : 버튼
	@FXML
	Button btn_s_StoreAdd;

	// PDF & EXCEL
	@FXML
	private Button btnSaveFileDir;
	@FXML
	private TextField txtSaveFileDir;
	@FXML
	private Button btnExcel;


	//
	ObservableList<StoreVO> data = FXCollections.observableArrayList();
	ObservableList<StoreVO> selectStore = null; // 테이블에서 선택한 정보 저장

	boolean editDelete = false; // 수정할 때 확인 버튼 상태 설정
	int selectedIndex; // 테이블에서 선택한 매장 정보 인덱스 저장

	private Stage primaryStage;
	String selectFileName = ""; // 이미지 파일명
	String localUrl = ""; // 이미지 파일 경로
	Image localImage;

	int s_number; // 삭제시 테이블에서 선택한 학생의 번호 저장
	File selectedFile = null;

	// 이미지 처리
	// 이미지 저장할 폴더를 매개변수로 파일 객체 생성
	private File dirSave = new File("C:/images");
	// 이미지 불러올 파일을 저장할 파일 객체 선언
	private File file = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// 입점날짜 철수날자 금일날짜 바로 실행
		txt_s_LocationDate.setValue(LocalDate.now());
		txt_s_WithdrawalDate.setValue(LocalDate.now());

		// 매장구분콤보 값 설정
		cb_s_DepartmentClassification.setItems(FXCollections.observableArrayList("백화점", "면세점", "아울렛", "해외매장", "행사"));
		// 매장구분콤보2 값 설정
		cb_s_DepartmentClassification2.setItems(FXCollections.observableArrayList("정상매장", "팝업매장", "팬사인회"));

		tableView_StoreStock.setEditable(false);

		// 테이블 뷰 컬럼이름 설정
		TableColumn cols_Number = new TableColumn("NO.");
		cols_Number.setMaxWidth(30);
		cols_Number.setStyle("-fx-allignment: CENTER");
		cols_Number.setCellValueFactory(new PropertyValueFactory<>("s_number"));

		TableColumn cols_DepartmentClassification = new TableColumn<>("매장구분");
		cols_DepartmentClassification.setMaxWidth(60);
		cols_DepartmentClassification.setCellValueFactory(new PropertyValueFactory<>("s_DepartmentClassification"));

		TableColumn cols_DepartmentClassification2 = new TableColumn<>("매장구분");
		cols_DepartmentClassification2.setMaxWidth(70);
		cols_DepartmentClassification2.setCellValueFactory(new PropertyValueFactory<>("s_DepartmentClassification2"));

		TableColumn cols_BrandName = new TableColumn("브랜드명");
		cols_BrandName.setMaxWidth(100);
		cols_BrandName.setStyle("-fx-allignment: CENTER");
		cols_BrandName.setCellValueFactory(new PropertyValueFactory<>("s_BrandName"));

		TableColumn cols_StoreName = new TableColumn("매장명");
		cols_StoreName.setMaxWidth(300);
		cols_StoreName.setStyle("-fx-allignment: CENTER");
		cols_StoreName.setCellValueFactory(new PropertyValueFactory<>("s_StoreName"));

		TableColumn cols_StoreLocation = new TableColumn("매장위치");
		cols_StoreLocation.setMaxWidth(100);
		cols_StoreLocation.setStyle("-fx-allignment: CENTER");
		cols_StoreLocation.setCellValueFactory(new PropertyValueFactory<>("s_StoreLocation"));

		TableColumn cols_LocationDate = new TableColumn("입점일");
		cols_LocationDate.setMaxWidth(110);
		cols_LocationDate.setStyle("-fx-allignment: CENTER");
		cols_LocationDate.setCellValueFactory(new PropertyValueFactory<>("s_LocationDate"));

		TableColumn cols_WithdrawalDate = new TableColumn("철수일");
		cols_WithdrawalDate.setMaxWidth(110);
		cols_WithdrawalDate.setStyle("-fx-allignment: CENTER");
		cols_WithdrawalDate.setCellValueFactory(new PropertyValueFactory<>("s_WithdrawalDate"));

		TableColumn cols_Material = new TableColumn("재질");
		cols_Material.setMaxWidth(100);
		cols_Material.setStyle("-fx-allignment: CENTER");
		cols_Material.setCellValueFactory(new PropertyValueFactory<>("s_Material"));

		TableColumn cols_VisualSize = new TableColumn("비쥬얼사이즈");
		cols_VisualSize.setMaxWidth(120);
		cols_VisualSize.setStyle("-fx-allignment: CENTER");
		cols_VisualSize.setCellValueFactory(new PropertyValueFactory<>("s_VisualSize"));

		TableColumn colGender = new TableColumn("영상여부");
		colGender.setMaxWidth(100);
		colGender.setStyle("-fx-allignment: CENTER");
		colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

		TableColumn cols_CompanyName = new TableColumn("업체명");
		cols_CompanyName.setMaxWidth(120);
		cols_CompanyName.setStyle("-fx-allignment: CENTER");
		cols_CompanyName.setCellValueFactory(new PropertyValueFactory<>("s_CompanyName"));

		TableColumn cols_ConstructionAmount = new TableColumn("공사금액");
		cols_ConstructionAmount.setMaxWidth(160);
		cols_ConstructionAmount.setStyle("-fx-allignment: CENTER");
		cols_ConstructionAmount.setCellValueFactory(new PropertyValueFactory<>("s_ConstructionAmount"));

		TableColumn colimageView = new TableColumn("이미지파일");
		colimageView.setMaxWidth(140);
		colimageView.setStyle("-fx-allignment: CENTER");
		colimageView.setCellValueFactory(new PropertyValueFactory<>("imageView"));

		tableView_StoreStock.getColumns().addAll(cols_Number, cols_DepartmentClassification,
				cols_DepartmentClassification2, cols_BrandName, cols_StoreName, cols_StoreLocation, cols_LocationDate,
				cols_WithdrawalDate, cols_Material, cols_VisualSize, colGender, cols_CompanyName,
				cols_ConstructionAmount, colimageView);

		// 매장 전체 정보
		totalList();

		tableView_StoreStock.setItems(data);

		// 기본 이미지
		localUrl = "/images/ImageView.png";
		localImage = new Image(localUrl, false);
		imageView.setImage(localImage);

		// 전체 리스트
		btn_s_TotalList.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					data.removeAll(data);
					// 매장 전체 정보
					totalList();
				} catch (Exception e) {

				}

			}
		});

		// 마우스커서 이벤트

		btn_s_Add.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());

		// 매장 정보 저장
		btn_s_Add.setOnAction(event -> {

			try {

				// 기존 내용을 지우고
				data.removeAll(data);
				StoreVO sVo = null;
				StoreDAO sDao = new StoreDAO();
				File dirMake = new File(dirSave.getAbsolutePath());

				// 이미지 저장 폴더 생성
				if (!dirMake.exists()) {
					dirMake.mkdirs();
				}

				// 이미지 파일 저장
				String fileName = imageSave(selectedFile);

				// 매장 정보 저장
				if (event.getSource().equals(btn_s_Add)) {

					sVo = new StoreVO(cb_s_DepartmentClassification.getSelectionModel().getSelectedItem(),
							cb_s_DepartmentClassification2.getSelectionModel().getSelectedItem(),
							txt_s_BrandName.getText(), txt_s_StoreName.getText(), txt_s_StoreLocation.getText(),
							txt_s_LocationDate.getValue().toString(), txt_s_WithdrawalDate.getValue().toString(),
							txt_s_Material.getText(), txt_s_VisualSize.getText(),
							genderGroup.getSelectedToggle().getUserData().toString(), txt_s_CompanyName.getText(),
							txt_s_ConstructionAmount.getText(), fileName);

					sDao = new StoreDAO();
					sDao.Storeregiste(sVo);

					if (sDao != null) {

						totalList();

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("매장 입력");
						alert.setHeaderText(txt_s_StoreName.getText() + "의 매장이 성공적으로 등록되었습니다.");
						alert.setContentText("다음매장을 입력하세요.");

						btn_s_StoreAdd.setDisable(true);

						// 기본 이미지

						localUrl = "/images/ImageView.png";
						localImage = new Image(localUrl, false);
						imageView.setImage(localImage);

						alert.showAndWait();

						cb_s_DepartmentClassification.getSelectionModel().clearSelection();
						cb_s_DepartmentClassification2.getSelectionModel().clearSelection();
						txt_s_BrandName.setEditable(true);
						txt_s_StoreName.setEditable(true);
						txt_s_StoreLocation.setEditable(true);
						txt_s_LocationDate.setEditable(true);
						txt_s_WithdrawalDate.setEditable(true);
						txt_s_Material.setEditable(true);
						txt_s_VisualSize.setEditable(true);
						txt_s_CompanyName.setEditable(true);
						txt_s_ConstructionAmount.setEditable(true);

					}
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 정보 입력");
				alert.setHeaderText("매장 정보를 정확히 입력하시오.");
				alert.setContentText("다음에는 주의하세요!");
				alert.showAndWait();

				totalList();
			}

		});
		cb_s_DepartmentClassification.setOnMouseClicked(event -> handlercbsDepartmentClassificationAction(event)); // 학생정보입력
		btn_s_StoreAdd.setOnAction(event -> handlerBtnsStoreAddAction(event)); // 이미지 선택창
		btn_s_Exit.setOnAction(event -> handlerBtnExitAction(event)); // 종료 버튼
		btn_s_Back.setOnAction(event -> handlerBtnBackAction(event)); // 메인메뉴 이동
		btn_s_Delete.setOnAction(event -> handlerBtnDeleteAction(event)); // 매장삭제버튼
		btn_s_Edit.setOnAction(event -> handlerBtnEditAction(event)); // 수정버튼
		tableView_StoreStock.setOnMouseClicked(event -> handlerImageViewAction(event)); // 테이블 매장목록 클릭시 사진 불러오기
		btn_s_Search.setOnAction(event -> handlerBtnSearchAction(event)); // 매장검색기능

		// 저장경로버튼 이벤트
		btnSaveFileDir.setOnAction(event -> handlerBtnSaveFileDirAction(event));
		// 엑셀파일버튼 이벤트
		btnExcel.setOnAction(event -> handlerBtnExcelAction(event));
		

		// 마우스커서 이벤트

		btn_s_StoreAdd.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_s_Exit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_s_Back.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_s_Delete.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_s_Edit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_s_Search.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());

	}
	
	// 엑셀파일버튼메소드
	public void handlerBtnExcelAction(ActionEvent event) {
		StoreDAO sDao = new StoreDAO();
		boolean saveSuccess = false;
		//
		ArrayList<StoreVO> list;
		list = sDao.getStoreTotal();
		StoreExcel excelWriter = new StoreExcel();

		// xlsx파일쓰기
		saveSuccess = excelWriter.xlsxWiter(list, txtSaveFileDir.getText());
		if (saveSuccess) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("엑셀 파일 생성");
			alert.setHeaderText("학생 목록 엑셀파일 생성성공");
			alert.setContentText("성공했답니다");
			alert.showAndWait();
		}

		//
		txtSaveFileDir.clear();
		btnExcel.setDisable(true);
		
	}// 엑셀파일버튼 끝
		// 저장경로버튼 메소드

	
	
	public void handlerBtnSaveFileDirAction(ActionEvent event) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		final File selectedDirectory = directoryChooser.showDialog(primaryStage);

		if (selectedDirectory != null) {
			txtSaveFileDir.setText(selectedDirectory.getAbsolutePath());
			btnExcel.setDisable(false);
			
		}
	}// 저장경로버튼 끝

	// 매장검색
	public void handlerBtnSearchAction(ActionEvent event) {

		StoreVO sVo = new StoreVO();
		StoreDAO sDao = null;

		Object[][] totalData = null;

		String searchName = "";
		boolean searchResult = false;

		try {
			searchName = txt_s_Search.getText().trim();
			sDao = new StoreDAO();
			sVo = sDao.getStoreCheck(searchName);

			if (searchName.equals("")) {
				searchResult = true;
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("매장 정보 검색");
				alert.setHeaderText("매장명을 입력해주세요.");
				alert.setContentText("다음에는 주의하세요.");
				alert.showAndWait();
			}

			if (!searchName.equals("") && (sVo != null)) {
				ArrayList<String> title;
				ArrayList<StoreVO> list;
				title = sDao.getColumnName();
				int columnCount = title.size();

				list = sDao.getStoreTotal();
				int rowCount = list.size();

				totalData = new Object[rowCount][columnCount];

				if (sVo.getS_StoreName().equals(searchName)) {
					txt_s_Search.clear();
					data.removeAll(data);
					for (int index = 0; index < rowCount; index++) {
						System.out.println(index);
						sVo = list.get(index);
						if (sVo.getS_StoreName().equals(searchName)) {
							System.out.println(index);
							if (sVo.getS_StoreName().equals(searchName)) {
								data.add(sVo);
								searchResult = true;

							}
						}
					}
				}
				if (!searchResult) {
					txt_s_Search.clear();
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

	// 테이블 클릭시 사진 보여주기
	public void handlerImageViewAction(MouseEvent event) {
		// 마우스 왼쪽 클릭

		if (event.getClickCount() != 2) {
			try {

				selectStore = tableView_StoreStock.getSelectionModel().getSelectedItems();
				s_number = selectStore.get(0).getS_number();
				selectFileName = selectStore.get(0).getImageView();
				localUrl = "file:/C:/images/" + selectFileName;
				localImage = new Image(localUrl, false);

				imageView.setImage(localImage);
				imageView.setFitHeight(310);
				imageView.setFitWidth(516);

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

	// 매장구분 콤보박스에 마우스 클릭시 초기화
	public void handlercbsDepartmentClassificationAction(MouseEvent event) {
		init();

	}

	// 매장 수정
	public void handlerBtnEditAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/StoreEdit.fxml"));

			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btn_s_Add.getScene().getWindow());
			dialog.setTitle("수정");

			Parent parentEdit = (Parent) loader.load();
			StoreVO storeEdit = tableView_StoreStock.getSelectionModel().getSelectedItem();
			selectedIndex = tableView_StoreStock.getSelectionModel().getSelectedIndex();
			System.out.println(selectedIndex);

			TextField editNumber = (TextField) parentEdit.lookup("#s_number");
			TextField editcb_s_DepartmentClassification = (TextField) parentEdit
					.lookup("#cb_s_DepartmentClassification");
			TextField editcb_s_DepartmentClassification2 = (TextField) parentEdit
					.lookup("#cb_s_DepartmentClassification2");
			TextField edittxt_s_BrandName = (TextField) parentEdit.lookup("#txt_s_BrandName");
			TextField edittxt_s_StoreName = (TextField) parentEdit.lookup("#txt_s_StoreName");
			TextField edittxt_s_StoreLocation = (TextField) parentEdit.lookup("#txt_s_StoreLocation");
			DatePicker edittxt_s_LocationDate = (DatePicker) parentEdit.lookup("#txt_LocationDate");
			DatePicker edittxt_s_WithdrawalDate = (DatePicker) parentEdit.lookup("#txt_WithdrawalDate");
			TextField edittxt_s_Material = (TextField) parentEdit.lookup("#txt_s_Material");
			TextField edittxt_s_VisualSize = (TextField) parentEdit.lookup("#txt_s_VisualSize");
			TextField edittxt_s_Gender = (TextField) parentEdit.lookup("#txt_s_Gender");
			TextField edittxt_s_CompanyName = (TextField) parentEdit.lookup("#txt_s_CompanyName");
			TextField edittxt_s_ConstructionAmount = (TextField) parentEdit.lookup("#txt_s_ConstructionAmount");
			TextField editimageView = (TextField) parentEdit.lookup("#imageView");

			// 수정 불가능 지정
			editNumber.setDisable(true);
			editcb_s_DepartmentClassification.setDisable(true);
			editcb_s_DepartmentClassification2.setDisable(true);
			edittxt_s_BrandName.setDisable(true);
			edittxt_s_StoreName.setDisable(true);
			edittxt_s_CompanyName.setDisable(true);
			edittxt_s_ConstructionAmount.setDisable(true);

			// DAO통해 데이터베이스 자료 불러오기!!!
			editNumber.setText(storeEdit.getS_number() + "");
			editcb_s_DepartmentClassification.setText(storeEdit.getS_DepartmentClassification());
			editcb_s_DepartmentClassification2.setText(storeEdit.getS_DepartmentClassification2());
			edittxt_s_BrandName.setText(storeEdit.getS_BrandName());
			edittxt_s_StoreName.setText(storeEdit.getS_StoreName());
			edittxt_s_StoreLocation.setText(storeEdit.getS_StoreLocation());
			edittxt_s_LocationDate.setValue(LocalDate.parse(storeEdit.getS_LocationDate()));
			edittxt_s_WithdrawalDate.setValue(LocalDate.parse(storeEdit.getS_WithdrawalDate()));
			edittxt_s_Material.setText(storeEdit.getS_Material());
			edittxt_s_VisualSize.setText(storeEdit.getS_VisualSize());
			edittxt_s_Gender.setText(storeEdit.getGender());
			edittxt_s_CompanyName.setText(storeEdit.getS_CompanyName());
			edittxt_s_ConstructionAmount.setText(storeEdit.getS_ConstructionAmount());
			editimageView.setText(storeEdit.getImageView());

			Button btn_s_Edit = (Button) parentEdit.lookup("#btn_s_Edit");
			Button btn_s_Exit = (Button) parentEdit.lookup("#btn_s_Exit");
			Button btn_StoreImage = (Button) parentEdit.lookup("#btn_StoreImage");

			btn_StoreImage.setOnAction(e -> {

				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
				try {
					selectedFile = fileChooser.showOpenDialog(primaryStage);
					if (selectedFile != null) {
						// 이미지 파일 경로
						localUrl = selectedFile.toURI().toURL().toString();
					}
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				localImage = new Image(localUrl, false);
				imageView.setImage(localImage);
				imageView.setFitHeight(310);
				imageView.setFitWidth(516);
				btn_s_Add.setDisable(false);

				if (selectedFile != null) {
					selectFileName = selectedFile.getName();
				}

			});

			btn_s_Edit.setOnAction(e -> {
				StoreVO sVo = null;
				StoreDAO sDao = null;

				TextField s_Number = (TextField) parentEdit.lookup("#s_number");
				TextField cb_s_DepartmentClassification = (TextField) parentEdit
						.lookup("#cb_s_DepartmentClassification");
				TextField cb_s_DepartmentClassification2 = (TextField) parentEdit
						.lookup("#cb_s_DepartmentClassification2");
				TextField txt_s_BrandName = (TextField) parentEdit.lookup("#txt_s_BrandName");
				TextField txt_s_StoreName = (TextField) parentEdit.lookup("#txt_s_StoreName");
				TextField txt_s_StoreLocation = (TextField) parentEdit.lookup("#txt_s_StoreLocation");
				DatePicker txt_LocationDate = (DatePicker) parentEdit.lookup("#txt_LocationDate");
				DatePicker txt_WithdrawalDate = (DatePicker) parentEdit.lookup("#txt_WithdrawalDate");
				TextField txt_s_Material = (TextField) parentEdit.lookup("#txt_s_Material");
				TextField txt_s_VisualSize = (TextField) parentEdit.lookup("#txt_s_VisualSize");
				TextField txt_s_Gender = (TextField) parentEdit.lookup("#txt_s_Gender");
				TextField txt_s_CompanyName = (TextField) parentEdit.lookup("#txt_s_CompanyName");
				TextField txt_s_ConstructionAmount = (TextField) parentEdit.lookup("#txt_s_ConstructionAmount");
				TextField imageView = (TextField) parentEdit.lookup("#imageView");
				// 저장된걸 수정된곳으로 이동
				data.remove(selectedIndex);

				AnchorPane imageBox2;

				try {

					File dirMake = new File(dirSave.getAbsolutePath());

					// 이미지 저장 폴더 생성
					if (!dirMake.exists()) {
						dirMake.mkdirs();
					}

					// 이미지 파일 저장
					String fileName = imageSave(selectedFile);

					sVo = new StoreVO(Integer.parseInt(s_Number.getText()), cb_s_DepartmentClassification.getText(),
							cb_s_DepartmentClassification2.getText(), txt_s_BrandName.getText(),
							txt_s_StoreName.getText(), txt_s_StoreLocation.getText(),
							txt_LocationDate.getValue().toString(), txt_WithdrawalDate.getValue().toString(),
							txt_s_Material.getText(), txt_s_VisualSize.getText(), txt_s_Gender.getText(),
							txt_s_CompanyName.getText(), txt_s_ConstructionAmount.getText(), fileName);
					sDao = new StoreDAO();
					sDao.getStoreUpdate(sVo, sVo.getS_number());
					data.removeAll(data);
					totalList();
					dialog.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			});

			// 취소 버튼
			btn_s_Exit.setOnAction(e -> {
				dialog.close();
			});

			Scene scene = new Scene(parentEdit);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();

		} catch (

		IOException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("매장 수정");
			alert.setHeaderText("매장목록에서 매장을 선택하시오.");
			alert.setContentText("다음에는 주의하세요!");
			alert.showAndWait();
		}
	}

	// 매장 삭제
	public void handlerBtnDeleteAction(ActionEvent event) {
		StoreDAO sDao = null;
		sDao = new StoreDAO();
		try {
			// 테이블명 지정 후 테이블에있는 넘버를 불러와서 지운다!
			sDao.getStoreDelete(tableView_StoreStock.getSelectionModel().getSelectedItem().getS_number());
			data.removeAll(data);
			// 매장 전체 정보
			totalList();
			handlerBtnInitAction(event);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("매장 삭제");
			alert.setHeaderText("매장목록에서 삭제할 매장을 선택하시오.");
			alert.setContentText("다음에는 주의하세요!");
			alert.showAndWait();
		}
	}

	// 초기화 버튼
	public void handlerBtnInitAction(ActionEvent event) {
		init();
	}

	// 초기화 메소드
	public void init() {
		cb_s_DepartmentClassification.setDisable(false);
		cb_s_DepartmentClassification.getSelectionModel().clearSelection();
		cb_s_DepartmentClassification2.setDisable(false);
		cb_s_DepartmentClassification2.getSelectionModel().clearSelection();
		txt_s_BrandName.clear();
		txt_s_BrandName.setEditable(true);
		txt_s_StoreName.clear();
		txt_s_StoreName.setEditable(true);
		txt_s_StoreLocation.clear();
		txt_s_StoreLocation.setEditable(true);
		txt_s_Material.clear();
		txt_s_Material.setEditable(true);
		txt_s_VisualSize.clear();
		txt_s_VisualSize.setEditable(true);
		rb_s_VideoOn.setDisable(false);
		rb_s_VideoOff.setDisable(false);
		genderGroup.selectToggle(null);
		txt_s_CompanyName.clear();
		txt_s_CompanyName.setDisable(false);
		txt_s_ConstructionAmount.clear();
		txt_s_ConstructionAmount.setDisable(false);
		btn_s_Add.setDisable(false);
		btn_s_Delete.setDisable(false);
		btn_s_StoreAdd.setDisable(false);
		btn_s_Edit.setDisable(false);
		btn_s_StoreAdd.setDisable(false); // 수정 가능

		// 기본 이미지

		localUrl = "/images/ImageView.png";
		localImage = new Image(localUrl, false);
		imageView.setImage(localImage);
	}

	// 메인메뉴로 이동
	public void handlerBtnBackAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD 메인메뉴");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_s_Back.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
		}
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
			Stage oldStage = (Stage) btn_s_Exit.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}

	// 이미지 선택창 (등록시)
	public void handlerBtnsStoreAddAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(primaryStage);
			if (selectedFile != null) {
				// 이미지 파일 경로
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		localImage = new Image(localUrl, false);
		imageView.setImage(localImage);
		imageView.setFitHeight(310);
		imageView.setFitWidth(516);
		btn_s_Add.setDisable(false);

		if (selectedFile != null) {
			selectFileName = selectedFile.getName();
		}

	}

	// 매장 전체 리스트
	private void totalList() {
		Object[][] totalData;

		StoreDAO sDao = new StoreDAO();
		StoreVO sVo = new StoreVO();
		ArrayList<String> title;
		ArrayList<StoreVO> list;

		title = sDao.getColumnName();
		int columnCount = title.size();

		list = sDao.getStoreTotal();
		int rowCount = list.size();
		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			sVo = list.get(index);
			data.add(sVo);
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
			fileName = "Store" + System.currentTimeMillis() + "_" + file.getName();
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
