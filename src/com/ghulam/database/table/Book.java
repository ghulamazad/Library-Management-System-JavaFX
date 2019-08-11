package com.ghulam.database.table;

import java.sql.Date;

import com.jfoenix.controls.JFXCheckBox;

public class Book {
	private int id;
	private String name;
	private String authorName;
	private String description;
	private int stock;
	private Date date;
	private JFXCheckBox action;

	public Book() {
		action = new JFXCheckBox();
	}

	public Book(int id, String name, String authorName, String description, int stock, Date date) {
		this.id = id;
		this.name = name;
		this.authorName = authorName;
		this.description = description;
		this.stock = stock;
		this.date = date;
		action = new JFXCheckBox();
	}

	public JFXCheckBox getAction() {
		return action;
	}

	public void setAction(JFXCheckBox action) {
		this.action = action;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
