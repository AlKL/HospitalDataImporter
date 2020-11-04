public class Volunteer extends Person {

   public Volunteer (String firstNameIn, String lastNameIn, Character personTypeIn) {
      super(firstNameIn, lastNameIn, personTypeIn);
   }   

   public String toString() {
      return "Volunteer: " + super.getFirstName();
   }

}