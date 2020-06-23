package fhir;

import java.util.Date;

public abstract class PatientDetailData implements Comparable<PatientDetailData> {
    private String id;
    private String sourceId;
    private String status;
    private String info;
    private Date adataTime;
    private String description;

    public PatientDetailData(String id, String sourceId, String status, String info, Date adataTime, String description) {
        this.id = id;
        this.sourceId = sourceId;
        this.status = status;
        this.info = info;
        this.adataTime = adataTime;
        this.description = description;
    }

    public Date getAdataTime() {
        return adataTime;
    }

    public String getDescription() {
        return description;
    }

    public String getInfo() {
        return info;
    }

    public String getId() {
        return id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int compareTo(PatientDetailData o) {
        return adataTime.compareTo(o.adataTime);
    }
}
