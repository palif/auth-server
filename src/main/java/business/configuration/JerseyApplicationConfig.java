package business.configuration;

import business.controller.SecurityController;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class JerseyApplicationConfig extends ResourceConfig {

    public JerseyApplicationConfig() {
//        Set<Class<?>> resources = new HashSet<>();
//
//        System.out.println("REST configuration starting: getClasses()");
//        resources.add(MOXyJsonProvider.class);
//        //features
//        //this will register MOXy JSON providers
//        //resources.add(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
//        //we could also use this
//        //resources.add(org.glassfish.jersey.moxy.xml.MoxyXmlFeature.class);
//        //instead let's do it manually:
//        resources.add(OrderController.class);
//        resources.add(TransportController.class);
//        resources.add(SecurityController.class);
//        resources.add(business.filter.JWTTokenFilter.class);
//        resources.add(KeyGenerator.class);
//
//        resources.add(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
//        resources.add(JsonMoxyConfigurationContextResolver.class);
//        //==> we could also choose packages, see below getProperties()
//
//        System.out.println("REST configuration ended: successfully.");
        register(MOXyJsonProvider.class);
        register(SecurityController.class);
        register(business.filter.JWTTokenFilter.class);
        register(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
        packages("business.configuration").register(JsonMoxyConfigurationContextResolver.class);
        register(new ApplicationBinder());
        System.out.println("JerseyApplicationConfig resolved");

    }



}
