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

	// ������ : �ؽ�Ʈ�ʵ�
	@FXML
	private TextField txt_s_StoreName; // �����
	@FXML
	private TextField txt_s_BrandName; // �귣���
	@FXML
	private TextField txt_s_StoreLocation; // ������ġ
	@FXML
	private TextField txt_s_Material; // ���������
	@FXML
	private TextField txt_s_VisualSize; // ����������
	@FXML
	private TextField txt_s_CompanyName; // ��ü��
	@FXML
	private TextField txt_s_ConstructionAmount; // ����ݾ�
	@FXML
	private TextField txt_s_Search; // ����˻�

	// ������ : ��ư
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
	private Button btn_s_TotalList; // ��ü����Ʈ
	@FXML
	private Button btnInit; // �ʱ�ȭ ��ư -> �������� FXML

	// ������¥ ö����¥ / ��������Ŀ
	@FXML
	private DatePicker txt_s_LocationDate;
	@FXML
	private DatePicker txt_s_WithdrawalDate;


	// ���屸�� : �޺��ڽ�
	@FXML
	private ComboBox<String> cb_s_DepartmentClassification;
	@FXML
	private ComboBox<String> cb_s_DepartmentClassification2;

	// ���󿩺� : ������ư
	@FXML
	private ToggleGroup genderGroup;
	@FXML
	private RadioButton rb_s_VideoOn;
	@FXML
	private RadioButton rb_s_VideoOff;

	// �����̹����ڽ� : ��ī��
	@FXML
	private AnchorPane imageBox;
	// �����̹������� : �̹�����

	// �����̹������� : �̹�����
	@FXML
	private ImageView imageView;
	// �����̹��� : ��ư
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
	ObservableList<StoreVO> selectStore = null; // ���̺��� ������ ���� ����

	boolean editDelete = false; // ������ �� Ȯ�� ��ư ���� ����
	int selectedIndex; // ���̺��� ������ ���� ���� �ε��� ����

	private Stage primaryStage;
	String selectFileName = ""; // �̹��� ���ϸ�
	String localUrl = ""; // �̹��� ���� ���
	Image localImage;

	int s_number; // ������ ���̺��� ������ �л��� ��ȣ ����
	File selectedFile = null;

	// �̹��� ó��
	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C:/images");
	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	private File file = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// ������¥ ö������ ���ϳ�¥ �ٷ� ����
		txt_s_LocationDate.setValue(LocalDate.now());
		txt_s_WithdrawalDate.setValue(LocalDate.now());

		// ���屸���޺� �� ����
		cb_s_DepartmentClassification.setItems(FXCollections.observableArrayList("��ȭ��", "�鼼��", "�ƿ﷿", "�ؿܸ���", "���"));
		// ���屸���޺�2 �� ����
		cb_s_DepartmentClassification2.setItems(FXCollections.observableArrayList("�������", "�˾�����", "�һ���ȸ"));

		tableView_StoreStock.setEditable(false);

		// ���̺� �� �÷��̸� ����
		TableColumn cols_Number = new TableColumn("NO.");
		cols_Number.setMaxWidth(30);
		cols_Number.setStyle("-fx-allignment: CENTER");
		cols_Number.setCellValueFactory(new PropertyValueFactory<>("s_number"));

		TableColumn cols_DepartmentClassification = new TableColumn<>("���屸��");
		cols_DepartmentClassification.setMaxWidth(60);
		cols_DepartmentClassification.setCellValueFactory(new PropertyValueFactory<>("s_DepartmentClassification"));

		TableColumn cols_DepartmentClassification2 = new TableColumn<>("���屸��");
		cols_DepartmentClassification2.setMaxWidth(70);
		cols_DepartmentClassification2.setCellValueFactory(new PropertyValueFactory<>("s_DepartmentClassification2"));

		TableColumn cols_BrandName = new TableColumn("�귣���");
		cols_BrandName.setMaxWidth(100);
		cols_BrandName.setStyle("-fx-allignment: CENTER");
		cols_BrandName.setCellValueFactory(new PropertyValueFactory<>("s_BrandName"));

		TableColumn cols_StoreName = new TableColumn("�����");
		cols_StoreName.setMaxWidth(300);
		cols_StoreName.setStyle("-fx-allignment: CENTER");
		cols_StoreName.setCellValueFactory(new PropertyValueFactory<>("s_StoreName"));

		TableColumn cols_StoreLocation = new TableColumn("������ġ");
		cols_StoreLocation.setMaxWidth(100);
		cols_StoreLocation.setStyle("-fx-allignment: CENTER");
		cols_StoreLocation.setCellValueFactory(new PropertyValueFactory<>("s_StoreLocation"));

		TableColumn cols_LocationDate = new TableColumn("������");
		cols_LocationDate.setMaxWidth(110);
		cols_LocationDate.setStyle("-fx-allignment: CENTER");
		cols_LocationDate.setCellValueFactory(new PropertyValueFactory<>("s_LocationDate"));

		TableColumn cols_WithdrawalDate = new TableColumn("ö����");
		cols_WithdrawalDate.setMaxWidth(110);
		cols_WithdrawalDate.setStyle("-fx-allignment: CENTER");
		cols_WithdrawalDate.setCellValueFactory(new PropertyValueFactory<>("s_WithdrawalDate"));

		TableColumn cols_Material = new TableColumn("����");
		cols_Material.setMaxWidth(100);
		cols_Material.setStyle("-fx-allignment: CENTER");
		cols_Material.setCellValueFactory(new PropertyValueFactory<>("s_Material"));

		TableColumn cols_VisualSize = new TableColumn("����������");
		cols_VisualSize.setMaxWidth(120);
		cols_VisualSize.setStyle("-fx-allignment: CENTER");
		cols_VisualSize.setCellValueFactory(new PropertyValueFactory<>("s_VisualSize"));

		TableColumn colGender = new TableColumn("���󿩺�");
		colGender.setMaxWidth(100);
		colGender.setStyle("-fx-allignment: CENTER");
		colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

		TableColumn cols_CompanyName = new TableColumn("��ü��");
		cols_CompanyName.setMaxWidth(120);
		cols_CompanyName.setStyle("-fx-allignment: CENTER");
		cols_CompanyName.setCellValueFactory(new PropertyValueFactory<>("s_CompanyName"));

		TableColumn cols_ConstructionAmount = new TableColumn("����ݾ�");
		cols_ConstructionAmount.setMaxWidth(160);
		cols_ConstructionAmount.setStyle("-fx-allignment: CENTER");
		cols_ConstructionAmount.setCellValueFactory(new PropertyValueFactory<>("s_ConstructionAmount"));

		TableColumn colimageView = new TableColumn("�̹�������");
		colimageView.setMaxWidth(140);
		colimageView.setStyle("-fx-allignment: CENTER");
		colimageView.setCellValueFactory(new PropertyValueFactory<>("imageView"));

		tableView_StoreStock.getColumns().addAll(cols_Number, cols_DepartmentClassification,
				cols_DepartmentClassification2, cols_BrandName, cols_StoreName, cols_StoreLocation, cols_LocationDate,
				cols_WithdrawalDate, cols_Material, cols_VisualSize, colGender, cols_CompanyName,
				cols_ConstructionAmount, colimageView);

		// ���� ��ü ����
		totalList();

		tableView_StoreStock.setItems(data);

		// �⺻ �̹���
		localUrl = "/images/ImageView.png";
		localImage = new Image(localUrl, false);
		imageView.setImage(localImage);

		// ��ü ����Ʈ
		btn_s_TotalList.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					data.removeAll(data);
					// ���� ��ü ����
					totalList();
				} catch (Exception e) {

				}

			}
		});

		// ���콺Ŀ�� �̺�Ʈ

		btn_s_Add.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());

		// ���� ���� ����
		btn_s_Add.setOnAction(event -> {

			try {

				// ���� ������ �����
				data.removeAll(data);
				StoreVO sVo = null;
				StoreDAO sDao = new StoreDAO();
				File dirMake = new File(dirSave.getAbsolutePath());

				// �̹��� ���� ���� ����
				if (!dirMake.exists()) {
					dirMake.mkdirs();
				}

				// �̹��� ���� ����
				String fileName = imageSave(selectedFile);

				// ���� ���� ����
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
						alert.setTitle("���� �Է�");
						alert.setHeaderText(txt_s_StoreName.getText() + "�� ������ ���������� ��ϵǾ����ϴ�.");
						alert.setContentText("���������� �Է��ϼ���.");

						btn_s_StoreAdd.setDisable(true);

						// �⺻ �̹���

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
				alert.setTitle("���� ���� �Է�");
				alert.setHeaderText("���� ������ ��Ȯ�� �Է��Ͻÿ�.");
				alert.setContentText("�������� �����ϼ���!");
				alert.showAndWait();

				totalList();
			}

		});
		cb_s_DepartmentClassification.setOnMouseClicked(event -> handlercbsDepartmentClassificationAction(event)); // �л������Է�
		btn_s_StoreAdd.setOnAction(event -> handlerBtnsStoreAddAction(event)); // �̹��� ����â
		btn_s_Exit.setOnAction(event -> handlerBtnExitAction(event)); // ���� ��ư
		btn_s_Back.setOnAction(event -> handlerBtnBackAction(event)); // ���θ޴� �̵�
		btn_s_Delete.setOnAction(event -> handlerBtnDeleteAction(event)); // ���������ư
		btn_s_Edit.setOnAction(event -> handlerBtnEditAction(event)); // ������ư
		tableView_StoreStock.setOnMouseClicked(event -> handlerImageViewAction(event)); // ���̺� ������ Ŭ���� ���� �ҷ�����
		btn_s_Search.setOnAction(event -> handlerBtnSearchAction(event)); // ����˻����

		// �����ι�ư �̺�Ʈ
		btnSaveFileDir.setOnAction(event -> handlerBtnSaveFileDirAction(event));
		// �������Ϲ�ư �̺�Ʈ
		btnExcel.setOnAction(event -> handlerBtnExcelAction(event));
		

		// ���콺Ŀ�� �̺�Ʈ

		btn_s_StoreAdd.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_s_Exit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_s_Back.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_s_Delete.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_s_Edit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_s_Search.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());

	}
	
	// �������Ϲ�ư�޼ҵ�
	public void handlerBtnExcelAction(ActionEvent event) {
		StoreDAO sDao = new StoreDAO();
		boolean saveSuccess = false;
		//
		ArrayList<StoreVO> list;
		list = sDao.getStoreTotal();
		StoreExcel excelWriter = new StoreExcel();

		// xlsx���Ͼ���
		saveSuccess = excelWriter.xlsxWiter(list, txtSaveFileDir.getText());
		if (saveSuccess) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("���� ���� ����");
			alert.setHeaderText("�л� ��� �������� ��������");
			alert.setContentText("�����ߴ�ϴ�");
			alert.showAndWait();
		}

		//
		txtSaveFileDir.clear();
		btnExcel.setDisable(true);
		
	}// �������Ϲ�ư ��
		// �����ι�ư �޼ҵ�

	
	
	public void handlerBtnSaveFileDirAction(ActionEvent event) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		final File selectedDirectory = directoryChooser.showDialog(primaryStage);

		if (selectedDirectory != null) {
			txtSaveFileDir.setText(selectedDirectory.getAbsolutePath());
			btnExcel.setDisable(false);
			
		}
	}// �����ι�ư ��

	// ����˻�
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
				alert.setTitle("���� ���� �˻�");
				alert.setHeaderText("������� �Է����ּ���.");
				alert.setContentText("�������� �����ϼ���.");
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
					alert.setTitle("���� ���� �˻�");
					alert.setHeaderText(searchName + "�˻��Ͻ� ������� ����Ʈ�� �����ϴ�.");
					alert.setContentText("�ٽ� �˻��ϼ���.");
					alert.showAndWait();
				}
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("���� ���� �˻� ����");
			alert.setHeaderText("���� ���� �˻��� ������ �߻��Ͽ����ϴ�.");
			alert.setContentText("�ٽ� �ϼ���.");
			alert.showAndWait();

		}
	}

	// ���̺� Ŭ���� ���� �����ֱ�
	public void handlerImageViewAction(MouseEvent event) {
		// ���콺 ���� Ŭ��

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
				alert.setTitle("�̹��� ����");
				alert.setHeaderText("�̹��� ���� ���ÿ� ������ �߻��Ͽ����ϴ�..");
				alert.setContentText("�ٽ� ���� �ϼ���.");
				alert.showAndWait();

			}
			return;
		}
	}

	// ���屸�� �޺��ڽ��� ���콺 Ŭ���� �ʱ�ȭ
	public void handlercbsDepartmentClassificationAction(MouseEvent event) {
		init();

	}

	// ���� ����
	public void handlerBtnEditAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/StoreEdit.fxml"));

			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btn_s_Add.getScene().getWindow());
			dialog.setTitle("����");

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

			// ���� �Ұ��� ����
			editNumber.setDisable(true);
			editcb_s_DepartmentClassification.setDisable(true);
			editcb_s_DepartmentClassification2.setDisable(true);
			edittxt_s_BrandName.setDisable(true);
			edittxt_s_StoreName.setDisable(true);
			edittxt_s_CompanyName.setDisable(true);
			edittxt_s_ConstructionAmount.setDisable(true);

			// DAO���� �����ͺ��̽� �ڷ� �ҷ�����!!!
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
						// �̹��� ���� ���
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
				// ����Ȱ� �����Ȱ����� �̵�
				data.remove(selectedIndex);

				AnchorPane imageBox2;

				try {

					File dirMake = new File(dirSave.getAbsolutePath());

					// �̹��� ���� ���� ����
					if (!dirMake.exists()) {
						dirMake.mkdirs();
					}

					// �̹��� ���� ����
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

			// ��� ��ư
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
			alert.setTitle("���� ����");
			alert.setHeaderText("�����Ͽ��� ������ �����Ͻÿ�.");
			alert.setContentText("�������� �����ϼ���!");
			alert.showAndWait();
		}
	}

	// ���� ����
	public void handlerBtnDeleteAction(ActionEvent event) {
		StoreDAO sDao = null;
		sDao = new StoreDAO();
		try {
			// ���̺�� ���� �� ���̺��ִ� �ѹ��� �ҷ��ͼ� �����!
			sDao.getStoreDelete(tableView_StoreStock.getSelectionModel().getSelectedItem().getS_number());
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
		btn_s_StoreAdd.setDisable(false); // ���� ����

		// �⺻ �̹���

		localUrl = "/images/ImageView.png";
		localImage = new Image(localUrl, false);
		imageView.setImage(localImage);
	}

	// ���θ޴��� �̵�
	public void handlerBtnBackAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD ���θ޴�");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_s_Back.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("����" + e);
		}
	}

	// �α׾ƿ� ��ư
	public void handlerBtnExitAction(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD ������ �α���");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_s_Exit.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("����" + e);
		}
	}

	// �̹��� ����â (��Ͻ�)
	public void handlerBtnsStoreAddAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(primaryStage);
			if (selectedFile != null) {
				// �̹��� ���� ���
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

	// ���� ��ü ����Ʈ
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

	// �̹��� ����
	public String imageSave(File file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		try {
			// �̹��� ���ϸ� ����
			fileName = "Store" + System.currentTimeMillis() + "_" + file.getName();
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "\\" + fileName));

			// ������ �̹��� ���� inputStream�� �������� �̸����� ���� -1
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
