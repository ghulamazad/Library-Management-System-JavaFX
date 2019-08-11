package com.ghulam.database.dao;

import com.ghulam.database.Database;
import com.ghulam.database.table.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {
    // query
    private static final String INSERT_BOOK = "INSERT INTO BOOKDB VALUES(?,?,?,?,?,?)";
    private static final String DISPLAY_ALL_BOOK = "SELECT ID,NAME,AUTHOR,DESCRIPTION,STOCK,DATE1 FROM BOOKDB";
    private static final String LAST_BOOK_ID = "SELECT ID FROM BOOKDB ORDER BY ID DESC";
    private static final String UPDATE_BOOK = "UPDATE BOOKDB SET NAME=?,AUTHOR=?,DESCRIPTION=?,STOCK=? WHERE ID=?";
    private static final String DELETE_BOOK = "DELETE FROM BOOKDB WHERE ID IN(";
    private static final String SEARCH_BOOKS = "SELECT ID,NAME,AUTHOR,DESCRIPTION,STOCK,DATE1 FROM BOOKDB WHERE ID LIKE ? OR UPPER(NAME) LIKE ? OR UPPER(AUTHOR) LIKE ? OR UPPER(DESCRIPTION) LIKE ? OR STOCK LIKE ?";
    private static final String SEARCH_BOOK = "SELECT ID,NAME,AUTHOR,DESCRIPTION,STOCK,DATE1 FROM BOOKDB WHERE ID=?";
    private static final String SEARCH_BOOKS_DATE = "SELECT ID,NAME,AUTHOR,DESCRIPTION,STOCK,DATE1 FROM BOOKDB WHERE DATE1=?";
    // Variable
    private Connection conn = null;
    private PreparedStatement pStatement = null;
    private ResultSet resultSet=null;
    private Book book;
    // method
    public BookDAO() {
        conn = Database.getConnection();
    }
    public int addBook(Book book) {
        try {
            pStatement = conn.prepareStatement(INSERT_BOOK);
            pStatement.setInt(1, book.getId());
            pStatement.setString(2, book.getName());
            pStatement.setString(3, book.getAuthorName());
            pStatement.setString(4, book.getDescription());
            pStatement.setInt(5, book.getStock());
            pStatement.setDate(6, book.getDate());
            return pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public Book getBook(int id){
        book=null;
        try {
            pStatement=conn.prepareStatement(SEARCH_BOOK);
            pStatement.setInt(1,id);
            resultSet=pStatement.executeQuery();
            if(resultSet.next()){
                book = new Book(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getString("AUTHOR"),
                        resultSet.getString("DESCRIPTION"), resultSet.getInt("STOCK"),
                        resultSet.getDate("DATE1"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
    public int getLast() {
        try {
            pStatement = conn.prepareStatement(LAST_BOOK_ID);
            resultSet = pStatement.executeQuery();
            if(resultSet.next())
                return resultSet.getInt("ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int updateBook(Book book) {
        try {
            pStatement = conn.prepareStatement(UPDATE_BOOK);
            pStatement.setString(1, book.getName());
            pStatement.setString(2, book.getAuthorName());
            pStatement.setString(3, book.getDescription());
            pStatement.setInt(4, book.getStock());
            pStatement.setInt(5, book.getId());
            return pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int deleteBook(int... id) {
        try {
            StringBuilder deleteQuery = new StringBuilder(DELETE_BOOK);
            for (int i = 0; i < id.length; i++) {
                deleteQuery.append(id[i]);
                if (i != id.length - 1) deleteQuery.append(",");
            }
            deleteQuery.append(")");
            pStatement = conn.prepareStatement(deleteQuery.toString());
            return pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public ArrayList<Book> getBooksArrayList() {
        ArrayList<Book> booksList = new ArrayList<>();
        resultSet = getBooksResultSet();
        try {
            while (resultSet.next()) {
                book = new Book(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getString("AUTHOR"),
                        resultSet.getString("DESCRIPTION"), resultSet.getInt("STOCK"),
                        resultSet.getDate("DATE1"));
                booksList.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return booksList;
    }
    public ArrayList<Book> getBooksArrayList(String text) {
        ArrayList<Book> booksList = new ArrayList<>();
        resultSet = getBooksResultSet(text);
        try {
            while (resultSet.next()) {
                book = new Book(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getString("AUTHOR"),
                        resultSet.getString("DESCRIPTION"), resultSet.getInt("STOCK"),
                        resultSet.getDate("DATE1"));
                booksList.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return booksList;
    }
    public ArrayList<Book> getBooksArrayList(Date date) {
        ArrayList<Book> booksList = new ArrayList<>();
        resultSet = getBooksResultSet(date);
        try {
            while (resultSet.next()) {
                book = new Book(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getString("AUTHOR"),
                        resultSet.getString("DESCRIPTION"), resultSet.getInt("STOCK"),
                        resultSet.getDate("DATE1"));
                booksList.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return booksList;
    }
    private ResultSet getBooksResultSet() {
        try {
            pStatement = conn.prepareStatement(DISPLAY_ALL_BOOK);
            return pStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    private ResultSet getBooksResultSet(String text) {
        try {
            pStatement = conn.prepareStatement(SEARCH_BOOKS);
            pStatement.setString(1, (text + "%").toUpperCase());
            pStatement.setString(2, (text + "%").toUpperCase());
            pStatement.setString(3, (text + "%").toUpperCase());
            pStatement.setString(4, (text + "%").toUpperCase());
            pStatement.setString(5, (text + "%").toUpperCase());
            return pStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    private ResultSet getBooksResultSet(Date date) {
        try {
            pStatement = conn.prepareStatement(SEARCH_BOOKS_DATE);
            pStatement.setDate(1,date);
            return pStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}

