package com.ghulam.splashScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.ghulam.Main;
import com.ghulam.alert.MaterialDialog;
import com.ghulam.database.Database;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreen implements Initializable {
	@FXML
	private StackPane stackPane;
	@FXML
	private AnchorPane rootPane;
	@FXML
	private Label txtMsg;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtMsg.setText("Initializing database");
		FadeTransition ft = new FadeTransition(Duration.millis(3000), txtMsg);
		ft.setFromValue(1.0);
		ft.setToValue(0.3);
		ft.setCycleCount(FadeTransition.INDEFINITE);
		ft.setAutoReverse(true);
		ft.play();
		new Database().initDatabase();

		Task<Void> task = new Task<>() {
			@Override
			protected Void call() throws Exception {

				txtMsg.setText("Database successfully setup");
				Thread.sleep(2000);
				return null;
			}
		};
		task.setOnSucceeded((e) -> openLogin());
		new Thread(task).start();
	}

	private void openLogin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("login/login.fxml"));
			loader.load();
			Parent parent = loader.getRoot();
			Scene login = new Scene(parent, Color.TRANSPARENT);
			Stage loginStage = (Stage) txtMsg.getScene().getWindow();
			loginStage.setScene(login);
			loginStage.show();
		} catch (IOException e) {
			MaterialDialog.DialogOK(stackPane, e.toString(), rootPane);
		}
	}
}