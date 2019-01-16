package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.JoinVO;

public class VMDController implements Initializable {

	@FXML
	private TableView<JoinVO> tableView_VMDStock = new TableView<>(); // VMD관리자 테이블
	ObservableList<JoinVO> data = FXCollections.observableArrayList();

	@FXML
	private Button btn_V_Exit;
	@FXML
	private Button btn_V_Edit;
	@FXML
	private Button btn_V_Delete;

	/*
	 * edittxt_I_StoreName.setItems(FXCollections.observableArrayList(StoreName));
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/* ObservableList<JoinVO> data = FXCollections.observableArrayList(); */

		tableView_VMDStock.setEditable(false);

		// VMD 관리자 테이블뷰 컬럼이름 설정
		// 재고관리목록 뷰 컬럼이름 설정
		TableColumn colId = new TableColumn("아이디");
		colId.setMinWidth(120);
		colId.setStyle("-fx-allignment: CENTER");
		colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		// 재고관리목록 뷰 컬럼이름 설정
		TableColumn colPassword = new TableColumn("패스워드");
		colPassword.setMinWidth(120);
		colPassword.setStyle("-fx-allignment: CENTER");
		colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
		// 재고관리목록 뷰 컬럼이름 설정
		TableColumn colName = new TableColumn("이름");
		colName.setMinWidth(80);
		colName.setStyle("-fx-allignment: CENTER");
		colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		// 재고관리목록 뷰 컬럼이름 설정
		TableColumn colPosition = new TableColumn("직급");
		colPosition.setMinWidth(80);
		colPosition.setStyle("-fx-allignment: CENTER");
		colPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
		// 재고관리목록 뷰 컬럼이름 설정
		TableColumn colPhonenumber = new TableColumn("휴대폰번호");
		colPhonenumber.setMinWidth(150);
		colPhonenumber.setStyle("-fx-allignment: CENTER");
		colPhonenumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));

		tableView_VMDStock.getColumns().addAll(colId, colPassword, colName, colPosition, colPhonenumber);

		VMDDAO vDao = new VMDDAO();
		ArrayList<JoinVO> VMDlist = vDao.getVMD();
		tableView_VMDStock.setItems(FXCollections.observableArrayList(VMDlist));

		btn_V_Exit.setOnAction(event -> HandlerExitAction(event));
		btn_V_Delete.setOnAction(event -> HandlerDeleteAction(event));

		// 마우스커서 이벤트
		btn_V_Exit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		// 마우스커서 이벤트
		btn_V_Delete.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());

	}

	// 관리자 삭제
	public void HandlerDeleteAction(ActionEvent event) {
		VMDDAO vDao = null;
		vDao = new VMDDAO();
		try {
			// 테이블명 지정 후 테이블에있는 넘버를 불러와서 지운다!
			vDao.getVMDDelete(tableView_VMDStock.getSelectionModel().getSelectedItem().getId());
			/*
			 * data.removeAll(data); // 매장 전체 정보 totalList();
			 */
			data.removeAll(data);
			// 매장 전체 정보
			totalList();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("집기 삭제");
			alert.setHeaderText("집기목록에서 삭제할 집기를 선택하시오.");
			alert.setContentText("다음에는 주의하세요!");
			alert.showAndWait();
		}
	}

	// 메인메뉴로 이동
	public void HandlerExitAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD 관리자 로그인");
			mainMtage.setScene(scane);

			Stage oldStage = (Stage) btn_V_Exit.getScene().getWindow();
			oldStage.close();
			mainMtage.show();

		} catch (IOException e) {
			System.out.println("오류" + e);
		}

	}

	// 관리자 리스트
	private void totalList() {
		Object[][] totalData;

		VMDDAO vDao = new VMDDAO();
		JoinVO jVo = new JoinVO();
		ArrayList<String> title;
		ArrayList<JoinVO> list;

		title = vDao.getColumnName();
		int columnCount = title.size();

		list = vDao.getVMDTotal();
		int rowCount = list.size();
		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			jVo = list.get(index);
			data.add(jVo);
		}
	}
}
