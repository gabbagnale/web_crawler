package com.dorbit.web_crawler.service;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class PageService {
    private static final Logger logger = Logger.getLogger(PageService.class.getName());

    private static final String LINK_REGEXP = "<a\\s+?href=([\"'])(https?://(?:www\\.)?[^\\s\"']+?)\\1";

    public String getPageContent(String pageUrl) {
        try {
            URL url = new URI(pageUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return getPageContent(connection);
        }  catch (IOException | URISyntaxException e) {
            logger.log(Level.INFO, "Unable to connect to page {}, continue the crawl", pageUrl);
            return null;
        }
    }

    public String getPageContent(HttpURLConnection connection) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = reader.lines().collect(Collectors.joining());
            reader.close();
            return content;
        } catch (IOException e) {
            //return empty page to continue the crawling
            return null;
        }
    }

    public List<String> getPageUrls(String pageContent) {
        Pattern pattern = Pattern.compile(LINK_REGEXP,  Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pageContent);
        List<String> pageUrls = new ArrayList<>();
        while (matcher.find()) {
            String foundUrl = matcher.group(2);
            pageUrls.add(foundUrl);
        }
        return pageUrls;
    }

    public String getSubdomain(URL url) {
        String host = url.getHost();
        return host.startsWith("www.") ? host.substring(4) : host;
    }

    public boolean isSameSubdomain(String pageUrl, String subdomain) {
        try {
            URL url = new URI(pageUrl).toURL();
            return getSubdomain(url).equalsIgnoreCase(subdomain);
        } catch (MalformedURLException | IllegalArgumentException | URISyntaxException e ) {
            //we don't want to fail in this case
            return false;
        }
    }
}
