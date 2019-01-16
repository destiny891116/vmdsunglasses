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
		btn_ManageSalesRepresentatives.setOnAction(event -> HandlerbtnVMDMangementAction(event)); // VMD담당자
		btn_Exit.setOnAction(event -> HandlerbtnExitAction(event));

		// 마우스커서 이벤트

		btn_StroeManagement.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_ItemInventoryManagement.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_LcooperatorManagement.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_ManageSalesRepresentatives.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
		btn_Exit.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseEventHandler());
	}

	// 로그인창으로 이동
	public void HandlerbtnExitAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scane = new Scene(mainView);
			Stage mainMtage = new Stage();
			mainMtage.setTitle("VMD 관리자 로그인");
			mainMtage.setScene(scane);

			Stage oldStage = (Stage) btn_Exit.getScene().getWindow();
			oldStage.close();
			mainMtage.show();

		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}

	// 매장관리
	public void HandlerbtnStroeManagementAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Store.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("매장 관리");
			mainStage.setScene(scene);

			// 매장관리버튼을 눌르면 메인창을 닫게한다.
			Stage oldStage = (Stage) btn_StroeManagement.getScene().getWindow();
			oldStage.close();
			mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
		/* TraineeDAO tDao = new TraineeDAO(); */
	}

	// 재고관리
	public void HandlerbtnItemInventoryManagementAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Item.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("재고 관리");
			mainStage.setScene(scene);

			// 매장관리버튼을 눌르면 메인창을 닫게한다.
			Stage oldStage = (Stage) btn_ItemInventoryManagement.getScene().getWindow();
			oldStage.close();
			mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 협력업체관리
	public void HandlerbtnLcooperatorManagementAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Lcooperator.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("협력업체 관리");
			mainStage.setScene(scene);

			// 매장관리버튼을 눌르면 메인창을 닫게한다.
			Stage oldStage = (Stage) btn_ItemInventoryManagement.getScene().getWindow();
			oldStage.close();
			mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// VMD담당자관리
	public void HandlerbtnVMDMangementAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/VMDManagement.fxml"));
			Scene scene = new Scene(root);
			Stage mainStage = new Stage();
			mainStage.setTitle("VMD담당자 관리");
			mainStage.setScene(scene);

			// 매장관리버튼을 눌르면 메인창을 닫게한다.
			Stage oldStage = (Stage) btn_ItemInventoryManagement.getScene().getWindow();
			oldStage.close();
			mainStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
