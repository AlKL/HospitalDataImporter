import java.io.FileNotFoundException;
import JavaQueries.DatabaseSQL;
import JavaQueries.Queries;
import java.util.Scanner;

public class Menu {
   public static void main(String[] args) throws FileNotFoundException {
   
      //       RECEIVE TEXT FILE
      //end program if no file provided
      if (args.length == 0) {
         System.out.println("***File name not provided"
            + " by command line argument.");
         System.out.print("Program ending.");
         return;
      }

      //       IMPORT DATA FROM TEXT FILE INTO ARRAY
      //Parameters must be absolute path
      DataImporter hospitalData = new DataImporter();
      hospitalData.readHospitalFile(args[0]);

      //SQL Queries Menu
      System.out.println("Please enter the query number to execute that query:");
      System.out.println();

      //Connect query to database
      Queries query = new Queries();
      query.connect();

      //QUERY MENU
      //1.1
      query.roomUtilization();

      //1.2
      query.roomOccupation();

      //1.3
      query.allRoomsOccupation();

      //2.1
      query.allPatients();

      //2.2
      query.allCurrentPatients();

      //2.3
      String dischargeStartDate = "2020-02-01";
      String dischargeEndDate = "2020-04-01";
      query.patientsWithinDischargeRange(dischargeStartDate, dischargeEndDate);

      //2.4
      String admissionStartDate = "2021-01-01";
      String admissionEndDate = "2021-02-01";
      query.patientsWithinAdmitRange(admissionStartDate, admissionEndDate);

      //2.5
      String givenPatientLastName = "Tucker";
      query.givenPatient(givenPatientLastName);

      //2.6
      String givenPatientLastName2 = "Westbrook";
      query.givenPatientTreatment(givenPatientLastName2);

      //2.7
      query.dischargeWithinThirty();

      //2.8
      query.patientHistory();

      //3.1
      query.inPatientDiagnosis();

      //3.2
      query.allPatientDiagnosis();

      //3.3
      query.listTreatments();

      //3.4
      query.listAdmittedTreatments();

      //3.5
      query.topMedications();

      //3.6
      query.topProcedure();

      //3.7
      query.mostRecentProcedure();

      //3.8
      query.topFiveDiagnosis();

      //4.1
      query.listEmployees();

      //4.2
      query.listFrequentDoctors();

      //4.3
      String doctorName = "Nurse";
      query.listDoctorDiagnosis(doctorName);

      //4.4
      String doctorName2 = "Lowry";
      query.listDoctorTreatment(doctorName2);

      //4.5
      query.listDoctorsAllInPatient();

   }
}