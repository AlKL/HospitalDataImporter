package Classes;

public class Diagnosis {
   protected int diagID;
   protected String diagName;
   protected int patientID;
   
   /** Constructor - takes diagnosis name and assigns diagnosis ID **/
   public Diagnosis(String diagNameIn, int diagIDIn) {
      diagName = diagNameIn;
      diagID = diagIDIn;
      //patientID = ptIDIn;
   }

   public int getDiagID() { return diagID; }
   public String getDiagName() { return diagName; }
   public int getPatientID() { return patientID; }

   
   /** 
   Check to see if diagnosis is already in tree, if not then
   **/
   // public static int addDiagToMap(String diagNameIn) {
   //    
      // boolean flag = diagMap.containsKey(diagNameIn);
      // if (flag == false) {
         // diagMap.put(diagNameIn, diagID);
         // int currDiagID = diagID;
         // diagID++;
         // return currDiagID;
      // }
      // return diagMap.get(diagNameIn);
   // }
}


/**
So a diagnosis is created and entered.
1 - check to see if the diagnosis is in the hashmap 
2 - if it is then return a Diagnosis object with (diagID, diagnosis)
3 - if not, then create a new hashmap entry with the new (diagID, diagnosis)
4 - then return this new one
**/