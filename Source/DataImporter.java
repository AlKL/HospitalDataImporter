import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.HashMap;
import Classes.*;
import JavaQueries.DatabaseSQL;

public class DataImporter {
   //Instance Variables
   // Variables for Patients
   private static HashMap<String, Integer> ptMap;     //tracks new patients name and ID
   private int inPatientNo;                           //tracks inPatient No
   private static int patientID;                      //tracks patientID
   private Patient[] patientList;                     //tracks all patients
   private Patient[] inPatientList;                   //tracks all in-patients
   private Patient[] currentInPatientList;            //tracks current in-patients
   private Patient[] outPatientList;                  //tracks all out-patients

   // Variables for Employees
   private Employee[] employeeList;                   //tracks all employees

   // Variables for Diagnosis'
   private static HashMap<String, Integer> diagMap;   //tracks new diagnosis names and ID
   private Diagnosis[] diagList;                      //tracks all diagnosis
   private static int diagID;                         //tracks diagnosisID

   // Variables for Treatments
   private static HashMap<String, Integer> treatMap;  //tracks new treatment names and ID
   private static int treatmentID;                    //tracks treatmentID
   private Treatment[] treatmentList;                 //

   //constructor
   public DataImporter() {
      patientList = new Patient[0];
      patientID = 1001;         
      inPatientList = new Patient[0];
      currentInPatientList = new Patient[0];
      inPatientNo = 300;
      ptMap = new HashMap<>();
      outPatientList = new Patient[0];
      treatmentList = new Treatment[0];
      treatmentID = 70001;
      treatMap = new HashMap<>();
      employeeList = new Employee[0];
      diagID = 50001;
      diagList = new Diagnosis[0];
      diagMap = new HashMap<>();
   }

   public void readHospitalFile(String fileName) 
                                       throws FileNotFoundException {  
      Scanner scanFile = new Scanner(new File(fileName));
      Scanner scanChecker = new Scanner(new File(fileName));

      //Connect to Database
      DatabaseSQL database = new DatabaseSQL();
      connectToDatabase(database);

      //check file type - hospitalInfo or treatmentInfo
      String line0 = scanChecker.nextLine().trim();
      Scanner wordScan0 = new Scanner(line0).useDelimiter(",");
      String firstWord = wordScan0.next().trim();
      
      if (firstWord.length() == 1) {
         while (scanFile.hasNext()) {
            String line1 = scanFile.nextLine().trim();
            try {
               Scanner wordScan = new Scanner(line1).useDelimiter(",");
               Character personType = wordScan.next().toUpperCase().trim().charAt(0);
               String firstName = wordScan.next().trim();
               String lastName = wordScan.next().trim(); 
            
               switch (personType) {
                  //If the person is a PATIENT
                  case 'P':
                     //Scan the rest of the PATIENT object
                     int roomNo = wordScan.nextInt();
                     String emergContact = wordScan.next().trim();
                     String emergNo = wordScan.next().trim();
                     String insPolicy = wordScan.next().trim();
                     String insPolicyNo = wordScan.next().trim();
                     String docLastName = wordScan.next().trim();
                     String iniDiagnosis = wordScan.next().trim();
                     String admissionDate = wordScan.next().trim();
                     String dischargeDate = wordScan.next().trim();
                     Patient p;

                     //Check if the patient is already in the system and assign/get patientID
                     boolean check = ptMap.containsKey(lastName);

                     //If the patient is not in the system, add them to Patient table
                     if (!check) {
                        int tempPtId = addPtMap(lastName);
                        p = new Patient(tempPtId, firstName, lastName, docLastName);
                        addPatient(p);
                     }
                     //If the patient is already in the Patient table, add subsequent visits
                     if (roomNo > 0) {
                        //Add to ALL inPatient list
                           //grab existing patient ID
                        int getPtId = ptMap.get(lastName);
                        p = new Patient(inPatientNo, getPtId, firstName, lastName, roomNo, emergContact,
                                emergNo, insPolicy, insPolicyNo, docLastName, iniDiagnosis, admissionDate,
                                dischargeDate);
                        addInPatient(p);
                        //If the patient is STILL and inPatient add to Current InPatient table
                        if (dischargeDate.equals("")) {
                           addCurrentInPatient(p);
                        }
                     }
                     //If not inPatient then add to OutPatient table
                     else {
                        int getPtId = ptMap.get(lastName);
                        p = new Patient(getPtId, docLastName, iniDiagnosis);
                        addOutPatient(p);
                     }
                     inPatientNo++;

                     //Add diagnosis - this can be optimized
                     boolean checkDiag = diagMap.containsKey(iniDiagnosis);
                     if (!checkDiag) {
                        Diagnosis d = new Diagnosis(iniDiagnosis, addDiagToMap(iniDiagnosis));
                        addDiag(d);
                     }
                     continue;

                  case 'D': 
                  case 'A': 
                  case 'V': 
                  case 'N': 
                  case 'T': 
                     Employee et = new Employee(firstName, lastName, personType);
                     addEmployee(et);                         
                     break;
                  default :
                     break;
               }
            
            }
            catch (Exception e) {
               System.out.print("Error - Program ending." + e);
               e.printStackTrace();
               return;
            }
         }
         //Person database operations
         personDatabaseOperations(database);
      }
      //Treatment file inserted
      else {
         while (scanFile.hasNext()) {
            String line1 = scanFile.nextLine().trim();
            try {
               Scanner wordScan = new Scanner(line1).useDelimiter(",");
               String treatFirstName = wordScan.next().trim();
               String treatLastName = wordScan.next().trim();
               Character treatType = wordScan.next().toUpperCase().trim().charAt(0);
               String treat = wordScan.next().trim();
               String treatDate = wordScan.next().trim();
               Treatment t;

               //If the treatment is new add to TreatmentList table - has treatment ID and name
               //Create new treatment object for only treatments without persons
               boolean checkTreat = treatMap.containsKey(treat);
               if (!checkTreat) {
                  addTreatMap(treat);
               }

               //Then add the treatment to the Treatment table with assigned patient, doctor, etc
               int getTreatId = treatMap.get(treat);
               t = new Treatment(getTreatId, treatFirstName, treatLastName, treatType,
                       treat, treatDate);
               addTreatment(t);
            }
            catch (Exception e) {
               System.out.print("Error - Program ending.");
               return;
            } 
         }
         //Treatment database operations
         treatmentDatabaseOperations(database);
      }
   }

