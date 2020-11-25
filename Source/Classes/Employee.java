package Classes;

/**
 * Creates an Employee object for the employee table
 */
public class Employee {
   protected String firstName;
   protected String lastName;
   protected Character jobCat;

   /**
    *
    * @param firstNameIn employee first name
    * @param lastNameIn employee last name
    * @param jobCatIn employee job type
    */
   public Employee(String firstNameIn, String lastNameIn, Character jobCatIn) {
      firstName = firstNameIn;
      lastName = lastNameIn;
      jobCat = jobCatIn;
   }

   /** getters */
   public Character getJobCat() { return jobCat; }
   public String getFirstName() { return firstName; }
   public String getLastName() { return lastName; }

}