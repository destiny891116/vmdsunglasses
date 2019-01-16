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
	private TableView<FurnitureVO> tableView_ReleaceStock2 = new TableView<>(); // ����������̺�

	// ���� ��� : �޺��ڽ�
	@FXML
	private ComboBox<String> cb_I_BrandName;

	// ���� ��� : �ؽ�Ʈ�ʵ�
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

	// ������ : ��ư
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
	private Button btn_I_TotalList; // ��ü����Ʈ
	@FXML
	private Button btn_I_TotalList2; // ��ü����Ʈ (�������)
	@FXML
	private Button btnInit; // �ʱ�ȭ ��ư -> �������� FXML
	@FXML
	private Button btn_I_Search;
	@FXML
	private Button btn_I_Search2; // ����������̺�˻�
	@FXML
	private Button btn_I_return; // ����ݳ���ư
	@FXML
	private Button btn_I_Warehousing; // �԰��ư
	@FXML
	private Button btn_I_ReleaseOK; // ���Ȯ����ư

	// �԰���
	@FXML
	private DatePicker txt_I_ReceivingDate;
	/*
	 * @FXML private DatePicker txt_I_ForwardingDate;
	 */

	// �����̹����ڽ� : ��ī��
	@FXML
	private AnchorPane imageBox2;
	// �����̹������� : �̹�����

	@FXML
	private ImageView I_imageView;
	// �����̹��� : ��ư

	// PDF & EXCEL
	@FXML
	private Button btnSaveFileDir;
	@FXML
	private TextField txtSaveFileDir;
	@FXML
	private Button btnPDF;
	@FXML
	private DatePicker pckDate; // pdf �ð�
	@FXML
	private Button btnBarchart;

	//
	ObservableList<ItemVO> data = FXCollections.observableArrayList();
	ObservableList<ItemVO> selectItem = null; // ���̺��� ������ ���� ����

	ObservableList<FurnitureVO> data2 = FXCollections.observableArrayList();
	ObservableList<FurnitureVO> selectItem2 = null; // ���̺��� ������ ���� ����

	boolean editDelete2 = false; // ������ �� Ȯ�� ��ư ���� ����
	int selectedIndex; // ���̺��� ������ ���� ���� �ε��� ����
	int selectedIndex2; // ���̺��� ������ ���� ���� �ε��� ����

	/*
	 * private Stage primaryStage; //
	 */ private Stage primaryStage2;
	String selectFileName = ""; // �̹��� ���ϸ�
	String localUrl = ""; // �̹��� ���� ���
	Image localImage;

	int I_number; // ������ ���̺��� ������ �л��� ��ȣ ����
	File selectedFile = null;

	// �̹��� ó��
	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C:/images/Item");
	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	private File file = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		txt_I_ReceivingDate.setValue(LocalDate.now());
		/* txt_I_ForwardingDate.setValue(LocalDate.now()); */
		// �귣����޺� �� ����
		cb_I_BrandName.setItems(FXCollections.observableArrayList("VEDIVERO", "TOMFORD", "BALENCIAGA"));

		tableView_ItemStock.setEditable(false);
		tableView_ReleaceStock2.setEditable(false);

		// ��������� �� �÷��̸� ����
		TableColumn colI_Number = new TableColumn("NO.");
		colI_Number.setMaxWidth(30);
		colI_Number.setStyle("-fx-allignment: CENTER");
		colI_Number.setCellValueFactory(new PropertyValueFactory<>("I_number"));

		TableColumn colI_BrandName = new TableColumn<>("�귣���");
		colI_BrandName.setMaxWidth(100);
		colI_BrandName.setCellValueFactory(new PropertyValueFactory<>("I_BrandName"));

		TableColumn colI_SojipgiName = new TableColumn("�������");
		colI_SojipgiName.setMaxWidth(300);
		colI_SojipgiName.setStyle("-fx-allignment: CENTER");
		colI_SojipgiName.setCellValueFactory(new PropertyValueFactory<>("I_SojipgiName"));

		TableColumn colI_ModelName = new TableColumn("�𵨸�");
		colI_ModelName.setMaxWidth(300);
		colI_ModelName.setStyle("-fx-allignment: CENTER");
		colI_ModelName.setCellValueFactory(new PropertyValueFactory<>("I_ModelName"));

		TableColumn colI_Storage = new TableColumn("�������");
		colI_Storage.setMaxWidth(180);
		colI_Storage.setStyle("-fx-allignment: CENTER");
		colI_Storage.setCellValueFactory(new PropertyValueFactory<>("I_Storage"));

		TableColumn colI_QuantityOfWarehoused = new TableColumn("�������");
		colI_QuantityOfWarehoused.setMaxWidth(110);
		colI_QuantityOfWarehoused.setStyle("-fx-allignment: CENTER");
		colI_QuantityOfWarehoused.setCellValueFactory(new PropertyValueFactory<>("I_QuantityOfWarehoused"));

		TableColumn colI_Price = new TableColumn("��  ��");
		colI_Price.setMaxWidth(110);
		colI_Price.setStyle("-fx-allignment: CENTER");
		colI_Price.setCellValueFactory(new PropertyValueFactory<>("I_Price"));

		TableColumn colI_CompanyName = new TableColumn("�԰�ó");
		colI_CompanyName.setMaxWidth(160);
		colI_CompanyName.setStyle("-fx-allignment: CENTER");
		colI_CompanyName.setCellValueFactory(new PropertyValueFactory<>("I_CompanyName"));

		TableColumn colI_ReceivingDate = new TableColumn("�԰���");
		colI_ReceivingDate.setMaxWidth(160);
		colI_ReceivingDate.setStyle("-fx-allignment: CENTER");
		colI_ReceivingDate.setCellValueFactory(new PropertyValueFactory<>("I_ReceivingDate"));

		TableColumn colI_imageView = new TableColumn("�̹�������");
		colI_imageView.setMaxWidth(140);
		colI_imageView.setStyle("-fx-allignment: CENTER");
		colI_imageView.setCellValueFactory(new PropertyValueFactory<>("I_imageView"));

		// �԰� ���̺�
		tableView_ItemStock.getColumns().addAll(colI_Number, colI_BrandName, colI_SojipgiName, colI_ModelName,
				colI_Storage, colI_QuantityOfWarehoused, colI_Price, colI_CompanyName, colI_ReceivingDate,
				colI_imageView);

		// ��� ����Ʈ ���̺� �� �÷��̸� ����
		TableColumn colI_Number2 = new TableColumn("NO.");
		colI_Number2.setMaxWidth(30);
		colI_Number2.setStyle("-fx-allignment: CENTER");
		colI_Number2.setCellValueFactory(new PropertyValueFactory<>("I_number"));

		TableColumn colI_BrandName2 = new TableColumn<>("�귣���");
		colI_BrandName2.setMaxWidth(100);
		colI_BrandName2.setCellValueFactory(new PropertyValueFactory<>("I_BrandName"));

		TableColumn colI_SojipgiName2 = new TableColumn("�������");
		colI_SojipgiName2.setMaxWidth(300);
		colI_SojipgiName2.setStyle("-fx-allignment: CENTER");
		colI_SojipgiName2.setCellValueFactory(new PropertyValueFactory<>("I_SojipgiName"));

		TableColumn colI_ModelName2 = new TableColumn("�𵨸�");
		colI_ModelName2.setMaxWidth(300);
		colI_ModelName2.setStyle("-fx-allignment: CENTER");
		colI_ModelName2.setCellValueFactory(new PropertyValueFactory<>("I_ModelName"));

		//
		TableColumn colI_StoreName = new TableColumn("�����");
		colI_StoreName.setMaxWidth(300);
		colI_StoreName.setStyle("-fx-allignment: CENTER");
		colI_StoreName.setCellValueFactory(new PropertyValueFactory<>("I_StoreName"));

		//
		TableColumn colI_ForwardingQuantity = new TableColumn("������");
		colI_ForwardingQuantity.setMaxWidth(300);
		colI_ForwardingQuantity.setStyle("-fx-allignment: CENTER");
		colI_ForwardingQuantity.setCellValueFactory(new PropertyValueFactory<>("I_ForwardingQuantity"));

		TableColumn colI_ForwardingDate = new TableColumn("�����");
		colI_ForwardingDate.setMaxWidth(180);
		colI_ForwardingDate.setStyle("-fx-allignment: CENTER");
		colI_ForwardingDate.setCellValueFactory(new PropertyValueFactory<>("I_ForwardingDate"));

		// ��� ���̺�
		tableView_ReleaceStock2.getColumns().addAll(colI_Number2, colI_BrandName2, colI_SojipgiName2, colI_ModelName2,
				colI_StoreName, colI_ForwardingQuantity, colI_ForwardingDate);

		// ���� ��ü ����
		totalList();
		tableView_ItemStock.setItems(data);
		totalList2();
		tableView_ReleaceStock2.setItems(data2);

		// �⺻ �̹���
		localUrl = "/images/image_I_View.png";
		localImage = new Image(localUrl, false);
		I_imageView.setImage(localImage);

		// ��ü ����Ʈ
		btn_I_TotalList.setOnAction(new EventHandler<ActionEvent>() {
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

		// ���� ��ü����Ʈ
		btn_I_TotalList2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					data2.removeAll(data2);
					// ���� ��ü ����
					totalList2();
				} catch (Exception e) {

				}

			}

		});
		// ���콺Ŀ�� �̺�Ʈ
		btn_I_Add.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		// ���� ���� ����
		btn_I_Add.setOnAction(event -> {

			try {
				// ���� ������ �����
				data.removeAll(data);
				ItemVO iVo = null;
				ItemDAO iDao = new ItemDAO();
				File dirMake = new File(dirSave.getAbsolutePath());

				// �̹��� ���� ���� ����
				if (!dirMake.exists()) {
					dirMake.mkdirs();
				}

				// �̹��� ���� ����
				String fileName = imageSave(selectedFile);

				// ���� ���� ����
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
						alert.setTitle("���� �Է�");
						alert.setHeaderText(txt_I_SojipgiName.getText() + "�� ������ ���������� ��ϵǾ����ϴ�.");
						alert.setContentText("���������� �Է��ϼ���.");

						btn_I_ImageAdd.setDisable(true);

						// �⺻ �̹���

						// �⺻ �̹���
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
				alert.setTitle("���� ���� �Է�");
				alert.setHeaderText("���� ������ ��Ȯ�� �Է��Ͻÿ�.");
				alert.setContentText("�������� �����ϼ���!");
				alert.showAndWait();
			}

		});

		// pdf���Ϲ�ư �̺�Ʈ

		cb_I_BrandName.setOnMouseClicked(event -> handlerCbIBrandNameAction(event));
		btn_I_ImageAdd.setOnAction(event -> handlerBtnsItemAddAction(event)); // �̹��� ����â
		btn_I_Exit.setOnAction(event -> handlerBtnExitAction(event)); // �α׾ƿ���ư
		btn_I_Back.setOnAction(event -> handlerBtnBackAction(event));
		btn_I_Delete.setOnAction(event -> handlerBtnDeleteAction(event)); // ������ư
		btn_I_Search.setOnAction(event -> handlerBtnSearchAction(event)); // ����˻����
		btn_I_Search2.setOnAction(event -> handlerBtnSearchAction2(event));
		tableView_ItemStock.setOnMouseClicked(event -> handlerImageViewAction2(event));
		btn_I_Release.setOnAction(event -> handlerBtnReleaseAction(event)); // ��������ư
		btn_I_return.setOnAction(event -> handlerBtnbtnreturnAction(event)); // ����ݳ���ư
		btn_I_Warehousing.setOnAction(event -> handlerBtnWarehousingAction(event)); // �԰�ó����ư
		btn_I_ReleaseOK.setOnAction(event -> handlerBtnReleaseOKAction(event)); // ���Ȯ����ư
		btnPDF.setOnAction(event -> handlerBtnPDFAction(event));
		// �����ι�ư �̺�Ʈ
		btnSaveFileDir.setOnAction(event -> handlerBtnSaveFileDirAction(event));
		// ���콺Ŀ�� �̺�Ʈ

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
			// pdf document ����
			// (Rectangle pageSize,float marginLeft.float marginRight,top,bottom
			Document document = new Document(PageSize.A4, 0, 0, 30, 30);
			// pdf ���� ������� ���� .pdf������ ����, ���� ��Ʈ������ ����
			String strReportPDFName = "customer_" + System.currentTimeMillis() + ".pdf";
			PdfWriter.getInstance(document, new FileOutputStream(txtSaveFileDir.getText() + "\\" + strReportPDFName));
			// document�� ���� pdf������ �����ֵ���
			document.open();
			// �ѱ�������Ʈ ����
			BaseFont bf = BaseFont.createFont("font/MALGUN.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 8, Font.NORMAL);
			Font font2 = new Font(bf, 14, Font.BOLD);
			// Ÿ��Ʋ
			Paragraph title = new Paragraph("���������LIST", font2);
			// �߰�����
			title.setAlignment(Element.ALIGN_CENTER);
			// ������ �߰�
			document.add(title);
			document.add(new Paragraph("\r\n"));
			// ������¥
			LocalDate date1 = LocalDate.now();
			Paragraph writeDay = new Paragraph(date1.toString(), font);
			// ������ ����
			writeDay.setAlignment(Element.ALIGN_RIGHT);
			// ������ �߰�
			document.add(writeDay);
			document.add(new Paragraph("\r\n"));

			// �����ڿ� �÷����� �ش�.
			PdfPTable table = new PdfPTable(7);
			table.setWidths(new int[] { 50, 100, 100, 100, 120, 80, 150 });
			// �÷�Ÿ��Ʋ

			// �÷� Ÿ��Ʋ
			PdfPCell header1 = new PdfPCell(new Paragraph("��ȣ", font));
			PdfPCell header2 = new PdfPCell(new Paragraph("�귣���", font));
			PdfPCell header3 = new PdfPCell(new Paragraph("�������", font));
			PdfPCell header4 = new PdfPCell(new Paragraph("�𵨸�", font));
			PdfPCell header5 = new PdfPCell(new Paragraph("�����", font));
			PdfPCell header6 = new PdfPCell(new Paragraph("������", font));
			PdfPCell header7 = new PdfPCell(new Paragraph("�����", font));

			// ��������
			header1.setHorizontalAlignment(Element.ALIGN_CENTER);
			header2.setHorizontalAlignment(Element.ALIGN_CENTER);
			header3.setHorizontalAlignment(Element.ALIGN_CENTER);
			header4.setHorizontalAlignment(Element.ALIGN_CENTER);
			header5.setHorizontalAlignment(Element.ALIGN_CENTER);
			header6.setHorizontalAlignment(Element.ALIGN_CENTER);
			header7.setHorizontalAlignment(Element.ALIGN_CENTER);

			// ���� ����
			header1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header4.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header5.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header6.setVerticalAlignment(Element.ALIGN_MIDDLE);
			header7.setVerticalAlignment(Element.ALIGN_MIDDLE);

			// ���̺� �߰�
			table.addCell(header1);
			table.addCell(header2);
			table.addCell(header3);
			table.addCell(header4);
			table.addCell(header5);
			table.addCell(header6);
			table.addCell(header7);

			// DB ���� �� ����Ʈ ����
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

				// ���� ����
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell7.setHorizontalAlignment(Element.ALIGN_CENTER);

				// ��������
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);

				// ���̺� �� �߰�
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				table.addCell(cell5);
				table.addCell(cell6);
				table.addCell(cell7);

			}
			// ������ ���̺� �߰�
			document.add(table);
			document.add(new Paragraph("\r\n"));
			Alert alert = new Alert(AlertType.INFORMATION);
			// ���� �ݱ� ���� ����
			document.close();

			txtSaveFileDir.clear();
			btnPDF.setDisable(true);

			alert.setTitle("PDF ����");
			alert.setHeaderText("�뿩�� ��� PDF���� ���� ����!");
			alert.setContentText("�뿩�ڸ�� PDF ����!");
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

	// ���θ޴������ư
	public void handlerBtnBackAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD ���θ޴�");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_I_Back.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("����" + e);
		}
	}

	// ����ݳ� ��ư (���� �ݳ� �� ��������)
	public void handlerBtnbtnreturnAction(ActionEvent event) {
		FurnitureVO fVo = null;
		FurnitureDAO fDao = null;
		fDao = new FurnitureDAO();

		selectItem2 = tableView_ReleaceStock2.getSelectionModel().getSelectedItems();
		try {
			String ForwardingQuantity; // ������
			ForwardingQuantity = selectItem2.get(0).getI_ForwardingQuantity();

			String SojipgiName; // �����̸�
			SojipgiName = selectItem2.get(0).getI_SojipgiName();

			data.remove(selectedIndex);

			fVo = new FurnitureVO(ForwardingQuantity, SojipgiName);
			System.out.println(ForwardingQuantity);
			System.err.println(SojipgiName);
			fDao = new FurnitureDAO();
			fDao.getReturnUpdate(fVo, fVo.getI_SojipgiName());

			data.removeAll(data);
			totalList();

			// ���̺�� ���� �� ���̺��ִ� �ѹ��� �ҷ��ͼ� �����!
			fDao.getItemReturn(tableView_ReleaceStock2.getSelectionModel().getSelectedItem().getI_number());
			data.removeAll(data);
			data2.removeAll(data2);
			// ���� ��ü ����
			totalList();
			totalList2();
			handlerBtnInitAction(event);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("��� �ݳ�");
			alert.setHeaderText("��� �����Ͽ��� ������ ���⸦ �����Ͻÿ�.");
			alert.setContentText("�������� �����ϼ���!");
			alert.showAndWait();
		}
	}

	// ���Ȯ����ư
	public void handlerBtnReleaseOKAction(ActionEvent event) {
		FurnitureVO fVo = null;
		FurnitureDAO fDao = null;

		selectItem2 = tableView_ReleaceStock2.getSelectionModel().getSelectedItems();

		try {
			String ForwardingQuantity; // ������
			ForwardingQuantity = selectItem2.get(0).getI_ForwardingQuantity();

			String SojipgiName; // �����̸�
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
			alert.setTitle("���� �԰�");
			alert.setHeaderText("�����Ͽ��� ���⸦ �����Ͻÿ�.");
			alert.setContentText("�������� �����ϼ���!2222");
			alert.showAndWait();
		}
	}

	// �����԰��ư
	public void handlerBtnWarehousingAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Warehousing.fxml"));

			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btn_I_Warehousing.getScene().getWindow());
			dialog.setTitle("���� �԰�");

			Parent parentEdit = (Parent) loader.load();
			ItemVO ItemEdit2 = tableView_ItemStock.getSelectionModel().getSelectedItem();
			selectedIndex = tableView_ItemStock.getSelectionModel().getSelectedIndex();

			TextField editI_Number = (TextField) parentEdit.lookup("#I_number"); // ��ȣ
			TextField edittxt_I_QuantityOfWarehoused = (TextField) parentEdit.lookup("#txt_I_QuantityOfWarehoused"); // �������
			TextField edittxt_I_AdditionalQuantity = (TextField) parentEdit.lookup("#txt_I_AdditionalQuantity"); // �߰�����
			TextField edittxt_I_Total = (TextField) parentEdit.lookup("#txt_I_Total"); // �հ�

			// ���� �Ұ��� ����
			editI_Number.setDisable(true);
			edittxt_I_QuantityOfWarehoused.setDisable(true);
			edittxt_I_Total.setDisable(true);

			// �����ͺ��̽� �ڷ� �ҷ�����

			editI_Number.setText(ItemEdit2.getI_number() + "");
			edittxt_I_QuantityOfWarehoused.setText(ItemEdit2.getI_QuantityOfWarehoused()); // �������

			Button btn_I_Reckoning = (Button) parentEdit.lookup("#btn_I_Reckoning"); // ����ư
			Button btn_I_Warehousing2 = (Button) parentEdit.lookup("#btn_I_Warehousing2"); // �԰��ư
			Button btn_I_Exit4 = (Button) parentEdit.lookup("#btn_I_Exit4"); // �ݱ��ư

			// �԰���� ���
			btn_I_Reckoning.setOnAction(e -> {

				if (edittxt_I_AdditionalQuantity.getText().equals("")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("�԰�");
					alert.setHeaderText("�԰� ������ �Է��ϼ���.");
					alert.setContentText("0���� ū ���� �Է��ϼ���.");
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

				// ����Ȱ� �����Ȱ����� �̵� data.remove(selectedIndex);
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

			// ��� ��ư

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
			alert.setTitle("���� �԰�");
			alert.setHeaderText("�����Ͽ��� ���⸦ �����Ͻÿ�.");
			alert.setContentText("�������� �����ϼ���!2222");
			alert.showAndWait();
		}
	}

	// ��������ư
	public void handlerBtnReleaseAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/ItemRelease.fxml"));
			Parent parentEdit = (Parent) loader.load();

			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btn_I_Release.getScene().getWindow());
			dialog.setTitle("���� ���");

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

			// ���� �Ұ��� ����
			editI_Number.setDisable(true);
			editcb_I_BrandName.setDisable(true);
			edittxt_I_SojipgiName.setDisable(true);
			edittxt_I_ModelName.setDisable(true);

			// �����ͺ��̽� �ڷ� �ҷ�����

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

				// ����Ȱ� �����Ȱ����� �̵� data.remove(selectedIndex);
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

				// �⺻ �̹���
				localUrl = "/images/image_I_View.png";
				localImage = new Image(localUrl, false);
				I_imageView.setImage(localImage);

			});

			// ��� ��ư

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
			alert.setTitle("���� ���");
			alert.setHeaderText("�����Ͽ��� ���⸦ �����Ͻÿ�.");
			alert.setContentText("�������� �����ϼ���!");
			alert.showAndWait();
		}
	}

	// �������˻�
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
				alert.setTitle("���� ���� �˻�");
				alert.setHeaderText("������� �Է����ּ���.");
				alert.setContentText("�������� �����ϼ���.");
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

	// �˻���ư
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
				alert.setTitle("���� ���� �˻�");
				alert.setHeaderText("������� �Է����ּ���.");
				alert.setContentText("�������� �����ϼ���.");
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

	// �̹��� ���̱�
	public void handlerImageViewAction2(MouseEvent event) {
		// ���콺 ���� Ŭ��

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
				alert.setTitle("�̹��� ����");
				alert.setHeaderText("�̹��� ���� ���ÿ� ������ �߻��Ͽ����ϴ�..");
				alert.setContentText("�ٽ� ���� �ϼ���.");
				alert.showAndWait();

			}
			return;
		}
	}

	// �귣��� Ŭ���� �ʱ�ȭ
	public void handlerCbIBrandNameAction(MouseEvent event) {
		init();
	}

	// ���� ��� ����
	public void handlerBtnDeleteAction(ActionEvent event) {
		ItemDAO iDao = null;
		iDao = new ItemDAO();
		try {
			// ���̺�� ���� �� ���̺��ִ� �ѹ��� �ҷ��ͼ� �����!
			iDao.getItemDelete(tableView_ItemStock.getSelectionModel().getSelectedItem().getI_number());
			data.removeAll(data);
			// ���� ��ü ����
			totalList();
			handlerBtnInitAction(event);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("���� ����");
			alert.setHeaderText("�����Ͽ��� ������ ���⸦ �����Ͻÿ�.");
			alert.setContentText("�������� �����ϼ���!");
			alert.showAndWait();
		}
	}

	// �ʱ�ȭ
	public void handlerBtnInitAction(ActionEvent event) {
		init();

	}

	// �ʱ�ȭ �޼ҵ�
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

		// �⺻ �̹���
		localUrl = "/images/image_I_View.png";
		localImage = new Image(localUrl, false);
		I_imageView.setImage(localImage);
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
			Stage oldStage = (Stage) btn_I_Exit.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("����" + e);
		}
	}

	// �̹��� ����â
	public void handlerBtnsItemAddAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(primaryStage2);
			if (selectedFile != null) {
				// �̹��� ���� ���
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

	// ������
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

	// ���� ��� ��ü����Ʈ
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

	// �̹��� ����
	public String imageSave(File file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		try {
			// �̹��� ���ϸ� ����
			fileName = "Item" + System.currentTimeMillis() + "_" + file.getName();
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