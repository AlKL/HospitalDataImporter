import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;   

public class DatabaseSQL {
   
   public Connection connect() {
        // SQLite connection string
      String url = "jdbc:sqlite:/Users/alexandrekhien/Google Drive/0 - Computer Science"
                     + "/Auburn/CPSC5133 Database 2/Hospital Project/Database/HospitalDB.sl3"; 
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
      Creates all Tables required for the DB.
   **/
   public void createTables() {
   
   //NTS: when an employee of any type is entered into the DB, also enter into this
   //NTS: employee must exist in order for the other classes to exist
      String employee = "CREATE TABLE IF NOT EXISTS Employee (\n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL PRIMARY KEY, \n"
                + "  jobCategory VARCHAR(15) NOT NULL \n"
                + ");";
                
                
                
   
      String doctor = "CREATE TABLE IF NOT EXISTS Doctor (\n"
                + "  firstName VARCHAR(50), \n"
                + "  lastName VARCHAR(50) PRIMARY KEY \n"
                + ");";
                
      String patient = "CREATE TABLE IF NOT EXISTS Patient (\n"
                + "  firstName VARCHAR(50), \n"
                + "  lastName VARCHAR(50) PRIMARY KEY, \n"
                + "	roomNumber integer,\n"
                + "  emergencyContact VARCHAR(100), \n"
                + "  emergencyNumber  VARCHAR(100), \n"
                + "  insPolicy VARCHAR(100), \n"
                + "  insPolicyNo VARCHAR(100), \n"
                + "  primaryDoctorLastName VARCHAR(50), \n"
                + "  iniDiagnosis VARCHAR(100), \n"
                + "  admissionDate VARCHAR(100), \n"
                + "  dischargeDate VARCHAR(100), \n"
                + "  FOREIGN KEY (primaryDoctorLastName) REFERENCES Doctor (lastName) \n"
                + ");";     
                
      String admin = "CREATE TABLE IF NOT EXISTS Administrator (\n"
                + "  firstName VARCHAR(50), \n"
                + "  lastName VARCHAR(50) PRIMARY KEY \n"
                + ");";   
                
      String volunteer = "CREATE TABLE IF NOT EXISTS Volunteer (\n"
                + "  firstName VARCHAR(50), \n"
                + "  lastName VARCHAR(50) PRIMARY KEY \n"
                + ");";   
                
      String nurse = "CREATE TABLE IF NOT EXISTS Nurse (\n"
                + "  firstName VARCHAR(50), \n"
                + "  lastName VARCHAR(50) PRIMARY KEY \n"
                + ");";   
                
      String tech = "CREATE TABLE IF NOT EXISTS Technician (\n"
                + "  firstName VARCHAR(50), \n"
                + "  lastName VARCHAR(50) PRIMARY KEY \n"
                + ");";      
                
      String treatment = "CREATE TABLE IF NOT EXISTS Treatment (\n"
                + "  firstName VARCHAR(50), \n"
                + "  lastName VARCHAR(50), \n"
                + "  treatMentType VARCHAR(1), \n"
                + "  treatment VARCHAR(50), \n"
                + "  treatmentDate VARCHAR(50) \n"
                + ");";   
                                   
      try (Connection conn = this.connect();) {
         Statement stmt  = conn.createStatement();
         stmt.execute(patient);   
         stmt.execute(doctor); 
         stmt.execute(admin);       
         stmt.execute(volunteer);  
         stmt.execute(nurse);       
         stmt.execute(tech);  
         stmt.execute(treatment);       
         stmt.execute(employee);   
             
         stmt.close();         
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
   }
   
   
   public void insertEmployee(Employee employeeIn) {
   
      String jobCharToString = employeeIn.jobCategory.toString();
   
      String sql = "INSERT INTO Employee(firstName, lastName, jobCategory)"
               + " VALUES (?, ?, ?);";
               
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setString(1, employeeIn.firstName);
         ps.setString(2, employeeIn.lastName);  
         ps.setString(3, jobCharToString);  
         ps.executeUpdate();
         ps.close();  
             
      } catch (SQLException e) {
         System.out.println("Insert Employee error: " + e.getMessage());
      }   
   }
   
   
   public void insertTreatment(Treatment treatmentIn) {
   
      String sql = "INSERT INTO Treatment(firstName, lastName, treatmentType,"
               + " treatment, treatmentDate)"
               + " VALUES (?, ?, ?, ?, ?);";
   
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setString(1, treatmentIn.firstName);
         ps.setString(2, treatmentIn.lastName);
         ps.setString(3, String.valueOf(treatmentIn.type));
         ps.setString(4, treatmentIn.treatment);
         ps.setString(5, treatmentIn.treatmentDate);
         ps.executeUpdate();
         ps.close();
      
      } catch (SQLException e) {
         System.out.println(e.getMessage() + "THIS");
      }   
   }

   
   public void insertPerson(Person personIn) {
      Character personType = personIn.jobCategory;
      String sql;
      
      switch(personType) {
         case 'P':
            sql = "INSERT INTO Patient(firstName, lastName, roomNumber,"
               + " emergencyContact, emergencyNumber, insPolicy, insPolicyNo,"
               + " primaryDoctorLastName, iniDiagnosis, admissionDate, dischargeDate)"
               + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            break;
         case 'D':
            sql = "INSERT INTO Doctor(firstName, lastName) VALUES (?, ?);";
            break;
         case 'A':
            sql = "INSERT INTO Administrator(firstName, lastName) VALUES (?, ?);";
            break;
         case 'V':
            sql = "INSERT INTO Volunteer(firstName, lastName) VALUES (?, ?);";
            break;
         case 'N':
            sql = "INSERT INTO Nurse(firstName, lastName) VALUES (?, ?);";
            break;
         case 'T':
            sql = "INSERT INTO Technician(firstName, lastName) VALUES (?, ?);";
            break;
         default:
            sql = "";
      }
      
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setString(1, personIn.firstName);
         ps.setString(2, personIn.lastName);
         
         if (personIn.roomNumber > 0) {
            ps.setInt(3, personIn.roomNumber);
            ps.setString(4, personIn.emergencyContact);
            ps.setString(5, personIn.emergencyNumber);
            ps.setString(6, personIn.insPolicy);
            ps.setString(7, personIn.insPolicyNo);
            ps.setString(8, personIn.primaryDoctorLastName);
            ps.setString(9, personIn.iniDiagnosis);
            ps.setString(10, personIn.admissionDate);
            ps.setString(11, personIn.dischargeDate);
         }
         ps.executeUpdate();
         ps.close();
      
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }   
   }
   
   public void dropTables() {
      String dropPatient = "DROP TABLE Patient";  
      String dropDoctor = "DROP TABLE Doctor";  
      String dropAdmin = "DROP TABLE Administrator";  
      String dropVolunteer = "DROP TABLE Volunteer";  
      String dropNurse = "DROP TABLE Nurse";  
      String dropTech = "DROP TABLE Technician";
      String dropTreatment = "DROP TABLE Treatment"; 
      String dropEmployee = "DROP TABLE Employee"; 
   
       
      try (Connection conn = this.connect();) {
         Statement stmt  = conn.createStatement();
         stmt.execute(dropPatient);   
         stmt.execute(dropDoctor);  
         stmt.execute(dropAdmin);       
         stmt.execute(dropVolunteer);       
         stmt.execute(dropNurse);       
         stmt.execute(dropTech); 
         stmt.execute(dropTreatment); 
         stmt.execute(dropEmployee);      
         stmt.close();         
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
   }
        
}