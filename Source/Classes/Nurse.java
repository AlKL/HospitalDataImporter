public class Nurse extends Person {

   public Nurse (String firstNameIn, String lastNameIn, Character personTypeIn) {
      super(firstNameIn, lastNameIn, personTypeIn);
   }   

   public String toString() {
      return "Nurse: " + super.getFirstName();
   }

}