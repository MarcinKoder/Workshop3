package pl.coderslab.app;

import pl.coderslab.model.Excercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminSolutionApp {

	public static void main(String[] args) throws SQLException {
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/school?useSSL=false&characterEncoding=utf8", "root",
				"coderslab"); Scanner scanner = new Scanner(System.in)) {
			boolean quit = false;
			while (!quit) {
				System.out.print("Wprowadź add/view/quit: ");
				try {
					switch (scanner.nextLine()) {
					case "add": {
						User[] users = User.loadAll(conn);
						for (User user : users) {
							System.out.println(user.toString());
						}
						System.out.print("Podaj ID użytkownika: ");
						Solution solution = new Solution();
						solution.setUserId(Integer.parseInt(scanner.nextLine()));
						Excercise[] excercises = Excercise.loadAll(conn);
						for (Excercise excercise : excercises) {
							System.out.println(excercise.toString());
						}
						System.out.print("Podaj ID zadania: ");
						solution.setExcerciseId(Integer.parseInt(scanner.nextLine()));
						solution.saveToDB(conn);
						System.out.println("Rozwiązanie zapisane");
					}
						break;
					case "view": {
						User[] users = User.loadAll(conn);
						for (User user : users) {
							System.out.println(user.toString());
						}
						System.out.print("Podaj ID użytkownika: ");
						Solution[] solutions = Solution.loadAllByUserId(conn, Integer.parseInt(scanner.nextLine()));
						for (Solution solution : solutions) {
							System.out.println(solution.toString());
						}
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
