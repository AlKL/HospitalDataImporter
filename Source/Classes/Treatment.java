public class Treatment {

   String ptLastName;
   String docLastName;
   Character treatType;
   String treatment;
   String treatmentDate;

   public Treatment(String ptNameIn, String docNameIn, Character typeIn,
                     String treatmentIn, String treatmentDateIn) {
      ptLastName = ptNameIn;
      docLastName = docNameIn;
      treatType = typeIn;
      treatment = treatmentIn;
      treatmentDate = treatmentDateIn;
   }
   
   public String toString() {
      return "-";
   }

}