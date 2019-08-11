package com.ghulam.bookissuereturn;
import com.ghulam.Main;
import com.ghulam.alert.MaterialDialog;
import com.ghulam.database.dao.BookDAO;
import com.ghulam.database.dao.BookIssueReturnDAO;
import com.ghulam.database.dao.StudentDAO;
import com.ghulam.database.table.Book;
import com.ghulam.database.table.BookIssueReturn;
import com.ghulam.database.table.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class BookIssueReturnController implements Initializable {
    @FXML
    private Label lblDate;
    @FXML
    private JFXButton btnAddBook;
    @FXML
    private JFXButton btnAddStudent;
    @FXML
    private JFXButton btnRefresh;
    @FXML
    private Label lblIssueID;
    @FXML
    private Label lblNoOfStudent;
    @FXML
    private Label lblNoOfBooks;
    @FXML
    private JFXButton btnIssueBook;
    @FXML
    private ImageView imgBookIssue;
    @FXML
    private JFXComboBox<Integer> txtBookIssueId;
    @FXML
    private JFXButton btnReturn;
    @FXML
    private ImageView imgRStudent;
    @FXML
    private Label lblRStudentId;
    @FXML
    private Label lblRStudentName;
    @FXML
    private Label lblRRoll;
    @FXML
    private Label lblRClass;
    @FXML
    private Label lblRMobile;
    @FXML
    private ImageView imgRBook;
    @FXML
    private Label lblRBookId;
    @FXML
    private Label lblRBookName;
    @FXML
    private Label lblRBookAuthor;
    @FXML
    private ImageView imgRIssue;
    @FXML
    private Label lblRIssueDate;
    @FXML
    private Label lblRRetrunDate;
    @FXML
    private Label lblBookAvailable;
    private StackPane stackPane;
    private AnchorPane rootPane;
    private StudentDAO studentDAO;
    private BookDAO bookDAO;
    private BookIssueReturnDAO bookIssueReturnDAO;
    private Student student = null;
    private Book book = null;
    private BookIssueReturn bookIssueReturn = null;
    private DateFormat df,df1;
    private final String inStock="In Stock";
    private final String outOfStock="Out Of Stock";
    private static ObservableList<Book> bookObservableList=FXCollections.observableArrayList();
    private static ObservableList<Student> studentObservableList=FXCollections.observableArrayList();
    private ObservableList<Integer> issueBookList=FXCollections.observableArrayList();
    private ArrayList<Student> selectedStudentList;
    private ArrayList<Book> selectedBookList;
    public void setPane(StackPane stackPane, AnchorPane rootPane) {
        this.stackPane = stackPane;
        this.rootPane = rootPane;
    }

    public static ObservableList<Book> getBookObservableList(){
        return bookObservableList;
    }
    public static ObservableList<Student> getStudentObservableList(){
        return studentObservableList;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedStudentList=new ArrayList<>();
        selectedBookList=new ArrayList<>();
        imgRBook.setImage(new Image(Main.class.getResource("images/bookstack_white.png").toString()));
        imgRStudent.setImage(new Image(Main.class.getResource("images/student.png").toString()));
        imgRIssue.setImage(new Image(Main.class.getResource("images/list.png").toString()));
        imgBookIssue.setImage(new Image(Main.class.getResource("images/bookissue.png").toString()));
        setVisibleRetrunBook(false);
        studentDAO = new StudentDAO();
        bookDAO = new BookDAO();
        bookIssueReturnDAO = new BookIssueReturnDAO();
        //Add Observable List from Database
        bookObservableList.clear();
        studentObservableList.clear();
        bookObservableList.addAll(bookDAO.getBooksArrayList());
        studentObservableList.addAll(studentDAO.getStudentsArrayList());
        issueBookList.addAll(bookIssueReturnDAO.getBooksIssueIDArrayList());
        txtBookIssueId.setItems(issueBookList);
        df = new SimpleDateFormat("dd MMMM yyyy");
        df1 = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(String.valueOf(df.format(new Date())));
        generateIssueId();
    }

    // Issue book coding start here
    private void openDialogSatge(String str){
        try {
            Parent root=FXMLLoader.load(getClass().getResource(str));
            Stage stage = new Stage();
            stage.setScene(new Scene(root,800,600));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.centerOnScreen();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLblNoOfStudent(){
        selectedStudentList.clear(); // Clear List
        studentObservableList.sort((o1,o2)-> {
                if(o1.getOnAction().isSelected())
                    return 0;
                return 1;
        });
        for(Student student:studentObservableList) {
            if (student.getOnAction().isSelected()) {
                selectedStudentList.add(student);
            }
            else
                break;
        }
        lblNoOfStudent.setText("No. of Students : "+selectedStudentList.size());
    }
    private void setLblNoOfBooks(){
        selectedBookList.clear(); // Clear List
        bookObservableList.sort(((o1, o2) -> {
            if(o1.getAction().isSelected())
                return 0;
            return 1;
        }));
        for(Book book:bookObservableList){
            if(book.getAction().isSelected()) {
                selectedBookList.add(book);
            }
        }
        lblNoOfBooks.setText("No. of Students : "+selectedBookList.size());
    }
    private void checkBookAvailability(){
        int noOfStudent=selectedStudentList.size();
        boolean available=true;
        for(Book book:selectedBookList){
            if(book.getStock()<noOfStudent) {
                available = false;
                break;
            }
        }
        if(available)
           lblBookAvailable.setText(inStock);
        else
           lblBookAvailable.setText(outOfStock);

    }
    @FXML
    void btnAddStudentOnAction(ActionEvent event) {
        openDialogSatge("studentList.fxml");
        setLblNoOfStudent();
        checkBookAvailability();
    }
    @FXML
    void btnAddBookOnAction(ActionEvent event) {
        openDialogSatge("bookList.fxml");
        setLblNoOfBooks();
        checkBookAvailability();
    }
    private void generateIssueId() {
         lblIssueID.setText(String.valueOf(bookIssueReturnDAO.getLast() + 1));
    }

    @FXML
    void btnIssueBookOnAction(ActionEvent event) {
        int issueId=Integer.parseInt(lblIssueID.getText());
        bookIssueReturn = new BookIssueReturn();
        // set the date
        bookIssueReturn.setIssueDate(java.sql.Date.valueOf((df1.format(new Date()))));

        if(lblBookAvailable.getText().equalsIgnoreCase(inStock)){
            for(Book book:selectedBookList) {
                for(Student student:selectedStudentList) {
                    bookIssueReturn.setIssueId(issueId++);
                    bookIssueReturn.setBookId(book.getId());
                    bookIssueReturn.setStudentId(student.getStudentID());
                    bookIssueReturnDAO.addData(bookIssueReturn);
                }
            }
            MaterialDialog.DialogOK(stackPane,(selectedBookList.size()*selectedStudentList.size())+" Record(s) successfully saved.",rootPane);
            btnRefresh.fire();
        }
        else{
            MaterialDialog.DialogOK(stackPane,"Book not available!!! Please try another book",rootPane);
        }
    }
    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        generateIssueId();
        studentObservableList.clear();
        bookObservableList.clear();
        bookObservableList.addAll(bookDAO.getBooksArrayList());
        studentObservableList.addAll(studentDAO.getStudentsArrayList());
        lblBookAvailable.setText("");
        lblNoOfBooks.setText("No. of Books : "+0);
        lblNoOfStudent.setText("No. of Students : "+0);
        issueBookList.clear();
        issueBookList.addAll(bookIssueReturnDAO.getBooksIssueIDArrayList());
        txtBookIssueId.setItems(issueBookList);
    }
    // Issue book coding end here

    //Return Book coding start here


    @FXML
    void txtBookIssueIdOnAction(ActionEvent event) {

        if (!txtBookIssueId.getSelectionModel().isEmpty()) {
            bookIssueReturn = bookIssueReturnDAO.getData(txtBookIssueId.getSelectionModel().getSelectedItem());
            if (bookIssueReturn != null) {
                lblRIssueDate.setText(df.format(bookIssueReturn.getIssueDate()));
                lblRRetrunDate.setText(df.format(new Date()));

                //retrieve Book Data.
                book = bookDAO.getBook(bookIssueReturn.getBookId());
                lblRBookId.setText(String.valueOf(book.getId()));
                lblRBookName.setText(book.getName());
                lblRBookAuthor.setText(book.getAuthorName());
                //retrieve Student Data.
                student = studentDAO.getStudent(bookIssueReturn.getStudentId());
                lblRStudentId.setText(String.valueOf(student.getStudentID()));
                lblRStudentName.setText(student.getName());
                lblRRoll.setText(String.valueOf(student.getRoll()));
                lblRClass.setText(student.getCls());
                lblRMobile.setText(String.valueOf(student.getMobile_no()));
                setVisibleRetrunBook(true);
            } else
                MaterialDialog.DialogOK(stackPane, "Data not found.", rootPane);
        }
    }

    private void setVisibleRetrunBook(Boolean aFlag) {
        imgRStudent.setVisible(aFlag);
        lblRStudentId.setVisible(aFlag);
        lblRStudentName.setVisible(aFlag);
        lblRRoll.setVisible(aFlag);
        lblRClass.setVisible(aFlag);
        lblRMobile.setVisible(aFlag);
        imgRBook.setVisible(aFlag);
        lblRBookId.setVisible(aFlag);
        lblRBookName.setVisible(aFlag);
        lblRBookAuthor.setVisible(aFlag);
        imgRIssue.setVisible(aFlag);
        lblRIssueDate.setVisible(aFlag);
        lblRRetrunDate.setVisible(aFlag);
    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {
        if (isEmptyBookReturn()) {
            if (bookIssueReturnDAO.deleteData(txtBookIssueId.getSelectionModel().getSelectedItem()) > 0) {
                MaterialDialog.DialogOK(stackPane, "Book successfully returned.", rootPane);
                setVisibleRetrunBook(false);
                clearReturnBookFields();
                btnRefresh.fire();
            } else
                MaterialDialog.DialogOK(stackPane, "Book not be returned.", rootPane);
        } else
            MaterialDialog.DialogOK(stackPane, "All fields required.", rootPane);
    }

    private void clearReturnBookFields() {
        txtBookIssueId.getSelectionModel().select(-1);
        lblRStudentId.setText("");
        lblRStudentName.setText("");
        lblRRoll.setText("");
        lblRClass.setText("");
        lblRMobile.setText("");
        lblRBookId.setText("");
        lblRBookName.setText("");
        lblRBookAuthor.setText("");
        lblRIssueDate.setText("");
        lblRRetrunDate.setText("");
    }

    private boolean isEmptyBookReturn() {
        if(txtBookIssueId.getSelectionModel().toString().isEmpty())
            return false;
        if(lblRStudentId.getText().trim().isEmpty())
            return false;
        if(lblRBookId.getText().trim().isEmpty())
            return false;
        return true;
    }
    // Return book coding end here
}
