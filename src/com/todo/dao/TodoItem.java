package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
	private String category;
    private String title;
    private String desc;
    private String current_date;
    private String due_date;
    private int is_completed;


    public TodoItem(String category, String title, String desc, String date, String duedate, int comp) {
    	this.category = category;
    	this.title = title;
    	this.desc = desc;
    	this.current_date = date;
    	this.due_date = duedate;
    	is_completed = comp;
    	System.out.print(is_completed);
    }
    
    public TodoItem(String category, String title, String desc, String due_date){
    	this.category = category;
        this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
        this.due_date = due_date;
    }
    
    public TodoItem(String category, String title, String desc, String due_date, int comp){
    	this.category = category;
        this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
        this.due_date = due_date;
        is_completed = comp;
    }
    
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setIsCompleted(int comp) {
    	is_completed = comp;
    }
    
    public int getIsCompleted() {
    	return is_completed;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String toString() {
    	String result;
    	if(is_completed == 1) {
        	result = id + " " + "[" + category + "] " + title + "[V] - " + desc + " - " + current_date + " - " + due_date;
    	}else {
        	result = id + " " + "[" + category + "] " + title + " - " + desc + " - " + current_date + " - " + due_date;
    	}
    	return result;
    }
    
    public String toSaveString() {
    	return category + "##" + title + "##" + desc + "##" + current_date + "##" + due_date + "\n";
    }
    
    public String toFindString() {
    	return category+title+desc+current_date+due_date;
    }
}

