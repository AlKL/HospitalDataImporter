public class Doctor extends Person {

   public Doctor (String firstNameIn, String lastNameIn, Character personTypeIn) {
      super(firstNameIn, lastNameIn, personTypeIn);
   }   

   public String toString() {
      return "Doctor: " + super.getFirstName();
   }

}