package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import com.todo.service.*;

public class TodoUtil {
	static Connection conn = DbConnect.getConnection();
	
	public static void dueDateFunction(TodoList l) {
		int count = 0;
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        String current_date= f.format(new Date());
		
		for (TodoItem item : l.getList()) {
			if(item.getDue_date().equals(current_date)) {
				System.out.println(item.toString());
				count++;
			}
		}
		
		if(count != 0) {
			System.out.printf("오늘 마감인 과제들입니다!! 서두르세요!\n");
		}
	}
	
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
		
		TodoItem t = new TodoItem(category, title, desc, due_date, 0, 0);
		if(list.addItem(t) > 0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하시오 > ");
		String index = sc.nextLine();
		StringTokenizer st = new StringTokenizer(index);
		
		while(st.hasMoreTokens()) {
			int delete = Integer.parseInt(st.nextToken());
			if(l.deleteItem(delete)>0) 
				System.out.printf("%d가 삭제되었습니다.\n", delete);
		}
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
		System.out.printf("\n완료 했나 (1:Y, 0:N) >  > ");
		String comp = sc.next();
		System.out.printf("\n중요한 일정인가 (1:Y, 0:N) > ");
		String important = sc.next();

		
		TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date, Integer.parseInt(comp), Integer.parseInt(important));
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
	
	public static void listComp(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			if(item.getIsCompleted()==1) {
				System.out.println(item.toString());
			}
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
	
	public static void sortByUncompDate(TodoList l, String query) {
		System.out.printf("[끝내지 않은 일정들 빠른순]\n", l.getCount());
		for (TodoItem item : l.getList(query)) {
			if(item.getIsCompleted()==0) {
				System.out.println(item.toString());
			}
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
		
	
	public static void completeItem(TodoList l, String n) {
		StringTokenizer st = new StringTokenizer(n);
		while(st.hasMoreTokens()) {
			int input = Integer.parseInt(st.nextToken());
			if(l.completeItem(input)==1) {
				System.out.printf("%d를 완료 체크하였습니다.", input);
			}
		}
	}
	
	public static void specializeItem(TodoList l, String n) {
		StringTokenizer st = new StringTokenizer(n);
		while(st.hasMoreTokens()) {
			int input = Integer.parseInt(st.nextToken());
			if(l.specializeItem(input)==1) {
				System.out.printf("%d를 Special 체크하였습니다.\n", input);
			}
		}
	}
	
	public static void updateCategory(TodoList l) {
		Set<String> set = new HashSet<String>();
		
		for(TodoItem item: l.getList()) {
			set.add(item.getCategory());
		}
		
		for(String item: set) {
			
		}
	}
	
	}



