package test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import ca.uhn.fhir.util.BundleUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.checkerframework.checker.units.qual.A;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseParameters;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.sun.xml.internal.ws.client.ContentNegotiation.none;

public class MainResourceGetter {

   public static void main(String[] args) {

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

      System.out.println("Found " +patients.getEntry().size()+ " patients named 'brekke'");

      ArrayList<Resource> resources=new ArrayList<>();
      for (Iterator<Bundle.BundleEntryComponent> it = patients.getEntry().iterator(); it.hasNext(); ) {
         Bundle.BundleEntryComponent p = it.next();
         resources.add(p.getResource());
      }

      ArrayList<String> idList=new ArrayList<>();
      for(Resource r : resources){
         idList.add(r.getId());
      }

      /*Parameters inParams = new Parameters();
      inParams.addParameter().setName("start").setValue(new DateType("2001-01-01"));
      inParams.addParameter().setName("end").setValue(new DateType("2015-03-01"));*/
      ArrayList<Patient> patients1=new ArrayList<>();
      for(String id : idList){
         /*Patient p = client
            .operation()
            .onInstance(new IdType("Patient", id))
            .named("$everything")
            .withParameters(inParams)
            .useHttpGet()
            .returnResourceType(Patient.class)
            .execute();*/
         Patient p=client
            .read()
            .resource(Patient.class)
            .withId(id)
            .encodedJson()
            .execute();
         patients1.add(p);
      }

      for(Patient p : patients1){
         System.out.println(p.NAME);

         System.out.println(p.getGender());
         System.out.println(p.getBirthDate());
      }

   }

}
