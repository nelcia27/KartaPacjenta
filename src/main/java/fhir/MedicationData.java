package fhir;

import java.util.Date;

public class MedicationData extends PatientDetailData {

    public String getMedicianName(){
        return medicianName;
    }

    private String medicianName;

    MedicationData(String id, String sourceId, String status, String info, Date date, String medicianName){
        super(id, sourceId, status, info, date, "Zlecenia leków");
        this.medicianName = medicianName;
    }
}
