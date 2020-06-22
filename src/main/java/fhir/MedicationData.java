package fhir;

import java.util.Date;

public class MedicationData {
    public String getId(){
        return id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getStatus() {
        return status;
    }

    public String getInfo(){
        return info;
    }

    public Date getDate(){
        return date;
    }

    public String getMedicianName(){
        return medicianName;
    }

    private String id;
    private String sourceId;
    private String status;
    private String info;
    private Date date;
    private String medicianName;

    MedicationData(String id, String sourceId, String status, String info, Date date, String medicianName){
        this.id=id;
        this.sourceId=sourceId;
        this.status=status;
        this.info=info;
        this.date=date;
        this.medicianName=medicianName;
    }
}
