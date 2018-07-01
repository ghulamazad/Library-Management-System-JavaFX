package com.ghulam.login;

import com.ghulam.Main;
import com.ghulam.alert.MaterialDialog;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private ImageView bgImage;
    @FXML
    void btnCancelClick(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void btnLoginClick(ActionEvent event) {
        openDashboard();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    private void openDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("menu/menu.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Scene scene = new Scene(parent);
            Stage dashboardStage = new Stage();
            dashboardStage.setMinHeight(626.0);
            dashboardStage.setMinWidth(926.0);
            dashboardStage.setScene(scene);
            dashboardStage.setMaximized(true);
            dashboardStage.show();
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            MaterialDialog.DialogOK(stackPane,e.toString(),rootPane);
        }
    }

}
