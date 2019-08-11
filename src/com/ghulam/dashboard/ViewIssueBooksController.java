package com.ghulam.dashboard;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.ghulam.alert.MaterialDialog;
import com.ghulam.database.dao.BookIssueReturnDAO;
import com.ghulam.database.table.BookIssueReturn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ViewIssueBooksController implements Initializable, Reportable {
	@FXML
	private JFXButton btnRefresh;
	@FXML
	private TableView<BookIssueReturn> dataTable;
	@FXML
	private TableColumn<BookIssueReturn, Integer> issueId;
	@FXML
	private TableColumn<BookIssueReturn, Integer> bookId;
	@FXML
	private TableColumn<BookIssueReturn, Integer> studentId;
	@FXML
	private TableColumn<BookIssueReturn, Date> issueDate;
	@FXML
	private TableColumn<BookIssueReturn, Date> returnDate;
	@FXML
	private JFXDatePicker datePicker;
	private ObservableList<BookIssueReturn> list = FXCollections.observableArrayList();
	private StackPane stackPane;
	private AnchorPane rootPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniCol();
		loadData();
	}

	private void iniCol() {
		issueId.setCellValueFactory(new PropertyValueFactory<>("issueId"));
		bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
		studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
		issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
	}

	public void loadData() {
		list.clear();
		list.addAll(new BookIssueReturnDAO().getBooksArrayList());
		dataTable.setItems(list);
	}

	@FXML
	void datePickerOnAction(ActionEvent event) {
		LocalDate date = datePicker.getValue();
		list.clear();
		list.addAll(new BookIssueReturnDAO().getBooksArrayList(java.sql.Date.valueOf(date)));
		if (list.isEmpty()) {
			MaterialDialog.DialogOK(stackPane, "Data not found.", rootPane);
		} else {
			MaterialDialog.DialogOK(stackPane, list.size() + " Record(s) found.", rootPane);
			dataTable.setItems(list);
		}
	}

	@Override
	public void setPane(StackPane stackPane, AnchorPane rootPane) {
		this.stackPane = stackPane;
		this.rootPane = rootPane;
	}

	@FXML
	public void btnRefreshOnAction(ActionEvent actionEvent) {
		loadData();
	}
}
