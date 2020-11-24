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

      //Connect to query
      Queries query = new Queries();
      query.connect();

      //1.1
      //query.roomUtilization();

      //1.2
      //query.roomOccupation();

      //1.3
      //query.allRoomsOccupation();

      //2.1
      //query.allPatients();

      //2.2
      //query.allCurrentPatients();

      //2.3
//      String startDate = "2020-02-01";
//      String lastDate = "2020-04-01";
//      query.patientsWithinDischargeRange(startDate, lastDate);

      //2.4
   }
}