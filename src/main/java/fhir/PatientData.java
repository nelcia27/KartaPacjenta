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

    public String getAlive() {
        return alive;
    }

    public String getAddress() {
        return address;
    }

    public String getMeritalStatus() {
        return meritalStatus;
    }

    public Boolean getBirthMultiple() {
        return birthMultiple;
    }

    public String getContactRelationShip() {
        return contactRelationShip;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public String getContactSex() {
        return contactSex;
    }

    public String getContactOrganization() {
        return contactOrganization;
    }

    public String getContactWhen() {
        return contactWhen;
    }

    public String getLanguage() {
        return language;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public String getMedician() {
        return medician;
    }

    public String getOrganizationMain() {
        return organizationMain;
    }

    String id;  // FIXME: może lepiej jak będą prywatne i init w konstruktorze?
    Boolean status;
    String name;
    String phone;
    String sex;
    Date dateBirth;
    String alive;
    String address;
    String meritalStatus;
    Boolean birthMultiple;
    String contactRelationShip;
    String contactName;
    String contactTelephone;
    String contactAddress;
    String contactSex;
    String contactOrganization;
    String contactWhen;
    String language;
    String preferredLanguage;
    String medician;
    String organizationMain;
    ArrayList<ObservationData> observationData;

    PatientData(String id){
        this.id=id;
    }
}
