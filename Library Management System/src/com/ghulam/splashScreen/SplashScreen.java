package com.ghulam.splashScreen;

import java.net.URL;
import java.util.ResourceBundle;

import com.ghulam.database.Database;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
	}

}