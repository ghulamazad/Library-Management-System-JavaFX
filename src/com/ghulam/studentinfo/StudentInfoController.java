package com.ghulam.studentinfo;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import com.ghulam.alert.MaterialDialog;
import com.ghulam.database.dao.StudentDAO;
import com.ghulam.database.table.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class StudentInfoController implements Initializable {
	@FXML
	private AnchorPane bookIssuePane;
	@FXML
	private JFXTextField txtSearch;
	@FXML
	private JFXButton btnSearch;
	@FXML
	private JFXButton btnRefresh;
	@FXML
	private JFXTextField txtSID;
	@FXML
	private JFXTextField txtStudentName;
	@FXML
	private JFXComboBox<String> txtClass;
	@FXML
	private JFXTextField txtRoll;
	@FXML
	private JFXTextField txtMobile;
	@FXML
	private JFXTextArea txtAddress;
	@FXML
	private JFXButton btnAdd;
	@FXML
	private JFXButton btnUpdate;
	@FXML
	private JFXButton btnDelete;
	@FXML
	private TableView<Student> table;
	@FXML
	private TableColumn<Student, Integer> idCol;
	@FXML
	private TableColumn<Student, String> studentNameCol;
	@FXML
	private TableColumn<Student, String> classCol;
	@FXML
	private TableColumn<Student, Integer> rollCol;
	@FXML
	private TableColumn<Student, Integer> mobileCol;
	@FXML
	private TableColumn<Student, String> addressCol;
	@FXML
	private TableColumn<Student, Integer> issueBookCol;
	private Student student;
	private StudentDAO studentDAO;
	private StackPane stackPane;
	private AnchorPane rootPane;
	private ObservableList<Student> list = FXCollections.observableArrayList();

	public void setPane(StackPane stackPane, AnchorPane rootPane) {
		this.stackPane = stackPane;
		this.rootPane = rootPane;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> classList = FXCollections.observableArrayList();
		classList.add("BCA-I");
		classList.add("BCA-II");
		classList.add("BCA-III");
		txtClass.setItems(classList);
		disableTextFields(true);
		studentDAO = new StudentDAO();
		generateStudentId();
		iniCol();
		loadData();
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// First row automatically selected
		if (table.getItems().size() > 0) {
			table.getSelectionModel().select(0);
			student = table.getSelectionModel().getSelectedItem();
			retriveObjectData();
		}
		// numeric validation
		AtomicBoolean isNumeric = new AtomicBoolean(false);
		txtRoll.addEventHandler(KeyEvent.KEY_TYPED, (event -> {
			if (!isNumeric.get()) {
				MaterialDialog.DialogOK(stackPane, "Only numeric value accepted.", rootPane);
				event.consume();
			}
		}));
		txtRoll.addEventHandler(KeyEvent.KEY_PRESSED, (event -> {
			if (event.getText().matches("[0-9]") || event.getCode() == KeyCode.ENTER
					|| event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE)
				isNumeric.set(true);
			else
				isNumeric.set(false);
		}));
		txtMobile.addEventHandler(KeyEvent.KEY_TYPED, (event -> {
			if (!isNumeric.get()) {
				MaterialDialog.DialogOK(stackPane, "Only numeric value accepted.", rootPane);
				event.consume();
			}
		}));
		txtMobile.addEventHandler(KeyEvent.KEY_PRESSED, (event -> {
			if (event.getText().matches("[0-9]") || event.getCode() == KeyCode.ENTER
					|| event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE)
				isNumeric.set(true);
			else
				isNumeric.set(false);
		}));
	}

	private void generateStudentId() {
		txtSID.setText(String.valueOf(studentDAO.getLast() + 1));
	}

	private void loadData() {
		list.clear();
		list.addAll(studentDAO.getStudentsArrayList());
		table.setItems(list);
	}

	private void iniCol() {
		idCol.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		studentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		classCol.setCellValueFactory(new PropertyValueFactory<>("cls"));
		rollCol.setCellValueFactory(new PropertyValueFactory<>("roll"));
		mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile_no"));
		addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
		issueBookCol.setCellValueFactory(new PropertyValueFactory<>("issuedBooks"));
	}

	private void disableTextFields(boolean aFlag) {
		txtStudentName.setDisable(aFlag);
		txtClass.setDisable(aFlag);
		txtRoll.setDisable(aFlag);
		txtMobile.setDisable(aFlag);
		txtAddress.setDisable(aFlag);
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
		student = table.getSelectionModel().getSelectedItem();
		retriveObjectData();
	}

	@FXML
	void btnAddOnAction(ActionEvent event) {
		if (btnAdd.getText().equalsIgnoreCase("Add New")) {
			changeButtonText(btnAdd);
			clearFields();
		} else {
			if (!retriveFieldsData())
				return;
			if (studentDAO.addStudent(student) > 0) {
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
			if (!retriveFieldsData())
				return;
			int row = studentDAO.updateStudent(student);
			if (row > 0) {
				MaterialDialog.DialogOK(stackPane, row + " Record(s) successfully updated.", rootPane);
				loadData();
				enableButtons();
			} else
				MaterialDialog.DialogOK(stackPane, "Record not be updated.", rootPane);
		}
	}

	private void clearFields() {
		generateStudentId();
		txtStudentName.setText("");
		txtClass.getSelectionModel().select(-1);
		txtRoll.setText("");
		txtMobile.setText("");
		txtAddress.setText("");
	}

	// retrive fields data in object
	private boolean retriveFieldsData() {
		if (isEmptyFields()) {
			student = new Student();
			student.setStudentID(Integer.parseInt(txtSID.getText().trim()));
			student.setName(txtStudentName.getText());
			student.setCls(txtClass.getSelectionModel().getSelectedItem().toString());
			student.setRoll(Integer.parseInt(txtRoll.getText().trim()));
			student.setMobile_no(Long.parseLong(txtMobile.getText().trim()));
			student.setAddress(txtAddress.getText());
			student.setIssuedBooks(0);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			student.setDate(java.sql.Date.valueOf((df.format(new Date()))));

		} else {
			MaterialDialog.DialogOK(stackPane, "All fields required.", rootPane);
			return false;
		}
		return true;
	}

	// retrive object data in fields
	private void retriveObjectData() {
		if (student != null) {
			txtSID.setText(String.valueOf(student.getStudentID()));
			txtStudentName.setText(student.getName());
			txtClass.getSelectionModel().select(student.getCls());
			txtRoll.setText(String.valueOf(student.getRoll()));
			txtMobile.setText(String.valueOf(student.getMobile_no()));
			txtAddress.setText(student.getAddress());
		}
	}

	private boolean isEmptyFields() {
		if (txtSID.getText().isEmpty())
			return false;
		if (txtStudentName.getText().isEmpty())
			return false;
		if (txtClass.getSelectionModel().getSelectedItem().toString().isEmpty())
			return false;
		if (txtRoll.getText().isEmpty())
			return false;
		if (txtMobile.getText().isEmpty())
			return false;
		if (txtAddress.getText().isEmpty())
			return false;
		return true;
	}

	@FXML
	void btnDeleteOnAction(ActionEvent event) {
		if (btnDelete.getText().equalsIgnoreCase("Cancel")) {
			enableButtons();
		} else {
			deleteStudent(Integer.parseInt(txtSID.getText()));
		}
	}

	@FXML
	public void menuDeleteOnAction(ActionEvent event) {
		if (table.getSelectionModel().getSelectedItem() != null) {
			ObservableList<Student> deletionStudentList = table.getSelectionModel().getSelectedItems();
			int[] id = new int[deletionStudentList.size()];
			int i = 0;
			for (Student student : deletionStudentList)
				id[i++] = student.getStudentID();
			deleteStudent(id);
		} else
			MaterialDialog.DialogOK(stackPane, "Please select record(s)", rootPane);
	}

	private void deleteStudent(int... id) {
		JFXButton[] btn = MaterialDialog.confirmDialog(stackPane, "Are you sure you wanna delete(s) this record?",
				rootPane);
		btn[0].addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			int row = studentDAO.deleteStudent(id);
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
			student = table.getSelectionModel().getSelectedItem();
			retriveObjectData();
		}
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
		list.addAll(studentDAO.getStudentsArrayList(txtSearch.getText()));
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
