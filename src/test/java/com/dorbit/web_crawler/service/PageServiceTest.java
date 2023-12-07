package com.dorbit.web_crawler.service;

import com.dorbit.web_crawler.BaseTest;
import com.dorbit.web_crawler.utils.MockConnection;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.dorbit.web_crawler.utils.MockConnection.MOCK_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;


public class PageServiceTest extends BaseTest {

    @Spy @InjectMocks private PageService service;

    @Test
    void getPageContent_shouldGetContent() {
        //mocks
        URL startingUrl = random(URL.class);
        HttpURLConnection connection = new MockConnection(startingUrl);

        String result = service.getPageContent(connection);

        assertThat(result.trim()).isEqualTo(MOCK_CONTENT.trim());
    }

    @Test
    void getPageUrls_getUrls() {
        String html = "<a href=\"https://www.linkedin.com/company/d-orbit/\" target=\"_blank\" class=\"social-icon my-1\"><i class=\"fa fa-linkedin\"><span class=\"visually-hidden\">D-Orbit on linkedin</span></i></a>";

        List<String> result = service.getPageUrls(html);

        assertThat(result)
                .containsExactlyInAnyOrder("https://www.linkedin.com/company/d-orbit/");

    }

    @Test
    void getSubdomain_shouldGet() {
        try {
            URL url = new URL("https://www.google.com");

            String result = service.getSubdomain(url);

            assertThat(result).isEqualTo("google.com");
        } catch (MalformedURLException e) {
            //doNothing
        }
    }

    @Test
    void isSameSubdomain_shouldBeSameSubdomain() {
        String subdomain = "google.com";
        String pageUrl = "https://www.google.com/test";

        boolean result = service.isSameSubdomain(pageUrl, subdomain);

        assertThat(result).isTrue();
    }

    @Test
    void isSameSubdomain_shouldNotBeSameSubdomain() {
        String subdomain = "google.com";
        String pageUrl = "https://www.dorbit.com/test";

        boolean result = service.isSameSubdomain(pageUrl, subdomain);

        assertThat(result).isFalse();
    }

    @Test
    void isSameSubdomain_shouldNotBeSameSubdomain_malformedUrl() {
        String subdomain = "google.com";
        String pageUrl = "badUrl";

        boolean result = service.isSameSubdomain(pageUrl, subdomain);

        assertThat(result).isFalse();
    }
}
