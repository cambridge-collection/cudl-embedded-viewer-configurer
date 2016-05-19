package ulcambridge.foundations.embeddedviewer.configuration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Configurer {
    public static Document configureViewerHtml(String viewerHtml, Config config) {
        return configureViewerHtml(Jsoup.parse(viewerHtml), config);
    }

    public static Document configureViewerHtml(Document viewerHtml, Config config) {
        viewerHtml.head()
            .prependElement("script")
            .attr("type", "application/json")
            .attr("id", "config")
            .text(config.asJson());

        return viewerHtml;
    }
}
