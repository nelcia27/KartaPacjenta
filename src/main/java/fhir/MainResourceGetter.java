package fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.util.BundleUtil;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainResourceGetter {

    public void run() {

        // Create a context
        FhirContext ctx = FhirContext.forR4();

        // Create a client
        IGenericClient client = ctx.newRestfulGenericClient("http://localhost:8080/baseR4");

        // Take all patients
        Bundle patients = client
                .search()
                .forResource(Patient.class)
                .returnBundle(Bundle.class)
                .encodedJson()
                .execute();

        Bundle observations = client
                .search()
                .forResource(Observation.class)
                .returnBundle(Bundle.class)
                .encodedJson()
                .execute();

        System.out.println("Found " +patients.getEntry().size()+ " patients ");
        System.out.println("Found " +observations.getEntry().size()+ " observations ");

        ArrayList<Resource> resourcesPatient=new ArrayList<>();
        for (Iterator<Bundle.BundleEntryComponent> it = patients.getEntry().iterator(); it.hasNext(); ) {
            Bundle.BundleEntryComponent p = it.next();
            resourcesPatient.add(p.getResource());
        }

        ArrayList<String> idListPatient=new ArrayList<>();
        for(Resource r : resourcesPatient){
            idListPatient.add(r.getId());
        }

        ArrayList<Resource> resourcesObservations=new ArrayList<>();
        for (Iterator<Bundle.BundleEntryComponent> it = observations.getEntry().iterator(); it.hasNext(); ) {
            Bundle.BundleEntryComponent p = it.next();
            resourcesObservations.add(p.getResource());
        }

        ArrayList<String> idListObservations=new ArrayList<>();
        for(Resource r : resourcesObservations){
            idListObservations.add(r.getId());
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

        for(Patient p : patients1){
            System.out.println(p.NAME.getParamName());
            System.out.println(p.getGender());
            System.out.println(p.getBirthDate());
        }

        for(Observation p : observations1){
            System.out.println(p.getSubject().getReference());
            System.out.println(p.getCode().getText());
            //System.out.println(p.getBodySite());
        }

    }

}
