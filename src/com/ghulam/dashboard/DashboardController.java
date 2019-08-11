package com.ghulam.dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.ghulam.Main;
import com.jfoenix.transitions.JFXFillTransition;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class DashboardController implements Initializable {
	@FXML
	private AnchorPane totalBook3dAnchor;

	@FXML
	private Label lblBook;

	@FXML
	private AnchorPane totalBookRoundAnchor;

	@FXML
	private ImageView imgTotalBook;

	@FXML
	private AnchorPane issue3dAnchor;

	@FXML
	private Label lblIssue;

	@FXML
	private AnchorPane issueRoundAnchor;

	@FXML
	private ImageView imgIssueBook;

	@FXML
	private AnchorPane returnBook3dAnchor;

	@FXML
	private Label lblReturn;

	@FXML
	private AnchorPane returnBookRoundAnchor;

	@FXML
	private ImageView imgRetrunBook;

	@FXML
	private AnchorPane dataReportViewer;

	private JFXFillTransition ft;
	private StackPane stackPane;
	private AnchorPane rootPane;

	public void setPane(StackPane stackPane, AnchorPane rootPane) {
		this.stackPane = stackPane;
		this.rootPane = rootPane;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imgTotalBook.setImage(new Image(Main.class.getResource("images/book-stack.png").toString()));
		imgIssueBook.setImage(new Image(Main.class.getResource("images/issue_book.png").toString()));
		imgRetrunBook.setImage(new Image(Main.class.getResource("images/return_book.png").toString()));
	}

	@FXML
	void anchorMouseEntered(MouseEvent event) {
		getAnchorPane((AnchorPane) event.getSource(), Color.rgb(0, 185, 255), Color.rgb(100, 255, 218));
	}

	@FXML
	void anchorMouseExited(MouseEvent event) {
		getAnchorPane((AnchorPane) event.getSource(), Color.rgb(100, 255, 218), Color.rgb(0, 185, 255));
	}

	@FXML
	void anchorMouseClick(MouseEvent event) {
		selectReport(((AnchorPane) event.getSource()).getId());
	}

	private void getAnchorPane(AnchorPane source, Color start, Color end) {
		if (source.getId().equalsIgnoreCase(totalBook3dAnchor.getId())
				|| source.getId().equalsIgnoreCase(totalBookRoundAnchor.getId())) {
			fillTransition(totalBook3dAnchor, start, end);
			fillTransition(totalBookRoundAnchor, start, end);
		} else if (source.getId().equalsIgnoreCase(issue3dAnchor.getId())
				|| source.getId().equalsIgnoreCase(issueRoundAnchor.getId())) {
			fillTransition(issue3dAnchor, start, end);
			fillTransition(issueRoundAnchor, start, end);
		} else if (source.getId().equalsIgnoreCase(returnBook3dAnchor.getId())
				|| source.getId().equalsIgnoreCase(returnBookRoundAnchor.getId())) {
			fillTransition(returnBook3dAnchor, start, end);
			fillTransition(returnBookRoundAnchor, start, end);
		}
	}

	private void fillTransition(AnchorPane anchorPane, Color start, Color end) {
		ft = new JFXFillTransition();
		ft.setRegion(anchorPane);
		ft.setDuration(new Duration(600));
		ft.setFromValue(start);
		ft.setToValue(end);
		ft.play();
	}

	private void selectReport(String id) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		String path = null;
		if (id.startsWith("totalBook"))
			path = "dashboard/ViewAllBooks.fxml";
		else if (id.startsWith("issue"))
			path = "dashboard/ViewIssueBooks.fxml";
		else if (id.startsWith("returnBook"))
			path = "dashboard/ViewReturnBooks.fxml";
		try {
			fxmlLoader.load(Main.class.getResource(path).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		AnchorPane root = fxmlLoader.getRoot();
		Reportable controller = fxmlLoader.getController();
		controller.setPane(stackPane, rootPane);
		dataReportViewer.getChildren().clear();
		dataReportViewer.getChildren().add(root);
		// this method is used to auto adjust height and weight
		AnchorPane.setTopAnchor(root, 0.0);
		AnchorPane.setRightAnchor(root, 0.0);
		AnchorPane.setBottomAnchor(root, 0.0);
		AnchorPane.setLeftAnchor(root, 0.0);
	}
}
