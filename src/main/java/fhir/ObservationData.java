package fhir;

public class ObservationData {
    String id;
    String sourceId;
    String status;
    String category;
    String info;
    String value;
    String adataTime;
    String encounter;

    ObservationData(String id){
        this.id=id;
    }
}
