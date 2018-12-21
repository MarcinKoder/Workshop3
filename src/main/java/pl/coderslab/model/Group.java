package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Group {
	private int id;
	private String name;
	private static final String LOAD_BY_ID = "SELECT * FROM Users_Groups WHERE id = ?";
	private static final String LOAD_ALL = "SELECT * FROM Users_Groups";
	private static final String DELETE = "DELETE FROM Users_Groups WHERE id = ?";
	private static final String SAVE = "INSERT INTO Users_Groups(name) VALUES (?)";
	private static final String UPDATE = "UPDATE Users_Groups SET name = ? WHERE id = ?";

	public static Group loadById(Connection conn, int id) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement(LOAD_BY_ID);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Group loadedGroup = new Group();
			loadedGroup.id = resultSet.getInt("id");
			loadedGroup.name = resultSet.getString("name");
			return loadedGroup;
		}
		return null;
	}

	public static Group[] loadAll(Connection conn) throws SQLException {
		List<Group> groups = new ArrayList<Group>();
		PreparedStatement preparedStatement = conn.prepareStatement(LOAD_ALL);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Group loadedGroup = new Group();
			loadedGroup.id = resultSet.getInt("id");
			loadedGroup.name = resultSet.getString("name");
			groups.add(loadedGroup);
		}
		Group[] gArray = new Group[groups.size()];
		gArray = groups.toArray(gArray);
		return gArray;
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
			preparedStatement.setString(1, this.name);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} else {
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE);
			preparedStatement.setString(1, this.name);
			preparedStatement.setInt(2, this.id);
			preparedStatement.executeUpdate();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return "ID: " + id + ", name: " + name;
	}
}