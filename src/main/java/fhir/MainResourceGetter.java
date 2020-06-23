package fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.util.BundleUtil;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.List;

public class MainResourceGetter {

    public ArrayList<PatientData> getPatients() {
        return patients;
    }
    private ArrayList<PatientData> patients;
    private static IGenericClient client;
    private static FhirContext ctx;

    public static IGenericClient getClient() {
        return client;
    }

    public void run() {

        // Create a context
        ctx = FhirContext.forR4();

        // Create a client
        client = ctx.newRestfulGenericClient("http://localhost:8080/baseR4");

        // Take all needed resources
        patients = getPatientsFromServer(ctx,client);
        ArrayList<ObservationData> observations=getObservations(ctx,client);
        ArrayList<MedicationData> medicationRequests=getMedicationRequests(ctx,client);

        for(PatientData p : patients){
            ArrayList<ObservationData> observationData=new ArrayList<>();
            for(ObservationData o : observations){
                if(o.getSourceId().equals("Patient/"+p.getId())){
                    observationData.add(o);
                }
            }
            p.observationData=observationData;

            ArrayList<MedicationData> medicationData=new ArrayList<>();
            for (MedicationData md : medicationRequests){
                if(md.getSourceId().equals("Patient/"+p.getId())){
                    medicationData.add(md);
                }
            }
            p.medicationData=medicationData;

            /*
            updatePatientStatus(p.patient,client,false);
            updatePatientTelecom(p.patient,client,"123456789");
            updatePatientAddress(p.patient,client,"A","B","C","D");
            try {
                System.out.println(p.observationData.get(0).observation.getValueQuantity().getUnit());
                updateObservationValue(p.observationData.get(0).observation, client, 12.0);
                updateObservationUnit(p.observationData.get(0).observation, client, "cm");
                System.out.println(p.observationData.get(0).observation.getValueQuantity().getUnit());
            }catch(Exception e){

            }*/
        }
    }

    public static ArrayList<PatientData> getPatientsFromServer(FhirContext ctx, IGenericClient client){
        List<IBaseResource> patientsX = new ArrayList<>();

        Bundle patients = client
                .search()
                .forResource(Patient.class)
                .returnBundle(Bundle.class)
                .encodedJson()
                .execute();
        patientsX.addAll(BundleUtil.toListOfResources(ctx, patients));

        while (patients.getLink(IBaseBundle.LINK_NEXT) != null) {
            patients = client
                    .loadPage()
                    .next(patients)
                    .execute();
            patientsX.addAll(BundleUtil.toListOfResources(ctx, patients));
        }

        ArrayList<String> idListPatient=new ArrayList<>();
        for(IBaseResource r : patientsX){
            idListPatient.add(r.getIdElement().getValue());
        }

        ArrayList<Patient> patients1=new ArrayList<>();
        for(String id : idListPatient){
            Patient p=client
                    .read()
                    .resource(Patient.class)
                    .withId(id)
                    .encodedJson()
                    .execute();
            patients1.add(p);
        }

        ArrayList<PatientData> res_patients= new ArrayList<>();
        for(Patient p : patients1){
            String dist="";
            String postal_code="";
            if(p.getAddress().get(0).getDistrict()!=null){
                dist=p.getAddress().get(0).getDistrict();
            }
            if(p.getAddress().get(0).getPostalCode()!=null){
                postal_code=p.getAddress().get(0).getPostalCode();
            }
            PatientData patient=new PatientData(p.getIdElement().getIdPart(),
                    p.getActive(),
                    p.getName().get(0).getNameAsSingleString(),
                    p.getTelecom().get(0).getValue(),
                    p.getGender().toString(),
                    p.getBirthDate(),
                    p.getAddress().get(0).getCountry(),
                    p.getAddress().get(0).getCity(),
                    dist,
                    postal_code,
                    p.getMaritalStatus().getText()
                    );
            patient.patient=p;
            res_patients.add(patient);
        }

        return res_patients;
    }

    public static ArrayList<ObservationData> getObservations(FhirContext ctx, IGenericClient client){
        List<IBaseResource> observationsX = new ArrayList<>();
        Bundle observations = client
                .search()
                .forResource(Observation.class)
                .returnBundle(Bundle.class)
                .encodedJson()
                .execute();
        observationsX.addAll(BundleUtil.toListOfResources(ctx, observations));

        while (observations.getLink(IBaseBundle.LINK_NEXT) != null) {
            observations = client
                    .loadPage()
                    .next(observations)
                    .execute();
            observationsX.addAll(BundleUtil.toListOfResources(ctx, observations));
        }

        ArrayList<String> idListObservations=new ArrayList<>();
        for(IBaseResource r : observationsX){
            idListObservations.add(r.getIdElement().getValue());
        }

        ArrayList<Observation> observations1=new ArrayList<>();
        for(String id : idListObservations){
            Observation p=client
                    .read()
                    .resource(Observation.class)
                    .withId(id)
                    .encodedJson()
                    .execute();
            observations1.add(p);
        }

        ArrayList<ObservationData> res_observation= new ArrayList<>();
        for(Observation p : observations1){
            Double value=0.0;
            String unit="";
            try{
                value=p.getValueQuantity().getValue().doubleValue();
                unit = p.getValueQuantity().getUnit();
            }catch (Exception e){

            }
            ObservationData o= new ObservationData(p.getId(),
                    p.getSubject().getReference(),
                    p.getStatus().toCode(),
                    p.getCategory().get(0).getCoding().get(0).getCode(),
                    p.getCode().getText(),
                    value,
                    p.getEffectiveDateTimeType().getValue(),
                    unit
                    );
            o.observation=p;
            res_observation.add(o);

        }
        return res_observation;
    }

