package com.ghulam.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ghulam.alert.SimpleDialog;
import com.ghulam.database.Database;
import com.ghulam.database.table.User;

public class UserDAO {
	// query
	private static final String INSERT_USER = "INSERT INTO USERDB VALUES(?,?)";
	private static final String GET_USER = "SELECT USERNAME,PASSWORD FROM USERDB WHERE UPPER(USERNAME)=? AND PASSWORD=?";
	// Variable
	private Connection conn = null;
	private PreparedStatement pStatement = null;
	private ResultSet resultSet = null;

	// method
	public UserDAO() {
		conn = Database.getConnection();
	}

	public void addUser(User user) {
		try {
			pStatement = conn.prepareStatement(INSERT_USER);
			pStatement.setString(1, user.getUsername());
			pStatement.setString(2, user.getPassword());
			pStatement.executeUpdate();
		} catch (SQLException e) {
			SimpleDialog.showMsg(e.toString());
		}
	}

	public boolean isUserExist(User user) {

		try {
			pStatement = conn.prepareStatement(GET_USER);
			pStatement.setString(1, user.getUsername().toUpperCase());
			pStatement.setString(2, user.getPassword());
			resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			SimpleDialog.showMsg(e.toString());
		}

		return false;
	}

}
