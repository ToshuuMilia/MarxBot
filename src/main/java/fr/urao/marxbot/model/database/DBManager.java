package fr.urao.marxbot.model.database;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DBManager {
	
	private String databaseUrl = "jdbc:sqlite:marxbot.sqlite";
	
	public DBManager(){
		this.initializeDatabase();
	}
	
	private void initializeDatabase(){
		String query_txt = "CREATE TABLE IF NOT EXISTS people_words (" +
				"user_id VARCHAR(100) NOT NULL," +
				"number_words INT," +
				"PRIMARY KEY (user_id));";
		
		try(Connection connection = DriverManager.getConnection(databaseUrl)) {
			Statement statement = connection.createStatement();
			
			statement.execute(query_txt);
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Integer> getNumberWords(){
		HashMap<String, Integer> people_words = new HashMap<>();
		String query = "SELECT user_id, number_words FROM people_words;";
		
		try(Connection connection = DriverManager.getConnection(databaseUrl)) {
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				people_words.put(rs.getString("user_id"), rs.getInt("number_words"));
			}
			
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return people_words;
	}
	
	private Optional<Integer> getNumberWords(String userId){
		Integer result = null;
		String query = "SELECT number_words FROM people_words WHERE user_id=?;";
		
		try(Connection connection = DriverManager.getConnection(databaseUrl)) {
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, userId);
			
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("number_words");
			}
			
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(result);
	}
	
	public void addNumberWords(String userId, int addNumberWords){
		Optional<Integer> numberWordsOpt = getNumberWords(userId);
		String query;
		int numberWords = numberWordsOpt.orElse(0) + addNumberWords;
		
		try(Connection connection = DriverManager.getConnection(databaseUrl)) {
			int numberWordsOffset, userIdOffset;
			if(numberWordsOpt.isPresent()){
				query = "UPDATE people_words SET number_words=? WHERE user_id=?;";
				numberWordsOffset = 1;
				userIdOffset = 2;
			} else {
				query = "INSERT INTO people_words VALUES (?,?);";
				userIdOffset = 1;
				numberWordsOffset = 2;
			}
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(numberWordsOffset, numberWords);
			statement.setString(userIdOffset, userId);
			
			statement.executeUpdate();
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		DBManager dbManager = new DBManager();
		
//		dbManager.addNumberWords("G", 34);
		
		System.out.println(dbManager.getNumberWords());
	}
}
