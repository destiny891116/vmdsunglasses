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
	private TableView<JoinVO> tableView_VMDStock = new TableView<>(); // VMD������ ���̺�
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

		// VMD ������ ���̺�� �÷��̸� ����
		// ��������� �� �÷��̸� ����
		TableColumn colId = new TableColumn("���̵�");
		colId.setMinWidth(120);
		colId.setStyle("-fx-allignment: CENTER");
		colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		// ��������� �� �÷��̸� ����
		TableColumn colPassword = new TableColumn("�н�����");
		colPassword.setMinWidth(120);
		colPassword.setStyle("-fx-allignment: CENTER");
		colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
		// ��������� �� �÷��̸� ����
		TableColumn colName = new TableColumn("�̸�");
		colName.setMinWidth(80);
		colName.setStyle("-fx-allignment: CENTER");
		colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		// ��������� �� �÷��̸� ����
		TableColumn colPosition = new TableColumn("����");
		colPosition.setMinWidth(80);
		colPosition.setStyle("-fx-allignment: CENTER");
		colPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
		// ��������� �� �÷��̸� ����
		TableColumn colPhonenumber = new TableColumn("�޴�����ȣ");
		colPhonenumber.setMinWidth(150);
		colPhonenumber.setStyle("-fx-allignment: CENTER");
		colPhonenumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));

		tableView_VMDStock.getColumns().addAll(colId, colPassword, colName, colPosition, colPhonenumber);

		VMDDAO vDao = new VMDDAO();
		ArrayList<JoinVO> VMDlist = vDao.getVMD();
		tableView_VMDStock.setItems(FXCollections.observableArrayList(VMDlist));

		btn_V_Exit.setOnAction(event -> HandlerExitAction(event));
		btn_V_Delete.setOnAction(event -> HandlerDeleteAction(event));

		// ���콺Ŀ�� �̺�Ʈ
		btn_V_Exit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		// ���콺Ŀ�� �̺�Ʈ
		btn_V_Delete.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());

	}

	// ������ ����
	public void HandlerDeleteAction(ActionEvent event) {
		VMDDAO vDao = null;
		vDao = new VMDDAO();
		try {
			// ���̺�� ���� �� ���̺��ִ� �ѹ��� �ҷ��ͼ� �����!
			vDao.getVMDDelete(tableView_VMDStock.getSelectionModel().getSelectedItem().getId());
			/*
			 * data.removeAll(data); // ���� ��ü ���� totalList();
			 */
			data.removeAll(data);
			// ���� ��ü ����
			totalList();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("���� ����");
			alert.setHeaderText("�����Ͽ��� ������ ���⸦ �����Ͻÿ�.");
			alert.setContentText("�������� �����ϼ���!");
			alert.showAndWait();
		}
	}

	// ���θ޴��� �̵�
	public void HandlerExitAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD ������ �α���");
			mainMtage.setScene(scane);

			Stage oldStage = (Stage) btn_V_Exit.getScene().getWindow();
			oldStage.close();
			mainMtage.show();

		} catch (IOException e) {
			System.out.println("����" + e);
		}

	}

	// ������ ����Ʈ
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