    public ArrayList<MedicationData> getMedicationRequests(FhirContext ctx, IGenericClient client){
        List<IBaseResource> medicationsX = new ArrayList<>();
        Bundle medicationRequests = client
                .search()
                .forResource(MedicationRequest.class)
                .returnBundle(Bundle.class)
                .encodedJson()
                .execute();
        medicationsX.addAll(BundleUtil.toListOfResources(ctx, medicationRequests));

        while (medicationRequests.getLink(IBaseBundle.LINK_NEXT) != null) {
            medicationRequests = client
                    .loadPage()
                    .next(medicationRequests)
                    .execute();
            medicationsX.addAll(BundleUtil.toListOfResources(ctx, medicationRequests));
        }

        ArrayList<String> idListMedicationRequests=new ArrayList<>();
        for(IBaseResource r : medicationsX){
            idListMedicationRequests.add(r.getIdElement().getValue());
        }

        ArrayList<MedicationRequest> medicationRequests1=new ArrayList<>();
        for(String id : idListMedicationRequests){
            MedicationRequest p=client
                    .read()
                    .resource(MedicationRequest.class)
                    .withId(id)
                    .encodedJson()
                    .execute();
            medicationRequests1.add(p);
        }

        ArrayList<MedicationData> res_medicationRequest= new ArrayList<>();
        for(MedicationRequest p : medicationRequests1){
            MedicationData md=new MedicationData(p.getId(),
                    p.getSubject().getReference(),
                    p.getStatus().toCode(),
                    p.getMedicationCodeableConcept().getCoding().get(0).getDisplay(),
                    p.getAuthoredOn(),
                    p.getRequester().getDisplay()
                    );
            res_medicationRequest.add(md);

        }

        return res_medicationRequest;
    }

    public static ArrayList<PlotData> getDataHeight(PatientData patient){
        ArrayList<PlotData> pd= new ArrayList<>();
        ArrayList<ObservationData> observation=patient.getObservationData();
        for(ObservationData o : observation){
            if(o.getInfo().equals("Body Height")){
                if(!o.getUnit().equals(""))
                    pd.add(new PlotData(o.getValue(),o.getAdataTime()));
            }
        }
        return pd;
    }

    public static ArrayList<PlotData> getDataWeight(PatientData patient){
        ArrayList<PlotData> pd= new ArrayList<>();
        ArrayList<ObservationData> observation=patient.getObservationData();
        for(ObservationData o : observation){
            if(o.getInfo().equals("Body Weight")){
                if(!o.getUnit().equals(""))
                    pd.add(new PlotData(o.getValue(),o.getAdataTime()));
            }
        }
        return pd;
    }

    public static Patient updatePatientStatus(PatientData patient, boolean status){
        patient.getPatient().setActive(status);
        MethodOutcome outcome = client.update()
                .resource(patient.getPatient())
                .execute();
        return getUpdatedPatient(patient);
    }

    public static Patient updatePatientTelecom(PatientData patient, String number){
        List<ContactPoint> tmp=patient.getPatient().getTelecom();
        tmp.get(0).setValue(number);
        patient.getPatient().setTelecom(tmp);
        MethodOutcome outcome = client.update()
                .resource(patient.getPatient())
                .execute();
        return getUpdatedPatient(patient);
    }

    public static Patient updatePatientAddress(PatientData patient, String country, String city, String dist, String post_code){
        List<Address> tmp=patient.getPatient().getAddress();
        tmp.get(0).setCity(city);
        tmp.get(0).setCountry(country);
        tmp.get(0).setDistrict(dist);
        tmp.get(0).setPostalCode(post_code);
        patient.getPatient().setAddress(tmp);
        MethodOutcome outcome = client.update()
                .resource(patient.getPatient())
                .execute();
        return getUpdatedPatient(patient);
    }

    public static void updateObservationUnit(ObservationData o, String unit){
        try{
            Type tmp= o.getObservation().getValueQuantity().setUnit(unit);
            o.getObservation().setValue(tmp);
            MethodOutcome outcome = client.update()
                    .resource(o.getObservation())
                    .execute();
        }catch (Exception ignored){ }
    }

    public static void updateObservationValue(ObservationData o, Double value){
        try{
            Type tmp= o.getObservation().getValueQuantity().setValue(value);
            o.getObservation().setValue(tmp);
            MethodOutcome outcome = client.update()
                    .resource(o.getObservation())
                    .execute();
        }catch (Exception ignored){ }
    }

    public static Patient getUpdatedPatient(PatientData patient){
        for(PatientData patientData : getPatientsFromServer(ctx, client)){
            if(patient.getId().equals(patientData.getId()))
                return patientData.getPatient();
        }
        return patient.getPatient();
    }

}
