package controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.LcooperatorVO;
import model.StoreVO;

public class LcooperatorController implements Initializable {

	@FXML
	private TableView<LcooperatorVO> tableView_LcooperatorStock = new TableView<>();

	// 업체등록 : 텍스트필드
	@FXML
	private TextField txt_L_Line;
	@FXML
	private TextField txt_L_CompanyName;
	@FXML
	private TextField txt_L_CorporateNumber;
	@FXML
	private TextField txt_L_Address;
	@FXML
	private TextField txt_L_PhoneNumber;
	@FXML
	private TextField txt_L_FAXNumber;
	@FXML
	private TextField txt_L_WebHard;
	@FXML
	private TextField txt_L_Email;
	@FXML
	private TextField txt_L_Director;
	@FXML
	private TextField txt_L_Department;
	@FXML
	private TextField txt_L_CellPhoneNumber;
	@FXML
	private TextField txt_L_Director2;
	@FXML
	private TextField txt_L_Department2;
	@FXML
	private TextField txt_L_CellPhoneNumber2;
	@FXML
	private TextField txt_L_Director3;
	@FXML
	private TextField txt_L_Department3;
	@FXML
	private TextField txt_L_CellPhoneNumber3;
	@FXML
	private TextField txt_L_Director4;
	@FXML
	private TextField txt_L_Department4;
	@FXML
	private TextField txt_L_CellPhoneNumber4;
	@FXML
	private TextField txt_L_Director5;
	@FXML
	private TextField txt_L_Department5;
	@FXML
	private TextField txt_L_CellPhoneNumber5;

	// 버튼
	@FXML
	private Button btn_L_Exit;
	@FXML
	private Button btn_L_Add;
	@FXML
	private Button btn_L_Delete;
	@FXML
	private Button btn_L_TotalList;

	ObservableList<LcooperatorVO> data = FXCollections.observableArrayList();
	ObservableList<LcooperatorVO> selectLcooperator = null; // 테이블에서 선택한 정보 저장

	int selectedIndex; // 테이블에서 선택한 매장 정보 인덱스 저장
	int L_Number; // 삭제시 테이블에서 선택한 학생의 번호 저장

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tableView_LcooperatorStock.setEditable(false);

		// 업체관리목록 뷰 컬럼이름 설정
		TableColumn colL_Number = new TableColumn("NO.");
		colL_Number.setMaxWidth(30);
		colL_Number.setStyle("-fx-allignment: CENTER");
		colL_Number.setCellValueFactory(new PropertyValueFactory<>("L_Number"));

		TableColumn colL_Line = new TableColumn<>("업종");
		colL_Line.setMinWidth(100);
		colL_Line.setStyle("-fx-allignment: CENTER");
		colL_Line.setCellValueFactory(new PropertyValueFactory<>("L_Line"));

		TableColumn colL_CompanyName = new TableColumn("업체명");
		colL_CompanyName.setMinWidth(110);
		colL_CompanyName.setStyle("-fx-allignment: CENTER");
		colL_CompanyName.setCellValueFactory(new PropertyValueFactory<>("L_CompanyName"));

		TableColumn colL_CorporateNumber = new TableColumn("사업자등록번호");
		colL_CorporateNumber.setMinWidth(110);
		colL_CorporateNumber.setStyle("-fx-allignment: CENTER");
		colL_CorporateNumber.setCellValueFactory(new PropertyValueFactory<>("L_CorporateNumber"));

		TableColumn colL_Address = new TableColumn("주소");
		colL_Address.setMinWidth(110);
		colL_Address.setStyle("-fx-allignment: CENTER");
		colL_Address.setCellValueFactory(new PropertyValueFactory<>("L_Address"));

