import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.HashMap;
import Classes.*;


public class DataImporter {

   //need to make proper methods for these so that they can be private 
   public Patient[] inPatientList;
   public Employee[] employeeList;
   
   public static int patientID;
   public Patient[] patientList;
   
   public static int treatmentID;
   public static HashMap<String, Integer> treatMap;
   public Treatment[] treatmentList;
   
   public static int diagID;
   public static HashMap<String, Integer> diagMap;
   public Diagnosis[] diagList;
   
   //constructor
   public DataImporter() {
      patientList = new Patient[0];
      patientID = 1001;         
      inPatientList = new Patient[0];
      
      treatmentList = new Treatment[0];
      treatmentID = 70001;
      treatMap = new HashMap<String, Integer>();
      
      employeeList = new Employee[0];
      
      diagID = 50001;
      diagList = new Diagnosis[0];
      diagMap = new HashMap<String, Integer>();
   }
   
   public void readHospitalFile(String fileName) 
                                       throws FileNotFoundException {  
      Scanner scanFile = new Scanner(new File(fileName));
      Scanner scanChecker = new Scanner(new File(fileName));
   
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
                  case 'P': 
                     int roomNo = wordScan.nextInt();
                     String emergContact = wordScan.next().trim();
                     String emergNo = wordScan.next().trim();
                     String insPolicy = wordScan.next().trim();
                     String insPolicyNo = wordScan.next().trim();
                     String docLastName = wordScan.next().trim();
                     String iniDiagnosis = wordScan.next().trim();
                     String admissionDate = wordScan.next().trim();
                     String dischargeDate = wordScan.next().trim();
                  
                     int diagIDCheck = addDiagToMap(iniDiagnosis);
                     Diagnosis d = new Diagnosis(iniDiagnosis, diagIDCheck, patientID);
                     addDiag(d); 
                     if (roomNo > 0) {
                     
                        Patient p = new Patient(patientID, firstName, lastName, roomNo,
                                    emergContact, emergNo,insPolicy, insPolicyNo, docLastName,
                                    iniDiagnosis, admissionDate, dischargeDate);
                        Patient np = new Patient(patientID, firstName, lastName, docLastName, iniDiagnosis);
                        addInPatient(p);
                        addPatient(np);
                        patientID++;
                        continue;
                     }
                     else {
                        Patient p = new Patient(patientID, firstName, lastName, docLastName, iniDiagnosis);
                        addPatient(p);
                        patientID++;
                     
                        continue;
                     }
                  
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
      }
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
               
               
               int treatIDCheck = addTreatToMap(treat);
               Treatment t = new Treatment(treatIDCheck, treatFirstName, treatLastName, treatType,
                                             treat, treatDate);
               addTreatment(t);
               continue;
            }
            catch (Exception e) {
               System.out.print("Error - Program ending.");
               return;
            } 
         }
      }
   
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
      if (flag == false) {
         treatMap.put(treat, treatmentID);
         int currTreatID = treatmentID;
         treatmentID++;
         return currTreatID;
      }
      return treatMap.get(treat);
   } 
   
   public static int addDiagToMap(String iniDiagnosis) {
      boolean flag = diagMap.containsKey(iniDiagnosis);
      if (flag == false) {
         diagMap.put(iniDiagnosis, diagID);
         int currDiagID = diagID;
         diagID++;
         return currDiagID;
      }
      return diagMap.get(iniDiagnosis);
   }
   
   public String toString() {
      String stringResult = "";
      for (Patient p : patientList) {
         stringResult += "\n" + p + "\n";
      }
      return stringResult;
   }
   
}