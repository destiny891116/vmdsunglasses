package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Logincontroller implements Initializable {

	@FXML
	private TextField txt_l_Id;
	@FXML
	private PasswordField pw_l_Password;
	@FXML
	private Button btn_l_Login;
	@FXML
	private Button btn_l_Join;
	@FXML
	private Button btn_l_Exit;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txt_l_Id.setOnKeyPressed(event -> handlerTxtIdkeyPressed(event)); /// ���̵� �Է¿��� enterŰ �̺�Ʈ����
		pw_l_Password.setOnKeyPressed(event -> handlerTxtPasswordKeyPressed(event)); // �н����� �Է¿��� enter Ű ����

		btn_l_Join.setOnAction(event -> handleBtnJoinAction(event)); // ȸ������â���� ��ȯ
		btn_l_Login.setOnAction(event -> handlerBtnLoginActoion(event));
		btn_l_Exit.setOnAction(event -> handlerBtnExitActoion(event));

		// ���콺Ŀ�� �̺�Ʈ
	
		btn_l_Join.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_l_Login.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_l_Exit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
	}

	// ���̵� �Է¿��� enterŰ �̺�Ʈ ����
	public void handlerTxtIdkeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			pw_l_Password.requestFocus();
		}
	}

	public void handlerTxtPasswordKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	// ȸ������ ���â���� ��ȯ
	public void handleBtnJoinAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/join.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD ȸ������");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_l_Login.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("����" + e);
			e.printStackTrace();
		}
	}

	// �α���â �ݱ�
	public void handlerBtnExitActoion(ActionEvent event) {
		Platform.exit();
	}

	// �α���
	public void handlerBtnLoginActoion(ActionEvent event) {
		login();

	}

	// �α��� �޼ҵ�
	private void login() {
		LoginDAO login = new LoginDAO();

		boolean sucess = false;

		try {
			System.out.println("��������¿���?");
			System.out.println(txt_l_Id.getText());
			System.out.println(pw_l_Password.getText());
			sucess = login.getLogin(txt_l_Id.getText(), pw_l_Password.getText());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("��������¿���?");
		}

		// �Ƶ� �н����� Ȯ���϶�� â
		Alert alert;
		if (txt_l_Id.getText().equals("") || pw_l_Password.getText().equals("")) {

			alert = new Alert(AlertType.WARNING);
			alert.setTitle("�α��� ����");
			alert.setHeaderText("���̵�� ��й�ȣ ����ġ");
			alert.setContentText("���̵�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�." + "\n �ٽ� ����� �Է��Ͻÿ�.");
			alert.setResizable(false);
			alert.showAndWait();
		}

		// �α��� ������ ���� �������� �̵�
		if (sucess) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scane = new Scene(mainView);
				Stage mainMtage = new Stage();
				mainMtage.setTitle("VMD ���� ���θ޴�");
				mainMtage.setResizable(false);
				mainMtage.setScene(scane);
				Stage oldStage = (Stage) btn_l_Login.getScene().getWindow();
				oldStage.close();
				mainMtage.show();
			} catch (IOException e) {
				System.out.println("����" + e);
				e.printStackTrace();
			}
		} else {
			//
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("�α��� ����");
			alert.setHeaderText("���̵�� ��й�ȣ ����ġ");
			alert.setContentText("���̵�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�." + "\n �ٽ� ����� �Է��Ͻÿ�.");
			alert.setResizable(false);
			alert.showAndWait();

			txt_l_Id.clear();
			pw_l_Password.clear();
		}
	}

}
