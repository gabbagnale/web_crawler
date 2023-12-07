package com.dorbit.web_crawler.controller.crawl.bean;

import java.util.List;

public class CrawlResponseBean {
    private String url;
    private List<String> crawledUrls;

    public CrawlResponseBean() {
    }

    public CrawlResponseBean(String url, List<String> crawledUrls) {
        this.url = url;
        this.crawledUrls = crawledUrls;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getCrawledUrls() {
        return crawledUrls;
    }

    @Override
    public String toString() {
        return "CrawlResponseBean{" +
                "url='" + url + '\'' +
                ", crawledUrls=" + crawledUrls +
                '}';
    }
}
