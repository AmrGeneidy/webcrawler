package com.webcrawler;

import com.webcrawler.model.Page;
import com.webcrawler.service.*;
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
    @Autowired
    private PageSenderService pageSenderService;
    @Autowired
    private PersistenceManagementService persistenceManagementService;

    public void crawl(String url) {
        urlFrontierService.enqueue(url);
        while (!urlFrontierService.isEmpty()) {
            String currUrl = urlFrontierService.dequeue();
            System.out.println("fetching : " + currUrl);
            pageSenderService.sendPage(new Page(currUrl, ""));
            persistenceManagementService.save(new Page(currUrl, ""));
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
