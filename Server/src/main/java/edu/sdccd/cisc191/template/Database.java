package edu.sdccd.cisc191.template;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.time.*;

import org.h2.server.web.ConnectionInfo;
import org.h2.tools.Server;

public class Database {
    static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/collegeclass";
    static final String USER = "Guest";
    static final String PASS = "AppleBanana123";

    private Server server;
    private Connection connection;
    private Statement statement;

    public Database() throws SQLException {


        org.h2.tools.Server.createTcpServer("-tcp").start();
        connection = DriverManager.getConnection(JDBC_URL, USER, PASS);


        try {
            //putting it all in when Database gets created ig
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS schedule(ScheduleID INT AUTO_INCREMENT PRIMARY KEY, gpa DOUBLE)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS subject(subjectObject VARBINARY, nameOfSubject VARCHAR(64), " +
                    "ScheduleID INT, entryDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (ScheduleID) REFERENCES schedule(ScheduleID))");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
/*
    public void createTables() throws SQLException{
        statement.execute("CREATE TABLE IF NOT EXISTS subject(class INT PRIMARY KEY AUTO_INCREMENT, nameOfSubject VARCHAR(64), FOREIGN KEY (ScheduleID) REFERENCES schedule(ScheduleID))");
    }
    public void createScheduleTables() throws SQLException{
        statement.execute("CREATE TABLE IF NOT EXISTS schedule(ScheduleID INT PRIMARY KEY AUTO_INCREMENT, gpa DOUBLE)");
    }
*/

    public synchronized void createSchedule(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO schedule(gpa, scheduleID) VALUES(?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, schedule.getGpa());
        ps.setInt(2, schedule.getId());

        int numRows = ps.executeUpdate();
        if (numRows == 0) {
            throw new SQLException("No rows affected");
        }
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {

            schedule.setId(rs.getInt(1));
        }
    }

    public synchronized Connection getConnection() {
        return connection;
    }


    public synchronized void create(Subject subject, Schedule schedule) throws SQLException {


        String sql = "INSERT INTO subject(subjectObject, nameOfSubject, ScheduleID) VALUES(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set subjectObject
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(subject);
            byte[] subjectBytes = bos.toByteArray();
            ps.setBytes(1, subjectBytes);

            // Set nameOfSubject
            ps.setString(2, subject.getNameOfSubject());

            // Set ScheduleID
            ps.setInt(3, schedule.getId());

            int numRows = ps.executeUpdate();
            if (numRows == 0) {
                throw new SQLException("No rows affected");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    public synchronized ArrayList<Subject> gatherSubject(long courseID) throws SQLException {
        ArrayList<Subject> subjectList = new ArrayList<>();
        String selectQuery = "SELECT id, nameOfSubject, subjectObject FROM subject WHERE ScheduleID = ?";

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, courseID);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                // Deserialize the Subject object from the SubjectObject column
                byte[] subjectBytes = rs.getBytes("SubjectObject");
                if (subjectBytes != null) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(subjectBytes);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    try {
                        Subject deserializedSubject = (Subject) ois.readObject();
                        Subject subject = new Subject(deserializedSubject);
                        subjectList.add(subject);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Handle SQLException or IOException
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return subjectList;
    }

     */

    public synchronized void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }
    }

    public synchronized Subject getSubjectFromBytes(byte[] subjectBytes) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(subjectBytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            Subject subject = (Subject) ois.readObject();
            return subject;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized ArrayList<Subject> getSubjectsByScheduleID(long scheduleID) throws SQLException {
        ArrayList<Subject> subjectList = new ArrayList<>();
        String selectQuery = "SELECT subjectObject FROM subject WHERE ScheduleID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, scheduleID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                byte[] subjectBytes = rs.getBytes("subjectObject");
                Subject subject = getSubjectFromBytes(subjectBytes);
                if (subject != null) {
                    subjectList.add(subject);
                }
            }
        }

        return subjectList;
    }

    /**
     * to gather courseID of the last time a schedule was inserted into database yay
     * @return
     * @throws SQLException
     */
    public synchronized long getCourseIDOfSchedule() throws SQLException {
        long courseID = -1;
        String selectQuery = "SELECT ScheduleID FROM schedule ORDER BY entryDateTime DESC LIMIT 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                courseID = rs.getLong("ScheduleID");
            }
        }

        return courseID;
    }





}