package edu.sdccd.cisc191.template;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    static final String DB_URL = "hello";
    static final String USER = "guest";
    static final String PASS = "guest123";
    public static void main(String args[]) throws SQLException {
        DriverManager.registerDriver();
    }
}
