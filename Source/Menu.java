import java.util.Scanner;
import java.io.FileNotFoundException;

public class Menu {

   public static void main(String[] args) {
   
      //       RECEIVE TEXT FILE
      //pass absolute path into parameters
      //end program if no file provided
      if (args.length == 0) {
         System.out.println("***File name not provided"
            + " by command line argument.");
         System.out.print("Program ending.");
         return;
      }
   
      try {
      //       IMPORT DATA FROM TEXT FILE INTO ARRAY
      //enter absolute path into parameters
         DataImporter hospitalData = new DataImporter();
         hospitalData.readHospitalFile(args[0]);
      
      //       INSERT PERSONS ARRAY INTO DATABASE
      //create database connection
         DatabaseSQL app = new DatabaseSQL();
         app.connect();
         
         
      //Check whether file is PersonList or TreatmentList
         Scanner userInput = new Scanner(System.in);
         
         System.out.print("File input Type: \n" 
                           + "P - Person(s) List\n"
                           + "T - Treatment(s) List\n"
                           + "Q - Quit\n\n");            
         String code;
         do {
            System.out.print("Enter Code [P, T, Q]: ");
            
            code = userInput.nextLine();
            if (code.length() == 0) {
               continue;
            }               
            
            code = code.toUpperCase();
            char choice = code.charAt(0);
            
            switch(choice) {
               case 'P':
                  app.dropAllTables();
                  app.createAllTables();
                           
               //insert all employeess from employee array
                  for (int i = 0; i < hospitalData.employeeList.length; i++) {
                     app.insertEmployee(hospitalData.employeeList[i]);
                  }
               
               //insert all patients from patient array
                  for (int i = 0; i < hospitalData.patientList.length; i++) {
                     app.insertPatient(hospitalData.patientList[i]);
                  }
                     
               //insert all in-patients from patient array
                  for (int i = 0; i < hospitalData.inPatientList.length; i++) {
                     app.insertInPatient(hospitalData.inPatientList[i]);
                  }
                  
                  System.out.println("Person text file inserted into database.");
               
                  break;
               case 'T':
                  app.dropTreatment();
                  app.createTreatmentTable();
                  
               //insert all treatments from treatment array
                  for (int i = 0; i < hospitalData.treatmentList.length; i++) {
                     app.insertTreatment(hospitalData.treatmentList[i]);
                  }
                  
                  System.out.println("Treatment text file inserted into database.");
                  
                  break;
                  
               case 'Q':
                  break;
               default:
                  System.out.println("\t*** invalid entry ***\n");  
            }
         } while (!code.equalsIgnoreCase("Q"));   
      
      
                  
      }
      //catch and trace exceptions
      catch (Exception e) {
         System.out.println(e + "*** Exception thrown");
         e.printStackTrace();
         System.out.print("Program ending.");
         return;
      }
   }
   
}