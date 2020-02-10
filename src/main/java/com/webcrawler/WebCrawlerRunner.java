package com.webcrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WebCrawlerRunner implements CommandLineRunner {
    @Autowired
    private WebCrawlerManager webCrawlerManager;
    @Override
    public void run(String... args) {
        webCrawlerManager.crawl("https://www.masrawy.com");
    }
}
