package JavaQueries;

/**
 * SQL table string getters
 */
public class Tables {
   /**
    * Creates table for various type of employees
    * @param employeeIn employee object
    * @return SQL create given employee table string
    */
   public static String employeeTable(String employeeIn) {
      return "CREATE TABLE IF NOT EXISTS " + employeeIn + "(\n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL PRIMARY KEY, \n"
                + "  FOREIGN KEY (lastName) REFERENCES Employee (lastName) \n"
                + ");"; 
   }

   /**
    * SQL create Employee table
    * @return SQL employee table string
    */
   public static String employee() {
      return "CREATE TABLE IF NOT EXISTS Employee (\n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL PRIMARY KEY, \n"
                + "  jobCategory VARCHAR(15) NOT NULL \n"
                + ");";
   }

   /**
    * SQL create Rooms table
    * @return SQL rooms table string
    */
   public static String room() {
      return "CREATE TABLE IF NOT EXISTS Rooms (\n"
                + "  roomNumber integer PRIMARY KEY NOT NULL, \n"
                + "  roomOcc integer, \n"
                + "  CHECK (roomNumber >= 0 AND roomNumber <= 20)"
                + ");";
   }

   /**
    * SQL create Patient table
    * @return SQL patient table string
    */
   public static String patient() {
      return "CREATE TABLE IF NOT EXISTS Patient (\n"
                + "  patientID integer PRIMARY KEY NOT NULL, \n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL, \n"
                + "  primaryDoctorLastName VARCHAR(50) NOT NULL, \n"
                + "  FOREIGN KEY (primaryDoctorLastName) REFERENCES Doctor (lastName)"
                + ");";
   }

   /**
    * SQL create InPatient table
    * @return SQL InPatient table string
    */
   public static String inPatient() {
      return "CREATE TABLE IF NOT EXISTS InPatient (\n"
                + "  inPtNo integer PRIMARY KEY NOT NULL, \n"
                + "  patientID integer NOT NULL, \n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL, \n"
                + "	 roomNumber integer, \n"
                + "  emergencyContact VARCHAR(100), \n"
                + "  emergencyNumber  VARCHAR(100), \n"
                + "  insPolicy VARCHAR(100), \n"
                + "  insPolicyNo VARCHAR(100), \n"
                + "  primaryDoctorLastName VARCHAR(50) NOT NULL, \n"
                + "  iniDiagnosis VARCHAR(100) NOT NULL, \n"
                + "  admissionDate TEXT, \n"
                + "  dischargeDate TEXT, \n"
                + "  FOREIGN KEY (patientID) REFERENCES Patient (patientID), \n"
                + "  FOREIGN KEY (primaryDoctorLastName) REFERENCES Doctor (lastName) \n"
                + "  FOREIGN KEY (roomNumber) REFERENCES Rooms (roomNumber) \n"
                + ");";
   }

   /**
    * SQL create CurrentInPatient table
    * @return SQL CurrentInPatient table string
    */
   public static String currentInPatient() {
      return "CREATE TABLE IF NOT EXISTS currentInPatient (\n"
              + "  inPtNo integer PRIMARY KEY NOT NULL, \n"
              + "  patientID integer NOT NULL, \n"
              + "  firstName VARCHAR(50) NOT NULL, \n"
              + "  lastName VARCHAR(50) NOT NULL, \n"
              + "  roomNumber integer UNIQUE, \n"
              + "  emergencyContact VARCHAR(100), \n"
              + "  emergencyNumber  VARCHAR(100), \n"
              + "  insPolicy VARCHAR(100), \n"
              + "  insPolicyNo VARCHAR(100), \n"
              + "  primaryDoctorLastName VARCHAR(50) NOT NULL, \n"
              + "  iniDiagnosis VARCHAR(100) NOT NULL, \n"
              + "  admissionDate TEXT NOT NULL, \n"
              + "  FOREIGN KEY (inPtNo) REFERENCES InPatient (inPtNo), \n"
              + "  FOREIGN KEY (patientID) REFERENCES Patient (patientID), \n"
              + "  FOREIGN KEY (primaryDoctorLastName) REFERENCES Doctor (lastName), \n"
              + "  FOREIGN KEY (roomNumber) REFERENCES Rooms (roomNumber) \n"
              + ");";
   }

   /**
    * SQL create OutPatient table
    * @return SQL create OutPatient table string
    */
   public static String outPatient() {
      return "CREATE TABLE IF NOT EXISTS OutPatient(\n"
              + "  patientID integer PRIMARY KEY NOT NULL, \n"
              + "  primaryDoctorLastName VARCHAR(50) NOT NULL, \n"
              + "  iniDiagnosis VARCHAR(100) NOT NULL, \n"
              + "  FOREIGN KEY (patientID) REFERENCES Patient (patientID), \n"
              + "  FOREIGN KEY (primaryDoctorLastName) REFERENCES Doctor (lastName) \n"
              + ");";
   }

   /**
    * SQL create Diagnosis table
    * @return SQL create Diagnosis table string
    */
   public static String diagnosis() {
      return "CREATE TABLE IF NOT EXISTS Diagnosis(\n"
              + "  diagnosisID integer PRIMARY KEY, \n"
              + "  diagnosisName VARCHAR(100) NOT NULL \n"
              + ");";
   }
}