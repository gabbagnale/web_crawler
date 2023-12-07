package com.dorbit.web_crawler.controller.crawl;

import com.dorbit.web_crawler.controller.crawl.bean.CrawlResponseBean;
import com.dorbit.web_crawler.exception.OpenConnectionException;
import com.dorbit.web_crawler.exception.ValidationException;
import com.dorbit.web_crawler.service.CrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.util.List;

@Component
public class CrawlDelegate {
    @Autowired private CrawlService crawlService;

    public List<CrawlResponseBean> crawl(String url) {
        try {
            URL startingUrl = new URI(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) startingUrl.openConnection();
            connection.setRequestMethod("GET");
            return crawlService.crawl(startingUrl, connection);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new ValidationException("Requested url not valid");
        } catch (IOException e) {
            throw new OpenConnectionException("Could not connect to the url " + url);
        }
    }
}
