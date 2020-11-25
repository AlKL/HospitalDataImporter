package Classes;

/**
 * Creates a Diagnosis object for the diagnosis table
 */
public class Diagnosis {
   protected int diagID;
   protected String diagName;

   /**
    *
    * @param diagNameIn diagnosis name
    * @param diagIDIn diagnosis ID number
    **/
   public Diagnosis(String diagNameIn, int diagIDIn) {
      diagName = diagNameIn;
      diagID = diagIDIn;
   }

   /** getters */
   public int getDiagID() { return diagID; }
   public String getDiagName() { return diagName; }

}