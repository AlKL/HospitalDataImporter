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
      //test.toString();
      
      //       INSERT PERSONS ARRAY INTO DATABASE
      //create database connection
         DatabaseSQL app = new DatabaseSQL();
         app.connect(); 
      
      //drop all tables
         app.dropTables();
      
      //create all tables if they do not exist
         app.createTables();
         
      //insert all employees's from employee array
         for (int i = 0; i < hospitalData.employeeList.length; i++) {
            app.insertEmployee(hospitalData.employeeList[i]);
         }
                     
      //insert all person's from person array
         for (int i = 0; i < hospitalData.patientList.length; i++) {
            app.insertPatient(hospitalData.patientList[i]);
         }
         
      //insert all treatments from treatment array
         for (int i = 0; i < hospitalData.treatmentList.length; i++) {
            app.insertTreatment(hospitalData.treatmentList[i]);
         }
         
      }
      catch (Exception e) {
         System.out.println(e + "*** Exception thrown");
         e.printStackTrace();
         System.out.print("Program ending.");
         return;
      }
   }
   
}