package JavaQueries;
import Classes.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class SpreadsheetPrint {
    private String fromTable = "";

    /**
     * Connects to a given database
     * @return a connection
     */
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

    /**
     * Prints spreadsheet with all Employee details
     * @throws IOException
     */
    public void employeePrint() throws IOException {
        String sql = "SELECT firstName, lastName, jobCategory"
                + " FROM Employee;";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                fromTable += (rs.getString("firstName") + ","
                        + rs.getString("lastName") + ","
                        + rs.getString("jobCategory") + "\n");
            }
        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }

        FileWriter output = new FileWriter("Input and Results/Employee Spreadsheet.csv");
        String result = "First Name, Last Name, Job Category\n" + fromTable;
        output.write(result);
        output.close();
        System.out.println("Created 'Employee Spreadsheet.csv' file containing all employees at the hospital.");
    }

    /**
     * Prints spreadsheet with all Patient details
     * @throws IOException
     */
    public void patientPrint() throws IOException {
        String sql = "SELECT patientID, firstName, lastName, primaryDoctorLastName"
                + " FROM Patient;";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                fromTable += (rs.getInt("patientID") + ","
                        + rs.getString("firstName") + ","
                        + rs.getString("lastName") + ","
                        + rs.getString("primaryDoctorLastName") + "\n");
            }
        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }

        FileWriter output = new FileWriter("Input and Results/Patient Spreadsheet.csv");
        String result = "PatientID, First Name, Last Name, Doctor\n" + fromTable;
        output.write(result);
        output.close();
        System.out.println("Created 'Patient Spreadsheet.csv' file containing all patients at the hospital.");
    }

    /**
     * Prints spreadsheet with all Treatment details
     * @throws IOException
     */
    public void treatmentPrint() throws IOException {
        String sql = "SELECT treatmentID, ptLastName, docLastName, treatmentType, treatment, treatmentDate"
                + " FROM Treatment;";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql))   {

            // loop through the result set
            while (rs.next()) {
                fromTable += (rs.getInt("treatmentID") + ","
                        + rs.getString("ptLastName") + ","
                        + rs.getString("docLastName") + ","
                        + rs.getString("treatmentType") + ","
                        + rs.getString("treatment") + ","
                        + rs.getString("treatmentDate") + "\n");
            }
        } catch (SQLException e) {
            System.out.println("Query issue. Exiting..");
        }

        FileWriter output = new FileWriter("Input and Results/Treatment Spreadsheet.csv");
        String result = "Treatment ID, Patient Name, Doctor, Type, Treatment, Date\n" + fromTable;
        output.write(result);
        output.close();
        System.out.println("Created 'Treatment Spreadsheet.csv' file containing all treatments at the hospital.");
    }
}
