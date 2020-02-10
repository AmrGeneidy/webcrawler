package com.webcrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WebCrawlerManager {
    @Autowired
    private Extractor extractor;
    @Autowired
    private UrlDedup urlDedup;
    @Autowired
    private UrlFrontier urlFrontier;
    @Autowired
    private UrlFetcher urlFetcher;

    public void crawl(String url) {
        urlFrontier.enqueue(url);
        while (!urlFrontier.isEmpty()) {
            String currUrl = urlFrontier.dequeue();
            System.out.println("fetching : " + currUrl);
            urlFetcher.fetch(currUrl)
                    .thenApply(extractor::extractUrls)
                    .thenApply(links -> links.stream()
                            .filter(link -> link.startsWith(url) && !urlDedup.isDuplicate(link))
                            .peek(link -> urlFrontier.enqueue(link.trim()))
                            .collect(Collectors.toList()))
                    .join();
        }
    }
}
