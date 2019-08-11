package com.ghulam.bookinfo;

import com.ghulam.alert.MaterialDialog;
import com.ghulam.database.dao.BookDAO;
import com.ghulam.database.table.Book;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookInfoController implements Initializable {
    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnRefresh;
    @FXML
    private JFXTextField txtBookId;

    @FXML
    private JFXTextField txtBookName;

    @FXML
    private JFXTextField txtAuthorName;

    @FXML
    private JFXTextField txtStocks;

    @FXML
    private JFXTextArea txtDescription;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TableView<Book> table;

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
    private Book book;
    private BookDAO bookDAO;
    private StackPane stackPane;
    private AnchorPane rootPane;
    private ObservableList<Book> list = FXCollections.observableArrayList();

    public void setPane(StackPane stackPane, AnchorPane rootPane) {
        this.stackPane = stackPane;
        this.rootPane = rootPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookDAO = new BookDAO();
        generateBookId();
        iniCol();
        loadData();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // First row automatically selected
        if (table.getItems().size()>0) {
            table.getSelectionModel().select(0);
            book = table.getSelectionModel().getSelectedItem();
            retriveObjectData();
        }
        //numeric validation
        AtomicBoolean isNumeric= new AtomicBoolean(false);
        txtStocks.addEventHandler(KeyEvent.KEY_TYPED,(event -> {
            if(!isNumeric.get()) {
                MaterialDialog.DialogOK(stackPane, "Only numeric value accepted.", rootPane);
                event.consume();
            }
        }));
        txtStocks.addEventHandler(KeyEvent.KEY_PRESSED,(event -> {
            if(event.getText().matches("[0-9]") || event.getCode()==KeyCode.ENTER || event.getCode()==KeyCode.BACK_SPACE || event.getCode()==KeyCode.DELETE)
                isNumeric.set(true);
            else
                isNumeric.set(false);
        }));
    }

    private void generateBookId() {
        txtBookId.setText(String.valueOf(bookDAO.getLast() + 1));
    }

    private void loadData() {
        list.clear();
        list.addAll(bookDAO.getBooksArrayList());
        table.setItems(list);
    }

    private void iniCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    private void disableTextFields(boolean aFlag){
        txtBookName.setDisable(aFlag);
        txtAuthorName.setDisable(aFlag);
        txtStocks.setDisable(aFlag);
        txtDescription.setDisable(aFlag);
    }
    private void changeButtonText(JFXButton btn) {
        disableTextFields(false);
        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        btn.setText("Save");
        btn.setDisable(false);
        btnDelete.setText("Cancel");
    }
    private void enableButtons() {
        disableTextFields(true);
        btnAdd.setText("Add New");
        btnUpdate.setText("Edit");
        btnDelete.setText("Delete");
        btnAdd.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        table.getSelectionModel().select(0);
        book = table.getSelectionModel().getSelectedItem();
        retriveObjectData();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (btnAdd.getText().equalsIgnoreCase("Add New")) {
            changeButtonText(btnAdd);
            clearFields();
        } else {
                if(!retriveFieldsData())
                    return;
                if (bookDAO.addBook(book) > 0) {
                    MaterialDialog.DialogOK(stackPane, "Record successfully saved.", rootPane);
                    loadData();
                    enableButtons();
                } else
                    MaterialDialog.DialogOK(stackPane, "Record not be saved.", rootPane);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (btnUpdate.getText().equalsIgnoreCase("Edit")) {
            changeButtonText(btnUpdate);
        } else {
                if(!retriveFieldsData())
                    return;
                int row = bookDAO.updateBook(book);
                if (row > 0) {
                    MaterialDialog.DialogOK(stackPane, row + " Record(s) successfully updated.", rootPane);
                    loadData();
                    enableButtons();
                } else
                    MaterialDialog.DialogOK(stackPane, "Record not be updated.", rootPane);
        }
    }

    private void clearFields() {
        generateBookId();
        txtBookName.setText("");
        txtAuthorName.setText("");
        txtStocks.setText("");
        txtDescription.setText("");
    }

    // retrive fields data in object
    private boolean retriveFieldsData() {
        if (isEmptyFields()) {
            book = new Book();
            book.setId(Integer.parseInt(txtBookId.getText().trim()));
            book.setName(txtBookName.getText());
            book.setAuthorName(txtAuthorName.getText());
            book.setStock(Integer.parseInt(txtStocks.getText().trim()));
            book.setDescription(txtDescription.getText());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            book.setDate(java.sql.Date.valueOf((df.format(new Date()))));
        } else{
            MaterialDialog.DialogOK(stackPane, "All fields required.", rootPane);
            return false;
        }
        return true;
    }
    private boolean isEmptyFields() {
        if (txtBookId.getText().isEmpty()) return false;
        if (txtBookName.getText().isEmpty()) return false;
        if (txtAuthorName.getText().isEmpty()) return false;
        if (txtStocks.getText().isEmpty()) return false;
        if (txtDescription.getText().isEmpty()) return false;
        return true;
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (btnDelete.getText().equalsIgnoreCase("Cancel")) {
            enableButtons();
        } else {
            deleteBook(Integer.parseInt(txtBookId.getText()));
        }
    }

    @FXML
    public void menuDeleteOnAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            ObservableList<Book> deletionBookList = table.getSelectionModel().getSelectedItems();
            int[] id = new int[deletionBookList.size()];
            int i = 0;
            for (Book book : deletionBookList)
                id[i++] = book.getId();
            deleteBook(id);
        } else
            MaterialDialog.DialogOK(stackPane, "Please select record(s)", rootPane);
    }

    private void deleteBook(int... id) {
        JFXButton[] btn = MaterialDialog.confirmDialog(stackPane, "Are you sure you wanna delete(s) this record?", rootPane);
        btn[0].addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            int row = bookDAO.deleteBook(id);
            if (row > 0) {
                MaterialDialog.DialogOK(stackPane, row + " Record(s) successfully deleted.", rootPane);
                loadData();
            } else
                MaterialDialog.DialogOK(stackPane, "Record not be deleted.", rootPane);
        });
        btn[1].addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            MaterialDialog.DialogOK(stackPane, "Deletion process cancel.", rootPane);
        });
    }


    @FXML
    public void tableMouseClicked(MouseEvent event) {
        if (!table.getSelectionModel().isEmpty()) {
            book = table.getSelectionModel().getSelectedItem();
            retriveObjectData();
        }
    }

    // retrive object data in fields
    private void retriveObjectData() {
        txtBookId.setText(String.valueOf(book.getId()));
        txtBookName.setText(book.getName());
        txtAuthorName.setText(book.getAuthorName());
        txtStocks.setText(String.valueOf(book.getStock()));
        txtDescription.setText(book.getDescription());
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        searchRecord();
    }

    @FXML
    void txtSearchKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            searchRecord();
    }

    private void searchRecord() {
        list.clear();
        list.addAll(bookDAO.getBooksArrayList(txtSearch.getText()));
        if (list.isEmpty())
            MaterialDialog.DialogOK(stackPane, "Not found.", rootPane);
        else {
            MaterialDialog.DialogOK(stackPane, list.size() + " Record(s) found.", rootPane);
            table.setItems(list);
        }
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        txtSearch.setText("");
        loadData();
    }

}