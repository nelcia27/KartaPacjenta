package fhir;

import org.hl7.fhir.r4.model.Observation;

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

    public Observation getObservation() {
        return observation;
    }

    public void update(Double valueUpdate, String unitUpdate){
        MainResourceGetter.updateObservationValue(this, valueUpdate);
        value = valueUpdate;
        MainResourceGetter.updateObservationUnit(this, unitUpdate);
        unit = unitUpdate;
    }

    private String category;
    private Double value;
    private String unit;
    Observation observation;

    ObservationData(String id, String sourceId, String status, String category, String info, Double value, Date adataTime, String unit){
        super(id, sourceId, status, info, adataTime, "Obserwacja");
        this.category = category;
        this.value = value;
        this.unit = unit;
    }
}
