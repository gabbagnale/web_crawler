package com.dorbit.web_crawler.service;

import com.dorbit.web_crawler.controller.crawl.bean.CrawlResponseBean;
import com.dorbit.web_crawler.controller.crawl.bean.CrawlResponseBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Component
public class CrawlService {

    @Autowired private PageService pageService;

    public List<CrawlResponseBean> crawl(URL startingUrl, HttpURLConnection connection) {
        String subdomain = pageService.getSubdomain(startingUrl);
        String content = pageService.getPageContent(connection);
        List<String> pageUrls = Optional.ofNullable(content)
                .map(c -> pageService.getPageUrls(c))
                .orElseGet(Collections::emptyList);
        Set<String> visitedUrls = new HashSet<>();
        visitedUrls.add(startingUrl.toString());
        List<CrawlResponseBean> crawledPages = new ArrayList<>();
        crawledPages.add(new CrawlResponseBeanBuilder()
                .withUrl(startingUrl.toString())
                .withCrawledUrls(pageUrls)
                .build());
        pageUrls.forEach(
                pageUrl -> crawlPage(pageUrl, subdomain, visitedUrls, crawledPages)
        );
        return crawledPages;
    }

    protected void crawlPage(String pageUrl, String subdomain, Set<String> visitedUrls, List<CrawlResponseBean> crawledPages) {
        if (visitedUrls.contains(pageUrl) || !pageService.isSameSubdomain(pageUrl, subdomain)) {
            visitedUrls.add(pageUrl);
            return;
        }
        visitedUrls.add(pageUrl);
        String content = pageService.getPageContent(pageUrl);
        List<String> foundUrls = Optional.ofNullable(content)
                .map(c -> pageService.getPageUrls(c))
                .orElseGet(Collections::emptyList);
        CrawlResponseBean page = new CrawlResponseBeanBuilder()
                .withUrl(pageUrl)
                .withCrawledUrls(foundUrls)
                .build();
        crawledPages.add(page);
        foundUrls.forEach(
                foundUrl -> crawlPage(foundUrl, subdomain, visitedUrls, crawledPages)
        );
    }
}

