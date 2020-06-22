package fhir;

import javax.swing.*;
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

    public Double getValue() {
        return value;
    }

    public Date getAdataTime() {
        return adataTime;
    }

    public String getUnit() {
        return unit;
    }

    private String id;
    private String sourceId;
    private String status;
    private String category;
    private String info;
    private Double value;
    private Date adataTime;
    private String unit;

    ObservationData(String id, String sourceId, String status, String category, String info, Double value, Date adataTime, String unit){
        this.id=id;
        this.sourceId=sourceId;
        this.status=status;
        this.category=category;
        this.info=info;
        this.value=value;
        this.adataTime=adataTime;
        this.unit=unit;
    }
}
