package fhir;

import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.Date;

public class PatientData {
    public String getId() {
        return id;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSex() {
        return sex;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public String getAddress() {
        return country+" "+city+" "+district+" "+postCode;
    }

    public String getMeritalStatus() {
        return meritalStatus;
    }

    public ArrayList<ObservationData> getObservationData() {
        return observationData;
    }

    public ArrayList<MedicationData> getMedicationData() {
        return medicationData;
    }

    public Patient getPatient(){
        return patient;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getPostCode() {
        return postCode;
    }

    private String id;
    private Boolean status;
    private String name;
    private String phone;
    private String sex;
    private Date dateBirth;
//    private String address;
    private String country;
    private String city;
    private String district;
    private String postCode;
    private String meritalStatus;

    ArrayList<ObservationData> observationData;
    ArrayList<MedicationData> medicationData;
    Patient patient;

    public void update(String phoneNumUpdate, String countryUpdate, String cityUpdate, String districtUpdate, String postCodeUpdate, boolean statusUpdate){
        patient = MainResourceGetter.updatePatientTelecom(this, phoneNumUpdate);
        phone = phoneNumUpdate;
        patient = MainResourceGetter.updatePatientAddress(this, countryUpdate, cityUpdate, districtUpdate, postCode);
        country = countryUpdate;
        city = cityUpdate;
        district = districtUpdate;
        postCode = postCodeUpdate;
        patient = MainResourceGetter.updatePatientStatus(this, status);
        status = statusUpdate;
    }


    PatientData(String id, Boolean status, String name, String phone, String sex, Date dateBirth, String country, String city, String district, String postCode, String meritalStatus){
        this.id=id;
        this.status=status;
        this.name=name;
        this.phone=phone;
        this.sex=sex;
        this.dateBirth=dateBirth;
        this.country = country;
        this.city = city;
        this.district = district;
        this.postCode = postCode;
        this.meritalStatus=meritalStatus;
    }
}
