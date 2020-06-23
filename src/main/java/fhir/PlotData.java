package fhir;

import java.util.Date;

public class PlotData implements Comparable<PlotData> {
    public Double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    Double value;
    Date date;

    PlotData(Double value, Date date){
        this.value=value;
        this.date=date;
    }

    @Override
    public int compareTo(PlotData o) {
        return date.compareTo(o.date);
    }
}
