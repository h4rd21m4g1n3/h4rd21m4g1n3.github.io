package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class BudgetAppApplication {

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5433/BUDGET";
		String username = "postgres";
		String password = "123Killme";

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			System.out.println("Connected to the database");
			// Вы можете использовать объект connection для выполнения запросов к базе данных
		} catch (SQLException e) {
			System.out.println("Connection failed. Check output console.");
			e.printStackTrace();
		}
	}

}
