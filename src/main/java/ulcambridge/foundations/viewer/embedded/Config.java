package ulcambridge.foundations.viewer.embedded;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.MapType;

import java.io.IOException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>The default configuration for the embedded viewer is currently:
 *
 * <p><pre><code>
 * {
 *     "googleAnalyticsTrackingId": "UA-57175370-1",
 *     "metadataUrlPrefix": "//services.cudl.lib.cam.ac.uk/v1/metadata/json/",
 *     "metadataUrlSuffix": "",
 *     "dziUrlPrefix": "//image01.cudl.lib.cam.ac.uk/",
 *     "metadataUrlHost": "//cudl.lib.cam.ac.uk"
 * }
 * </code></pre></p>
 */
public class Config {
    private final Map<ConfigProperty, String> values;

    public Config(Map<ConfigProperty, String> values) {
        if(values.isEmpty())
            this.values = Collections.EMPTY_MAP;
        else
            this.values = Collections.unmodifiableMap(new EnumMap<>(values));
    }

    public Map<ConfigProperty, String> getValues() {
        return this.values;
    }

    public String asJson() {
        try {
            return new ObjectMapper()
                .writer()
                .with(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
                .writeValueAsString(this.getValues());
        }
        catch (JsonProcessingException e) {
            // This should never happen as we strictly control the data being
            // serialised.
            throw new RuntimeException("Failed to convert Config to JSON", e);
        }
    }

    public static Config parseJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        MapType type = mapper.getTypeFactory().constructMapType(HashMap.class, ConfigProperty.class, String.class);
        try {
            HashMap<ConfigProperty, String> map = mapper.readValue(json, type);
            return new Config(map);
        }
        catch(JsonProcessingException e) {
            throw e;
        }
        catch(IOException e) {
            // Should never happen
            throw new RuntimeException(
                "IOException thrown while parsing string", e);
        }
    }

}
