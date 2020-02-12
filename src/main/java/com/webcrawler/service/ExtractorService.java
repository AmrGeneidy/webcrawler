package com.webcrawler.service;

import java.util.List;

public interface ExtractorService {
    List<String> extractUrls(String htmlContent);
}
