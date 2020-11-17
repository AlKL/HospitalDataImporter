package Classes;

public class Treatment {

   protected int treatmentID;
   protected String ptLastName;
   protected String docLastName;
   protected Character treatType;
   protected String treatment;
   protected String treatmentDate;

   public Treatment(int treatmentIDIn, String ptNameIn, String docNameIn, Character typeIn,
                     String treatmentIn, String treatmentDateIn) {
      treatmentID = treatmentIDIn;
      ptLastName = ptNameIn;
      docLastName = docNameIn;
      treatType = typeIn;
      treatment = treatmentIn;
      treatmentDate = treatmentDateIn;
   }

   public int getTreatmentID() { return treatmentID; }
   public String getPtLastName() { return ptLastName; }
   public String getDocLastName() { return docLastName; }
   public Character getTreatType() { return treatType; }
   public String getTreatment() { return treatment; }
   public String getTreatmentDate() { return treatmentDate; }

   public String toString() {
      return "-";
   }

}