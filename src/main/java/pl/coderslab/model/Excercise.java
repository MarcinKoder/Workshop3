package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Excercise {
	private int id;
	private String title;
	private String description;

	private static final String LOAD_BY_ID = "SELECT * FROM Excercises WHERE id = ?";
	private static final String LOAD_ALL = "SELECT * FROM Excercises";
	private static final String DELETE = "DELETE FROM Excercises WHERE id = ?";
	private static final String SAVE = "INSERT INTO Excercises(title, description) VALUES (?, ?)";
	private static final String UPDATE = "UPDATE Excercises SET title = ?, description = ? WHERE id = ?";

	public static Excercise loadById(Connection conn, int id) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement(LOAD_BY_ID);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Excercise loadedExcercise = new Excercise();
			loadedExcercise.id = resultSet.getInt("id");
			loadedExcercise.title = resultSet.getString("title");
			loadedExcercise.description = resultSet.getString("description");
			return loadedExcercise;
		}
		return null;
	}

	public static Excercise[] loadAll(Connection conn) throws SQLException {
		List<Excercise> excercises = new ArrayList<Excercise>();
		PreparedStatement preparedStatement = conn.prepareStatement(LOAD_ALL);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Excercise loadedExcercise = new Excercise();
			loadedExcercise.id = resultSet.getInt("id");
			loadedExcercise.title = resultSet.getString("title");
			loadedExcercise.description = resultSet.getString("description");
			excercises.add(loadedExcercise);
		}
		Excercise[] eArray = new Excercise[excercises.size()];
		eArray = excercises.toArray(eArray);
		return eArray;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement = conn.prepareStatement(SAVE, generatedColumns);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} else {
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.setInt(3, this.id);
			preparedStatement.executeUpdate();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return "ID: " + id + ", title: " + title + ", description: " + description;
	}
}
