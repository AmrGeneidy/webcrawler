package com.webcrawler.service;

import com.webcrawler.model.Page;
import com.webcrawler.service.events.PageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlProcessor {

    @Autowired
    private UrlDedupService urlDedupService;
    @Autowired
    private UrlFetcherService urlFetcherService;

    @Autowired
    private PageSenderService pageSenderService;

    public void process(String url){
        if(urlDedupService.visit(url)){
            urlFetcherService.fetch(url).thenApply(body -> pageSenderService.sendPage(new Page(url, body))).join();
        }
    }

}
