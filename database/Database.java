package database;

import java.util.*;

import common.CreateAccountData;
import common.LoginData;

import java.io.*;
import java.sql.*;

public class Database {
	private Connection conn;

	public Database() {
		// Open database properties using the FileInputStream
		try {
			FileInputStream fis = new FileInputStream("database/db.properties");
			// Create Properties object
			Properties prop = new Properties();

			// Load the properties
			try {
				prop.load(fis);

				// get the url from prop
				String url = prop.getProperty("url");

				// get the username from prop
				String user = prop.getProperty("user");

				// get the password from prop
				String password = prop.getProperty("password");

				// uncomment next line if error getting driver to work
				// DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
				try {
					conn = DriverManager.getConnection(url, user, password);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// retrieves queries such as SELECT * FROM and returns a list with the results
	public ArrayList<String> query(String query) {
		ArrayList<String> list = new ArrayList<String>();

		try {
			// Create a statement from the conn object
			Statement statement = conn.createStatement();

			// Create a Result Set
			ResultSet rs = statement.executeQuery(query);

			String row = new String();

			ResultSetMetaData rmd = rs.getMetaData();
			int no_columns = rmd.getColumnCount();

			while (rs.next()) {
				for (int i = 0; i < no_columns; i++) {
					row += rs.getString(i + 1);
				}
			}

			if (row.length() == 0)
				return null;
			else
				return list;
		} catch (SQLException sql) {
			return null;
		}
	}

	// executes dml states like INSERT queries
	public void executeDML(String dml) throws SQLException {
		// Add your code here
		Statement st = conn.createStatement();
		st.executeUpdate(dml); // Execute query

	}

	public void CreateAccount(CreateAccountData createAccountData) {
		String username = createAccountData.getUsername();
		String password = createAccountData.getPassword();

		String dml = "INSERT INTO player(username, password) VALUES('" + username + "', AES_ENCRYPT('" + password
				+ "','key'));";
		try {
			executeDML(dml);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Returns true if the username and password combination is found in the
	// database
	public boolean credentialsValid(LoginData createAccountData) {
		String username = createAccountData.getUsername();
		String password = createAccountData.getPassword();

		try {
			Statement statement = conn.createStatement();

			ResultSet rs = statement.executeQuery("SELECT * FROM player WHERE username='" + username
					+ "' AND password= AES_ENCRYPT('" + password + "', 'key');");

			if (rs.next()) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			return false;
		}
	}

	// returns true if the username already exists in the database
	public boolean usernameExists(CreateAccountData createAccountData) {
		String username = createAccountData.getUsername();
		String password = createAccountData.getPassword();

		try {
			Statement statement = conn.createStatement();

			ResultSet rs = statement.executeQuery("SELECT * FROM player WHERE username='" + username + "';");

			if (rs.next()) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			return false;
		}
	}

}
