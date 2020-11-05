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
   public void createAllTables() {
   
   //NTS: when an employee of any type is entered into the DB, also enter into this
   //NTS: employee must exist in order for the other classes to exist
      String employee = "CREATE TABLE IF NOT EXISTS Employee (\n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL PRIMARY KEY, \n"
                + "  jobCategory VARCHAR(15) NOT NULL \n"
                + ");";
                
      createTable("Doctor");
      createTable("Nurse");
      createTable("Administrator");
      createTable("Technician");
      createTable("Volunteer");
      
      String patient = "CREATE TABLE IF NOT EXISTS Patient (\n"
                + "  patientID integer PRIMARY KEY NOT NULL, \n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL, \n"
                + "  primaryDoctorLastName VARCHAR(50) NOT NULL, \n"
                + "  iniDiagnosis VARCHAR(100) NOT NULL \n"
                + ");";         
   
      String inPatient = "CREATE TABLE IF NOT EXISTS InPatient (\n"
                + "  patientID integer PRIMARY KEY NOT NULL, \n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL, \n"
                + "	roomNumber integer, \n"
                + "  emergencyContact VARCHAR(100), \n"
                + "  emergencyNumber  VARCHAR(100), \n"
                + "  insPolicy VARCHAR(100), \n"
                + "  insPolicyNo VARCHAR(100), \n"
                + "  primaryDoctorLastName VARCHAR(50) NOT NULL, \n"
                + "  iniDiagnosis VARCHAR(100) NOT NULL, \n"
                + "  admissionDate VARCHAR(100) NOT NULL, \n"
                + "  dischargeDate VARCHAR(100), \n"
                + "  FOREIGN KEY (primaryDoctorLastName) REFERENCES Doctor (lastName) \n"
                + ");";         
                
      String room = "CREATE TABLE IF NOT EXISTS Rooms (\n"
                + "  roomNumber integer PRIMARY KEY NOT NULL, \n"
                + "  roomOcc integer, \n"
                + "  CHECK (roomNumber >= 0 AND roomNumber <= 20)"
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
         stmt.execute(employee); 
         stmt.execute(room);     
         this.createRooms();
         stmt.execute(patient); 
         stmt.execute(inPatient); 
         stmt.execute(treatment);       
         stmt.close();         
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      
      }
   }
   
   //initializes 20 rooms
   public void createRooms() {    
      try (Connection conn = this.connect();) {
         Statement stmt  = conn.createStatement();
         for (int i = 1; i <= 20; i++) {
            String sql = "INSERT INTO Rooms (roomNumber, roomOcc)"
               + "  VALUES (" + i + ", 0);";
            stmt.execute(sql); 
         }   
         stmt.close();         
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      
      }   
   }
   
   public void createTable(String tableNameIn) {
   
      String sql = "CREATE TABLE IF NOT EXISTS " + tableNameIn + "(\n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL PRIMARY KEY, \n"
                + "  FOREIGN KEY (lastName) REFERENCES Employee (lastName) \n"
                + ");"; 
                     
      try (Connection conn = this.connect();) {
         Statement stmt  = conn.createStatement();
         stmt.execute(sql);         
         stmt.close();         
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      }      
   }
      
   public void dropAllTables() {      
      this.dropTable("InPatient");
      this.dropTable("Patient");
      this.dropTable("Nurse");
      this.dropTable("Administrator");
      this.dropTable("Technician");
      this.dropTable("Volunteer");
      this.dropTable("Treatment");
      this.dropTable("Doctor");
      this.dropTable("Employee");
      this.dropTable("Rooms");
   }
   
   public void dropTable(String tableIn) {
      String sql = "DROP TABLE " + tableIn + ";";
      
      try (Connection conn = this.connect();) {
         Statement stmt  = conn.createStatement();
         stmt.execute(sql);       
         stmt.close();         
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      }      
   }
   
   public void insertEmployee(Employee employeeIn) {
   
      Character jobCat = employeeIn.jobCat;
      String jobCharToString = employeeIn.jobCat.toString();
      String profession;
   
      String sql = "INSERT INTO Employee(firstName, lastName, jobCategory)"
               + " VALUES (?, ?, ?);";
               
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setString(1, employeeIn.firstName);
         ps.setString(2, employeeIn.lastName);  
         ps.setString(3, jobCharToString);  
         ps.executeUpdate();
         
         switch (jobCat) {
            case 'D': 
               profession = "Doctor";
               insertEmpToCat(employeeIn, profession);
               break;
            case 'N': 
               profession = "Nurse";
               insertEmpToCat(employeeIn, profession);
               break;
            case 'A': 
               profession = "Administrator";
               insertEmpToCat(employeeIn, profession);
               break;
            case 'T': 
               profession = "Technician";
               insertEmpToCat(employeeIn, profession);
               break;
            case 'V': 
               profession = "Volunteer";
               insertEmpToCat(employeeIn, profession);
               break;
            default:
               break;
         }
         ps.close();
         
      } catch (SQLException e) {
         System.out.println("Insert Employee error: " + e.getMessage());
         e.printStackTrace();
      
      }   
   }
   
   public void insertEmpToCat(Employee employeeIn, String professionIn) {
      String sql = "INSERT INTO " + professionIn + "(firstName, lastName) VALUES (?, ?);";
      
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setString(1, employeeIn.firstName);
         ps.setString(2, employeeIn.lastName);
         ps.executeUpdate();
         ps.close();
         
      } catch (SQLException e) {
         System.out.println(e.getMessage());
      }
   }
   
   public void insertInPatient(Patient patientIn) {
      String sql = "INSERT INTO InPatient(patientID, firstName, lastName, roomNumber,"
               + " emergencyContact, emergencyNumber, insPolicy, insPolicyNo,"
               + " primaryDoctorLastName, iniDiagnosis, admissionDate, dischargeDate)"
               + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"; 
               
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         
         ps.setInt(1, patientIn.patientID);
         ps.setString(2, patientIn.firstName);
         ps.setString(3, patientIn.lastName);          
         ps.setInt(4, patientIn.roomNo);
         ps.setString(5, patientIn.emergContact);
         ps.setString(6, patientIn.emergNo);
         ps.setString(7, patientIn.insPolicy);
         ps.setString(8, patientIn.insPolicyNo);
         ps.setString(9, patientIn.docLastName);
         ps.setString(10, patientIn.iniDiagnosis);
         ps.setString(11, patientIn.admissionDate);
         ps.setString(12, patientIn.dischargeDate); 
                 
         ps.executeUpdate();
         ps.close();
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      
      } 
   }
   
   public void insertPatient(Patient patientIn) {
      String sql = "INSERT INTO Patient(patientID, firstName, lastName,"
               + " primaryDoctorLastName, iniDiagnosis)"
               + " VALUES (?, ?, ?, ?, ?);";
      
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         
         ps.setInt(1, patientIn.patientID);
         ps.setString(2, patientIn.firstName);
         ps.setString(3, patientIn.lastName);          
         ps.setString(4, patientIn.docLastName);
         ps.setString(5, patientIn.iniDiagnosis);
                 
         ps.executeUpdate();
         ps.close();
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      
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
         System.out.println(e.getMessage());
      }   
   }
        
}