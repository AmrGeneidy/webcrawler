package com.webcrawler.service;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Service
public class ExtractorServiceImpl implements ExtractorService {
    @Override
    public List<String> extractUrls(String htmlContent) {
        Elements elements = Jsoup.parse(htmlContent).select("a[href]");
        return elements.stream()
                .map(e -> e.attr("abs:href"))
                .filter(not(String::isEmpty))
                .collect(Collectors.toList());
    }
}
