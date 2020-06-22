package fhir;

import org.hl7.fhir.r4.model.HumanName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        return address;
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

    private String id;
    private Boolean status;
    private String name;
    private String phone;
    private String sex;
    private Date dateBirth;
    private String address;
    private String meritalStatus;

    ArrayList<ObservationData> observationData;
    ArrayList<MedicationData> medicationData;

    PatientData(String id, Boolean status, String name, String phone, String sex, Date dateBirth, String address, String meritalStatus){
        this.id=id;
        this.status=status;
        this.name=name;
        this.phone=phone;
        this.sex=sex;
        this.dateBirth=dateBirth;
        this.address=address;
        this.meritalStatus=meritalStatus;
    }
}
