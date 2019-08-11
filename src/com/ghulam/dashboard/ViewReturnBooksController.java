package com.ghulam.dashboard;

import com.ghulam.alert.MaterialDialog;
import com.ghulam.database.dao.IssueBookDBHistoryDAO;
import com.ghulam.database.table.IssueBookDBHistory;
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

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewReturnBooksController  implements Initializable,Reportable {
    @FXML
    private JFXButton btnRefresh;
    @FXML
    private TableView<IssueBookDBHistory> dataTable;
    @FXML
    private TableColumn<IssueBookDBHistory, Integer> issueId;
    @FXML
    private TableColumn<IssueBookDBHistory, Integer> bookId;
    @FXML
    private TableColumn<IssueBookDBHistory, Integer> studentId;
    @FXML
    private TableColumn<IssueBookDBHistory, Date> issueDate;
    @FXML
    private TableColumn<IssueBookDBHistory, Date> returnDate;
    @FXML
    private JFXDatePicker datePicker;
    private ObservableList<IssueBookDBHistory> list = FXCollections.observableArrayList();
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
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }
    @Override
    public void setPane(StackPane stackPane, AnchorPane rootPane) {
        this.stackPane = stackPane;
        this.rootPane = rootPane;
    }

    public void loadData() {
        list.clear();
        list.addAll(new IssueBookDBHistoryDAO().getBooksArrayList());
        dataTable.setItems(list);
    }

    @FXML
    void datePickerOnAction(ActionEvent event) {
        LocalDate date=datePicker.getValue();
        list.clear();
        list.addAll(new IssueBookDBHistoryDAO().getBooksArrayList(java.sql.Date.valueOf(date)));
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
