package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {

	@FXML
	private Button btn_StroeManagement;
	@FXML
	private Button btn_ItemInventoryManagement;
	@FXML
	private Button btn_LcooperatorManagement;
	@FXML
	private Button btn_ManageSalesRepresentatives;
	@FXML
	private Button btn_Exit;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btn_StroeManagement.setOnAction(event -> HandlerbtnStroeManagementAction(event));
		btn_ItemInventoryManagement.setOnAction(event -> HandlerbtnItemInventoryManagementAction(event));
		btn_LcooperatorManagement.setOnAction(event -> HandlerbtnLcooperatorManagementAction(event));
		btn_ManageSalesRepresentatives.setOnAction(event -> HandlerbtnVMDMangementAction(event)); // VMD�����
		btn_Exit.setOnAction(event -> HandlerbtnExitAction(event));

		// ���콺Ŀ�� �̺�Ʈ

		btn_StroeManagement.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_ItemInventoryManagement.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_LcooperatorManagement.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_ManageSalesRepresentatives.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_Exit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
	}

	// �α���â���� �̵�
	public void HandlerbtnExitAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD ������ �α���");
			mainMtage.setScene(scane);

			Stage oldStage = (Stage) btn_Exit.getScene().getWindow();
			oldStage.close();
			mainMtage.show();

		} catch (IOException e) {
			System.out.println("����" + e);
		}
	}

	// �������
	public void HandlerbtnStroeManagementAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Store.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("���� ����");
			mainStage.setScene(scene);

			// ���������ư�� ������ ����â�� �ݰ��Ѵ�.
			Stage oldStage = (Stage) btn_StroeManagement.getScene().getWindow();
			oldStage.close();
			mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
		/* TraineeDAO tDao = new TraineeDAO(); */
	}

	// ������
	public void HandlerbtnItemInventoryManagementAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Item.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("��� ����");
			mainStage.setScene(scene);

			// ���������ư�� ������ ����â�� �ݰ��Ѵ�.
			Stage oldStage = (Stage) btn_ItemInventoryManagement.getScene().getWindow();
			oldStage.close();
			mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���¾�ü����
	public void HandlerbtnLcooperatorManagementAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Lcooperator.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("���¾�ü ����");
			mainStage.setScene(scene);

			// ���������ư�� ������ ����â�� �ݰ��Ѵ�.
			Stage oldStage = (Stage) btn_ItemInventoryManagement.getScene().getWindow();
			oldStage.close();
			mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// VMD����ڰ���
	public void HandlerbtnVMDMangementAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/VMDManagement.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("VMD����� ����");
			mainStage.setScene(scene);

			// ���������ư�� ������ ����â�� �ݰ��Ѵ�.
			Stage oldStage = (Stage) btn_ItemInventoryManagement.getScene().getWindow();
			oldStage.close();
			mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
