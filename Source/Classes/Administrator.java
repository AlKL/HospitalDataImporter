
public class Administrator extends Person {

   public Administrator (String firstNameIn, String lastNameIn, Character personTypeIn) {
      super(firstNameIn, lastNameIn, personTypeIn);
   }   

   public String toString() {
      return "Administrator: " + super.getFirstName();
   }
}