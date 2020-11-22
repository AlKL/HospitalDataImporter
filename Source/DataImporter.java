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
   public int inPatientNo;
   public Employee[] employeeList;

   public Patient[] currentInPatientList;
   public Patient[] outPatientList;

   public static HashMap<String, Integer> ptMap;
   public static int patientID;
   public Patient[] patientList;

   public static HashMap<String, Integer> treatList;
   public Treatment[] allTreatmentList;

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
      currentInPatientList = new Patient[0];
      inPatientNo = 300;
      ptMap = new HashMap<>();
      outPatientList = new Patient[0];

      allTreatmentList = new Treatment[0];
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
                  t = new Treatment(treat, addTreatMap(treat));
                  addAllTreatmentList(t);
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

   public void addAllTreatmentList(Treatment treatmentIn) {
      allTreatmentList = Arrays.copyOf(allTreatmentList, allTreatmentList.length + 1);
      allTreatmentList[allTreatmentList.length - 1] = treatmentIn;
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