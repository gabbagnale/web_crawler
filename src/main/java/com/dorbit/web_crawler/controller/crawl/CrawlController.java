package com.dorbit.web_crawler.controller.crawl;

import com.dorbit.web_crawler.controller.crawl.bean.CrawlResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CrawlController {

    @Autowired private CrawlDelegate delegate;

    @GetMapping(value = "/crawl")
    @ResponseBody
    public List<CrawlResponseBean> crawl(@RequestParam String url) {
        return delegate.crawl(url);
    }
}
