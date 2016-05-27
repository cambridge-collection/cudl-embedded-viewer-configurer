package ulcambridge.foundations.viewer.embedded;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;

public enum ConfigProperty {
    GA_TRACKING_ID("googleAnalyticsTrackingId"),
    METADATA_URL_PREFIX("metadataUrlPrefix"),
    METADATA_URL_SUFFIX("metadataUrlSuffix"),
    DZI_URL_PREFIX("dziUrlPrefix"),
    METADATA_URL_HOST("metadataUrlHost");

    private final String jsonKey;

    ConfigProperty(String jsonKey) {
        assert jsonKey != null;
        this.jsonKey = jsonKey;
    }

    @JsonValue
    public String getJsonKey() {
        return this.jsonKey;
    }

    @JsonCreator
    public static Optional<ConfigProperty> fromJsonKey(String jsonKey) {
        return Arrays.stream(ConfigProperty.values())
            .filter(cp -> cp.jsonKey.equals(jsonKey))
            .findFirst();
    }
}
