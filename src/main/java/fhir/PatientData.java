package fhir;

import org.hl7.fhir.r4.model.HumanName;

import java.util.Date;
import java.util.List;

public class PatientData {
    String id;
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

    PatientData(String id){
        this.id=id;
    }
}
