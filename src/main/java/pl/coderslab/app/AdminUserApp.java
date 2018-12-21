package pl.coderslab.app;

import pl.coderslab.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminUserApp {

	public static void main(String[] args) throws SQLException {
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/school?useSSL=false&characterEncoding=utf8", "root",
				"coderslab"); Scanner scanner = new Scanner(System.in)) {
			boolean quit = false;
			while (!quit) {
				User[] users = User.loadAll(conn);
				for (User user : users) {
					System.out.println(user.toString());
				}
				System.out.print("Wprowadź add/edit/delete/quit: ");
				try {
					switch (scanner.nextLine()) {
					case "add": {
						User user = new User();
						System.out.print("Podaj nazwę użytkownika: ");
						user.setUsername(scanner.nextLine());
						System.out.print("Podaj email: ");
						user.setEmail(scanner.nextLine());
						System.out.print("Podaj hasło: ");
						user.setPassword(scanner.nextLine());
						user.saveToDB(conn);
						System.out.println("Użytkownik zapisany");
					}
						break;
					case "edit": {
						System.out.print("Podaj ID użytkownika do edycji: ");
						int id = Integer.parseInt(scanner.nextLine());
						User user = User.loadById(conn, id);
						if (user == null) {
							System.out.println("Nie ma użytkownika o takim ID");
							break;
						}
						System.out.print("Podaj nazwę użytkownika: ");
						user.setUsername(scanner.nextLine());
						System.out.print("Podaj email: ");
						user.setEmail(scanner.nextLine());
						System.out.print("Podaj hasło: ");
						user.setPassword(scanner.nextLine());
						user.saveToDB(conn);
						System.out.println("Użytkownik edytowany");
					}
						break;
					case "delete": {
						System.out.print("Podaj ID użytkownika do usunięcia: ");
						int id = Integer.parseInt(scanner.nextLine());
						User user = User.loadById(conn, id);
						if (user == null) {
							System.out.println("Nie ma użytkownika o takim ID");
							break;
						}
						user.delete(conn);
						System.out.println("Użytkownik usunięty");
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
