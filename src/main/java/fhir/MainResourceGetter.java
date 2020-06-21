package fhir;

import ca.uhn.fhir.context.FhirContext;
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

    public void run() {

        // Create a context
        FhirContext ctx = FhirContext.forR4();

        // Create a client
        IGenericClient client = ctx.newRestfulGenericClient("http://localhost:8080/baseR4");

        // Take all needed resources
        patients = getPatientsFromServer(ctx,client);
        //ArrayList<String> observations=getObservations(ctx,client);
        ArrayList<ObservationData> observations=getObservations(ctx,client);
        //ArrayList<String> medicationRequests=getMedicationRequests(ctx,client);

        for(PatientData p : patients){
            ArrayList<ObservationData> observationData=new ArrayList<>();
            for(ObservationData o : observations){
                if(o.sourceId.equals("Patient/"+p.id)){
                    observationData.add(o);
                }
            }
            p.observationData=observationData;
            System.out.println("Found "+p.observationData.size()+" observations for patient with id "+p.id);
        }

        for(ObservationData o : observations){
            //System.out.println(o);
        }

        /*for(String m : medicationRequests){
            System.out.println(m);
        }*/
    }

    public ArrayList<PatientData> getPatientsFromServer(FhirContext ctx, IGenericClient client){
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

        System.out.println("Found " +patientsX.size()+ " patients ");

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
            //System.out.println(p.NAME.getParamName());
            //System.out.println(p.getGender());
            PatientData patient=new PatientData(p.getIdElement().getIdPart());
            patient.status=p.getActive();
            patient.name=p.getName().get(0).getNameAsSingleString();
            patient.phone=p.getTelecom().get(0).getValue();
            patient.sex=p.getGender().toString();
            patient.dateBirth=p.getBirthDate();
            //patient.alive=p.getDeceased().toString();
            patient.address=p.getAddress().get(0).getCity()+" "+p.getAddress().get(0).getCountry()+" "+p.getAddress().get(0).getDistrict()+" "+p.getAddress().get(0).getPostalCode();
            patient.meritalStatus=p.getMaritalStatus().getText();
//            patient.birthMultiple=p.getMultipleBirthBooleanType().booleanValue();
            //patient.contactRelationShip=p.getContact().get(0).getRelationship().toString(); //BRAK DANYCH NA SERWERZE
            patient.language=p.getLanguage(); //BRAK DANYCH NA SERWERZE
            patient.medician=p.getGeneralPractitioner().toString(); //BRAK DANYCH NA SERWERZE
            patient.organizationMain=p.getManagingOrganization().getDisplayElement().getValueNotNull(); //BRAK DANYCH NA SERWERZE
            /*System.out.println(patient.id);
            System.out.println(patient.status);
            System.out.println(patient.name);
            System.out.println(patient.phone);
            System.out.println(patient.sex);
            System.out.println(patient.dateBirth);
            System.out.println(patient.alive);
            System.out.println(patient.address);
            System.out.println(patient.meritalStatus);
            System.out.println(patient.birthMultiple);
            System.out.println(patient.contactRelationShip);
            System.out.println(patient.language);
            System.out.println(patient.medician);
            System.out.println(patient.organizationMain);*/
            res_patients.add(patient);
        }

        return res_patients;
    }

    public ArrayList<ObservationData> getObservations(FhirContext ctx, IGenericClient client){
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

        System.out.println("Found " +observationsX.size()+ " observations ");

        /*ArrayList<Resource> resourcesObservations=new ArrayList<>();
        for (Iterator<Bundle.BundleEntryComponent> it = observations.getEntry().iterator(); it.hasNext(); ) {
            Bundle.BundleEntryComponent p = it.next();
            resourcesObservations.add(p.getResource());
        }*/

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
        //int i=0;
        for(Observation p : observations1){
            //i++;
            ObservationData o= new ObservationData(p.getId());
            o.sourceId=p.getSubject().getReference();
            o.status=p.getStatus().toCode();
            o.category=p.getCategory().get(0).getCoding().get(0).getCode();
            o.info=p.getCode().getText();
            o.adataTime=p.getEffectiveDateTimeType().getValueAsString();
            //o.value=p.getValue().primitiveValue();
            o.encounter=p.getEncounter().getReference();
            res_observation.add(o);
            /*if(i<2){
                System.out.println(o.sourceId);
                System.out.println(o.status);
                System.out.println(o.category);
                System.out.println(o.info);
                System.out.println(o.adataTime);
                //System.out.println(o.value);
                System.out.println(o.encounter);
            }*/
            //System.out.println(p.getBodySite());

        }
        return res_observation;
    }

    public ArrayList<String> getMedicationRequests(FhirContext ctx, IGenericClient client){
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

        System.out.println("Found " +medicationsX.size()+ " medicationReqests");

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

        ArrayList<String> res_medicationRequest= new ArrayList<>();
        for(MedicationRequest p : medicationRequests1){
            //System.out.println(p.getSubject().getReference());
            //--TYLKO NA CHWILE
            //res_medicationRequest.add(p.getSubject().getDisplay());
            //System.out.println(p.getBodySite());
        }

        return res_medicationRequest;
    }

}
