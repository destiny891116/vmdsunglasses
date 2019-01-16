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
		txt_l_Id.setOnKeyPressed(event -> handlerTxtIdkeyPressed(event)); /// 아이디 입력에서 enter키 이벤트적용
		pw_l_Password.setOnKeyPressed(event -> handlerTxtPasswordKeyPressed(event)); // 패스워드 입력에서 enter 키 적용

		btn_l_Join.setOnAction(event -> handleBtnJoinAction(event)); // 회원가입창으로 전환
		btn_l_Login.setOnAction(event -> handlerBtnLoginActoion(event));
		btn_l_Exit.setOnAction(event -> handlerBtnExitActoion(event));

		// 마우스커서 이벤트
	
		btn_l_Join.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_l_Login.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_l_Exit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
	}

	// 아이디 입력에서 enter키 이벤트 적용
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

	// 회원가입 등록창으로 전환
	public void handleBtnJoinAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/join.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD 회원가입");
			mainMtage.setScene(scane);
			Stage oldStage = (Stage) btn_l_Login.getScene().getWindow();
			oldStage.close();
			mainMtage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
			e.printStackTrace();
		}
	}

	// 로그인창 닫기
	public void handlerBtnExitActoion(ActionEvent event) {
		Platform.exit();
	}

	// 로그인
	public void handlerBtnLoginActoion(ActionEvent event) {
		login();

	}

	// 로그인 메소드
	private void login() {
		LoginDAO login = new LoginDAO();

		boolean sucess = false;

		try {
			System.out.println("여기까지는오니?");
			System.out.println(txt_l_Id.getText());
			System.out.println(pw_l_Password.getText());
			sucess = login.getLogin(txt_l_Id.getText(), pw_l_Password.getText());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("여기까지는오니?");
		}

		// 아디 패스워드 확인하라는 창
		Alert alert;
		if (txt_l_Id.getText().equals("") || pw_l_Password.getText().equals("")) {

			alert = new Alert(AlertType.WARNING);
			alert.setTitle("로그인 실패");
			alert.setHeaderText("아이디와 비밀번호 불일치");
			alert.setContentText("아이디와 비밀번호가 일치하지 않습니다." + "\n 다시 제대로 입력하시오.");
			alert.setResizable(false);
			alert.showAndWait();
		}

		// 로그인 성공시 메인 페이지로 이동
		if (sucess) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scane = new Scene(mainView);
				Stage mainMtage = new Stage();
				mainMtage.setTitle("VMD 관리 메인메뉴");
				mainMtage.setResizable(false);
				mainMtage.setScene(scane);
				Stage oldStage = (Stage) btn_l_Login.getScene().getWindow();
				oldStage.close();
				mainMtage.show();
			} catch (IOException e) {
				System.out.println("오류" + e);
				e.printStackTrace();
			}
		} else {
			//
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("로그인 실패");
			alert.setHeaderText("아이디와 비밀번호 불일치");
			alert.setContentText("아이디와 비밀번호가 일치하지 않습니다." + "\n 다시 제대로 입력하시오.");
			alert.setResizable(false);
			alert.showAndWait();

			txt_l_Id.clear();
			pw_l_Password.clear();
		}
	}

}
