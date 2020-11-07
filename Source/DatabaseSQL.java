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
                         
      String room = "CREATE TABLE IF NOT EXISTS Rooms (\n"
                + "  roomNumber integer PRIMARY KEY NOT NULL, \n"
                + "  roomOcc integer, \n"
                + "  CHECK (roomNumber >= 0 AND roomNumber <= 20)"
                + ");";
                
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
                + "	roomNumber integer UNIQUE, \n"
                + "  emergencyContact VARCHAR(100), \n"
                + "  emergencyNumber  VARCHAR(100), \n"
                + "  insPolicy VARCHAR(100), \n"
                + "  insPolicyNo VARCHAR(100), \n"
                + "  primaryDoctorLastName VARCHAR(50) NOT NULL, \n"
                + "  iniDiagnosis VARCHAR(100) NOT NULL, \n"
                + "  admissionDate VARCHAR(100) NOT NULL, \n"
                + "  dischargeDate VARCHAR(100), \n"
                + "  FOREIGN KEY (patientID) REFERENCES Patient (patientID), \n"
                + "  FOREIGN KEY (primaryDoctorLastName) REFERENCES Doctor (lastName), \n"
                + "  FOREIGN KEY (roomNumber) REFERENCES Rooms (roomNumber) \n"
                + ");";         
   
      createTreatmentTable(); 
      createDiagnosisTable();
       
                                                  
      try (Connection conn = this.connect();) {
         Statement stmt  = conn.createStatement();
         stmt.execute(employee); 
         stmt.execute(room);     
         this.createRooms();
         stmt.execute(patient); 
         stmt.execute(inPatient); 
         stmt.close();         
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      
      }
   }
   
   /**
   Initializes 20 rooms in Rooms table and sets occupied to 0 (false)
   **/
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
   
   public void createTreatmentTable() {
   
      String sql = "CREATE TABLE IF NOT EXISTS Treatment (\n"
                + "  treatmentID integer, \n"
                + "  ptLastName VARCHAR(50) PRIMARY KEY, \n"
                + "  docLastName VARCHAR(50), \n"
                + "  treatmentType VARCHAR(1), \n"
                + "  treatment VARCHAR(50), \n"
                + "  treatmentDate VARCHAR(50) \n"
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
   
   //////////////////////////////////////////////////////////////
   //Create diagnosis array//////////////////////////////////////
   //diagnosis' are created with inpatient creation
   public void createDiagnosisTable() {
      String sql = "CREATE TABLE IF NOT EXISTS Diagnosis (\n"
                + "  diagnosisID integer, \n"
                + "  diagnosisName VARCHAR(100), \n"
                + "  patientID integer PRIMARY KEY, \n"
                + "  FOREIGN KEY (patientID) REFERENCES Patient (patientID) \n"
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
      this.dropTable("Diagnosis");
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
   
   public void dropTreatment() {
      String sql = "DROP TABLE Treatment;";
      
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
               
   //updates Rooms table when new in-patient is inserted            
      String update = "UPDATE Rooms"
                  + " SET roomOcc = 1"
                  + " WHERE roomNumber = " + patientIn.roomNo + ";";
                  
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         Statement stmt  = conn.createStatement();
         
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
         stmt.execute(update); 
      
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
   
      String sql = "INSERT INTO Treatment(treatmentID, ptLastName, docLastName, treatmentType,"
               + " treatment, treatmentDate)"
               + " VALUES (?, ?, ?, ?, ?, ?);";
   
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setInt(1, treatmentIn.treatmentID);
         ps.setString(2, treatmentIn.ptLastName);
         ps.setString(3, treatmentIn.docLastName);
         ps.setString(4, String.valueOf(treatmentIn.treatType));
         ps.setString(5, treatmentIn.treatment);
         ps.setString(6, treatmentIn.treatmentDate);
         ps.executeUpdate();
         ps.close();
      
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      }   
   }
   
   public void insertDiag(Diagnosis diagIn) {
   
      String sql = "INSERT INTO Diagnosis(diagnosisID, diagnosisName, patientID)"
               + " VALUES (?, ?, ?);";
   
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setInt(1, diagIn.diagID);
         ps.setString(2, diagIn.diagName);
         ps.setInt(3, diagIn.patientID);
         ps.executeUpdate();
         ps.close();
      
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      }   
   }
}