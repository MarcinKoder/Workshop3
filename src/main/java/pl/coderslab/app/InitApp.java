package pl.coderslab.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitApp {

	private static final String CREATE_USERS_GROUPS = "CREATE TABLE Users_Groups"
			+ "(id INT(11) AUTO_INCREMENT,"
			+ "name VARCHAR(255) NOT NULL,"
			+ "PRIMARY KEY (id))";

	private static final String CREATE_USERS = "CREATE TABLE Users"
			+ "(id BIGINT(20) AUTO_INCREMENT,"
			+ "username VARCHAR(255),"
			+ "email VARCHAR(255) UNIQUE NOT NULL,"
			+ "password VARCHAR(245),"
			+ "user_group_id INT(11),"
			+ "PRIMARY KEY (id),"
			+ "FOREIGN KEY (user_group_id) REFERENCES Users_Groups (id) ON DELETE SET NULL ON UPDATE CASCADE)";

	private static final String CREATE_EXCERCISES = "CREATE TABLE Excercises"
			+ "(id INT(11) AUTO_INCREMENT,"
			+ "title VARCHAR(255) NOT NULL,"
			+ "description TEXT,"
			+ "PRIMARY KEY (id))";

	private static final String CREATE_SOLUTIONS = "CREATE TABLE Solutions"
			+ "(id INT(11) AUTO_INCREMENT,"
			+ "created DATETIME,"
			+ "updated DATETIME,"
			+ "description TEXT,"
			+ "excercise_id INT(11) NOT NULL,"
			+ "user_id BIGINT(20),"
			+ "PRIMARY KEY (id),"
			+ "FOREIGN KEY (excercise_id) REFERENCES Excercises (id) ON DELETE CASCADE ON UPDATE CASCADE,"
			+ "FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE)";

	public static void main(String[] args) throws SQLException {
		try (Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/school?useSSL=false&characterEncoding=utf8", "root",
				"coderslab")) {
			Statement stat = con.createStatement();

			stat.executeUpdate(CREATE_USERS_GROUPS);
			stat.executeUpdate(CREATE_USERS);
			stat.executeUpdate(CREATE_EXCERCISES);
			stat.executeUpdate(CREATE_SOLUTIONS);
		}
	}
}
