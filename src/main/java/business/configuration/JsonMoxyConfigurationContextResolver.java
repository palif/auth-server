package business.configuration;

import org.glassfish.jersey.moxy.json.MoxyJsonConfig;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class JsonMoxyConfigurationContextResolver implements ContextResolver<MoxyJsonConfig> {


    private final MoxyJsonConfig config;

    public JsonMoxyConfigurationContextResolver() {

        System.out.println("Json Moxy Provider configuration: start");

        final Map<String, String> namespacePrefixMapper = new HashMap<String, String>();
        namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");

        config = new MoxyJsonConfig()
                .setNamespacePrefixMapper(namespacePrefixMapper)
                .setNamespaceSeparator(':')
//            .setAttributePrefix("")
//            .setValueWrapper("value")
//            .property(JAXBContextProperties.JSON_WRAPPER_AS_ARRAY_NAME, true)
                .setFormattedOutput(false)
                .setIncludeRoot(false)
                .setMarshalEmptyCollections(false);

    }

    public static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
        System.out.println("CLIENT: Json Moxy Provider configuration: start");

        final Map<String, String> namespacePrefixMapper = new HashMap<String, String>();
        namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");

        MoxyJsonConfig config = new MoxyJsonConfig()
                .setNamespacePrefixMapper(namespacePrefixMapper)
                .setNamespaceSeparator(':')
//            .setAttributePrefix("")
//            .setValueWrapper("value")
//            .property(JAXBContextProperties.JSON_WRAPPER_AS_ARRAY_NAME, true)
                .setFormattedOutput(false)
                .setIncludeRoot(false)
                .setMarshalEmptyCollections(false);

        return config.resolver();
    }

    @Override
    public MoxyJsonConfig getContext(Class<?> type) {

        System.out.println("Json Moxy Provider configuration: end");

        return config;
    }


}
