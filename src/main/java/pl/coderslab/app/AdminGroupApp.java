package pl.coderslab.app;

import pl.coderslab.model.Group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminGroupApp {

	public static void main(String[] args) throws SQLException {
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/school?useSSL=false&characterEncoding=utf8", "root",
				"coderslab"); Scanner scanner = new Scanner(System.in)) {
			boolean quit = false;
			while (!quit) {
				Group[] groups = Group.loadAll(conn);
				for (Group group : groups) {
					System.out.println(group.toString());
				}
				System.out.print("Wprowadź add/edit/delete/quit: ");
				try {
					switch (scanner.nextLine()) {
					case "add": {
						Group group = new Group();
						System.out.print("Podaj nazwę grupy: ");
						group.setName(scanner.nextLine());
						group.saveToDB(conn);
						System.out.println("Grupa zapisana");
					}
						break;
					case "edit": {
						System.out.print("Podaj ID grupy do edycji: ");
						int id = Integer.parseInt(scanner.nextLine());
						Group group = Group.loadById(conn, id);
						if (group == null) {
							System.out.println("Nie ma grupy o takim ID");
							break;
						}
						System.out.print("Podaj nazwę grupy: ");
						group.setName(scanner.nextLine());
						group.saveToDB(conn);
						System.out.println("Grupa zapisana");
					}
						break;
					case "delete": {
						System.out.print("Podaj ID grupy do usunięcia: ");
						int id = Integer.parseInt(scanner.nextLine());
						Group group = Group.loadById(conn, id);
						if (group == null) {
							System.out.println("Nie ma grupy o takim ID");
							break;
						}
						group.delete(conn);
						System.out.println("Grupa usunięta");
					}
						break;
					case "quit":
						System.out.println("Koniec programu");
						quit = true;
						break;
					}
				} catch (Exception e) {
					System.err.println("Błąd programu!");
					e.printStackTrace();
				}
			}
		}
	}
}
