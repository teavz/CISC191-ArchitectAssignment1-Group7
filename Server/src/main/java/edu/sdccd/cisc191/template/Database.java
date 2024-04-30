package edu.sdccd.cisc191.template;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import org.h2.tools.Server;

public class Database {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/./Server;DB_CLOSE_ON_EXIT=FALSE;";
    static final String USER = "Guest";
    static final String PASS = "AppleBanana123";

    private Server server;
    private Connection connection;
    private Statement statement;

    public Database() throws SQLException {


            server = Server.createTcpServer("-tcpPort", "5432", "-tcpAllowOthers", "-ifNotExists").start();
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

    }

    public void createTables() throws SQLException{
        statement.execute("CREATE TABLE IF NOT EXISTS subject(class INT PRIMARY KEY AUTO_INCREMENT, nameOfSubject VARCHAR(64), FOREIGN KEY (ScheduleID) REFERENCES schedule(ScheduleID))");
    }
    public void createScheduleTables() throws SQLException{
        statement.execute("CREATE TABLE IF NOT EXISTS schedule(ScheduleID INT PRIMARY KEY AUTO_INCREMENT, gpa DOUBLE)");
    }

    public void create(Subject subject) throws SQLException {
        String sql = "INSERT INTO subject(nameOfSubject) VALUES(?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, subject.getNameOfSubject());
        int numRows = ps.executeUpdate();
        if (numRows == 0) {
            throw new SQLException("No rows affected");
        }
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()){
            subject.setId(rs.getInt(1));
        }
    }
    public void createSchedule(Schedule schedule) throws SQLException{
        String sql = "INSERT INTO schedule(gpa) VALUES(?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, schedule.getGpa());
        int numRows = ps.executeUpdate();
        if (numRows == 0) {
            throw new SQLException("No rows affected");
        }
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()){
            schedule.setId(rs.getInt(1));
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
