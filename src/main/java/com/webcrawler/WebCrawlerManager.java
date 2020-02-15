package com.webcrawler;

import com.webcrawler.model.Page;
import com.webcrawler.model.UrlContainer;
import com.webcrawler.service.*;
import com.webcrawler.service.events.PageSenderService;
import com.webcrawler.service.events.UrlSenderService;
import com.webcrawler.service.persistence.UrlPersistenceManager;
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
    private UrlSenderService urlSenderService;
    @Autowired
    private UrlPersistenceManager persistenceManagementService;

    public void crawl(String url) {
        urlFrontierService.enqueue(url);
        while (!urlFrontierService.isEmpty()) {
            String currUrl = urlFrontierService.dequeue();
            System.out.println("fetching : " + currUrl);
            pageSenderService.sendPage(new Page(currUrl, "random"));
            persistenceManagementService.save(new Page(currUrl, "random")).join();
            System.out.println(persistenceManagementService.getBody(currUrl).join());
            urlFetcherService.fetch(currUrl).thenApply(extractorService::extractUrls)
                    .thenApply(links -> links.stream()
                            .filter(link -> link.startsWith(url) && !urlDedupService.isDuplicate(link))
                            .peek(link -> urlFrontierService.enqueue(link.trim()))
                            .peek(link -> urlSenderService.sendUrl(new UrlContainer(link)).join())
                            .collect(Collectors.toList()))
                    .join();
        }
    }
}
