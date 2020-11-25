package Classes;

/**
 * Creates a Treatment object for the treatment table
 */
public class Treatment {
   protected int treatmentID;
   protected String ptLastName;
   protected String docLastName;
   protected Character treatType;
   protected String treatment;
   protected String treatmentDate;

   /**
    *
    * @param treatmentIn treatment name
    * @param treatmentIDIn treatment ID
    */
   public Treatment(String treatmentIn, int treatmentIDIn) {
      treatmentID = treatmentIDIn;
      treatment = treatmentIn;
   }

   /**
    *
    * @param treatmentIDIn treatmentID
    * @param ptNameIn patient name
    * @param docNameIn doctor's name
    * @param typeIn treatment type
    * @param treatmentIn treatment name
    * @param treatmentDateIn treatment date
    */
   public Treatment(int treatmentIDIn, String ptNameIn, String docNameIn, Character typeIn,
                     String treatmentIn, String treatmentDateIn) {
      treatmentID = treatmentIDIn;
      ptLastName = ptNameIn;
      docLastName = docNameIn;
      treatType = typeIn;
      treatment = treatmentIn;
      treatmentDate = treatmentDateIn;
   }

   /** getters */
   public int getTreatmentID() { return treatmentID; }
   public String getPtLastName() { return ptLastName; }
   public String getDocLastName() { return docLastName; }
   public Character getTreatType() { return treatType; }
   public String getTreatment() { return treatment; }
   public String getTreatmentDate() { return treatmentDate; }

}