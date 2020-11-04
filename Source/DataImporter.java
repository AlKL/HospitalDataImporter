import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.NoSuchElementException;

public class DataImporter {

   //need to make proper methods for these so that they can be private 
   public Person[] personList;
   public Treatment[] treatmentList;
   public Employee[] employeeList;
   
   //constructor
   public DataImporter() {
      personList = new Person[0];
      treatmentList = new Treatment[0];
      employeeList = new Employee[0];
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
                     int roomNumber = wordScan.nextInt();
                     String emergencyContact = wordScan.next().trim();
                     String emergencyNumber = wordScan.next().trim();
                     String insPolicy = wordScan.next().trim();
                     String insPolicyNo = wordScan.next().trim();
                     String primaryDoctorLastName = wordScan.next().trim();
                     String iniDiagnosis = wordScan.next().trim();
                     String admissionDate = wordScan.next().trim();
                     String dischargeDate = wordScan.next().trim();
                  
                     Patient p = new Patient(firstName, lastName, roomNumber,
                                    emergencyContact, emergencyNumber,
                                    insPolicy, insPolicyNo, primaryDoctorLastName,
                                    iniDiagnosis, admissionDate, dischargeDate, personType);
                     addPerson(p);
                     break;
                  case 'D': 
                     Doctor d = new Doctor(firstName, lastName, personType);
                     addPerson(d);
                     Employee e = new Employee(firstName, lastName, personType);
                     addEmployee(e);
                     break;
                  case 'A': 
                     Administrator a = new Administrator(firstName, lastName, personType);
                     addPerson(a);
                     break;
                  case 'V': 
                     Volunteer v = new Volunteer(firstName, lastName, personType);
                     addPerson(v);
                     break;
                  case 'N': 
                     Nurse n = new Nurse(firstName, lastName, personType);
                     addPerson(n);
                     break;
                  case 'T': 
                     Technician t = new Technician(firstName, lastName, personType);
                     addPerson(t);
                     break;
                  default :
                     break;
               }
            
            }
            catch (Exception e) {
               System.out.print("Error - Program ending." + e);
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
               Treatment t = new Treatment(treatFirstName, treatLastName, treatType,
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
   
   public void addPerson(Person personIn) {
      personList = Arrays.copyOf(personList, personList.length + 1);
      personList[personList.length - 1] = personIn;
   }
   
   public void addTreatment(Treatment treatmentIn) {
      treatmentList = Arrays.copyOf(treatmentList, treatmentList.length + 1);
      treatmentList[treatmentList.length - 1] = treatmentIn;
   }
   
   public String toString() {
      String stringResult = "";
      for (Person p : personList) {
         stringResult += "\n" + p + "\n";
      }
      return stringResult;
   }
   
}