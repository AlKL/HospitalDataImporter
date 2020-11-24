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

    //1.3 List all rooms, rooms occupied, by whom and admission dates for those occupied
    public void allRoomsOccupation() {
        String sql = "SELECT Rooms.roomNumber, currentInPatient.firstName, currentInPatient.lastName, "
                + "currentInPatient.admissionDate "
                + "FROM Rooms "
                + "LEFT JOIN currentInPatient "
                + "ON Rooms.roomNumber = currentInPatient.roomNumber;";

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

    //2.1 List all patients in the database with all personal information
    public void allPatients() {
        String sql = "SELECT * "
                + " FROM Patient;";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("patientID") +  "\t"
                        + rs.getString("firstName") + "\t"
                        + rs.getString("lastName") + "\t"
                        + rs.getString("primaryDoctorLastName"));
                }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //2.2 List all currently admitted inpatients at the hospital
    public void allCurrentPatients() {
        String sql = "SELECT patientID, firstName, lastName "
                + " FROM currentInPatient;";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("patientID") +  "\t"
                        + rs.getString("firstName") + "\t"
                        + rs.getString("lastName"));}

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //2.3 List patients discharged within a given range
    public void patientsWithinDischargeRange(String startDateIn, String lastDateIn) {

        String sql = "SELECT patientID, firstName, lastName\n"
                + " FROM InPatient "
                + " WHERE dischargeDate "
                + " BETWEEN '" + startDateIn + "' AND '" + lastDateIn + "';";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("patientID") +  "\t"
                        + rs.getString("firstName") + "\t"
                        + rs.getString("lastName"));}

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //2.4

}