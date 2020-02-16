package com.webcrawler.service;

import com.webcrawler.model.Page;
import com.webcrawler.service.events.PageSenderService;
import com.webcrawler.service.persistence.UrlPersistenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Process Urls by  <ul>
 *     <li>Checking for duplicate.</li>
 *     <li>Fetching the HTML corresponding to the URL.</li>
 *     <li>Mark Url as visited.</li>
 *     <li>Send the fetched {@link Page} to kafka for further processing.</li>
 * </ul>
 */
@Service
public class UrlProcessor {

    @Autowired
    private UrlFetcherService urlFetcherService;
    @Autowired
    private PageSenderService pageSenderService;
    @Autowired
    private UrlPersistenceManager urlPersistenceManager;

    public CompletableFuture<Void> process(String url) {
        return urlPersistenceManager.contains(url).thenCompose(isUrlVisited -> {
            if (isUrlVisited) {
                return CompletableFuture.completedFuture(null);
            }
            return urlFetcherService.fetch(url)
                    .thenCompose(body -> CompletableFuture.allOf(urlPersistenceManager.insert(url),
                            pageSenderService.sendPage(new Page(url, body))));
        });
    }

}
