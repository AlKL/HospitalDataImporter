public class Treatment {

   int treatmentID;
   String ptLastName;
   String docLastName;
   Character treatType;
   String treatment;
   String treatmentDate;

   public Treatment(int treatmentIDIn, String ptNameIn, String docNameIn, Character typeIn,
                     String treatmentIn, String treatmentDateIn) {
      treatmentID = treatmentIDIn;
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