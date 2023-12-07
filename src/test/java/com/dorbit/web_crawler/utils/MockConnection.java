package com.dorbit.web_crawler.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MockConnection extends HttpURLConnection {

    public static final String MOCK_CONTENT = "<div class=\"row test-site\">" +
            "<div class=\"col-lg-7 order-lg-2\">" +
            "<h2 class=\"site-heading\">" +
            "<a href=\"/test-sites/e-commerce/static\">" +
            "E-commerce site with pagination links" +
            "</a>" +
            "</h2>" +
            "<p class=\"lead\">" +
            "E-commerce site with multiple categories, subcategories." +
            "Standard links are used for pagination." +
            "</p>" +
            "</div>" +
            "<div class=\"col-lg-5 order-lg-1\">" +
            "<img src=\"/images/test-sites/screens/test-sites-ecommerce-static.png\" alt=\"E-commerce site\">" +
            "</div>" +
            "</div>";

    public MockConnection(URL url) {
        super(url);
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public void disconnect() {
    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(MOCK_CONTENT.getBytes(StandardCharsets.UTF_8));
    }
}
