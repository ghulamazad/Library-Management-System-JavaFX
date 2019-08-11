package com.ghulam.setting;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class SettingController {
	@FXML
	private StackPane stackPane;
	@FXML
	private AnchorPane rootPane;

	public void setPane(StackPane stackPane, AnchorPane rootPane) {
		this.stackPane = stackPane;
		this.rootPane = rootPane;
	}
}
