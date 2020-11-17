package Classes;

public class Technician extends Person {

   public Technician (String firstNameIn, String lastNameIn, Character personTypeIn) {
      super(firstNameIn, lastNameIn, personTypeIn);
   }   

   public String toString() {
      return "Technician: " + super.getFirstName();
   }

}