package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Solution {
	private int id;
	private Date created;
	private Date updated;
	private String description;
	private int excerciseId;
	private int userId;

	private static final String LOAD_BY_ID = "SELECT * FROM Solutions WHERE id = ?";
	private static final String LOAD_ALL = "SELECT * FROM Solutions";
	private static final String LOAD_ALL_WITH_LIMIT = "SELECT * FROM Solutions ORDER BY updated DESC LIMIT ?";
	private static final String LOAD_ALL_BY_USER_ID = "SELECT * FROM Solutions WHERE user_id = ?";
	private static final String LOAD_ALL_BY_EXCERCISE_ID = "SELECT * FROM Solutions WHERE excercise_id = ? ORDER BY updated DESC";
	private static final String DELETE = "DELETE FROM Solutions WHERE id = ?";
	private static final String SAVE = "INSERT INTO Solutions(created, description, excercise_id, user_id) VALUES (NOW(), ?, ?, ?)";
	private static final String UPDATE = "UPDATE Solutions SET updated = NOW(), description = ?, excercise_id = ?, user_id = ? WHERE id = ?";

	public static Solution loadById(Connection conn, int id) throws SQLException {
		PreparedStatement preparedStatement = conn.prepareStatement(LOAD_BY_ID);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.excerciseId = resultSet.getInt("excercise_id");
			loadedSolution.userId = resultSet.getInt("user_id");
			return loadedSolution;
		}
		return null;
	}

	public static Solution[] loadAllWithLimit(Connection conn, int limit) throws SQLException {
		List<Solution> users = new ArrayList<Solution>();
		PreparedStatement preparedStatement = conn.prepareStatement(LOAD_ALL_WITH_LIMIT);
		preparedStatement.setInt(1, limit);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.excerciseId = resultSet.getInt("excercise_id");
			loadedSolution.userId = resultSet.getInt("user_id");
			users.add(loadedSolution);
		}
		Solution[] uArray = new Solution[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
	}

	public static Solution[] loadAll(Connection conn) throws SQLException {
		List<Solution> users = new ArrayList<Solution>();
		PreparedStatement preparedStatement = conn.prepareStatement(LOAD_ALL);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.excerciseId = resultSet.getInt("excercise_id");
			loadedSolution.userId = resultSet.getInt("user_id");
			users.add(loadedSolution);
		}
		Solution[] uArray = new Solution[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
	}

	public static Solution[] loadAllByUserId(Connection conn, int userId) throws SQLException {
		if (userId == 0) {
			return new Solution[0];
		}
		List<Solution> users = new ArrayList<Solution>();
		PreparedStatement preparedStatement = conn.prepareStatement(LOAD_ALL_BY_USER_ID);
		preparedStatement.setInt(1, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.excerciseId = resultSet.getInt("excercise_id");
			loadedSolution.userId = resultSet.getInt("user_id");
			users.add(loadedSolution);
		}
		Solution[] uArray = new Solution[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
	}

	public static Solution[] loadAllByExcerciseId(Connection conn, int excerciseId) throws SQLException {
		if (excerciseId == 0) {
			return new Solution[0];
		}
		List<Solution> solutions = new ArrayList<Solution>();
		PreparedStatement preparedStatement = conn.prepareStatement(LOAD_ALL_BY_EXCERCISE_ID);
		preparedStatement.setInt(1, excerciseId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.excerciseId = resultSet.getInt("excercise_id");
			loadedSolution.userId = resultSet.getInt("user_id");
			solutions.add(loadedSolution);
		}
		Solution[] sArray = new Solution[solutions.size()];
		sArray = solutions.toArray(sArray);
		return sArray;
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
			preparedStatement.setString(1, this.description);
			preparedStatement.setInt(2, this.excerciseId);
			
			this.created = new Date(System.currentTimeMillis());
			
			preparedStatement.setInt(3, this.userId);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} else {
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE);
			preparedStatement.setString(1, this.description);
			preparedStatement.setInt(2, this.excerciseId);
			preparedStatement.setInt(3, this.userId);
			preparedStatement.setInt(4, this.id);


			
			preparedStatement.executeUpdate();
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getExcerciseId() {
		return excerciseId;
	}

	public void setExcerciseId(int excerciseId) {
		this.excerciseId = excerciseId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}

	public String toString() {
		return "ID: " + id + ", created: " + created + ", updated: " + updated + ", description: " + description;
	}
}
