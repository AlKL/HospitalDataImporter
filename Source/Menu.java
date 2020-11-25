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

      //Connect query to database
      Queries query = new Queries();
      query.connect();

      //SQL Queries Menu
      Scanner userInput = new Scanner(System.in);
      String choice;
      System.out.println("Please enter the query number to execute that query:");
      System.out.println("1.1\n1.2\n1.3\n2.1\n2.2\n2.3\n2.4\n2.5\n2.6\n2.7\n2.8\n"
              + "3.1\n3.2\n3.3\n3.4\n3.5\n3.6\n3.7\n3.8\n4.1\n4.2\n4.3\n4.4\n4.5\n");
      System.out.print("Which query would you like to execute? ");
      do {
         choice = userInput.nextLine();
         switch(choice) {
            case "1.1":
               query.roomUtilization();
               break;
            case "1.2":
               query.roomOccupation();
               break;
            case "1.3":
               query.allRoomsOccupation();
               break;
            case "2.1":
               query.allPatients();
               break;
            case "2.2":
               query.allCurrentPatients();
               break;
            case "2.3":
               System.out.print("Enter beginning date [yyyy-mm-dd]: ");
               String dischargeStartDate = userInput.nextLine();
               System.out.print("Enter end date [yyyy-mm-dd]: ");
               String dischargeEndDate = userInput.nextLine();
               query.patientsWithinDischargeRange(dischargeStartDate, dischargeEndDate);
               break;
            case "2.4":
               System.out.print("Enter beginning date [yyyy-mm-dd]: ");
               String admissionStartDate = userInput.nextLine();
               System.out.print("Enter end date [yyyy-mm-dd]: ");
               String admissionEndDate = userInput.nextLine();
               query.patientsWithinAdmitRange(admissionStartDate, admissionEndDate);
               break;
            case "2.5":
               System.out.print("Enter patient last name: ");
               String givenPatientLastName = userInput.nextLine();
               query.givenPatient(givenPatientLastName);
               break;
            case "2.6":
               System.out.print("Enter patient last name: ");
               String givenPatientLastName2 = userInput.nextLine();
               query.givenPatientTreatment(givenPatientLastName2);
               break;
            case "2.7":
               query.dischargeWithinThirty();
               break;
            case "2.8":
               query.patientHistory();
               break;
            case "3.1":
               query.inPatientDiagnosis();
               break;
            case "3.2":
               query.allPatientDiagnosis();
               break;
            case "3.3":
               query.listTreatments();
               break;
            case "3.4":
               query.listAdmittedTreatments();
               break;
            case "3.5":
               query.topMedications();
               break;
            case "3.6":
               query.topProcedure();
               break;
            case "3.7":
               query.mostRecentProcedure();
               break;
            case "3.8":
               query.topFiveDiagnosis();
               break;
            case "4.1":
               query.listEmployees();
               break;
            case "4.2":
               query.listFrequentDoctors();
               break;
            case "4.3":
               System.out.print("Enter doctor last name: ");
               String doctorName = userInput.nextLine();
               query.listDoctorDiagnosis(doctorName);
            case "4.4":
               System.out.print("Enter doctor last name: ");
               String doctorName2 = userInput.nextLine();
               query.listDoctorTreatment(doctorName2);
            case "4.5":
               query.listDoctorsAllInPatient();
               break;
            default:
               System.out.print("Invalid entry. ");
         }
         do {
            System.out.print("Would you like to enter a new query? (Y/Q): ");
            choice = userInput.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
               System.out.print("Please enter the new query number: ");
            }
            if (choice.equalsIgnoreCase("Q")) {
               System.out.println("Thank you, quitting.");
            }
            if ((!(choice.equalsIgnoreCase("Y"))) && (!(choice.equalsIgnoreCase("Q")))) {
               System.out.print("Invalid entry - please try again or quit. ");
            }
         } while ((!(choice.equalsIgnoreCase("Y"))) && (!(choice.equalsIgnoreCase("Q"))));
      } while (!(choice.equalsIgnoreCase("Q")));
   }
}