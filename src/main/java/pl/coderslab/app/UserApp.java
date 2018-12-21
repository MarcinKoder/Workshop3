package pl.coderslab.app;

import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class UserApp {

	public static void main(String[] args) throws SQLException {
		Integer userId = Integer.parseInt("1");
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/school?useSSL=false&characterEncoding=utf8", "root",
				"coderslab"); Scanner scanner = new Scanner(System.in)) {
			User user = User.loadById(conn, userId);
			if (user == null) {
				System.out.println("Nie znaleziono użytkownika o ID " + userId);
			}
			boolean quit = false;
			while (!quit) {
				System.out.print("Wprowadź add/view/quit: ");
				try {
					switch (scanner.nextLine()) {
					case "add": {
						Solution[] solutions = Solution.loadAllByUserId(conn, userId);
						Set<Integer> availableSolutions = new TreeSet<Integer>();
						for (Solution solution : solutions) {
							if (solution.getUpdated() == null) {
								availableSolutions.add(solution.getId());
								System.out.println(solution.toString());
							}
						}
						System.out.print("Podaj ID rozwiązania do wypełnienia: ");
						int solutionId = Integer.parseInt(scanner.nextLine());
						if (!availableSolutions.contains(solutionId)) {
							System.out.println("To rozwiązanie nie może być wypełnione");
							break;
						}
						Solution solution = Solution.loadById(conn, solutionId);
						System.out.println("Podaj treść rozwiązania: ");
						solution.setDescription(scanner.nextLine());
						solution.saveToDB(conn);
						System.out.println("Rozwiązanie zapisane");
					}
						break;
					case "view": {
						Solution[] solutions = Solution.loadAllByUserId(conn, userId);
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
