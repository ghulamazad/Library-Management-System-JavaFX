package com.ghulam.database.table;

import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.control.CheckBox;

import java.sql.Date;

public class Student {
        private int studentID;
        private String name;
        private String cls;
        private int roll;
        private long mobile_no;
        private String address;
        private int issuedBooks;
        private Date date;
        private JFXCheckBox onAction;
        public Student(){
                onAction=new JFXCheckBox();
        }
        public Student(int studentID, String name, String cls, int roll, long mobile_no, String address, int issuedBooks, Date date) {
                this.studentID = studentID;
                this.name = name;
                this.cls = cls;
                this.roll = roll;
                this.mobile_no = mobile_no;
                this.address = address;
                this.issuedBooks = issuedBooks;
                this.date = date;
                onAction=new JFXCheckBox();
        }

        public CheckBox getOnAction() {
                return onAction;
        }

        public void setOnAction(JFXCheckBox onAction) {
                this.onAction = onAction;
        }

        public int getStudentID() {
                return studentID;
        }

        public void setStudentID(int studentID) {
                this.studentID = studentID;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getCls() {
                return cls;
        }

        public void setCls(String cls) {
                this.cls = cls;
        }

        public int getRoll() {
                return roll;
        }

        public void setRoll(int roll) {
                this.roll = roll;
        }

        public long getMobile_no() {
                return mobile_no;
        }

        public void setMobile_no(long mobile_no) {
                this.mobile_no = mobile_no;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public int getIssuedBooks() {
                return issuedBooks;
        }

        public void setIssuedBooks(int issuedBooks) {
                this.issuedBooks = issuedBooks;
        }

        public Date getDate() {
                return date;
        }

        public void setDate(Date date) {
                this.date = date;
        }
}
