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

	// ��ü��� : �ؽ�Ʈ�ʵ�
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

	// ��ư
	@FXML
	private Button btn_L_Exit;
	@FXML
	private Button btn_L_Add;
	@FXML
	private Button btn_L_Delete;
	@FXML
	private Button btn_L_TotalList;

	ObservableList<LcooperatorVO> data = FXCollections.observableArrayList();
	ObservableList<LcooperatorVO> selectLcooperator = null; // ���̺��� ������ ���� ����

	int selectedIndex; // ���̺��� ������ ���� ���� �ε��� ����
	int L_Number; // ������ ���̺��� ������ �л��� ��ȣ ����

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tableView_LcooperatorStock.setEditable(false);

		// ��ü������� �� �÷��̸� ����
		TableColumn colL_Number = new TableColumn("NO.");
		colL_Number.setMaxWidth(30);
		colL_Number.setStyle("-fx-allignment: CENTER");
		colL_Number.setCellValueFactory(new PropertyValueFactory<>("L_Number"));

		TableColumn colL_Line = new TableColumn<>("����");
		colL_Line.setMinWidth(100);
		colL_Line.setStyle("-fx-allignment: CENTER");
		colL_Line.setCellValueFactory(new PropertyValueFactory<>("L_Line"));

		TableColumn colL_CompanyName = new TableColumn("��ü��");
		colL_CompanyName.setMinWidth(110);
		colL_CompanyName.setStyle("-fx-allignment: CENTER");
		colL_CompanyName.setCellValueFactory(new PropertyValueFactory<>("L_CompanyName"));

		TableColumn colL_CorporateNumber = new TableColumn("����ڵ�Ϲ�ȣ");
		colL_CorporateNumber.setMinWidth(110);
		colL_CorporateNumber.setStyle("-fx-allignment: CENTER");
		colL_CorporateNumber.setCellValueFactory(new PropertyValueFactory<>("L_CorporateNumber"));

		TableColumn colL_Address = new TableColumn("�ּ�");
		colL_Address.setMinWidth(110);
		colL_Address.setStyle("-fx-allignment: CENTER");
		colL_Address.setCellValueFactory(new PropertyValueFactory<>("L_Address"));

		TableColumn colL_PhoneNumber = new TableColumn("��ȭ��ȣ");
		colL_PhoneNumber.setMinWidth(110);
		colL_PhoneNumber.setStyle("-fx-allignment: CENTER");
		colL_PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("L_PhoneNumber"));

		TableColumn colL_FAXNumber = new TableColumn("�ѽ���ȣ");
		colL_FAXNumber.setMinWidth(110);
		colL_FAXNumber.setStyle("-fx-allignment: CENTER");
		colL_FAXNumber.setCellValueFactory(new PropertyValueFactory<>("L_FAXNumber"));

		TableColumn colL_WebHard = new TableColumn("���ϵ�");
		colL_WebHard.setMinWidth(110);
		colL_WebHard.setStyle("-fx-allignment: CENTER");
		colL_WebHard.setCellValueFactory(new PropertyValueFactory<>("L_WebHard"));

		TableColumn colL_Email = new TableColumn("�̸���");
		colL_Email.setMinWidth(110);
		colL_Email.setStyle("-fx-allignment: CENTER");
		colL_Email.setCellValueFactory(new PropertyValueFactory<>("L_Email"));

		TableColumn colL_Director = new TableColumn("�����");
		colL_Director.setMinWidth(110);
		colL_Director.setStyle("-fx-allignment: CENTER");
		colL_Director.setCellValueFactory(new PropertyValueFactory<>("L_Director"));

		TableColumn colL_Department = new TableColumn("�μ�");
		colL_Department.setMinWidth(110);
		colL_Department.setStyle("-fx-allignment: CENTER");
		colL_Department.setCellValueFactory(new PropertyValueFactory<>("L_Department"));

		TableColumn colL_CellPhoneNumber = new TableColumn("�޴�����ȣ");
		colL_CellPhoneNumber.setMinWidth(110);
		colL_CellPhoneNumber.setStyle("-fx-allignment: CENTER");
		colL_CellPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("L_CellPhoneNumber"));

		TableColumn colL_Director2 = new TableColumn("�����2");
		colL_Director2.setMinWidth(110);
		colL_Director2.setStyle("-fx-allignment: CENTER");
		colL_Director2.setCellValueFactory(new PropertyValueFactory<>("L_Director2"));

		TableColumn colL_Department2 = new TableColumn("�μ�");
		colL_Department2.setMinWidth(110);
		colL_Department2.setStyle("-fx-allignment: CENTER");
		colL_Department2.setCellValueFactory(new PropertyValueFactory<>("L_Department2"));

		TableColumn colL_CellPhoneNumber2 = new TableColumn("�޴�����ȣ");
		colL_CellPhoneNumber2.setMinWidth(110);
		colL_CellPhoneNumber2.setStyle("-fx-allignment: CENTER");
		colL_CellPhoneNumber2.setCellValueFactory(new PropertyValueFactory<>("L_CellPhoneNumber2"));

		TableColumn colL_Director3 = new TableColumn("�����");
		colL_Director3.setMinWidth(110);
		colL_Director3.setStyle("-fx-allignment: CENTER");
		colL_Director3.setCellValueFactory(new PropertyValueFactory<>("L_Director3"));

		TableColumn colL_Department3 = new TableColumn("�μ�");
		colL_Department3.setMinWidth(110);
		colL_Department3.setStyle("-fx-allignment: CENTER");
		colL_Department3.setCellValueFactory(new PropertyValueFactory<>("L_Department3"));

		TableColumn colL_CellPhoneNumber3 = new TableColumn("�޴�����ȣ");
		colL_CellPhoneNumber3.setMinWidth(110);
		colL_CellPhoneNumber3.setStyle("-fx-allignment: CENTER");
		colL_CellPhoneNumber3.setCellValueFactory(new PropertyValueFactory<>("L_CellPhoneNumber3"));

		TableColumn colL_Director4 = new TableColumn("�����");
		colL_Director4.setMinWidth(110);
		colL_Director4.setStyle("-fx-allignment: CENTER");
		colL_Director4.setCellValueFactory(new PropertyValueFactory<>("L_Director4"));

		TableColumn colL_Department4 = new TableColumn("�μ�");
		colL_Department4.setMinWidth(110);
		colL_Department4.setStyle("-fx-allignment: CENTER");
		colL_Department4.setCellValueFactory(new PropertyValueFactory<>("L_Department4"));

		TableColumn colL_CellPhoneNumber4 = new TableColumn("�޴�����ȣ");
		colL_CellPhoneNumber4.setMinWidth(110);
		colL_CellPhoneNumber4.setStyle("-fx-allignment: CENTER");
		colL_CellPhoneNumber4.setCellValueFactory(new PropertyValueFactory<>("L_CellPhoneNumber4"));

		TableColumn colL_Director5 = new TableColumn("�����");
		colL_Director5.setMinWidth(110);
		colL_Director5.setStyle("-fx-allignment: CENTER");
		colL_Director5.setCellValueFactory(new PropertyValueFactory<>("L_Director5"));

		TableColumn colL_Department5 = new TableColumn("�μ�");
		colL_Department5.setMinWidth(110);
		colL_Department5.setStyle("-fx-allignment: CENTER");
		colL_Department5.setCellValueFactory(new PropertyValueFactory<>("L_Department5"));

		TableColumn colL_CellPhoneNumber5 = new TableColumn("�޴�����ȣ");
		colL_CellPhoneNumber5.setMinWidth(110);
		colL_CellPhoneNumber5.setStyle("-fx-allignment: CENTER");
		colL_CellPhoneNumber5.setCellValueFactory(new PropertyValueFactory<>("L_CellPhoneNumber5"));

		// ���¾�ü ���̺�
		tableView_LcooperatorStock.getColumns().addAll(colL_Number, colL_Line, colL_CompanyName, colL_CorporateNumber,
				colL_Address, colL_PhoneNumber, colL_FAXNumber, colL_WebHard, colL_Email, colL_Director,
				colL_Department, colL_CellPhoneNumber, colL_Director2, colL_Department2, colL_CellPhoneNumber2,
				colL_Director3, colL_Department3, colL_CellPhoneNumber3, colL_Director4, colL_Department4,
				colL_CellPhoneNumber4, colL_Director5, colL_Department5, colL_CellPhoneNumber5);

		// ���¾�ü ����

		tableView_LcooperatorStock.setItems(data);

		// ���� ��ü ����
		totalList();
		tableView_LcooperatorStock.setItems(data);

		// ��ü ����Ʈ
		btn_L_TotalList.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					data.removeAll(data);
					// ���� ��ü ����
					totalList();
				} catch (Exception e) {

				}

			}
		});
		// ���콺Ŀ�� �̺�Ʈ
		btn_L_Add.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		// ���� ���� ����
		btn_L_Add.setOnAction(event -> {

			try {
				// ���� ������ �����
				data.removeAll(data);
				LcooperatorVO lVo = null;
				LcooperatorDAO lDao = new LcooperatorDAO();

				// ���� ���� ����
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
						alert.setTitle("���� �Է�");
						alert.setHeaderText(txt_L_CompanyName.getText() + "�� ���¾�ü�� ���������� ��ϵǾ����ϴ�.");
						alert.setContentText("�������¾�ü�̸��� �Է��ϼ���.");

						alert.showAndWait();
						init();
						txt_L_Line.setEditable(true);

					}
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���� ���� �Է�");
				alert.setHeaderText("���� ������ ��Ȯ�� �Է��Ͻÿ�.");
				alert.setContentText("�������� �����ϼ���!");
				alert.showAndWait();
			}

		});

		btn_L_Delete.setOnAction(event -> handlerBtnDeleteAction(event)); // ���¾�ü����
		txt_L_Line.setOnMouseClicked(event -> handlertxtLineAction(event)); // ���� Ŭ���� ����
		btn_L_Exit.setOnAction(event -> handlerBtnExitAction(event)); // �ڷΰ����ư

		// ���콺Ŀ�� �̺�Ʈ
		btn_L_Delete.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		// ���콺Ŀ�� �̺�Ʈ
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

					btn_L_Add.setDisable(true); // ��� �Ұ���
					btn_L_Delete.setDisable(false); // ��밡��

				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("�л� ���� ���� ����");
					alert.setHeaderText("�л� ������ �Է��Ͻÿ�.");
					alert.setContentText("�������� �����ϼ���!");
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
			mainMtage.setTitle("VMD ���θ޴�");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_L_Exit.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("����" + e);
		}
	}

	// ���� Ŭ���� �ʱ�ȭ
	public void handlertxtLineAction(MouseEvent event) {
		init();
	}

	// ���� ����
	public void handlerBtnDeleteAction(ActionEvent event) {
		LcooperatorDAO sDao = null;
		sDao = new LcooperatorDAO();
		try {
			// ���̺�� ���� �� ���̺��ִ� �ѹ��� �ҷ��ͼ� �����!
			sDao.getLcooperatorDelete(tableView_LcooperatorStock.getSelectionModel().getSelectedItem().getL_Number());
			data.removeAll(data);
			// ���� ��ü ����
			totalList();
			handlerBtnInitAction(event);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("���� ����");
			alert.setHeaderText("�����Ͽ��� ������ ������ �����Ͻÿ�.");
			alert.setContentText("�������� �����ϼ���!");
			alert.showAndWait();
		}
	}

	// �ʱ�ȭ ��ư
	public void handlerBtnInitAction(ActionEvent event) {
		init();

	}

	// �ʱ�ȭ �޼ҵ�
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

	// ���� ��ü ����Ʈ
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
