package com.webcrawler.service;

import com.webcrawler.model.Page;
import com.webcrawler.model.UrlContainer;
import com.webcrawler.service.events.UrlSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageProcessor {

    @Autowired
    private ExtractorService extractorService;
    @Autowired
    private UrlDedupService urlDedupService;
    @Autowired
    private UrlSenderService urlSenderService;

    @Value("${crawl.domain}")
    private String urlDomain;

    public void processPage(Page page){
        List<String> urls = extractorService.extractUrls(page.getBody());
        urls.stream()
                .filter(link -> link.contains(urlDomain) && !urlDedupService.isDuplicate(link))
                .map(link -> urlSenderService.sendUrl(new UrlContainer(link)).join())
                .collect(Collectors.toList());
    }
}
