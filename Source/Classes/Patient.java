package Classes;

public class Patient {

   protected int patientID;
   protected String firstName;
   protected String lastName;
   protected int roomNo;
   protected String emergContact;
   protected String emergNo;
   protected String insPolicy;
   protected String insPolicyNo;
   protected String docLastName;
   protected String iniDiagnosis;
   protected String admissionDate;
   protected String dischargeDate;

   //constructor - all patients
   public Patient(int patientIDIn, String firstNameIn, String lastNameIn, String docLastNameIn,
               String iniDiagnosisIn) {
      patientID = patientIDIn;
      firstName = firstNameIn;
      lastName = lastNameIn;
      docLastName = docLastNameIn;
      iniDiagnosis = iniDiagnosisIn;
   }

   //constructor for in-patient
   public Patient(int patientIDIn, String firstNameIn, String lastNameIn,
               int roomNoIn, String emergContactIn, String emergNoIn,
               String insPolicyIn, String insPolicyNoIn, String docLastNameIn,
               String iniDiagnosisIn, String admissionDateIn, String dischargeDateIn) {
      patientID = patientIDIn;
      firstName = firstNameIn;
      lastName = lastNameIn;
      roomNo = roomNoIn;
      emergContact = emergContactIn;
      emergNo = emergNoIn;
      insPolicy = insPolicyIn;
      insPolicyNo = insPolicyNoIn;
      docLastName = docLastNameIn;
      iniDiagnosis = iniDiagnosisIn;
      admissionDate = admissionDateIn;
      dischargeDate = dischargeDateIn;
   }

   public int getPatientID() { return patientID; }
   public String getFirstName() { return firstName; }
   public String getLastName() { return lastName; }
   public int getRoomNo() { return roomNo; }
   public String getEmergContact() { return emergContact; }
   public String getEmergNo() { return emergNo; }
   public String getInsPolicy() { return insPolicy; }
   public String getInsPolicyNo() { return insPolicyNo; }
   public String getDocLastName() { return docLastName; }
   public String getIniDiagnosis() { return iniDiagnosis; }
   public String getAdmissionDate() { return admissionDate; }
   public String getDischargeDate() { return dischargeDate; }

   public String toString() {
      return "temp";
   }
}