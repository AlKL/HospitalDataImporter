public class Employee extends Person {

   public Employee (String firstNameIn, String lastNameIn, Character personTypeIn) {
      super(firstNameIn, lastNameIn, personTypeIn);
   }   

   public String toString() {
      return "Employee: " + super.getFirstName();
   }
}