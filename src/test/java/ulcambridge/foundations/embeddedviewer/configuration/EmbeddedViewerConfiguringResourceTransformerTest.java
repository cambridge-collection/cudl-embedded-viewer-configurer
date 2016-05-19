package ulcambridge.foundations.embeddedviewer.configuration;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.resource.ResourceTransformerChain;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class EmbeddedViewerConfiguringResourceTransformerTest {

    private ResourceTransformerChain noopTransformer;

    @Before
    public void setUp() throws IOException {
        ResourceTransformerChain chain = mock(ResourceTransformerChain.class);
        when(chain.transform(anyObject(), anyObject()))
            .thenAnswer((inv) -> inv.getArgumentAt(1, Resource.class));
        noopTransformer = chain;
    }

    @Test
    public void test() throws IOException {
        Resource r = new ClassPathResource("test/viewer.html", EmbeddedViewerConfiguringResourceTransformerTest.class);
        Config c = Config.parseJson("{\"googleAnalyticsTrackingId\":\"ABCD\"}");

        Resource result = new EmbeddedViewerConfiguringResourceTransformer(c)
            .transform(new MockHttpServletRequest(), r, noopTransformer);

        ConfigurerTest.assertHtmlContainsConfigElement(
            new String(FileCopyUtils.copyToByteArray(result.getInputStream()),
                       "UTF-8"), c);
    }
}
