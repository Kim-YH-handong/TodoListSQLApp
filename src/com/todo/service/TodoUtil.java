package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import com.todo.service.*;

public class TodoUtil {
	static Connection conn = DbConnect.getConnection();
	
	public static void createItem(TodoList list) {
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("\n"
				+ "--------일정 추가하기--------\n"
				+ "카테고리 입력 > ");
		category = sc.nextLine();
		
		System.out.printf("제목 입력 > ");
		title = sc.nextLine();
		
		if (list.isDuplicate(title)) {
			System.out.printf("[오류]이미 제목의 일정이 존재합니다!!");
			return;
		}
		
		System.out.printf("설명 입력 > ");
		desc = sc.nextLine();
		
		System.out.printf("마감일자 입력(yyyy/mm/dd) > ");
		due_date = sc.nextLine();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		if(list.addItem(t) > 0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();

		if(l.deleteItem(index)>0) 
			System.out.println("삭제되었습니다.");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("\n"
				+ "-------일정 수정-------\n"
				+ "수정할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();
			
		System.out.printf("\n새로운 일정 이름 > ");
		String new_title = sc.next().trim();
		System.out.printf("\n새로운 카테고리 > ");
		String new_category = sc.next().trim();
		sc.nextLine();
		System.out.printf("\n새로운 내용 > ");
		String new_description = sc.nextLine().trim();
		System.out.printf("\n새로운 마감일 입력 (yyyy/mm/dd) > ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date);
		t.setId(index);
		if(l.updateItem(t)>0)
			System.out.println("수정되었습니다.");
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void sortByName(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void sortByName(TodoList l, String query) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList(query)) {
			System.out.println(item.toString());
		}
	}

	public static void findList(TodoList l, String keyword) {
		keyword = keyword.trim();
		ArrayList<String> list = new ArrayList<String>();
		int i = 1;
		
		for(TodoItem item: l.getList()) {
			if(item.toFindString().contains(keyword)){
				list.add(i+". " + "[" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
			}
			i++;
		}
		System.out.printf("[전체 목록, 총 %d개]\n", list.size());
		
		for(String item: list) {
			System.out.println(item);
		}
	}
	
	public static void findCate(TodoList l, String cateName) {
		cateName = cateName.trim();
		ArrayList<String> list = new ArrayList<String>();
		int i = 1;
		
		for(TodoItem item: l.getList()) {
			if(item.getCategory().contains(cateName)){
				list.add(i+". " + "[" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
			}
			i++;
		}
		System.out.printf("[전체 목록, 총 %d개]\n", list.size());
		
		for(String item: list) {
			System.out.println(item);
		}
	}
	
	public static void countCategory(TodoList l) {
		Set<String> set = new HashSet<String>();
		boolean first = true;
		
		for(TodoItem item: l.getList()) {
			set.add(item.getCategory());
		}
		
		for(String item: set) {
			if(first) {
				System.out.printf(item);
				first = false;
			}else {
				System.out.printf(" / %s", item);
			}
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.", set.size());
	}
			
	}

