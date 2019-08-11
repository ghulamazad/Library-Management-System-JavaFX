package com.ghulam;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Rectangle rect = new Rectangle(661, 325);
		rect.setArcHeight(26.0);
		rect.setArcWidth(26.0);
		Parent root = FXMLLoader.load(getClass().getResource("splashScreen/splashScreen.fxml"));
		root.setClip(rect);
		Scene scene = new Scene(root, 661, 325);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
