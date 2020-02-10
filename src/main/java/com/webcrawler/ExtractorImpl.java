package com.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Component
public class ExtractorImpl implements Extractor {
    @Override
    public List<String> extractUrls(String htmlContent) {
        Elements elements = Jsoup.parse(htmlContent).select("a[href]");
        return elements.stream()
                .map(e -> e.attr("abs:href"))
                .filter(not(String::isEmpty))
                .collect(Collectors.toList());
    }
}
