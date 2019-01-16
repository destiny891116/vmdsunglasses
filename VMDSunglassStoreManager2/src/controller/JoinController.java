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
	private Button btn_m_Exit; // 뒤로가기
	@FXML
	private Button btn_m_Join; // 가입하기
	@FXML
	private Button btn_m_Check; // 중복확인

	JoinVO joinVO = new JoinVO();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btn_m_Join.setDisable(true); // 버튼 비활성화
		txt_m_Password.setEditable(false); // 수정 불가능
		txt_m_PasswordRepeat.setEditable(false); // 수정 불가능

		btn_m_Check.setOnAction(event -> HandlerBtnCheckAction(event)); // 아이디 중복 검사
		btn_m_Join.setOnAction(event -> HandlerBtnJoinAction(event)); // 선생님 등록
		btn_m_Exit.setOnAction(event -> HandlerBtnExitAction(event)); // 등록창 닫기
	}

	public void HandlerBtnCheckAction(ActionEvent event) {
		btn_m_Join.setDisable(false); // 버튼 활성화
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
				alert.setTitle("아이디 중복 검사");
				alert.setHeaderText(searchld + "를 사용할 수 있습니다.");
				alert.setContentText("패스워드를 입력하세요.");
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
				alert.setTitle("아이디 중복 검색");
				alert.setHeaderText("아이디를 입력하시오.");
				alert.setContentText("등록할 아이디를 입력하세요.");
				alert.showAndWait();
			} else {
				btn_m_Join.setDisable(true);
				btn_m_Check.setDisable(false);
				txt_m_Id.clear();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("아이디 중복 검사");
				alert.setHeaderText(searchld + "를 사용할 수 없습니다.");
				alert.setContentText("아이디를 다른것으로 입력하세요.");
				alert.showAndWait();

				txt_m_Id.requestFocus();
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("아이디 중복 검사 오류");
			alert.setHeaderText("아이디 중복 검사에 오류가 발생하였습니다.");
			alert.setContentText("다시 하세요.");
			alert.showAndWait();
		}
	}

	// 등록창 닫기
	public void HandlerBtnExitAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD 관리자 로그인");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_m_Join.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}

	// 등록
	public void HandlerBtnJoinAction(ActionEvent event) {
		JoinVO jvo = null;
		JoinDAO jdao = null;

		boolean joinSucess = false;

		// 패스워드 확인
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
			alert.setTitle("패스워드 확인");
			alert.setHeaderText("패스워드 확인 검사에 오류가 발생하였습니다.");
			alert.setContentText("패스워드를 다시 입력하세요.");
			alert.showAndWait();
		}

	}

}
