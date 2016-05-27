package ulcambridge.foundations.viewer.embedded;

import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.resource.ResourceTransformer;
import org.springframework.web.servlet.resource.ResourceTransformerChain;
import org.springframework.web.servlet.resource.TransformedResource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.function.Predicate;


/**
 * A {@link ResourceTransformer} which inserts {@link Config configuration} into
 * the CUDL embedded viewer HTML.
 */
public class EmbeddedViewerConfiguringResourceTransformer
        implements ResourceTransformer {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /** Matches resources named <code>viewer.html</code>. */
    public static final Predicate<Resource> VIEWER_HTML_FILENAME_PREDICATE =
        (resource) -> resource.getFilename().equals("viewer.html");

    private final Config config;
    private final Predicate<Resource> isViewerHtml;
    private final Charset charset;


    /**
     * Creates a transformer using {@link #VIEWER_HTML_FILENAME_PREDICATE} and
     * {@link #DEFAULT_CHARSET} (UTF-8).
     */
    public EmbeddedViewerConfiguringResourceTransformer(Config config) {
        this(config, VIEWER_HTML_FILENAME_PREDICATE, DEFAULT_CHARSET);
    }

    public EmbeddedViewerConfiguringResourceTransformer(
            Config config, Predicate<Resource> isViewerHtml, Charset charset) {
        Assert.notNull(config);
        Assert.notNull(isViewerHtml);
        Assert.notNull(charset);

        this.config = config;
        this.isViewerHtml = isViewerHtml;
        this.charset = charset;
    }

    @Override
    public Resource transform(HttpServletRequest request, Resource resource,
                              ResourceTransformerChain transformerChain)
                throws IOException {

        resource = transformerChain.transform(request, resource);

        if(!isViewerHtml.test(resource)) {
            return resource;
        }

        byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        String content = new String(bytes, DEFAULT_CHARSET);

        String configuredHtml = Configurer.configureViewerHtml(
            content, this.config).toString();

        return new TransformedResource(resource,
                                       configuredHtml.getBytes(this.charset));
    }
}
