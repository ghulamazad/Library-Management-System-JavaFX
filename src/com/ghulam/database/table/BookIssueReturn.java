package com.ghulam.database.table;

import java.sql.Date;

public class BookIssueReturn {
    private int issueId;
    private int bookId;
    private int studentId;
    private Date issueDate;

    public BookIssueReturn(){
        super();
    }

    public BookIssueReturn(int issueId, int bookId, int studentId, Date issueDate) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.studentId = studentId;
        this.issueDate = issueDate;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }


    public int getBookId() {
        return bookId;
    }

    public int getStudentId() {
        return studentId;
    }

    public Date getIssueDate() {
        return issueDate;
    }
}