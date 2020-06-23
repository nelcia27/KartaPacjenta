package fhir;

import javax.swing.*;
import java.util.Date;

public class ObservationData extends PatientDetailData {

    public String getCategory() {
        return category;
    }

    public Double getValue() {

        return value;
    }

    public String getUnit() {
        return unit;
    }

    private String category;
    private Double value;
    private String unit;

    ObservationData(String id, String sourceId, String status, String category, String info, Double value, Date adataTime, String unit){
        super(id, sourceId, status, info, adataTime, "Obserwacja");
        this.category = category;
        this.value = value;
        this.unit = unit;
    }
}
