public abstract class Person implements Comparable<Person> {
   protected int patientID;
   protected String type;
   protected String firstName;
   protected String lastName;
   protected int roomNumber;
   protected String emergencyContact;
   protected String emergencyNumber;
   protected String insPolicy;
   protected String insPolicyNo;
   protected String primaryDoctorLastName;
   protected String iniDiagnosis;
   protected String admissionDate;
   protected String dischargeDate;
   protected Character jobCategory;
   
   //creates employee
   public Person(String firstNameIn, String lastNameIn, Character jobCategoryIn) {
      firstName = firstNameIn;
      lastName = lastNameIn;
      roomNumber = 0;
      jobCategory = jobCategoryIn;
   }
   
   //creates patient
   public Person(int patientIDIn, String firstNameIn, String lastNameIn,
                  int roomNumberIn, String emergencyContactIn,
                  String emergencyNumberIn, String insPolicyIn,
                  String insPolicyNoIn, String primaryDoctorLastNameIn,
                  String iniDiagnosisIn, String admissionDateIn,
                  String dischargeDateIn, Character jobCategoryIn) {
      patientID = patientIDIn;
      firstName = firstNameIn;
      lastName = lastNameIn;
      roomNumber = roomNumberIn;
      emergencyContact = emergencyContactIn;
      emergencyNumber = emergencyNumberIn;
      insPolicy = insPolicyIn;
      insPolicyNo = insPolicyNoIn;
      primaryDoctorLastName = primaryDoctorLastNameIn;
      iniDiagnosis = iniDiagnosisIn;
      admissionDate = admissionDateIn;
      dischargeDate = dischargeDateIn;
      jobCategory = jobCategoryIn;
   }
         
   public String getFirstName() {
      return firstName;
   }      
   
   public String getLastName() {
      return lastName;
   }
   
   public String toString() {
      return firstName + "," + lastName;
   }
   
   public boolean equals(Object obj) {
      if (!(obj instanceof Person)) {
         return false;
      }
      else {
         Person other = (Person) obj;
         return (firstName + lastName).
            equals(other.firstName + other.lastName);
      }
   }
   
   public int compareTo(Person other) {
      return getLastName().toLowerCase()
          .compareTo(other.getLastName().toLowerCase());
   }
   
      /** @return 0 */
   public int hashCode() {
      return 0;
   }
}