package JavaQueries;

public class Tables {
   public static String employeeTable(String employeeIn) {
      return "CREATE TABLE IF NOT EXISTS " + employeeIn + "(\n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL PRIMARY KEY, \n"
                + "  FOREIGN KEY (lastName) REFERENCES Employee (lastName) \n"
                + ");"; 
   }

   public static String employee() {
      return "CREATE TABLE IF NOT EXISTS Employee (\n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL PRIMARY KEY, \n"
                + "  jobCategory VARCHAR(15) NOT NULL \n"
                + ");";
   }
   
   public static String room() {
      return "CREATE TABLE IF NOT EXISTS Rooms (\n"
                + "  roomNumber integer PRIMARY KEY NOT NULL, \n"
                + "  roomOcc integer, \n"
                + "  CHECK (roomNumber >= 0 AND roomNumber <= 20)"
                + ");";
   }
   
   public static String patient() {
      return "CREATE TABLE IF NOT EXISTS Patient (\n"
                + "  patientID integer PRIMARY KEY NOT NULL, \n"
                + "  firstName VARCHAR(50) NOT NULL, \n"
                + "  lastName VARCHAR(50) NOT NULL, \n"
                + "  primaryDoctorLastName VARCHAR(50) NOT NULL, \n"
                + "  FOREIGN KEY (primaryDoctorLastName) REFERENCES Doctor (lastName)"
                + ");";
   }
   
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

   public static String diagnosis() {
      return "CREATE TABLE IF NOT EXISTS Diagnosis(\n"
                + "  diagnosisID integer PRIMARY KEY, \n"
                + "  diagnosisName VARCHAR(100) NOT NULL \n"
                + ");";
   }

   public static String outPatient() {
      return "CREATE TABLE IF NOT EXISTS OutPatient(\n"
              + "  patientID integer PRIMARY KEY NOT NULL, \n"
              + "  primaryDoctorLastName VARCHAR(50) NOT NULL, \n"
              + "  iniDiagnosis VARCHAR(100) NOT NULL, \n"
              + "  FOREIGN KEY (patientID) REFERENCES Patient (patientID), \n"
              + "  FOREIGN KEY (primaryDoctorLastName) REFERENCES Doctor (lastName) \n"
              + ");";
   }
}