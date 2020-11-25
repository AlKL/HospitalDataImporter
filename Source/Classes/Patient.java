package Classes;

/**
 * Creates all related Patient objects for various Patient tables
 */
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
   protected int inPatientNo;

   /**
    * Creates a universal patient (all patients)
    * @param patientIDIn patient ID
    * @param firstNameIn first name
    * @param lastNameIn last name
    * @param docLastNameIn doctor's last name
    */
   public Patient(int patientIDIn, String firstNameIn, String lastNameIn, String docLastNameIn) {
      patientID = patientIDIn;
      firstName = firstNameIn;
      lastName = lastNameIn;
      docLastName = docLastNameIn;
   }

   /**
    * Creates an out-patient (patient was not admitted)
    * @param patientIDIn patient ID
    * @param docLastNameIn doctor's last name
    * @param iniDiagnosisIn initial diagnosis
    */
   public Patient(int patientIDIn, String docLastNameIn, String iniDiagnosisIn) {
      patientID = patientIDIn;
      docLastName = docLastNameIn;
      iniDiagnosis = iniDiagnosisIn;
   }

   /**
    * Creates a patient who has been admitted or is currently admitted (all in-patient)
    * @param inPatientNoIn inPatient number
    * @param patientIDIn patient ID
    * @param firstNameIn first name
    * @param lastNameIn last name
    * @param roomNoIn room number
    * @param emergContactIn emergency contact
    * @param emergNoIn emergency number
    * @param insPolicyIn insurance policy company
    * @param insPolicyNoIn insurance policy number
    * @param docLastNameIn doctor's last name
    * @param iniDiagnosisIn initial diagnosis
    * @param admissionDateIn admission date
    * @param dischargeDateIn discharge date
    */
   public Patient(int inPatientNoIn, int patientIDIn, String firstNameIn, String lastNameIn,
               int roomNoIn, String emergContactIn, String emergNoIn,
               String insPolicyIn, String insPolicyNoIn, String docLastNameIn,
               String iniDiagnosisIn, String admissionDateIn, String dischargeDateIn) {
      inPatientNo = inPatientNoIn;
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

   /** getters */
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
   public int getInPatientNo() { return inPatientNo; }

}