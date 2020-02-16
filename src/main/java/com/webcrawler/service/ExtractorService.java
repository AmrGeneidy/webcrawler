package com.webcrawler.service;

import java.util.List;

/**
 * Extracts Urls from a given HTML page content.
 */
public interface ExtractorService {
    List<String> extractUrls(String htmlContent);
}
