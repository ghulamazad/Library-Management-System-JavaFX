package com.ghulam.bookissuereturn;

import java.net.URL;
import java.util.ResourceBundle;

import com.ghulam.database.table.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StudentList implements Initializable {
	@FXML
	private BorderPane rootPane;
	@FXML
	private JFXTextField txtSearch;
	@FXML
	private JFXButton btnSubmit;
	@FXML
	private TableView<Student> table;
	@FXML
	private TableColumn<Student, Integer> colStudentID;
	@FXML
	private TableColumn<Student, String> colStudentName;
	@FXML
	private TableColumn<Student, String> colClass;
	@FXML
	private TableColumn<Student, Integer> colRoll;
	@FXML
	private TableColumn<Student, JFXCheckBox> colAction;
	private ObservableList<Student> list;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = BookIssueReturnController.getStudentObservableList();
		colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colClass.setCellValueFactory(new PropertyValueFactory<>("cls"));
		colRoll.setCellValueFactory(new PropertyValueFactory<>("roll"));
		colAction.setCellValueFactory(new PropertyValueFactory<>("onAction"));
		table.setItems(list);
		FilteredList<Student> filteredList = new FilteredList<>(list, e -> true);
		txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(student -> {
				if (newValue == null || newValue.isEmpty())
					return true;
				String data = newValue.toLowerCase();
				if (String.valueOf(student.getStudentID()).toLowerCase().contains(data)
						|| student.getName().toLowerCase().contains(data)
						|| String.valueOf(student.getRoll()).toLowerCase().contains(data)
						|| student.getCls().toLowerCase().contains(data))
					return true;
				return false;
			});
		});
		SortedList<Student> sortedList = new SortedList<>(filteredList);
		sortedList.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(sortedList);
	}

	@FXML
	void btnSubmitOnAction(ActionEvent event) {
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();
	}
}
