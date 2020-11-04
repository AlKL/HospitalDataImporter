public class Treatment {

   String firstName;
   String lastName;
   Character type;
   String treatment;
   String treatmentDate;

   public Treatment(String firstNameIn, String lastNameIn, Character typeIn,
                     String treatmentIn, String treatmentDateIn) {
      firstName = firstNameIn;
      lastName = lastNameIn;
      type = typeIn;
      treatment = treatmentIn;
      treatmentDate = treatmentDateIn;
   }
   
   public String toString() {
      return lastName + "," + firstName;
   }

}