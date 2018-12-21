package pl.coderslab.app;

import pl.coderslab.model.Excercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminExcerciseApp {

	public static void main(String[] args) throws SQLException {
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/school?useSSL=false&characterEncoding=utf8", "root",
				"coderslab"); Scanner scanner = new Scanner(System.in)) {
			boolean quit = false;
			while (!quit) {
				Excercise[] excercises = Excercise.loadAll(conn);
				for (Excercise excercise : excercises) {
					System.out.println(excercise.toString());
				}
				System.out.print("Wprowadź add/edit/delete/quit: ");
				try {
					switch (scanner.nextLine()) {
					case "add": {
						Excercise excercise = new Excercise();
						System.out.print("Podaj tytuł zadania: ");
						excercise.setTitle(scanner.nextLine());
						System.out.print("Podaj opis zadania: ");
						excercise.setDescription(scanner.nextLine());
						excercise.saveToDB(conn);
						System.out.println("Zadanie zapisane");
					}
						break;
					case "edit": {
						System.out.print("Podaj ID zadania do edycji: ");
						int id = Integer.parseInt(scanner.nextLine());
						Excercise excercise = Excercise.loadById(conn, id);
						if (excercise == null) {
							System.out.println("Nie ma zadania o takim ID");
							break;
						}
						System.out.print("Podaj tytuł zadania: ");
						excercise.setTitle(scanner.nextLine());
						System.out.print("Podaj opis zadania: ");
						excercise.setDescription(scanner.nextLine());
						excercise.saveToDB(conn);
						System.out.println("Zadanie edytowane");
					}
						break;
					case "delete": {
						System.out.print("Podaj ID zadania do usunięcia: ");
						int id = Integer.parseInt(scanner.nextLine());
						Excercise excercise = Excercise.loadById(conn, id);
						if (excercise == null) {
							System.out.println("Nie ma zadania o takim ID");
							break;
						}
						excercise.delete(conn);
						System.out.println("Zadanie usunięte");
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