		TableColumn colL_PhoneNumber = new TableColumn("전화번호");
		colL_PhoneNumber.setMinWidth(110);
		colL_PhoneNumber.setStyle("-fx-allignment: CENTER");
		colL_PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("L_PhoneNumber"));

		TableColumn colL_FAXNumber = new TableColumn("팩스번호");
		colL_FAXNumber.setMinWidth(110);
		colL_FAXNumber.setStyle("-fx-allignment: CENTER");
		colL_FAXNumber.setCellValueFactory(new PropertyValueFactory<>("L_FAXNumber"));

		TableColumn colL_WebHard = new TableColumn("웹하드");
		colL_WebHard.setMinWidth(110);
		colL_WebHard.setStyle("-fx-allignment: CENTER");
		colL_WebHard.setCellValueFactory(new PropertyValueFactory<>("L_WebHard"));

		TableColumn colL_Email = new TableColumn("이메일");
		colL_Email.setMinWidth(110);
		colL_Email.setStyle("-fx-allignment: CENTER");
		colL_Email.setCellValueFactory(new PropertyValueFactory<>("L_Email"));

		TableColumn colL_Director = new TableColumn("담당자");
		colL_Director.setMinWidth(110);
		colL_Director.setStyle("-fx-allignment: CENTER");
		colL_Director.setCellValueFactory(new PropertyValueFactory<>("L_Director"));

		TableColumn colL_Department = new TableColumn("부서");
		colL_Department.setMinWidth(110);
		colL_Department.setStyle("-fx-allignment: CENTER");
		colL_Department.setCellValueFactory(new PropertyValueFactory<>("L_Department"));

		TableColumn colL_CellPhoneNumber = new TableColumn("휴대폰번호");
		colL_CellPhoneNumber.setMinWidth(110);
		colL_CellPhoneNumber.setStyle("-fx-allignment: CENTER");
		colL_CellPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("L_CellPhoneNumber"));

		TableColumn colL_Director2 = new TableColumn("담당자2");
		colL_Director2.setMinWidth(110);
		colL_Director2.setStyle("-fx-allignment: CENTER");
		colL_Director2.setCellValueFactory(new PropertyValueFactory<>("L_Director2"));

		TableColumn colL_Department2 = new TableColumn("부서");
		colL_Department2.setMinWidth(110);
		colL_Department2.setStyle("-fx-allignment: CENTER");
		colL_Department2.setCellValueFactory(new PropertyValueFactory<>("L_Department2"));

		TableColumn colL_CellPhoneNumber2 = new TableColumn("휴대폰번호");
		colL_CellPhoneNumber2.setMinWidth(110);
		colL_CellPhoneNumber2.setStyle("-fx-allignment: CENTER");
		colL_CellPhoneNumber2.setCellValueFactory(new PropertyValueFactory<>("L_CellPhoneNumber2"));

		TableColumn colL_Director3 = new TableColumn("담당자");
		colL_Director3.setMinWidth(110);
		colL_Director3.setStyle("-fx-allignment: CENTER");
		colL_Director3.setCellValueFactory(new PropertyValueFactory<>("L_Director3"));

		TableColumn colL_Department3 = new TableColumn("부서");
		colL_Department3.setMinWidth(110);
		colL_Department3.setStyle("-fx-allignment: CENTER");
		colL_Department3.setCellValueFactory(new PropertyValueFactory<>("L_Department3"));

		TableColumn colL_CellPhoneNumber3 = new TableColumn("휴대폰번호");
		colL_CellPhoneNumber3.setMinWidth(110);
		colL_CellPhoneNumber3.setStyle("-fx-allignment: CENTER");
		colL_CellPhoneNumber3.setCellValueFactory(new PropertyValueFactory<>("L_CellPhoneNumber3"));

		TableColumn colL_Director4 = new TableColumn("담당자");
		colL_Director4.setMinWidth(110);
		colL_Director4.setStyle("-fx-allignment: CENTER");
		colL_Director4.setCellValueFactory(new PropertyValueFactory<>("L_Director4"));

		TableColumn colL_Department4 = new TableColumn("부서");
		colL_Department4.setMinWidth(110);
		colL_Department4.setStyle("-fx-allignment: CENTER");
		colL_Department4.setCellValueFactory(new PropertyValueFactory<>("L_Department4"));

		TableColumn colL_CellPhoneNumber4 = new TableColumn("휴대폰번호");
		colL_CellPhoneNumber4.setMinWidth(110);
		colL_CellPhoneNumber4.setStyle("-fx-allignment: CENTER");
		colL_CellPhoneNumber4.setCellValueFactory(new PropertyValueFactory<>("L_CellPhoneNumber4"));

		TableColumn colL_Director5 = new TableColumn("담당자");
		colL_Director5.setMinWidth(110);
		colL_Director5.setStyle("-fx-allignment: CENTER");
		colL_Director5.setCellValueFactory(new PropertyValueFactory<>("L_Director5"));

		TableColumn colL_Department5 = new TableColumn("부서");
		colL_Department5.setMinWidth(110);
		colL_Department5.setStyle("-fx-allignment: CENTER");
		colL_Department5.setCellValueFactory(new PropertyValueFactory<>("L_Department5"));

		TableColumn colL_CellPhoneNumber5 = new TableColumn("휴대폰번호");
		colL_CellPhoneNumber5.setMinWidth(110);
		colL_CellPhoneNumber5.setStyle("-fx-allignment: CENTER");
		colL_CellPhoneNumber5.setCellValueFactory(new PropertyValueFactory<>("L_CellPhoneNumber5"));

		// 협력업체 테이블
		tableView_LcooperatorStock.getColumns().addAll(colL_Number, colL_Line, colL_CompanyName, colL_CorporateNumber,
				colL_Address, colL_PhoneNumber, colL_FAXNumber, colL_WebHard, colL_Email, colL_Director,
				colL_Department, colL_CellPhoneNumber, colL_Director2, colL_Department2, colL_CellPhoneNumber2,
				colL_Director3, colL_Department3, colL_CellPhoneNumber3, colL_Director4, colL_Department4,
				colL_CellPhoneNumber4, colL_Director5, colL_Department5, colL_CellPhoneNumber5);

		// 협력업체 정보

		tableView_LcooperatorStock.setItems(data);

		// 매장 전체 정보
		totalList();
		tableView_LcooperatorStock.setItems(data);

		// 전체 리스트
		btn_L_TotalList.setOnAction(new EventHandler<ActionEvent>() {

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
		// 마우스커서 이벤트
		btn_L_Add.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		// 집기 정보 저장
		btn_L_Add.setOnAction(event -> {

			try {
				// 기존 내용을 지우고
				data.removeAll(data);
				LcooperatorVO lVo = null;
				LcooperatorDAO lDao = new LcooperatorDAO();

				// 집기 정보 저장
				if (event.getSource().equals(btn_L_Add)) {

					lVo = new LcooperatorVO(txt_L_Line.getText(), txt_L_CompanyName.getText(),
							txt_L_CorporateNumber.getText(), txt_L_Address.getText(), txt_L_PhoneNumber.getText(),
							txt_L_FAXNumber.getText(), txt_L_WebHard.getText(), txt_L_Email.getText(),
							txt_L_Director.getText(), txt_L_Department.getText(), txt_L_CellPhoneNumber.getText(),
							txt_L_Director2.getText(), txt_L_Department2.getText(), txt_L_CellPhoneNumber2.getText(),
							txt_L_Director3.getText(), txt_L_Department3.getText(), txt_L_CellPhoneNumber3.getText(),
							txt_L_Director4.getText(), txt_L_Department4.getText(), txt_L_CellPhoneNumber4.getText(),
							txt_L_Director5.getText(), txt_L_Department5.getText(), txt_L_CellPhoneNumber5.getText());

					lDao = new LcooperatorDAO();
					lDao.LcooperatorRegiste(lVo);

					if (lDao != null) {

						totalList();

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("매장 입력");
						alert.setHeaderText(txt_L_CompanyName.getText() + "의 협력업체가 성공적으로 등록되었습니다.");
						alert.setContentText("다음협력업체이름을 입력하세요.");

						alert.showAndWait();
						init();
						txt_L_Line.setEditable(true);

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

		btn_L_Delete.setOnAction(event -> handlerBtnDeleteAction(event)); // 협력업체삭제
		txt_L_Line.setOnMouseClicked(event -> handlertxtLineAction(event)); // 업종 클릭시 리셋
		btn_L_Exit.setOnAction(event -> handlerBtnExitAction(event)); // 뒤로가기버튼

		// 마우스커서 이벤트
		btn_L_Delete.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		// 마우스커서 이벤트
		btn_L_Exit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());

		tableView_LcooperatorStock.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				selectLcooperator = tableView_LcooperatorStock.getSelectionModel().getSelectedItems();
				selectedIndex = tableView_LcooperatorStock.getSelectionModel().getSelectedIndex();

				try {
					txt_L_Line.setText(selectLcooperator.get(0).getL_Line());
					txt_L_CompanyName.setText(selectLcooperator.get(0).getL_CompanyName());
					txt_L_CorporateNumber.setText(selectLcooperator.get(0).getL_CorporateNumber());
					txt_L_Address.setText(selectLcooperator.get(0).getL_Address());
					txt_L_PhoneNumber.setText(selectLcooperator.get(0).getL_PhoneNumber());
					txt_L_FAXNumber.setText(selectLcooperator.get(0).getL_FAXNumber());
					txt_L_WebHard.setText(selectLcooperator.get(0).getL_WebHard());
					txt_L_Email.setText(selectLcooperator.get(0).getL_Email());
					txt_L_Director.setText(selectLcooperator.get(0).getL_Director());
					txt_L_Department.setText(selectLcooperator.get(0).getL_Department());
					txt_L_CellPhoneNumber.setText(selectLcooperator.get(0).getL_CellPhoneNumber());

					txt_L_Director2.setText(selectLcooperator.get(0).getL_Director2());
					txt_L_Department2.setText(selectLcooperator.get(0).getL_Department2());
					txt_L_CellPhoneNumber2.setText(selectLcooperator.get(0).getL_CellPhoneNumber2());

					txt_L_Director3.setText(selectLcooperator.get(0).getL_Director3());
					txt_L_Department3.setText(selectLcooperator.get(0).getL_Department3());
					txt_L_CellPhoneNumber3.setText(selectLcooperator.get(0).getL_CellPhoneNumber3());

					txt_L_Director4.setText(selectLcooperator.get(0).getL_Director4());
					txt_L_Department4.setText(selectLcooperator.get(0).getL_Department4());
					txt_L_CellPhoneNumber4.setText(selectLcooperator.get(0).getL_CellPhoneNumber4());

					txt_L_Director5.setText(selectLcooperator.get(0).getL_Director5());
					txt_L_Department5.setText(selectLcooperator.get(0).getL_Department5());
					txt_L_CellPhoneNumber5.setText(selectLcooperator.get(0).getL_CellPhoneNumber5());

					btn_L_Add.setDisable(true); // 사용 불가능
					btn_L_Delete.setDisable(false); // 사용가능

				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("학생 정보 수정 삭제");
					alert.setHeaderText("학생 정보를 입력하시오.");
					alert.setContentText("다음에는 주의하세요!");
					alert.showAndWait();
				}

			}

		});

	}

	public void handlerBtnExitAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD 메인메뉴");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_L_Exit.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}

	// 업종 클릭시 초기화
	public void handlertxtLineAction(MouseEvent event) {
		init();
	}

	// 매장 삭제
	public void handlerBtnDeleteAction(ActionEvent event) {
		LcooperatorDAO sDao = null;
		sDao = new LcooperatorDAO();
		try {
			// 테이블명 지정 후 테이블에있는 넘버를 불러와서 지운다!
			sDao.getLcooperatorDelete(tableView_LcooperatorStock.getSelectionModel().getSelectedItem().getL_Number());
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
		txt_L_Line.clear();
		txt_L_Line.setEditable(true);
		txt_L_CompanyName.clear();
		txt_L_CompanyName.setEditable(true);
		txt_L_CorporateNumber.clear();
		txt_L_CorporateNumber.setEditable(true);
		txt_L_Address.clear();
		txt_L_Address.setEditable(true);
		txt_L_PhoneNumber.clear();
		txt_L_PhoneNumber.setEditable(true);
		txt_L_FAXNumber.clear();
		txt_L_FAXNumber.setEditable(true);
		txt_L_WebHard.clear();
		txt_L_WebHard.setEditable(true);
		txt_L_Email.clear();
		txt_L_Email.setEditable(true);
		txt_L_Director.clear();
		txt_L_Director.setEditable(true);
		txt_L_Department.clear();
		txt_L_Department.setEditable(true);
		txt_L_CellPhoneNumber.clear();
		txt_L_CellPhoneNumber.setEditable(true);
		txt_L_Director2.clear();
		txt_L_Director2.setEditable(true);
		txt_L_Department2.clear();
		txt_L_Department2.setEditable(true);
		txt_L_CellPhoneNumber2.clear();
		txt_L_CellPhoneNumber2.setEditable(true);
		txt_L_Director3.clear();
		txt_L_Director3.setEditable(true);
		txt_L_Department3.clear();
		txt_L_Department3.setEditable(true);
		txt_L_CellPhoneNumber3.clear();
		txt_L_CellPhoneNumber3.setEditable(true);
		txt_L_Director4.clear();
		txt_L_Director4.setEditable(true);
		txt_L_Department4.clear();
		txt_L_Department4.setEditable(true);
		txt_L_CellPhoneNumber4.clear();
		txt_L_CellPhoneNumber4.setEditable(true);
		txt_L_Director5.clear();
		txt_L_Director5.setEditable(true);
		txt_L_Department5.clear();
		txt_L_Department5.setEditable(true);
		txt_L_CellPhoneNumber5.clear();
		txt_L_CellPhoneNumber5.setEditable(true);

		btn_L_Add.setDisable(false);
		btn_L_Delete.setDisable(false);
	}

	// 매장 전체 리스트
	private void totalList() {
		Object[][] totalData;

		LcooperatorDAO lDao = new LcooperatorDAO();
		LcooperatorVO lVo = new LcooperatorVO();
		ArrayList<String> title;
		ArrayList<LcooperatorVO> list;

		title = lDao.getColumnName();
		int columnCount = title.size();

		list = lDao.getLcooperatorTotal();
		int rowCount = list.size();
		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			lVo = list.get(index);
			data.add(lVo);
		}

	}

}
