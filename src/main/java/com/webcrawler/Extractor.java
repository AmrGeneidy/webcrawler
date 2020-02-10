package com.webcrawler;

import java.util.List;

public interface Extractor {
    List<String> extractUrls(String htmlContent);
}
