package com.ghulam.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.ghulam.Main;
import com.ghulam.about.AboutController;
import com.ghulam.bookinfo.BookInfoController;
import com.ghulam.bookissuereturn.BookIssueReturnController;
import com.ghulam.dashboard.DashboardController;
import com.ghulam.setting.SettingController;
import com.ghulam.studentinfo.StudentInfoController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.transitions.JFXFillTransition;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MenuController implements Initializable {
	private final static Image DASHBOARD_BLACK = new Image(
			Main.class.getResource("images/dashboard_black.png").toString());
	private final static Image DASHBOARD_WHITE = new Image(
			Main.class.getResource("images/dashboard_white.png").toString());
	private final static Image ISSUE_RETURN_BALCK = new Image(
			Main.class.getResource("images/return_issueBook_balck.png").toString());
	private final static Image ISSUE_RETURN_WHITE = new Image(
			Main.class.getResource("images/return_issueBook_white.png").toString());
	private final static Image STUDENTINFO_BLACK = new Image(
			Main.class.getResource("images/students_black.png").toString());
	private final static Image STUDENTINFO_WHITE = new Image(
			Main.class.getResource("images/students_white.png").toString());
	private final static Image BOOKINFO_BLACK = new Image(Main.class.getResource("images/books_black.png").toString());
	private final static Image BOOKINFO_WHITE = new Image(Main.class.getResource("images/books_white.png").toString());
	private final static Image SETTING_BLACK = new Image(Main.class.getResource("images/setting_black.png").toString());
	private final static Image SETTING_WHITE = new Image(Main.class.getResource("images/setting_white.png").toString());
	private final static Image ABOUT_BLACK = new Image(Main.class.getResource("images/about_black.png").toString());
	private final static Image ABOUT_WHITE = new Image(Main.class.getResource("images/about_white.png").toString());
	@FXML
	private StackPane stackRootPane;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private JFXButton btnDashboard;

	@FXML
	private ImageView imgDashboard;

	@FXML
	private JFXButton btnIsuueReturnBook;

	@FXML
	private ImageView imgIsuueReturnBook;

	@FXML
	private JFXButton btnStudentInfo;

	@FXML
	private ImageView imgStudentInfo;

	@FXML
	private JFXButton btnBookInfo;

	@FXML
	private ImageView imgBookInfo;

	@FXML
	private JFXButton btnSetting;

	@FXML
	private ImageView imgSetting;

	@FXML
	private JFXButton btnAbout;

	@FXML
	private ImageView imgAbout;

	@FXML
	private AnchorPane centerPane;

	private JFXButton activeMenuButton;
	private JFXFillTransition ft;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ft = new JFXFillTransition();
		activeMenuButton = btnDashboard;
		btnDashboard.fire();
	}

	@FXML
	void btnAboutClick(ActionEvent event) {
		selectMenu("about/about.fxml", event, "About");
	}

	@FXML
	void btnBookInfoClick(ActionEvent event) {
		selectMenu("bookinfo/bookinfo.fxml", event, "BookInfo");
	}

	@FXML
	void btnStudentInfoClick(ActionEvent event) {
		selectMenu("studentinfo/studentinfo.fxml", event, "StudentInfo");
	}

	@FXML
	public void btnIsuueReturnBookClick(ActionEvent event) {
		selectMenu("bookissuereturn/bookIssueReturn.fxml", event, "BookIssueReturn");
	}

	@FXML
	void btnDashboardClick(ActionEvent event) {
		selectMenu("dashboard/dashboard.fxml", event, "Dashboard");
	}

	@FXML
	void btnSettingClick(ActionEvent event) {
		selectMenu("setting/setting.fxml", event, "Setting");
	}

	@FXML
	void menuButtonMouseEntered(MouseEvent event) {
		if (event.getSource() != activeMenuButton) {
			ft = new JFXFillTransition();
			ft.setRegion((JFXButton) event.getSource());
			ft.setDuration(new Duration(500));
			ft.setFromValue(Color.WHITE);
			ft.setToValue(Color.rgb(214, 214, 214));
			ft.play();
		}
	}

	@FXML
	void menuButtonMouseExited(MouseEvent event) {
		if (event.getSource() != activeMenuButton) {
			ft = new JFXFillTransition();
			ft.setRegion((JFXButton) event.getSource());
			ft.setDuration(new Duration(500));
			ft.setFromValue(Color.rgb(214, 214, 214));
			ft.setToValue(Color.WHITE);
			ft.play();
		}
	}

	private void selectMenu(String fxmlName, ActionEvent event, String controllerClass) {
		if (ft.getStatus() == Animation.Status.RUNNING)
			ft.stop();
		defaultButtonImage(((JFXButton) event.getSource()).getId());
		activeMenuButton.getStyleClass().remove("menuButtonActive");
		((JFXButton) event.getSource()).getStyleClass().add("menuButtonActive");
		activeMenuButton = ((JFXButton) event.getSource());
		FXMLLoader fxmlLoader = new FXMLLoader();
		try {
			fxmlLoader.load(Main.class.getResource(fxmlName).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (controllerClass.equalsIgnoreCase("Dashboard"))
			((DashboardController) fxmlLoader.getController()).setPane(stackRootPane, anchorPane);
		else if (controllerClass.equalsIgnoreCase("BookIssueReturn"))
			((BookIssueReturnController) fxmlLoader.getController()).setPane(stackRootPane, anchorPane);
		else if (controllerClass.equalsIgnoreCase("StudentInfo"))
			((StudentInfoController) fxmlLoader.getController()).setPane(stackRootPane, anchorPane);
		else if (controllerClass.equalsIgnoreCase("BookInfo"))
			((BookInfoController) fxmlLoader.getController()).setPane(stackRootPane, anchorPane);
		else if (controllerClass.equalsIgnoreCase("Setting"))
			((SettingController) fxmlLoader.getController()).setPane(stackRootPane, anchorPane);
		else if (controllerClass.equalsIgnoreCase("About"))
			((AboutController) fxmlLoader.getController()).setPane(stackRootPane, anchorPane);
		AnchorPane root = fxmlLoader.getRoot();
		centerPane.getChildren().clear();
		centerPane.getChildren().add(root);
		// this method is used to auto adjust height and weight
		AnchorPane.setTopAnchor(root, 0.0);
		AnchorPane.setRightAnchor(root, 0.0);
		AnchorPane.setBottomAnchor(root, 0.0);
		AnchorPane.setLeftAnchor(root, 0.0);
	}

	private void defaultButtonImage(String btnName) {
		imgDashboard.setImage(DASHBOARD_BLACK);
		imgIsuueReturnBook.setImage(ISSUE_RETURN_BALCK);
		imgStudentInfo.setImage(STUDENTINFO_BLACK);
		imgBookInfo.setImage(BOOKINFO_BLACK);
		imgSetting.setImage(SETTING_BLACK);
		imgAbout.setImage(ABOUT_BLACK);
		if (btnName.equalsIgnoreCase(btnDashboard.getId()))
			imgDashboard.setImage(DASHBOARD_WHITE);
		else if (btnName.equalsIgnoreCase(btnIsuueReturnBook.getId()))
			imgIsuueReturnBook.setImage(ISSUE_RETURN_WHITE);
		else if (btnName.equalsIgnoreCase(btnStudentInfo.getId()))
			imgStudentInfo.setImage(STUDENTINFO_WHITE);
		else if (btnName.equalsIgnoreCase(btnBookInfo.getId()))
			imgBookInfo.setImage(BOOKINFO_WHITE);
		else if (btnName.equalsIgnoreCase(btnSetting.getId()))
			imgSetting.setImage(SETTING_WHITE);
		else if (btnName.equalsIgnoreCase(btnAbout.getId()))
			imgAbout.setImage(ABOUT_WHITE);
	}

}
