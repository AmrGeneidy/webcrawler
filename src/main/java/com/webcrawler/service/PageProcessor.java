package com.webcrawler.service;

import com.webcrawler.model.Page;
import com.webcrawler.model.UrlContainer;
import com.webcrawler.service.events.UrlSenderService;
import com.webcrawler.service.persistence.UrlPersistenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <ul>
 *     <li>Extracts url from a given HTML page content.</li>
 *     <li>Checks for Urls duplicate.</li>
 *     <li>Sends extracted Urls to urlFrontier kafka topic.</li>
 * </ul>
 */
@Service
public class PageProcessor {

    @Autowired
    private ExtractorService extractorService;
    @Autowired
    private UrlSenderService urlSenderService;

    @Value("${crawl.domain}")
    private String urlDomain;

    @Autowired
    private UrlPersistenceManager urlPersistenceManager;

    public CompletableFuture<Void> processPage(Page page) {
        List<String> urls = extractorService.extractUrls(page.getBody());
        List<CompletableFuture<SendResult<String, UrlContainer>>> extractedUrls = urls.stream()
                .filter(link -> link.contains(urlDomain))
                .map(link -> urlPersistenceManager.contains(link).thenCompose(isVisited -> {
                            if (isVisited) {
                                return CompletableFuture.completedFuture(null);
                            }
                            return urlSenderService.sendUrl(new UrlContainer(link));
                        }
                )).collect(Collectors.toList());
        return CompletableFuture.allOf(extractedUrls.toArray(new CompletableFuture[extractedUrls.size()]));
    }
}
