package com.ghulam.bookissuereturn;

import java.net.URL;
import java.util.ResourceBundle;

import com.ghulam.database.table.Book;
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

public class BookList implements Initializable {
	@FXML
	private BorderPane rootPane;
	@FXML
	private JFXTextField txtSearch;
	@FXML
	private JFXButton btnSubmit;
	@FXML
	private TableView<Book> table;
	@FXML
	private TableColumn<Book, Integer> colBookID;
	@FXML
	private TableColumn<Book, String> colNameBook;
	@FXML
	private TableColumn<Book, String> colAuthorName;
	@FXML
	private TableColumn<Book, String> colDescription;
	@FXML
	private TableColumn<Book, Integer> colStock;
	@FXML
	private TableColumn<Book, JFXCheckBox> colAction;
	private ObservableList<Book> list;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = BookIssueReturnController.getBookObservableList();
		colBookID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNameBook.setCellValueFactory(new PropertyValueFactory<>("name"));
		colAuthorName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
		colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
		table.setItems(list);
		FilteredList<Book> filteredList = new FilteredList<>(list, e -> true);
		txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(book -> {
				if (newValue == null || newValue.isEmpty())
					return true;
				String data = newValue.toLowerCase();
				if (String.valueOf(book.getId()).toLowerCase().contains(data)
						|| book.getName().toLowerCase().contains(data)
						|| book.getAuthorName().toLowerCase().contains(data))
					return true;
				return false;
			});
		});
		SortedList<Book> sortedData = new SortedList<>(filteredList);
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(sortedData);

	}

	@FXML
	void btnSubmitOnAction(ActionEvent event) {
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();
	}
}
