package ulcambridge.foundations.embeddedviewer.configuration;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurerTest {

    public static void assertHtmlContainsConfigElement(Document html, Config c) {
        Elements e = html.select(
            "html > head > script#config[type=\"application/json\"]");

        assertEquals(1, e.size());
        assertEquals(c.asJson(), e.get(0).data());
    }

    public static void assertHtmlContainsConfigElement(String html, Config c) {
        assertHtmlContainsConfigElement(Jsoup.parse(html), c);
    }

    @Test
    public void testConfigureInsertsConfigElement() throws JsonProcessingException {
        Document doc = Configurer.configureViewerHtml("", Config.parseJson("{}"));
    }
}
