package com.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/access")
public class AccessDatabaseController {

	// Upload Access database file and query data
	@PostMapping("/upload")
	public List<Map<String, Object>> uploadAndQuery(@RequestParam("file") MultipartFile file,
			@RequestParam("tableName") String tableName) {
		List<Map<String, Object>> results = new ArrayList<>();
		Connection connection = null;
		try {
			// Save the uploaded file to the server's temporary directory
			File tempFile = File.createTempFile("upload-", ".accdb");
			file.transferTo(tempFile);

			// Connect to the Access database
			String dbUrl = "jdbc:ucanaccess://" + tempFile.getAbsolutePath();
			connection = DriverManager.getConnection(dbUrl);

			// Query the specified table in the database
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM " + tableName;
			ResultSet resultSet = statement.executeQuery(query);

			// Store query results in a List<Map<String, Object>>
			while (resultSet.next()) {
				Map<String, Object> row = new HashMap<>();
				int columnCount = resultSet.getMetaData().getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = resultSet.getMetaData().getColumnName(i);
					row.put(columnName, resultSet.getObject(i));
				}
				results.add(row);
			}

			// Close the ResultSet and Statement
			resultSet.close();
			statement.close();

			// Mark temporary file for deletion upon JVM exit
			tempFile.deleteOnExit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the database connection
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return results;
	}
}
