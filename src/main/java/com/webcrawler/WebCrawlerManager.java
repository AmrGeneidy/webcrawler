package com.webcrawler;

import com.webcrawler.model.UrlContainer;
import com.webcrawler.service.events.UrlSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebCrawlerManager {

    @Autowired
    private UrlSenderService urlSenderService;

    public void crawl(String url) {
        urlSenderService.sendUrl(new UrlContainer(url)).join();
    }
}
