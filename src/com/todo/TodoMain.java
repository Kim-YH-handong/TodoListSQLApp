package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		TodoUtil.dueDateFunction(l);
		boolean quit = false;
		Menu.displaymenu();
		do {
			Menu.prompt();
			TodoUtil.updateCategory(l);
			
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "asc":
				TodoUtil.sortByName(l, "ORDER BY title");
				break;

			case "desc":
				TodoUtil.sortByName(l, "ORDER BY title DESC");
				break;
				
			case "date":
				TodoUtil.sortByName(l, "ORDER BY list.current_date");
				break;
				
			case "ls_date_desc":
				TodoUtil.sortByName(l, "ORDER BY list.current_date DESC");
				break;
				
			case "ls_uncomp_date":
				TodoUtil.sortByUncompDate(l, "ORDER BY list.due_date");
				break;
				
			case "ls_cate":
				TodoUtil.countCategory(l);
				break;
				
			case "find":
				String input = sc.next();
				TodoUtil.findList(l, input);
				break;
				
			case "find_cate":
				String input2 = sc.next();
				TodoUtil.findCate(l, input2);
				break;
				
			case "comp":
				String inputComp = sc.nextLine();
				TodoUtil.completeItem(l, inputComp);
				break;
				
			case "ls_comp":
				TodoUtil.listComp(l);
				break;
				
			case "special":
				String inputSpecial = sc.nextLine();
				TodoUtil.specializeItem(l, inputSpecial);
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				break;
				
			default:
				System.out.println("위에 언급된 명령어를 입력해주세요(help - 도움말)");
				break;
			}	
		} while (!quit);
	}
	
}