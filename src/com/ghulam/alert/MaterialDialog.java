package com.ghulam.alert;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class MaterialDialog {
	private static boolean isOpen = false;
	private BoxBlur blue;
	private JFXDialogLayout dialogLayout;
	private JFXDialog dialog;
	private static JFXButton btn[];

	public MaterialDialog(StackPane root, String msg, Node nodeToBeBlue, String btn1, String btn2) {
		btn = new JFXButton[2];
		blue = new BoxBlur(3, 3, 3);
		dialogLayout = new JFXDialogLayout();
		btn[0] = new JFXButton(btn1);
		btn[0].setPrefSize(86, 46);
		btn[0].getStyleClass().add("allButtons");
		dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.CENTER);
		dialog.setOverlayClose(false);
		btn[0].addEventHandler(MouseEvent.MOUSE_CLICKED, (mouseEvent) -> {
			isOpen = false;
			dialog.close();
		});
		if (!btn2.isEmpty()) {
			btn[1] = new JFXButton(btn2);
			btn[1].setPrefSize(86, 46);
			btn[1].getStyleClass().add("allButtons");
			btn[1].addEventHandler(MouseEvent.MOUSE_CLICKED, (mouseEvent) -> {
				isOpen = false;
				dialog.close();
			});
			dialogLayout.setActions(btn);
		} else {
			dialogLayout.setActions(btn[0]);
		}
		dialogLayout.setBody(new Label(msg));
		dialog.setOnDialogClosed((JFXDialogEvent e) -> {
			nodeToBeBlue.setEffect(null);
			root.requestFocus();
		});
		dialog.show();
		isOpen = true;
		nodeToBeBlue.setEffect(blue);
	}

	public static void DialogOK(StackPane root, String msg, Node nodeToBeBlue) {
		if (!isOpen)
			new MaterialDialog(root, msg, nodeToBeBlue, "OK", "");
	}

	public static JFXButton[] confirmDialog(StackPane root, String msg, Node nodeToBeBlue) {
		new MaterialDialog(root, msg, nodeToBeBlue, "OK", "Cancel");
		return btn;
	}
}
