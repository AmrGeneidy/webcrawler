package com.webcrawler;

import com.webcrawler.model.UrlContainer;
import com.webcrawler.service.events.UrlSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WebCrawlerRunner implements CommandLineRunner {

    @Value("${crawl.url}")
    private String url;
    @Autowired
    private UrlSenderService urlSenderService;

    @Override
    public void run(String... args) {
        urlSenderService.sendUrl(new UrlContainer(url)).join();
    }
}
