public class Patient extends Person {

   //Constructor
   public Patient(String firstName, String lastName, int roomNumber, 
                     String emergencyContact, String emergencyNumber,
                     String insPolicy, String insPolicyNo, String primaryDoctorLastName,
                     String iniDiagnosis, String admissionDate, String dischargeDate,
                     Character personType) {
      super(firstName, lastName, roomNumber, emergencyContact, emergencyNumber,
               insPolicy, insPolicyNo, primaryDoctorLastName, iniDiagnosis,
               admissionDate, dischargeDate, personType);
   }

   public String toString() {
      return super.getFirstName() + "," + super.getLastName()
         + "," + roomNumber + "," + emergencyContact + "," + insPolicy
         + "," + insPolicyNo + "," + primaryDoctorLastName + ","
         + iniDiagnosis + "," + admissionDate + "," + dischargeDate;
   }
}
