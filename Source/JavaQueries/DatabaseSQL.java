package JavaQueries;

import JavaQueries.Tables;
import Classes.*;

import java.sql.DriverManager;
import java.sql.Connection;
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
   
   /** Creates all Tables required for the DB. **/
   public void createAllTables() {
   
   //NTS: when an employee of any type is entered into the DB, also enter into this
   //NTS: employee must exist in order for the other classes to exist
      String employee = Tables.employee();
      String doctor = Tables.employeeTable("Doctor");
      String nurse = Tables.employeeTable("Nurse");
      String administrator = Tables.employeeTable("Administrator");
      String technician = Tables.employeeTable("Technician");
      String volunteer = Tables.employeeTable("Volunteer");
      String room = Tables.room();
      String patient = Tables.patient(); 
      String inPatient = Tables.inPatient();
      createTreatmentTable(); 
      String diagnosis = Tables.diagnosis();
       
      try (Connection conn = this.connect();) {
         Statement stmt  = conn.createStatement();
         stmt.execute(employee); 
         stmt.execute(doctor); 
         stmt.execute(nurse); 
         stmt.execute(administrator); 
         stmt.execute(technician); 
         stmt.execute(volunteer); 
         stmt.execute(room);     
         this.createRooms();
         stmt.execute(patient); 
         stmt.execute(inPatient); 
         stmt.execute(diagnosis); 
         stmt.close();         
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      }
   }
   
   public void createTreatmentTable() {
   
      String sql = "CREATE TABLE IF NOT EXISTS Treatment (\n"
                + "  treatmentID integer, \n"
                + "  ptLastName VARCHAR(50), \n"
                + "  docLastName VARCHAR(50), \n"
                + "  treatmentType VARCHAR(1), \n"
                + "  treatment VARCHAR(50), \n"
                + "  treatmentDate TEXT \n"
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
   
      Character jobCat = employeeIn.getJobCat();
      String jobCharToString = jobCat.toString();
      String profession;
   
      String sql = "INSERT INTO Employee(firstName, lastName, jobCategory)"
               + " VALUES (?, ?, ?);";
               
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setString(1, employeeIn.getFirstName());
         ps.setString(2, employeeIn.getLastName());
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
         ps.setString(1, employeeIn.getFirstName());
         ps.setString(2, employeeIn.getLastName());
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
                  + " WHERE roomNumber = " + patientIn.getRoomNo() + ";";
                  
      try (Connection conn = this.connect();) {
         PreparedStatement ps = conn.prepareStatement(sql);
         Statement stmt  = conn.createStatement();
         
         ps.setInt(1, patientIn.getPatientID());
         ps.setString(2, patientIn.getFirstName());
         ps.setString(3, patientIn.getLastName());
         ps.setInt(4, patientIn.getRoomNo());
         ps.setString(5, patientIn.getEmergContact());
         ps.setString(6, patientIn.getEmergNo());
         ps.setString(7, patientIn.getInsPolicy());
         ps.setString(8, patientIn.getInsPolicyNo());
         ps.setString(9, patientIn.getDocLastName());
         ps.setString(10, patientIn.getIniDiagnosis());
         ps.setString(11, patientIn.getAdmissionDate());
         ps.setString(12, patientIn.getDischargeDate());
                 
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
         
         ps.setInt(1, patientIn.getPatientID());
         ps.setString(2, patientIn.getFirstName());
         ps.setString(3, patientIn.getLastName());
         ps.setString(4, patientIn.getDocLastName());
         ps.setString(5, patientIn.getIniDiagnosis());
                 
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
         ps.setInt(1, treatmentIn.getTreatmentID());
         ps.setString(2, treatmentIn.getPtLastName());
         ps.setString(3, treatmentIn.getDocLastName());
         ps.setString(4, String.valueOf(treatmentIn.getTreatType()));
         ps.setString(5, treatmentIn.getTreatment());
         ps.setString(6, treatmentIn.getTreatmentDate());
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
         ps.setInt(1, diagIn.getDiagID());
         ps.setString(2, diagIn.getDiagName());
         ps.setInt(3, diagIn.getPatientID());
         ps.executeUpdate();
         ps.close();
      
      } catch (SQLException e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
      }   
   }
}