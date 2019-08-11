package com.ghulam.database.dao;

import com.ghulam.database.Database;
import com.ghulam.database.table.BookIssueReturn;

import java.sql.*;
import java.util.ArrayList;

public class BookIssueReturnDAO {
    // query
    private static final String INSERT_ISSUE_DETAIL="INSERT INTO ISSUEBOOKDB VALUES(?,?,?,?)";
    private static final String DISPLAY_ALL_ISSUE_DETAIL="SELECT ISSUE_ID,BOOK_ID,STUDENT_ID,ISSUE_DATE FROM ISSUEBOOKDB";
    private static final String DISPLAY_ALL_ISSUE_ID="SELECT ISSUE_ID FROM ISSUEBOOKDB";
    private static final String DISPLAY_ISSUE_DETAIL="SELECT ISSUE_ID,BOOK_ID,STUDENT_ID,ISSUE_DATE FROM ISSUEBOOKDB WHERE ISSUE_ID=?";
    private static final String SEARCH_ISSUE_DETAIL="SELECT ISSUE_ID,BOOK_ID,STUDENT_ID,ISSUE_DATE FROM ISSUEBOOKDB WHERE ISSUE_ID=? OR STUDENT_ID=? OR BOOK_ID=?";
    private static final String CHECK_ISSUE_BOOK_DETAIL="SELECT ISSUE_ID FROM ISSUEBOOKDB WHERE BOOK_ID=? AND STUDENT_ID=?";
    private static final String LAST_ISSUE_ID = "SELECT ISSUE_ID FROM ISSUEBOOKDB ORDER BY ISSUE_ID DESC";
    private static final String DELETE_ISSUE_DETAIL="DELETE FROM ISSUEBOOKDB WHERE ISSUE_ID=?";
    private static final String SEARCH_ISSUE_DATE = "SELECT ISSUE_ID,BOOK_ID,STUDENT_ID,ISSUE_DATE FROM ISSUEBOOKDB WHERE ISSUE_DATE=?";
    // Variable
    private Connection conn = null;
    private PreparedStatement pStatement = null;
    private ResultSet resultSet=null;
    private BookIssueReturn bookIssueReturn;
    // method
    public BookIssueReturnDAO() {
        conn = Database.getConnection();
    }
    public BookIssueReturn getData(int id){
        bookIssueReturn=null;
        try {
            pStatement=conn.prepareStatement(DISPLAY_ISSUE_DETAIL);
            pStatement.setInt(1,id);
            resultSet=pStatement.executeQuery();
            if(resultSet.next()){
                bookIssueReturn=new BookIssueReturn(resultSet.getInt("ISSUE_ID"),
                        resultSet.getInt("BOOK_ID"),
                        resultSet.getInt("STUDENT_ID"),
                        resultSet.getDate("ISSUE_DATE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookIssueReturn;
    }

    public int getLast() {
        try {
            pStatement = conn.prepareStatement(LAST_ISSUE_ID);
            resultSet = pStatement.executeQuery();
            if(resultSet.next())
                return resultSet.getInt("ISSUE_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int addData(BookIssueReturn bookIssueReturn) {
        try {
            pStatement=conn.prepareStatement(INSERT_ISSUE_DETAIL);
            pStatement.setInt(1,bookIssueReturn.getIssueId());
            pStatement.setInt(2,bookIssueReturn.getBookId());
            pStatement.setInt(3,bookIssueReturn.getStudentId());
            pStatement.setDate(4,bookIssueReturn.getIssueDate());
            return pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int  deleteData(int id) {
        try {
            pStatement=conn.prepareStatement(DELETE_ISSUE_DETAIL);
            pStatement.setInt(1,id);
            return pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean isBookIssued(int studentId,int bookId){
        try {
            pStatement=conn.prepareStatement(CHECK_ISSUE_BOOK_DETAIL);
            pStatement.setInt(1,bookId);
            pStatement.setInt(2,studentId);
            resultSet=pStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<BookIssueReturn> getBooksArrayList() {
        ArrayList<BookIssueReturn> bookIssueReturnsList = new ArrayList<>();
        resultSet = getBooksResultSet();
        try {
            while (resultSet.next()) {
                bookIssueReturn=new BookIssueReturn(resultSet.getInt("ISSUE_ID"),
                        resultSet.getInt("BOOK_ID"),
                        resultSet.getInt("STUDENT_ID"),
                        resultSet.getDate("ISSUE_DATE"));
                bookIssueReturnsList.add(bookIssueReturn);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return bookIssueReturnsList;
    }
    public ArrayList<Integer> getBooksIssueIDArrayList() {
        ArrayList<Integer> bookIssueIDList = new ArrayList<>();
        resultSet = getIssueBooksIDResultSet();
        try {
            while (resultSet.next()) {
                bookIssueIDList.add(resultSet.getInt("ISSUE_ID"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return bookIssueIDList;
    }
    public ArrayList<BookIssueReturn> getBooksArrayList(String text) {
        ArrayList<BookIssueReturn> bookIssueReturnsList = new ArrayList<>();
        resultSet = getBooksResultSet(text);
        try {
            while (resultSet.next()) {
                bookIssueReturn=new BookIssueReturn(resultSet.getInt("ISSUE_ID"),
                        resultSet.getInt("BOOK_ID"),
                        resultSet.getInt("STUDENT_ID"),
                        resultSet.getDate("ISSUE_DATE"));
                bookIssueReturnsList.add(bookIssueReturn);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return bookIssueReturnsList;
    }
    public ArrayList<BookIssueReturn> getBooksArrayList(Date date) {
        ArrayList<BookIssueReturn> bookIssueReturnsList = new ArrayList<>();
        resultSet = getBooksResultSet(date);
        try {
            while (resultSet.next()) {
                bookIssueReturn=new BookIssueReturn(resultSet.getInt("ISSUE_ID"),
                        resultSet.getInt("BOOK_ID"),
                        resultSet.getInt("STUDENT_ID"),
                        resultSet.getDate("ISSUE_DATE"));
                bookIssueReturnsList.add(bookIssueReturn);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return bookIssueReturnsList;
    }
    private ResultSet getIssueBooksIDResultSet() {
        try {
            pStatement = conn.prepareStatement(DISPLAY_ALL_ISSUE_ID);
            return pStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    private ResultSet getBooksResultSet() {
        try {
            pStatement = conn.prepareStatement(DISPLAY_ALL_ISSUE_DETAIL);
            return pStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    private ResultSet getBooksResultSet(String text) {
        try {
            pStatement = conn.prepareStatement(SEARCH_ISSUE_DETAIL);
            pStatement.setString(1, (text + "%").toUpperCase());
            pStatement.setString(2, (text + "%").toUpperCase());
            pStatement.setString(3, (text + "%").toUpperCase());
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
            return pStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}

