package ulcambridge.foundations.viewer.embedded;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.util.Collections;

import static junit.framework.Assert.assertEquals;

public class ConfigTest {
    @Test
    public void testEmptyConfigJsonIsEmptyObject() {
        assertEquals("{}", new Config(Collections.emptyMap()).asJson());
    }

    @Test
    public void testConfigSerialise1() {
        Config c = new Config(Collections.singletonMap(
            ConfigProperty.DZI_URL_PREFIX, "foo"));

        assertEquals("{\"dziUrlPrefix\":\"foo\"}", c.asJson());
    }

    @Test
    public void testParsingEmptyObjectCreatesEmptyConfig() throws JsonProcessingException {
        assertEquals(0, Config.parseJson("{}").getValues().size());
    }

    @Test
    public void testParsingFullConfig() throws JsonProcessingException {
        String configJson = "{\n" +
            "    \"googleAnalyticsTrackingId\": \"UA-57175370-1\",\n" +
            "    \"metadataUrlPrefix\": \"//services.cudl.lib.cam.ac.uk/v1/metadata/json/\",\n" +
            "    \"metadataUrlSuffix\": \"\",\n" +
            "    \"dziUrlPrefix\": \"//image01.cudl.lib.cam.ac.uk/\",\n" +
            "    \"metadataUrlHost\": \"//cudl.lib.cam.ac.uk\"\n" +
            "}\n";

        Config c = Config.parseJson(configJson);

        assertEquals(5, c.getValues().size());
        assertEquals("UA-57175370-1",
             c.getValues().get(ConfigProperty.GA_TRACKING_ID));
        assertEquals("//services.cudl.lib.cam.ac.uk/v1/metadata/json/",
            c.getValues().get(ConfigProperty.METADATA_URL_PREFIX));
        assertEquals("",
            c.getValues().get(ConfigProperty.METADATA_URL_SUFFIX));
        assertEquals("//image01.cudl.lib.cam.ac.uk/",
            c.getValues().get(ConfigProperty.DZI_URL_PREFIX));
        assertEquals("//cudl.lib.cam.ac.uk",
            c.getValues().get(ConfigProperty.METADATA_URL_HOST));
    }

    @Test(expected=JsonProcessingException.class)
    public void testDeserialiseInvalidKeyThrowsJPE() throws JsonProcessingException {
        Config.parseJson("{\"invalidKey\":\"foo\"}");
    }
}
