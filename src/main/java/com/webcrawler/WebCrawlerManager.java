package com.webcrawler;

import com.webcrawler.service.ExtractorService;
import com.webcrawler.service.UrlDedupService;
import com.webcrawler.service.UrlFetcherService;
import com.webcrawler.service.UrlFrontierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WebCrawlerManager {
    @Autowired
    private ExtractorService extractorService;
    @Autowired
    private UrlDedupService urlDedupService;
    @Autowired
    private UrlFrontierService urlFrontierService;
    @Autowired
    private UrlFetcherService urlFetcherService;

    public void crawl(String url) {
        urlFrontierService.enqueue(url);
        while (!urlFrontierService.isEmpty()) {
            String currUrl = urlFrontierService.dequeue();
            System.out.println("fetching : " + currUrl);
            urlFetcherService.fetch(currUrl)
                    .thenApply(extractorService::extractUrls)
                    .thenApply(links -> links.stream()
                            .filter(link -> link.startsWith(url) && !urlDedupService.isDuplicate(link))
                            .peek(link -> urlFrontierService.enqueue(link.trim()))
                            .collect(Collectors.toList()))
                    .join();
        }
    }
}
