package com.ghulam.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;

public class Database {
	private static Connection conn;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private static Alert alert = null;
	private final static String sql = "SELECT TABLE_NAME FROM USER_TABLES WHERE UPPER(TABLE_NAME)=?";

	public Database() {
		alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText("Error");
		alert.setHeaderText(null);
		alert.initModality(Modality.APPLICATION_MODAL);
	}

	public final static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Ghulam", "ghulam");
			return conn;
		} catch (Exception e) {
			alert.setContentText(e.toString());
			alert.showAndWait();
			createUsername();
		}
		return null;
	}

	private static Connection getConnection(String systemPass) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "System", systemPass);
			return conn;
		} catch (Exception e) {
			alert.setContentText(e.toString());
			alert.showAndWait();
		}
		return null;
	}

	public void initDatabase() {
		while (conn == null) {
			conn = getConnection();
		}
		checkUserTable();
		checkStudentTable();
		checkBookTable();
		checkBookIssueTable();
	}

	private static void createUsername() {
		while (conn == null) {
			conn = getConnection(getDbSystemPass());
		}
		try {
			Statement st = conn.createStatement();
			st.execute("CREATE USER GHULAM IDENTIFIED BY ghulam");
			st.execute("GRANT DBA TO GHULAM");
		} catch (SQLException e) {
			alert.setContentText("Username already exist\nPlease delete Ghulam user !!!\nRun again");
			alert.showAndWait();
		} finally {
			conn = null;
		}
	}

	private static String getDbSystemPass() {
		TextInputDialog dialog = new TextInputDialog("Ghulam");
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter oracle system password:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			return (result.get());
		} else {
			Platform.exit();
			System.exit(0);
		}
		return null;
	}

	private void checkUserTable() {
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "USERDB");
			rs = pst.executeQuery();
			if (rs.next())
				return;
			else {
				pst.execute("CREATE TABLE USERDB(USERNAME VARCHAR2(20) PRIMARY KEY,PASSWORD VARCHAR2(26))");
				pst.execute("INSERT INTO USERDB VALUES('GHULAM','Ghulam')");
			}
		} catch (SQLException e) {
			alert.setContentText(e.toString());
			alert.show();
		}
	}

	private void checkStudentTable() {
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "STUDENTDB");
			rs = pst.executeQuery();
			if (rs.next())
				return;
			else {
				pst.execute("CREATE TABLE  STUDENTDB (ID NUMBER PRIMARY KEY,"
						+ "NAME VARCHAR2(21),CLS VARCHAR2(10),ROLL NUMBER,MOBILE_NO NUMBER,"
						+ "ADDRESS VARCHAR2(60),ISSUEDBOOKS NUMBER,DATE1 DATE)");
			}
		} catch (SQLException e) {
			alert.setContentText(e.toString());
			alert.show();
		}
	}

	private void checkBookTable() {
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "BOOKDB");
			rs = pst.executeQuery();
			if (rs.next())
				return;
			else {
				pst.execute("CREATE TABLE BOOKDB(ID NUMBER PRIMARY KEY,NAME VARCHAR2(30),AUTHOR VARCHAR2(26),"
						+ "DESCRIPTION VARCHAR2(60),STOCK NUMBER,DATE1 DATE)");
			}
		} catch (SQLException e) {
			alert.setContentText(e.toString());
			alert.show();
		}
	}

	private void checkBookIssueTable() {
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "ISSUEBOOKDB");
			rs = pst.executeQuery();
			if (rs.next())
				return;
			else {
				pst.execute("CREATE TABLE ISSUEBOOKDB(ISSUE_ID NUMBER PRIMARY KEY,BOOK_ID NUMBER,STUDENT_ID NUMBER,"
						+ "ISSUE_DATE DATE,RETURN_DATE DATE)");
			}
		} catch (SQLException e) {
			alert.setContentText(e.toString());
			alert.show();
		}

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "ISSUEBOOKDB_HISTORY");
			rs = pst.executeQuery();
			if (rs.next())
				return;
			else {
				// Create table history of issue book
				pst.execute("CREATE TABLE ISSUEBOOKDB_HISTORY(ISSUE_ID NUMBER,BOOK_ID NUMBER,STUDENT_ID NUMBER,"
						+ "ISSUE_DATE DATE,RETURN_DATE DATE)");
			}
		} catch (SQLException e) {
			alert.setContentText(e.toString());
			alert.show();
		}
		try {
			// create trigger
			pst.execute("create or replace TRIGGER  ISSUEBOOKDB_TRIGGER AFTER DELETE ON ISSUEBOOKDB FOR EACH ROW\n"
					+ "DECLARE \n" + "    num STUDENTDB.ISSUEDBOOKS%type; \n" + "   stock BOOKDB.STOCK%type; \n"
					+ "   CURSOR cs is SELECT ISSUEDBOOKS FROM STUDENTDB where id=:old.STUDENT_ID; \n"
					+ "   CURSOR cs1 is SELECT STOCK FROM BOOKDB where id=:old.BOOK_ID; \n" + "BEGIN \n"
					+ "    OPEN cs; \n" + "   FETCH cs into num; \n"
					+ "    UPDATE STUDENTDB SET ISSUEDBOOKS=(num-1) WHERE ID=:old.STUDENT_ID;\n" + "   CLOSE cs; \n"
					+ "   OPEN cs1; \n" + "   FETCH cs1 into stock; \n"
					+ "    UPDATE BOOKDB SET STOCK=(stock+1) WHERE ID=:old.BOOK_ID;\n" + "   CLOSE cs1; \n"
					+ " INSERT INTO ISSUEBOOKDB_HISTORY(ISSUE_ID,BOOK_ID,STUDENT_ID,ISSUE_DATE,RETURN_DATE) VALUES (:old.ISSUE_ID,:old.BOOK_ID,:old.STUDENT_ID,:old.ISSUE_DATE,sysdate);\n"
					+ "END;\u200B");

			pst.execute(
					"CREATE OR REPLACE TRIGGER  TRIGGER_ISSUEDBOOKS_UPDATION BEFORE INSERT ON ISSUEBOOKDB FOR EACH ROW\n"
							+ "DECLARE \n" + "   num STUDENTDB.ISSUEDBOOKS%type; \n" + "   stock BOOKDB.STOCK%type; \n"
							+ "   CURSOR cs is SELECT ISSUEDBOOKS FROM STUDENTDB where id=:new.STUDENT_ID; \n"
							+ "   CURSOR cs1 is SELECT STOCK FROM BOOKDB where id=:new.BOOK_ID; \n" + "BEGIN \n"
							+ "   OPEN cs; \n" + "   FETCH cs into num; \n"
							+ "    UPDATE STUDENTDB SET ISSUEDBOOKS=(num+1) WHERE ID=:NEW.STUDENT_ID;\n"
							+ "   CLOSE cs; \n" + "   OPEN cs1; \n" + "   FETCH cs1 into stock; \n"
							+ "    UPDATE BOOKDB SET STOCK=(stock-1) WHERE ID=:NEW.BOOK_ID;\n" + "   CLOSE cs1; \n"
							+ "END;");
		} catch (SQLException e) {
			alert.setContentText(e.toString());
			alert.show();
		}
	}
}
