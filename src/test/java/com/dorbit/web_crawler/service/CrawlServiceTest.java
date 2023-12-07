package com.dorbit.web_crawler.service;

import com.dorbit.web_crawler.BaseTest;
import com.dorbit.web_crawler.controller.crawl.bean.CrawlResponseBean;
import com.dorbit.web_crawler.controller.crawl.bean.CrawlResponseBeanBuilder;
import com.dorbit.web_crawler.utils.MockConnection;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.dorbit.web_crawler.service.CrawlService.visitedUrls;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CrawlServiceTest extends BaseTest {

    @Mock private PageService pageService;
    @Spy @InjectMocks private CrawlService service;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(pageService);
    }

    @Test
    void crawl_shouldCrawl() {
        //mocks
        URL startingUrl = random(URL.class);
        HttpURLConnection connection = new MockConnection(startingUrl);

        String subdomain = RandomStringUtils.random(5);
        when(pageService.getSubdomain(any())).thenReturn(subdomain);

        String content = RandomStringUtils.random(5);
        when(pageService.getPageContent(any(HttpURLConnection.class))).thenReturn(content);

        List<String> pageUrls = IntStream.range(0, 10)
                .mapToObj(i -> RandomStringUtils.random(5))
                .toList();
        when(pageService.getPageUrls(any())).thenReturn(pageUrls);

        doNothing().when(service).crawlPage(any(), any());


        List<CrawlResponseBean> result = service.crawl(startingUrl, connection);


        //assertions
        assertThat(result.size()).isOne();
        assertThat(result.get(0))
                .usingRecursiveComparison()
                .isEqualTo(new CrawlResponseBeanBuilder()
                        .withUrl(startingUrl.toString())
                        .withCrawledUrls(pageUrls)
                        .build());
        verify(pageService).getSubdomain(startingUrl);
        verify(pageService).getPageContent(connection);
        verify(pageService).getPageUrls(content);
        pageUrls.forEach(
                url -> verify(service).crawlPage(url, subdomain)
        );
    }


    @Test
    void crawlPage_shouldCrawl() {
        //mocks
        String subdomain = RandomStringUtils.random(5);
        String pageUrl = RandomStringUtils.random(5);
        when(pageService.isSameSubdomain(eq(pageUrl), any())).thenReturn(true);
        String content = RandomStringUtils.random(5);
        when(pageService.getPageContent(pageUrl)).thenReturn(content);
        String foundUrl = RandomStringUtils.random(5);
        String otherSubdomain = RandomStringUtils.random(5);
        when(pageService.getPageUrls(content)).thenReturn(List.of(foundUrl, otherSubdomain));

        when(pageService.isSameSubdomain(eq(otherSubdomain), any())).thenReturn(false);
        when(pageService.isSameSubdomain(eq(foundUrl), any())).thenReturn(true);

        String foundUrlContent = RandomStringUtils.random(5);
        when(pageService.getPageContent(foundUrl)).thenReturn(foundUrlContent);
        when(pageService.getPageUrls(foundUrlContent)).thenReturn(Collections.emptyList());

        service.crawlPage(pageUrl, subdomain);

        //assertions
        List<String> scrapedUrls = visitedUrls.stream().toList();
        assertThat(scrapedUrls)
                .containsExactlyInAnyOrder(pageUrl, foundUrl, otherSubdomain);
        verify(pageService).isSameSubdomain(pageUrl, subdomain);
        verify(pageService).getPageContent(pageUrl);
        verify(pageService).getPageUrls(content);

        verify(pageService).isSameSubdomain(otherSubdomain, subdomain);

        verify(pageService).isSameSubdomain(foundUrl, subdomain);
        verify(pageService).getPageContent(foundUrl);
        verify(pageService).getPageUrls(foundUrlContent);
    }

}
