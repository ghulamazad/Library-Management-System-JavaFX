package com.ghulam.database.dao;

import com.ghulam.database.Database;
import com.ghulam.database.table.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO {
    // query
    private static final String INSERT_STUDENT = "INSERT INTO STUDENTDB VALUES(?,?,?,?,?,?,?,?)";
    private static final String DISPLAY_ALL_STUDENT = "SELECT ID,NAME,CLS,ROLL,MOBILE_NO,ADDRESS,ISSUEDBOOKS,DATE1 FROM STUDENTDB";
    private static final String LAST_STUDENT_ID = "SELECT ID FROM STUDENTDB ORDER BY ID DESC";
    private static final String UPDATE_STUDENT = "UPDATE STUDENTDB SET NAME=?,CLS=?,ROLL=?,MOBILE_NO=?,ADDRESS=? WHERE ID=?";
    private static final String DELETE_STUDENT = "DELETE FROM STUDENTDB WHERE ID IN(";
    private static final String SEARCH_STUDENTS = "SELECT ID,NAME,CLS,ROLL,MOBILE_NO,ADDRESS,ISSUEDBOOKS,DATE1 FROM STUDENTDB WHERE ID LIKE ? OR UPPER(NAME) LIKE ? OR UPPER(CLS) LIKE ? OR ROLL LIKE ? OR MOBILE_NO LIKE ?";
    private static final String SEARCH_STUDENT = "SELECT ID,NAME,CLS,ROLL,MOBILE_NO,ADDRESS,ISSUEDBOOKS,DATE1 FROM STUDENTDB WHERE ID=?";
    // Variable
    private Connection conn = null;
    private PreparedStatement pStatement = null;
    private ResultSet resultSet=null;
    private Student student;
    // method
    public StudentDAO() {
        conn = Database.getConnection();
    }
    public int addStudent(Student student) {
        try {
            pStatement = conn.prepareStatement(INSERT_STUDENT);
            pStatement.setInt(1, student.getStudentID());
            pStatement.setString(2, student.getName());
            pStatement.setString(3, student.getCls());
            pStatement.setInt(4, student.getRoll());
            pStatement.setLong(5, student.getMobile_no());
            pStatement.setString(6, student.getAddress());
            pStatement.setInt(7, student.getIssuedBooks());
            pStatement.setDate(8, student.getDate());
            return pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public Student getStudent(int id) {
        student=null;
        try {
            pStatement=conn.prepareStatement(SEARCH_STUDENT);
            pStatement.setInt(1,id);;
            resultSet=pStatement.executeQuery();
            if (resultSet.next()){
                student = new Student(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("CLS"),
                        resultSet.getInt("ROLL"),
                        resultSet.getLong("MOBILE_NO"),
                        resultSet.getString("ADDRESS"),
                        resultSet.getInt("ISSUEDBOOKS"),
                        resultSet.getDate("DATE1"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }
    public int getLast() {
        try {
            pStatement = conn.prepareStatement(LAST_STUDENT_ID);
            resultSet = pStatement.executeQuery();
            if(resultSet.next())
                return resultSet.getInt("ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int updateStudent(Student student) {
        try {
            pStatement = conn.prepareStatement(UPDATE_STUDENT);
            pStatement.setString(1, student.getName());
            pStatement.setString(2, student.getCls());
            pStatement.setInt(3, student.getRoll());
            pStatement.setLong(4, student.getMobile_no());
            pStatement.setString(5, student.getAddress());
            pStatement.setInt(6, student.getStudentID());
            return pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int deleteStudent(int... id) {
        try {
            StringBuilder deleteQuery = new StringBuilder(DELETE_STUDENT);
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
    public ArrayList<Student> getStudentsArrayList() {
        ArrayList<Student> studentsList = new ArrayList<>();
        resultSet = getStudentsResultSet();
        try {
            while (resultSet.next()) {
                student = new Student(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("CLS"),
                        resultSet.getInt("ROLL"),
                        resultSet.getLong("MOBILE_NO"),
                        resultSet.getString("ADDRESS"),
                        resultSet.getInt("ISSUEDBOOKS"),
                        resultSet.getDate("DATE1"));
                studentsList.add(student);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return studentsList;
    }
    public ArrayList<Student> getStudentsArrayList(String text) {
        ArrayList<Student> studentsList = new ArrayList<>();
        resultSet = getStudentsResultSet(text);
        try {
            while (resultSet.next()) {
                student = new Student(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("CLS"),
                        resultSet.getInt("ROLL"),
                        resultSet.getLong("MOBILE_NO"),
                        resultSet.getString("ADDRESS"),
                        resultSet.getInt("ISSUEDBOOKS"),
                        resultSet.getDate("DATE1"));
                studentsList.add(student);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return studentsList;
    }
    private ResultSet getStudentsResultSet() {
        try {
            pStatement = conn.prepareStatement(DISPLAY_ALL_STUDENT);
            return pStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
    private ResultSet getStudentsResultSet(String text) {
        try {
            pStatement = conn.prepareStatement(SEARCH_STUDENTS);
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
}


