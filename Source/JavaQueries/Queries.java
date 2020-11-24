package JavaQueries;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Queries {
    //Constructor to connect to database
    public Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/Users/alexandrekhien/Google Drive/0 - Computer Science"
                + "/Auburn/CPSC5133 Database 2/Hospital Project/Database/database.sl3";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);

            //This will turn on foreign keys
            //by default SQLite turns them off
            conn.createStatement().executeUpdate("PRAGMA foreign_keys = ON;");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    //1.1 - Room Utilization
    public void roomUtilization() {
        String sql = "SELECT roomNumber, firstName, lastName, admissionDate"
            + " FROM currentInPatient;";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("roomNumber") +  "\t"
                        + rs.getString("firstName") + "\t"
                        + rs.getString("lastName") + "\t"
                        + rs.getString("admissionDate"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //1.2 - List the rooms that are currently unoccupied
    public void roomOccupation() {
        String sql = "SELECT roomNumber " +
                " FROM Rooms " +
                " WHERE roomOcc = 0;";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("roomNumber") +  "\t");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    
}