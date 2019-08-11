package com.ghulam.dashboard;

import com.ghulam.alert.MaterialDialog;
import com.ghulam.database.dao.BookDAO;
import com.ghulam.database.table.Book;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewAllBooksController implements Initializable,Reportable {
    @FXML
    private JFXButton btnRefresh;
    @FXML
    private TableView<Book> dataTable;
    @FXML
    private TableColumn<Book, Integer> idCol;
    @FXML
    private TableColumn<Book, String> bookNameCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> descriptionCol;
    @FXML
    private TableColumn<Book, Integer> stockCol;
    @FXML
    private Label lblHeader;
    @FXML
    private JFXDatePicker datePicker;
    private ObservableList<Book> list = FXCollections.observableArrayList();
    private StackPane stackPane;
    private AnchorPane rootPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iniCol();
        loadData();
    }
    @Override
    public void setPane(StackPane stackPane, AnchorPane rootPane) {
        this.stackPane = stackPane;
        this.rootPane = rootPane;
    }


    private void iniCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    public void loadData() {
        list.clear();
        list.addAll(new BookDAO().getBooksArrayList());
        dataTable.setItems(list);
    }

    @FXML
    void datePickerOnAction(ActionEvent event) {
        LocalDate date=datePicker.getValue();
        list.clear();
        list.addAll(new BookDAO().getBooksArrayList(java.sql.Date.valueOf(date)));
        if(list.isEmpty()){
            MaterialDialog.DialogOK(stackPane, "Data not found.", rootPane);
        }else {
            MaterialDialog.DialogOK(stackPane, list.size() + " Record(s) found.", rootPane);
            dataTable.setItems(list);
        }
    }
@FXML
    public void btnRefreshOnAction(ActionEvent actionEvent) {
        loadData();
    }
}
