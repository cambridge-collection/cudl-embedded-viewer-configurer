package ulcambridge.foundations.viewer.embedded;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurerTest {

    @Test
    public void testConfigureInsertsConfigElement() throws JsonProcessingException {
        Document doc = Configurer.configureViewerHtml("", Config.parseJson("{}"));

        Elements e = doc.select(
            "html head script#config[type=\"application/json\"]");

        assertEquals(1, e.size());
        assertEquals("{}", e.get(0).text());
    }
}
