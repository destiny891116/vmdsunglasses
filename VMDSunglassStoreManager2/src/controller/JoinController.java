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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.JoinVO;

public class JoinController implements Initializable {
	@FXML
	private TextField txt_m_Id;
	@FXML
	private TextField txt_m_Password;
	@FXML
	private TextField txt_m_PasswordRepeat;
	@FXML
	private TextField txt_m_Name;
	@FXML
	private TextField txt_m_Position;
	@FXML
	private TextField txt_m_PhoneNumber;
	@FXML
	private Button btn_m_Exit; // �ڷΰ���
	@FXML
	private Button btn_m_Join; // �����ϱ�
	@FXML
	private Button btn_m_Check; // �ߺ�Ȯ��

	JoinVO joinVO = new JoinVO();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btn_m_Join.setDisable(true); // ��ư ��Ȱ��ȭ
		txt_m_Password.setEditable(false); // ���� �Ұ���
		txt_m_PasswordRepeat.setEditable(false); // ���� �Ұ���

		btn_m_Check.setOnAction(event -> HandlerBtnCheckAction(event)); // ���̵� �ߺ� �˻�
		btn_m_Join.setOnAction(event -> HandlerBtnJoinAction(event)); // ������ ���
		btn_m_Exit.setOnAction(event -> HandlerBtnExitAction(event)); // ���â �ݱ�
	}

	public void HandlerBtnCheckAction(ActionEvent event) {
		btn_m_Join.setDisable(false); // ��ư Ȱ��ȭ
		btn_m_Check.setDisable(true);

		JoinDAO jDao = null;

		String searchld = "";
		boolean searchResult = true;

		try {
			searchld = txt_m_Id.getText().trim();
			jDao = new JoinDAO();
			searchResult = (boolean) jDao.getIdCheck(searchld);

			if (!searchResult && !searchld.equals("")) {
				txt_m_Id.setDisable(true);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���̵� �ߺ� �˻�");
				alert.setHeaderText(searchld + "�� ����� �� �ֽ��ϴ�.");
				alert.setContentText("�н����带 �Է��ϼ���.");
				alert.showAndWait();

				btn_m_Join.setDisable(false);
				btn_m_Check.setDisable(true);
				txt_m_Password.setEditable(true);
				txt_m_PasswordRepeat.setEditable(true);
				txt_m_Password.requestFocus();
			} else if (searchld.equals("")) {
				btn_m_Join.setDisable(true);
				btn_m_Check.setDisable(false);
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���̵� �ߺ� �˻�");
				alert.setHeaderText("���̵� �Է��Ͻÿ�.");
				alert.setContentText("����� ���̵� �Է��ϼ���.");
				alert.showAndWait();
			} else {
				btn_m_Join.setDisable(true);
				btn_m_Check.setDisable(false);
				txt_m_Id.clear();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("���̵� �ߺ� �˻�");
				alert.setHeaderText(searchld + "�� ����� �� �����ϴ�.");
				alert.setContentText("���̵� �ٸ������� �Է��ϼ���.");
				alert.showAndWait();

				txt_m_Id.requestFocus();
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("���̵� �ߺ� �˻� ����");
			alert.setHeaderText("���̵� �ߺ� �˻翡 ������ �߻��Ͽ����ϴ�.");
			alert.setContentText("�ٽ� �ϼ���.");
			alert.showAndWait();
		}
	}

	// ���â �ݱ�
	public void HandlerBtnExitAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD ������ �α���");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_m_Join.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("����" + e);
		}
	}

	// ���
	public void HandlerBtnJoinAction(ActionEvent event) {
		JoinVO jvo = null;
		JoinDAO jdao = null;

		boolean joinSucess = false;

		// �н����� Ȯ��
		if (txt_m_Password.getText().trim().equals(txt_m_PasswordRepeat.getText().trim())) {
			jvo = new JoinVO(txt_m_Id.getText().trim(), txt_m_Password.getText().trim(), txt_m_Name.getText().trim(),
					txt_m_Position.getText().trim(), txt_m_PhoneNumber.getText().trim());
			jdao = new JoinDAO();
			
			try {
				joinSucess = jdao.getTeacherRegiste(jvo);
				if (joinSucess) {
					HandlerBtnExitAction(event);
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			txt_m_Password.clear();
			txt_m_PasswordRepeat.clear();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("�н����� Ȯ��");
			alert.setHeaderText("�н����� Ȯ�� �˻翡 ������ �߻��Ͽ����ϴ�.");
			alert.setContentText("�н����带 �ٽ� �Է��ϼ���.");
			alert.showAndWait();
		}

	}

}
