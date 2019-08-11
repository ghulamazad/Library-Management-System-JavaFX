package com.ghulam.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SimpleDialog {
	public static void showMsg(String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(contentText);
		alert.setHeaderText(null);
		alert.setTitle("Error");
		alert.show();
	}

}
