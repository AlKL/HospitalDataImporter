package Classes;

public class Employee {
   protected String firstName;
   protected String lastName;
   protected Character jobCat;
   
   public Employee(String firstNameIn, String lastNameIn, Character jobCatIn) {
      firstName = firstNameIn;
      lastName = lastNameIn;
      jobCat = jobCatIn;
   }

   public Character getJobCat() {
      return jobCat;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public String toString() {
      return "temp";
   }
}