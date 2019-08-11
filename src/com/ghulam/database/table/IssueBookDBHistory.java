package com.ghulam.database.table;

import java.sql.Date;

public class IssueBookDBHistory {
    private int issueId;
    private int bookId;
    private int studentId;
    private Date issueDate;
    private Date returnDate;

    public IssueBookDBHistory(int issueId, int bookId, int studentId, Date issueDate, Date returnDate) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.studentId = studentId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
