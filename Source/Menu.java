import java.io.FileNotFoundException;

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

   }
}