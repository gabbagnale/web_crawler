package com.dorbit.web_crawler.controller.crawl.bean;

import java.util.List;

public final class CrawlResponseBeanBuilder {
    private String url;
    private List<String> crawledUrls;

    public CrawlResponseBeanBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public CrawlResponseBeanBuilder withCrawledUrls(List<String> crawledUrls) {
        this.crawledUrls = crawledUrls;
        return this;
    }

    public CrawlResponseBean build() {
        return new CrawlResponseBean(url, crawledUrls);
    }
}
