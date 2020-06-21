package fhir;

import java.util.Date;

public class ObservationData {
    public String getId() {
        return id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }

    public String getInfo() {
        return info;
    }

    public String getValue() {
        return value;
    }

    public Date getAdataTime() {
        return adataTime;
    }

    public String getEncounter() {
        return encounter;
    }

    String id;
    String sourceId;
    String status;
    String category;
    String info;
    String value;
    Date adataTime;     // Poprawiłem na Date bo łatwiej mi sie korzysta niż ze stringa
    String encounter;

    ObservationData(String id){
        this.id=id;
    }
}