   public void connectToDatabase(DatabaseSQL databaseIn) {
      databaseIn.createNewDatabase("database.sl3");
      databaseIn.connect();
   }

   public void personDatabaseOperations(DatabaseSQL databaseIn) {
      databaseIn.dropAllTables();
      databaseIn.createAllTables();

      for (int i = 0; i < employeeList.length; i++) {
         databaseIn.insertEmployee(employeeList[i]);
      }

      //insert all patients from patient array
      for (int i = 0; i < patientList.length; i++) {
         databaseIn.insertPatient(patientList[i]);
      }

      //insert all in-patients from patient array
      for (int i = 0; i < inPatientList.length; i++) {
         databaseIn.insertInPatient(inPatientList[i]);
      }

      //insert all CURRENT in-patients from patient array
      for (int i = 0; i < currentInPatientList.length; i++) {
         databaseIn.insertCurrentInPatient(currentInPatientList[i]);
         databaseIn.updateRoom(currentInPatientList[i]);
      }

      //insert all out-patients from patient array
      for (int i = 0; i < outPatientList.length; i++) {
         databaseIn.insertOutPatient(outPatientList[i]);
      }
      //insert all diagnosis from diagnosis array
      for (int i = 0; i < diagList.length; i++) {
         databaseIn.insertDiag(diagList[i]);
      }
      System.out.println("Person text file inserted into database.");
   }

   public void treatmentDatabaseOperations(DatabaseSQL databaseIn) {
      databaseIn.dropTreatment();
      databaseIn.createTreatmentTable();

      //insert all treatments from treatment array
      for (int i = 0; i < treatmentList.length; i++) {
         databaseIn.insertTreatment(treatmentList[i]);
      }
      System.out.println("Treatment text file inserted into database.");

   }
   
   public void addEmployee(Employee employeeIn) {
      employeeList = Arrays.copyOf(employeeList, employeeList.length + 1);
      employeeList[employeeList.length - 1] = employeeIn;
   }
   
   public void addPatient(Patient patientIn) {
      patientList = Arrays.copyOf(patientList, patientList.length + 1);
      patientList[patientList.length - 1] = patientIn;
   }

   public void addInPatient(Patient patientIn) {
      inPatientList = Arrays.copyOf(inPatientList, inPatientList.length + 1);
      inPatientList[inPatientList.length - 1] = patientIn;
   }

   public void addOutPatient(Patient patientIn) {
      outPatientList = Arrays.copyOf(outPatientList, outPatientList.length + 1);
      outPatientList[outPatientList.length - 1] = patientIn;
   }

   public void addCurrentInPatient(Patient patientIn) {
      currentInPatientList = Arrays.copyOf(currentInPatientList, currentInPatientList.length + 1);
      currentInPatientList[currentInPatientList.length - 1] = patientIn;
   }
   
   public void addTreatment(Treatment treatmentIn) {
      treatmentList = Arrays.copyOf(treatmentList, treatmentList.length + 1);
      treatmentList[treatmentList.length - 1] = treatmentIn;
   }
   
   public void addDiag(Diagnosis diagIn) {
      diagList = Arrays.copyOf(diagList, diagList.length + 1);
      diagList[diagList.length - 1] = diagIn;
   }
        
   public static int addTreatToMap(String treat) {
      boolean flag = treatMap.containsKey(treat);
      if (!flag) {
         treatMap.put(treat, treatmentID);
         int currTreatID = treatmentID;
         treatmentID++;
         return currTreatID;
      }
      return treatMap.get(treat);
   }

   public static int addTreatMap(String treatmentName) {
      treatMap.put(treatmentName, treatmentID);
      int temp = treatmentID;
      treatmentID++;
      return temp;
   }
   
   public static int addDiagToMap(String iniDiagnosis) {
      boolean flag = diagMap.containsKey(iniDiagnosis);
      if (!flag) {
         diagMap.put(iniDiagnosis, diagID);
         int currDiagID = diagID;
         diagID++;
         return currDiagID;
      }
      return diagMap.get(iniDiagnosis);
   }

   public static int addPtMap(String lastName) {
      ptMap.put(lastName, patientID);
      int temp = patientID;
      patientID++;
      return temp;
   }

   
   public String toString() {
      String stringResult = "";
      for (Patient p : patientList) {
         stringResult += "\n" + p + "\n";
      }
      return stringResult;
   }
   
}