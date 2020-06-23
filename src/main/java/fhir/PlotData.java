package fhir;

import java.util.Date;

public class PlotData {
    Double value;
    Date date;

    PlotData(Double value, Date date){
        this.value=value;
        this.date=date;
    }
}
