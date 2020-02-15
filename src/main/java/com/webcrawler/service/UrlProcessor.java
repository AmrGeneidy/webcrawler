package com.webcrawler.service;

import com.webcrawler.model.UrlContainer;
import com.webcrawler.service.events.UrlSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UrlProcessor {

    @Autowired
    private ExtractorService extractorService;
    @Autowired
    private UrlDedupService urlDedupService;
    @Autowired
    private UrlFetcherService urlFetcherService;
    @Autowired
    private UrlSenderService urlSenderService;

    public void process(String url){
        urlFetcherService.fetch(url).thenApply(extractorService::extractUrls)
                .thenApply(links -> links.stream()
                        .filter(link -> link.startsWith(url) && !urlDedupService.isDuplicate(link))
                        .map(link -> urlSenderService.sendUrl(new UrlContainer(link)).join())
                        .collect(Collectors.toList()))
                .join();
    }

}
