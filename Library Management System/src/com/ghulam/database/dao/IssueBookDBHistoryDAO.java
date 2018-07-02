package com.ghulam.database.dao;

import com.ghulam.database.Database;
import com.ghulam.database.table.BookIssueReturn;
import com.ghulam.database.table.IssueBookDBHistory;

import java.sql.*;
import java.util.ArrayList;

public class IssueBookDBHistoryDAO {
    // query
    private static final String DISPLAY_ISSUE_HISTORY = "SELECT ISSUE_ID,BOOK_ID,STUDENT_ID,ISSUE_DATE,RETURN_DATE FROM ISSUEBOOKDB_HISTORY";
    private static final String SEARCH_ISSUE_DATE = "SELECT ISSUE_ID,BOOK_ID,STUDENT_ID,ISSUE_DATE,RETURN_DATE FROM ISSUEBOOKDB_HISTORY WHERE ISSUE_DATE=? OR RETURN_DATE=?";
    // Variable
    private Connection conn = null;
    private PreparedStatement pStatement = null;
    private ResultSet resultSet=null;
    private IssueBookDBHistory issueBookDBHistory;
    // method
    public IssueBookDBHistoryDAO() {
        conn = Database.getConnection();
    }
    public ArrayList<IssueBookDBHistory> getBooksArrayList() {
        ArrayList<IssueBookDBHistory> issueBookDBHistories = new ArrayList<>();
        resultSet = getBooksResultSet();
        try {
            while (resultSet.next()) {
                issueBookDBHistory=new IssueBookDBHistory(resultSet.getInt("ISSUE_ID"),
                        resultSet.getInt("BOOK_ID"),
                        resultSet.getInt("STUDENT_ID"),
                        resultSet.getDate("ISSUE_DATE"),
                        resultSet.getDate("RETURN_DATE"));
                issueBookDBHistories.add(issueBookDBHistory);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return issueBookDBHistories;
    }
    public ArrayList<IssueBookDBHistory> getBooksArrayList(Date date) {
        ArrayList<IssueBookDBHistory> issueBookDBHistories = new ArrayList<>();
        resultSet = getBooksResultSet(date);
        try {
            while (resultSet.next()) {
                issueBookDBHistory=new IssueBookDBHistory(resultSet.getInt("ISSUE_ID"),
                        resultSet.getInt("BOOK_ID"),
                        resultSet.getInt("STUDENT_ID"),
                        resultSet.getDate("ISSUE_DATE"),
                        resultSet.getDate("RETURN_DATE"));
                issueBookDBHistories.add(issueBookDBHistory);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return issueBookDBHistories;
    }
    private ResultSet getBooksResultSet() {
        try {
            pStatement = conn.prepareStatement(DISPLAY_ISSUE_HISTORY);
            return pStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    private ResultSet getBooksResultSet(Date date) {
        try {
            pStatement = conn.prepareStatement(SEARCH_ISSUE_DATE);
            pStatement.setDate(1, date);
            pStatement.setDate(2, date);
            return pStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
